package cl.usach.gobierno;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.usach.gobierno.entities.Political;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import cl.usach.gobierno.repositories.PoliticalRepository;

import javax.print.Doc;

public class Lucene {
    public PoliticalRepository politicalrepository;
    //private List<String> idList = null;
    private List<Tweet> resultList = null;
    private int positiveResult=0;
    private int negativeResult=0;
    private int neutralResult=0;
    private int commentsCountry=0;
    private List<String> countryList = null;

    public void indexCreate()
    {
        try{
            Directory dir = FSDirectory.open(Paths.get("indice/"));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(OpenMode.CREATE);

            IndexWriter writer = new IndexWriter(dir,iwc);
            //  DBCursor cursor = this.mongoConnection.getTweets();
            MongoConnection mongo = MongoConnection.getMongo();
            mongo.OpenMongoClient();
            DBCursor cursor = mongo.getTweets();
            Document doc = null;

            System.out.println("lei los tweets");

            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                doc = new Document();
			      //Political political = new Political();
			      //political.setNombre("piñera");
			      //political.setComentariosPositivos(0.0);
			      //political.setComentariosNegativos(0.0);
			      //political.setComentariosNeutros(0.0);
			      //political.setDescripcion(" ");
			      //PoliticalRepository.save(political);
                System.out.println("------------------------");
			      System.out.println("Guardado\n");
                  doc.add(new StringField("id",cur.get("_id").toString(),Field.Store.YES));
                doc.add(new TextField("text", cur.get("text").toString(),Field.Store.YES));
                doc.add(new StringField("analysis",cur.get("analysis").toString(),Field.Store.YES));
                //doc.add(new StringField("finalCountry",cur.get("finalCountry").toString(),Field.Store.YES));
                //doc.add(new StringField("userName",cur.get("userName").toString(),Field.Store.YES));
                doc.add(new StringField("userFollowersCount",cur.get("userFollowersCount").toString(),Field.Store.YES));
                doc.add(new StringField("favoriteCount",cur.get("favoriteCount").toString(),Field.Store.YES));
                //System.out.println("GUARDE LA MIERDA");
                //System.out.println("pais del comentario indexando :"+ doc.get("finalCountry"));
                if (writer.getConfig().getOpenMode() == OpenMode.CREATE){
                    System.out.println("Indexando el tweet: "+doc.get("text")+"\n");
                    writer.addDocument(doc);
                    System.out.println(doc);
                }
                else{
                    System.out.println("Actualizando el tweet: "+doc.get("text")+"\n");
                    writer.updateDocument(new Term("text"+cur.get("text")), doc);
                    System.out.println(doc);
                }
            }
            cursor.close();
            writer.close();
        }
        catch(IOException ioe){
            System.out.println(" caught a "+ ioe.getClass() + "\n with message: " + ioe.getMessage());

        }

    }
    public void indexSearch(String Politicals)
    {
        try{
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("indice/")));
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            this.positiveResult=0;
            this.negativeResult=0;
            this.neutralResult=0;
            QueryParser parser = new QueryParser("text",analyzer);
            Query query = parser.parse(Politicals);
            //countryList = new ArrayList<String>();
            TopDocs result = searcher.search(query,25000);
            ScoreDoc[] hits =result.scoreDocs;
            //System.out.println("cantidad tweets:"+hits.length);
            for (int i=0; i<hits.length;i++){
                Document doc = searcher.doc(hits[i].doc);
                //System.out.println(doc.get("userName"));


                //System.out.println("pais del comentario indexando :"+ doc.get("finalCountry"));
                if((doc.get("analysis")).equals("Positive")){
                    //this.countryList.add(doc.get("finalCountry"));
                    //System.out.println(doc.get("finalCountry")+"\n");
                    this.positiveResult++;
                }
                else if((doc.get("analysis")).equals("Negative")){
                    this.negativeResult++;
                }
                else if((doc.get("analysis")).equals("Neutral")){
                    this.neutralResult++;
                }
                //System.out.println((i+1) + ".- score="+hits[i].score+" doc="+hits[i].doc+" id="+doc.get("id")+ "twee="+doc.get("text"));
            }

            reader.close();


        }
        catch(IOException | ParseException ex){
            Logger.getLogger(Lucene.class.getName()).log(Level.SEVERE,null,ex);

        }
        //return 0;
    }

	/*public void countryCommentsCount(String artista, String country){
		int comments= this.countryList.size();
		this.commentsCountry=0;
		for(int i=0;i<comments;i++){
			if(country.equals(this.countryList.get(i))){
				this.commentsCountry++;
			}
		}
	}*/

    public List<Tweet> getTweets(String Political)
    {
        this.resultList = null ;
        try{
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("indice/")));
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            this.resultList = new ArrayList<Tweet>();
            QueryParser parser = new QueryParser("text",analyzer);
            Query query = parser.parse(Political);

            TopDocs result = searcher.search(query,25000);
            ScoreDoc[] hits =result.scoreDocs;

            for (int i=0; i<hits.length;i++)
            {
                Document doc = searcher.doc(hits[i].doc);
                Tweet tweet = new Tweet();
                tweet.setUserName(doc.get("userName"));
                tweet.setText(doc.get("text"));
                tweet.setFollowers(Integer.parseInt(doc.get("userFollowersCount")));
                tweet.setFollowees(Integer.parseInt(doc.get("favoriteCount")));
                this.resultList.add(tweet);
                tweet = null;
            }
            reader.close();
        }
        catch(IOException | ParseException ex)
        {
            Logger.getLogger(Lucene.class.getName()).log(Level.SEVERE,null,ex);

        }
        return this.resultList;
    }

    public List<Tweet> getResultList(){
        return this.resultList;
    }
    public int getpositiveResult(){
        return this.positiveResult;
    }
    public int getnegativeResult(){
        return this.negativeResult;
    }
    public int getneutralResult(){
        return this.neutralResult;
    }
    public List<String> getCountryList(){
        return this.countryList;
    }

    public int getCommentsCountry() {
        return commentsCountry;
    }


    private Map<String, Object> mapDouble(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

}