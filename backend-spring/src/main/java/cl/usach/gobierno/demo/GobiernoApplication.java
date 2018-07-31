package cl.usach.gobierno.demo;

import cl.usach.gobierno.Neo4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"cl.usach.gobierno", "cl.usach.gobierno.controllers"})
@EntityScan({"cl.usach.gobierno.entities"})
@EnableJpaRepositories("cl.usach.gobierno.repositories")
@EnableScheduling
public class GobiernoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GobiernoApplication.class, args);
		Neo4j grafo = new Neo4j();
		grafo.OpenNeo4jClient();
		grafo.CreateNodePolitical("Sebastian Piñera", "Presidente de la Republica");
		grafo.CloseNeo4jClient();
	}
}
