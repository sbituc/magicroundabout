package bachelor.project;

import bachelor.project.graph.CYAML;
import bachelor.project.graph.network.CGraph;
import bachelor.project.graph.network.IGraph;
import bachelor.project.ui.FancyWaypointRenderer;
import bachelor.project.ui.MyWaypoint;
import bachelor.project.ui.VirtualEarthTileFactoryInfo;
import bachelor.project.vehicle.CVehicle;
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
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


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


        // Visualisation
        JXMapViewer mapViewer = new JXMapViewer();


        // Create a TileFactoryInfo
        TileFactoryInfo veInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        DefaultTileFactory tileFactory = new DefaultTileFactory(veInfo);
        mapViewer.setTileFactory(tileFactory);

        // Setup local file cache
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        LocalResponseCache.installResponseCache(veInfo.getBaseURL(), cacheDir, false);


        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(8);
       //create card1
        JPanel card1 = new JPanel();
        card1.add(new JLabel("Startpunkt:"));
        card1.add(new JComboBox());
        card1.add(new JLabel("Zielpunkt:"));
        card1.add(new JComboBox());
        card1.add(new JButton("Route zeichnen"));
        card1.add(new JButton("Random-Route zeichnen"));
        card1.add(new JButton("fahren"));


        //create card2
        JPanel card2 = new JPanel();
        card2.add(new JButton("Knoten zeichnen"));
        card2.add(new JButton("Graphen zeichnen"));


        // Set the focus and zoom
        GeoPosition magicRoundabout = new GeoPosition(51.562842, -1.771473);
        mapViewer.setZoom(0);
        mapViewer.setAddressLocation(magicRoundabout);

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("Magic Roundabout");
        frame.getContentPane().add(mapViewer);
        //add cards
        frame.add(card1, BorderLayout.NORTH);
        frame.add(card2, BorderLayout.SOUTH);
//        frame.setSize(1000, 1000);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        // --- read yaml file ------------------------------------------------------------------------------------------

        final CYAML l_configuration = new CYAML( l_cli.getOptionValue( "yaml", "bachelor/project/graph/network.yml" ) );
        // build graph
        final IGraph<Integer> l_graph = new CGraph<>( l_configuration.nodes(), l_configuration.edges() );
        //assign cells to all edges for navigation, painting etc.
        l_configuration.edges().forEach( edge -> {
            edge.makeLane(edge, l_graph);
        } );


        int m_maxSteps = 1000;

        // VehicleSource source_X = new VehicleSource(X0, int maxAttempts, int probability, IGraph graphObject);
//        int m_maxAttempts = 100000;
        int m_maxAttempts = 1;
/*
 * nodes' numbers vs. streets' names
 * roundabout is defined in YAML file as follows
 * 1X --> Drove Road (= 6 o'clock entry/exit)           ??, medium traffic (generation probability 45%)
 * 2X --> Fleming Way (= 8 o'clock entry/exit)          from town centre, medium to higher traffic (generation probability 55%)
 * 3X --> County Road (= 10 o'clock entry/exit)         from bus + train stations, medium to higher traffic (generation probability 55%)
 * 4X --> Shrivenham Road (= 1 o'clock entry/exit)      residential area, low traffic (generation probability 30%)
 * 5X --> Queens Drive (= 4 o'clock entry/exit)         from motorway, high traffic (generation probability 65%)
 *
 * X0 --> source node
 * X9 --> sink node
 */

        // delay init of sources by 10 ms for different random output
        VehicleSource source_1 = new VehicleSource(l_graph.node(10), m_maxAttempts, 45, l_graph);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VehicleSource source_2 = new VehicleSource(l_graph.node(20), m_maxAttempts, 55, l_graph);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VehicleSource source_3 = new VehicleSource(l_graph.node(30), m_maxAttempts, 55, l_graph);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VehicleSource source_4 = new VehicleSource(l_graph.node(40), m_maxAttempts, 30, l_graph);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VehicleSource source_5 = new VehicleSource(l_graph.node(50), m_maxAttempts, 65, l_graph);


        //Liste aller Vehicles aus den VehicleFactorys
        List<CVehicle> allVehicles = new CopyOnWriteArrayList<CVehicle>();
        // Create markers for vehicles from the geo-positions
//        Set<Waypoint> vehicleMarkers = new HashSet<Waypoint>();
        Set<MyWaypoint> colouredVehicleMarkers = new HashSet<MyWaypoint>();

        int i = 0;
        while (i < m_maxSteps) {
            i++;

            // "generate" vehicles every second step
            if( (i%2) != 0 ) {
                allVehicles.add(source_1.generateVehicle());
                allVehicles.add(source_2.generateVehicle());
                allVehicles.add(source_3.generateVehicle());
                allVehicles.add(source_4.generateVehicle());
                allVehicles.add(source_5.generateVehicle());
            }
//            vehicleMarkers.clear();
            colouredVehicleMarkers.clear();

            // remove possible null elements from list
            allVehicles.removeAll(Collections.singleton(null));
            if (allVehicles.isEmpty()) continue;

            for (CVehicle vehicle : allVehicles) {
                if (vehicle.canRemove()) allVehicles.remove(vehicle);
                else {
                    vehicle.move();
//                    vehicleMarkers.add( new DefaultWaypoint( vehicle.getPositionCoordinates().get(0), vehicle.getPositionCoordinates().get(1) ));
                    GeoPosition vehiclePositon = new GeoPosition( vehicle.getPositionCoordinates().get(0), vehicle.getPositionCoordinates().get(1) );
                    colouredVehicleMarkers.add( new MyWaypoint(vehicle.get_label(), vehicle.getColor(), vehiclePositon ) );
                }
            }
            // Create a waypoint painter that takes all the vehicle markers
//            WaypointPainter<Waypoint> vehicleMarkerPainter = new WaypointPainter<Waypoint>();
//            vehicleMarkerPainter.setWaypoints(vehicleMarkers);

            // create coloured waypoint layer for vehicles
            WaypointPainter<MyWaypoint> vehicleMarkerPainter = new WaypointPainter<MyWaypoint>();
            vehicleMarkerPainter.setWaypoints(colouredVehicleMarkers);
            vehicleMarkerPainter.setRenderer(new FancyWaypointRenderer());

            // Create a compound painter that uses painter
            List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();

            painters.add(vehicleMarkerPainter);

            CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
            mapViewer.setOverlayPainter(painter);

            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //painter.removePainter(painter);
            //mapViewer.setOverlayPainter(painter);
        }


/*

        // Create a track from the geo-positions
        final List<GeoPosition> track = new ArrayList<GeoPosition>();

        for (int i = 0; i < route.size(); i++) {
            ArrayList coords = (ArrayList) route.get(i);

            double lat = (double) coords.get(0);
            double lon = (double) coords.get(1);

            track.add(new GeoPosition( lat,lon ));

        }

//        System.out.println(track);

//        RoutePainter routePainter = new RoutePainter(track);


        // visualize graph (I)
        final LinkedHashMap<Integer,List<GeoPosition>> graphMap = new LinkedHashMap<>();
        l_configuration.edges().forEach( edge -> {
            final List<GeoPosition> MapTrack = new ArrayList<GeoPosition>();
            MapTrack.add(new GeoPosition( l_graph.node(edge.from()).yposition(),l_graph.node(edge.from()).xposition() ));
            MapTrack.add(new GeoPosition( l_graph.node(edge.to()).yposition(),l_graph.node(edge.to()).xposition() ));
            graphMap.put( edge.hashCode(), MapTrack );
        } );
        System.out.println(graphMap);
*/
/*

        // Create waypoints from the geo-positions
        Set<Waypoint> waypoints = new HashSet<Waypoint>();
        // create waypoint for each graph node
        l_configuration.nodes().forEach( node -> {
            waypoints.add( new DefaultWaypoint( node.yposition(), node.xposition()  ) );
        } );

        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();

        // visualize graph (II)
        List<RoutePainter> painterList = new ArrayList<>();
        for ( Map.Entry<Integer, List<GeoPosition>> entry : graphMap.entrySet() ) {
            int key = entry.getKey();
            List<GeoPosition> value = entry.getValue();

            painterList.add(new RoutePainter(value));
        }
        painterList.forEach( singlePainter -> {
//            painters.add(singlePainter);
        } );
//        painters.add(routePainter);
//        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
*/

    }
}
