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

public class ImcStringDefs {

	public static final String IMC_SHA = "42d0ba28e7fd54e20ef9e918b524dab2037cfb43";
	public static final String IMC_BRANCH = "2023-07-27 42d0ba2 (HEAD -> feature/mechsonardata, origin/feature/mechsonardata)";
	public static final String IMC_COMMIT = "Renato Campos (rcampos.oceanscan@gmail.com), Thu Jul 27 18:10:12 WEST 2023, release to 5.4.32";

	public static java.util.Map<String, Integer> IMC_ADDRESSES = new java.util.LinkedHashMap<String, Integer>();

	static {
		IMC_ADDRESSES.put("*", 65535);
		IMC_ADDRESSES.put("announce", 0);
		IMC_ADDRESSES.put("durius-1", 2050);
		IMC_ADDRESSES.put("lauv-simulator-1", 209);
		IMC_ADDRESSES.put("lauv-dolphin-1", 257);
		IMC_ADDRESSES.put("lauv-dolphin-1-doam", 24577);
		IMC_ADDRESSES.put("lauv-dolphin-2", 259);
		IMC_ADDRESSES.put("lauv-dolphin-2-aux", 24580);
		IMC_ADDRESSES.put("lauv-dolphin-3", 260);
		IMC_ADDRESSES.put("lauv-dolphin-3-aux", 24581);
		IMC_ADDRESSES.put("lauv-lupis-1", 258);
		IMC_ADDRESSES.put("lauv-lupis-1-aux", 24591);
		IMC_ADDRESSES.put("lauv-oceaneco-1", 261);
		IMC_ADDRESSES.put("lauv-oceaneco-2", 262);
		IMC_ADDRESSES.put("lauv-banga", 8198);
		IMC_ADDRESSES.put("lauv-banga-aux", 24582);
		IMC_ADDRESSES.put("lauv-gelme", 8199);
		IMC_ADDRESSES.put("lauv-gelme-aux", 24583);
		IMC_ADDRESSES.put("lauv-srove", 8200);
		IMC_ADDRESSES.put("lauv-srove-aux", 24584);
		IMC_ADDRESSES.put("lauv-id-1", 8201);
		IMC_ADDRESSES.put("lauv-id-1-aux", 24585);
		IMC_ADDRESSES.put("lauv-eagleowl-1", 8202);
		IMC_ADDRESSES.put("lauv-eagleowl-1-aux", 24586);
		IMC_ADDRESSES.put("lauv-eagleowl-2", 8203);
		IMC_ADDRESSES.put("lauv-eagleowl-2-aux", 24587);
		IMC_ADDRESSES.put("lauv-omst-1", 8204);
		IMC_ADDRESSES.put("lauv-omst-1-aux", 24588);
		IMC_ADDRESSES.put("lauv-harald", 8205);
		IMC_ADDRESSES.put("lauv-fridtjof", 8206);
		IMC_ADDRESSES.put("lauv-fridtjof-aux", 24590);
		IMC_ADDRESSES.put("lauv-oceaneco-3", 8207);
		IMC_ADDRESSES.put("lauv-dmo-1", 8208);
		IMC_ADDRESSES.put("lauv-dmo-1-aux", 24593);
		IMC_ADDRESSES.put("lauv-tno-1", 8209);
		IMC_ADDRESSES.put("lauv-tno-1-aux", 24594);
		IMC_ADDRESSES.put("lauv-pub-1", 8210);
		IMC_ADDRESSES.put("lauv-pub-1-aux", 24595);
		IMC_ADDRESSES.put("lauv-oceaneco-4", 8211);
		IMC_ADDRESSES.put("lauv-oceaneco-4-aux", 24596);
		IMC_ADDRESSES.put("manta-10", 32795);
		IMC_ADDRESSES.put("manta-13", 32798);
		IMC_ADDRESSES.put("manta-14", 32799);
		IMC_ADDRESSES.put("manta-banga", 32868);
		IMC_ADDRESSES.put("manta-gelme", 32869);
		IMC_ADDRESSES.put("manta-srove", 32870);
		IMC_ADDRESSES.put("manta-id-1", 32871);
		IMC_ADDRESSES.put("manta-eagleowl-1", 32872);
		IMC_ADDRESSES.put("manta-omst-1", 32873);
		IMC_ADDRESSES.put("manta-ntnu-1", 32880);
		IMC_ADDRESSES.put("manta-oceaneco-3", 32881);
		IMC_ADDRESSES.put("manta-tno-1", 32883);
		IMC_ADDRESSES.put("manta-pub-1", 32884);
		IMC_ADDRESSES.put("manta-oceaneco-4", 32885);
		IMC_ADDRESSES.put("broadcast", 65520);
		IMC_ADDRESSES.put("null", 65535);

		IMC_ADDRESSES = java.util.Collections.unmodifiableMap(IMC_ADDRESSES);
	}

	public static String getDefinitions() {

		java.io.InputStream xmlStream = ImcStringDefs.class.getResourceAsStream("/xml/IMC.xml");
		java.io.InputStreamReader isreader = new java.io.InputStreamReader(xmlStream);
		java.io.BufferedReader reader = new java.io.BufferedReader(isreader);
		java.lang.StringBuilder builder = new java.lang.StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null)
				builder.append(line+"\n");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			return null;
		}

		return builder.toString();
	}
}
