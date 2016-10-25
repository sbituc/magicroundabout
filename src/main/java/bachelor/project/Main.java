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
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
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

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("Magic Roundabout");
        frame.getContentPane().add(mapViewer);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


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
        //assign cells to all edges for navigation
        l_configuration.edges().forEach( edge -> {
            // LON = x-axis
            // LAT = y-axis

            Point2D p1 = new Point2D.Double( l_graph.node( edge.from() ).xposition(), l_graph.node( edge.from() ).yposition() );
            Point2D p2 = new Point2D.Double( l_graph.node( edge.to() ).xposition(), l_graph.node( edge.to() ).yposition() );

            edge.setCells( CalculateCellCenterCoordinates.CellCenterCoordinates( p1, p2 ) );

            edge.weight( CalculateCellCenterCoordinates.distanceBetweenCoordinates( p1, p2 ) );

        } );

        System.out.println("Graph");
        System.out.println( l_graph );
        System.out.println();

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
        VehicleSource source_1 = new VehicleSource(l_graph.node(10), m_maxattempts, 45, l_graph);
        VehicleSource source_2 = new VehicleSource(l_graph.node(20), m_maxattempts, 55, l_graph);
        VehicleSource source_3 = new VehicleSource(l_graph.node(30), m_maxattempts, 55, l_graph);
        VehicleSource source_4 = new VehicleSource(l_graph.node(40), m_maxattempts, 30, l_graph);
        VehicleSource source_5 = new VehicleSource(l_graph.node(50), m_maxattempts, 65, l_graph);

        List route1 = source_1.generateVehicles();
        List route2 = source_2.generateVehicles();
        List route3 = source_3.generateVehicles();
        List route4 = source_4.generateVehicles();
        List route5 = source_5.generateVehicles();

        // Create a track from the geo-positions
        final List<GeoPosition> track = new ArrayList<GeoPosition>();

        for (int i = 0; i < route1.size(); i++) {
            ArrayList coords = (ArrayList) route1.get(i);

            double lat = (double) coords.get(0);
            double lon = (double) coords.get(1);

            track.add(new GeoPosition( lat,lon ));
        }

//        System.out.println(track);

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

    }
}
