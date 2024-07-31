package com.example.vehiclemanagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            JsonNode json1 = objectMapper.readTree(new File(config.getJson1Path()));
            JsonNode json2 = objectMapper.readTree(new File(config.getJson2Path()));

            logger.info("JSON 1: " + json1.toString());
            logger.info("JSON 2: " + json2.toString());

            try (FileWriter recoveryWriter = new FileWriter(config.getRecoveryFilePath());
                 FileWriter logWriter = new FileWriter(config.getLogFilePath())) {
                JsonComparisonHandler handler = new JsonComparisonHandler();
                handler.compareJson(json1, json2, recoveryWriter, logWriter);
            }
        } catch (IOException e) {
            logger.error("Error reading JSON files", e);
        }
    }
}
