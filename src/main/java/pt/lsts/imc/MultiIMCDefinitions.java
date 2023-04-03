package pt.lsts.imc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;



public class MultiIMCDefinitions {

    private final static Logger LOG = Logger.getLogger("MultiIMCDefinitions");
    private static LinkedHashMap<Long, IMCDefinition> alternativeDefinitions = null;
    private final static LinkedHashMap<Integer, IMCDefinition> definitionsBySystem = new LinkedHashMap<>();
    private final static IMCDefinition defaultDefinitions = IMCDefinition.getInstance();

    public static synchronized IMCDefinition getInstance(long syncWord) {
        if (alternativeDefinitions == null)
            loadAlternativeDefinitions();
        if (alternativeDefinitions.containsKey(syncWord))
            return alternativeDefinitions.get(syncWord);
        return IMCDefinition.getInstance();
    }

    public static synchronized IMCDefinition getDefinitionsForSystem(int sys_id) {
        if (alternativeDefinitions == null)
            loadAlternativeDefinitions();
        if (definitionsBySystem.containsKey(sys_id))
            return definitionsBySystem.get(sys_id);
        else
        {
            LOG.fine("Using default definitions for system "+sys_id);
            return IMCDefinition.getInstance();
        }            
    }

    public static String resolveSystemName(int sysId) {
        return getDefinitionsForSystem(sysId).getResolver().resolve(sysId);
    }

    public static synchronized IMCDefinition getInstance() {
        return defaultDefinitions;
    }

   
    public synchronized static HashSet<IMCDefinition> getAllDefinitions() {
        if (alternativeDefinitions == null)
            loadAlternativeDefinitions();
        return new HashSet<>(alternativeDefinitions.values());
    }

    public static pt.lsts.imc.IMCMessage nextMessage(java.io.InputStream in) throws java.io.IOException {
        PushbackInputStream pb = new PushbackInputStream(in,2);
        byte[] syncBuf = new byte[2];
        pb.read(syncBuf);
        long sync = syncBuf[0] & 0xFF | (syncBuf[1] & 0xFF) << 8;
        IMCDefinition def = getInstance(sync);
        boolean swapped = def.getSwappedWord() == sync;
        pb.unread(syncBuf);        
        IMCInputStream iis = new IMCInputStream(pb, def);
        if (swapped)
            iis.setBigEndian(false);
        return def.nextMessage(iis);
    }

    public static IMCMessage parseMessage(byte[] data) throws IOException {
        long sync = (data[0] & 0xFF) | ((data[1] & 0xFF) << 8);
        if (alternativeDefinitions == null)
            loadAlternativeDefinitions();
        if (!alternativeDefinitions.containsKey(sync)) {
            LOG.warning("Sync Word not recognized: " + sync);
            return null;
        }
        IMCMessage msg = alternativeDefinitions.get(sync).parseMessage(data);
        if (msg != null)
            definitionsBySystem.put(msg.getSrc(), alternativeDefinitions.get(sync));

        return msg;
    }

    public static byte[] serializeMessage(IMCMessage msg, long syncWord) throws Exception {
        IMCDefinition def = alternativeDefinitions.get(syncWord);
        if (def == null)
            def = IMCDefinition.getInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IMCOutputStream ios = new IMCOutputStream(def, baos);
        def.serialize(msg, ios);
        return baos.toByteArray();
    }

    public static byte[] serializeMessage(IMCMessage msg) throws Exception {
        IMCDefinition def = definitionsBySystem.get(msg.getSrc());
        if (def == null)
            def = IMCDefinition.getInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IMCOutputStream ios = new IMCOutputStream(def, baos);
        def.serialize(msg, ios);
        return baos.toByteArray();
    }

    public static synchronized void loadAlternativeDefinitions() {

        if (alternativeDefinitions == null) {
            alternativeDefinitions = new LinkedHashMap<>();

            alternativeDefinitions.put(IMCDefinition.getInstance().getSyncWord(), IMCDefinition.getInstance());
            alternativeDefinitions.put(IMCDefinition.getInstance().getSwappedWord(), IMCDefinition.getInstance());

            // try to load other IMCDefinitions
            File imcDefs = new File("conf/imc");
            if (!imcDefs.exists() || !imcDefs.isDirectory()) {
                LOG.warning("Could not load any alternative IMC definitions from 'conf/imc' folder.");
                return;
            }
            for (File f : imcDefs.listFiles()) {
                if (f.getName().endsWith("xml")) {
                    try {
                        IMCDefinition def = IMCDefinition.getInstance(new FileInputStream(f));
                        if (!alternativeDefinitions.containsKey(def.getSyncWord())) {
                            alternativeDefinitions.put(def.getSyncWord(), def);
                            alternativeDefinitions.put(def.getSwappedWord(), def);
                            LOG.info("Loaded alternative IMC definition: " + def.getSyncWord());
                        }
                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "Could not load alternative IMC definition: " + f.getName(), e);
                    }
                }
            }

            LOG.info("Loaded " + alternativeDefinitions.size() / 2 + " IMC definitions");

            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                IMCDefinition defaults = IMCDefinition.getInstance();
                Map<String,Integer> addrs = defaults.getResolver().getAddresses();
                HashSet<IMCDefinition> updated = new HashSet<>();
                updated.add(defaults);
                for (IMCDefinition def : alternativeDefinitions.values()) {
                    if (!updated.contains(def)) {
                        updated.add(def);
                        addrs.entrySet().forEach(e -> {
                            def.getResolver().addEntry(e.getValue(), e.getKey());
                        });
                        def.getResolver().getAddresses().forEach((k,v) -> {
                            defaults.getResolver().addEntry(v, k);
                        });
                    }
                }
            }, 0, 10, TimeUnit.SECONDS);            
        }
    }
}
