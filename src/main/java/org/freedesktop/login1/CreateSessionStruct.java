package org.freedesktop.login1;

import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.Variant;

/**
 * Auto-generated class.
 */
public class CreateSessionStruct extends Struct {
    @Position(0)
    private final String member0;
    @Position(1)
    private final Variant<?> member1;

    public CreateSessionStruct(String member0, Variant<?> member1) {
        this.member0 = member0;
        this.member1 = member1;
    }


    public String getMember0() {
        return member0;
    }

    public Variant<?> getMember1() {
        return member1;
    }


}