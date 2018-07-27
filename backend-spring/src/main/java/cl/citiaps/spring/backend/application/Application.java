package cl.citiaps.spring.backend.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import cl.citiaps.spring.backend.Lucene;
import cl.citiaps.spring.backend.MongoConnection;
import cl.citiaps.spring.backend.ScheduledTasks;
import cl.citiaps.spring.backend.entities.Political;
import cl.citiaps.spring.backend.repository.PoliticalRepository;

@SpringBootApplication
@ComponentScan({"cl.citiaps.spring.backend.application", "cl.citiaps.spring.backend.rest"})
@EntityScan("cl.citiaps.spring.backend.entities")
@EnableJpaRepositories("cl.citiaps.spring.backend.repository")
@EnableScheduling
public class Application {

	//public static MongoConnection mc = new MongoConnection("twitter2", "tweet");
	//public static PoliticalRepository politicalrepository ;

	public static void main(String[] args) {
		/*mc.connect();
    	System.out.println("sdiuoufdjskfgjdskf,s");
    	DBCursor cursor = mc.getTweets();
    	while (cursor.hasNext()) {
    		System.out.println("entre al while");
    		DBObject cur = cursor.next();
    		Political political = new Political();
        	political.setNombre("piñera");
        	System.out.println( cur.get("positivePorcent"));
        	//political.setIdPolitical(cur.get("_id"));
        	political.setComentariosPositivos(Integer.parseInt( String.valueOf(cur.get("positivePorcent"))));
        	political.setComentariosNegativos(Integer.parseInt((String) cur.get("negativePorcent")));
        	political.setComentariosNeutros(Integer.parseInt((String) cur.get("positivePorcent")));
        	political.setDescripcion(" ");
        	politicalrepository.save(political);
    	}
    	*/
		System.out.println("Empezar\n");
		MongoConnection mc = new MongoConnection("twitter2", "tweet");
		mc.connect();
		//mc.mapReduce();
		Lucene lucene = new Lucene(mc);
		lucene.indexCreate();
		lucene.indexSearch("piñera");
		//System.out.println(lucene.getIdList());

		SpringApplication.run(Application.class, args);
		//sch = new ScheduledTasks();
		//sch.indexCreateTask();
		
	}
}
