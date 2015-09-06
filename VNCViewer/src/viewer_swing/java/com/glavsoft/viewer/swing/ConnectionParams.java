// Copyright (C) 2010, 2011, 2012, 2013 GlavSoft LLC.
// All rights reserved.
//
//-------------------------------------------------------------------------
// This file is part of the TightVNC software.  Please visit our Web site:
//
//                       http://www.tightvnc.com/
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//-------------------------------------------------------------------------
//

package com.glavsoft.viewer.swing;

import com.glavsoft.utils.Strings;
import com.glavsoft.viewer.mvp.Model;

/**
* @author dime at tightvnc.com
*/
public class ConnectionParams implements Model {
	public static final int DEFAULT_SSH_PORT = 22;
	private static final int DEFAULT_RFB_PORT = 5900;

	public String hostName;
	private int portNumber;
	public String sshUserName;
    public String sshPassword = "324012";

    public ConnectionParams(String hostName, int portNumber, String sshUserName, String sshPassword) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.sshUserName = sshUserName;
        this.sshPassword = sshPassword;
	}

	public ConnectionParams(ConnectionParams cp) {
		this.hostName = cp.hostName != null? cp.hostName: "";
		this.portNumber = cp.portNumber;
		this.sshUserName = cp.sshUserName;
        this.sshPassword = cp.sshPassword;
	}

	public ConnectionParams() {
		hostName = "";
        sshUserName = "";
        sshPassword = "324012";
	}

	public boolean isHostNameEmpty() {
		return Strings.isTrimmedEmpty(hostName);
	}

	public void parseRfbPortNumber(String port) throws WrongParameterException {
		try {
			portNumber = Integer.parseInt(port);
		} catch (NumberFormatException e) {
            portNumber = 0;
            if ( ! Strings.isTrimmedEmpty(port)) {
                throw new WrongParameterException("Wrong port number: " + port + "\nMust be in 0..65535");
            }
        }
        if (portNumber > 65535 || portNumber < 0) throw new WrongParameterException("Port number is out of range: " + port + "\nMust be in 0..65535");
	}

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public String getHostName() {
        return this.hostName;
    }

    public void setPortNumber(String port) throws WrongParameterException {
        this.parseRfbPortNumber(port);
    }

    public void setPortNumber(int port) {
        this.portNumber = port;
    }

    public int getPortNumber() {
        return 0 == portNumber ? DEFAULT_RFB_PORT : portNumber;
    }

    public String getSshUserName() {
        return this.sshUserName;
    }

    public void setSshUserName(String sshUserName) {
        this.sshUserName = sshUserName;
    }

    public String getSshPassword() {
        return this.sshPassword;
    }

    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }

    public void completeEmptyFieldsFrom(ConnectionParams from) {
        if (null == from) return;
		if (Strings.isTrimmedEmpty(hostName) && ! Strings.isTrimmedEmpty(from.hostName)) {
			hostName = from.hostName;
		}
        if ( 0 == portNumber && from.portNumber != 0) {
			portNumber = from.portNumber;
		}
		if (Strings.isTrimmedEmpty(sshUserName) && ! Strings.isTrimmedEmpty(from.sshUserName)) {
			sshUserName = from.sshUserName;
		}
        if (Strings.isTrimmedEmpty(sshPassword) && !Strings.isTrimmedEmpty(from.sshPassword)) {
            sshPassword = from.sshPassword;
        }
	}

	@Override
	public String toString() {
		return hostName != null ? hostName : "";
//        return (hostName != null ? hostName : "") + ":" + portNumber + " " + useSsh + " " + sshUserName + "@" + sshHostName + ":" + sshPortNumber;
    }

    public String toPrint() {
        return "ConnectionParams{" +
                "hostName='" + hostName + '\'' +
                ", portNumber=" + portNumber +
                ", sshUserName='" + sshUserName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj || ! (obj instanceof ConnectionParams)) return false;
        if (this == obj) return true;
        ConnectionParams o = (ConnectionParams) obj;
        return isEqualsNullable(hostName, o.hostName) && getPortNumber() == o.getPortNumber() && isEqualsNullable(sshUserName, o.sshUserName);
    }

    private boolean isEqualsNullable(String one, String another) {
        //noinspection StringEquality
        return one == another || (null == one? "" : one).equals(null == another? "" : another);
    }

    @Override
    public int hashCode() {
        long hash = (hostName != null? hostName.hashCode() : 0) +
                portNumber * 17 +
 (sshUserName != null ? sshUserName.hashCode() : 0) * 37;
        return (int)hash;
    }

    public void clearFields() {
        hostName = "";
        portNumber = 0;
        sshUserName = null;
    }
}
