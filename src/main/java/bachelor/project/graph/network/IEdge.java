package bachelor.project.graph.network;

import bachelor.project.vehicle.CVehicle;

import java.util.LinkedList;
import java.util.List;

/**
 * interface of edges
 */
public interface IEdge<T> {

    /**
     * returns the identifier of the source / start node
     * on which the edge starts
     *
     * @return node identifier
     */
    T from();

    /**
     * return the identifier of the target / end node
     * on which the edge points to
     *
     * @return node identifier
     */
    T to();

    /**
     * returns the weight of the edge
     *
     * @return weight
     */
    double weight();

    /**
     * sets the weight
     *
     * @param p_weight new weight of the edge
     * @return self reference
     */
    IEdge<T> weight(final double p_weight);

    /**
     * sets cells
     *
     * @param p_cellsList new list of cells assigned to the edge
     */
    void setCells(final List p_cellsList);

    /**
     * gets cells assigned to edge
     */
    List getCells();

    /**
     * make edge to lane
     *
     * @param p_edge
     * @param p_graph
     */
    void makeLane(final IEdge p_edge, final IGraph p_graph);

    /**
     * sets information about lane behaviour to edge
     *
     * @param p_laneInfo
     */
    void setLaneInfo(final LinkedList p_laneInfo);

    /**
     * return lane information of edge
     *
     * @return
     */
    LinkedList getLaneInfo();

    void occupyCell(int p_cellIndex, final CVehicle p_vehicle);

    boolean isOccupied(int p_cellIndex);

}