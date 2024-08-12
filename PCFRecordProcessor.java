package pcflayout;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PCFRecordProcessor {
//
//    public static void main(String[] args) {
//        List<FieldLayout> layouts = new ArrayList<>();
//        layouts.add(new FieldLayout(19, 4, "XXX-AEWS-ATTR-CD"));
//        layouts.add(new FieldLayout(33, 3, "XXX-AEWS-LTRL-TOTL-SIZE-NR"));
//        layouts.add(new FieldLayout(36, 3, "XXX-AEWS-LTRL-DCML-SIZE-NR"));
//
//        PCFParser parser = new PCFParser();
//        String record = "                    1234    567 890"; // Make sure this is long enough for all fields
//
//        ParsedRecord parsedRecord = parser.parsePCF(record, layouts);
//        System.out.println(parsedRecord);
//    }
//}


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PCFRecordProcessor {
    public static void main(String[] args) throws Exception {
        // Load the PCF layout from the resource file
        List<FieldLayout> layouts = new ArrayList<>();
        try (InputStream pcfInputStream = PCFRecordProcessor.class.getResourceAsStream("/sample_pcf.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(pcfInputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 4) {
                    int startPos = Integer.parseInt(parts[2]);
                    int length = Integer.parseInt(parts[3].substring(parts[3].indexOf('(') + 1, parts[3].indexOf(')')));
                    String name = parts[1];
                    layouts.add(new FieldLayout(startPos, length, name));
                }
            }
        }

        // Load the sample record from the resource file
        StringBuilder recordBuilder = new StringBuilder();
        try (InputStream recordInputStream = PCFRecordProcessor.class.getResourceAsStream("/sample_record.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(recordInputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                recordBuilder.append(line);
            }
        }

        String record = recordBuilder.toString();

        // Process the record
        PCFParser parser = new PCFParser();
        ParsedRecord parsedRecord = parser.parsePCF(record, layouts);
        System.out.println(parsedRecord);
    }
}
