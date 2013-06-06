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

// Source generated by IMCJava from IMC version 5.2.0
package pt.up.fe.dceg.neptus.imc;

/**
 *  IMC Message Navigation Uncertainty (354)<br/>
 *  Report of navigation uncertainty.<br/>
 *  This is usually given by the output of the state<br/>
 *  covariance matrix of an Extended Kalman Filter.<br/>
 */

public class NavigationUncertainty extends IMCMessage {

	public static final int ID_STATIC = 354;

	public NavigationUncertainty() {
		super(ID_STATIC);
	}

	public NavigationUncertainty(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public static NavigationUncertainty create(Object... values) {
		NavigationUncertainty m = new NavigationUncertainty();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static NavigationUncertainty clone(IMCMessage msg) throws Exception {

		NavigationUncertainty m = new NavigationUncertainty();
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

	public NavigationUncertainty(float x, float y, float z, float phi, float theta, float psi, float p, float q, float r, float u, float v, float w, float bias_psi, float bias_r) {
		super(ID_STATIC);
		setX(x);
		setY(y);
		setZ(z);
		setPhi(phi);
		setTheta(theta);
		setPsi(psi);
		setP(p);
		setQ(q);
		setR(r);
		setU(u);
		setV(v);
		setW(w);
		setBiasPsi(bias_psi);
		setBiasR(bias_r);
	}

	/**
	 *  @return Variance - x Position (m) - fp32_t
	 */
	public double getX() {
		return getDouble("x");
	}

	/**
	 *  @return Variance - y Position (m) - fp32_t
	 */
	public double getY() {
		return getDouble("y");
	}

	/**
	 *  @return Variance - z Position (m) - fp32_t
	 */
	public double getZ() {
		return getDouble("z");
	}

	/**
	 *  @return Variance - Roll (rad) - fp32_t
	 */
	public double getPhi() {
		return getDouble("phi");
	}

	/**
	 *  @return Variance - Pitch (rad) - fp32_t
	 */
	public double getTheta() {
		return getDouble("theta");
	}

	/**
	 *  @return Variance - Yaw (rad) - fp32_t
	 */
	public double getPsi() {
		return getDouble("psi");
	}

	/**
	 *  @return Variance - Gyro. Roll Rate (rad/s) - fp32_t
	 */
	public double getP() {
		return getDouble("p");
	}

	/**
	 *  @return Variance - Gyro. Pitch Rate (rad/s) - fp32_t
	 */
	public double getQ() {
		return getDouble("q");
	}

	/**
	 *  @return Variance - Gyro. Yaw Rate (rad/s) - fp32_t
	 */
	public double getR() {
		return getDouble("r");
	}

	/**
	 *  @return Variance - Body-Fixed xx Velocity (m/s) - fp32_t
	 */
	public double getU() {
		return getDouble("u");
	}

	/**
	 *  @return Variance - Body-Fixed yy Velocity (m/s) - fp32_t
	 */
	public double getV() {
		return getDouble("v");
	}

	/**
	 *  @return Variance - Body-Fixed ww Velocity (m/s) - fp32_t
	 */
	public double getW() {
		return getDouble("w");
	}

	/**
	 *  @return Variance - Yaw Bias (rad) - fp32_t
	 */
	public double getBiasPsi() {
		return getDouble("bias_psi");
	}

	/**
	 *  @return Variance - Gyro. Yaw Rate Bias (rad/s) - fp32_t
	 */
	public double getBiasR() {
		return getDouble("bias_r");
	}

	/**
	 *  @param x Variance - x Position (m)
	 */
	public void setX(double x) {
		values.put("x", x);
	}

	/**
	 *  @param y Variance - y Position (m)
	 */
	public void setY(double y) {
		values.put("y", y);
	}

	/**
	 *  @param z Variance - z Position (m)
	 */
	public void setZ(double z) {
		values.put("z", z);
	}

	/**
	 *  @param phi Variance - Roll (rad)
	 */
	public void setPhi(double phi) {
		values.put("phi", phi);
	}

	/**
	 *  @param theta Variance - Pitch (rad)
	 */
	public void setTheta(double theta) {
		values.put("theta", theta);
	}

	/**
	 *  @param psi Variance - Yaw (rad)
	 */
	public void setPsi(double psi) {
		values.put("psi", psi);
	}

	/**
	 *  @param p Variance - Gyro. Roll Rate (rad/s)
	 */
	public void setP(double p) {
		values.put("p", p);
	}

	/**
	 *  @param q Variance - Gyro. Pitch Rate (rad/s)
	 */
	public void setQ(double q) {
		values.put("q", q);
	}

	/**
	 *  @param r Variance - Gyro. Yaw Rate (rad/s)
	 */
	public void setR(double r) {
		values.put("r", r);
	}

	/**
	 *  @param u Variance - Body-Fixed xx Velocity (m/s)
	 */
	public void setU(double u) {
		values.put("u", u);
	}

	/**
	 *  @param v Variance - Body-Fixed yy Velocity (m/s)
	 */
	public void setV(double v) {
		values.put("v", v);
	}

	/**
	 *  @param w Variance - Body-Fixed ww Velocity (m/s)
	 */
	public void setW(double w) {
		values.put("w", w);
	}

	/**
	 *  @param bias_psi Variance - Yaw Bias (rad)
	 */
	public void setBiasPsi(double bias_psi) {
		values.put("bias_psi", bias_psi);
	}

	/**
	 *  @param bias_r Variance - Gyro. Yaw Rate Bias (rad/s)
	 */
	public void setBiasR(double bias_r) {
		values.put("bias_r", bias_r);
	}

}
