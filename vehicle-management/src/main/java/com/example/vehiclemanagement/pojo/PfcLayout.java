package com.example.vehiclemanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PfcLayout {
    @JsonProperty("field1")
    private int field1;

    @JsonProperty("field2")
    private String field2;

    public PfcLayout(Integer f0, String f1) {
    }
}
