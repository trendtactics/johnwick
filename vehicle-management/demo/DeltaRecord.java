package com.example.vehiclemanagement.demo;

import java.util.Map;

public class DeltaRecord {
    public String CSPA;
    public String layout_name;
    public Map<String, String> changes;
    public String timestamp;

    public String getLayout_name() {
        return layout_name;
    }

    public void setLayout_name(String layout_name) {
        this.layout_name = layout_name;
    }

    public String getCSPA() { return CSPA; }
    public void setCSPA(String CSPA) { this.CSPA = CSPA; }

    public String getLayoutName() { return layout_name; }
    public void setLayoutName(String layout_name) { this.layout_name = layout_name; }

    public Map<String, String> getChanges() { return changes; }
    public void setChanges(Map<String, String> changes) { this.changes = changes; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}