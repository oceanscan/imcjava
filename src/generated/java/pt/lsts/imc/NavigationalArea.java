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
 * 
 */
package pt.lsts.imc;

/**
 *  IMC Message Navigational Area (2037)<br/>
 */

public class NavigationalArea extends IMCMessage {

	public enum OP {
		SET(0),
		GET(1),
		CLEAR(2);

		protected long value;

		public long value() {
			return value;
		}

		OP(long value) {
			this.value = value;
		}
	}

	public static final int ID_STATIC = 2037;

	public NavigationalArea() {
		super(ID_STATIC);
	}

	public NavigationalArea(IMCMessage msg) {
		super(ID_STATIC);
		try{
			copyFrom(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public NavigationalArea(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public NavigationalArea(IMCDefinition defs, int type) {
		super(defs, type);
	}

	public static NavigationalArea create(Object... values) {
		NavigationalArea m = new NavigationalArea();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static NavigationalArea clone(IMCMessage msg) throws Exception {

		NavigationalArea m = new NavigationalArea();
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

	public NavigationalArea(OP op, java.util.Collection<Zone> admissible_zones, java.util.Collection<Zone> forbidden_zones) {
		super(ID_STATIC);
		setOp(op);
		if (admissible_zones != null)
			setAdmissibleZones(admissible_zones);
		if (forbidden_zones != null)
			setForbiddenZones(forbidden_zones);
	}

	/**
	 *  @return Operation (enumerated) - uint8_t
	 */
	public OP getOp() {
		try {
			OP o = OP.valueOf(getMessageType().getFieldPossibleValues("op").get(getLong("op")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getOpStr() {
		return getString("op");
	}

	public short getOpVal() {
		return (short) getInteger("op");
	}

	/**
	 *  @param op Operation (enumerated)
	 */
	public NavigationalArea setOp(OP op) {
		values.put("op", op.value());
		return this;
	}

	/**
	 *  @param op Operation (as a String)
	 */
	public NavigationalArea setOpStr(String op) {
		setValue("op", op);
		return this;
	}

	/**
	 *  @param op Operation (integer value)
	 */
	public NavigationalArea setOpVal(short op) {
		setValue("op", op);
		return this;
	}

	/**
	 *  @return Admissible Zones - message-list
	 */
	public java.util.Vector<Zone> getAdmissibleZones() {
		try {
			return getMessageList("admissible_zones", Zone.class);
		}
		catch (Exception e) {
			return null;
		}

	}

	/**
	 *  @param admissible_zones Admissible Zones
	 */
	public NavigationalArea setAdmissibleZones(java.util.Collection<Zone> admissible_zones) {
		values.put("admissible_zones", admissible_zones);
		return this;
	}

	/**
	 *  @return Forbidden Zones - message-list
	 */
	public java.util.Vector<Zone> getForbiddenZones() {
		try {
			return getMessageList("forbidden_zones", Zone.class);
		}
		catch (Exception e) {
			return null;
		}

	}

	/**
	 *  @param forbidden_zones Forbidden Zones
	 */
	public NavigationalArea setForbiddenZones(java.util.Collection<Zone> forbidden_zones) {
		values.put("forbidden_zones", forbidden_zones);
		return this;
	}

}
