package cl.usach.gobierno.controllers;

import cl.usach.gobierno.Neo4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController    // This means that this class is a Controller
@RequestMapping("/neo4j")

public class neo4jController {

    @CrossOrigin
    @GetMapping(path="/crearrel")
    public @ResponseBody
    void createGrafoRel() {
        Neo4j grafo2 = new Neo4j();
        grafo2.OpenNeo4jClient();
        grafo2.CreateRelPolitical("Sebastian Pinera", "Andres Chadwick");
        grafo2.CloseNeo4jClient();
    }
}
