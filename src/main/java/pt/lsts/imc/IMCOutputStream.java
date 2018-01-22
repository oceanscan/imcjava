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

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class IMCOutputStream implements DataOutput {
    private int crc = 0;
    private long count = 0;
    private DataOutputStream output;
    private boolean bigEndian = false;
    protected IMCDefinition def;

    public IMCOutputStream(IMCDefinition def, OutputStream out) {
        output = new DataOutputStream(out);
        this.def = def;
    }

    public IMCOutputStream(OutputStream out) {
        output = new DataOutputStream(out);
    }

    public void resetCRC() {
        crc = 0;
    }

    public int getCRC() {
        return crc;
    }

    public long getCount() {
        return count;
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(int b) throws IOException {
        crc = (crc >> 8) ^ IMCUtil.crc16_table[(crc ^ b) & 0xff];
        output.write(b);
        count++;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (int i = off; i < len; i++)
            crc = (crc >> 8) ^ IMCUtil.crc16_table[(crc ^ b[i]) & 0xff];
        output.write(b, off, len);
        count += len;
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        output.writeBoolean(v);
    }

    @Override
    public void writeByte(int v) throws IOException {
        write(v);
    }

    @Override
    public void writeBytes(String s) throws IOException {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            write((byte) s.charAt(i));
        }
    }

    @Override
    public void writeDouble(double v) throws IOException {
        long l = Double.doubleToLongBits(v);
        writeLong(l);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        int i = Float.floatToIntBits(v);
        writeInt(i);
    }

    @Override
    public void writeUTF(String s) throws IOException {
        throw new IOException("Not implemented");
    }

    @Override
    public void writeChar(int v) throws IOException {
        write((v >>> 8) & 0xFF);
        write((v >>> 0) & 0xFF);
    }

    @Override
    public void writeChars(String s) throws IOException {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int v = s.charAt(i);
            write((v >>> 8) & 0xFF);
            write((v >>> 0) & 0xFF);
        }
    }

    @Override
    public void writeInt(int v) throws IOException {
        if (!bigEndian)
            v = Integer.reverseBytes(v);
        write((v >>> 24) & 0xFF);
        write((v >>> 16) & 0xFF);
        write((v >>> 8) & 0xFF);
        write((v >>> 0) & 0xFF);
    }

    private byte writeBuffer[] = new byte[8];

    @Override
    public void writeLong(long v) throws IOException {
        if (!bigEndian)
            v = Long.reverseBytes(v);

        writeBuffer[0] = (byte) (v >>> 56);
        writeBuffer[1] = (byte) (v >>> 48);
        writeBuffer[2] = (byte) (v >>> 40);
        writeBuffer[3] = (byte) (v >>> 32);
        writeBuffer[4] = (byte) (v >>> 24);
        writeBuffer[5] = (byte) (v >>> 16);
        writeBuffer[6] = (byte) (v >>> 8);
        writeBuffer[7] = (byte) (v >>> 0);
        write(writeBuffer, 0, 8);
    }

    @Override
    public void writeShort(int v) throws IOException {
        if (!bigEndian)
            v = Short.reverseBytes((short) v);
        write((v >>> 8) & 0xFF);
        write((v >>> 0) & 0xFF);
    }

    public void writeMessage(IMCMessage message) throws IOException {
        message.serialize(this);
    }

    public void close() throws IOException {
        output.close();
    }
}
