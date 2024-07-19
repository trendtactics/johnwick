package com.example.vehiclemanagement.utility;

import com.example.vehiclemanagement.pojo.PfcLayout;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.io.CsvReader;

public class CsvFileReader {
    public static DataSet<PfcLayout> readCsvFile(ExecutionEnvironment env, String filePath) {
        DataSource<Tuple2<Integer, String>> csvReader = env.readCsvFile(filePath)
                .ignoreFirstLine()
                .parseQuotedStrings('"')
                .ignoreInvalidLines()
                .types(Integer.class, String.class);

        DataSet<PfcLayout> csvData = csvReader.map(new MapFunction<Tuple2<Integer, String>, PfcLayout>() {
            @Override
            public PfcLayout map(Tuple2<Integer, String> value) throws Exception {
                return new PfcLayout(value.f0, value.f1);
            }
        });

        return csvData;
    }
}
