package com.github.hypfvieh.sandbox.dbus.struct;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.IntFunction;

import org.freedesktop.dbus.StructHelper;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnection.DBusBusType;
import org.freedesktop.dbus.types.DBusStructType;
import org.freedesktop.dbus.types.Variant;

/**
 * Sample on how to use structs wrapped in Variant or not wrapped at all.
 * This is the server side.
 * @author hypfvieh
 * @since v1.0.0 - 2019-10-25
 */
public class StructServer implements IStructServer {

    private boolean method1Done = false;
    private boolean method2Done = false;

    public static void main(String[] args) throws Exception {
        DBusConnection sessionConnection = DBusConnection.getConnection(DBusBusType.SESSION);
        StructServer structServer = new StructServer();

        sessionConnection.requestBusName("hypfvieh.struct.Sample");

        sessionConnection.exportObject("/hypfvieh/struct/StructServer", structServer);

        System.out.println("server started");

        int i = 0;
        while(!structServer.method1Done && !structServer.method2Done) {
            Thread.sleep(1000L);
            i++;
            if (i > 100) {
                break;
            }
        }

        System.out.println("server is shutting down");

        sessionConnection.close();
    }

    @Override
    public String getObjectPath() {
        return "/";
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public void setStructDirectly(SampleStruct _struct) {
        System.out.println("The direct struct says: " + _struct.getAnInt() + " is " + _struct.getaString());
        method1Done = true;
    }

    @Override
    public void setStructFromVariant(Variant<?> _variant) {
        try {
            SampleStruct newInstance = StructHelper.createStruct(argTypes, _variant.getValue(), SampleStruct.class);

            System.out.println("The variant struct says: " + newInstance.getAnInt() + " is " + newInstance.getaString());
            method2Done = true;

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException _ex) {
            // TODO Auto-generated catch block
            _ex.printStackTrace();
        }
    }


}
