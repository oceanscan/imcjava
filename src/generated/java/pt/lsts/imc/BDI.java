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
 *  IMC Message Battery Detailed Info (2035)<br/>
 *  Used to debug the state of a BMS<br/>
 */

public class BDI extends IMCMessage {

	public enum IS_FD {
		NOT_FULLY_DISCHARGED(0),
		FULLY_DISCHARGED(1),
		UNKNOWN(2);

		protected long value;

		public long value() {
			return value;
		}

		IS_FD(long value) {
			this.value = value;
		}
	}

	public enum IS_FC {
		NOT_FULLY_CHARGED(0),
		FULLY_CHARGED(1),
		UNKNOWN(2);

		protected long value;

		public long value() {
			return value;
		}

		IS_FC(long value) {
			this.value = value;
		}
	}

	public enum REST {
		REST_NOT_SET(0),
		REST_SET(1),
		UNKNOWN(2);

		protected long value;

		public long value() {
			return value;
		}

		REST(long value) {
			this.value = value;
		}
	}

	public enum VDQ {
		VDQ_NOT_SET(0),
		VDQ_SET(1),
		UNKNOWN(2);

		protected long value;

		public long value() {
			return value;
		}

		VDQ(long value) {
			this.value = value;
		}
	}

	public static final int ID_STATIC = 2035;

	public BDI() {
		super(ID_STATIC);
	}

	public BDI(IMCMessage msg) {
		super(ID_STATIC);
		try{
			copyFrom(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BDI(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public BDI(IMCDefinition defs, int type) {
		super(defs, type);
	}

	public static BDI create(Object... values) {
		BDI m = new BDI();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static BDI clone(IMCMessage msg) throws Exception {

		BDI m = new BDI();
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

	public BDI(short soh, IS_FD is_fd, IS_FC is_fc, REST rest, VDQ vdq, int cycle_count, int full_capacity, int rem_capacity) {
		super(ID_STATIC);
		setSoh(soh);
		setIsFd(is_fd);
		setIsFc(is_fc);
		setRest(rest);
		setVdq(vdq);
		setCycleCount(cycle_count);
		setFullCapacity(full_capacity);
		setRemCapacity(rem_capacity);
	}

	/**
	 *  @return State of Health (%) - int16_t
	 */
	public short getSoh() {
		return (short) getInteger("soh");
	}

	/**
	 *  @param soh State of Health (%)
	 */
	public BDI setSoh(short soh) {
		values.put("soh", soh);
		return this;
	}

	/**
	 *  @return Is Fully Discharged (enumerated) - uint8_t
	 */
	public IS_FD getIsFd() {
		try {
			IS_FD o = IS_FD.valueOf(getMessageType().getFieldPossibleValues("is_fd").get(getLong("is_fd")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getIsFdStr() {
		return getString("is_fd");
	}

	public short getIsFdVal() {
		return (short) getInteger("is_fd");
	}

	/**
	 *  @param is_fd Is Fully Discharged (enumerated)
	 */
	public BDI setIsFd(IS_FD is_fd) {
		values.put("is_fd", is_fd.value());
		return this;
	}

	/**
	 *  @param is_fd Is Fully Discharged (as a String)
	 */
	public BDI setIsFdStr(String is_fd) {
		setValue("is_fd", is_fd);
		return this;
	}

	/**
	 *  @param is_fd Is Fully Discharged (integer value)
	 */
	public BDI setIsFdVal(short is_fd) {
		setValue("is_fd", is_fd);
		return this;
	}

	/**
	 *  @return Is Fully Charged (enumerated) - uint8_t
	 */
	public IS_FC getIsFc() {
		try {
			IS_FC o = IS_FC.valueOf(getMessageType().getFieldPossibleValues("is_fc").get(getLong("is_fc")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getIsFcStr() {
		return getString("is_fc");
	}

	public short getIsFcVal() {
		return (short) getInteger("is_fc");
	}

	/**
	 *  @param is_fc Is Fully Charged (enumerated)
	 */
	public BDI setIsFc(IS_FC is_fc) {
		values.put("is_fc", is_fc.value());
		return this;
	}

	/**
	 *  @param is_fc Is Fully Charged (as a String)
	 */
	public BDI setIsFcStr(String is_fc) {
		setValue("is_fc", is_fc);
		return this;
	}

	/**
	 *  @param is_fc Is Fully Charged (integer value)
	 */
	public BDI setIsFcVal(short is_fc) {
		setValue("is_fc", is_fc);
		return this;
	}

	/**
	 *  @return REST (enumerated) - uint8_t
	 */
	public REST getRest() {
		try {
			REST o = REST.valueOf(getMessageType().getFieldPossibleValues("rest").get(getLong("rest")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getRestStr() {
		return getString("rest");
	}

	public short getRestVal() {
		return (short) getInteger("rest");
	}

	/**
	 *  @param rest REST (enumerated)
	 */
	public BDI setRest(REST rest) {
		values.put("rest", rest.value());
		return this;
	}

	/**
	 *  @param rest REST (as a String)
	 */
	public BDI setRestStr(String rest) {
		setValue("rest", rest);
		return this;
	}

	/**
	 *  @param rest REST (integer value)
	 */
	public BDI setRestVal(short rest) {
		setValue("rest", rest);
		return this;
	}

	/**
	 *  @return VDQ (enumerated) - uint8_t
	 */
	public VDQ getVdq() {
		try {
			VDQ o = VDQ.valueOf(getMessageType().getFieldPossibleValues("vdq").get(getLong("vdq")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getVdqStr() {
		return getString("vdq");
	}

	public short getVdqVal() {
		return (short) getInteger("vdq");
	}

	/**
	 *  @param vdq VDQ (enumerated)
	 */
	public BDI setVdq(VDQ vdq) {
		values.put("vdq", vdq.value());
		return this;
	}

	/**
	 *  @param vdq VDQ (as a String)
	 */
	public BDI setVdqStr(String vdq) {
		setValue("vdq", vdq);
		return this;
	}

	/**
	 *  @param vdq VDQ (integer value)
	 */
	public BDI setVdqVal(short vdq) {
		setValue("vdq", vdq);
		return this;
	}

	/**
	 *  @return Cycle Count - int32_t
	 */
	public int getCycleCount() {
		return getInteger("cycle_count");
	}

	/**
	 *  @param cycle_count Cycle Count
	 */
	public BDI setCycleCount(int cycle_count) {
		values.put("cycle_count", cycle_count);
		return this;
	}

	/**
	 *  @return Full Charge Capacity (mah) - int32_t
	 */
	public int getFullCapacity() {
		return getInteger("full_capacity");
	}

	/**
	 *  @param full_capacity Full Charge Capacity (mah)
	 */
	public BDI setFullCapacity(int full_capacity) {
		values.put("full_capacity", full_capacity);
		return this;
	}

	/**
	 *  @return Remaining Capacity (mah) - int32_t
	 */
	public int getRemCapacity() {
		return getInteger("rem_capacity");
	}

	/**
	 *  @param rem_capacity Remaining Capacity (mah)
	 */
	public BDI setRemCapacity(int rem_capacity) {
		values.put("rem_capacity", rem_capacity);
		return this;
	}

}
