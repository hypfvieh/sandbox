package com.github.hypfvieh.sandbox.ssh;

/**
 * Contains a forwarded host/port combination.
 *
 * @author hypfvieh
 */
public record SshForwardTarget(String targetHost, int targetPort, String localListenAddress, int localPort) {}