package com.github.hypfvieh.sandbox.ssh;

import java.io.IOException;
import java.net.Socket;

public class SshTunnelExample {
    public static void main(String[] args) throws InterruptedException {
        try (var tunnel = SshTunnelBuilder.create().withForwardTarget("localhost", 4711).withSshHost("localhost").withSshUserPassword(args[0]).build()) {

            Integer port = tunnel.getLocalListenPorts().get("localhost:4711");

            if (port == null) {
                System.err.println("No port found");
                return;
            }

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
