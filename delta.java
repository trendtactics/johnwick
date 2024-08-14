package com.example.flink;

import org.apache.flink.api.java.ExecutionEnvironment;

public class DeltaChangeProcessor {

    public static void main(String[] args) {
        try {
            final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
            DeltaChangeJob job = new DeltaChangeJob(env, "mongodb://your_mongo_db_url_here", "yourDatabase", "actualCollection", "deltaChangesCollection");
            job.execute();
            env.execute("Delta Change Processor");
        } catch (Exception e) {
            System.err.println("Error during Flink execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
//2
package com.example.flink;

        import com.mongodb.client.MongoCollection;
        import com.mongodb.client.MongoClients;
        import com.mongodb.client.MongoDatabase;
        import org.apache.flink.api.java.ExecutionEnvironment;
        import org.bson.Document;

        import java.io.FileReader;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Objects;

        import static com.mongodb.client.model.Filters.eq;

public class DeltaChangeJob {

    private final ExecutionEnvironment env;
    private final String mongoUri;
    private final String databaseName;
    private final String actualCollectionName;
    private final String deltaCollectionName;

    public DeltaChangeJob(ExecutionEnvironment env, String mongoUri, String databaseName, String actualCollectionName, String deltaCollectionName) {
        this.env = env;
        this.mongoUri = mongoUri;
        this.databaseName = databaseName;
        this.actualCollectionName = actualCollectionName;
        this.deltaCollectionName = deltaCollectionName;
    }

    public void execute() {
        try (var mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> actualCollection = database.getCollection(actualCollectionName);
            MongoCollection<Document> deltaCollection = database.getCollection(deltaCollectionName);

            // Load JSON file (Assuming the file is structured as the one you provided)
            Document localData = loadLocalJson("path/to/local/file.json");

            // Fetch existing MongoDB data
            List<Document> mongoData = fetchMongoDBData(actualCollection);

            // Compare local JSON data with MongoDB data
            List<Document> deltaDocs = compareData(localData, mongoData);

            // Store delta changes in MongoDB
            for (Document deltaDoc : deltaDocs) {
                deltaCollection.insertOne(deltaDoc);
            }

            System.out.println("Delta changes inserted into delta collection successfully.");

        } catch (Exception e) {
            System.err.println("Error during MongoDB operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Document loadLocalJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return Document.parse(reader.toString());
        } catch (Exception e) {
            System.err.println("Error loading local JSON file: " + e.getMessage());
            e.printStackTrace();
            return new Document();
        }
    }

    private List<Document> fetchMongoDBData(MongoCollection<Document> collection) {
        List<Document> mongoData = new ArrayList<>();
        try (var cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                mongoData.add(cursor.next());
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
        return mongoData;
    }

    private List<Document> compareData(Document localData, List<Document> mongoData) {
        List<Document> deltaDocs = new ArrayList<>();
        List<Document> localRecords = (List<Document>) localData.get("pcf-file");

        // Compare local records with MongoDB records
        for (Document localRecord : localRecords) {
            String cspa = localRecord.getString("CSPA");
            Document mongoRecord = mongoData.stream().filter(doc -> Objects.equals(doc.getString("CSPA"), cspa)).findFirst().orElse(null);

            if (mongoRecord == null) {
                // New record, create insert delta
                Document deltaDoc = new Document("operation", "insert")
                        .append("record", localRecord)
                        .append("timestamp", System.currentTimeMillis());
                deltaDocs.add(deltaDoc);
            } else {
                // Check for updates
                if (!localRecord.equals(mongoRecord)) {
                    Document deltaDoc = new Document("operation", "update")
                            .append("before", mongoRecord)
                            .append("after", localRecord)
                            .append("timestamp", System.currentTimeMillis());
                    deltaDocs.add(deltaDoc);
                }
                mongoData.remove(mongoRecord); // Remove matched record from mongoData
            }
        }

        // Remaining records in mongoData are deletions
        for (Document remainingMongoRecord : mongoData) {
            Document deltaDoc = new Document("operation", "delete")
                    .append("record", remainingMongoRecord)
                    .append("timestamp", System.currentTimeMillis());
            deltaDocs.add(deltaDoc);
        }

        return deltaDocs;
    }
}
//3
{
        "operation": "update",
        "before": {
        "CSPA": "value1",
        "date": "2024-08-14",
        "version": "v1",
        "layout_name": "layout1",
        "document": {
        "key1": "oldValue1",
        "key2": "oldValue2"
        }
        },
        "after": {
        "CSPA": "value1",
        "date": "2024-08-14",
        "version": "v1",
        "layout_name": "layout1",
        "document": {
        "key1": "newValue1",
        "key2": "newValue2"
        }
        },
        "timestamp": 1692098247123
        }
//
        {
        "operation": "insert",
        "record": {
        "CSPA": "value3",
        "date": "2024-08-14",
        "version": "v1",
        "layout_name": "layout1",
        "document": {
        "key1": "value5",
        "key2": "value6"
        }
        },
        "timestamp": 1692098247123
        }
        //
        {
        "operation": "delete",
        "record": {
        "CSPA": "value2",
        "date": "2024-08-14",
        "version": "v1",
        "layout_name": "layout1",
        "document": {
        "key1": "value3",
        "key2": "value4"
        }
        },
        "timestamp": 1692098247123
        }
        Subject: Confirmation of Approach for Delta Changes Implementation in MongoDB

        Dear [Manager's Name], [Scrum Master's Name],

        I hope this message finds you well.

        I am writing to confirm the approach I plan to take for implementing the delta changes in MongoDB as described in Jira ticket OTSD-2463. This approach is designed to align with both the technical requirements and business expectations, ensuring that all changes (inserts, updates, and deletions) are accurately tracked and stored in a dedicated MongoDB collection.

        Proposed Approach:
        Load Local JSON Data:

        I will load the data from a local JSON file that contains the current state of records.
        Compare with Existing MongoDB Data:

        The data from the local JSON file will be compared against the existing records in the MongoDB collection (actualCollection) to detect any changes.
        Generate Delta Changes:

        Based on the comparison, I will generate delta documents that capture:
        Insertions: New records present in the local JSON file but not in MongoDB.
        Updates: Records that exist in both the local JSON file and MongoDB but have different field values.
        Deletions: Records that exist in MongoDB but are missing from the local JSON file.
        Store Delta Changes:

        These delta changes will be stored in a separate MongoDB collection (deltaChangesCollection). This collection will include detailed information about the changes, including the type of operation (insert, update, delete) and the specific fields affected.
        Sample MongoDB Delta Changes Collection:
        For your reference, I have attached sample documents that illustrate how the delta changes will be recorded in MongoDB. These samples reflect the structure and content of the deltaChangesCollection as per the approach outlined above.

        Sample Insert Delta: Reflects new records detected in the local JSON file.
        Sample Update Delta: Captures modifications to existing records.
        Sample Delete Delta: Logs records that have been removed from the dataset.
        Request for Confirmation:
        Please review the proposed approach and let me know if it aligns with your expectations. If there are any adjustments or additional requirements, kindly share them with me so that I can incorporate them into the implementation.

        Your feedback is crucial to ensure that the solution meets the business needs and the specific objectives of the Jira ticket.

        Thank you for your time and support.
