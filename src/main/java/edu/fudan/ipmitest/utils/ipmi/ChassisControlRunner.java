/*
 * VxipmiRunner.java 
 * Created on 2011-09-20
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package edu.fudan.ipmitest.utils.ipmi;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.chassis.ChassisControl;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatus;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatusResponseData;
import com.veraxsystems.vxipmi.coding.commands.chassis.PowerCommand;
import com.veraxsystems.vxipmi.coding.commands.session.SetSessionPrivilegeLevel;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;
import com.veraxsystems.vxipmi.connection.Connection;
import com.veraxsystems.vxipmi.sm.states.Ciphers;

import java.net.InetAddress;

public class ChassisControlRunner {
    private static IpmiConnector connector;
    private static ConnectionHandle handle;
    private static CipherSuite cs;

    private static void setConnection(String hostname, String username, String password, int port) throws Exception {
        // Create the connector, specify port that will be used to communicate
        // with the remote host. The UDP layer starts listening at this port, so
        // no 2 connectors can work at the same time on the same port.
        connector = new IpmiConnector(port);
        System.out.println("Connector created");

        // Create the connection and get the handle, specify IP address of the
        // remote host. The connection is being registered in ConnectionManager,
        // the handle will be needed to identify it among other connections
        // (target IP address isn't enough, since we can handle multiple
        // connections to the same host)
        handle = connector.createConnection(InetAddress.getByName(hostname));
        System.out.println("Connection created");
//
//        // Get available cipher suites list via getAvailableCipherSuites and
//        // pick one of them that will be used further in the session.
//        cs = connector.getAvailableCipherSuites(handle).get(3);
//        System.out.println("Cipher suite picked");
//
//        // Provide chosen cipher suite and privilege level to the remote host.
//        // From now on, your connection handle will contain these information.
//        connector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.Administrator);
//        System.out.println("Channel authentication capabilities receivied");

        // new action to getCipherSuite
        cs = new CipherSuite((byte) 0, (byte) 1, (byte) 1,(byte) 1);
        Connection connection = connector.getAsyncConnector().getConnectionManager().getConnection(handle.getHandle());
        connection.getStateMachine().setCurrent(new Ciphers());
        connector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.User);
        System.out.println("Channel authentication capabilities receivied");

        // Start the session, provide username and password, and optionally the
        // BMC key (only if the remote host has two-key authentication enabled,
        // otherwise this parameter should be null)
        connector.openSession(handle, username, password, null);
        System.out.println("Session open");
    }

    public static boolean getChassisPowerState(String hostname, String username, String password, int port) throws Exception {
        GetChassisStatusResponseData rd = null;
        try {
            setConnection(hostname, username, password, port);

            // Send some message and read the response
            rd = (GetChassisStatusResponseData) connector.sendMessage(handle,
                    new GetChassisStatus(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus));

            System.out.println("Received answer");
            System.out.println("System power state is " + (rd.isPowerOn() ? "up" : "down"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the session
            connector.closeSession(handle);
            System.out.println("Session closed");

            // Close connection manager and release the listener port.
            connector.tearDown();
            System.out.println("Connection manager closed");
        }

        return rd.isPowerOn();
    }

    public static boolean powerOn(String hostname, String username, String password, int port) throws Exception {
        GetChassisStatusResponseData rd = null;
        try {
            setConnection(hostname, username, password, port);

            // Set session privilege level to administrator, as ChassisControl command requires this level
            connector.sendMessage(handle, new SetSessionPrivilegeLevel(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
                    PrivilegeLevel.Administrator));

            ChassisControl chassisControl = null;

            // Send some message and read the response
            rd = (GetChassisStatusResponseData) connector.sendMessage(handle,
                    new GetChassisStatus(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus));

            //Power on
            if (!rd.isPowerOn()) {
                chassisControl = new ChassisControl(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus, PowerCommand.PowerUp);
                connector.sendMessage(handle, chassisControl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the session
            connector.closeSession(handle);
            System.out.println("Session closed");

            // Close connection manager and release the listener port.
            connector.tearDown();
            System.out.println("Connection manager closed");
        }
        return rd.isPowerOn();
    }

    public static boolean powerOff(String hostname, String username, String password, int port) throws Exception {
        GetChassisStatusResponseData rd = null;
        try {
            setConnection(hostname, username, password, port);

            // Set session privilege level to administrator, as ChassisControl command requires this level
            connector.sendMessage(handle, new SetSessionPrivilegeLevel(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
                    PrivilegeLevel.Administrator));

            ChassisControl chassisControl = null;

            // Send some message and read the response
            rd = (GetChassisStatusResponseData) connector.sendMessage(handle,
                    new GetChassisStatus(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus));

            //Power off
            if (rd.isPowerOn()) {
                chassisControl = new ChassisControl(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
                        PowerCommand.PowerDown);
                connector.sendMessage(handle, chassisControl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the session
            connector.closeSession(handle);
            System.out.println("Session closed");

            // Close connection manager and release the listener port.
            connector.tearDown();
            System.out.println("Connection manager closed");
        }
        return rd.isPowerOn();
    }
}
