package com.example.vehiclemanagement.demo;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;

public class FlinkMongoProcessor {

    public static void main(String[] args) throws Exception {
        // Setup Flink environment
        //final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

        // MongoDB source stream
        DataStream<MongoRecord> mongoStream = MongoDBSource.getMongoStream(env);

        // Local file source stream
        String filePath = "src/main/resources/sample-data.json";
        System.out.println("file path"+filePath);
        DataStream<LocalRecord> fileStream = LocalFileSource.getFileStream(env, filePath);

        // Connect streams, keyBy layout_name, and process
        DataStream<DeltaRecord> deltaStream = mongoStream
                .connect(fileStream)
                .keyBy(MongoRecord::getLayoutName, LocalRecord::getLayoutName)
                .process(new CompareAndGenerateDelta());

        // MongoDB sink for delta stream
        deltaStream.addSink(new MongoDBSink(
                "mongodb+srv://imanuelprabhu:WsbJiFJ0pWfd4dBQ@johnwick.nj102.mongodb.net/",
                "PCF_FILE",
                "deltaCollection"));
        env.setParallelism(1);
        // Execute the Flink job
        env.execute("Flink MongoDB Delta Processor");
    }
}
