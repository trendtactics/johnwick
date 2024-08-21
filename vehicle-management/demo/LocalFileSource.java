package com.example.vehiclemanagement.demo;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class LocalFileSource {
    public static DataStream<LocalRecord> getFileStream(StreamExecutionEnvironment env, String filePath) {
        DataStreamSource<String> fileSourceStream = env.readTextFile(filePath);

        return fileSourceStream.map(new MapFunction<String, LocalRecord>() {
            @Override
            public LocalRecord map(String jsonString) {
                // Convert jsonString to LocalRecord using your JSON library
                LocalRecord record = new LocalRecord();
                // Populate the LocalRecord fields from the jsonString
                return record;
            }
        });
    }
}