package Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChileDetector {

    private static ChileDetector instance = null;

    private ChileDetector() {
        loadCities();
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
