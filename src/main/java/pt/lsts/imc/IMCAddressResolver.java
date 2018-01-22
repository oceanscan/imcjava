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
 */
package pt.lsts.imc;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class is used to store known imc names (mapping from their imc ids)
 *
 * @author zp
 *
 */
public class IMCAddressResolver {
	private LinkedHashMap<Integer, String> addresses = new LinkedHashMap<>();
	private LinkedHashMap<String, Integer> addressesReverse = new LinkedHashMap<>();
	private LinkedHashMap<Integer, LinkedHashMap<Integer, String>> knownEntities = new LinkedHashMap<>();

	/**
	 * Created a new resolver that loads all the static imc addresses from the
	 * IMC definitions
	 */
	public IMCAddressResolver() {
	}

	/**
	 * Tries to name a given imc id
	 *
	 * @param imcId
	 *            The IMC identifier to look for
	 * @return The resolved name. If the system is still unknown it will return
	 *         "unknown (&lt;imcId&gt;)"
	 */
	public String resolve(int imcId) {
		String name = addresses.get(imcId);
		return (name != null) ? name : "unknown (" + imcId + ")";
	}

	/**
	 * Given an IMC name retrieves its IMC id
	 *
	 * @param imcName
	 *            The IMC name to look for
	 * @return The found IMC id or -1 if no such system was found
	 */
	public int resolve(String imcName) {
		Integer id = addressesReverse.get(imcName);
		if (id == null)
			return -1;
		return id;
	}

	/**
	 * Adds or a updates a resolution entry
	 *
	 * @param imcid
	 *            The IMC identifier of the system
	 * @param imcName
	 *            The IMC name to be used for that ID
	 */
	public void addEntry(int imcid, String imcName) {
		addresses.put(imcid, imcName);
		addressesReverse.put(imcName, imcid);
	}

	/**
	 * Adds or updates an entity name resolution
	 *
	 * @param imcId
	 *            The IMC Id of the source system
	 * @param entityId
	 *            The entity Id of the entity
	 * @param name
	 *            The name of this entity id in that system
	 */
	public void setEntityName(int imcId, int entityId, String name) {
		if (!knownEntities.containsKey(imcId))
			knownEntities.put(imcId, new LinkedHashMap<>());
		knownEntities.get(imcId).put(entityId, name);
	}

	/**
	 * Updates entity name resolution given a map from entity ids to names
	 *
	 * @param imcId
	 *            The IMC Id of the source system
	 * @param idsToNames
	 *            A map from entity names to Ids. The types of the map are
	 *            fairly flexible: The entries are converted to String and the
	 *            parsed using Integer.parseInt(). The values are just converted
	 *            to String.
	 */
	public void setEntityMap(int imcId, Map<?, ?> namesToIds) {
		for (Entry<?, ?> e : namesToIds.entrySet()) {
			try {
				int entityId = Integer.parseInt("" + e.getValue());
				String name = "" + e.getKey();
				setEntityName(imcId, entityId, name);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Resolve the name of an entity Id
	 *
	 * @param src
	 *            The source system IMC Id
	 * @param src_ent
	 *            The entity Id
	 * @return The found entity name or <code>null</code> in case the entity was
	 *         not found.
	 */
	public String resolveEntity(int src, int src_ent) {
		if (!knownEntities.containsKey(src))
			return null;
		return knownEntities.get(src).get(src_ent);
	}
}
