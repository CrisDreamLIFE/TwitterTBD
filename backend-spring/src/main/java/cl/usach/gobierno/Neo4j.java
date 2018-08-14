package cl.usach.gobierno;

import org.apache.lucene.document.Document;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.Driver;

import javax.print.Doc;
import java.sql.*;
import java.sql.Statement;
import java.util.*;

import static org.neo4j.driver.v1.Values.parameters;


public class Neo4j {
    private static Driver driver;
    private static volatile Session session;

    public List<Map<String, Object>> listaNodos = new ArrayList<>();
    public List<Map<String, Object>> listaRelTweet = new ArrayList<>();

    public void OpenNeo4jClient(){
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));
        session = driver.session();
    }

    public void CloseNeo4jClient(){
        session.close();
        driver.close();
    }

    /**
     * Crea nodos para cada politico que esta en la BD de mysql
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
    /**
     * Crea relaciones entre usuarios y politicos
     *
     * Busca en Lucene los tweets que contengan el nombre del politico y
     * si ese usuario tiene mas de 1000 seguidores entonces lo agrega a
     * una lista de usuarios en el que luego se itera.
     *
     * Finalmente crea el nodo usario, crea la relacion y agrega
     * la cantidad de seguidores a los usuarios
     */
    public void CrearRelacionTweet()
    {
        Lucene luca = new Lucene();
        StatementResult politicos = session.run("MATCH (a:Political) return a.nombre");
        while(politicos.hasNext())
        {
            Record recordPolitico = politicos.next();
            for(int i=0;i<recordPolitico.size();i++)
            {
                ArrayList<Document> lista = luca.politicalSearch(recordPolitico.get("a.nombre").asString());
                for (int j = 0; j < lista.size(); j++) {
                    String query2 = "MERGE (a:Usuario" + "{" + "nombre:" + "'" + lista.get(j).get("userScreenName") + "'})";
                    session.run(query2);

                    String tweetText = lista.get(j).get("text").replaceAll("'", "\"");
                    System.out.println(tweetText);
                    String query = "MATCH (a:Political),(b:Usuario) WHERE a.nombre = '" + recordPolitico.get("a.nombre").asString() + "' AND b.nombre = '" + lista.get(j).get("userScreenName") + "'" +
                            " CREATE (a)-[r:Tweet {text: '" + tweetText + "'}]->(b)";
                    session.run(query);

                    String followersCount = lista.get(j).get("userFollowersCount");
                    String query3 = "MATCH (a:Usuario) WHERE a.nombre = '"+ lista.get(j).get("userScreenName") +"' SET a.followersCount =  '" + followersCount + "'";
                    session.run(query3);

                }
            }
        }
    }

    /**
     * Elimina todos los nodos que no tengan alguna relacion
     */
    public void DeleteNodesWithoutRel(){
        String query = "MATCH (n) WHERE NOT (n)--() DELETE n";
        session.run(query);
    }

    /**
     * Obtiene los nodos totales y los agrega a un map que contiene el id,
     * usuario y "peso" que es la cantidad de seguidores.
     *
     * Primero itera cada politico, obteniendo todos los usuarios que tienen una relacion con este
     * y limita la cantidad a 12 usuarios o nodos, ordenados por la cantidad de seguidores.
     */
    public void GetNodosTotal()
    {
        int id = 0;
        ArrayList<String> usuarios = new ArrayList<String>();
        StatementResult nodesPoliticos = session.run("MATCH (a:Political) return a.nombre as politico");
        while(nodesPoliticos.hasNext()) {
            Record record2 = nodesPoliticos.next();
            StatementResult nodes = session.run("MATCH (u:Usuario)-[r:Tweet]-(a:Political) WHERE a.nombre='" + record2.get("politico").asString() + "' RETURN u.nombre AS usuario, u.followersCount as followersCount, a.nombre AS politico ORDER BY r.followerRank DESC LIMIT 12");
            while (nodes.hasNext()) {
                Record record = nodes.next();
                if (!usuarios.contains(record.get("usuario").asString())) {
                    listaNodos.add(mapTriple("id", id, "username", record.get("usuario").asString(), "weight", Integer.parseInt(record.get("followersCount").asString())));
                    id++;
                    usuarios.add(record.get("usuario").asString());
                }
            }
            listaNodos.add(mapTriple("id", id, "username", record2.get("politico").asString(), "weight", 10));
            id++;
        }
    }

    /**
     * Obtiene todas las relaciones de los nodos, los cuales tienen el id del nodo fuente
     * y el id del nodo destino
     *
     * Itera la lista de nodos obtenidos por GetNodosTotal() en el cual busca en la lista
     * el nombre de usuario que coincida con el usuario del "record" si es asi, obtiene el id del nodo
     * y vuelve a iterar la lista en busca del id del politico.
     */
    public void GetRelNodos()
    {
        int userIndex = -1;
        int politicalIndex = -1;
        StatementResult rel = session.run("MATCH (u:Usuario)-[r:Tweet]-(a:Political) RETURN u.nombre AS usuario, a.nombre AS politico ORDER BY u.followerRank DESC LIMIT 2000");
        while(rel.hasNext())
        {
            Record record = rel.next();
            for(int i = 0; i< listaNodos.size(); i++)
            {
                if(listaNodos.get(i).get("username").equals(record.get("usuario").asString()))
                {
                    userIndex = Integer.parseInt(listaNodos.get(i).get("id").toString());
                    for(int j = 0; j< listaNodos.size(); j++)
                    {
                        if(listaNodos.get(j).get("username").equals(record.get("politico").asString()))
                        {
                            politicalIndex = Integer.parseInt(listaNodos.get(j).get("id").toString());
                            break;
                        }
                    }
                }
            }

            listaRelTweet.add(mapDouble("source", userIndex, "target", politicalIndex));
        }
    }

    private Map<String, Object> mapMultiple(Map<String, Object> neoValues)
    {
        Map<String, Object> result = new HashMap<String, Object>(2);

        for(Map.Entry<String, Object> entry : neoValues.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            result.put(key, value);
        }

        return result;
    }

    private Map<String, Object> mapTriple(String key1, Object value1, String key2, Object value2, String key3, Object value3)
    {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        result.put(key3, value3);
        return result;
    }

    private Map<String, Object> mapDouble(String key1, Object value1, String key2, Object value2)
    {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

    /**
     * Funcion que permite generar un "map" para poder ser enviado al frontend
     */
    public Map<String, Object> getGrafo()
    {
        GetNodosTotal();
        GetRelNodos();
        return mapDouble("nodes", listaNodos, "links", listaRelTweet);
    }

}
