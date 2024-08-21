package com.example.vehiclemanagement.demo;


import java.util.Map;

public class LocalRecord {
    public String CSPA;
    public String date;
    public String version;
    public String layout_name;
    public Map<String, String> document;

    public String getLayout_name() {
        return layout_name;
    }

    public void setLayout_name(String layout_name) {
        this.layout_name = layout_name;
    }

    public String getCSPA() { return CSPA; }
    public void setCSPA(String CSPA) { this.CSPA = CSPA; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getLayoutName() { return layout_name; }
    public void setLayoutName(String layout_name) { this.layout_name = layout_name; }

    public Map<String, String> getDocument() { return document; }
    public void setDocument(Map<String, String> document) { this.document = document; }
}