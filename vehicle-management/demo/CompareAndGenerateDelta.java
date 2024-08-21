package com.example.vehiclemanagement.demo;

import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class CompareAndGenerateDelta extends CoProcessFunction<MongoRecord, LocalRecord, DeltaRecord> {

    private Map<String, MongoRecord> mongoState = new HashMap<>();

    @Override
    public void processElement1(MongoRecord mongoRecord, Context ctx, Collector<DeltaRecord> out) {
        // Store the MongoRecord in the state for comparison when a corresponding LocalRecord arrives
        mongoState.put(mongoRecord.getCSPA(), mongoRecord);
    }

    @Override
    public void processElement2(LocalRecord localRecord, Context ctx, Collector<DeltaRecord> out) {
        // Check if there is a corresponding MongoRecord in the state
        MongoRecord mongoRecord = mongoState.get(localRecord.getCSPA());

        if (mongoRecord != null) {
            // If a MongoRecord exists, compare the records
            compareRecords(mongoRecord, localRecord, out);
        } else {
            // If no corresponding MongoRecord exists, consider this as a new record and insert it
            DeltaRecord deltaRecord = new DeltaRecord();
            deltaRecord.setCSPA(localRecord.getCSPA());
            deltaRecord.setLayoutName(localRecord.getLayoutName());
            deltaRecord.setChanges(localRecord.getDocument()); // Treat the entire document as new changes
            deltaRecord.setTimestamp(Instant.now().toString()); // Add timestamp of insertion

            out.collect(deltaRecord); // Emit the delta record for new insert
        }
    }

    private void compareRecords(MongoRecord mongoRecord, LocalRecord localRecord, Collector<DeltaRecord> out) {
        Map<String, String> changes = new HashMap<>();

        for (Map.Entry<String, String> entry : localRecord.getDocument().entrySet()) {
            String key = entry.getKey();
            String localValue = entry.getValue();
            String mongoValue = mongoRecord.getDocument().get(key);

            if (!localValue.equals(mongoValue)) {
                changes.put(key, localValue); // Add the updated value to the changes map
            }
        }

        if (!changes.isEmpty()) {
            DeltaRecord deltaRecord = new DeltaRecord();
            deltaRecord.setCSPA(localRecord.getCSPA());
            deltaRecord.setLayoutName(localRecord.getLayoutName());
            deltaRecord.setChanges(changes);
            deltaRecord.setTimestamp(Instant.now().toString()); // Add timestamp of the change

            out.collect(deltaRecord); // Emit the delta record with changes
        }
    }
}
