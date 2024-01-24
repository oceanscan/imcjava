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
 *  IMC Message Directional Sonar Data (2019)<br/>
 *  This message contains the data acquired by a single sonar measurement whose angle can be controlled.<br/>
 */

public class DirSonarData extends IMCMessage {

	public static final int ID_STATIC = 2019;

	public DirSonarData() {
		super(ID_STATIC);
	}

	public DirSonarData(IMCMessage msg) {
		super(ID_STATIC);
		try{
			copyFrom(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DirSonarData(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public DirSonarData(IMCDefinition defs, int type) {
		super(defs, type);
	}

	public static DirSonarData create(Object... values) {
		DirSonarData m = new DirSonarData();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static DirSonarData clone(IMCMessage msg) throws Exception {

		DirSonarData m = new DirSonarData();
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

	public DirSonarData(DeviceState pose, SonarData measurement) {
		super(ID_STATIC);
		if (pose != null)
			setPose(pose);
		if (measurement != null)
			setMeasurement(measurement);
	}

	/**
	 *  @return Relative Pose - message
	 */
	public DeviceState getPose() {
		try {
			IMCMessage obj = getMessage("pose");
			return DeviceState.clone(obj);
		}
		catch (Exception e) {
			return null;
		}

	}

	/**
	 *  @param pose Relative Pose
	 */
	public DirSonarData setPose(DeviceState pose) {
		values.put("pose", pose);
		return this;
	}

	/**
	 *  @return Measurement - message
	 */
	public SonarData getMeasurement() {
		try {
			IMCMessage obj = getMessage("measurement");
			return SonarData.clone(obj);
		}
		catch (Exception e) {
			return null;
		}

	}

	/**
	 *  @param measurement Measurement
	 */
	public DirSonarData setMeasurement(SonarData measurement) {
		values.put("measurement", measurement);
		return this;
	}

}