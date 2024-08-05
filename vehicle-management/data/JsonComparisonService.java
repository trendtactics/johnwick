package com.example.vehiclemanagement;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonComparisonService {
    private final AppConfig config;
    private final Logger logger;

    public JsonComparisonService(AppConfig config) {
        this.config = config;
        this.logger = LogManager.getLogger(JsonComparisonService.class);
    }

    public void compareJsonFiles() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode json1 = (ArrayNode) objectMapper.readTree(new File(config.getJson1Path()));
            ArrayNode json2 = (ArrayNode) objectMapper.readTree(new File(config.getJson2Path()));

            logger.info("JSON 1: " + json1.toString());
            logger.info("JSON 2: " + json2.toString());

            FileWriter recoveryWriter = new FileWriter(config.getRecoveryFilePath(), false);
            FileWriter logWriter = new FileWriter(config.getLogFilePath(), false);

            JsonComparisonHandler handler = new JsonComparisonHandler();
            handler.compareJson(json1, json2, recoveryWriter, logWriter);

            recoveryWriter.close();
            logWriter.close();

            // Update json1doc.json with the modified json1 content
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(config.getJson1Path()), json1);

        } catch (IOException e) {
            logger.error("Error reading JSON files", e);
        }
    }
}
