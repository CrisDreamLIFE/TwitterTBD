package luceneIndexation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class CreaSimple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Directory dir = FSDirectory.open(Paths.get("index"));
		     Analyzer analyzer = new StandardAnalyzer();
		     IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		     
		     // Create a new index in the directory, removing any
		     // previously indexed documents:
		     iwc.setOpenMode(OpenMode.CREATE);
		     //// Add new documents to an existing index: OpenMode.CREATE_OR_APPEND
		     
		     IndexWriter writer = new IndexWriter(dir, iwc);
		     String ruta = "libros/";
		     if(Files.isDirectory(Paths.get(ruta))) {
		    	 File directorioLibros = new File(ruta);
		    	 File[] libros = directorioLibros.listFiles();
		    	 Document doc = null;
		    	 
		    	 for(File f:libros) {
		    		 if(f.isFile() && f.canRead() && f.getName().endsWith(".txt")) {
		    			 doc = new Document();
		    			 doc.add(new StringField("path",f.toString(),Field.Store.YES));
		    			 doc.add(new TextField("contenido",new FileReader(f)));
		    			 if(writer.getConfig().getOpenMode() == OpenMode.CREATE) {
		    				 System.out.println("Indexando el archivo: "+f.getName());
		    				 writer.addDocument(doc);
		    			 }
		    			 else {
		    				 writer.updateDocument(new Term("path", f.toString()), doc);
		    			 }
		    			 
		    		 }
		    	 }
		     }
		     writer.close();		     
		}
		catch(IOException ioe) {
			Logger.getLogger(BuscaSimple.class.getName()).log(Level.SEVERE, null, ioe);
		}		
	}

}
