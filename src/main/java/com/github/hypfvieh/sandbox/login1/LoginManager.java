package com.github.hypfvieh.sandbox.login1;

import java.io.IOException;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnection.DBusBusType;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.login1.Manager;

public class LoginManager {

    public static void main(String[] args) throws DBusException, IOException {
        try (DBusConnection sysConn = DBusConnection.getConnection(DBusBusType.SYSTEM)) {
            Manager mgr = sysConn.getRemoteObject("org.freedesktop.login1", "/org/freedesktop/login1", org.freedesktop.login1.Manager.class);

            System.out.println("Reboot allowed: " + mgr.CanReboot());
            System.out.println("PowerOff allowed: " + mgr.CanPowerOff());
        }
    }

}
