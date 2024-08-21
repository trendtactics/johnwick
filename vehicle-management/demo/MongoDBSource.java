package com.example.vehiclemanagement.demo;/*
import com.mongodb.client.MongoCollection;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoDBSource {

    public static DataStream<MongoRecord> getMongoStream(StreamExecutionEnvironment env) {
        MongoCollection<Document> collection = MongoDBConnection.getMongoCollection();

        // Fetch all documents from MongoDB and convert them to a list of MongoRecord
        List<MongoRecord> records = new ArrayList<>();
        for (Document doc : collection.find()) {
            MongoRecord record = new MongoRecord();
            record.setCSPA(doc.getString("CSPA"));
            record.setDate(doc.getString("date"));
            record.setVersion(doc.getString("version"));
            record.setLayoutName(doc.getString("layout_name"));
            record.setDocument(doc.get("document", Map.class));
            records.add(record);
        }

        // Convert list to DataStream
        return env.fromCollection(records);
    }
}
*//*
import com.mongodb.client.MongoCollection;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoDBSource {

    public static DataStream<MongoRecord> getMongoStream(StreamExecutionEnvironment env) {
        MongoCollection<Document> collection = MongoDBConnection.getMongoCollection();

        // Fetch all documents from MongoDB and convert them to a list of MongoRecord
        List<MongoRecord> records = new ArrayList<>();
        for (Document doc : collection.find()) {
            MongoRecord record = new MongoRecord();
            record.setCSPA(doc.getString("CSPA"));
            record.setDate(doc.getString("date"));
            record.setVersion(doc.getString("version"));
            record.setLayoutName(doc.getString("layout_name"));
            record.setDocument(doc.get("document", Map.class));
            records.add(record);
        }

        if (records.isEmpty()) {
            throw new IllegalStateException("MongoDB collection is empty. No records to process.");
        }

        // Convert list to DataStream
        return env.fromCollection(records);
    }
}
*/
import com.mongodb.client.MongoCollection;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoDBSource {

    public static DataStream<MongoRecord> getMongoStream(StreamExecutionEnvironment env) {
        MongoCollection<Document> collection = MongoDBConnection.getMongoCollection();

        // Fetch all documents from MongoDB and convert them to a list of MongoRecord
        List<MongoRecord> records = new ArrayList<>();
        for (Document doc : collection.find()) {
            // Add this line to log each document fetched from MongoDB
            System.out.println(doc.toJson());

            MongoRecord record = new MongoRecord();
            record.setCSPA(doc.getString("CSPA"));
            record.setDate(doc.getString("date"));
            record.setVersion(doc.getString("version"));
            record.setLayoutName(doc.getString("layout_name"));
            record.setDocument(doc.get("document", Map.class));
            records.add(record);
        }

        if (records.isEmpty()) {
            throw new IllegalStateException("MongoDB collection is empty. No records to process.");
        }

        // Convert list to DataStream
        return env.fromCollection(records);
    }
}
