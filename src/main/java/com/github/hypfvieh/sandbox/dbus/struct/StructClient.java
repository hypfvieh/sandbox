package com.github.hypfvieh.sandbox.dbus.struct;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnection.DBusBusType;
import org.freedesktop.dbus.types.Variant;

/**
 * Sample client connecting to struct server.
 * Will provide the "new" structs to send it to the server.
 *
 * @author hypfvieh
 * @since v1.0.0 - 2019-10-25
 */
public class StructClient {
    public static void main(String[] args) throws Exception {
        try (DBusConnection sessionConnection = DBusConnection.getConnection(DBusBusType.SESSION)) {

            System.out.println("getting server");
            IStructServer remoteServer = sessionConnection.getRemoteObject("hypfvieh.struct.Sample",
                    "/hypfvieh/struct/StructServer", IStructServer.class);

            System.out.println("setting struct to server");

            SampleStruct variantStruct = new SampleStruct(21, "only the half of the truth.");

            System.out.println("Testing struct with variant");
            remoteServer.setStructFromVariant(new Variant<>(variantStruct));

            SampleStruct directStruct = new SampleStruct(42, "the answer for all questions.");

            System.err.println("Testing struct directly");
            remoteServer.setStructDirectly(directStruct);


            System.out.println("closing dbus connection");
        }
    }
}
