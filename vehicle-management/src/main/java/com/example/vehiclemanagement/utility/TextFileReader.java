package com.example.vehiclemanagement.utility;

import com.example.vehiclemanagement.pojo.PfcLayout;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileReader {
    public static DataSet<PfcLayout> readTextFile(ExecutionEnvironment env, String filePath) throws Exception {
        Scanner scanner = new Scanner(new java.io.File(filePath));
        List<Tuple2<Integer, String>> data = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fields = line.split(",");
            int field1 = Integer.parseInt(fields[0].trim());
            String field2 = fields[1].trim();
            data.add(new Tuple2<>(field1, field2));
        }

        scanner.close();

        DataSet<Tuple2<Integer, String>> dataSet = env.fromCollection(data);
        return dataSet.map(new MapFunction<Tuple2<Integer, String>, PfcLayout>() {
            @Override
            public PfcLayout map(Tuple2<Integer, String> value) throws Exception {
                return new PfcLayout(value.f0, value.f1);
            }
        });
    }
}
