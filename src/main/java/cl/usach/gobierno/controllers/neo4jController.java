package cl.usach.gobierno.controllers;

import cl.usach.gobierno.Neo4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController    // This means that this class is a Controller
@RequestMapping("/neo4j")

public class neo4jController {

    private Map<String, Object> grafo3;

    /**
     * Creacion de los datos del grafo para el front
     */
    @CrossOrigin
    @GetMapping(path="/grafo")
    @ResponseBody
    public Map<String, Object> mostrarGrafo(){
        Neo4j grafo2 = new Neo4j();
        grafo2.OpenNeo4jClient();
        grafo3 = grafo2.getGrafo();
        grafo2.CloseNeo4jClient();
        return grafo3;
    }
}
