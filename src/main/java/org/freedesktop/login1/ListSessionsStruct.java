package org.freedesktop.login1;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.UInt32;

/**
 * Auto-generated class.
 */
public class ListSessionsStruct extends Struct {
    @Position(0)
    private final String member0;
    @Position(1)
    private final UInt32 member1;
    @Position(2)
    private final String member2;
    @Position(3)
    private final String member3;
    @Position(4)
    private final DBusPath member4;

    public ListSessionsStruct(String member0, UInt32 member1, String member2, String member3, DBusPath member4) {
        this.member0 = member0;
        this.member1 = member1;
        this.member2 = member2;
        this.member3 = member3;
        this.member4 = member4;
    }


    public String getMember0() {
        return member0;
    }

    public UInt32 getMember1() {
        return member1;
    }

    public String getMember2() {
        return member2;
    }

    public String getMember3() {
        return member3;
    }

    public DBusPath getMember4() {
        return member4;
    }


}