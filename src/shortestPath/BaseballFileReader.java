package shortestPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads provided text file and returns an ArrayList of Connection objects
 * @author Sean Kan
 */
public class BaseballFileReader
{
    /**
     * Parses a csv file and generates an ArrayList of Connection objects for each row in the file
     * @param filePath   The name of the file
     * @return   An ArrayList of Connection objects
     */
    public ArrayList<Connection> readFile(String filePath)
    {
        ArrayList<Connection> citiesEdge = new ArrayList<Connection>();

        try
        {
            Scanner reader = new Scanner(new File(filePath));
            while (reader.hasNextLine())
            {
                String input = reader.nextLine();
                String[] temp = input.split(",");
                Connection entry = new Connection(temp[0].trim(),temp[1].trim(),Integer.parseInt(temp[2].trim()));
                citiesEdge.add(entry);
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return citiesEdge;
    }
}

