package com.github.hypfvieh.sandbox.ssh.sshj;

import java.io.IOException;
import java.net.Socket;

/**
 * Example on how to use SSHJ based SSH Tunnel.
 */
public class SshjTunnelExample {
    public static void main(String[] args) throws InterruptedException {
        try (var tunnel = SshjTunnelBuilder.create().withForwardTarget("localhost", 4711).withSshHost("localhost").withSshUserPassword(args[0]).build()) {

            Integer port = tunnel.getLocalListenPorts().get("localhost:4711");

            if (port == null) {
                System.err.println("No port found");
                return;
            }

            // we need a bit of time to establish the connection (due to concurrency)
            // otherwise the tunnel will be closed before any connection is made
            Thread.sleep(1000);

            System.out.println("Found port: " + port);
            try (Socket clientSocket = new Socket("localhost", port)) {
                System.out.println("writing message");
                clientSocket.getOutputStream().write("Hello World\n".getBytes());
                clientSocket.getOutputStream().flush();
            }

        } catch (IOException _ex) {
            _ex.printStackTrace();
        }
    }
}
