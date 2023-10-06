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
 *  IMC Message HealthCheck (2015)<br/>
 *  Used to request/reply a health check of a system<br/>
 */

public class HealthCheck extends IMCMessage {

	public enum OP {
		REQUEST(0),
		REPLY(1),
		QUERY(2);

		protected long value;

		public long value() {
			return value;
		}

		OP(long value) {
			this.value = value;
		}
	}

	public enum STATUS {
		FINISHED_OK(0),
		FINISHED_ERROR(1),
		IN_PROGRESS(2),
		IMP(3),
		NOT_IMP(4),
		ABORT(5),
		CONN_ERROR(6),
		SEND_ERROR(7),
		READ_ERROR(8),
		DEV_ERROR(9),
		DISC_ERROR(10);

		protected long value;

		public long value() {
			return value;
		}

		STATUS(long value) {
			this.value = value;
		}
	}

	public static final int ID_STATIC = 2015;

	public HealthCheck() {
		super(ID_STATIC);
	}

	public HealthCheck(IMCMessage msg) {
		super(ID_STATIC);
		try{
			copyFrom(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HealthCheck(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public HealthCheck(IMCDefinition defs, int type) {
		super(defs, type);
	}

	public static HealthCheck create(Object... values) {
		HealthCheck m = new HealthCheck();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static HealthCheck clone(IMCMessage msg) throws Exception {

		HealthCheck m = new HealthCheck();
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

	public HealthCheck(OP op, long request_id, String entity_name, STATUS status, String text) {
		super(ID_STATIC);
		setOp(op);
		setRequestId(request_id);
		if (entity_name != null)
			setEntityName(entity_name);
		setStatus(status);
		if (text != null)
			setText(text);
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
	public HealthCheck setOp(OP op) {
		values.put("op", op.value());
		return this;
	}

	/**
	 *  @param op Operation (as a String)
	 */
	public HealthCheck setOpStr(String op) {
		setValue("op", op);
		return this;
	}

	/**
	 *  @param op Operation (integer value)
	 */
	public HealthCheck setOpVal(short op) {
		setValue("op", op);
		return this;
	}

	/**
	 *  @return Request identitier - uint32_t
	 */
	public long getRequestId() {
		return getLong("request_id");
	}

	/**
	 *  @param request_id Request identitier
	 */
	public HealthCheck setRequestId(long request_id) {
		values.put("request_id", request_id);
		return this;
	}

	/**
	 *  @return Entity Name - plaintext
	 */
	public String getEntityName() {
		return getString("entity_name");
	}

	/**
	 *  @param entity_name Entity Name
	 */
	public HealthCheck setEntityName(String entity_name) {
		values.put("entity_name", entity_name);
		return this;
	}

	/**
	 *  @return Status (enumerated) - uint16_t
	 */
	public STATUS getStatus() {
		try {
			STATUS o = STATUS.valueOf(getMessageType().getFieldPossibleValues("status").get(getLong("status")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getStatusStr() {
		return getString("status");
	}

	public int getStatusVal() {
		return getInteger("status");
	}

	/**
	 *  @param status Status (enumerated)
	 */
	public HealthCheck setStatus(STATUS status) {
		values.put("status", status.value());
		return this;
	}

	/**
	 *  @param status Status (as a String)
	 */
	public HealthCheck setStatusStr(String status) {
		setValue("status", status);
		return this;
	}

	/**
	 *  @param status Status (integer value)
	 */
	public HealthCheck setStatusVal(int status) {
		setValue("status", status);
		return this;
	}

	/**
	 *  @return Text - plaintext
	 */
	public String getText() {
		return getString("text");
	}

	/**
	 *  @param text Text
	 */
	public HealthCheck setText(String text) {
		values.put("text", text);
		return this;
	}

}
