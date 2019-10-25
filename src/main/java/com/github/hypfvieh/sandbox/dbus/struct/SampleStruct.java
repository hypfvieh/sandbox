package com.github.hypfvieh.sandbox.dbus.struct;

import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.Variant;

/**
 * Sample Struct to demonstrate struct usage in {@link Variant}s.
 *
 * @author hypfvieh
 * @since v1.0.0 - 2019-10-25
 */
public class SampleStruct extends Struct {
    @Position(0)
    private int anInt;

    @Position(1)
    private String aString;

    public SampleStruct(int _anInt, String _aString) {
        super();
        anInt = _anInt;
        aString = _aString;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int _anInt) {
        anInt = _anInt;
    }

    public String getaString() {
        return aString;
    }

    public void setaString(String _aString) {
        aString = _aString;
    }



}
