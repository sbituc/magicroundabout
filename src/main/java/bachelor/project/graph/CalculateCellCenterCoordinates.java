package bachelor.project.graph;


import bachelor.project.graph.network.CGraph;
import bachelor.project.graph.network.IGraph;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class CalculateCellCenterCoordinates {

    private static double distBetweenWGS84Coordinates(double p_lon1, double p_lat1, double p_lon2, double p_lat2) {
        // formula according to https://www.kompf.de/gps/distcalc.html --> Verbesserte Methode
        // sufficient precise enough for distances < 1 km

        double distLat = 110950; // in m, aprox. distance in km between 1° of latitude in Swindon area
        double distLon = 68980; // in m, aprox. distance in km between 1° of longitude in Swindon area
        double m_lat1 = p_lat1;
        double m_lon1 = p_lon1;
        double m_lat2 = p_lat2;
        double m_lon2 = p_lon2;

        double dist = Math.sqrt( Math.pow((distLon * (p_lon1 - p_lon2)),2) + Math.pow((distLat * (p_lat1 - p_lat2)),2) );

        return dist;
    }

    private static int calculateQuantityCells(double p_distance) {
        double cellSize = 4.0; // Cell size is 4 m
        double m_distance = p_distance;

        int qtyCells = (int) (m_distance/cellSize);

        return qtyCells;
    }

    public static List CellCenterCoordinates(double p_lon1, double p_lat1, double p_lon2, double p_lat2) {
        double m_lat1 = p_lat1;
        double m_lon1 = p_lon1;
        double m_lat2 = p_lat2;
        double m_lon2 = p_lon2;

        // LON = x-axis
        // LAT = y-axis
        Point2D p1 = new Point2D.Double(m_lon1, m_lat1);
        Point2D p2 = new Point2D.Double(m_lon2, m_lat2);

//        double coordDistance = distBetweenWGS84Coordinates(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        int quantityCells = calculateQuantityCells( distBetweenWGS84Coordinates(p1.getX(), p1.getY(), p2.getX(), p2.getY()) );
        ArrayList<ArrayList<Double>> coordinatesList = new ArrayList<ArrayList<Double>>();

//        System.out.println(coordDistance);
//        System.out.println(quantityCells);

        double incLon = (p2.getX() - p1.getX()) / quantityCells;
//        System.out.println(incLon);
        double ingLat = (p2.getY() - p1.getY()) / quantityCells;
//        System.out.println(ingLat);

        for (int i=0; i <= quantityCells; i++) {
            ArrayList<Double> singleCoordinate = new ArrayList<Double>();
            double singleCoordinateLon = (p1.getX() + i * incLon);
            double singleCoordinateLat = (p1.getY() + i * ingLat);

//            System.out.println("Punkt" + i + ": " + singleCoordinateX + ", " + singleCoordinateY);
            singleCoordinate.add(singleCoordinateLat);
            singleCoordinate.add(singleCoordinateLon);
            coordinatesList.add(singleCoordinate);

        }

        System.out.println(coordinatesList);

        return null;
    }

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

/*
//  - id  : 10
//    to  : [ 11 ]
        double lat10 = 51.562350;
        double lon10 = -1.771391;
        double x10 = 585163;
        double y10 = 5713079;
//    utmz: 30u

//  - id  : 11
//    to  : [ 12, 112, 28 ]
        double lat11 = 51.562514;
        double lon11 = -1.771438;
        double x11 = 585160;
        double y11 = 5713097;
//    utmz: 30u

//	  - id  : 12
//	    to  : [ 13, 27, 112 ]
        double lat12 = 51.562559;
        double lon12 = -1.771635;
        double x12 = 585146;
        double y12 = 5713102;
//	    utmz: 30u

//	    - id  : 13
//	    to  : [ 14 ]
        double lat13 = 51.562615;
        double lon13 = -1.771587;
        double x13 = 585149;
        double y13 = 5713108;
//	    utmz: 30u
*/

        // LON = x-axis
        // LAT = y-axis
        Point2D p1 = new Point2D.Double(lon12, lat12);
        Point2D p2 = new Point2D.Double(lon13, lat13);

        System.out.println(CellCenterCoordinates(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
    }

}
