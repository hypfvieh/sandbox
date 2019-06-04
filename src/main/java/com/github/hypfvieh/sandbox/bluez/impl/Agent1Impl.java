package com.github.hypfvieh.sandbox.bluez.impl;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.bluez.Agent1;
import org.bluez.exceptions.BluezCanceledException;
import org.bluez.exceptions.BluezRejectedException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.UInt32;

import com.github.hypfvieh.util.TypeUtil;

public class Agent1Impl implements Agent1 {
    @Override
    public void Release() {
        System.out.println("release called");
    }

    @Override
    public String RequestPinCode(DBusPath _device) throws BluezRejectedException, BluezCanceledException {
        System.out.println("RequestPinCode called");
        return null;
    }

    @Override
    public void DisplayPinCode(DBusPath _device, String _pincode) throws BluezRejectedException, BluezCanceledException {
        System.out.println("DisplayPinCode called");
    }

    @Override
    public UInt32 RequestPasskey(DBusPath _device) throws BluezRejectedException, BluezCanceledException {
        System.out.println("RequestPasskey called");
        String passKey = JOptionPane.showInputDialog("Please enter bluetooth pass key:");
        
        if (passKey != null) {
            String maskedKey = passKey.length() >= 2 ? passKey.substring(0, 2) + (StringUtils.repeat('*', passKey.length() - 2)) : "**";
            System.out.println("Passkey " + maskedKey + " entered");
            
            if (TypeUtil.isInteger(passKey)) {
                return new UInt32(Integer.parseInt(passKey));
            }
        }
        return new UInt32(0);
    }

    @Override
    public void DisplayPasskey(DBusPath _device, UInt32 _passkey, UInt16 _entered) {
        System.out.println("DisplayPasskey called");
    }

    @Override
    public void RequestConfirmation(DBusPath _device, UInt32 _passkey) throws BluezRejectedException, BluezCanceledException {
        System.out.println("RequestConfirmation called");
    }

    @Override
    public void RequestAuthorization(DBusPath _device) throws BluezRejectedException, BluezCanceledException {
        System.out.println("RequestAuthorization called");
    }

    @Override
    public void AuthorizeService(DBusPath _device, String _uuid) throws BluezRejectedException, BluezCanceledException {
        System.out.println("AuthorizeService called");
    }

    @Override
    public void Cancel() {
        System.out.println("cancel called");
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public String getObjectPath() {
        return "/";
    }
}
