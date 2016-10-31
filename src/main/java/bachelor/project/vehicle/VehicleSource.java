package bachelor.project.vehicle;

import bachelor.project.graph.network.IEdge;
import bachelor.project.graph.network.IGraph;
import bachelor.project.graph.network.INode;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
 * nodes' numbers vs. streets' names
 * roundabout is defined in YAML file as follows
 * 1X --> Drove Road (= 6 o'clock entry/exit)           ??, medium traffic (45%)
 * 2X --> Fleming Way (= 8 o'clock entry/exit)          from town centre, medium to higher traffic (55%)
 * 3x --> County Road (= 10 o'clock entry/exit)         from bus + train stations, medium to higher traffic (55%)
 * 4x --> Shrivenham Road (= 1 o'clock entry/exit)      residential area, low traffic (30%)
 * 5x --> Queens Drive (= 4 o'clock entry/exit)         from motorway, high traffic (65%)
 *
 *  VehicleSource source_X = new VehicleSource(X0, int maxAttempts, int probability, IGraph graphObject);
 */

public class VehicleSource extends CVehicleFactory {

    private final INode<Integer> m_startNode;
    private final int m_maxAttemptsOfGeneration;
    private final int m_probabilityOfGeneration;
    private final IGraph m_graph;
    private Random m_randomizer;

    public VehicleSource(final INode<Integer> p_startNode, int p_maxAttemptsOfGeneration, int p_probabilityOfGeneration, final IGraph p_graph) {
        m_startNode = p_startNode;
        m_maxAttemptsOfGeneration = p_maxAttemptsOfGeneration;
        m_probabilityOfGeneration = p_probabilityOfGeneration;
        m_graph = p_graph;
        m_randomizer = new Random(System.currentTimeMillis());
    }

    @Override
    public void generateVehicles() {
        for (int i = 0; i < m_maxAttemptsOfGeneration; i++) {
            generateVehicle();
        }
    }

    @Override
    public CVehicle generateVehicle() {
        /*
         * could be modified to create different types of vehicles (if classes are present)
         * vehicles.add(new Car());
         * vehicles.add(new Lorry());
         * vehicles.add(new Bus());
         * vehicles.add(new Motorbike());
         */
        int randomInt;
//        Random myRandomizer = new Random(System.currentTimeMillis());
        List<INode> endNodesList = m_graph.getEndNodesList();

        List randomRouteCells = null;
        LinkedList randomRouteLanes = null;
        randomInt = this.m_randomizer.nextInt(200);
        if (randomInt < m_probabilityOfGeneration) {
            List<IEdge> randomRoute = generateRandomRoute(endNodesList, this.m_randomizer);
            randomRouteCells = convertedRouteToCells( randomRoute );
            randomRouteLanes = convertedRouteToLanes( randomRoute );

//            vehicles.add( new Car( 2, randomRouteCells, randomRouteLanes, pickColor(m_startNode) ) );
            return (new Car( 2, randomRouteCells, randomRouteLanes, pickColor(m_startNode) ));
        }
        else return null;
    }

    /**
     * converts a given path of graph's edges to a route with lane information
     *
     * @param p_randomRoute
     * @return
     */
    private LinkedList<ImmutablePair<IEdge,Integer>> convertedRouteToLanes (List<IEdge> p_randomRoute) {
        LinkedList<ImmutablePair<IEdge,Integer>> ll_routeLaneList = new LinkedList<>();
        for ( IEdge edge : p_randomRoute ) {
            ll_routeLaneList.addAll( edge.getLaneInfo() );
        }
        return ll_routeLaneList;
    }

    /**
     * converts a given path of graph's edges to a route of coordiate tupels
     *
     * @param p_randomRoute
     * @return
     */
    private List convertedRouteToCells(List<IEdge> p_randomRoute) {
        List routeCellList = new ArrayList();
        for ( IEdge edge : p_randomRoute ) {
            routeCellList.addAll( edge.getCells() );
        }
        return routeCellList;
    }

    /**
     * Returns the shortest path (list of edges) between start node and a random end node
     *
     * @param p_endNodes
     * @param p_random
     * @return
     */
    private List<bachelor.project.graph.network.IEdge> generateRandomRoute(List<INode> p_endNodes, Random p_random) {
        return m_graph.route( m_startNode, p_endNodes.get( p_random.nextInt( p_endNodes.size() ) ) );
    }

    /**
     *
     * @param p_startNode
     * @return Color
     */
    private Color pickColor(INode p_startNode) {
        Color vehicleColor;
        if ((int) Math.floor( m_startNode.id()/10 ) == 1) vehicleColor = Color.RED;
        else if ((int) Math.floor( m_startNode.id()/10 ) == 2) vehicleColor = Color.GREEN;
        else if ((int) Math.floor( m_startNode.id()/10 ) == 3) vehicleColor = Color.BLUE;
        else if ((int) Math.floor( m_startNode.id()/10 ) == 4) vehicleColor = Color.MAGENTA;
        else if ((int) Math.floor( m_startNode.id()/10 ) == 5) vehicleColor = Color.YELLOW;
        else  vehicleColor = Color.BLACK;
        return vehicleColor;
    }

}
