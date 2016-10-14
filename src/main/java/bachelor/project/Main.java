package bachelor.project;

import bachelor.project.graph.CYAML;
import bachelor.project.graph.CalculateCellCenterCoordinates;
import bachelor.project.graph.network.CGraph;
import bachelor.project.graph.network.CNode;
import bachelor.project.graph.network.IGraph;
import bachelor.project.vehicle.VehicleSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.awt.geom.Point2D;

public class Main {

    public static void main(final String[] p_args) throws Exception {
        // --- define CLI options --------------------------------------------------------------------------------------

        final Options l_clioptions = new Options();
        l_clioptions.addOption("help", false, "shows this information");
        l_clioptions.addOption("yaml", true, "YAML graph file");
        l_clioptions.addOption("start", true, "start ID");
        l_clioptions.addOption("end", true, "end ID");

        final CommandLine l_cli;
        try {
            l_cli = new DefaultParser().parse(l_clioptions, p_args);
        } catch (final Exception l_exception) {
            System.err.println("command-line arguments parsing error");
            System.exit(-1);
            return;
        }

        // --- read yaml file ------------------------------------------------------------------------------------------

        final CYAML l_configuration = new CYAML( l_cli.getOptionValue( "yaml", "bachelor/project/graph/network.yml" ) );
        // build graph
        final IGraph<Integer> l_graph = new CGraph<>( l_configuration.nodes(), l_configuration.edges() );
        //assign cells to all edges
        l_configuration.edges().forEach( edge -> {
            // LON = x-axis
            // LAT = y-axis

            Point2D p1 = new Point2D.Double( l_graph.node( edge.from() ).xposition(), l_graph.node( edge.from() ).yposition() );
            Point2D p2 = new Point2D.Double( l_graph.node( edge.to() ).xposition(), l_graph.node( edge.to() ).yposition() );

            edge.setCells( CalculateCellCenterCoordinates.CellCenterCoordinates( p1, p2 ) );
        } );

        l_configuration.edges().forEach( edge -> {
            System.out.println( edge.from() + " --> " + edge.to() );
            System.out.println( edge.getCells() );
            System.out.println();
        } );
        /**
         * VehicleSource source_X = new VehicleSource(X0, int maxAttempts, int probability, IGraph graphObject);
         *
         */
        int m_maxattempts = 100000;
/*
 * nodes' numbers vs. streets' names
 * roundabout is defined in YAML file as follows
 * 1X --> Drove Road (= 6 o'clock entry/exit)           ??, medium traffic (45%)
 * 2X --> Fleming Way (= 8 o'clock entry/exit)          from town centre, medium to higher traffic (55%)
 * 3x --> County Road (= 10 o'clock entry/exit)         from bus + train stations, medium to higher traffic (55%)
 * 4x --> Shrivenham Road (= 1 o'clock entry/exit)      residential area, low traffic (30%)
 * 5x --> Queens Drive (= 4 o'clock entry/exit)         from motorway, high traffic (65%)
 */
        VehicleSource source_1 = new VehicleSource(l_graph.node(10), m_maxattempts, 45, l_graph);
        VehicleSource source_2 = new VehicleSource(l_graph.node(20), m_maxattempts, 55, l_graph);
        VehicleSource source_3 = new VehicleSource(l_graph.node(30), m_maxattempts, 55, l_graph);
        VehicleSource source_4 = new VehicleSource(l_graph.node(40), m_maxattempts, 30, l_graph);
        VehicleSource source_5 = new VehicleSource(l_graph.node(50), m_maxattempts, 65, l_graph);

    }
}
