package pt.lsts.util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import pt.lsts.imc.IMCDefinition;
import pt.lsts.imc.IMCMessage;
import pt.lsts.imc.IMCOutputStream;
import pt.lsts.imc.lsf.UnserializedMessage;

public class ImcConverter {

    private File log = null;
    private File xmlDefs = null;
    private IMCDefinition imcDefs = null;
    private HashSet<Integer> selectedMessages = null;
    private File outFolder = null;

    public ImcConverter() {
        selectImcDefinitions();
        selectLogFolder();
        selectMessages();
        outFolder = new File(log, "imc_" + imcDefs.getVersion());
        outFolder.mkdirs();
        copyXmlDefs();
        convert();
    }

    public void selectImcDefinitions() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select target IMC definitions (IMC.xml)");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.exit(0);
        }
        String imcFile = fileChooser.getSelectedFile().getAbsolutePath();
        try {
            System.out.println("Loading IMC definitions from " + imcFile);
            xmlDefs = new File(imcFile);
            imcDefs = new IMCDefinition(new File(imcFile));
            System.out.println("Loaded " + imcDefs.getVersion() + " / " + imcDefs.getSyncWord());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading IMC definitions: " + e.getMessage());
            System.exit(1);
        }
    }

    public void selectLogFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select log folder");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.exit(0);
        }
        String logFile = fileChooser.getSelectedFile().getAbsolutePath();
        try {
            System.out.println("Loading log from " + logFile);
            log = new File(logFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading log: " + e.getMessage());
            System.exit(1);
        }
    }

    public void selectMessages() {
        HashSet<String> availableMessages = new HashSet<>();
        imcDefs.getMessageNames().forEach(availableMessages::add);

        ArrayList<String> selectedMsgs = new ArrayList<>(availableMessages);
        String[] selectedMsgsArray = selectedMsgs.stream().filter(Objects::nonNull).sorted().toArray(String[]::new);
        String[] result = CheckboxList.selectOptions(null, true, "Messages to export",
                selectedMsgsArray);

        HashSet<Integer> selectedIds = new HashSet<>();
        for (String msg : result) {
            selectedIds.add(imcDefs.getMessageId(msg));
        }

        this.selectedMessages = selectedIds;
    }

    public void copyXmlDefs() {
        System.out.println("Converting to " + outFolder.getAbsolutePath());
        if (xmlDefs != null)
            try {
                Files.copy(xmlDefs.toPath(), new File(outFolder, xmlDefs.getName()).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error outputing XML definitions: " + e.getMessage());
                System.exit(1);
            }
    }

    public void convert() {

        DataInputStream input = null;
        IMCOutputStream ios = null;

        try {
            ios = new IMCOutputStream(imcDefs, new FileOutputStream(new File(outFolder, "Data.lsf")));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error creating output log file: " + e.getMessage());
            System.exit(1);
        }

        try {
            if (new File(log, "Data.lsf").canRead()) {
                input = new DataInputStream(new FileInputStream(new File(log, "Data.lsf")));
            } else if (new File(log, "Data.lsf.gz").canRead()) {
                input = new DataInputStream(
                        new GzipCompressorInputStream(new FileInputStream(new File(log, "Data.lsf.gz")), true));
            } else {
                JOptionPane.showMessageDialog(null, "No valid log file found in " + log.getAbsolutePath());
                System.exit(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error opening log file: " + e.getMessage());
            System.exit(1);
            return;
        }

        IMCDefinition originalDefs = null;
        try {
            if (new File(log, "IMC.xml").canRead()) {
                originalDefs = new IMCDefinition(new File(log, "IMC.xml"));
            } else if (new File(log, "IMC.xml.gz").canRead()) {
                originalDefs = new IMCDefinition(new File(log, "IMC.xml.gz"));
            } else {
                System.err.println("No IMC.xml found in log folder.");
                originalDefs = IMCDefinition.getInstance();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error opening IMC definitions file: " + e.getMessage());
            System.exit(1);
            return;
        }

        try {
            while (input.available() >= 0) {
                UnserializedMessage msg = UnserializedMessage.readMessage(originalDefs, input);
                if (selectedMessages.contains(msg.getMgId())) {
                    IMCMessage m = msg.deserialize();
                    ios.writeMessage(m, imcDefs);
                }
            }

            System.out.println("Finished conversion.");
            
            ios.close();
            input.close();                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading log file: " + e.getMessage());
            System.exit(1);
        }

        JOptionPane.showMessageDialog(null, "Conversion finished.");

    }

    public static void main(String[] args) {
        new ImcConverter();
    }

}