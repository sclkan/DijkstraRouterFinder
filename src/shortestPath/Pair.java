package shortestPath;

/**
 * Pair data structure for creating an adjacency list
 * @param <E>  Generic object
 * @param <F>  Generic object
 */
public class Pair<E, F>
{
    public E first;
    public F second;

    /**
     * Constructor
     * @param x   Generic object (vertex in this case)
     * @param y  Generic object  (cost in this case)
     */
    public Pair(E x, F y) { first = x; second = y; }

    /**
     * Compares two objects
     * @param rhs   Object to compare with
     * @return   True if identical
     */
    public boolean equals(Object rhs)
    {
        Pair<E, F> other;
        other = (Pair<E, F>)rhs;
        return first.equals(other.first);
    }

    /**
     * Obtain hashcode value of an object
     * @return  Hashcode
     */
    public int hashCode()
    {
        return first.hashCode();
    }
}