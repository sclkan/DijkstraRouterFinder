package shortestPath;

import java.util.*;

/**
 * Class that contains vertex data, adjacency list, and supporting methods that are useful for various graph algorithms
 * @param <E>
 */
class FHvertex<E>
{
    public static Stack<Integer> keyStack = new Stack<Integer>();
    public static final int KEY_ON_DATA = 0, KEY_ON_DIST = 1;
    public static int keyType = KEY_ON_DATA;
    public static final double INFINITY = Double.MAX_VALUE;

    public HashSet< Pair<FHvertex<E>, Double> > adjList
            = new HashSet< Pair<FHvertex<E>, Double> >();

    public E data;

    public double dist;
    public FHvertex<E> nextInPath;  // for client-specific info

    /**
     * Creates an object of type FHvertex with an initial
     * data value passed as argument,
     * the distance to source of interest set to INFINITY to indicate unknown,
     * and nextInPath to FHvertex as null to indicate unknown.
     * @param x  The object being represented.
     */
    public FHvertex( E x )
    {
        data = x;
        dist = INFINITY;
        nextInPath = null;
    }

    /**
     * Constructor with no parameters
     */
    public FHvertex() { this(null); }

    /**
     * Adds a vertex to the adjacency list
     * @param neighbor  Vertex
     * @param cost  Edge cost (a double)
     */
    public void addToAdjList(FHvertex<E> neighbor, double cost)
    {
        adjList.add( new Pair<FHvertex<E>, Double> (neighbor, cost) );
    }

    /**
     * Adds a vertex to the adjacency list
     * Converts cost to a double value
     * @param neighbor   Vertex
     * @param cost  Edge cost (an integer)
     */
    public void addToAdjList(FHvertex<E> neighbor, int cost)
    {
        addToAdjList( neighbor, (double)cost );
    }

    /**
     * Compares two FHvertex objects
     * Allows user to compare the date member or the dist member of the vertices
     * @param rhs   Object to compare with
     * @return  True if same value
     */
    public boolean equals(Object rhs)
    {
        FHvertex<E> other = (FHvertex<E>)rhs;
        switch (keyType)
        {
            case KEY_ON_DIST:
                return (dist == other.dist);
            case KEY_ON_DATA:
                return (data.equals(other.data));
            default:
                return (data.equals(other.data));
        }
    }

    /**
     * Obtain hashcode value of either a distance member or data member
     * @return  A hashcode
     */
    public int hashCode()
    {
        switch (keyType)
        {
            case KEY_ON_DIST:
                Double d = dist;
                return (d.hashCode());
            case KEY_ON_DATA:
                return (data.hashCode());
            default:
                return (data.hashCode());
        }
    }

    /**
     * Display the adjacency list of a vertex
     */
    public void showAdjList()
    {
        Iterator< Pair<FHvertex<E>, Double> > iter ;
        Pair<FHvertex<E>, Double> pair;

        System.out.print( "Adj List for " + data + ": ");
        for (iter = adjList.iterator(); iter.hasNext(); )
        {
            pair = iter.next();
            System.out.print( pair.first.data + "("
                    + String.format("%3.1f", pair.second)
                    + ") " );
        }
        System.out.println();
    }

    /**
     * Set key type
     * @param whichType  Key type (0 for data, 1 for distance)
     * @return  True when the key has been set
     */
    public static boolean setKeyType( int whichType )
    {
        switch (whichType)
        {
            case KEY_ON_DATA:
            case KEY_ON_DIST:
                keyType = whichType;
                return true;
            default:
                return false;
        }
    }

    /**
     * Adds key to stack
     */
    public static void pushKeyType() { keyStack.push(keyType); }

    /**
     * Removes key from stack
     */
    public static void popKeyType() { keyType = keyStack.pop(); };
}

/**
 * Class that allow our algorithms to define and manipulate edges
 * @param <E>
 */
class FHedge<E> implements Comparable< FHedge<E> >
{
    FHvertex<E> source, dest;
    double cost;

    /**
     * Constructor
     * @param src   Source
     * @param dst   Destination
     * @param cst  Edge cost (a double)
     */
    FHedge( FHvertex<E> src, FHvertex<E> dst, Double cst)
    {
        source = src;
        dest = dst;
        cost = cst;
    }

    /**
     * Constructor
     * @param src  Source
     * @param dst  Destination
     * @param cst  Edge cost (an integer)
     */
    FHedge( FHvertex<E> src, FHvertex<E> dst, Integer cst)
    {
        this (src, dst, cst.doubleValue());
    }

    /**
     * Constructor with no parameters
     */
    FHedge()
    {
        this(null, null, 1.);
    }

    /**
     * Compares the cost of two FHedge objects
     * @param rhs  Object to compare with
     * @return  -1 if the edge cost of the current object is lower, 1 if higher, and 0 if the same
     */
    public int compareTo( FHedge<E> rhs )
    {
        return (cost < rhs.cost? -1 : cost > rhs.cost? 1 : 0);
    }
}

/**
 * A public graph data class that our client will use
 * @param <E>
 */
public class FHgraph<E>
{
    // the graph data is all here --------------------------
    protected HashSet< FHvertex<E> > vertexSet;

    /**
     * Constructor with no parameters
     */
    public FHgraph ()
    {
        vertexSet = new HashSet< FHvertex<E> >();
    }

    /**
     * Constructor that takes 1 parameter
     * @param edges  A list of FHedge objects
     */
    public FHgraph( FHedge<E>[] edges )
    {
        this();
        int k, numEdges;
        numEdges = edges.length;

        for (k = 0; k < numEdges; k++)
            addEdge( edges[k].source.data,
                    edges[k].dest.data,  edges[k].cost);
    }

    /**
     * Creates vertex list and add destination to source's adjacency list
     * @param source Source
     * @param dest  Destination
     * @param cost  Edge cost (a double)
     */
    public void addEdge(E source, E dest, double cost)
    {
        FHvertex<E> src, dst;

        // put both source and dest into vertex list(s) if not already there
        src = addToVertexSet(source);
        dst = addToVertexSet(dest);

        // add dest to source's adjacency list
        src.addToAdjList(dst, cost);
    }

    /**
     * Creates vertex list and add destination to source's adjacency list
     * Converts cost to a double value if necessary
     * @param source   Source
     * @param dest  Destination
     * @param cost  Edge cost (an integer)
     */
    public void addEdge(E source, E dest, int cost)
    {
        addEdge(source, dest, (double)cost);
    }

    /**
     * Instantiates an FHvertex object internally which holds the client data
     * Puts the new object into the vertexSet of the graph
     * @param x  Client's data to add to the vertex set
     * @return  A reference to the constructed FHvertex
     */
    public FHvertex<E> addToVertexSet(E x)
    {
        FHvertex<E> retVal, vert;
        boolean successfulInsertion;
        Iterator< FHvertex<E> > iter;

        // save sort key for client
        FHvertex.pushKeyType();
        FHvertex.setKeyType(FHvertex.KEY_ON_DATA);

        // build and insert vertex into master list
        retVal = new FHvertex<E>(x);
        successfulInsertion = vertexSet.add(retVal);

        if ( successfulInsertion )
        {
            FHvertex.popKeyType();  // restore client sort key
            return retVal;
        }

        // the vertex was already in the set, so get its ref
        for (iter = vertexSet.iterator(); iter.hasNext(); )
        {
            vert = iter.next();
            if (vert.equals(retVal))
            {
                FHvertex.popKeyType();  // restore client sort key
                return vert;
            }
        }

        FHvertex.popKeyType();  // restore client sort key
        return null;   // should never happen
    }

    /**
     * Displays the adjacency table of the graph
     */
    public void showAdjTable()
    {
        Iterator< FHvertex<E> > iter;

        System.out.println( "------------------------ ");
        for (iter = vertexSet.iterator(); iter.hasNext(); )
            (iter.next()).showAdjList();
        System.out.println();
    }

    /**
     * Clones the vertex set
     * @return Cloned data
     */
    public HashSet< FHvertex<E> > getVertSet()
    {
        return (HashSet< FHvertex<E> > )vertexSet.clone();
    }

    /**
     * Clears the vertex set
     */
    public void clear()
    {
        vertexSet.clear();
    }

    /**
     * Clears existing vertex set and makes a new set based on input
     * @param edges  Arraylist of FHedge objects
     */
    public void setGraph( ArrayList< FHedge<E> > edges )
    {
        int k, numEdges;
        numEdges = edges.size();

        clear();
        for (k = 0; k < numEdges; k++)
            addEdge( edges.get(k).source.data,
                    edges.get(k).dest.data,  edges.get(k).cost);
    }

    /**
     * Implements Dijkstra's algorithm
     * @param x   Origin
     * @param avoidCities   Arraylist of cities to avoid
     * @return  Results from the algorithm
     */
    public boolean dijkstra(E x, ArrayList<E> avoidCities)
    {
        FHvertex<E> w, s, v;
        ArrayList<FHvertex<E>> verticesToAvoid =  new ArrayList<>(); //A list of FHvertex objects that we need to avoid
        Pair<FHvertex<E>, Double> edge;
        Iterator< FHvertex<E> > iter;
        Iterator< Pair<FHvertex<E>, Double> > edgeIter;
        Double costVW;
        Deque< FHvertex<E> > partiallyProcessedVerts
                = new LinkedList< FHvertex<E> >();

        //Find the corresponding vertices for data points in the citiesToAvoid array
        //Obtain vertex only if it is a valid city
        for (E cities:avoidCities)
        {
            if (getVertexWithThisData(cities)!=null)
                verticesToAvoid.add(getVertexWithThisData(cities));
        }

        s = getVertexWithThisData(x);
        if (s == null)
            return false;

        // initialize the vertex list and place the starting vert in p_p_v queue
        for (iter = vertexSet.iterator(); iter.hasNext(); )
            iter.next().dist = FHvertex.INFINITY;

        s.dist = 0;
        partiallyProcessedVerts.addLast(s);

        // outer dijkstra loop
        while( !partiallyProcessedVerts.isEmpty() )
        {
            v = partiallyProcessedVerts.removeFirst();

            // inner dijkstra loop: for each vert adj to v, lower its dist
            // to s if you can
            for (edgeIter = v.adjList.iterator(); edgeIter.hasNext(); )
            {
                edge = edgeIter.next();
                w = edge.first;
                costVW = edge.second;
                //add w only if it is not a vertex that we need to avoid
                if (v.dist + costVW < w.dist && !verticesToAvoid.contains(w))
                {
                    w.dist = v.dist + costVW;
                    w.nextInPath = v;

                    // w now has improved distance, so add w to PPV queue
                    partiallyProcessedVerts.addLast(w);
                }
            }
        }
        return true;
    }

    /**
     * Applies dijkstra, prints path
     * @param x1   Origin
     * @param x2   Destination
     * @param avoidCities  Arraylist of cities to avoid
     * @return  The shortest path from origin to destination
     */
    public boolean showShortestPath(E x1, E x2, ArrayList<E> avoidCities)
    {
        FHvertex<E> start, stop, vert;
        Stack< FHvertex<E> > pathStack = new Stack< FHvertex<E> >();

        start = getVertexWithThisData(x1);
        stop = getVertexWithThisData(x2);
        if (start == null || stop == null)
            return false;

        dijkstra(x1, avoidCities);

        if (stop.dist == FHvertex.INFINITY)
        {
            System.out.println("No path exists.");
            return false;
        }

        for (vert = stop; vert != start; vert = vert.nextInPath)
            pathStack.push(vert);
        pathStack.push(vert);

        System.out.println( "Cost of shortest path from " + x1
                + " to " + x2 + ": " + stop.dist );
        while (true)
        {
            vert = pathStack.pop();
            if ( pathStack.empty() )
            {
                System.out.println( vert.data );
                break;
            }
            System.out.print( vert.data + " -> ");
        }
        return true;
    }

    /**
     * Applies dijkstra, prints distances
     * @param x   Origin
     * @param avoidCities   Arraylist of cities to avoid
     * @return The shortest distance between origin and other vertices
     */
    public boolean showDistancesTo(E x, ArrayList<E> avoidCities)
    {

        Iterator< FHvertex<E> > iter;
        FHvertex<E> vert;

        if (!dijkstra(x, avoidCities))
            return false;

        for (iter = vertexSet.iterator(); iter.hasNext(); )
        {
            vert = iter.next();
            System.out.println( vert.data + " " + vert.dist);
        }
        System.out.println();
        return true;
    }

    /**
     * Helper that takes some data and returns the object in the master vertexSet
     * @param x   Data from client
     * @return  An FHvertex object in the master vertexSet that contains the inputted data
     */
    protected FHvertex<E> getVertexWithThisData(E x)
    {
        FHvertex<E> searchVert, vert;
        Iterator< FHvertex<E> > iter;

        // save sort key for client
        FHvertex.pushKeyType();
        FHvertex.setKeyType(FHvertex.KEY_ON_DATA);

        // build vertex with data = x for the search
        searchVert = new FHvertex<E>(x);


        // the vertex was already in the set, so get its ref
        for (iter = vertexSet.iterator(); iter.hasNext(); )
        {
            vert = iter.next();
            if (vert.equals(searchVert))
            {
                FHvertex.popKeyType();
                return vert;
            }
        }

        FHvertex.popKeyType();
        return null;   // not found
    }
}