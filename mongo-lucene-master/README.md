The maven dependecy:

pom.xml
```xml
<dependency>
    <groupId>com.github.mongoutils</groupId>
    <artifactId>mongo-lucene</artifactId>
    <version>1.0</version>
</dependency>
<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-core</artifactId>
    <version>7.4.0</version>
</dependency>
<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-analyzers-common</artifactId>
    <version>7.4.0</version>
</dependency>
<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-queryparser</artifactId>
    <version>7.4.0</version>
</dependency>
<dependency>
	<groupId>org.mongodb</groupId>
	<artifactId>mongo-java-driver</artifactId>
	<version>3.8.0</version>
</dependency>
```
Agregadas otras necesarias para lucene y mongo db

## How to use it

Porciones de codigo necesarias para usar:

Conección a mongo:

```java
// Mongo connection
Mongo mongo = new Mongo("localhost", options);
DB db = mongo.getDB("testdb");
DBCollection dbCollection = db.getCollection("testcollection");


Serializar y optencion del mapa de la BD, necesarios para indexar y hacer la busqueda

// serializers + map-store
DBObjectSerializer<String> keySerializer = new SimpleFieldDBObjectSerializer<String>("key");
DBObjectSerializer<MapDirectoryEntry> valueSerializer = new MapDirectoryEntrySerializer("value");
ConcurrentMap<String, MapDirectoryEntry> store = new MongoConcurrentMap<String, MapDirectoryEntry>(dbCollection, keySerializer, valueSerializer);


// lucene directory
Directory dir = new MapDirectory(store);

Para indexar:
// index files
StandardAnalyzer analyser = new StandardAnalyzer();
IndexWriter writer = new IndexWriter(dir, ...);
Document doc = new Document();
doc.add(new TextField("title", "My file's content ...", Field.Store.YES));
writer.addDocument(doc);
writer.close();

...

Para buscar en el indice:
// search index
Query q = new QueryParser(Version.LUCENE_4_9, "title", analyser).parse("My*content");
IndexReader reader = IndexReader.open(dir);
IndexSearcher searcher = new IndexSearcher(reader);
TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
```

## Test it using mongodb-vm


El projecto viene con vagrant y VM para crear una mongodb y hacer pruebas.
En el directorio del proyecto, abrir la terminal y ejecutar:
```text
cd mongovm
vagrant up
```
