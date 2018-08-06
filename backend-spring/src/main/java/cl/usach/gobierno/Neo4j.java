package cl.usach.gobierno;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neo4j.driver.v1.Values.parameters;


public class Neo4j {
    private static Driver driver;
    private static volatile Session session;

    public void OpenNeo4jClient(){
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));
        session = driver.session();
    }

    public void CloseNeo4jClient(){
        session.close();
        driver.close();
    }

    public void CreateNodePolitical(String name, String cargo)
    {
        String query = "CREATE (a:Political" + "{" + "nombre:" + "'" + name + "'" + "," + "cargo:" + "'" + cargo + "'" + "})";
        session.run(query);
    }

    public void CreateRelPolitical(String name1, String name2)
    {
        String query = "MATCH (a:Political),(b:Political) WHERE a.nombre = '"+ name1 +"' AND b.nombre = '"+name2+"' CREATE (a)-[r:Tweet {test: 'ewfw'}]->(b)";
        session.run(query);
    }



}
