package cl.usach.gobierno;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import org.bson.Document;


public class MongoConnection {
    private static volatile MongoConnection instance;
    private static MongoClient mongoClient;
    private static int port = 27017;
    private String database2 = "twitter2";
    private String collection2 = "tweet";
    private DBCollection collection;
    private DB database;
    private MongoConnection(){}


    public static MongoConnection getMongo(){
        if (instance == null){
            synchronized (MongoConnection.class){
                if (instance == null){
                    instance = new MongoConnection();
                }
            }
        }
        return instance;
    }

    public void OpenMongoClient() {
        if(mongoClient == null)
            mongoClient = new MongoClient("localhost", port);
    }

    public MongoCollection<Document> getCollection(){

        MongoDatabase mongoDatabase = mongoClient.getDatabase(database2);
        return mongoDatabase.getCollection(collection2);
    }

    public void closeMongoClient(){
        mongoClient.close();
    }

 /*
  * Esta clase deberia ser solo la conexion a la BD
  * de mongo, los metodos deben estar en su respectiva clase

    public void showTweets(){
        DBCursor cursor = this.collection.find();
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
    }

    public List<String> getUserNames(){
        String map ="function(){emit(this.userName,1);}";
        String reduce = "function(userName,valores){return Array.sum(valores);}";
        MapReduceCommand cmd = new MapReduceCommand(this.collection, map, reduce,
                null, MapReduceCommand.OutputType.INLINE, null);

        MapReduceOutput out = this.collection.mapReduce(cmd);
        List<String> result = new ArrayList<String>();
        for (DBObject o : out.results()) {
            result.add( o.get("_id").toString());
            //System.out.println(o.get("_id"));
        }
        return result;
    }
  */
    public DBCursor getTweets(){
        System.out.println("2222222");

        DB mongoDatabase = mongoClient.getDB(database2);
        collection = mongoDatabase.getCollection(collection2);
        DBCursor cursor = collection.find();
        System.out.println("lei los tweets");
        return cursor;
    }

}