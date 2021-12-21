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
 *  IMC Message Typed Entity Parameter (2008)<br/>
 *  Entity parameter with all the data that defines an entity parameter.<br/>
 */

public class TypedEntityParameter extends IMCMessage {

	public enum TYPE {
		BOOL(1),
		INT(2),
		FLOAT(3),
		STRING(4),
		LIST_BOOL(5),
		LIST_INT(6),
		LIST_FLOAT(7),
		LIST_STRING(8);

		protected long value;

		public long value() {
			return value;
		}

		TYPE(long value) {
			this.value = value;
		}
	}

	public enum UNITS {
		METERPERSQUARESECOND(0),
		DECIBELPERMETER(1),
		DEGREE(2),
		RADIAN(3),
		RADIANPERSECOND(4),
		DEGREEPERSECOND(5),
		SQUAREMETER(6),
		KILOGRAMPERCUBICMETER(7),
		BIT(8),
		BYTE(9),
		KIBIBYTE(10),
		MIBIBYTE(11),
		GIBIBYTE(12),
		BITPERSECOND(13),
		PIXEL(14),
		METER(15),
		CENTIMETER(16),
		KILOMETER(17),
		KILOGRAMPERSECOND(18),
		KILOGRAMPERMETER(19),
		COULOMB(20),
		AMPEREHOUR(21),
		MILLIAMPEREHOUR(22),
		AMPERE(23),
		MILLIAMPERE(24),
		VOLT(25),
		MILLIVOLT(26),
		WATTHOUR(27),
		KILLOWATTHOUR(28),
		NEWTON(29),
		HERTZ(30),
		KILOHERTZ(31),
		MEGAHERTZ(32),
		DECIBEL(33),
		GAUSS(34),
		GRAM(35),
		KILOGRAM(36),
		KILOGRAMSQUAREMETER(37),
		WATT(38),
		MILLIWATT(39),
		ATMOSPHERE(40),
		PASCAL(41),
		KILOPASCAL(42),
		BAR(43),
		RPM(44),
		DEGREECELSIUS(45),
		SECOND(46),
		MILLISECOND(47),
		MICROSECOND(48),
		NANOSECOND(49),
		MINUTE(50),
		NETWONMETER(51),
		NETWONMETERPERRADIAN(52),
		METERPERSECOND(53),
		KNOT(54),
		CUBICMETER(55),
		PERCENTAGE(56),
		NONE(57);

		protected long value;

		public long value() {
			return value;
		}

		UNITS(long value) {
			this.value = value;
		}
	}

	public enum VISIBILITY {
		USER(0),
		DEVELOPER(1);

		protected long value;

		public long value() {
			return value;
		}

		VISIBILITY(long value) {
			this.value = value;
		}
	}

	public enum SCOPE {
		GLOBAL(0),
		IDLE(1),
		PLAN(2),
		MANEUVER(3);

		protected long value;

		public long value() {
			return value;
		}

		SCOPE(long value) {
			this.value = value;
		}
	}

	public static final int ID_STATIC = 2008;

	public TypedEntityParameter() {
		super(ID_STATIC);
	}

	public TypedEntityParameter(IMCMessage msg) {
		super(ID_STATIC);
		try{
			copyFrom(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TypedEntityParameter(IMCDefinition defs) {
		super(defs, ID_STATIC);
	}

	public static TypedEntityParameter create(Object... values) {
		TypedEntityParameter m = new TypedEntityParameter();
		for (int i = 0; i < values.length-1; i+= 2)
			m.setValue(values[i].toString(), values[i+1]);
		return m;
	}

	public static TypedEntityParameter clone(IMCMessage msg) throws Exception {

		TypedEntityParameter m = new TypedEntityParameter();
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

	public TypedEntityParameter(String name, TYPE type, String default_value, UNITS units, String description, float min_value, float max_value, long list_min_size, long list_max_size, VISIBILITY visibility, SCOPE scope) {
		super(ID_STATIC);
		if (name != null)
			setName(name);
		setType(type);
		if (default_value != null)
			setDefaultValue(default_value);
		setUnits(units);
		if (description != null)
			setDescription(description);
		setMinValue(min_value);
		setMaxValue(max_value);
		setListMinSize(list_min_size);
		setListMaxSize(list_max_size);
		setVisibility(visibility);
		setScope(scope);
	}

	/**
	 *  @return Entity Name - plaintext
	 */
	public String getName() {
		return getString("name");
	}

	/**
	 *  @param name Entity Name
	 */
	public TypedEntityParameter setName(String name) {
		values.put("name", name);
		return this;
	}

	/**
	 *  @return Type (enumerated) - uint8_t
	 */
	public TYPE getType() {
		try {
			TYPE o = TYPE.valueOf(getMessageType().getFieldPossibleValues("type").get(getLong("type")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getTypeStr() {
		return getString("type");
	}

	public short getTypeVal() {
		return (short) getInteger("type");
	}

	/**
	 *  @param type Type (enumerated)
	 */
	public TypedEntityParameter setType(TYPE type) {
		values.put("type", type.value());
		return this;
	}

	/**
	 *  @param type Type (as a String)
	 */
	public TypedEntityParameter setTypeStr(String type) {
		setValue("type", type);
		return this;
	}

	/**
	 *  @param type Type (integer value)
	 */
	public TypedEntityParameter setTypeVal(short type) {
		setValue("type", type);
		return this;
	}

	/**
	 *  @return Default Value - plaintext
	 */
	public String getDefaultValue() {
		return getString("default_value");
	}

	/**
	 *  @param default_value Default Value
	 */
	public TypedEntityParameter setDefaultValue(String default_value) {
		values.put("default_value", default_value);
		return this;
	}

	/**
	 *  @return Units (enumerated) - uint8_t
	 */
	public UNITS getUnits() {
		try {
			UNITS o = UNITS.valueOf(getMessageType().getFieldPossibleValues("units").get(getLong("units")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getUnitsStr() {
		return getString("units");
	}

	public short getUnitsVal() {
		return (short) getInteger("units");
	}

	/**
	 *  @param units Units (enumerated)
	 */
	public TypedEntityParameter setUnits(UNITS units) {
		values.put("units", units.value());
		return this;
	}

	/**
	 *  @param units Units (as a String)
	 */
	public TypedEntityParameter setUnitsStr(String units) {
		setValue("units", units);
		return this;
	}

	/**
	 *  @param units Units (integer value)
	 */
	public TypedEntityParameter setUnitsVal(short units) {
		setValue("units", units);
		return this;
	}

	/**
	 *  @return Description - plaintext
	 */
	public String getDescription() {
		return getString("description");
	}

	/**
	 *  @param description Description
	 */
	public TypedEntityParameter setDescription(String description) {
		values.put("description", description);
		return this;
	}

	/**
	 *  @return Min Value - fp32_t
	 */
	public double getMinValue() {
		return getDouble("min_value");
	}

	/**
	 *  @param min_value Min Value
	 */
	public TypedEntityParameter setMinValue(double min_value) {
		values.put("min_value", min_value);
		return this;
	}

	/**
	 *  @return Max Value - fp32_t
	 */
	public double getMaxValue() {
		return getDouble("max_value");
	}

	/**
	 *  @param max_value Max Value
	 */
	public TypedEntityParameter setMaxValue(double max_value) {
		values.put("max_value", max_value);
		return this;
	}

	/**
	 *  @return List Min Size - uint32_t
	 */
	public long getListMinSize() {
		return getLong("list_min_size");
	}

	/**
	 *  @param list_min_size List Min Size
	 */
	public TypedEntityParameter setListMinSize(long list_min_size) {
		values.put("list_min_size", list_min_size);
		return this;
	}

	/**
	 *  @return List Max Size - uint32_t
	 */
	public long getListMaxSize() {
		return getLong("list_max_size");
	}

	/**
	 *  @param list_max_size List Max Size
	 */
	public TypedEntityParameter setListMaxSize(long list_max_size) {
		values.put("list_max_size", list_max_size);
		return this;
	}

	/**
	 *  @return Visibility (enumerated) - uint8_t
	 */
	public VISIBILITY getVisibility() {
		try {
			VISIBILITY o = VISIBILITY.valueOf(getMessageType().getFieldPossibleValues("visibility").get(getLong("visibility")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getVisibilityStr() {
		return getString("visibility");
	}

	public short getVisibilityVal() {
		return (short) getInteger("visibility");
	}

	/**
	 *  @param visibility Visibility (enumerated)
	 */
	public TypedEntityParameter setVisibility(VISIBILITY visibility) {
		values.put("visibility", visibility.value());
		return this;
	}

	/**
	 *  @param visibility Visibility (as a String)
	 */
	public TypedEntityParameter setVisibilityStr(String visibility) {
		setValue("visibility", visibility);
		return this;
	}

	/**
	 *  @param visibility Visibility (integer value)
	 */
	public TypedEntityParameter setVisibilityVal(short visibility) {
		setValue("visibility", visibility);
		return this;
	}

	/**
	 *  @return Scope (enumerated) - uint8_t
	 */
	public SCOPE getScope() {
		try {
			SCOPE o = SCOPE.valueOf(getMessageType().getFieldPossibleValues("scope").get(getLong("scope")));
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getScopeStr() {
		return getString("scope");
	}

	public short getScopeVal() {
		return (short) getInteger("scope");
	}

	/**
	 *  @param scope Scope (enumerated)
	 */
	public TypedEntityParameter setScope(SCOPE scope) {
		values.put("scope", scope.value());
		return this;
	}

	/**
	 *  @param scope Scope (as a String)
	 */
	public TypedEntityParameter setScopeStr(String scope) {
		setValue("scope", scope);
		return this;
	}

	/**
	 *  @param scope Scope (integer value)
	 */
	public TypedEntityParameter setScopeVal(short scope) {
		setValue("scope", scope);
		return this;
	}

}