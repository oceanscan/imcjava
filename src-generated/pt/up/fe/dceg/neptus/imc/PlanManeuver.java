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
 * $Id:: PlanManeuver.java 392 2013-02-28 17:26:14Z zepinto@gmail.com          $:
 */

// Source generated by IMCJava from IMC version 5.1.0
package pt.up.fe.dceg.neptus.imc;

/**
 *  IMC Message Plan Maneuver (552)<br/>
 *  Named plan maneuver.<br/>
 */

public class PlanManeuver extends IMCMessage {

	public static final int ID_STATIC = 552;

	public PlanManeuver() {
		super(ID_STATIC);
	}

	public PlanManeuver(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public PlanManeuver(String maneuver_id, Maneuver data, java.util.Collection<IMCMessage> start_actions, java.util.Collection<IMCMessage> end_actions) {
		super(ID_STATIC);
		if (maneuver_id != null)
			setManeuverId(maneuver_id);
		if (data != null)
			setData(data);
		if (start_actions != null)
			setStartActions(start_actions);
		if (end_actions != null)
			setEndActions(end_actions);
	}

	/**
	 *  @return Maneuver ID - plaintext
	 */
	public String getManeuverId() {
		return getString("maneuver_id");
	}

	/**
	 *  @return Maneuver Specification - message
	 */
	public Maneuver getData() {
		try {
			IMCMessage obj = getMessage("data");
			if (obj instanceof Maneuver)
				return (Maneuver) obj;
			else
				return null;
		}
		catch (Exception e) {
			return null;
		}

	}

	/**
	 *  @return Start Actions - message-list
	 */
	public java.util.Vector<IMCMessage> getStartActions() {
		return getMessageList("start_actions");
	}

	/**
	 *  @return End Actions - message-list
	 */
	public java.util.Vector<IMCMessage> getEndActions() {
		return getMessageList("end_actions");
	}

	/**
	 *  @param maneuver_id Maneuver ID
	 */
	public void setManeuverId(String maneuver_id) {
		values.put("maneuver_id", maneuver_id);
	}

	/**
	 *  @param data Maneuver Specification
	 */
	public void setData(Maneuver data) {
		values.put("data", data);
	}

	/**
	 *  @param start_actions Start Actions
	 */
	public void setStartActions(java.util.Collection<IMCMessage> start_actions) {
		values.put("start_actions", start_actions);
	}

	/**
	 *  @param end_actions End Actions
	 */
	public void setEndActions(java.util.Collection<IMCMessage> end_actions) {
		values.put("end_actions", end_actions);
	}

}
