package com.github.hypfvieh.sandbox.bluez;

import java.io.IOException;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnection.DBusBusType;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.handlers.AbstractPropertiesChangedHandler;
import org.freedesktop.dbus.interfaces.Properties.PropertiesChanged;

/**
 * Simple sample on how to register a properties changed listener.
 */
public class PropertiesChangedExample {
    public static void main(String[] args) throws DBusException, InterruptedException, IOException {
        // open connection to bluez on SYSTEM Bus
        DBusConnection connection = DBusConnection.getConnection(DBusBusType.SYSTEM);

        connection.addSigHandler(PropertiesChanged.class, new PropChangedHandler());

        System.out.println("sleeping");

        Thread.sleep(60000L);

        System.out.println("shutting down");
        connection.close();
    }

    /**
     * This handler will be called for every change property.
     */
    public static class PropChangedHandler extends AbstractPropertiesChangedHandler {

        @Override
        public void handle(PropertiesChanged _s) {
            System.out.println("changed: " + _s.getPropertiesChanged());
        }

    }
}
