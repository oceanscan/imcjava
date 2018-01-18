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
 * $Id:: IMCInputStream.java 333 2013-01-02 11:11:44Z zepinto                  $:
 */

package pt.lsts.imc;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class IMCInputStream extends FilterInputStream implements DataInput {

    private DataInputStream input;
    private boolean bigEndian = true;
    private int crc = 0;
    private IMCDefinition defs;

    public IMCInputStream(InputStream in, IMCDefinition defs) {
        super(in);
        this.input = new DataInputStream(in);
        this.defs = defs;
    }

    public boolean isBigEndian() {
        return bigEndian;
    }

    public void setBigEndian(boolean big_endian) {
        this.bigEndian = big_endian;
    }

    public int getCrc() {
        return crc;
    }

    public int resetCrc(int crc) {
        int before = this.crc;
        this.crc = crc;
        return before;
    }

    public int resetCrc() {
        return resetCrc(0);
    }

    public int resync(int syncword) throws IOException {

        int byte1, byte2, count = 0, b1 = 0, b2 = 0;

        if (bigEndian) {
            byte2 = (syncword & 0xFF00) >> 8;
            byte1 = syncword & 0x00FF;
        } else {
            byte1 = (syncword & 0xFF00) >> 8;
            byte2 = syncword & 0x00FF;
        }

        while (true) {
            if (b1 == -1)
                throw new IOException("Reached the end of file");
            else if (b1 == byte1) {
                b2 = read();
                count++;
                if (b1 == byte1 && b2 == byte2)
                    return count;
                else
                    b1 = b2;
            } else {
                b1 = read();
                count++;
                continue;
            }
        }
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return super.read(b, off, len);
    }

    @Override
    public int read() throws IOException {
        int b = super.read();
        crc = (crc >> 8) ^ IMCUtil.crc16_table[(crc ^ b) & 0xff];
        return b;
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        input.readFully(b);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        input.readFully(b, off, len);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        return input.skipBytes(n);
    }

    @Override
    public boolean readBoolean() throws IOException {
        return input.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return input.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return input.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        short val = input.readShort();
        if (bigEndian)
            return val;
        else
            return Short.reverseBytes(val);
    }

    @Override
    public int readUnsignedShort() throws IOException {
        short val = readShort();
        return 0xFFFF & val;
    }

    @Override
    public char readChar() throws IOException {
        char val = input.readChar();
        if (bigEndian)
            return val;
        else
            return Character.reverseBytes(val);
    }

    @Override
    public int readInt() throws IOException {
        int val = input.readInt();
        if (!bigEndian)
            val = Integer.reverseBytes(val);

        return val;
    }

    public long readUnsignedInt() throws IOException {
        int val = readInt();
        return 0x00000000FFFFFFFFL & val;
    }

    @Override
    public long readLong() throws IOException {
        long val = input.readLong();
        if (bigEndian)
            return val;
        else
            return Long.reverseBytes(val);
    }

    public BigInteger readUnsignedLong() throws IOException {
        byte[] bytes = new byte[8];

        read(bytes);
        if (!bigEndian) {
            //reverse byte order
            for (int i = 0; i < 4; i++) {
                byte tmp = bytes[i];
                bytes[i] = bytes[7 - i];
                bytes[7 - i] = tmp;
            }
        }
        BigInteger bint = new BigInteger(bytes);

        return bint;
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @SuppressWarnings("deprecation")
    @Override
    public String readLine() throws IOException {
        return input.readLine();
    }

    @Override
    public String readUTF() throws IOException {
        return input.readUTF();
    }

    public byte[] readRawdata() throws IOException {
        int size = input.readUnsignedShort();
        byte b[] = new byte[size];
        readFully(b);
        return b;
    }

    public String readPlaintext() throws IOException {
        byte[] data = readRawdata();
        return new String(data, "UTF-8");
    }

    public IMCMessage readInlineMessage() throws Exception {
        int type = readUnsignedShort();
        IMCMessage msg = IMCDefinition.getInstance().newMessage(type);
        defs.deserializeFields(msg, this);
        return msg;
    }

    public IMCMessage readMessage() throws IOException {
        resetCrc();
        long sync = input.readUnsignedShort();
        if (sync == defs.syncWord)
            setBigEndian(true);
        else if (sync == defs.swappedWord)
            setBigEndian(false);
        else
            throw new IOException("Unrecognized Sync word: " + String.format("%02X", sync));
        IMCMessage header = defs.createHeader();
        header.setValue("sync", defs.syncWord);
        defs.deserializeAllFieldsBut(header, this, "sync");
        IMCMessage message = new IMCMessage(defs.getType(header.getInteger("mgid")));
        message.setHeader((Header) header.cloneMessage(defs));
        defs.deserializeFields(message, this);
        //int myCrc = getCrc();
        readUnsignedShort(); //footer

        return message;
    }

    /**
     * @return the IMC Definitions
     */
    public IMCDefinition getImcDefinition() {
        return defs;
    }
}
