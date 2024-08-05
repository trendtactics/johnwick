package com.example.vehiclemanagement;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonComparisonApp {
    private static final Logger logger = LogManager.getLogger(JsonComparisonApp.class);

    public static void main(String[] args) {
        try {
            AppConfig config = new AppConfig();
            JsonComparisonService comparisonService = new JsonComparisonService(config);
            comparisonService.compareJsonFiles();
        } catch (Exception e) {
            logger.error("Application error", e);
        }
    }
}
