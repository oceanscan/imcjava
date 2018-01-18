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

/**
 * @author pdias
 */
public interface MessageInfo {
    String PUBLISHER_KEY = "Publisher";
    String PUBLISHER_INET_ADDRESS_KEY = "PublisherInetAddress";
    String PUBLISHER_PORT_KEY = "PublisherPort";
    String SUBSCRIBER_KEY = "Subscriber";
    String NOT_TO_LOG_MSG_KEY = "NotToLogMessage";
    String ENVELOPED_MSG_KEY = "Enveloped";
    String WEB_FETCH_MSG_KEY = "Web Fetch";
    String TRANSPORT_MSG_KEY = "Transport";

    double getTimeSentSec();

    void setTimeSentSec(double timeSent);

    long getTimeSentNanos();

    void setTimeSentNanos(long timeSent);

    double getTimeReceivedSec();

    void setTimeReceivedSec(double timeReceived);

    long getTimeReceivedNanos();

    void setTimeReceivedNanos(long timeReceived);

    String getProperty(String name);

    void setProperty(String name, String value);

    /**
     * @return the publisher of the message. Should be the same
     * as {@link #getProperty(String)} with name {@value #PUBLISHER_KEY}
     */
    String getPublisher();

    /**
     * Sets the publisher of the message. Should be the same
     * as {@link #setProperty(String, String))} with name {@value #PUBLISHER_KEY}
     */
    void setPublisher(String value);

    String getPublisherInetAddress();

    void setPublisherInetAddress(String value);

    int getPublisherPort();

    void setPublisherPort(int value);
}
