package pcflayout;
import pcflayout.FieldLayout;
import pcflayout.ParsedRecord;

import java.util.List;

public class PCFParser {

    public ParsedRecord parsePCF(String record, List<FieldLayout> layouts) {
        ParsedRecord parsedRecord = new ParsedRecord();

        for (FieldLayout layout : layouts) {
            int startPos = layout.getStartPosition() - 1; // Convert to zero-based index
            int endPos = Math.min(startPos + layout.getLength(), record.length()); // Ensure endPos doesn't exceed record length

            if (startPos < record.length()) {
                String fieldValue = record.substring(startPos, endPos).trim();
                parsedRecord.addField(layout.getName(), fieldValue);
            } else {
                parsedRecord.addField(layout.getName(), ""); // Add empty string if the start position is beyond the record length
            }
        }

        return parsedRecord;
    }
}
