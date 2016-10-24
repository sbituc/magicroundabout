package bachelor.project.graph;


import bachelor.project.graph.network.CGraph;
import bachelor.project.graph.network.CNode;
import bachelor.project.graph.network.IGraph;
import bachelor.project.graph.network.INode;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculateCellCenterCoordinates {

    private static double distBetweenWGS84Coordinates(double p_lon1, double p_lat1, double p_lon2, double p_lat2) {
        // formula according to https://www.kompf.de/gps/distcalc.html --> Verbesserte Methode
        // sufficient precise enough for distances < 1 km

        double distLat = 110950; // in m, aprox. distance in km between 1° of latitude in Swindon area
        double distLon = 68980; // in m, aprox. distance in km between 1° of longitude in Swindon area

        return Math.sqrt( Math.pow((distLon * (p_lon1 - p_lon2)),2) + Math.pow((distLat * (p_lat1 - p_lat2)),2) );
    }

    private static int calculateQuantityCells(double p_distance) {
        double cellSize = 4.0; // Cell size is 4 m

        return (int) (p_distance /cellSize);
    }

    public static double distanceBetweenCoordinates(Point2D p_p1, Point2D p_p2) {
        return distBetweenWGS84Coordinates(p_p1.getX(), p_p1.getY(), p_p2.getX(), p_p2.getY());
    }

    public static List CellCenterCoordinates(Point2D p_p1, Point2D p_p2) {

        int quantityCells = calculateQuantityCells( distBetweenWGS84Coordinates(p_p1.getX(), p_p1.getY(), p_p2.getX(), p_p2.getY()) );
        // force one use of the for-loop in CellCenterCoordinates method and avoid division by zero
        if (quantityCells == 0) { quantityCells = 1; }

        double incLon = (p_p2.getX() - p_p1.getX()) / quantityCells;
        double ingLat = (p_p2.getY() - p_p1.getY()) / quantityCells;

        ArrayList<ArrayList<Double>> coordinatesList = new ArrayList<ArrayList<Double>>();

        for (int i = 0; i < quantityCells; i++) {
            ArrayList<Double> singleCoordinate = new ArrayList<Double>();
            double singleCoordinateLon = (p_p1.getX() + i * incLon);
            double singleCoordinateLat = (p_p1.getY() + i * ingLat);

            singleCoordinate.add(singleCoordinateLat);
            singleCoordinate.add(singleCoordinateLon);
            coordinatesList.add(singleCoordinate);
        }

//        System.out.println(coordinatesList);

        return coordinatesList;
    }
/*
//    public static void main(String[] args) {
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

        // --- show help -----------------------------------------------------------------------------------------------
        if (l_cli.hasOption("help")) {
            new HelpFormatter().printHelp(new java.io.File(CMain.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName(), l_clioptions);
            System.exit(0);
            return;
        }

        // --- read yaml file ------------------------------------------------------------------------------------------

        final CYAML l_configuration = new CYAML( l_cli.getOptionValue( "yaml", "bachelor/project/graph/network.yml" ) );
        // build graph
        final IGraph<Integer> l_graph = new CGraph<>( l_configuration.nodes(), l_configuration.edges() );

        l_configuration.edges().forEach( edge -> {
//            System.out.println( l_graph.node( edge.from() ) + "\t-->\t" + l_graph.node( edge.to() ) );
            // LON = x-axis
            // LAT = y-axis
            Point2D p1 = new Point2D.Double(l_graph.node( edge.from() ).xposition(), l_graph.node( edge.from() ).yposition());
            Point2D p2 = new Point2D.Double(l_graph.node( edge.to() ).xposition(), l_graph.node( edge.to() ).yposition());

            edge.setCells( CellCenterCoordinates(p1, p2) );

        } );

        l_configuration.edges().forEach( edge -> {
            System.out.println( edge.from() + " --> " + edge.to() );
            System.out.println( edge.getCells() );
            System.out.println();
        } );

    }
*/
}
