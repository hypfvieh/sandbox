package com.github.hypfvieh.sandbox.ssh.sshj;

import com.github.hypfvieh.sandbox.ssh.SshForwardTarget;
import com.github.hypfvieh.sandbox.ssh.SshTunnelConfig;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Builder to create a SSH tunnel connection with SSHJ.
 *
 * @author hypfvieh
 */
public class SshjTunnelBuilder {

    private SshTunnelConfig config;

    private SshjTunnelBuilder() {
        config = new SshTunnelConfig();
    }

    /**
     * Creates a new builder instance.
     *
     * @return new instance
     */
    public static SshjTunnelBuilder create() {
        return new SshjTunnelBuilder();
    }

    /**
     * SSH server to connect to.<br>
     * <b>Required</b>
     * @param _host server name or IP
     * @return this
     */
    public SshjTunnelBuilder withSshHost(String _host) {
        config.setSshHost(_host);
        return this;
    }

    /**
     * SSH server port to connect to.<br>
     * Default: 22
     *
     * @param _port port
     * @return
     */
    public SshjTunnelBuilder withSshPort(int _port) {
        config.setSshPort(_port <= 0 || _port > 65535 ? 22 : _port);
        return this;
    }

    /**
     * Private key file to use for authentication.<br>
     * You have to define this or {@link #withSshUserPassword(String)}.<br>
     * You can also configure both.
     *
     * @param _keyFile key file
     * @return this
     */
    public SshjTunnelBuilder withPrivateKeyFile(File _keyFile) {
        config.setPrivateKeyFile(_keyFile);
        return this;
    }

    /**
     * Password used for accessing private key file.
     *
     * @param _password password
     * @return this
     */
    public SshjTunnelBuilder withKeyFilePassword(String _password) {
        config.setKeyFilePassword(_password);
        return this;
    }

    /**
     * Username to use for SSH.<br>
     * Default: current process user
     *
     * @param _user username
     * @return this
     */
    public SshjTunnelBuilder withSshUserName(String _user) {
        config.setSshUserName(_user);
        return this;
    }

    /**
     * Password to authenticate the user if password authentication should be used.<br>
     * You have to define this or use {@link #withPrivateKeyFile(File)}.<br>
     * You can configure both.
     *
     * @param _password password
     * @return this
     */
    public SshjTunnelBuilder withSshUserPassword(String _password) {
        config.setSshUserPassword(_password);
        return this;
    }

    /**
     * Add a forwarding target by hostname/IP and target port.<br>
     * You can use this multiple times to add as much as you want.<br>
     * The local listen port will be determined dynamically and can be queried using<br>
     * {@link SshTunnel#getLocalListenPorts()}.<br>
     * All forwarding connections added by this method will use localhost as local listening address.<br>
     * <br>
     * The map contains local ports for all forwarding connections where the key is created using:<br>
     * _targetHost + : + _targetPort<br>
     * Example:<br>
     * <pre>
     * somemachine:12345
     * </pre>
     *
     * @param _targetHost target host name or IP
     * @param _targetPort target port
     *
     * @return this
     */
    public SshjTunnelBuilder withForwardTarget(String _targetHost, int _targetPort) {
        config.getForwardTargets().add(new SshForwardTarget(_targetHost, _targetPort, "localhost", 0));
        return this;
    }

    /**
     * Add a forwarding target by hostname/IP and target port with a pre-defined local port.<br>
     * You can use this multiple times to add as much as you want.<br>
     *
     * @param _targetHost target host name or IP
     * @param _targetPort target port
     * @param _localPort port to use locally
     *
     * @return this
     */
    public SshjTunnelBuilder withForwardTarget(String _targetHost, int _targetPort, int _localPort) {
        config.getForwardTargets().add(new SshForwardTarget(_targetHost, _targetPort, "localhost", _localPort));
        return this;
    }

    /**
     * Add a forwarding target by hostname/IP and target port.<br>
     * You can use this multiple times to add as much as you want.<br>
     *
     * @param _targetHost target host name or IP
     * @param _targetPort target port
     * @param _localListenAddress address to listen for incoming forward connections
     * @param _localPort local port to use
     *
     * @return this
     */
    public SshjTunnelBuilder withForwardTarget(String _targetHost, int _targetPort, String _localListenAddress, int _localPort) {
        config.getForwardTargets().add(new SshForwardTarget(_targetHost, _targetPort, _localListenAddress, _localPort));
        return this;
    }

    /**
     * Creates a new connection.<br>
     * After calling this method successfully the configuration created beforehand is resettet.
     *
     * @return tunnel
     */
    public SshjTunnel build() {
        if (config.getSshHost() == null || config.getSshHost().isBlank()) {
            throw new IllegalArgumentException("SSH Host is required");
        }

        if (config.getSshUserName() == null || config.getSshUserName().isBlank()) {
            String userName = List.of("user.name", "USER", "USERNAME").stream()
                .map(x -> System.getProperty(x))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("SSH Username required"));
            config.setSshUserName(userName);
        }

        if (config.getSshPort() <= 0) {
            config.setSshPort(22);
        }

        if (config.getForwardTargets().isEmpty()) {
            throw new IllegalArgumentException("No forward targets configured");
        }

        if (config.getSshUserPassword() == null && config.getPrivateKeyFile() == null) {
            throw new IllegalArgumentException("SSH User password or private key file is required");
        }

        @SuppressWarnings("resource")
        SshjTunnel tunnel = new SshjTunnel(config);
        config = new SshTunnelConfig();

        return tunnel.connect();
    }


}
