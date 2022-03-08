package shortestPath;

import java.util.ArrayList;
import java.util.Arrays;

public class RouteFinder
{
	private ArrayList<Connection> readRoutesBetweenCities;

	/**
	 * Parameterized constructor for an object of class RouteFinder.
	 * Reads in a CSV file of source and destination cities and
	 * converts it to a graph.
	 * @param filePath	The input file to parse.	
	 */
	public RouteFinder(String filePath)
	{
		// TODO: Define the class BaseballFileReader
		BaseballFileReader reader = new BaseballFileReader();

		// TODO: Define the readFile() method which reads the CSV (Comma Separated Value)
		//       file of connections between cities  and creates a specified ArrayList
		//       of Connection objects.
		// 
		// NOTE: Catch all exceptions in the readFile() method.
		//       That means readFile() method should not throw an exception.
		readRoutesBetweenCities = reader.readFile(filePath);

		// Check the size of the resulting ArrayList object.
		if (readRoutesBetweenCities.size() < 1) 
		{
			System.out.println("WARNING: The list of cities is empty.");
			return;
		} 

		System.out.printf("The list of cities has %d items. \n", readRoutesBetweenCities.size());
	}
	
	/**
	 * Accessor method returns the list of items read from input file.
	 * @return	the routes between cities.
	 */
	public ArrayList<Connection> getConnectionsBetweenCities()
	{
		return readRoutesBetweenCities;
	}
	
    public static void main(String[] args) throws Exception
    {
		// NOTE: Make sure to use *relative* path instead of specifying the entire path. 
		//       Otherwise, your program will result in run time errors when the instructor
		//       tests your implementation.
		final String FILEPATH = "resources/BaseballCitiesEdgeCosts.txt";

		RouteFinder seeker = new RouteFinder(FILEPATH);
		ArrayList<Connection> connections = seeker.getConnectionsBetweenCities();

		// displays the prices of items in the input file
		System.out.println("Connections found between cities hosting Baseball games:");
		System.out.println(connections);
		
		
        // build graph
        FHgraph<String> baseBallRoutes = new FHgraph<String>();
		connections.forEach(current ->
				baseBallRoutes.addEdge(current.getSource(),current.getDestination(), current.getDistance()));
        
        baseBallRoutes.showAdjTable();

        // dijkstra called from inside
        // TODO: Update dijsktra() method to avoid paths which go through the
        //       requested list of cities to avoid.
        final String startingCity = "San Francisco";
        final String [] destinations = {"Boston", "Chicago A", "Chicago N"};
        final ArrayList<String> citiesToAvoid = new ArrayList(Arrays.asList(new String[]{"Los Angeles X", "Milwaukee"}));
        System.out.println("Showing distances to " + startingCity);
        System.out.println("Cities to avoid are " + citiesToAvoid);
        baseBallRoutes.showDistancesTo(startingCity, citiesToAvoid);
        System.out.println();

        for (String current : destinations)
        {
			// TODO: Update showShortestPath() to call dijkstra with requested cities to avoid.
            baseBallRoutes.showShortestPath(startingCity, current, citiesToAvoid);
            System.out.println();
        }

		System.out.println("Done.");
    }
}