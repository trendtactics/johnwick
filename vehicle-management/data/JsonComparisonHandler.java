package com.example.vehiclemanagement;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JsonComparisonHandler {
    private static final Logger logger = LogManager.getLogger(JsonComparisonHandler.class);

    public void compareJson(ArrayNode json1, ArrayNode json2, FileWriter recoveryWriter, FileWriter logWriter) throws IOException {
        boolean hasChanges = false;
        Iterator<JsonNode> json2Elements = json2.elements();

        while (json2Elements.hasNext()) {
            JsonNode json2Node = json2Elements.next();
            String id = json2Node.get("id").asText();
            JsonNode json1Node = findNodeById(json1, id);

            if (json1Node == null) {
                // New record, insert into json1
                json1.add(json2Node);
                logWriter.write("Record inserted: " + json2Node.toString() + "\n");
                recoveryWriter.write(json2Node.toString() + "\n");
                logger.info("Record inserted: " + json2Node.toString());
                hasChanges = true;
            } else if (!json1Node.equals(json2Node)) {
                // Existing record, update in json1
                updateJsonNode((ObjectNode) json1Node, (ObjectNode) json2Node);
                logWriter.write("Record updated: " + json1Node.toString() + " -> " + json2Node.toString() + "\n");
                recoveryWriter.write(json2Node.toString() + "\n");
                logger.info("Record updated: " + json1Node.toString() + " -> " + json2Node.toString());
                hasChanges = true;
            }
        }

        if (!hasChanges) {
            recoveryWriter.write("{}\n");
        }
        logWriter.flush(); // Ensure the data is written
        recoveryWriter.flush(); // Ensure the data is written
    }

    private JsonNode findNodeById(ArrayNode jsonArray, String id) {
        Iterator<JsonNode> elements = jsonArray.elements();
        while (elements.hasNext()) {
            JsonNode node = elements.next();
            if (node.get("id").asText().equals(id)) {
                return node;
            }
        }
        return null;
    }

    private void updateJsonNode(ObjectNode target, ObjectNode source) {
        Iterator<String> fieldNames = source.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode updatedValue = source.get(fieldName);
            target.set(fieldName, updatedValue);
        }
    }
}
