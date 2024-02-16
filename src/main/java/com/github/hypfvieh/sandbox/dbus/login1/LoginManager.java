package com.github.hypfvieh.sandbox.dbus.login1;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.login1.Manager;

import java.io.IOException;

public class LoginManager {

    public static void main(String[] args) throws DBusException, IOException {
        try (DBusConnection sysConn = DBusConnectionBuilder.forSystemBus().build()) {
            Manager mgr = sysConn.getRemoteObject("org.freedesktop.login1", "/org/freedesktop/login1", org.freedesktop.login1.Manager.class);

            System.out.println("Reboot allowed: " + mgr.CanReboot());
            System.out.println("PowerOff allowed: " + mgr.CanPowerOff());
        }
    }

}
