package shortestPath;


/**
 * One object of Connections class represents a connection between 
 * two cities with a positive distance.
 * 
 * @author Foothill College, Bita Mazloom
 */
public class Connection
{
	// the name of the source city
	private String source;
	// the name of destination city
	private String destination;

	// the distance between cities
	private int distance;

	/**
	 * Constructor takes name for source and name of destination.
	 * The distance is the cost between them.
	 * @param source	name of the source
	 * @param dest	name of the destination
	 * @param dist	the cost between source and dest
	 */
	public Connection(String source, String dest, int dist)
	{	
		this.source = source;
		this.destination = dest;
		this.distance = dist;
	}


	/**
	 * Source city
	 * @return	the city name
	 */
	public String getSource()
	{	return source;	}


	/**
	 * Destination city
	 * @return	the city name
	 */
	public String getDestination()
	{	return destination;	}		
	

	/**
	 * Distance between source and destination city
	 * @return	int value of distance
	 */
	public int getDistance()
	{	return distance;	}


	/**
	 * Two connections are the same if the source and destinations have the same name.
	 * @return true if same connection. Otherwise false.
	 */
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Connection)
		{
			Connection otherConn = (Connection)other;
			if (otherConn.source.equals(this.source) &&
			    otherConn.destination.equals(this.destination))
				return true;
		}
		return false;
	}

	/**
	 * Returns a string representation with the item name and count.
	 */
	public String toString()
	{
		return String.format("%s, %s, %d\n", source, destination, distance);
	}
}
