/*
 * Below is the copyright agreement for IMCJava.
 *
 * Copyright (c) 2010-2016, Laboratório de Sistemas e Tecnologia Subaquática
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     - Neither the names of IMC, LSTS, IMCJava nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL LABORATORIO DE SISTEMAS E TECNOLOGIA SUBAQUATICA
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package pt.lsts.neptus.messages.listener;

import java.io.PrintStream;
import java.util.Hashtable;

/**
 * @author pdias
 */
public class MessageInfoImpl implements MessageInfo {
    private double timeReceivedSec = -1;
    private double timeSentSec = -1;

    private Hashtable<String, Object> properties = new Hashtable<>();

    @Override
    public double getTimeReceivedSec() {
        return timeReceivedSec;
    }

    @Override
    public void setTimeReceivedSec(double timeReceived) {
        timeReceivedSec = timeReceived;
    }

    @Override
    public long getTimeReceivedNanos() {
        return (long) (this.timeReceivedSec * 1E9);
    }

    @Override
    public void setTimeReceivedNanos(long timeReceived) {
        this.timeReceivedSec = timeReceived / 1E9;
    }

    @Override
    public double getTimeSentSec() {
        return timeSentSec;
    }

    @Override
    public void setTimeSentSec(double timeSent) {
        timeSentSec = timeSent;
    }

    @Override
    public long getTimeSentNanos() {
        return (long) (this.timeSentSec * 1E9);
    }

    @Override
    public void setTimeSentNanos(long timeSent) {
        this.timeSentSec = timeSent / 1E9;
    }

    @Override
    public String getProperty(String name) {
        Object prop = this.properties.get(name);
        return (prop == null) ? null : prop.toString();
    }

    @Override
    public void setProperty(String name, String value) {
        this.properties.put(name, value);
    }

    public void setProperty(String name, Object value) {
        this.properties.put(name, value);
    }

    @Override
    public String getPublisher() {
        return getProperty(PUBLISHER_KEY);
    }

    @Override
    public String getPublisherInetAddress() {
        return getProperty(PUBLISHER_INET_ADDRESS_KEY);
    }

    @Override
    public int getPublisherPort() {
        try {
            return Integer.parseInt(getProperty(PUBLISHER_PORT_KEY));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void setPublisher(String value) {
        setProperty(PUBLISHER_KEY, value);
    }

    @Override
    public void setPublisherInetAddress(String value) {
        setProperty(PUBLISHER_INET_ADDRESS_KEY, value);
    }

    @Override
    public void setPublisherPort(int value) {
        setProperty(PUBLISHER_PORT_KEY, value);
    }

    @Override
    public void dump(PrintStream out) {
        out.println("__MessageInfo Properties_______");
        for (String key : properties.keySet()) {
            out.println("  " + key + " :: " + getProperty(key));
        }
        out.println("_______________________________");
    }
}
