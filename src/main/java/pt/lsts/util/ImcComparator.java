package pt.lsts.util;

import java.util.HashSet;

import pt.lsts.imc.IMCDefinition;
import pt.lsts.imc.IMCMessage;
import pt.lsts.imc.IMCUtil;

/**
 * This class compares two IMC.xml definitions and checks for incompatibilities
 */
public class ImcComparator {

    public static void compare(IMCDefinition defs1, IMCDefinition defs2) throws Exception {

        if (defs1.getSyncWord() != defs2.getSyncWord())
            throw new Exception("Synch words do not match: "+defs1.getSyncWord()+" vs "+defs2.getSyncWord());

        HashSet<String> checkedMessages = new HashSet<>();

        for (String msg : defs1.getMessageNames()) {
            IMCMessage message1 = defs1.create(msg);
            IMCUtil.fillWithRandomData(message1);
            IMCMessage message2 = defs2.create(msg);
            if (message2 == null) {
                System.err.println(msg+" does not exists in second IMC definitions.");
            }
            else {
                message2.copyFrom(message1);
                if (!message1.asJSON(true).equals(message2.asJSON(true)))
                    throw new Exception("Mismatched serializaton of message "+msg+".");
                }
        }

        for (String msg : defs2.getMessageNames()) {
            IMCMessage message2 = defs2.create(msg);
            IMCUtil.fillWithRandomData(message2);
            IMCMessage message1 = defs1.create(msg);
            if (message1 == null) {
                System.err.println(msg+" does not exists in first IMC definitions.");
            }
            else {
                message2.copyFrom(message1);
                if (!message1.asJSON(true).equals(message2.asJSON(true)))
                    throw new Exception("Mismatched serializaton of message "+msg+".");
                }
        }
    }
    
}
