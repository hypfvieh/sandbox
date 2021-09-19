package com.github.hypfvieh.sandbox.dbus.daemon.twopart;

import java.io.File;
import java.io.IOException;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import com.github.hypfvieh.sandbox.dbus.daemon.twopart.RunTwoPartDaemon.IExport;
import com.github.hypfvieh.util.FileIoUtil;
import com.github.hypfvieh.util.StringUtil;
import com.github.hypfvieh.util.SystemUtil;

public class RunTwoPartClient {
	public static void main(String[] args) {
		String twopartAddress = FileIoUtil.readFileToString(new File(SystemUtil.getTempDir(), "twopartdaemon.address").getAbsolutePath());
		if (StringUtil.isBlank(twopartAddress)) {
			throw new RuntimeException("No twopart daemon found");
		}
		
		try (DBusConnection conn = DBusConnection.getConnection(twopartAddress)) {
			// repeat until JVM is killed
			while (true) {
				IExport remoteObject = conn.getRemoteObject(RunTwoPartDaemon.EXPORT_NAME, "/", IExport.class);
				System.out.println("Remote side says: " + remoteObject.sayHello());

				try {
					Thread.sleep(500L);
				} catch (InterruptedException ex) {
				}
			}

		} catch (IOException | DBusException e) {
			throw new RuntimeException("Could not connect to twopart daemon");
		}
	}
	
}
