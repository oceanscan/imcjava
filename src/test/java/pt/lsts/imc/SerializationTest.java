package pt.lsts.imc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SerializationTest {

    static Stream<String> allMessageNames() {
        IMCDefinition defs = IMCDefinition.getInstance();
        Collection<String> concrete = defs.getConcreteMessages();
        return concrete.stream();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("allMessageNames")
    void serializeDeserializeChecksum(String msgName) throws Exception {
        IMCDefinition defs = IMCDefinition.getInstance();
        IMCMessage original = defs.create(msgName);
        assertNotNull(original, "Failed to create message: " + msgName);

        // Set a fixed timestamp so serialization is deterministic
        original.setTimestamp(1000000000.0);

        // Serialize
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IMCOutputStream out = new IMCOutputStream(baos);
        original.serialize(defs, out);
        byte[] serialized = baos.toByteArray();
        assertTrue(serialized.length > 0, "Serialized bytes empty for: " + msgName);

        // Compute MD5 of original
        byte[] originalMD5 = original.payloadMD5();

        // Deserialize
        IMCMessage deserialized = defs.parseMessage(serialized);
        assertNotNull(deserialized, "Failed to deserialize message: " + msgName);
        assertEquals(msgName, deserialized.getAbbrev(),
                "Message type mismatch after deserialization");

        // Compute MD5 of deserialized
        byte[] deserializedMD5 = deserialized.payloadMD5();

        // Compare checksums
        assertArrayEquals(originalMD5, deserializedMD5,
                "Payload MD5 mismatch for: " + msgName);

        // Also verify re-serialization produces identical bytes
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        IMCOutputStream out2 = new IMCOutputStream(baos2);
        deserialized.serialize(defs, out2);
        byte[] reserialized = baos2.toByteArray();

        assertArrayEquals(serialized, reserialized,
                "Re-serialized bytes differ for: " + msgName);
    }
}
