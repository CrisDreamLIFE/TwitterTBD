package Utilities;

import java.text.Normalizer;

public class TextCleaner {

    public static String textCleaner(String input) {

        //System.out.println("String original: '" + input + "'");

        String cleaned;
        cleaned = Normalizer.normalize(input, Normalizer.Form.NFD);
        cleaned = cleaned.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        cleaned = cleaned.toLowerCase();
        cleaned = cleaned.trim();
        cleaned = cleaned.replaceAll(",", " ");
        cleaned = cleaned.replaceAll("/", " ");
        cleaned = cleaned.replaceAll("-", " ");
        cleaned = cleaned.replaceAll("  ", " ");
        cleaned = cleaned.replaceAll("[^a-zA-Z0-9\\s]", "");

        //System.out.println("String limpiado: '" + cleaned + "'");
        return cleaned;
    }

    public static String textCleanerLocation(String input){
        String cleaned;
        cleaned = Normalizer.normalize(input, Normalizer.Form.NFD);
        cleaned = cleaned.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        cleaned = cleaned.toLowerCase();
        cleaned = cleaned.trim();
        cleaned = cleaned.replaceAll("/", " ");
        cleaned = cleaned.replaceAll("-", " ");
        cleaned = cleaned.replaceAll("  ", " ");
        cleaned = cleaned.replaceAll("[^a-zA-Z0-9-,\\s]", "");
        return cleaned;
    }
}
