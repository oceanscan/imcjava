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
 *  IMC Message Station Keeping (461)<br/>
 *  The Station Keeping maneuver makes the vehicle come to the surface<br/>
 *  and then enter a given circular perimeter around a waypoint coordinate<br/>
 *  for a certain amount of time.<br/>
 */

public class StationKeeping extends Maneuver {

	public static final int ID_STATIC = 461;

	public enum Z_UNITS {
		NONE(0),
		DEPTH(1),
		ALTITUDE(2),
		HEIGHT(3);

		protected long value;

		public long value() {
			return value;
		}

		Z_UNITS(long value) {
			this.value = value;
		}
	}

	public enum SPEED_UNITS {
		METERS_PS(0),
		RPM(1),
		PERCENTAGE(2);

		protected long value;

		public long value() {
			return value;
		}

		SPEED_UNITS(long value) {
			this.value = value;
		}
	}

	public StationKeeping() {
		super(ID_STATIC);
	}

	public StationKeeping(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public static StationKeeping create(Object... values) {
		StationKeeping m = new StationKeeping();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static StationKeeping clone(IMCMessage msg) throws Exception {

		StationKeeping m = new StationKeeping();
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

	public StationKeeping(double lat, double lon, float z, Z_UNITS z_units, float radius, int duration, float speed, SPEED_UNITS speed_units, String custom) {
		super(ID_STATIC);
		setLat(lat);
		setLon(lon);
		setZ(z);
		setZUnits(z_units);
		setRadius(radius);
		setDuration(duration);
		setSpeed(speed);
		setSpeedUnits(speed_units);
		if (custom != null)
			setCustom(custom);
	}

	/**
	 *  @return Latitude WGS-84 (rad) - fp64_t
	 */
	public double getLat() {
		return getDouble("lat");
	}

	/**
	 *  @return Longitude WGS-84 (rad) - fp64_t
	 */
	public double getLon() {
		return getDouble("lon");
	}

	/**
	 *  @return Z Reference (m) - fp32_t
	 */
	public double getZ() {
		return getDouble("z");
	}

	/**
	 *  Units of the z reference.<br/>
	 *  @return Z Units (enumerated) - uint8_t
	 */
	public Z_UNITS getZUnits() {
		try {
			Z_UNITS o = Z_UNITS.valueOf(getMessageType().getFieldPossibleValues("z_units").get(getLong("z_units")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 *  @return Radius (m) - fp32_t
	 */
	public double getRadius() {
		return getDouble("radius");
	}

	/**
	 *  @return Duration (s) - uint16_t
	 */
	public int getDuration() {
		return getInteger("duration");
	}

	/**
	 *  @return Speed - fp32_t
	 */
	public double getSpeed() {
		return getDouble("speed");
	}

	/**
	 *  Indicates the units used for the speed value.<br/>
	 *  @return Speed Units (enumerated) - uint8_t
	 */
	public SPEED_UNITS getSpeedUnits() {
		try {
			SPEED_UNITS o = SPEED_UNITS.valueOf(getMessageType().getFieldPossibleValues("speed_units").get(getLong("speed_units")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 *  @return Custom settings for maneuver (tuplelist) - plaintext
	 */
	public java.util.LinkedHashMap<String, String> getCustom() {
		return getTupleList("custom");
	}

	/**
	 *  @param lat Latitude WGS-84 (rad)
	 */
	public void setLat(double lat) {
		values.put("lat", lat);
	}

	/**
	 *  @param lon Longitude WGS-84 (rad)
	 */
	public void setLon(double lon) {
		values.put("lon", lon);
	}

	/**
	 *  @param z Z Reference (m)
	 */
	public void setZ(double z) {
		values.put("z", z);
	}

	/**
	 *  @param z_units Z Units (enumerated)
	 */
	public void setZUnits(Z_UNITS z_units) {
		values.put("z_units", z_units.value());
	}

	/**
	 *  @param z_units Z Units (as a String)
	 */
	public void setZUnits(String z_units) {
		setValue("z_units", z_units);
	}

	/**
	 *  @param z_units Z Units (integer value)
	 */
	public void setZUnits(short z_units) {
		setValue("z_units", z_units);
	}

	/**
	 *  @param radius Radius (m)
	 */
	public void setRadius(double radius) {
		values.put("radius", radius);
	}

	/**
	 *  @param duration Duration (s)
	 */
	public void setDuration(int duration) {
		values.put("duration", duration);
	}

	/**
	 *  @param speed Speed
	 */
	public void setSpeed(double speed) {
		values.put("speed", speed);
	}

	/**
	 *  @param speed_units Speed Units (enumerated)
	 */
	public void setSpeedUnits(SPEED_UNITS speed_units) {
		values.put("speed_units", speed_units.value());
	}

	/**
	 *  @param speed_units Speed Units (as a String)
	 */
	public void setSpeedUnits(String speed_units) {
		setValue("speed_units", speed_units);
	}

	/**
	 *  @param speed_units Speed Units (integer value)
	 */
	public void setSpeedUnits(short speed_units) {
		setValue("speed_units", speed_units);
	}

	/**
	 *  @param custom Custom settings for maneuver (tuplelist)
	 */
	public void setCustom(java.util.LinkedHashMap<String, ?> custom) {
		String val = encodeTupleList(custom);
		values.put("custom", val);
	}

	public void setCustom(String custom) {
		values.put("custom", custom);
	}

}
