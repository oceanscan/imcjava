package pt.lsts.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

import pt.lsts.imc.IMCMessage;
import pt.lsts.imc.lsf.LsfIndex;

public class ImcReplay {

    int messageIds[];
    int entityIds[];
    int sourceIds[];
    LsfIndex index;

    public static void LsfToJsonReplay(Path lsfFile, String... imcMessages) throws Exception {
        LsfIndex index = new LsfIndex(lsfFile.toFile());
        
        File parentDir = index.getLsfFile().getParentFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(parentDir, "replay.lsf")));
        double startTime = index.getMessage(0).getTimestamp();
        HashSet<Integer> messageIds = new HashSet<>();
        
        for (int i = 0; i < imcMessages.length; i++) {
            messageIds.add(index.getDefinitions().getMessageId(imcMessages[i]));
        }
        System.out.print("[");
        writer.write("[");
        boolean firstMessage = true;

        for (int m = 0; m < index.getNumberOfMessages(); m++) {
            int mid = index.typeOf(m);
            if (messageIds.contains(mid)) {
                IMCMessage message = index.getMessage(m);
                message.setTimestamp((Math.max(0, message.getTimestamp() - startTime)));
                String out = "\n";
                if (!firstMessage)
                    out = ",\n";                                    
                firstMessage = false;
                out += message.asJSON(); 
                
                System.out.print(out);
                writer.write(out);
            }
        }
        System.out.print("\n]\n");
        writer.write("\n]\n");
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        LsfToJsonReplay(Paths.get("/home/zp/Desktop/logs/160213_before-lever-arms-2/Data.lsf"), "PlanControl", "PlanSpecification");
    }
    
}
