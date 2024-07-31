package com.example.vehiclemanagement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppConfig {
    private final String json1Path = "src/main/resources/json1doc.json";
    private final String json2Path = "src/main/resources/json2doc.json";
    private final String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());

    public String getJson1Path() {
        return json1Path;
    }

    public String getJson2Path() {
        return json2Path;
    }

    public String getRecoveryFilePath() {
        return "src/main/resources/recovery_" + timestamp + ".json";
    }

    public String getLogFilePath() {
        return "src/main/resources/log_" + timestamp + ".txt";
    }

    public String getTimestamp() {
        return timestamp;
    }
}
