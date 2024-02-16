package com.github.hypfvieh.sandbox.bluez;

import com.github.hypfvieh.sandbox.bluez.impl.Agent1Impl;

import org.bluez.AgentManager1;
import org.bluez.Device1;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;

public class AgentManagerExample {

    public static void main(String[] args) throws Exception {
        try (DBusConnection connection = DBusConnectionBuilder.forSystemBus().build()) {
            System.out.println("Retrieving AgentManager1");
            AgentManager1 agentManager1 = connection.getRemoteObject("org.bluez", "/org/bluez", AgentManager1.class);
            System.out.println("Creating Agent1");
            Agent1Impl btAgent = new Agent1Impl();

            System.out.println("Registering Agent1 on AgentManager1");
            agentManager1.RegisterAgent(new DBusPath(btAgent.getObjectPath()), "KeyboardOnly");

            System.out.println("Registering Agent1 as default agent");
            agentManager1.RequestDefaultAgent(new DBusPath(btAgent.getObjectPath()));

            System.out.println("Exporting Agent1 to Dbus");
            connection.exportObject("/", btAgent);

            // Replace MAC address with the MAC of your bluetooth device
            String deviceMac = "dev_AA:BB:CC:DD:EE:FF".replace(":", "_");

            Device1 device = connection.getRemoteObject("org.bluez", "/org/bluez/hci0/" + deviceMac, Device1.class);
            if (device != null) {
                device.Connect();
                System.out.println("Connected!");
            }

            System.out.println("All Done");
        }
    }

}
