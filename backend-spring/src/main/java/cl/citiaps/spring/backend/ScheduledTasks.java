package cl.citiaps.spring.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cl.citiaps.spring.backend.entities.Political;
import cl.citiaps.spring.backend.repository.PoliticalRepository;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.sql.SQLException;

@Component
public class ScheduledTasks
{
	public MongoConnection mc = new MongoConnection("twitter2", "tweet");
	public Lucene lucene = new Lucene(mc);
	@Autowired
	public PoliticalRepository politicalrepository;
	@Autowired
	public Iterable<Political> politicals = null;
	public int[] countryComments;
	

	
    @Scheduled(fixedRate = 5000)
    public void indexCreateTask()
	{
    	mc.connect();
    	System.out.println("sdiuoufdjskfgjdskf,s");
    	DBCursor cursor = this.mc.getTweets();
    	while (cursor.hasNext()) {
    		DBObject cur = cursor.next();
    		Political political = new Political();
        	political.setNombre("piñera");
        	political.setComentariosPositivos(0.0);
        	political.setComentariosNegativos(0.0);
        	political.setComentariosNeutros(0);
        	//political.setIdPolitical(Integer.parseInt((String) cur.get("_id")));
        	//political.setComentariosPositivos(Integer.parseInt((String) cur.get("positivePorcent")));
        	//political.setComentariosNegativos(Integer.parseInt((String) cur.get("negativePorcent")));
        	//political.setComentariosNeutros(Integer.parseInt((String) cur.get("positivePorcent")));
        	political.setDescripcion(" ");
        	politicalrepository.save(political);
    	}
    	
    	System.out.println("[Scheduled Task][Start]: Indexing tweets.");
    	lucene.indexCreate();
    	System.out.println("[Scheduled Task] [End] : Indexing tweets.\n");
    	mc.disconnect();
    }
    
    /*
    @Scheduled(cron="0 0 * * * *")
    public void updateComments()
	{
    	
    	System.out.println("[Scheduled Task][Start]: Update comments.");
    	politicals= politicalrepository.findAll();
    	for (Political political:politicals)
    	{
    		//countryList= paisrepository.findAll();
    		lucene.indexSearch(political.getNombre());
    		
    		/*System.out.println("Artista:"+ artista.getNombre());
    		System.out.println("Positivos: "+ lucene.getpositiveResult());
    		System.out.println("Negativos: "+ lucene.getnegativeResult());
    		System.out.println("Neutral: "+ lucene.getneutralResult());
    	    political.setComentariosPositivos(lucene.getpositiveResult());
    	    political.setComentariosNegativos(lucene.getnegativeResult());
			political.setComentariosNeutros(lucene.getneutralResult());
			
			for(Pais countryArtista:countryList){
				lucene.countryCommentsCount(artista.getNombre(), countryArtista.getNombre());
				if(countryArtista.getComentariosPositivos() < lucene.getCommentsCountry()){
					countryArtista.setArtista(artista);
					countryArtista.setComentariosPositivos(lucene.getCommentsCountry());
					paisrepository.save(countryArtista);
				}
			}
			artistarepository.save(artista);
    	}
    	System.out.println("[Scheduled Task] [End] : Update comments.\n");

    	Time time = Time.getInstance();
    	time.setArtistas();
    	time.setGeneros();
    	time.setMapa();
    } */
    /*@Scheduled(cron="0 0 * * * *")
    public void mapreduce() throws SQLException
	{
    	System.out.println("[Scheduled Task][Start]: Update graph db.");
    	Neo4j neo = new Neo4j();
        neo.connect("bolt://localhost", "neo4j", "root");
        neo.crearGrafo();
        neo.disconnect();
        System.out.println("[Scheduled Task] [End] : Update graph db.\n");

		Time time = Time.getInstance();
		time.setGrafo();
	}*/
}