//package com.example.vehiclemanagement;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import java.util.List;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class SampleDataProcessor implements Processor {
//    private List<FieldDefinition> fieldDefinitions;
//
//    public SampleDataProcessor(List<FieldDefinition> fieldDefinitions) {
//        this.fieldDefinitions = fieldDefinitions;
//    }
//
//    @Override
//    public void process(Exchange exchange) throws Exception {
//        String line = exchange.getIn().getBody(String.class);
//
//        if (line.length() < 10) {
//            exchange.getIn().setBody("Record is too short: " + line);
//            return;
//        }
//
//        Map<String, String> jsonMap = new LinkedHashMap<>();
//        for (FieldDefinition fieldDefinition : fieldDefinitions) {
//            int startIndex = fieldDefinition.getStartPos();
//            int endIndex = Math.min(fieldDefinition.getEndPos(), line.length());
//            jsonMap.put(fieldDefinition.getName(), line.substring(startIndex, endIndex).trim());
//        }
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(jsonMap);
//        exchange.getIn().setBody(json);
//    }
//}
