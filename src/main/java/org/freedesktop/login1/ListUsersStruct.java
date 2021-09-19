package org.freedesktop.login1;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.UInt32;

/**
 * Auto-generated class.
 */
public class ListUsersStruct extends Struct {
    @Position(0)
    private final UInt32 member0;
    @Position(1)
    private final String member1;
    @Position(2)
    private final DBusPath member2;

    public ListUsersStruct(UInt32 member0, String member1, DBusPath member2) {
        this.member0 = member0;
        this.member1 = member1;
        this.member2 = member2;
    }


    public UInt32 getMember0() {
        return member0;
    }

    public String getMember1() {
        return member1;
    }

    public DBusPath getMember2() {
        return member2;
    }


}