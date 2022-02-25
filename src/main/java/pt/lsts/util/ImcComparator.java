package pt.lsts.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import pt.lsts.imc.IMCDefinition;
import pt.lsts.imc.IMCMessage;
import pt.lsts.imc.IMCUtil;

/**
 * This class compares two IMC.xml definitions and checks for incompatibilities
 */
public class ImcComparator {

    public static void compare(IMCDefinition defs1, String name1, IMCDefinition defs2, String name2) throws Exception {

        if (defs1.getSyncWord() != defs2.getSyncWord())
            throw new Exception("Synch words do not match: "+defs1.getSyncWord()+" vs "+defs2.getSyncWord());

        HashSet<String> checkedMessages = new HashSet<>();

        for (String msg : defs1.getMessageNames()) {
            int id = defs1.getMessageId(msg);            
            String abbrev2 = defs2.getMessageName(id); 
            if (abbrev2 != null && !abbrev2.equals(msg)) {
                System.err.println(msg+" id ("+id+") corresponds to a different message in "+name2+" protocol: "+abbrev2);
            }
            IMCMessage message1 = defs1.create(msg);
            IMCUtil.fillWithRandomData(message1);
            IMCMessage message2 = defs2.create(msg);            
            if (message2 == null) {
                System.err.println(msg+" does not exist in "+name2+" protocol.");
            }
            else {
                message2.copyFrom(message1);
                if (message2.getMgid() != message1.getMgid()) {
                    System.err.println(msg+" has conflicting IDs.");
                }
                String[] json1 = message1.asJSON(true).split("\n");
                String[] json2 = message2.asJSON(true).split("\n");
                for (int i = 0; i < json1.length; i++) {
                    if (!json1[i].equals(json2[i])) {
                        //System.out.println("   "+json1[i-1]);
                        System.out.println(" > "+json1[i]+" vs "+json2[i]);
                        //System.out.println("   "+json1[i+1]);
                        break;
                    }
                }

                if (!message1.asJSON(true).equals(message2.asJSON(true)))
                    throw new Exception("Mismatched serializaton of message "+msg+".");
                }
                checkedMessages.add(msg);
        }

        for (String msg : defs2.getMessageNames()) {
            if (checkedMessages.contains(msg))
                continue;
            
            int id = defs2.getMessageId(msg);            
            String abbrev1 = defs1.getMessageName(id); 
            if (abbrev1 != null && !abbrev1.equals(msg)) {
                System.err.println(msg+" id ("+id+") corresponds to a different message in "+name1+" protocol: "+abbrev1);
            }
            IMCMessage message2 = defs2.create(msg);
            IMCMessage message1 = defs1.create(msg);
            if (message1 == null) {
                System.err.println(msg+" does not exists in "+name1+" protocol.");
            }
            else {
                IMCUtil.fillWithRandomData(message2);
                message1.copyFrom(message2);
                String[] json1 = message1.asJSON(true).split("\n");
                String[] json2 = message2.asJSON(true).split("\n");
                
                for (int i = 0; i < json1.length; i++) {
                    if (!json1[i].equals(json2[i])) {
                        //System.out.println("   "+json1[i-1]);
                        System.out.println(" > "+json1[i]+" vs "+json2[i]);
                        //System.out.println("   "+json1[i+1]);
                        break;
                    }
                }
                if (!message1.asJSON(true).equals(message2.asJSON(true)))
                    throw new Exception("Mismatched serializaton of message "+msg+".");
                }
        }
    }

    public static void main(String[] args) throws Exception {
        IMCDefinition defsOMST, defsUnify;
        defsOMST = new IMCDefinition(new File("/home/zp/workspace/IMCs/omst/IMC.xml"));
        defsUnify = new IMCDefinition(new File("/home/zp/workspace/IMCs/lsts-unify/IMC.xml"));

        ImcComparator.compare(defsOMST, "OMST",  defsUnify, "LSTS");
    }    
}
