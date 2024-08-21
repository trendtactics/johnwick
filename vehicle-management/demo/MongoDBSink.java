package com.example.vehiclemanagement.demo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;

public class MongoDBSink implements SinkFunction<DeltaRecord> {

    private final String mongoUri;
    private final String databaseName;
    private final String collectionName;

    public MongoDBSink(String mongoUri, String databaseName, String collectionName) {
        this.mongoUri = mongoUri;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    @Override
    public void invoke(DeltaRecord deltaRecord, Context context) throws Exception {
        // Create a MongoClient
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            // Access the database and collection
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Convert DeltaRecord to a MongoDB document
            Document deltaDocument = new Document()
                    .append("CSPA", deltaRecord.getCSPA())
                    .append("layout_name", deltaRecord.getLayoutName())
                    .append("changes", deltaRecord.getChanges())
                    .append("timestamp", deltaRecord.getTimestamp());

            // Upsert (insert or update) the document in the collection
            collection.updateOne(
                    new Document("CSPA", deltaRecord.getCSPA()).append("layout_name", deltaRecord.getLayoutName()),
                    new Document("$set", deltaDocument),
                    new com.mongodb.client.model.UpdateOptions().upsert(true)
            );
        }
    }
}
