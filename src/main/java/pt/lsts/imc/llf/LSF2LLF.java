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
 * $Id:: LSF2LLF.java 333 2013-01-02 11:11:44Z zepinto                         $:
 */
package pt.lsts.imc.llf;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Vector;

import pt.lsts.imc.IMCDefinition;
import pt.lsts.imc.IMCMessage;
import pt.lsts.imc.gz.MultiMemberGZIPInputStream;

public class LSF2LLF {
	protected Vector<IConverterListener> listeners = new Vector<>();
	private boolean useSeparateThread = false;
	protected Exception error = null;
	protected boolean complete = false;
	private boolean abortRqst = false;

	/**
	 * <p>
	 * IEEE 1541 recommends:
	 * </p>
	 * <ul>
	 * <li>a set of units to refer to quantities used in digital electronics and
	 * computing:
	 * <ul>
	 * <li><i>bit</i> (symbol 'b'), a binary digit;</li>
	 * <li><i>byte</i> (symbol 'B'), a set of adjacent bits (usually, but not
	 * necessarily, eight) operated on as a group;</li>
	 * <li><i>octet</i> (symbol 'o'), a group of eight bits;</li>
	 *
	 * </ul>
	 * </li>
	 * <li>a set of prefixes to indicate binary multiples of the aforesaid
	 * units:
	 * <ul>
	 * <li><i>kibi</i> (symbol 'Ki'), 2<sup>10</sup> = <span
	 * style="white-space: nowrap;">1<span
	 * style="margin-left: 0.25em;">024</span></span>;</li>
	 * <li><i>mebi</i> (symbol 'Mi'), 2<sup>20</sup> = <span
	 * style="white-space: nowrap;">1<span
	 * style="margin-left: 0.25em;">048</span><span
	 * style="margin-left: 0.25em;">576</span></span>;</li>
	 *
	 * <li><i>gibi</i> (symbol 'Gi'), 2<sup>30</sup> = <span
	 * style="white-space: nowrap;">1<span
	 * style="margin-left: 0.25em;">073</span><span
	 * style="margin-left: 0.25em;">741</span><span
	 * style="margin-left: 0.25em;">824</span></span>;</li>
	 * <li><i>tebi</i> (symbol 'Ti'), 2<sup>40</sup> = <span
	 * style="white-space: nowrap;">1<span
	 * style="margin-left: 0.25em;">099</span><span
	 * style="margin-left: 0.25em;">511</span><span
	 * style="margin-left: 0.25em;">627</span><span
	 * style="margin-left: 0.25em;">776</span></span>;</li>
	 *
	 * <li><i>pebi</i> (symbol 'Pi'), 2<sup>50</sup> = <span
	 * style="white-space: nowrap;">1<span
	 * style="margin-left: 0.25em;">125</span><span
	 * style="margin-left: 0.25em;">899</span><span
	 * style="margin-left: 0.25em;">906</span><span
	 * style="margin-left: 0.25em;">842</span><span
	 * style="margin-left: 0.25em;">624</span></span>;</li>
	 * <li><i>exbi</i> (symbol 'Ei'), 2<sup>60</sup> = <span
	 * style="white-space: nowrap;">1<span
	 * style="margin-left: 0.25em;">152</span><span
	 * style="margin-left: 0.25em;">921</span><span
	 * style="margin-left: 0.25em;">504</span><span
	 * style="margin-left: 0.25em;">606</span><span
	 * style="margin-left: 0.25em;">846</span><span
	 * style="margin-left: 0.25em;">976</span></span>;</li>
	 * <li><i>zebi</i> (symbol 'Zi'), 2<sup>70</sup> = <span
	 * style="white-space: nowrap;">11<span
	 * style="margin-left: 0.25em;">805</span><span
	 * style="margin-left: 0.25em;">916</span><span
	 * style="margin-left: 0.25em;">207</span><span
	 * style="margin-left: 0.25em;">174</span><span
	 * style="margin-left: 0.25em;">113</span><span
	 * style="margin-left: 0.25em;">034</span><span
	 * style="margin-left: 0.25em;">241</span></span>;</li>
	 * <li><i>yobi</i> (symbol 'Yi'), 2<sup>80</sup> = <span
	 * style="white-space: nowrap;">1<span
	 * style="margin-left: 0.25em;">208</span><span
	 * style="margin-left: 0.25em;">925</span><span
	 * style="margin-left: 0.25em;">819</span><span
	 * style="margin-left: 0.25em;">614</span><span
	 * style="margin-left: 0.25em;">629</span><span
	 * style="margin-left: 0.25em;">174</span><span
	 * style="margin-left: 0.25em;">706</span><span
	 * style="margin-left: 0.25em;">176</span></span>;</li>
	 *
	 * </ul>
	 * </li>
	 * <li>that the first part of the binary prefix is pronounced as the
	 * analogous SI prefix, and the second part is pronounced as <i>bee</i>;</li>
	 * <li>that SI prefixes are not used to indicate binary multiples.</li>
	 * </ul>
	 * <p>
	 * The <i>bi</i> part of the prefix comes from the word binary, so for
	 * example, kibibyte means a kilobinary byte, that is 1024 bytes.
	 * </p>
	 * <p>
	 * Note the capital 'K' for the <i>kibi-</i> symbol: while the symbol for
	 * the analogous SI prefix <i>kilo-</i> is a small 'k', a capital 'K' has
	 * been selected for consistency with the other prefixes and with the
	 * widespread use of the misspelled SI prefix (as in 'KB').
	 * </p>
	 *
	 * <p>
	 * IEEE 1541 is closely related to Amendment 2 to IEC International Standard
	 * <a href="/wiki/IEC_60027" title="IEC 60027">IEC 60027</a>-2, except the
	 * latter uses 'bit' as the symbol for bit, as opposed to 'b'.
	 * </p>
	 * <p>
	 * Today the harmonized <a href="/wiki/ISO" title="ISO"
	 * class="mw-redirect">ISO</a>/<a
	 * href="/wiki/International_Electrotechnical_Commission"
	 * title="International Electrotechnical Commission">IEC</a> <a
	 * href="/wiki/ISO/IEC_80000" title="ISO/IEC 80000">IEC 80000-13:2008 -
	 * Quantities and units -- Part 13: Information science and technology</a>
	 * standard cancels and replaces subclauses 3.8 and 3.9 of IEC 60027-2:2005
	 * (those related to Information theory and Prefixes for binary multiples).
	 * </p>
	 *
	 * @param val
	 * @param decimalHouses
	 * @return
	 */
	private static String parseToEngineeringRadix2Notation(double val, int decimalHouses) {
		int mulTmp = 0;
		int signal = 1;
		if (val < 0)
			signal = -1;
		else
			signal = 1;
		double valTmp = val < 0 ? val * -1 : val;
		if (val >= 1024) {
			do {
				mulTmp++;
				valTmp = valTmp / 1024.0;
			} while (valTmp >= 1024 || mulTmp == 8);
		}
		else if (val == 0) {
			// Nothing to do
		}
		else if (val < 1.0) {
			do {
				mulTmp--;
				valTmp = valTmp * 1024.0;
			} while (valTmp < 1.0 || mulTmp == -8);

		}

		double vl = valTmp;
		vl = round(vl, decimalHouses);
		int mul = mulTmp * 10;
		String mulStr = "";
		switch (mul) {
		case 80:
			mulStr = "Yi";
			break;
		case 70:
			mulStr = "Zi";
			break;
		case 60:
			mulStr = "Ei";
			break;
		case 50:
			mulStr = "Pi";
			break;
		case 40:
			mulStr = "Ti";
			break;
		case 30:
			mulStr = "Gi";
			break;
		case 20:
			mulStr = "Mi";
			break;
		case 10:
			mulStr = "Ki";
			break;
		case -10:
			mulStr = "mi";
			break;
		case -20:
			mulStr = "ui";
			mulStr = "\u00B5i";
			break;
		case -30:
			mulStr = "ni";
			break;
		case -40:
			mulStr = "pi";
			break;
		case -50:
			mulStr = "fi";
			break;
		case -60:
			mulStr = "ai";
			break;
		case -70:
			mulStr = "zi";
			break;
		case -80:
			mulStr = "yi";
			break;
		default:
			mulStr = "";
			break;
		}
		if ("".equalsIgnoreCase(mulStr) && vl < 1024) {
			if (vl == (long) vl)
				return (signal > 0 ? "" : "-") + ((long) vl) + mulStr;
		}
		return (signal > 0 ? "" : "-") + vl + mulStr;
	}

	public static double round(double val, int decimalHouses) {
		double base = Math.pow(10d, decimalHouses);
		double result = Math.round(val * base) / base;
		return result;
	}

	public void addListener(IConverterListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeListener(IConverterListener listener) {
		listeners.remove(listener);
	}

	public void convert(File dir) throws Exception {
		File data_lsf = null;

		for (File f : dir.listFiles()) {
			String filename = f.getName().toLowerCase();
			if (filename.endsWith(".lsf")) {
				data_lsf = f;
				break;
			}
		}
		if (data_lsf == null) {
			for (File f : dir.listFiles()) {
				String filename = f.getName().toLowerCase();
				if (filename.endsWith(".lsf.gz")) {
					data_lsf = f;
					break;
				}
			}
		}

		if (data_lsf != null) {
			if (new File(data_lsf.getParent(), "IMC.xml").canRead()) {
				convert(new File(data_lsf.getParent(), "IMC.xml"), data_lsf);
			} else if (new File(data_lsf.getParent(), "IMC.xml.gz").canRead()) {
				InputStream is = new MultiMemberGZIPInputStream(
						new FileInputStream(new File(data_lsf.getParent(),
								"IMC.xml.gz")));
				convert(IMCDefinition.getInstance(is), data_lsf);
			} else {
				convert(IMCDefinition.getInstance(), data_lsf);
			}
		} else {
			System.err
					.println("This program should be run inside a folder containing lsf files");
		}
	}

	private void convertInBackground(IMCDefinition defs, File data_lsf) {
		final IMCDefinition d = defs;
		final File lsf = data_lsf;
		error = null;
		complete = false;
		Thread t = new Thread() {
			public void run() {
				try {
					long size = lsf.length(), pos = 0, perc_parts = size / 100, percent = 0, msgcount = 0;
					LLFMessageLogger logger = new LLFMessageLogger(
							lsf.getParent());

					FileInputStream is = new FileInputStream(lsf);
					MappedByteBuffer buff = is.getChannel().map(
							MapMode.READ_ONLY, 0, size);

					IMCMessage message = null;
					abortRqst = false;
					while (buff.remaining() > 0 && !abortRqst) {
						message = d.nextMessage(buff);
						if (message != null) {
							pos += message.getLong("size") + d.headerLength()
									+ 2;
							msgcount += logger.logMessage(message);
							long p = Math.min(100, pos / perc_parts);
							if (p != percent) {
								for (IConverterListener l : listeners)
									l.update(size, pos, msgcount);
								percent = p;
							}
						}
					}
					logger.close();
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
					error = e;
				}
				complete = true;
			};
		};
		t.start();
	}

	public void convert(IMCDefinition defs, File lsf) throws Exception {

		if (useSeparateThread) {
			convertInBackground(defs, lsf);
			return;
		}
		long size = lsf.length();
		FileInputStream fis = new FileInputStream(lsf);
		InputStream is = fis;
		if (lsf.getName().toLowerCase().endsWith("lsf.gz")) {
			is = new MultiMemberGZIPInputStream(is);
			size = -1;
		}
		convert(defs, is, size, lsf.getParent());
	}

	public void convert(IMCDefinition defs, InputStream lsfIS, long size,
			String logDirectory) throws Exception {
		long pos = 0, perc_parts = size / 100, percent = 0, msgcount = 0;

		LLFMessageLogger logger = new LLFMessageLogger(logDirectory);
		logger.defs = defs;

		boolean isBufferOrStream = true;
		MappedByteBuffer buff = null;

		if (lsfIS instanceof FileInputStream) {
			FileInputStream is = (FileInputStream) lsfIS;
			FileChannel channel = is.getChannel();
			buff = channel.map(MapMode.READ_ONLY, 0, size);
			isBufferOrStream = true;
		} else {
			isBufferOrStream = false;
		}

		IMCMessage message = null;

		abortRqst = false;
		try {
			while (true) {
				if (isBufferOrStream) {
					if (!(buff.remaining() > 0 && !abortRqst))
						break;
				}
				if (isBufferOrStream)
					message = defs.nextMessage(buff);
				else
					message = defs.nextMessage(lsfIS);

				if (message != null) {
					pos += message.getLong("size") + defs.headerLength() + 2;
					msgcount += logger.logMessage(message);
					if (size > 0) {
						long p =  Math.min(100, pos / perc_parts);
						if (p != percent) {
							for (IConverterListener l : listeners)
								l.update(size, pos, msgcount);
							percent = p;
						}
					}
					else {
						if (msgcount % 2500 == 0) {
							for (IConverterListener l : listeners)
								l.update(-1, pos, msgcount);
						}
					}
				}
			}
		} catch (Exception e1) {
			if (!(e1 instanceof EOFException))
				throw e1;
		} finally {
			logger.close();
			// try { channel.close(); } catch (Exception e) {}
			try {
				lsfIS.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void convert(InputStream is, File data_lsf) throws Exception {
		IMCDefinition defs = new IMCDefinition(is);
		convert(defs, data_lsf);
	}

	public void convert(File imc_xml, File data_lsf) throws Exception {
		System.out.println("Converting " + data_lsf.getCanonicalPath()
				+ " using definitions from " + imc_xml.getCanonicalPath()
				+ "...");

		convert(new FileInputStream(imc_xml), data_lsf);
	}

	static void printOptions() {
		System.out.println(getOptions());
	}

	static String getOptions() {
		return "IMCJava - " + LSF2LLF.class.getSimpleName()
				+ "\nCopyright (c) 2011 - Universidade do Porto"
				+ " - FEUP LSTS. All rights reserved.\n"
				+ "http://whale.fe.up.pt | lsts@fe.up.pt" + "\n\nOptions:\n"
				+ "[[LSF-File | LSF.GZ-File] [IMC-Def-File]]\n";
	}

	public static void main(String[] args) throws Exception {
		final long startTime = System.currentTimeMillis();
		LSF2LLF converter = new LSF2LLF();
		converter.addListener((filesize, curPosition, messageCount) -> {
            if (filesize > 0) {
                System.out.print("\rProcessed "
                        + String.format("%3d", curPosition * 100 / filesize)
                        + "% ("
                        + messageCount
                        + " msgs, "
                        + parseToEngineeringRadix2Notation(
                                curPosition, 1) + "B)           ");
            }
            else {
                System.out.print("\rProcessed "
                        + messageCount
                        + " msgs, "
                        + parseToEngineeringRadix2Notation(
                                curPosition, 1) + "B           ");
            }
        });

		if (args.length == 0)
			converter.convert(new File("."));
		else if (args.length == 1) {
			if ("-h".equalsIgnoreCase(args[0])
					|| "--help".equalsIgnoreCase(args[0])) {
				printOptions();
				return;
			}
			File data_lsf = new File(args[0]).getAbsoluteFile();
			if (data_lsf.isDirectory()) {
				converter.convert(data_lsf);
				return;
			}

			if (new File(data_lsf.getParentFile(), "IMC.xml").canRead()) {
				System.out.println("Loading IMC definitions in "
						+ new File(data_lsf.getParentFile(), "IMC.xml")
								.getCanonicalPath());
				converter.convert(
						new FileInputStream(new File(data_lsf.getParentFile(),
								"IMC.xml")), data_lsf);
			} else if (new File(data_lsf.getParent(), "IMC.xml.gz").canRead()) {
				InputStream is = new MultiMemberGZIPInputStream(
						new FileInputStream(new File(data_lsf.getParent(),
								"IMC.xml.gz")));
				converter.convert(IMCDefinition.getInstance(is), data_lsf);
			} else {
				converter.convert(IMCDefinition.getInstance(), data_lsf);
			}

		} else if (args.length == 2) {
			File data_lsf = new File(args[0]).getAbsoluteFile();
			File imc_xml = new File(args[1]).getAbsoluteFile();
			converter.convert(new FileInputStream(imc_xml), data_lsf);
		}
		converter = null;
		System.out.println("\nGeneration time: "
				+ ((float) (System.currentTimeMillis() - startTime) / 1000)
				+ " seconds");
	}
}
