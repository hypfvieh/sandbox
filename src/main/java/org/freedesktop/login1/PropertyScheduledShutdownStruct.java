package org.freedesktop.login1;

import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.UInt64;

/**
 * Auto-generated class.
 */
public class PropertyScheduledShutdownStruct extends Struct {
    @Position(0)
    private final String member0;
    @Position(1)
    private final UInt64 member1;

    public PropertyScheduledShutdownStruct(String member0, UInt64 member1) {
        this.member0 = member0;
        this.member1 = member1;
    }


    public String getMember0() {
        return member0;
    }

    public UInt64 getMember1() {
        return member1;
    }


}