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
 * $Id:: IMCDefinition.java 389 2013-02-26 14:03:03Z zepinto@gmail.com         $:
 */

package pt.lsts.imc;

import pt.lsts.imc.def.DefaultProtocolParser;
import pt.lsts.imc.def.ProtocolDefinition;
import pt.lsts.imc.gz.MultiMemberGZIPInputStream;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * This class loads and holds an IMC definition (XML) and allows creation of
 * compatible messages (factory), parsing and serialization
 *
 * @author zp
 */
public class IMCDefinition {
    /** Logger instance. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(IMCDefinition.class);
    private static IMCDefinition instance;
    private IMCAddressResolver resolver = new IMCAddressResolver();
    private String name;
    protected IMCMessageType headerType;
    private LinkedHashMap<Integer, String> id_Abbrev = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> abbrev_Id = new LinkedHashMap<>();
    private LinkedHashMap<String, Vector<String>> subTypes = new LinkedHashMap<>();
    private String specification = null;
    protected LinkedHashMap<String, IMCMessageType> types = new LinkedHashMap<>();
    protected long syncWord;
    protected long swappedWord;
    protected String version;

    /**
     * Create a new IMCDefinition, loading the definitions from <b>f</b>
     *
     * @param f The file from where to read the definitions.
     * @throws Exception In case there are problems reading the file.
     */
    public IMCDefinition(File f) throws Exception {
        InputStream is;
        if (!f.canRead())
            is = null;
        else if (f.getName().endsWith(".gz"))
            is = new MultiMemberGZIPInputStream(new FileInputStream(f));
        else
            is = new FileInputStream(f);

        readDefs(is);
    }

    /**
     * Create a new IMCDefinition, loading the XML definitions from the given
     * {@link InputStream}
     */
    public IMCDefinition(InputStream is) throws Exception {
        readDefs(is);
    }

    /**
     * Retrieve (possibly reusing) default definitions
     *
     * @return Default IMC definitions
     */
    public static IMCDefinition getInstance() {
        return getInstance(null);
    }

    /**
     * Load the XML on the given {@link InputStream} and changes the default
     * instance to this
     *
     * @param isDefinitions {@link InputStream} holding the XML definitions
     * @return new IMCDefinition, loading the XML definitions from the given
     * {@link InputStream}
     */
    public synchronized static IMCDefinition getInstance(InputStream isDefinitions) {
        try {
            if (isDefinitions != null) {
                instance = new IMCDefinition(isDefinitions);
                return instance;
            }
            if (instance == null) {
                instance = new IMCDefinition(new ByteArrayInputStream(
                        ImcStringDefs.getDefinitions().getBytes("UTF-8")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * Retrieve the header length in this IMC version
     *
     * @return the header length in this IMC version
     */
    public int headerLength() {
        return headerType.getComputedLength();
    }

    /**
     * Create a new header compatible with these definitions
     *
     * @return a new header compatible with these definitions
     */
    public Header createHeader() {
        return new Header(this);
    }

    public final IMCMessageType getHeaderType() {
        return headerType;
    }

    private void readDefs(InputStream is) throws Exception {
        DefaultProtocolParser parser = new DefaultProtocolParser();
        ProtocolDefinition def = parser.parseDefinitions(is);
        specification = parser.getSpecification();
        this.version = def.getVersion();
        this.syncWord = def.getSyncWord();
        this.swappedWord = (syncWord & 0xFF) << 8 | ((syncWord & 0xFF00) >> 8);
        this.name = def.getName();
        this.headerType = def.getHeader();

        for (IMCMessageType msgType : def.getMessageDefinitions()) {
            if (!msgType.isAbstract()) {
                id_Abbrev.put(msgType.getId(), msgType.getShortName());
                abbrev_Id.put(msgType.getShortName(), msgType.getId());
            }
            if (msgType.getSupertype() != null) {
                String superType = msgType.getSupertype().getShortName();
                if (!subTypes.containsKey(superType))
                    subTypes.put(superType, new Vector<>());
                subTypes.get(superType).add(msgType.getShortName());
            }
            types.put(msgType.getShortName(), msgType);
        }
    }

    /**
     * Retrieve the message type of given message id
     *
     * @param id a (numeric) message id
     * @return The corresponding {@link IMCMessageType} or <strong>null</strong>
     * if that type was not found
     */
    public final IMCMessageType getType(Integer id) {
        return types.get(id_Abbrev.get(id));
    }

    /**
     * Retrieve the message type of given message abbreviated name
     *
     * @param name the abbreviated name of the wanted message type
     * @return The corresponding {@link IMCMessageType} or <strong>null</strong>
     * if that type was not found
     */
    public final IMCMessageType getType(String name) {
        return types.get(name);
    }

    /**
     * Retrieve the Synchronization word of this IMC definition
     *
     * @return Synchronization word of this IMC definition
     */
    public long getSyncWord() {
        return syncWord;
    }

    /**
     * Retrieve the swapped (little endian) synchronization word of this IMC
     * definition
     *
     * @return Swapped synchronization word of this IMC definition
     */
    public long getSwappedWord() {
        return swappedWord;
    }

    /**
     * Retrieve the version found on these definitions' XML
     */
    public String version() {
        return getVersion();
    }

    /**
     * Retrieve the version found on these definitions' XML
     */
    public String getVersion() {
        return version;
    }

    public String name() {
        return getName() + " v" + getVersion();
    }

    /**
     * Retrieve the definitions' name found on the XML
     *
     * @return the definitions' name found on the XML
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieve the definitions' author names found on the XML
     */
    public String getAuthor() {
        return "Porto University - LSTS";
    }

    /**
     * @return the specification
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * Create and retrieve an IMCMessage that is serialized in an array of bytes
     *
     * @param data Where to read the message from
     * @return The deserialized IMCMessage
     * @throws IOException In case of a buffer underflow
     */
    public IMCMessage parseMessage(byte[] data) throws IOException {
        try {
            return nextMessage(new IMCInputStream(
                    new ByteArrayInputStream(data), this));
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Retrieve the next message from the buffer
     *
     * @param buff The buffer where to read the message from
     * @return The next message in the buffer
     * @throws Exception if end of the buffer is reached
     */
    public IMCMessage nextMessage(ByteBuffer buff) throws Exception {

        Header header = createHeader();
        long sync = buff.getShort() & 0xFFFF;
        if (sync == swappedWord)
            buff.order(ByteOrder.LITTLE_ENDIAN);
        else if (sync == syncWord)
            header.setValue("sync", syncWord);
        else {
            LOG.debug("message with invalid sync {} was discarded\n", String.format("%04X", sync));
            byte[] tmp = new byte[header.getInteger("size") + 2];
            buff.get(tmp);
            return nextMessage(buff);
        }

        deserializeAllFieldsBut(header, buff, "sync");
        IMCMessageType type = getType(getMessageName(header.get_mgid()));
        if (type != null) {
            IMCMessage message = MessageFactory
                    .getInstance()
                    .createTypedMessage(getMessageName(header.get_mgid()), this);
            message.setHeader(header);
            message.setType(type);
            deserializeFields(message, buff);
            deserialize(IMCFieldType.TYPE_UINT16, buff); // footer
            return message;
        } else {
            LOG.debug("message with unknown id was discarded: {}", header.getInteger("mgid"));
            byte[] tmp = new byte[header.getInteger("size") + 2];
            buff.get(tmp);
            return nextMessage(buff);
        }
    }

    /**
     * Retrieve the next message from the given IMCInputStream
     *
     * @param input where to read the message from
     * @return The next message in the input
     * @throws IOException In case of any IO error (like end of input)
     */
    private IMCMessage nextMessage(IMCInputStream input) throws IOException {
        Header header = createHeader();
        input.resetCrc();

        // Let us try to check if the first byte could be the synch number
        // This avoids desynchronization if we are reading a continuous stream with errors
        long syncFirstByte = input.readUnsignedByte();
        if (!(syncFirstByte == ((syncWord & 0xFF00) >> 8)
                || syncFirstByte == ((swappedWord & 0xFF00) >> 8))) {
            // If we are here probably this is not a synch word
            if (input.available() == 0 && syncFirstByte == 0xFF)
                return null;
            else
                throw new IOException(String.format("Unrecognized first sync byte %02X",  syncFirstByte));
        }

        long sync = ((syncFirstByte & 0xFF) << 8) + input.readUnsignedByte();
        if (sync == syncWord)
            input.setBigEndian(true);
        else if (sync == swappedWord)
            input.setBigEndian(false);
        else
            throw new IOException(String.format("Unrecognized sync word: %02X", sync));

        header.setValue("sync", syncWord);

        deserializeAllFieldsBut(header, input, "sync");
        int msgid = header.getInteger("mgid");
        if (msgid != -1) {
            IMCMessage message = MessageFactory.getInstance()
                    .createTypedMessage(getMessageName(msgid), this);
            message.setHeader(header);
            deserializeFields(message, input);
            deserialize(IMCFieldType.TYPE_UINT16, input, getMessageName(msgid)
                    + ".footer"); // footer
            return message;
        } else {
            LOG.debug("message with unknown id was discarded: {}", header.getInteger("mgid"));
            input.skip(header.getInteger("size") + 2);
            return nextMessage(input);
        }
    }

    /**
     * Retrieve the next message in the given InputStream. This is done by
     * converting the InputStream in an IMCInputStream.
     *
     * @param in Where to read the message from
     * @return The next message in the input
     * @throws IOException IO errors
     */
    public IMCMessage nextMessage(InputStream in) throws IOException {
        return nextMessage(new IMCInputStream(in, this));
    }

    private Object deserialize(IMCFieldType type, DataInput in, String context)
            throws IOException {
        switch (type) {
            case TYPE_UINT8:
                return 0xFF & in.readByte();
            case TYPE_UINT16:
                return 0xFFFF & in.readShort();
            case TYPE_UINT32:
                return 0xFFFFFFFFL & in.readInt();
            case TYPE_INT8:
                return in.readByte();
            case TYPE_INT16:
                return in.readShort();
            case TYPE_INT32:
                return in.readInt();
            case TYPE_INT64:
                return in.readLong();
            case TYPE_FP32:
                return in.readFloat();
            case TYPE_FP64:
                return in.readDouble();
            case TYPE_RAWDATA:
                int size = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in, context);
                byte[] data = new byte[size];
                in.readFully(data);
                return data;
            case TYPE_PLAINTEXT:
                int l = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in, context);
                byte[] d = new byte[l];
                in.readFully(d);
                return new String(d, "UTF-8");
            case TYPE_MESSAGE:
                int t = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in, context);
                if (t == 65535)
                    return null;

                if (getType(t) == null)
                    throw new IOException("Inline message has an unknown type: "
                            + t + " (" + context + ")");

                IMCMessage message = MessageFactory.getInstance()
                        .createTypedMessage(getMessageName(t), this);
                deserializeFields(message, in);
                return message;
            case TYPE_MESSAGELIST:
                Vector<IMCMessage> vec = new Vector<>();
                int numMessages = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in, context);
                for (int i = 0; i < numMessages; i++) {
                    int mgid = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in, context + "[" + i + "]");
                    if (mgid == 65535)
                        vec.add(null);
                    else {
                        if (getType(mgid) == null)
                            throw new IOException(
                                    "Message in message-list has an unknown type: "
                                            + mgid + " (" + context + ")");
                        IMCMessage m = MessageFactory.getInstance()
                                .createTypedMessage(getMessageName(mgid), this);
                        deserializeFields(m, in);
                        vec.add(m);
                    }

                }
                return vec;
        }
        return null;
    }

    private Object deserialize(IMCFieldType type, ByteBuffer in)
            throws IOException {
        switch (type) {
            case TYPE_UINT8:
                return 0xFF & in.get();
            case TYPE_UINT16:
                return 0xFFFF & in.getShort();
            case TYPE_UINT32:
                return 0xFFFFFFFFL & in.getInt();
            case TYPE_INT8:
                return in.get();
            case TYPE_INT16:
                return in.getShort();
            case TYPE_INT32:
                return in.getInt();
            case TYPE_INT64:
                return in.getLong();
            case TYPE_FP32:
                return in.getFloat();
            case TYPE_FP64:
                return in.getDouble();
            case TYPE_RAWDATA:
                int size = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in);
                byte[] data = new byte[size];
                in.get(data);
                return data;
            case TYPE_PLAINTEXT:
                int l = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in);
                byte[] d = new byte[l];
                in.get(d);
                return new String(d, "UTF-8");
            case TYPE_MESSAGE:
                int t = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in);
                if (t == 65535)
                    return null;
                IMCMessage message = new IMCMessage(this, getType(t));
                deserializeFields(message, in);
                return message;
            case TYPE_MESSAGELIST:
                Vector<IMCMessage> vec = new Vector<>();
                int numMessages = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in);
                for (int i = 0; i < numMessages; i++) {
                    int mgid = (Integer) deserialize(IMCFieldType.TYPE_UINT16, in);
                    if (mgid == 65535)
                        vec.add(null);
                    else {
                        if (getType(mgid) == null)
                            throw new IOException(
                                    "Message in message-list has an unknown type: "
                                            + mgid);
                        IMCMessage m = MessageFactory.getInstance()
                                .createTypedMessage(getMessageName(mgid), this);
                        deserializeFields(m, in);
                        vec.add(m);
                    }
                }
                return vec;
        }
        return null;
    }

    public void deserializeFields(IMCMessage message, DataInput in)
            throws IOException {
        for (String field : message.getMessageType().getFieldNames()) {

            Object o = deserialize(
                    message.getMessageType().getFieldType(field), in,
                    message.getAbbrev() + "." + field);
            if (o instanceof IMCMessage) {
                for (String f : new String[]{"src", "dst", "src_ent",
                        "dst_ent"})
                    ((IMCMessage) o).getHeader().setValue(f,
                            message.getHeader().getValue(f));

                ((IMCMessage) o).setTimestamp(message.getTimestamp());
            }
            message.setValue(field, o);
        }
    }

    private void deserializeFields(IMCMessage message, ByteBuffer in)
            throws IOException {
        for (String field : message.getMessageType().getFieldNames()) {
            Object o = deserialize(
                    message.getMessageType().getFieldType(field), in);
            if (o instanceof IMCMessage) {

                for (String f : new String[]{"src", "dst", "src_ent",
                        "dst_ent"})
                    ((IMCMessage) o).getHeader().setValue(f,
                            message.getHeader().getValue(f));

                ((IMCMessage) o).setTimestamp(message.getTimestamp());
            }
            message.setValue(field, o);
        }
    }

    protected void deserializeAllFieldsBut(IMCMessage message, DataInput in, String fieldToSkip) throws IOException {
        for (String field : message.getMessageType().getFieldNames()) {
            if (field.equals(fieldToSkip))
                continue;

            message.setValue(
                    field,
                    deserialize(message.getMessageType().getFieldType(field),
                            in, message.getAbbrev() + "." + field));
        }
    }

    private void deserializeAllFieldsBut(IMCMessage message, ByteBuffer in, String fieldToSkip) throws IOException {
        for (String field : message.getMessageType().getFieldNames()) {
            if (field.equals(fieldToSkip))
                continue;
            message.setValue(
                    field,
                    deserialize(message.getMessageType().getFieldType(field),
                            in));
        }
    }

    int serialize(Number value, IMCFieldType type, IMCOutputStream out)
            throws IOException {
        switch (type) {
            case TYPE_UINT8:
            case TYPE_INT8:
                out.write(value.byteValue());
                return 1;
            case TYPE_UINT16:
            case TYPE_INT16:
                out.writeShort(value.shortValue());
                return 2;
            case TYPE_UINT32:
            case TYPE_INT32:
                out.writeInt(value.intValue());
                return 4;
            case TYPE_INT64:
                out.writeLong(value.longValue());
                return 8;
            case TYPE_FP32:
                out.writeFloat(value.floatValue());
                return 4;
            case TYPE_FP64:
                out.writeDouble(value.doubleValue());
                return 8;
            default:
                break;
        }
        return 0;
    }

    protected int serialize(Object value, IMCFieldType type, IMCOutputStream out)
            throws IOException {
        switch (type) {
            case TYPE_PLAINTEXT:
            case TYPE_RAWDATA:
                try {
                    if (value instanceof String)
                        value = value.toString().getBytes("UTF-8");

                    if (value instanceof byte[]) {
                        byte[] d = (byte[]) value;
                        serialize(d.length, IMCFieldType.TYPE_UINT16, out);
                        out.write(d);
                        return 2 + d.length;
                    }
                    if (value == null)
                        throw new Exception(
                                "Trying to serialize a null value as a RAWDATA type");
                    else
                        throw new Exception("Trying to serialize a "
                                + value.getClass().getSimpleName()
                                + " as a RAWDATA type");
                } catch (Exception e) {
                    return serialize(0, IMCFieldType.TYPE_UINT16, out);
                }
            case TYPE_MESSAGE:
                try {
                    if (value instanceof IMCMessage) {
                        IMCMessage inner = (IMCMessage) value;
                        serialize(inner.getMessageType().getId(),
                                IMCFieldType.TYPE_UINT16, out);
                        return 2 + serializeFields(inner, out);
                    } else if (value == null)
                        return serialize(65535, IMCFieldType.TYPE_UINT16, out);
                    else {
                        throw new Exception("Inline Message " + value
                                + " is not valid");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case TYPE_MESSAGELIST:
                try {
                    if (value == null) {
                        return serialize(0, IMCFieldType.TYPE_UINT16, out);
                    } else if (value instanceof Collection<?>) {
                        Collection<?> collection = (Collection<?>) value;
                        int count = serialize(collection.size(),
                                IMCFieldType.TYPE_UINT16, out);

                        for (Object o : collection) {
                            if (o instanceof IMCMessage) {
                                IMCMessage inner = (IMCMessage) o;
                                serialize(inner.getMessageType().getId(),
                                        IMCFieldType.TYPE_UINT16, out);
                                count += 2 + serializeFields(inner, out);
                            } else
                                count += serialize(65535, IMCFieldType.TYPE_UINT16,
                                        out);
                        }
                        return count;
                    } else if (value.getClass().isArray()) {
                        int numMsgs = Array.getLength(value);
                        int count = serialize(numMsgs, IMCFieldType.TYPE_UINT16,
                                out);

                        for (int i = 0; i < numMsgs; i++) {
                            Object o = Array.get(value, i);
                            if (o instanceof IMCMessage) {
                                IMCMessage inner = (IMCMessage) o;
                                serialize(inner.getMessageType().getId(),
                                        IMCFieldType.TYPE_UINT16, out);
                                count += 2 + serializeFields(inner, out);
                            } else {
                                count += serialize(65535, IMCFieldType.TYPE_UINT16,
                                        out);
                            }
                        }
                        return count;
                    } else {
                        throw new Exception("The value of type "
                                + value.getClass().getSimpleName()
                                + " is not valid for a message-list field.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            default:
                break;
        }
        if (value == null)
            value = 0;
        if (value instanceof Number)
            return serialize((Number) value, type, out);

        return 0;
    }

    public int serializeFields(IMCMessage message, IMCOutputStream out)
            throws IOException {
        int count = 0;
        for (String field : message.getMessageType().getFieldNames()) {
            count += serialize(message.getValue(field), message
                    .getMessageType().getFieldType(field), out);
        }
        return count;
    }

    /**
     * Verify if a message with given abbreviated name exists in these
     * definitions
     *
     * @param name The name to be searched for
     * @return Whether that message exists in this definition or not
     */
    private boolean messageExists(String name) {
        return types.containsKey(name);
    }

    public int getMessageId(String name) {
        return abbrev_Id.getOrDefault(name, -1);
    }

    public String getMessageName(int id) {
        return id_Abbrev.get(id);
    }

    public Collection<String> getMessageNames() {
        return types.keySet();
    }

    public Collection<String> getConcreteMessages() {
        return id_Abbrev.values();
    }

    public IMCMessage newMessage(int id) {
        return MessageFactory.getInstance().createTypedMessage(
                getMessageName(id), this);
    }

    public IMCMessage newMessage(String name) {
        int id = getMessageId(name);
        return newMessage(id);
    }

    public IMCMessage replicate(IMCMessage msg) {
        IMCMessage m = create(msg.getAbbrev());
        m.getHeader().setValues(msg.getHeader().values);
        m.setValues(msg.values);

        return m;
    }

    /**
     * Create a new message and fills it with given values
     *
     * @param name   The abbreviated name of the message to be created
     * @param values A list of pairs &lt;field,value&gt; for initializing the
     *               message. Example:
     *               <p>
     *               <pre>
     *                                                         IMCMessage estimatedState = new IMCMessage(&quot;EstimatedState&quot;, &quot;x&quot;, 10.0, &quot;lat&quot;,
     *                                                         		0.71, &quot;ref&quot;, &quot;NED&quot;);
     *                                                         </pre>
     * @return The created message or <strong>null</strong> if the given name is
     * not valid
     */
    public IMCMessage create(String name, Object... values) {
        if (!messageExists(name))
            return null;
        IMCMessage m = MessageFactory.getInstance().createTypedMessage(name,
                this);

        if (m.getMgid() == 65535)
            m = new IMCMessage(getType(name));

        m.definitions = this;

        for (int i = 0; i < values.length - 1; i += 2)
            m.setValue(values[i].toString(), values[i + 1]);
        return m;
    }

    public <T extends IMCMessage> T create(Class<T> clazz, Object... values) {
        try {
            T m = clazz.getConstructor().newInstance();
            for (int i = 0; i < values.length - 1; i += 2)
                m.setValue(values[i].toString(), values[i + 1]);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    IMCMessageType createDummyType() {
        IMCMessageType type = new IMCMessageType();
        type.setId(65535);
        type.setShortName("null");
        type.setFullName("null");
        return type;
    }

    /**
     * Retrieve this definition's address resolver
     *
     * @return This definition's address resolver
     */
    public final IMCAddressResolver getResolver() {
        return resolver;
    }
}
