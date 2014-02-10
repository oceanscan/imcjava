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
 *                                                                             $:
 */

// Source generated by IMCJava from IMC version 5.4.x
package pt.lsts.imc;

/**
 *  IMC Message Maneuver Control State (470)<br/>
 *  Maneuver control state.<br/>
 */

public class ManeuverControlState extends IMCMessage {

	public static final int ID_STATIC = 470;

	public enum STATE {
		EXECUTING(0),
		DONE(1),
		ERROR(2);

		protected long value;

		public long value() {
			return value;
		}

		STATE(long value) {
			this.value = value;
		}
	}

	public ManeuverControlState() {
		super(ID_STATIC);
	}

	public ManeuverControlState(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public static ManeuverControlState create(Object... values) {
		ManeuverControlState m = new ManeuverControlState();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static ManeuverControlState clone(IMCMessage msg) throws Exception {

		ManeuverControlState m = new ManeuverControlState();
		if (msg == null)
			return m;
		if(msg.definitions != m.definitions){
			msg = msg.cloneMessage();
			IMCUtil.updateMessage(msg, m.definitions);
		}
		else if (msg.getMgid()!=m.getMgid())
			throw new Exception("Argument "+msg.getAbbrev()+" is incompatible with message "+m.getAbbrev());

		m.getHeader().values.putAll(msg.getHeader().values);
		m.values.putAll(msg.values);
		return m;
	}

	public ManeuverControlState(STATE state, int eta, String info) {
		super(ID_STATIC);
		setState(state);
		setEta(eta);
		if (info != null)
			setInfo(info);
	}

	/**
	 *  Code indicating maneuver state.<br/>
	 *  @return State (enumerated) - uint8_t
	 */
	public STATE getState() {
		try {
			STATE o = STATE.valueOf(getMessageType().getFieldPossibleValues("state").get(getLong("state")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 *  @return Completion Time (s) - uint16_t
	 */
	public int getEta() {
		return getInteger("eta");
	}

	/**
	 *  @return Info - plaintext
	 */
	public String getInfo() {
		return getString("info");
	}

	/**
	 *  @param state State (enumerated)
	 */
	public void setState(STATE state) {
		values.put("state", state.value());
	}

	/**
	 *  @param state State (as a String)
	 */
	public void setState(String state) {
		setValue("state", state);
	}

	/**
	 *  @param state State (integer value)
	 */
	public void setState(short state) {
		setValue("state", state);
	}

	/**
	 *  @param eta Completion Time (s)
	 */
	public void setEta(int eta) {
		values.put("eta", eta);
	}

	/**
	 *  @param info Info
	 */
	public void setInfo(String info) {
		values.put("info", info);
	}

}