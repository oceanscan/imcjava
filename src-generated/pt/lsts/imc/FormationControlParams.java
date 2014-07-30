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

// Source generated by IMCJava from IMC version 5.5.x
package pt.lsts.imc;

/**
 *  IMC Message Formation Control Parameters (820)<br/>
 *  Formation controller paramenters, as: trajectory gains, control boundary layer thickness, and formation shape gains.<br/>
 */

public class FormationControlParams extends IMCMessage {

	public static final int ID_STATIC = 820;

	public enum ACTION {
		REQ(0),
		SET(1),
		REP(2);

		protected long value;

		public long value() {
			return value;
		}

		ACTION(long value) {
			this.value = value;
		}
	}

	public FormationControlParams() {
		super(ID_STATIC);
	}

	public FormationControlParams(IMCMessage msg) {
		super(ID_STATIC);
		try{
			copyFrom(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FormationControlParams(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public static FormationControlParams create(Object... values) {
		FormationControlParams m = new FormationControlParams();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static FormationControlParams clone(IMCMessage msg) throws Exception {

		FormationControlParams m = new FormationControlParams();
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

	public FormationControlParams(ACTION Action, float lon_gain, float lat_gain, float bond_thick, float lead_gain, float deconfl_gain, float accel_switch_gain, float safe_dist, float deconflict_offset, float accel_safe_margin, float accel_lim_x) {
		super(ID_STATIC);
		setAction(Action);
		setLonGain(lon_gain);
		setLatGain(lat_gain);
		setBondThick(bond_thick);
		setLeadGain(lead_gain);
		setDeconflGain(deconfl_gain);
		setAccelSwitchGain(accel_switch_gain);
		setSafeDist(safe_dist);
		setDeconflictOffset(deconflict_offset);
		setAccelSafeMargin(accel_safe_margin);
		setAccelLimX(accel_lim_x);
	}

	/**
	 *  Action on the vehicle formation control parameters.<br/>
	 *  @return Action (enumerated) - uint8_t
	 */
	public ACTION getAction() {
		try {
			ACTION o = ACTION.valueOf(getMessageType().getFieldPossibleValues("Action").get(getLong("Action")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 *  @return Longitudinal Gain - fp32_t
	 */
	public double getLonGain() {
		return getDouble("lon_gain");
	}

	/**
	 *  @return Lateral Gain - fp32_t
	 */
	public double getLatGain() {
		return getDouble("lat_gain");
	}

	/**
	 *  @return Boundary Layer Thickness - fp32_t
	 */
	public double getBondThick() {
		return getDouble("bond_thick");
	}

	/**
	 *  @return Leader Gain - fp32_t
	 */
	public double getLeadGain() {
		return getDouble("lead_gain");
	}

	/**
	 *  @return Deconfliction Gain - fp32_t
	 */
	public double getDeconflGain() {
		return getDouble("deconfl_gain");
	}

	/**
	 *  @return Acceleration Switch Gain - fp32_t
	 */
	public double getAccelSwitchGain() {
		return getDouble("accel_switch_gain");
	}

	/**
	 *  @return Safety Distance - fp32_t
	 */
	public double getSafeDist() {
		return getDouble("safe_dist");
	}

	/**
	 *  @return Deconfliction Offset - fp32_t
	 */
	public double getDeconflictOffset() {
		return getDouble("deconflict_offset");
	}

	/**
	 *  @return Acceleration Safety Margin - fp32_t
	 */
	public double getAccelSafeMargin() {
		return getDouble("accel_safe_margin");
	}

	/**
	 *  @return Maximum Longitudinal Acceleration - fp32_t
	 */
	public double getAccelLimX() {
		return getDouble("accel_lim_x");
	}

	/**
	 *  @param Action Action (enumerated)
	 */
	public FormationControlParams setAction(ACTION Action) {
		values.put("Action", Action.value());
		return this;
	}

	/**
	 *  @param Action Action (as a String)
	 */
	public FormationControlParams setAction(String Action) {
		setValue("Action", Action);
		return this;
	}

	/**
	 *  @param Action Action (integer value)
	 */
	public FormationControlParams setAction(short Action) {
		setValue("Action", Action);
		return this;
	}

	/**
	 *  @param lon_gain Longitudinal Gain
	 */
	public FormationControlParams setLonGain(double lon_gain) {
		values.put("lon_gain", lon_gain);
		return this;
	}

	/**
	 *  @param lat_gain Lateral Gain
	 */
	public FormationControlParams setLatGain(double lat_gain) {
		values.put("lat_gain", lat_gain);
		return this;
	}

	/**
	 *  @param bond_thick Boundary Layer Thickness
	 */
	public FormationControlParams setBondThick(double bond_thick) {
		values.put("bond_thick", bond_thick);
		return this;
	}

	/**
	 *  @param lead_gain Leader Gain
	 */
	public FormationControlParams setLeadGain(double lead_gain) {
		values.put("lead_gain", lead_gain);
		return this;
	}

	/**
	 *  @param deconfl_gain Deconfliction Gain
	 */
	public FormationControlParams setDeconflGain(double deconfl_gain) {
		values.put("deconfl_gain", deconfl_gain);
		return this;
	}

	/**
	 *  @param accel_switch_gain Acceleration Switch Gain
	 */
	public FormationControlParams setAccelSwitchGain(double accel_switch_gain) {
		values.put("accel_switch_gain", accel_switch_gain);
		return this;
	}

	/**
	 *  @param safe_dist Safety Distance
	 */
	public FormationControlParams setSafeDist(double safe_dist) {
		values.put("safe_dist", safe_dist);
		return this;
	}

	/**
	 *  @param deconflict_offset Deconfliction Offset
	 */
	public FormationControlParams setDeconflictOffset(double deconflict_offset) {
		values.put("deconflict_offset", deconflict_offset);
		return this;
	}

	/**
	 *  @param accel_safe_margin Acceleration Safety Margin
	 */
	public FormationControlParams setAccelSafeMargin(double accel_safe_margin) {
		values.put("accel_safe_margin", accel_safe_margin);
		return this;
	}

	/**
	 *  @param accel_lim_x Maximum Longitudinal Acceleration
	 */
	public FormationControlParams setAccelLimX(double accel_lim_x) {
		values.put("accel_lim_x", accel_lim_x);
		return this;
	}

}