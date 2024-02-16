package com.github.hypfvieh.sandbox.bluez;

import org.bluez.Device1;
import org.bluez.Profile1;
import org.bluez.ProfileManager1;
import org.bluez.exceptions.BluezCanceledException;
import org.bluez.exceptions.BluezRejectedException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.types.Variant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConnectProfileExample {
    public static void main(String[] args) throws DBusException, InterruptedException, IOException {
        // open connection to bluez on SYSTEM Bus
        try (DBusConnection connection = DBusConnectionBuilder.forSystemBus().build()) {
            ProfileImpl object = new ProfileImpl();
            connection.exportObject("/", object);
            
            ProfileManager1 profileManager = connection.getRemoteObject("org.bluez", "/org/bluez", ProfileManager1.class);
            
            Map<String, Variant<?>> props = new HashMap<String, Variant<?>>();
            profileManager.RegisterProfile( new DBusPath("/"), "00001101-0000-1000-8000-00805F9B34FB", props);
            
            Device1 device= connection.getRemoteObject("org.bluez", "/org/bluez/hci0/dev_04_4F_4C_FA_04_E7", Device1.class);
            device.ConnectProfile("00001101-0000-1000-8000-00805F9B34FB");
            
            System.out.println("sleeping");
            
            Thread.sleep(60000L);
            
            System.out.println("shutting down");
        }
    }
    
    public static class ProfileImpl implements Profile1 {

        @Override
        public boolean isRemote() {
            return false;
        }

        @Override
        public String getObjectPath() {
            return "/";
        }

        @Override
        public void Release() {
            System.err.println("Release called");
        }

      

        @Override
        public void RequestDisconnection(DBusPath _device) throws BluezRejectedException, BluezCanceledException {
            System.err.println("Disconnect requested: Path=" + _device);
        }

        @Override
        public void NewConnection(DBusPath _arg0, org.freedesktop.dbus.FileDescriptor _arg1,
                Map<String, Variant<?>> _arg2) throws BluezRejectedException, BluezCanceledException {
            System.err.println("New connection: FD=" + _arg1 + ", Path=" + _arg0+ ", Props=" + _arg2);            
        }
        
    }
}
