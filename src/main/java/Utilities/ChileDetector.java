package Utilities;

import javax.xml.soap.Text;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChileDetector {

    private static ChileDetector instance = null;

    private ChileDetector() {
        //loadCities();
        loadRegions();
    }

    public static ChileDetector getInstance() {
        if (instance == null) {
            synchronized (ChileDetector.class) {
                if (instance == null) {
                    instance = new ChileDetector();
                }
            }
        }
        return instance;
    }

    private List<String> cities = new ArrayList<>();
    private List<String> regions = new ArrayList<>();

    private ArrayList<ArrayList<String>> region = new ArrayList<>();
    private ArrayList<String> provincia;

    /**
     * Procedimiento que carga todas las ciudades chilenas a partir de un archivo adjunto "cities.txt".
     */
    private void loadCities() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File("src/cities")));
        }
        catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }

        String city;

        try {
            city = reader.readLine();
            while (city != null) {
                cities.add(city);
                city = reader.readLine();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Se leyeron las ciudades");
        System.out.println(cities);
    }

    /**
     * Carga las regiones en una lista de listas
     * Cada region tiene sus provincias y comunas las cuales son añadidas a la lista
     * de provincia, luego esa lista  que es una region es añadida a la lista de region.
     *
     * La primera palabra de cada lista de region contiene el nombre de la region
     */
    private void loadRegions() {
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new FileReader(new File("src/ciudadesDeChile.txt")));
    	}
    	catch(FileNotFoundException fnfe) {
    		fnfe.printStackTrace();
    	}

    	try {
    		String linea = reader.readLine();
    		while(linea != null){
                provincia = new ArrayList<>();
        		for (String ciudades2 : linea.split(",")){
        		    provincia.add(ciudades2);
        		}
        		region.add(provincia);
        		linea = reader.readLine();
    		}
    	}
    	catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
    }

    /**
     * Recibe el "location" de un usuario y por cada palabra busca en la lista de provincia.
     * En caso de que se encuentre el lugar en la primera lista de la lista region, significa que
     * es de la region metropolitana y por lo tanto se debe guardar la comuna, si no es asi se guarda la
     * region a la que pertenece el tweet.
     *
     * En caso que no se detecte ninguna provincia, comuna o region entonces por default se retorna
     * la ciudad de Santiago.
     */
    public String getRegion(String input) {
    	boolean esChileno = false;
        if(input != null) {
    		String cleaned = TextCleaner.textCleanerLocation(input);
    		for(String word : cleaned.split(",")) {
    			word = word.trim();
    			for (int i = 0; i < region.size(); i++) {
    			    for (int j = 0; j < region.get(i).size(); j++) {
    			        /******/
    			        if(word.contains("chile") || region.get(i).get(j).contains(word)){
    			            esChileno = true;
                        }
                        /*****/
    			        String regionLimpio = TextCleaner.textCleaner(region.get(i).get(j));
    			        if (word.contains(regionLimpio)) {
                            System.out.println("--------------------------");
    			            System.out.println("-----REGION ENCONTRADA----");
    			            System.out.println("PROVINCIA: " + word + " | REGION: " + region.get(i).get(0));
    			            System.out.println("--------------------------");
    			            if (i == 0) {
    			                if(j == 0) {
                                    return "Santiago";
                                }else{
                                    return region.get(0).get(j);
                                }
    			            } else {
    			                return region.get(i).get(0);
    			            }
    			        }
    			    }
                }
    		}
    		if(esChileno) {
                System.out.println("--------------------");
                System.out.println("REGION NO ENCONTRADA");
                System.out.println("--------------------");
                return "Santiago";
            }
        }
        System.out.println("--------------------");
        System.out.println("NO ES CHILENO: " + input);
        System.out.println("--------------------");
		return "null";
    }
    /*
    public boolean isChilean(String input) {
        String[] words = null;

        if (input != null) {
            String cleaned = TextCleaner.textCleaner(input);
            words = cleaned.split(" ");
            for (String word : words) {
                word = word.trim();
                System.out.println("Analizando palabra '" + word + "'");
                if (word.equals("chile") || cities.contains(word)) {
                    System.out.println("Es chileno");
                    return true;
                }
            }
        }
        return false;
    }
    */
}
