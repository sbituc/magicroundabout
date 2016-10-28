package bachelor.project.graph.network;

import bachelor.project.graph.CalculateCellCenterCoordinates;
import bachelor.project.vehicle.CVehicle;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.awt.geom.Point2D;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public final class CEdge<T> implements IEdge<T>
{
    /**
     * identifier (source of the edge)
     */
    private final T m_sourceidentifier;
    /**
     * identifier (target of the edge)
     */
    private final T m_targetidentifier;
    /**
     * weight of the edge
     */
    private double m_weight;
    /**
     * cells assigned to edge
     * tuple of WGS84 coordinates
     */
    private List m_cellsList;
    /**
     * cells assigned to edge
     * tuple of hashcode of edge and position/index on edge
     */
    private LinkedList<ImmutablePair<IEdge,Integer>> m_laneInfo;
    /**
     * occupied cells of edge
     * key is index, value is CVehicle object
     */
    protected LinkedHashMap<Integer, CVehicle> m_occupiedCells;

    /**
     * ctor
     *
     * @param p_sourceidentifier source identifier of the edge
     * @param p_targetidentifier target identifiers of the edge
     */
    public CEdge( final T p_sourceidentifier, final T p_targetidentifier )
    {
        m_sourceidentifier = p_sourceidentifier;
        m_targetidentifier = p_targetidentifier;
    }

    @Override
    public final T from()
    {
        return m_sourceidentifier;
    }

    @Override
    public final double weight()
    {
        return m_weight;
    }

    @Override
    public final IEdge<T> weight( final double p_weight )
    {
        m_weight = p_weight;
        return this;
    }

    @Override
    public void setCells(List p_cellsList) { m_cellsList = p_cellsList; }

    @Override
    public List getCells() { return m_cellsList; }

    @Override
    public void makeLane(IEdge p_edge, IGraph p_graph) {
        INode m_fromNode = p_graph.node( p_edge.from() );
        INode m_toNode = p_graph.node( p_edge.to() );

        // LON = x-axis
        // LAT = y-axis
        Point2D p1 = new Point2D.Double( m_fromNode.xposition(), m_fromNode.yposition() );
        Point2D p2 = new Point2D.Double( m_toNode.xposition(), m_toNode.yposition() );

        List cellsList = CalculateCellCenterCoordinates.CellCenterCoordinates( p1, p2 );
        LinkedList<ImmutablePair<IEdge,Integer>> ll_laneInfo = new LinkedList<>();
        for (int i = 0; i < cellsList.size(); i++) {
            ll_laneInfo.add(ImmutablePair.of(p_edge,i));
        }

        this.setLaneInfo( ll_laneInfo );
        this.setCells( cellsList );
        this.weight( CalculateCellCenterCoordinates.distanceBetweenCoordinates( p1, p2 ) );
    }

    @Override
    public void setLaneInfo(LinkedList p_laneInfo) { m_laneInfo = p_laneInfo; }

    @Override
    public LinkedList getLaneInfo() { return m_laneInfo; }

    @Override
    public final T to() {
        return m_targetidentifier;
    }

    /**
     * overload the toString method
     * to get information about the edge content
     *
     * @return string representation
     */
    @Override
    public final String toString()
    {
        return MessageFormat.format( "{0} --{1}--> {2}", m_sourceidentifier, m_weight, m_targetidentifier );
    }

    /**
     * overload hashcode for checking equality of edge,
     * edges are equal if the source and target node are equal
     *
     * @return hashcode
     */
    @Override
    public final int hashCode()
    {
        // get the hashcode from source & target identifier
        // and multiply it with a large prim number, because for example
        // a identifier is Integer and the source identifier is 2 and target identifier 3
        // than the edge from node 1 to 4 and 2 to 3 are equal, but in this case these nodes
        // are not equal, so the prim number avoid the number collection
        return 	911 * m_sourceidentifier.hashCode() + 313 * m_targetidentifier.hashCode();
    }

    /**
     * check edge equality
     *
     * @param p_object object for checking
     * @return boolean flag if edges are equal
     */
    @Override
    public boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IEdge<?> ) && ( p_object.hashCode() == this.hashCode() );
    }

    /**
     * occupies (CVehicle as value) or frees (null as value) cell on lane
     * @param p_cellIndex   int
     * @param p_vehicle     CVehicle or null
     */
    public void occupyCell(int p_cellIndex, final CVehicle p_vehicle){
        if (p_vehicle != null && m_occupiedCells.get(p_cellIndex) != null) throw new IllegalStateException("Cell is already taken!");
        m_occupiedCells.put(p_cellIndex, p_vehicle);
    }

    /**
     * check if cell is occupied
     * @param p_cellIndex
     * @return boolen flag if cell is occupied or not
     */
    public boolean isOccupied(int p_cellIndex) {
        if (m_occupiedCells.get(p_cellIndex) == null) return false;
        else return true;
    }

}
