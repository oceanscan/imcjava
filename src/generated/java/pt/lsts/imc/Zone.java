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
 *  IMC Message Zone (2038)<br/>
 */

public class Zone extends IMCMessage {

	public static final int ID_STATIC = 2038;

	public Zone() {
		super(ID_STATIC);
	}

	public Zone(IMCMessage msg) {
		super(ID_STATIC);
		try{
			copyFrom(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Zone(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public Zone(IMCDefinition defs, int type) {
		super(defs, type);
	}

	public static Zone create(Object... values) {
		Zone m = new Zone();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static Zone clone(IMCMessage msg) throws Exception {

		Zone m = new Zone();
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

	public Zone(java.util.Collection<GeoCoordinates> zone, float min_depth, float max_depth) {
		super(ID_STATIC);
		if (zone != null)
			setZone(zone);
		setMinDepth(min_depth);
		setMaxDepth(max_depth);
	}

	/**
	 *  @return Zone - message-list
	 */
	public java.util.Vector<GeoCoordinates> getZone() {
		try {
			return getMessageList("zone", GeoCoordinates.class);
		}
		catch (Exception e) {
			return null;
		}

	}

	/**
	 *  @param zone Zone
	 */
	public Zone setZone(java.util.Collection<GeoCoordinates> zone) {
		values.put("zone", zone);
		return this;
	}

	/**
	 *  @return Minimum Depth (m) - fp32_t
	 */
	public double getMinDepth() {
		return getDouble("min_depth");
	}

	/**
	 *  @param min_depth Minimum Depth (m)
	 */
	public Zone setMinDepth(double min_depth) {
		values.put("min_depth", min_depth);
		return this;
	}

	/**
	 *  @return Maximum Depth (m) - fp32_t
	 */
	public double getMaxDepth() {
		return getDouble("max_depth");
	}

	/**
	 *  @param max_depth Maximum Depth (m)
	 */
	public Zone setMaxDepth(double max_depth) {
		values.put("max_depth", max_depth);
		return this;
	}

}
