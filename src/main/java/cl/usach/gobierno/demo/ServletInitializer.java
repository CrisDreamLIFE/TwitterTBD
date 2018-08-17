package cl.usach.gobierno.demo;

import cl.usach.gobierno.demo.GobiernoApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@ComponentScan({"cl.usach.gobierno", "cl.usach.gobierno.controllers"})
@EntityScan("cl.usach.gobierno.entities")
@EnableJpaRepositories("cl.usach.gobierno.repositories")
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GobiernoApplication.class);
	}

}
