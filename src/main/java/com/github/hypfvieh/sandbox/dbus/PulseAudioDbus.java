package com.github.hypfvieh.sandbox.dbus;

import java.io.File;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnection.DBusBusType;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.Properties;

public class PulseAudioDbus {

    public static void main(String[] _args) throws DBusException {
        DBusConnection sessionConnection = DBusConnection.getConnection(DBusBusType.SESSION);

        Properties properties = sessionConnection.getRemoteObject("org.pulseaudio.Server", "/org/pulseaudio/server_lookup1", org.freedesktop.dbus.interfaces.Properties.class);
        String address =  properties.Get("org.PulseAudio.ServerLookup1", "Address");

        System.out.println("Found address: " + address + "\n");

        sessionConnection.disconnect();

        File file = new File(address.replace("unix:path=", ""));
        if (!file.exists()) {
            System.out.println("Extracted address " + address + " is not a valid file/unix-socket");
        } else {
        	DBusConnection connection = DBusConnection.getConnection(address, false, false);

            Properties core1Props = connection.getRemoteObject("org.PulseAudio.Core1", "/org/pulseaudio/core1", org.freedesktop.dbus.interfaces.Properties.class);
            System.out.println("PulseAudio Name: " + core1Props.Get("org.PulseAudio.Core1", "Name"));
            System.out.println("PulseAudio Version: " + core1Props.Get("org.PulseAudio.Core1", "Version"));

            System.out.println("-----------------------------------------");
            System.out.println("PulseAudio Hostname: " + core1Props.Get("org.PulseAudio.Core1", "Hostname"));
            System.out.println("PulseAudio Username: " + core1Props.Get("org.PulseAudio.Core1", "Username"));

            connection.disconnect();
        }

    }

    public static void main2(String[] args) throws DBusException {
        DBusConnection sessionConnection = DBusConnection.getConnection(DBusBusType.SESSION);
        org.freedesktop.dbus.interfaces.Properties properties = sessionConnection.getRemoteObject("org.pulseaudio.Server", "/org/pulseaudio/server_lookup1", org.freedesktop.dbus.interfaces.Properties.class);
        String address = properties.Get("org.PulseAudio.ServerLookup1", "Address");
        sessionConnection.disconnect();

        System.out.println(address);
    }

}
