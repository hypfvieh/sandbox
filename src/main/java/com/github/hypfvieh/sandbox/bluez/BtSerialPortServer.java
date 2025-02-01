package com.github.hypfvieh.sandbox.bluez;

import com.github.hypfvieh.sandbox.bluez.impl.Agent1Impl;
import org.bluez.AgentManager1;
import org.bluez.Profile1;
import org.bluez.ProfileManager1;
import org.bluez.exceptions.BluezCanceledException;
import org.bluez.exceptions.BluezRejectedException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.exceptions.MarshallingException;
import org.freedesktop.dbus.transport.junixsocket.JUnixSocketSocketProvider;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.Variant;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BtSerialPortServer {
    private static final String BLUEZ_BUS_NAME = "org.bluez";
    private static final String PROFILE_MANAGER_PATH = "/org/bluez";
    private static final String SERVICE_UUID = "00001101-0000-1000-8000-00805f9b34fb"; // SPP UUID
    private static final String PROFILE_UUID = "18f1560a-1700-40ac-87f4-e957a999c921"; // My Uuid
    private static final String PROFILE_PATH = "/org/bluez/myprofile";

    public static void main(String[] args) {
        try (DBusConnection connection = DBusConnectionBuilder.forSystemBus().build()) {
            ProfileImpl profile = new ProfileImpl();
            connection.exportObject(PROFILE_PATH, profile);

            // used for pairing devices
            // might show a dialog asking for the passphrase seen on the device to pair
            Agent1Impl btAgent = new Agent1Impl();
            connection.exportObject("/", btAgent);

            AgentManager1 agentManager1 = connection.getRemoteObject(BLUEZ_BUS_NAME, PROFILE_MANAGER_PATH, AgentManager1.class);

            System.out.println("Registering Agent1 on AgentManager1");
            agentManager1.RegisterAgent(new DBusPath(btAgent.getObjectPath()), "KeyboardOnly");

            System.out.println("Registering Agent1 as default agent");
            agentManager1.RequestDefaultAgent(new DBusPath(btAgent.getObjectPath()));

            ProfileManager1 profileManager = connection.getRemoteObject(
                BLUEZ_BUS_NAME, PROFILE_MANAGER_PATH, ProfileManager1.class);

            // Register the custom profile
            Map<String, Variant<?>> props = new HashMap<String, Variant<?>>();
            props.put("Role", new Variant<>("server"));
            props.put("Name", new Variant<>("myprofile"));
            props.put("RequireAuthentication", new Variant<>(false));
            props.put("RequireAuthorization", new Variant<>(false));
            props.put("AutoConnect", new Variant<>(true));
            props.put("Service", new Variant<>(SERVICE_UUID));
            props.put("Version", new Variant<>(new UInt16(1)));
            props.put("Channel", new Variant<>(new UInt16(1)));

            profileManager.RegisterProfile(new DBusPath(profile.getObjectPath()), PROFILE_UUID, props);

            System.out.println("sleeping");
            Thread.sleep(600000L); // do something useful

            System.out.println("shutting down");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ProfileImpl implements Profile1 {

        @Override
        public boolean isRemote() {
            System.out.println("isRemote() Called");
            return false;
        }

        @Override
        public String getObjectPath() {
            System.out.println("getObjectPath() called");
            return PROFILE_PATH;
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
            System.out.println("New connection: FD=" + _arg1 + ", Path=" + _arg0 + ", Props=" + _arg2);
            FileDescriptor unixFd;

            // example code to read / write data from/to unix socket received as file descriptor
            // this code reads the FD, writes the content to STDOUT and echos it back to the sender prefixing the answer with "Got:"
            // Requires junixsocket as transport (otherwise no file descriptors supported)

            try {
                unixFd = _arg1.toJavaFileDescriptor(new JUnixSocketSocketProvider());
            } catch (MarshallingException _ex) {
                throw new RuntimeException(_ex);
            }

            Thread readerThread = new Thread(() -> {
                boolean run = true;


                try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(unixFd)));
                    var writer = new OutputStreamWriter(new FileOutputStream(unixFd))) {

                    while (run) {
                        if (reader.ready()) {
                            String line = reader.readLine();
                            System.out.println("Received: " + line);
                            writer.write("Got: " + line + "\n");
                            writer.flush();
                        }
                        Thread.sleep(50);
                    }

                } catch (IOException _ex) {
                    _ex.printStackTrace();
                } catch (InterruptedException _ex) {
                    run = false;
                    Thread.currentThread().interrupt();
                }
            });
            readerThread.setName("Echo Thread");
            readerThread.setDaemon(true);
            readerThread.start();

        }
    }
}