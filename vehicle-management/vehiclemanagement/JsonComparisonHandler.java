package com.example.vehiclemanagement;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JsonComparisonHandler {
    private static final Logger logger = LogManager.getLogger(JsonComparisonHandler.class);

    public void compareJson(JsonNode json1, JsonNode json2, FileWriter recoveryWriter, FileWriter logWriter) throws IOException {
        compareJsonNodes(json1, json2, recoveryWriter, logWriter, false);
        compareJsonNodes(json2, json1, recoveryWriter, logWriter, true);
        logWriter.flush(); // Ensure the data is written
        recoveryWriter.flush(); // Ensure the data is written
    }

    private void compareJsonNodes(JsonNode sourceJson, JsonNode targetJson, FileWriter recoveryWriter, FileWriter logWriter, boolean reverseComparison) throws IOException {
        Iterator<JsonNode> sourceElements = sourceJson.elements();
        while (sourceElements.hasNext()) {
            JsonNode sourceNode = sourceElements.next();
            String id = sourceNode.get("id").asText();
            JsonNode targetNode = findNodeById(targetJson, id);

            if (targetNode == null) {
                if (!reverseComparison) {
                    logWriter.write("Record removed: " + sourceNode.toString() + "\n");
                    logger.info("Record removed: " + sourceNode.toString());
                }
            } else if (!sourceNode.equals(targetNode)) {
                String status = reverseComparison ? "I" : "U";
                recoveryWriter.write("{\"key\":\"" + id + "\",\"value\":" + targetNode.toString() + ",\"status\":\"" + status + "\"}\n");
                logWriter.write("Record " + (reverseComparison ? "inserted" : "updated") + ": " + sourceNode.toString() + " -> " + targetNode.toString() + "\n");
                logger.info("Record " + (reverseComparison ? "inserted" : "updated") + ": " + sourceNode.toString() + " -> " + targetNode.toString());
            }
        }
    }

    private JsonNode findNodeById(JsonNode jsonArray, String id) {
        Iterator<JsonNode> elements = jsonArray.elements();
        while (elements.hasNext()) {
            JsonNode node = elements.next();
            if (node.get("id").asText().equals(id)) {
                return node;
            }
        }
        return null;
    }
}
