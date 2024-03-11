package com.github.hypfvieh.sandbox.ssh.jsch;

import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class JschTunnelExample {
    public static void main(String[] args) throws InterruptedException {

        try (var tunnel = JschTunnelBuilder.create().withForwardTarget("localhost", 4711)
            .withSshHost("localhost").withSshUserPassword(args[0])
            .withIgnoreHostKey(true)
            .build()) {

            Integer port = tunnel.getLocalListenPorts().get("localhost:4711");

            if (port == null) {
                System.err.println("No port found");
                return;
            }

            System.out.println("Found port: " + port);

            try (Socket clientSocket = new Socket("localhost", port)) {
                System.out.println("writing message");
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write("Hello World\n".getBytes());
                outputStream.flush();
                Thread.sleep(1000);
            }

        } catch (IOException | JSchException _ex) {
            _ex.printStackTrace();
        }
    }
}
