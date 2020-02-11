package com.company;

import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import org.bson.Document;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
        By Halim Dakir
*/


public class MongoDB {

    public static void main(String[] args) throws UnknownHostException {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

        //Connecting with server
        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("lab3");
        DBCollection collection = db.getCollection("restaurants");

        ///Delete All documents before running example again
        WriteResult result = collection.remove(new BasicDBObject());
        System.out.println(result.toString());
        parseJSON(collection);
        System.out.println("Print out all documents of the collection.");
        selectAllRecordsFromACollection(collection);
        System.out.println("Skriv en metod som skriver ut namnet på alla dokument som har kategorin \"Cafe\".");
        selectByCategory();
        System.out.println("uppdaterar genom increment “stars” för den restaurang som har “name” “XYZ Coffee Bar” så att nya värdet på stars blir 6.");
        findAndIncrement(collection);
        System.out.println("Print out all document after updating a document.");
        selectSingleRecordAndUpdate(collection);
        System.out.println("Aggregerar en lista med alla restauranger som har 4.");
        aggregateDocuments();

    }

        private static void parseJSON(DBCollection collection){
        //Insert data to the collection
        String json = "{'_id\' : 'ObjectId(\"5c39f9b5df831369c19b6bca\")'," +
                "'name' : 'Sun Bakery Trattoria'," +
                "'stars' : 4," +
                "'categories' : [" +
                                "'Pizza'," +
                                "'Pasta'," +
                                "'Italian'," +
                                "'Coffee'," +
                                "'Sandwiches'" +
                                "]" +
                "}";

        String json1= "{'_id' : 'ObjectId(\"5c39f9b5df831369c19b6bcb\")',"+
                    "'name' : 'Blue Bagels Grill'," +
                    "'stars' : 3," +
                    "'categories' : [" +
                                    "'Bagels'," +
                                    "'Cookies'," +
                                    "'Sandwiches'" +
                                    "]" +
                    "}";

        String json2 = "{'_id' : 'ObjectId(\"5c39f9b5df831369c19b6bcc\")'," +
                    "'name' : 'Hot Bakery Cafe'," +
                    "'stars' : 4," +
                    "'categories' : [" +
                                    "'Bakery'," +
                                    "'Cafe'," +
                                    "'Coffee'," +
                                    "'Dessert'" +
                                    "]" +
                    "}";

        String json3 = "{'_id' : 'ObjectId(\"5c39f9b5df831369c19b6bcd\")'," +
                    "'name' : 'XYZ Coffee Bar'," +
                    "'stars' : 5," +
                    "'categories' : [" +
                                    "'Coffee'," +
                                    "'Cafe'," +
                                    "'Bakery'," +
                                    "'Chocolates'" +
                                    "]" +
                    "}";
        String json4 = "{'_id' : 'ObjectId(\"5c39f9b5df831369c19b6bce\")'," +
                    "'name' : '456 Cookies Shop'," +
                    "'stars' : 4," +
                    "'categories' : [" +
                                    "'Bakery'," +
                                    "'Cookies'," +
                                    "'Cake'," +
                                    "'Coffee'" +
                                    "]" +
                    "}";

            DBObject dbObject = (DBObject)JSON.parse(json);
            collection.insert(dbObject);
            DBObject dbObject1 = (DBObject)JSON.parse(json1);
            collection.insert(dbObject1);
            DBObject dbObject2 = (DBObject)JSON.parse(json2);
            collection.insert(dbObject2);
            DBObject dbObject3 = (DBObject)JSON.parse(json3);
            collection.insert(dbObject3);
            DBObject dbObject4 = (DBObject)JSON.parse(json4);
            collection.insert(dbObject4);
    }

    //Print out all documents of the collection
    private static void selectAllRecordsFromACollection(DBCollection collection)
    {
        DBCursor cursor = collection.find();
        while(cursor.hasNext())
        {
            System.out.println(cursor.next());
        }
    }

    //Skriv en metod som skriver ut namnet på alla dokument som har kategorin “Cafe”
    private static void selectByCategory() {
        MongoCollection<Document> collection = new MongoClient().getDatabase("lab3").getCollection("restaurants");
        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$unwind", "$stars"),
                new Document("$match", new Document("categories", "Cafe")),
                new Document("$project", new Document()
                        .append("name", "$name"))));

        for (Document dbObject : output)
        {
            System.out.println("Name :"+dbObject.get("name"));
        }
    }

    //uppdaterar genom increment “stars” för den restaurang som har “name” “XYZ Coffee Bar” så att nya värdet på stars blir 6.
    private static void findAndIncrement(DBCollection dbCollection) {
        MongoCollection<Document> collection = new MongoClient().getDatabase("lab3").getCollection("restaurants");
        Document find = new Document();
        find.append("name", "XYZ Coffee Bar");
        Document update = new Document();
        update.append("$inc", new BasicDBObject("stars", 1));
        Document obj = collection.findOneAndUpdate(find, update);

        DBCursor cursor = dbCollection.find();
        while(cursor.hasNext())
        {
            System.out.println(cursor.next());
        }
    }

    // uppdaterar “name” för "456 Cookies Shop" till “123 Cookies Heaven”
    private static void selectSingleRecordAndUpdate(DBCollection collection) {
        MongoCollection<Document> mongoCollection = new MongoClient().getDatabase("lab3").getCollection("restaurants");
        Document query = new Document();
        query.append("name", "456 Cookies Shop");
        Document setData = new Document();
        setData.append("name", "123 Cookies Heaven");
        Document update = new Document();
        update.append("$set", setData);
        //To update single Document
        mongoCollection.updateOne(query, update);
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    //Aggregerar en lista med alla restauranger som har 4.
    private static void aggregateDocuments() {
        MongoCollection<Document> collection = new MongoClient().getDatabase("lab3").getCollection("restaurants");
        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$unwind", "$stars"),
                new Document("$match", new Document("stars", 4)),
                new Document("$project", new Document()
                        .append("name", "$name")
                        .append("stars", "$stars"))));
        // Print
        for (Document dbObject : output)
        {
            System.out.println("Name :"+dbObject.get("name")+" , "+"Stars :"+dbObject.get("stars"));
        }
    }
}
