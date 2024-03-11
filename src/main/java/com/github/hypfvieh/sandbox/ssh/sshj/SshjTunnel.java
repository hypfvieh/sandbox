package com.github.hypfvieh.sandbox.ssh.sshj;

import com.github.hypfvieh.sandbox.ssh.SshForwardTarget;
import com.github.hypfvieh.sandbox.ssh.SshTunnelConfig;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;
import net.schmizz.sshj.connection.channel.direct.Parameters;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import net.schmizz.sshj.userauth.method.AbstractAuthMethod;
import net.schmizz.sshj.userauth.method.AuthMethod;
import net.schmizz.sshj.userauth.method.AuthPassword;
import net.schmizz.sshj.userauth.method.AuthPublickey;
import net.schmizz.sshj.userauth.password.PasswordFinder;
import net.schmizz.sshj.userauth.password.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents a SSH connection with several forwarding connections using SSHJ.
 *
 * @author hypfvieh
 */
public class SshjTunnel implements Closeable {
    private static final AtomicLong TC_INSTANCE_COUNTER = new AtomicLong(1);

    private final Logger logger = LoggerFactory.getLogger(getClass());;

    private final SshTunnelConfig config;
    private final ExecutorService threadPool;

    private final Map<String, Integer> localListenPorts = new ConcurrentHashMap<>();
    private final List<Future<?>> tasks;

    private SSHClient ssh;

    SshjTunnel(SshTunnelConfig _config) {
        config = _config;
        tasks = new ArrayList<>();
        threadPool =  Executors.newFixedThreadPool(_config.getForwardTargets().size(), r -> {
            Thread t = new Thread(r, "SshTunnel-" + TC_INSTANCE_COUNTER.getAndIncrement() + "-Thread");
            t.setDaemon(true);
            return t;
        });
    }

    /**
     * Create the connection to the SSH server and start all forwarding connections.
     * @return this
     */
    SshjTunnel connect() {
        CountDownLatch latch = new CountDownLatch(config.getForwardTargets().size());
        try {
            ssh = new SSHClient();

            if (config.isIgnoreHostKeys()) {
                ssh.addHostKeyVerifier(new PromiscuousVerifier());
            } else {
                ssh.loadKnownHosts();
            }

            ssh.connect(config.getSshHost(), config.getSshPort());

            List<AbstractAuthMethod> authMethods = configureAuthMethods();

            ssh.auth(config.getSshUserName(), authMethods.toArray(AuthMethod[]::new));

            for (SshForwardTarget targets : config.getForwardTargets()) {
                ServerSocket serverSock = new ServerSocket();

                serverSock.setReuseAddress(true);
                serverSock.bind(new InetSocketAddress(targets.localListenAddress(), targets.localPort()));

                final Parameters params = new Parameters(targets.localListenAddress(), serverSock.getLocalPort(), targets.targetHost(), targets.targetPort());

                final LocalPortForwarder portForward = ssh.newLocalPortForwarder(params, serverSock);

                tasks.add(threadPool.submit(() -> {
                    try {
                        localListenPorts.put(targets.targetHost() + ":" + targets.targetPort(), serverSock.getLocalPort());
                        latch.countDown();
                        portForward.listen();
                    } catch (IOException _ex) {
                        logger.error("Unable to create forwarding connection", _ex);
                    }
                }));

            }

        } catch (IOException _ex) {
            logger.error("Connection failed", _ex);
        }

        return this;
    }

    private List<AbstractAuthMethod> configureAuthMethods() throws IOException, UserAuthException, TransportException {
        List<AbstractAuthMethod> authMethods = new ArrayList<>();

        if (config.getPrivateKeyFile() != null && config.getPrivateKeyFile().exists() && config.getPrivateKeyFile().canRead()) {
            KeyProvider keys = ssh.loadKeys(config.getPrivateKeyFile().getAbsolutePath(), config.getKeyFilePassword());
            authMethods.add(new AuthPublickey(keys));
        }

        if (config.getSshUserPassword() != null) {
            authMethods.add(new AuthPassword(new PasswordFinder() {

                @Override
                public char[] reqPassword(Resource<?> resource) {
                    return config.getSshUserPassword().toCharArray().clone();
                }

                @Override
                public boolean shouldRetry(Resource<?> resource) {
                    return false;
                }

            }));
        }
        return authMethods;
    }

    public Map<String, Integer> getLocalListenPorts() {
        return Collections.unmodifiableMap(localListenPorts);
    }

    @Override
    public void close() {
        if (ssh != null) {
            try {
                ssh.close();
            } catch (Exception _ex) {
                logger.warn("Error closing ssh connection", _ex);
            }
        }

        for (Future<?> task : tasks) {
            try {
                task.cancel(true);
            } catch (Exception _ex) {
                logger.warn("Error canceling task", _ex);
            }
        }

    }
}
