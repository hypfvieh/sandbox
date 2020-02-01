package com.github.hypfvieh.sandbox.dbus;

import java.io.IOException;
import java.util.List;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnection.DBusBusType;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.networkmanager.device.Wireless;

public class NetworkManagerExample2 {
    public static void main(String[] args) {
        try (DBusConnection dbusConn = DBusConnection.getConnection(DBusBusType.SYSTEM)) {

            Wireless wifiAdaptor = dbusConn.getRemoteObject("org.freedesktop.NetworkManager",
                    "/org/freedesktop/NetworkManager/Devices/0", Wireless.class);
            
            List<DBusPath> getAllAccessPoints = wifiAdaptor.GetAllAccessPoints();
            System.out.println(getAllAccessPoints);

        } catch (IOException | DBusException _ex) {
            _ex.printStackTrace();
        }
    }
}
