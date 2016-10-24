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

//        String routeString = "[51.56235, -1.771391], [51.562391000000005, -1.77140275], [51.562432, -1.7714145000000001], [51.562473, -1.77142625], [51.562514, -1.771438], [51.562516125, -1.771501], [51.56251825, -1.7715640000000001], [51.562520375, -1.771627], [51.5625225, -1.77169], [51.562524625, -1.771753], [51.56252675, -1.7718159999999998], [51.562528875, -1.771879], [51.562531, -1.771942], [51.5625168, -1.7720064], [51.5625026, -1.7720708], [51.5624884, -1.7721352], [51.562474200000004, -1.7721996]";
//        String routeString = "[51.56235, -1.771391], [51.562391000000005, -1.77140275], [51.562432, -1.7714145000000001], [51.562473, -1.77142625], [51.562514, -1.771438], [51.562529, -1.7715036666666668], [51.562544, -1.7715693333333333], [51.562559, -1.771635], [51.562615, -1.771587], [51.56263, -1.7715223333333334], [51.562645, -1.7714576666666666], [51.56266, -1.771393], [51.562686666666664, -1.7713488333333334], [51.562713333333335, -1.7713046666666667], [51.562740000000005, -1.7712605], [51.56276666666667, -1.7712163333333333], [51.56279333333333, -1.7711721666666667], [51.56282, -1.771128], [51.562856333333336, -1.7711531666666667], [51.56289266666667, -1.7711783333333333], [51.562929, -1.7712035], [51.56296533333333, -1.7712286666666668], [51.563001666666665, -1.7712538333333334], [51.563038, -1.771279], [51.563074, -1.771279], [51.563118, -1.771236], [51.563088500000006, -1.771171], [51.563059, -1.771106], [51.56302383333333, -1.7710813333333335], [51.56298866666667, -1.7710566666666667], [51.562953500000006, -1.771032], [51.562918333333336, -1.7710073333333334], [51.562883166666666, -1.7709826666666668], [51.562848, -1.770958], [51.562796500000005, -1.770984], [51.562745, -1.77101], [51.56272157142857, -1.7710562857142857], [51.562698142857144, -1.7711025714285713], [51.56267471428571, -1.771148857142857], [51.56265128571429, -1.7711951428571429], [51.56262785714286, -1.7712414285714286], [51.56260442857143, -1.7712877142857142], [51.562581, -1.771334], [51.562524, -1.771316], [51.562483, -1.7713135], [51.562442000000004, -1.7713109999999999], [51.562401, -1.7713085]";
//        String routeString = "[51.56235, -1.771391], [51.562391000000005, -1.77140275], [51.562432, -1.7714145000000001], [51.562473, -1.77142625], [51.562514, -1.771438], [51.562529, -1.7715036666666668], [51.562544, -1.7715693333333333], [51.562559, -1.771635], [51.562615, -1.771587], [51.56263, -1.7715223333333334], [51.562645, -1.7714576666666666], [51.56266, -1.771393], [51.562686666666664, -1.7713488333333334], [51.562713333333335, -1.7713046666666667], [51.562740000000005, -1.7712605], [51.56276666666667, -1.7712163333333333], [51.56279333333333, -1.7711721666666667], [51.56282, -1.771128], [51.562856333333336, -1.7711531666666667], [51.56289266666667, -1.7711783333333333], [51.562929, -1.7712035], [51.56296533333333, -1.7712286666666668], [51.563001666666665, -1.7712538333333334], [51.563038, -1.771279], [51.563074, -1.771279], [51.563118, -1.771236], [51.563088500000006, -1.771171], [51.563059, -1.771106], [51.563024, -1.77105], [51.562989, -1.770994], [51.5629578, -1.7709584], [51.5629266, -1.7709228], [51.5628954, -1.7708872], [51.5628642, -1.7708515999999999], [51.562833, -1.770816], [51.5628264, -1.7707566], [51.5628198, -1.7706971999999999], [51.5628132, -1.7706378], [51.5628066, -1.7705784]";
//        String routeString = "[51.56235, -1.771391], [51.562391000000005, -1.77140275], [51.562432, -1.7714145000000001], [51.562473, -1.77142625], [51.562514, -1.771438], [51.562557, -1.771453], [51.56256066666666, -1.7715185], [51.562564333333334, -1.771584], [51.562568, -1.7716495], [51.56257166666666, -1.771715], [51.562575333333335, -1.7717805], [51.562579, -1.771846], [51.5626215, -1.7718820000000002], [51.562664, -1.771918], [51.56270214285714, -1.771925142857143], [51.562740285714284, -1.7719322857142858], [51.56277842857143, -1.7719394285714287], [51.56281657142857, -1.7719465714285714], [51.56285471428571, -1.7719537142857142], [51.562892857142856, -1.7719608571428571], [51.562931, -1.771968], [51.562947, -1.772068], [51.562959, -1.7721246], [51.562971, -1.7721812], [51.562983, -1.7722378], [51.562995, -1.7722944]";
        String routeString = "[51.56235, -1.771391], [51.562391000000005, -1.77140275], [51.562432, -1.7714145000000001], [51.562473, -1.77142625], [51.562514, -1.771438], [51.562529, -1.7715036666666668], [51.562544, -1.7715693333333333], [51.562559, -1.771635], [51.562615, -1.771587], [51.56263, -1.7715223333333334], [51.562645, -1.7714576666666666], [51.56266, -1.771393], [51.562686666666664, -1.7713488333333334], [51.562713333333335, -1.7713046666666667], [51.562740000000005, -1.7712605], [51.56276666666667, -1.7712163333333333], [51.56279333333333, -1.7711721666666667], [51.56282, -1.771128], [51.562856333333336, -1.7711531666666667], [51.56289266666667, -1.7711783333333333], [51.562929, -1.7712035], [51.56296533333333, -1.7712286666666668], [51.563001666666665, -1.7712538333333334], [51.563038, -1.771279], [51.563074, -1.771279], [51.563118, -1.771236], [51.563134500000004, -1.7711645], [51.563151, -1.771093], [51.563179999999996, -1.7710416666666666], [51.563209, -1.7709903333333334]";

        Pattern pattern = Pattern.compile("\\[(([0-9\\.-]*), ([0-9\\.-]*))\\]");
        Matcher matcher = pattern.matcher(routeString);

        ArrayList al = new ArrayList();

        while (matcher.find()) {
            ArrayList<Double> singleCoordinate = new ArrayList<Double>();
            double singleCoordinateLon = ( Double.parseDouble( matcher.group(3) ) );
            double singleCoordinateLat = ( Double.parseDouble( matcher.group(2) ) );

            singleCoordinate.add(singleCoordinateLat);
            singleCoordinate.add(singleCoordinateLon);
            al.add(singleCoordinate);
        }

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("Magic Roundabout");
        frame.getContentPane().add(mapViewer);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Create a track from the geo-positions
        final List<GeoPosition> track = new ArrayList<GeoPosition>();
        System.out.println("OUTPUT");

        for (int i = 0; i < al.size(); i++) {
            ArrayList coords = (ArrayList) al.get(i);

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
