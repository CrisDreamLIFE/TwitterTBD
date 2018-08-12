package cl.usach.gobierno;

import org.apache.lucene.document.Document;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import java.sql.*;
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

    /*
       Crea nodos para cada uno de los politicos en la base de datos
     */
    public void CreateNodePolitical() throws SQLException
    {
        Connection conexion = DriverManager.getConnection ("jdbc:mysql://localhost:3306/politicos","root", "root");
        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery ("SELECT nombre FROM political");

        while(rs.next()) {
            String query = "CREATE (a:Political" + "{" + "nombre:" + "'" + rs.getString("nombre") + "'})";
            session.run(query);
        }

        conexion.close();
    }

    public void CreateNodeUsuario() throws SQLException
    {
        Lucene luca = new Lucene();
        ArrayList<Document> usuarios2;
        usuarios2 = luca.indexSearch();

        for (int i = 0; i < usuarios2.size(); i++) {
            String query = "CREATE (a:Political" + "{" + "nombre:" + "'" + usuarios2.get(i).get("userScreenName") + "'})";
            System.out.println(usuarios2.get(i).get("userScreenName"));
            session.run(query);
        }

    }

    public void CreateRelPolitical(String name1, String name2)
    {
        String query = "MATCH (a:Political),(b:Political) WHERE a.nombre = '"+ name1 +"' AND b.nombre = '"+name2+"' CREATE (a)-[r:Tweet {test: 'ewfw'}]->(b)";
        session.run(query);
    }
}
