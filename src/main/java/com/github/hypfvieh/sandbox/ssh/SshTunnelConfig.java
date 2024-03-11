package com.github.hypfvieh.sandbox.ssh;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration parameters for any SSH tunnel implementation.
 *
 * @author hypfvieh
 */
public class SshTunnelConfig {
    private String                 sshHost;
    private int                    sshPort;
    private File                   privateKeyFile;
    private String                 keyFilePassword;
    private String                 sshUserName;
    private String                 sshUserPassword;
    private List<SshForwardTarget> forwardTargets = new ArrayList<>();

    private boolean                ignoreHostKeys;

    public boolean isIgnoreHostKeys() {
        return ignoreHostKeys;
    }

    public void setIgnoreHostKeys(boolean _ignoreHostKeys) {
        ignoreHostKeys = _ignoreHostKeys;
    }

    public String getSshHost() {
        return sshHost;
    }

    public void setSshHost(String _sshHost) {
        sshHost = _sshHost;
    }

    public int getSshPort() {
        return sshPort;
    }

    public void setSshPort(int _sshPort) {
        sshPort = _sshPort;
    }

    public File getPrivateKeyFile() {
        return privateKeyFile;
    }

    public void setPrivateKeyFile(File _privateKeyFile) {
        privateKeyFile = _privateKeyFile;
    }

    public String getKeyFilePassword() {
        return keyFilePassword;
    }

    public void setKeyFilePassword(String _keyFilePassword) {
        keyFilePassword = _keyFilePassword;
    }

    public String getSshUserName() {
        return sshUserName;
    }

    public void setSshUserName(String _sshUserName) {
        sshUserName = _sshUserName;
    }

    public String getSshUserPassword() {
        return sshUserPassword;
    }

    public void setSshUserPassword(String _sshUserPassword) {
        sshUserPassword = _sshUserPassword;
    }

    public List<SshForwardTarget> getForwardTargets() {
        return forwardTargets;
    }

    public void setForwardTargets(List<SshForwardTarget> _forwardTargets) {
        forwardTargets = _forwardTargets;
    }

}
