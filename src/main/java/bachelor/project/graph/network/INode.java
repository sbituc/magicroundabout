package bachelor.project.graph.network;

/**
 * interface of graph nodes to create different types of nodes
 * @tparam T any type of the identifier
 */
public interface INode<T>
{

    /**
     * returns the identifier of the node
     *
     * @return identifier
     */
    T id();

    /**
     * x-position of the node (longitude)
     *
     * @return position
     */
    double xposition();

    /**
     * y-position of the node (latitude)
     *
     * @return position
     */
    double yposition();

}
