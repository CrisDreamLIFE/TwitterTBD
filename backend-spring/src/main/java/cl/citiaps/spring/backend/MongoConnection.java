package cl.citiaps.spring.backend;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


public class MongoConnection {
	private static String uriConnection = "mongodb://localhost:27017";
	private String databaseName;
	private String collectionName;
	
	private MongoClient mongoClient;
	private DB database;
	private DBCollection collection;
	
	public MongoConnection(String db, String collection)
	{
		this.databaseName = db;
		this.collectionName = collection;
	}
	
	public void connect()
	{
		try 
		{
			this.mongoClient = new MongoClient(new MongoClientURI(uriConnection));
			System.out.println("me coencte? xd ");
		} catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.database = mongoClient.getDB(this.databaseName);
		this.collection = database.getCollection(this.collectionName);
		System.out.println("me conecte xxd ");
		
	}

	void disconnect()
	{
		this.mongoClient.close();
	}
	
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
	
	/*public List<String> getUserNames(){
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
	}*/
	public DBCursor getTweets(){
		DBCursor cursor = this.collection.find();
		System.out.println("lei los tweets");
		return cursor;
	}
}
