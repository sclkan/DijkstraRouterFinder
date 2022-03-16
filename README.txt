project folder:
sclkan-cs1c-project08/


Brief description of submitted files:

src/shortestPath/BaseballFileReader.java
    - Reads provided text file and returns an ArrayList of Connection objects

src/shortestPath/Connection.java
    - Class that defines the Connection object
    - One object of Connections class represents a connection between two cities with a positive distance.

src/shortestPath/FHgraph.java
    - Graph data structure that contains vertices, edges and adjacency list
    - Incorporates Dijkstra's algorithm which can compute the shortest path
    - Updated dijkstra() method that can now exclude specific cities from the shortest path

src/shortestPath/Pair.java
    - Pair data structure that is used to create an adjacency list
    - Stores vertices and their corresponding costs

src/shortestPath/RouteFinder.java
    - Class that allows the user to determine the shortest path between cities
    - Also enables them to avoid certain cities in the route
    - Dijkstra's algorithm is used to determine the shortest route

resources/BaseballCitiesEdgeCosts.txt
    - File that contains a list of distance in miles between cities
    - Each row contains the first city (start), second city (destination), and distance between them

resources/RUN.txt
    - Console output of RouteFinder.java
    - Test cases include: default cities, an empty citiesToAvoid list, a citiesToAvoid list that contains a destination,
    and different set of cities being filtered

README.txt
    - description of submitted files

Observations:

The number of cities we choose to filter could significantly impact the route and distance of our trip.
For example, if we want to travel from Miami to Los Angeles N, the shortest path is from
Miami -> Tampa -> St. Louis -> Denver -> Phoenix -> Los Angeles N, which is about 3,498 miles.
However, if we want to avoid Tampa, then the shortest route becomes Miami -> Cincinnati -> Kansas City ->
Denver -> Phoenix -> Los Angeles N, for a total of 3,567 miles.  Now, if we also add Cincinnati to the list, then we would
have to drive through Atlanta (Miami -> Atlanta -> Kansas City -> Denver -> Phoenix -> Los Angeles N), which increases
 the total distance to 3,719 miles.  But if we decide to skip Atlanta as well, this trip would not be feasible since
 we have eliminated all the possible routes from Miami to LA.