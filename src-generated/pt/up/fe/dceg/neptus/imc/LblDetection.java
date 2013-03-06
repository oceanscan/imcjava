/*
 * Below is the copyright agreement for IMCJava.
 * 
 * Copyright (c) 2010-2013, Laboratório de Sistemas e Tecnologia Subaquática
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
 *  
 * $Id:: LblDetection.java 392 2013-02-28 17:26:14Z zepinto@gmail.com          $:
 */

// Source generated by IMCJava from IMC version 5.1.0
package pt.up.fe.dceg.neptus.imc;

/**
 *  IMC Message LBL Detection (201)<br/>
 *  Report of an LBL detection.<br/>
 */

public class LblDetection extends IMCMessage {

	public static final int ID_STATIC = 201;

	public LblDetection() {
		super(ID_STATIC);
	}

	public LblDetection(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public LblDetection(short tx, short channel, int timer) {
		super(ID_STATIC);
		setTx(tx);
		setChannel(channel);
		setTimer(timer);
	}

	/**
	 *  @return Transmission - uint8_t
	 */
	public short getTx() {
		return (short) getInteger("tx");
	}

	/**
	 *  @return Channel - uint8_t
	 */
	public short getChannel() {
		return (short) getInteger("channel");
	}

	/**
	 *  @return Timer - uint16_t
	 */
	public int getTimer() {
		return getInteger("timer");
	}

	/**
	 *  @param tx Transmission
	 */
	public void setTx(short tx) {
		values.put("tx", tx);
	}

	/**
	 *  @param channel Channel
	 */
	public void setChannel(short channel) {
		values.put("channel", channel);
	}

	/**
	 *  @param timer Timer
	 */
	public void setTimer(int timer) {
		values.put("timer", timer);
	}

}
