package Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChileDetector {

    private static ChileDetector instance = null;

    private ChileDetector() {
        loadCities();
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
    private List<String[]> regions = new ArrayList<String[]>();

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
    		while(linea !=null) {
    			String[] ciudades= null;
        		ciudades = linea.split(",");
        		regions.add(ciudades);
        		linea = reader.readLine();
    		}
    	}
    	catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
    }
    
    public String getRegion(String input) {
    	String[] words = null;
    	if(input != null) {
    		String cleaned = TextCleaner.textCleaner(input);
    		words = cleaned.split(" ");
    		for(String word : words) {
    			word = word.trim();
    			for(String[] ciudades : regions) {
    				for(String ciudad : ciudades) {
    					String limpio = TextCleaner.textCleaner(ciudad);
    					String masLimpio = limpio.trim();
    					if(masLimpio.equals(word)) {
    						System.out.println("ciudad a comparar: "+masLimpio);
        					System.out.println("word a comparar: "+word);
    						System.out.println("pertenece a: "+ciudades[0]);
    						if(ciudades[0].equals("Metropolitana")) {
    							return word;
    						}
    						return ciudades[0];
    					}
    				}
    			}
    		}
    	}
		return "Metropolitana";
    }
    
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
}
