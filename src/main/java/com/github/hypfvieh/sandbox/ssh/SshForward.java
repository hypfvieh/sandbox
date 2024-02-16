package com.github.hypfvieh.sandbox.ssh;

import net.schmizz.sshj.common.LoggerFactory;
import net.schmizz.sshj.connection.Connection;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;
import net.schmizz.sshj.connection.channel.direct.Parameters;

import java.net.ServerSocket;

public class SshForward extends LocalPortForwarder {

    public SshForward(Connection _conn, Parameters _parameters, ServerSocket _serverSocket, LoggerFactory _loggerFactory) {
        super(_conn, _parameters, _serverSocket, _loggerFactory);
    }

}
