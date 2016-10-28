package bachelor.project;

import bachelor.project.graph.CYAML;
import bachelor.project.graph.network.CGraph;
import bachelor.project.graph.network.IGraph;
import bachelor.project.ui.VirtualEarthTileFactoryInfo;
import bachelor.project.vehicle.CVehicle;
import bachelor.project.vehicle.VehicleSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


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
        //assign cells to all edges for navigation, painting etc.
        l_configuration.edges().forEach( edge -> {
            edge.makeLane(edge, l_graph);
        } );


        // VehicleSource source_X = new VehicleSource(X0, int maxAttempts, int probability, IGraph graphObject);
//        int m_maxattempts = 100000;
        int m_maxattempts = 10;
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

        VehicleSource source_1 = new VehicleSource(l_graph.node(10), m_maxattempts, 45, l_graph);
        VehicleSource source_2 = new VehicleSource(l_graph.node(20), m_maxattempts, 55, l_graph);
        VehicleSource source_3 = new VehicleSource(l_graph.node(30), m_maxattempts, 55, l_graph);
        VehicleSource source_4 = new VehicleSource(l_graph.node(40), m_maxattempts, 30, l_graph);
        VehicleSource source_5 = new VehicleSource(l_graph.node(50), m_maxattempts, 65, l_graph);

        source_1.generateVehicles();
        source_2.generateVehicles();
        source_3.generateVehicles();
        source_4.generateVehicles();
        source_5.generateVehicles();


        // TODO remove random generation
/*
        Random rand = new Random(System.currentTimeMillis());
        int randomNumber = rand.nextInt(100);
        List route = null;
        if (randomNumber < 20) route = source_1.generateVehicles();
        else if (randomNumber >= 20 && randomNumber < 42) route = source_2.generateVehicles();
        else if (randomNumber >= 42 && randomNumber < 65) route = source_3.generateVehicles();
        else if (randomNumber >= 65 && randomNumber < 75) route = source_4.generateVehicles();
        else if (randomNumber >= 75 && randomNumber < 100) route = source_5.generateVehicles();

        // Create a track from the geo-positions
        final List<GeoPosition> track = new ArrayList<GeoPosition>();

        for (int i = 0; i < route.size(); i++) {
            ArrayList coords = (ArrayList) route.get(i);

            double lat = (double) coords.get(0);
            double lon = (double) coords.get(1);

            track.add(new GeoPosition( lat,lon ));

        }

//        System.out.println(track);

        RoutePainter routePainter = new RoutePainter(track);


        // visualize graph (I)
        final LinkedHashMap<Integer,List<GeoPosition>> graphMap = new LinkedHashMap<>();
        l_configuration.edges().forEach( edge -> {
            final List<GeoPosition> MapTrack = new ArrayList<GeoPosition>();
            MapTrack.add(new GeoPosition( l_graph.node(edge.from()).yposition(),l_graph.node(edge.from()).xposition() ));
            MapTrack.add(new GeoPosition( l_graph.node(edge.to()).yposition(),l_graph.node(edge.to()).xposition() ));
            graphMap.put( edge.hashCode(), MapTrack );
        } );
        System.out.println(graphMap);




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

        painters.add(routePainter);
//        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
*/

         //Liste aller Vehicles aus den VehicleFactorys
        List<CVehicle> allVehicles = new ArrayList();

        allVehicles.addAll(source_1.getVehicles());
        allVehicles.addAll(source_2.getVehicles());
        allVehicles.addAll(source_3.getVehicles());
        allVehicles.addAll(source_4.getVehicles());
        allVehicles.addAll(source_5.getVehicles());

        System.out.println(

                allVehicles.size()

        );


//        while (!allVehicles.isEmpty()) {
            allVehicles.forEach( vehicle -> {
                vehicle.move();
                System.out.println(
                vehicle.getPositionCoordinates()
                );
            } );
//        }

    }
}
