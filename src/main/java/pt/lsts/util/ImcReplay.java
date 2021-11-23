package pt.lsts.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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

        int messageIds[] = new int[imcMessages.length];

        for (int i = 0; i < imcMessages.length; i++) {
            messageIds[i] = index.getDefinitions().getMessageId(imcMessages[i]);
        }

        for (int m = 0; m < index.getNumberOfMessages(); m++) {
            int mid = index.typeOf(m);
            if (Arrays.binarySearch(messageIds, mid) >= 0) {
                IMCMessage message = index.getMessage(m);
                message.setTimestamp((Math.max(0, message.getTimestamp() - startTime)));
                String json = message.asJSON(); 
                System.out.println(json+"\n");
                writer.write(json+"\n");
            }
        }

        writer.close();
    }

    public static void main(String[] args) throws Exception {
        LsfToJsonReplay(Paths.get("/home/zp/Desktop/logs/160213_before-lever-arms-2/Data.lsf"), "CpuUsage", "EstimatedState");
    }
    
}
