package org.freedesktop.login1;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;

/**
 * Auto-generated class.
 */
public class ListSeatsStruct extends Struct {
    @Position(0)
    private final String member0;
    @Position(1)
    private final DBusPath member1;

    public ListSeatsStruct(String member0, DBusPath member1) {
        this.member0 = member0;
        this.member1 = member1;
    }


    public String getMember0() {
        return member0;
    }

    public DBusPath getMember1() {
        return member1;
    }


}