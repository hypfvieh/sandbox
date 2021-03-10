package org.freedesktop.login1;

import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.UInt32;

/**
 * Auto-generated class.
 */
public class ListInhibitorsStruct extends Struct {
    @Position(0)
    private final String member0;
    @Position(1)
    private final String member1;
    @Position(2)
    private final String member2;
    @Position(3)
    private final String member3;
    @Position(4)
    private final UInt32 member4;
    @Position(5)
    private final UInt32 member5;

    public ListInhibitorsStruct(String member0, String member1, String member2, String member3, UInt32 member4, UInt32 member5) {
        this.member0 = member0;
        this.member1 = member1;
        this.member2 = member2;
        this.member3 = member3;
        this.member4 = member4;
        this.member5 = member5;
    }


    public String getMember0() {
        return member0;
    }

    public String getMember1() {
        return member1;
    }

    public String getMember2() {
        return member2;
    }

    public String getMember3() {
        return member3;
    }

    public UInt32 getMember4() {
        return member4;
    }

    public UInt32 getMember5() {
        return member5;
    }


}