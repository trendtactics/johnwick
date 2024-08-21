package com.example.vehiclemanagement.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnection {
    private static final String DATABASE_NAME = "PCF_FILE";
    private static final String COLLECTION_NAME = "SamplePcf";
    private static final String MONGO_URI = "mongodb+srv://imanuelprabhu:WsbJiFJ0pWfd4dBQ@johnwick.nj102.mongodb.net/";

    public static MongoCollection<Document> getMongoCollection() {
        MongoClient mongoClient = MongoClients.create(MONGO_URI);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        return database.getCollection(COLLECTION_NAME);
    }
}
