package Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Singleton
public class PoliticalFinder
{
    private static PoliticalFinder instance = null;

    protected PoliticalFinder()
    {
    }

    public static PoliticalFinder getInstance()
    {
        if(instance == null)
        {
            instance = new PoliticalFinder();
        }
        return instance;
    }

    private String finalPolitical;

    private List<String> Politicals = new ArrayList<String>();

    private List<String> PoliticalsFullName = new ArrayList<String>();

    public void loadPoliticals()
    {
        BufferedReader politicalReader = null;
        BufferedReader politicalsFullNameReader = null;
        try
        {
            politicalReader = new BufferedReader(new FileReader(new File(
                    "./src/artistsToLoad.txt")));
            politicalsFullNameReader = new BufferedReader(new FileReader(new File(
                    "./src/artistsToSave.txt")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String word;

        try {
            while ((word = politicalReader.readLine()) != null)
            {
                Politicals.add(word);
            }
            while ((word = politicalsFullNameReader.readLine()) != null)
            {
                PoliticalsFullName.add(word);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            politicalReader.close();
            politicalsFullNameReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void findPolitical(String input)
    {

        input = TextUtilities.limpiarAcentos(input);
        input = input.toLowerCase();
        input = input.trim();
        input = input.replaceAll("ñ", "n");
        input = input.replaceAll(",", " ");
        input = input.replaceAll("  ", " ");
        input = input.replaceAll("[^a-zA-Z0-9\\s]", "");

        //System.out.println("> Modified text: " + input);

        String[] words = input.split(" ");

        this.finalPolitical = "unknown";

        for (int i = 0; i < words.length; i++)
        {
            if (Politicals.contains(words[i]))
            {
                int index = Politicals.indexOf(words[i]);
                this.finalPolitical = PoliticalsFullName.get(index);
            }

        }

        System.out.println("> Politico: " + this.finalPolitical);

    }

    public String getFinalPolitical()
    {
        return this.finalPolitical;
    }


}
