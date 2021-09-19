package org.freedesktop.login1;

import java.util.List;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.TypeRef;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.UInt32;
import org.freedesktop.dbus.types.UInt64;
import org.freedesktop.login1.CreateSessionStruct;
import org.freedesktop.login1.ListInhibitorsStruct;
import org.freedesktop.login1.ListSeatsStruct;
import org.freedesktop.login1.ListSessionsStruct;
import org.freedesktop.login1.ListUsersStruct;
import org.freedesktop.login1.PropertyScheduledShutdownStruct;

/**
 * Auto-generated class.
 */
@DBusProperty(name = "EnableWallMessages", type = Boolean.class, access = Access.READ_WRITE)
@DBusProperty(name = "WallMessage", type = String.class, access = Access.READ_WRITE)
@DBusProperty(name = "NAutoVTs", type = UInt32.class, access = Access.READ)
@DBusProperty(name = "KillOnlyUsers", type = Manager.PropertyKillOnlyUsersType.class, access = Access.READ)
@DBusProperty(name = "KillExcludeUsers", type = Manager.PropertyKillExcludeUsersType.class, access = Access.READ)
@DBusProperty(name = "KillUserProcesses", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "RebootParameter", type = String.class, access = Access.READ)
@DBusProperty(name = "RebootToFirmwareSetup", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "RebootToBootLoaderMenu", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "RebootToBootLoaderEntry", type = String.class, access = Access.READ)
@DBusProperty(name = "BootLoaderEntries", type = Manager.PropertyBootLoaderEntriesType.class, access = Access.READ)
@DBusProperty(name = "IdleHint", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "IdleSinceHint", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "IdleSinceHintMonotonic", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "BlockInhibited", type = String.class, access = Access.READ)
@DBusProperty(name = "DelayInhibited", type = String.class, access = Access.READ)
@DBusProperty(name = "InhibitDelayMaxUSec", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "UserStopDelayUSec", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "HandlePowerKey", type = String.class, access = Access.READ)
@DBusProperty(name = "HandleSuspendKey", type = String.class, access = Access.READ)
@DBusProperty(name = "HandleHibernateKey", type = String.class, access = Access.READ)
@DBusProperty(name = "HandleLidSwitch", type = String.class, access = Access.READ)
@DBusProperty(name = "HandleLidSwitchExternalPower", type = String.class, access = Access.READ)
@DBusProperty(name = "HandleLidSwitchDocked", type = String.class, access = Access.READ)
@DBusProperty(name = "HoldoffTimeoutUSec", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "IdleAction", type = String.class, access = Access.READ)
@DBusProperty(name = "IdleActionUSec", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "PreparingForShutdown", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "PreparingForSleep", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "ScheduledShutdown", type = PropertyScheduledShutdownStruct.class, access = Access.READ)
@DBusProperty(name = "Docked", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "LidClosed", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "OnExternalPower", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "RemoveIPC", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "RuntimeDirectorySize", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "InhibitorsMax", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "NCurrentInhibitors", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "SessionsMax", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "NCurrentSessions", type = UInt64.class, access = Access.READ)
public interface Manager extends DBusInterface {


    public DBusPath GetSession(String arg0);
    public DBusPath GetSessionByPID(UInt32 arg0);
    public DBusPath GetUser(UInt32 arg0);
    public DBusPath GetUserByPID(UInt32 arg0);
    public DBusPath GetSeat(String arg0);
    public List<ListSessionsStruct> ListSessions();
    public List<ListUsersStruct> ListUsers();
    public List<ListSeatsStruct> ListSeats();
    public List<ListInhibitorsStruct> ListInhibitors();
    public String CreateSession(UInt32 arg0, UInt32 arg1, String arg2, String arg3, String arg4, String arg5, String arg6, UInt32 arg7, String arg8, String arg9, boolean arg10, String arg11, String arg12, List<CreateSessionStruct> arg13);
    public void ReleaseSession(String arg0);
    public void ActivateSession(String arg0);
    public void ActivateSessionOnSeat(String arg0, String arg1);
    public void LockSession(String arg0);
    public void UnlockSession(String arg0);
    public void LockSessions();
    public void UnlockSessions();
    public void KillSession(String arg0, String arg1, int arg2);
    public void KillUser(UInt32 arg0, int arg1);
    public void TerminateSession(String arg0);
    public void TerminateUser(UInt32 arg0);
    public void TerminateSeat(String arg0);
    public void SetUserLinger(UInt32 arg0, boolean arg1, boolean arg2);
    public void AttachDevice(String arg0, String arg1, boolean arg2);
    public void FlushDevices(boolean arg0);
    public void PowerOff(boolean arg0);
    public void Reboot(boolean arg0);
    public void Halt(boolean arg0);
    public void Suspend(boolean arg0);
    public void Hibernate(boolean arg0);
    public void HybridSleep(boolean arg0);
    public void SuspendThenHibernate(boolean arg0);
    public String CanPowerOff();
    public String CanReboot();
    public String CanHalt();
    public String CanSuspend();
    public String CanHibernate();
    public String CanHybridSleep();
    public String CanSuspendThenHibernate();
    public void ScheduleShutdown(String arg0, UInt64 arg1);
    public boolean CancelScheduledShutdown();
    public FileDescriptor Inhibit(String arg0, String arg1, String arg2, String arg3);
    public String CanRebootParameter();
    public void SetRebootParameter(String arg0);
    public String CanRebootToFirmwareSetup();
    public void SetRebootToFirmwareSetup(boolean arg0);
    public String CanRebootToBootLoaderMenu();
    public void SetRebootToBootLoaderMenu(UInt64 arg0);
    public String CanRebootToBootLoaderEntry();
    public void SetRebootToBootLoaderEntry(String arg0);
    public void SetWallMessage(String arg0, boolean arg1);


    public static interface PropertyKillOnlyUsersType extends TypeRef<List<String>> {




    }

    public static interface PropertyKillExcludeUsersType extends TypeRef<List<String>> {




    }

    public static interface PropertyBootLoaderEntriesType extends TypeRef<List<String>> {




    }

    public static class SessionNew extends DBusSignal {

        private final String arg0;
        private final DBusPath arg1;

        public SessionNew(String _path, String _interfaceName, String _arg0, DBusPath _arg1) throws DBusException {
            super(_path, _interfaceName);
            this.arg0 = _arg0;
            this.arg1 = _arg1;
        }


        public String getArg0() {
            return arg0;
        }

        public DBusPath getArg1() {
            return arg1;
        }


    }

    public static class SessionRemoved extends DBusSignal {

        private final String arg0;
        private final DBusPath arg1;

        public SessionRemoved(String _path, String _interfaceName, String _arg0, DBusPath _arg1) throws DBusException {
            super(_path, _interfaceName);
            this.arg0 = _arg0;
            this.arg1 = _arg1;
        }


        public String getArg0() {
            return arg0;
        }

        public DBusPath getArg1() {
            return arg1;
        }


    }

    public static class UserNew extends DBusSignal {

        private final UInt32 arg0;
        private final DBusPath arg1;

        public UserNew(String _path, String _interfaceName, UInt32 _arg0, DBusPath _arg1) throws DBusException {
            super(_path, _interfaceName);
            this.arg0 = _arg0;
            this.arg1 = _arg1;
        }


        public UInt32 getArg0() {
            return arg0;
        }

        public DBusPath getArg1() {
            return arg1;
        }


    }

    public static class UserRemoved extends DBusSignal {

        private final UInt32 arg0;
        private final DBusPath arg1;

        public UserRemoved(String _path, String _interfaceName, UInt32 _arg0, DBusPath _arg1) throws DBusException {
            super(_path, _interfaceName);
            this.arg0 = _arg0;
            this.arg1 = _arg1;
        }


        public UInt32 getArg0() {
            return arg0;
        }

        public DBusPath getArg1() {
            return arg1;
        }


    }

    public static class SeatNew extends DBusSignal {

        private final String arg0;
        private final DBusPath arg1;

        public SeatNew(String _path, String _interfaceName, String _arg0, DBusPath _arg1) throws DBusException {
            super(_path, _interfaceName);
            this.arg0 = _arg0;
            this.arg1 = _arg1;
        }


        public String getArg0() {
            return arg0;
        }

        public DBusPath getArg1() {
            return arg1;
        }


    }

    public static class SeatRemoved extends DBusSignal {

        private final String arg0;
        private final DBusPath arg1;

        public SeatRemoved(String _path, String _interfaceName, String _arg0, DBusPath _arg1) throws DBusException {
            super(_path, _interfaceName);
            this.arg0 = _arg0;
            this.arg1 = _arg1;
        }


        public String getArg0() {
            return arg0;
        }

        public DBusPath getArg1() {
            return arg1;
        }


    }

    public static class PrepareForShutdown extends DBusSignal {

        private final boolean arg0;

        public PrepareForShutdown(String _path, String _interfaceName, boolean _arg0) throws DBusException {
            super(_path, _interfaceName);
            this.arg0 = _arg0;
        }


        public boolean getArg0() {
            return arg0;
        }


    }

    public static class PrepareForSleep extends DBusSignal {

        private final boolean arg0;

        public PrepareForSleep(String _path, String _interfaceName, boolean _arg0) throws DBusException {
            super(_path, _interfaceName);
            this.arg0 = _arg0;
        }


        public boolean getArg0() {
            return arg0;
        }


    }
}