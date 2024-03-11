package com.github.hypfvieh.sandbox.ssh.jsch;

import com.github.hypfvieh.sandbox.ssh.SshForwardTarget;
import com.github.hypfvieh.sandbox.ssh.SshTunnelConfig;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A SSH tunnel based on JSCH.
 *
 * @author hypfvieh
 */
public class JschTunnel implements Closeable {

    private final SshTunnelConfig config;
    private final JSch jsch;
    private final Map<String, Integer> localListenPorts;

    private Session session;

    JschTunnel(SshTunnelConfig _config) {
        localListenPorts = new ConcurrentHashMap<>();
        config = _config;
        jsch = new JSch();
    }

    JschTunnel connect() throws JSchException {
        if (config.getPrivateKeyFile() != null && config.getPrivateKeyFile().exists() && config.getPrivateKeyFile().canRead()) {
            jsch.addIdentity(config.getPrivateKeyFile().getAbsolutePath(), config.getKeyFilePassword());
        }

        session = jsch.getSession(config.getSshUserName(), config.getSshHost(), config.getSshPort());

        if (config.isIgnoreHostKeys()) {
            session.setConfig("StrictHostKeyChecking", "no");
        }

        if (config.getSshUserPassword() != null) {
            session.setPassword(config.getSshUserPassword());
        }

        session.connect();

        for (SshForwardTarget targets : config.getForwardTargets()) {
            int localPort = session.setPortForwardingL(targets.localListenAddress(), targets.localPort(), targets.targetHost(), targets.targetPort());
            localListenPorts.put(targets.targetHost() + ":" + targets.targetPort(), localPort);
        }

        return this;
    }

    @Override
    public void close() throws IOException {
        if (session != null) {
            session.disconnect();
        }
    }

    public Map<String, Integer> getLocalListenPorts() {
        return Collections.unmodifiableMap(localListenPorts);
    }

}
