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
 *  IMC Message Control Parcel (412)<br/>
 *  Report of PID control parcels.<br/>
 */

public class ControlParcel extends IMCMessage {

	public static final int ID_STATIC = 412;

	public ControlParcel() {
		super(ID_STATIC);
	}

	public ControlParcel(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public static ControlParcel create(Object... values) {
		ControlParcel m = new ControlParcel();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static ControlParcel clone(IMCMessage msg) throws Exception {

		ControlParcel m = new ControlParcel();
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

	public ControlParcel(float p, float i, float d, float a) {
		super(ID_STATIC);
		setP(p);
		setI(i);
		setD(d);
		setA(a);
	}

	/**
	 *  @return Proportional Parcel - fp32_t
	 */
	public double getP() {
		return getDouble("p");
	}

	/**
	 *  @return Integrative Parcel - fp32_t
	 */
	public double getI() {
		return getDouble("i");
	}

	/**
	 *  @return Derivative Parcel - fp32_t
	 */
	public double getD() {
		return getDouble("d");
	}

	/**
	 *  @return Anti-Windup Parcel - fp32_t
	 */
	public double getA() {
		return getDouble("a");
	}

	/**
	 *  @param p Proportional Parcel
	 */
	public void setP(double p) {
		values.put("p", p);
	}

	/**
	 *  @param i Integrative Parcel
	 */
	public void setI(double i) {
		values.put("i", i);
	}

	/**
	 *  @param d Derivative Parcel
	 */
	public void setD(double d) {
		values.put("d", d);
	}

	/**
	 *  @param a Anti-Windup Parcel
	 */
	public void setA(double a) {
		values.put("a", a);
	}

}