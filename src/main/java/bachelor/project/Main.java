package bachelor.project;

import bachelor.project.graph.CYAML;
import bachelor.project.graph.CalculateCellCenterCoordinates;
import bachelor.project.graph.network.CGraph;
import bachelor.project.graph.network.IGraph;
import bachelor.project.ui.RoutePainter;
import bachelor.project.ui.VirtualEarthTileFactoryInfo;
import bachelor.project.vehicle.VehicleSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(final String[] p_args) throws Exception {

        // Visualisation
        JXMapViewer mapViewer = new JXMapViewer();

        // Create a TileFactoryInfo
        TileFactoryInfo veInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        DefaultTileFactory tileFactory = new DefaultTileFactory(veInfo);
        mapViewer.setTileFactory(tileFactory);

        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(8);


        // Set the focus and zoom
        GeoPosition magicRoundabout = new GeoPosition(51.562842, -1.771473);
        mapViewer.setZoom(0);
        mapViewer.setAddressLocation(magicRoundabout);


        String routeString = "[51.56235, -1.771391], [51.562391000000005, -1.77140275], [51.562432, -1.7714145000000001], [51.562473, -1.77142625], [51.562514, -1.771438], [51.562516125, -1.771501], [51.56251825, -1.7715640000000001], [51.562520375, -1.771627], [51.5625225, -1.77169], [51.562524625, -1.771753], [51.56252675, -1.7718159999999998], [51.562528875, -1.771879], [51.562531, -1.771942], [51.5625168, -1.7720064], [51.5625026, -1.7720708], [51.5624884, -1.7721352], [51.562474200000004, -1.7721996]";
//        Pattern pattern = Pattern.compile("\\[([0-9\\., -]*)\\]");
        Pattern pattern = Pattern.compile("\\[(([0-9\\.-]*), ([0-9\\.-]*))\\]");

        Matcher matcher = pattern.matcher(routeString);


        ArrayList al = new ArrayList();


        while (matcher.find()) {
//            System.out.println(matcher.group(1));
            //al.add(matcher.group(1));

            ArrayList<Double> singleCoordinate = new ArrayList<Double>();
            double singleCoordinateLon = ( Double.parseDouble( matcher.group(3) ) );
            double singleCoordinateLat = ( Double.parseDouble( matcher.group(2) ) );

            singleCoordinate.add(singleCoordinateLat);
            singleCoordinate.add(singleCoordinateLon);
            al.add(singleCoordinate);

        }

//        System.out.println(al.size());
//        System.out.println(al);


        // Display the viewer in a JFrame
        JFrame frame = new JFrame("Magic Roundabout");
        frame.getContentPane().add(mapViewer);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Create a track from the geo-positions
        final List<GeoPosition> track = new ArrayList<GeoPosition>();
        System.out.println("OUTPUT");

        int i = 0;
        while (i < al.size()) {


            System.out.println(
                    al.get(i).toString().getClass()
            );

//            Pattern pattern2 = Pattern.compile("\\[([0-9.-]*), ([0-9.-]*)\\]");
//            Pattern pattern2 = Pattern.compile("[0-9]*");
//            Matcher matcher2 = pattern2.matcher(al.get(i).toString());

//            System.out.println(matcher2.group());
/*
            System.out.println(
                    Double.parseDouble( matcher2.group(3) )
            );
            System.out.println(
                    Double.parseDouble( matcher2.group(2) )
            );
*/

            i++;
        }

//                    track.add(new GeoPosition( a[0],a[1] ));

/*

        RoutePainter routePainter = new RoutePainter(track);

        // Set the focus
//        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);

        // Create waypoints from the geo-positions
        Set<Waypoint> waypoints = new HashSet<Waypoint>();

        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(routePainter);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);

*/


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
        /*
        l_configuration.edges().forEach( edge -> {
            System.out.println( edge.from() + " --> " + edge.to() );
            System.out.println( edge.getCells() );
            System.out.println();
        } );
        */
        // VehicleSource source_X = new VehicleSource(X0, int maxAttempts, int probability, IGraph graphObject);
//        int m_maxattempts = 100000;
        int m_maxattempts = 10;
/*
 * nodes' numbers vs. streets' names
 * roundabout is defined in YAML file as follows
 * 1X --> Drove Road (= 6 o'clock entry/exit)           ??, medium traffic (45%)
 * 2X --> Fleming Way (= 8 o'clock entry/exit)          from town centre, medium to higher traffic (55%)
 * 3X --> County Road (= 10 o'clock entry/exit)         from bus + train stations, medium to higher traffic (55%)
 * 4X --> Shrivenham Road (= 1 o'clock entry/exit)      residential area, low traffic (30%)
 * 5X --> Queens Drive (= 4 o'clock entry/exit)         from motorway, high traffic (65%)
 *
 * X0 --> source node
 * X9 --> sink node
 */
        //System.out.println( l_graph.getEndNodes() );
        VehicleSource source_1 = new VehicleSource(l_graph.node(10), m_maxattempts, 45, l_graph);
        source_1.generateVehicles();

        /*
        VehicleSource source_2 = new VehicleSource(l_graph.node(20), m_maxattempts, 55, l_graph);
        VehicleSource source_3 = new VehicleSource(l_graph.node(30), m_maxattempts, 55, l_graph);
        VehicleSource source_4 = new VehicleSource(l_graph.node(40), m_maxattempts, 30, l_graph);
        VehicleSource source_5 = new VehicleSource(l_graph.node(50), m_maxattempts, 65, l_graph);
        */

    }
}
