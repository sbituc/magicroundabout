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

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("Magic Roundabout");
        frame.getContentPane().add(mapViewer);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

/*
        //  1 -> 1 (fucked)
//        String routeString = "[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562567, -1.771462], [51.562608, -1.77149], [51.562655, -1.771412], [51.56268083333333, -1.7713633333333334], [51.562706666666664, -1.7713146666666666], [51.562732499999996, -1.771266], [51.562758333333335, -1.7712173333333334], [51.56278416666667, -1.7711686666666666], [51.56281, -1.77112], [51.56284933333333, -1.771143], [51.562888666666666, -1.771166], [51.562928, -1.7711890000000001], [51.56296733333333, -1.771212], [51.563006666666666, -1.771235], [51.563046, -1.771258], [51.563089, -1.771274], [51.56313, -1.77122], [51.5630965, -1.771161], [51.563063, -1.771102], [51.56302483333333, -1.7710775], [51.56298666666667, -1.771053], [51.562948500000005, -1.7710285], [51.562910333333335, -1.771004], [51.562872166666665, -1.7709795000000002], [51.562834, -1.770955], [51.562800333333335, -1.770978], [51.56276666666667, -1.771001], [51.562733, -1.771024], [51.56270683333334, -1.7710766666666666], [51.562680666666665, -1.7711293333333333], [51.5626545, -1.771182], [51.562628333333336, -1.7712346666666665], [51.562602166666665, -1.7712873333333332], [51.562576, -1.77134], [51.562537, -1.771323], [51.562493, -1.771319], [51.562449, -1.771315], [51.562405, -1.7713109999999999]";
        //  1 -> 2
//        String routeString = "[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562529, -1.7715184285714285], [51.562533, -1.7715818571428572], [51.562537, -1.7716452857142857], [51.562541, -1.7717087142857142], [51.562545, -1.7717721428571427], [51.562549000000004, -1.7718355714285714], [51.562553, -1.771899], [51.56253766666667, -1.7719598333333333], [51.562522333333334, -1.7720206666666667], [51.562507, -1.7720815], [51.562491666666666, -1.7721423333333333], [51.562476333333336, -1.7722031666666667]";
        //  1 -> 3
//        String routeString = "[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562531, -1.771518], [51.562537, -1.771581], [51.562543, -1.771644], [51.562554666666664, -1.7717086666666666], [51.562566333333336, -1.7717733333333334], [51.562578, -1.771838], [51.56261066666667, -1.7718676666666666], [51.562643333333334, -1.7718973333333334], [51.562676, -1.771927], [51.56271733333334, -1.7719346666666667], [51.56275866666667, -1.7719423333333333], [51.5628, -1.77195], [51.56284133333334, -1.7719576666666668], [51.56288266666667, -1.7719653333333334], [51.562924, -1.771973], [51.562946, -1.772069], [51.5629582, -1.7721242], [51.5629704, -1.7721794], [51.5629826, -1.7722346], [51.5629948, -1.7722898]";
        //  1 -> 4
//        String routeString = "[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562567, -1.771462], [51.562608, -1.77149], [51.562655, -1.771412], [51.56268083333333, -1.7713633333333334], [51.562706666666664, -1.7713146666666666], [51.562732499999996, -1.771266], [51.562758333333335, -1.7712173333333334], [51.56278416666667, -1.7711686666666666], [51.56281, -1.77112], [51.56284933333333, -1.771143], [51.562888666666666, -1.771166], [51.562928, -1.7711890000000001], [51.56296733333333, -1.771212], [51.563006666666666, -1.771235], [51.563046, -1.771258], [51.563089, -1.771274], [51.56313, -1.77122], [51.563137, -1.771159], [51.563144, -1.771098], [51.5631708, -1.7710554], [51.5631976, -1.7710128], [51.563224399999996, -1.7709702], [51.563251199999996, -1.7709276]";
        //  1 -> 5 (fucked)
//        String routeString = "[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562567, -1.771462], [51.562608, -1.77149], [51.562655, -1.771412], [51.56268083333333, -1.7713633333333334], [51.562706666666664, -1.7713146666666666], [51.562732499999996, -1.771266], [51.562758333333335, -1.7712173333333334], [51.56278416666667, -1.7711686666666666], [51.56281, -1.77112], [51.56284933333333, -1.771143], [51.562888666666666, -1.771166], [51.562928, -1.7711890000000001], [51.56296733333333, -1.771212], [51.563006666666666, -1.771235], [51.563046, -1.771258], [51.563089, -1.771274], [51.56313, -1.77122], [51.5630965, -1.771161], [51.563063, -1.771102], [51.563029, -1.7710564999999998], [51.562995, -1.771011], [51.5629608, -1.7709683999999999], [51.5629266, -1.7709257999999999], [51.5628924, -1.7708832], [51.5628582, -1.7708406], [51.562824, -1.770798], [51.562818166666666, -1.7707350000000002], [51.56281233333333, -1.770672], [51.5628065, -1.770609], [51.56280066666667, -1.7705460000000002], [51.562794833333335, -1.770483]";

        //  2 ->
//        String routeString = "[51.562552, -1.77127], [51.56255892307692, -1.7713271538461537], [51.562565846153845, -1.7713843076923077], [51.56257276923077, -1.7714414615384615], [51.562579692307686, -1.7714986153846153], [51.56258661538461, -1.7715557692307693], [51.562593538461535, -1.771612923076923], [51.56260046153846, -1.7716700769230769], [51.56260738461538, -1.7717272307692307], [51.56261430769231, -1.7717843846153847], [51.562621230769224, -1.7718415384615385], [51.56262815384615, -1.7718986923076923], [51.56263507692307, -1.7719558461538463], [51.562642, -1.772013], [51.56269399999999, -1.7719960000000001], [51.562746, -1.771979], [51.562782999999996, -1.7719768], [51.562819999999995, -1.7719746], [51.562857, -1.7719724], [51.562894, -1.7719702], [51.562931, -1.771968], [51.5629705, -1.7719285], [51.56301, -1.771889], [51.563019818181814, -1.7718296363636363], [51.56302963636364, -1.7717702727272728], [51.563039454545454, -1.7717109090909091], [51.56304927272727, -1.7716515454545454], [51.56305909090909, -1.771592181818182], [51.56306890909091, -1.7715328181818182], [51.56307872727273, -1.7714734545454547], [51.56308854545455, -1.771414090909091], [51.563098363636364, -1.7713547272727272], [51.56310818181819, -1.7712953636363638], [51.563118, -1.771236], [51.563134500000004, -1.7711645], [51.563151, -1.771093], [51.563179999999996, -1.7710416666666666], [51.563209, -1.7709903333333334]";


        String routeString = "[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562567, -1.771462], [51.562608, -1.77149], [51.562655, -1.771412], [51.56268083333333, -1.7713633333333334], [51.562706666666664, -1.7713146666666666], [51.562732499999996, -1.771266], [51.562758333333335, -1.7712173333333334], [51.56278416666667, -1.7711686666666666], [51.56281, -1.77112], [51.56284933333333, -1.771143], [51.562888666666666, -1.771166], [51.562928, -1.7711890000000001], [51.56296733333333, -1.771212], [51.563006666666666, -1.771235], [51.563046, -1.771258], [51.563089, -1.771274], [51.56313, -1.77122], [51.5630965, -1.771161], [51.563063, -1.771102], [51.563029, -1.7710564999999998], [51.562995, -1.771011], [51.5629608, -1.7709683999999999], [51.5629266, -1.7709257999999999], [51.5628924, -1.7708832], [51.5628582, -1.7708406], [51.562824, -1.770798], [51.562818166666666, -1.7707350000000002], [51.56281233333333, -1.770672], [51.5628065, -1.770609], [51.56280066666667, -1.7705460000000002], [51.562794833333335, -1.770483]";

        /*
        //  2 ->
        String routeString = "[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562529, -1.7715184285714285], [51.562533, -1.7715818571428572], [51.562537, -1.7716452857142857], [51.562541, -1.7717087142857142], [51.562545, -1.7717721428571427], [51.562549000000004, -1.7718355714285714], [51.562553, -1.771899], [51.56253766666667, -1.7719598333333333], [51.562522333333334, -1.7720206666666667], [51.562507, -1.7720815], [51.562491666666666, -1.7721423333333333], [51.562476333333336, -1.7722031666666667]";

Versuch 3/10:	[]
Versuch 5/10:	[[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562529, -1.7715184285714285], [51.562533, -1.7715818571428572], [51.562537, -1.7716452857142857], [51.562541, -1.7717087142857142], [51.562545, -1.7717721428571427], [51.562549000000004, -1.7718355714285714], [51.562553, -1.771899], [51.56253766666667, -1.7719598333333333], [51.562522333333334, -1.7720206666666667], [51.562507, -1.7720815], [51.562491666666666, -1.7721423333333333], [51.562476333333336, -1.7722031666666667]]
Versuch 7/10:	[[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562529, -1.7715184285714285], [51.562533, -1.7715818571428572], [51.562537, -1.7716452857142857], [51.562541, -1.7717087142857142], [51.562545, -1.7717721428571427], [51.562549000000004, -1.7718355714285714], [51.562553, -1.771899], [51.56253766666667, -1.7719598333333333], [51.562522333333334, -1.7720206666666667], [51.562507, -1.7720815], [51.562491666666666, -1.7721423333333333], [51.562476333333336, -1.7722031666666667]]
Versuch 8/10:	[[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562567, -1.771462], [51.562608, -1.77149], [51.562655, -1.771412], [51.56268083333333, -1.7713633333333334], [51.562706666666664, -1.7713146666666666], [51.562732499999996, -1.771266], [51.562758333333335, -1.7712173333333334], [51.56278416666667, -1.7711686666666666], [51.56281, -1.77112], [51.56284933333333, -1.771143], [51.562888666666666, -1.771166], [51.562928, -1.7711890000000001], [51.56296733333333, -1.771212], [51.563006666666666, -1.771235], [51.563046, -1.771258], [51.563089, -1.771274], [51.56313, -1.77122], [51.5630965, -1.771161], [51.563063, -1.771102], [51.563029, -1.7710564999999998], [51.562995, -1.771011], [51.5629608, -1.7709683999999999], [51.5629266, -1.7709257999999999], [51.5628924, -1.7708832], [51.5628582, -1.7708406], [51.562824, -1.770798], [51.562818166666666, -1.7707350000000002], [51.56281233333333, -1.770672], [51.5628065, -1.770609], [51.56280066666667, -1.7705460000000002], [51.562794833333335, -1.770483]]
Versuch 9/10:	[[51.562349, -1.771392], [51.5623842, -1.7714046], [51.562419399999996, -1.7714172000000001], [51.5624546, -1.7714298], [51.5624898, -1.7714424], [51.562525, -1.771455], [51.562567, -1.771462], [51.562608, -1.77149], [51.562655, -1.771412], [51.56268083333333, -1.7713633333333334], [51.562706666666664, -1.7713146666666666], [51.562732499999996, -1.771266], [51.562758333333335, -1.7712173333333334], [51.56278416666667, -1.7711686666666666], [51.56281, -1.77112], [51.56284933333333, -1.771143], [51.562888666666666, -1.771166], [51.562928, -1.7711890000000001], [51.56296733333333, -1.771212], [51.563006666666666, -1.771235], [51.563046, -1.771258], [51.563089, -1.771274], [51.56313, -1.77122], [51.5630965, -1.771161], [51.563063, -1.771102], [51.563029, -1.7710564999999998], [51.562995, -1.771011], [51.5629608, -1.7709683999999999], [51.5629266, -1.7709257999999999], [51.5628924, -1.7708832], [51.5628582, -1.7708406], [51.562824, -1.770798], [51.562818166666666, -1.7707350000000002], [51.56281233333333, -1.770672], [51.5628065, -1.770609], [51.56280066666667, -1.7705460000000002], [51.562794833333335, -1.770483]]



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
/*
        VehicleSource source_1 = new VehicleSource(l_graph.node(10), m_maxattempts, 45, l_graph);
        source_1.generateVehicles();
*/

        VehicleSource source_2 = new VehicleSource(l_graph.node(20), m_maxattempts, 55, l_graph);
        List route = source_2.generateVehicles();

        /*
        VehicleSource source_3 = new VehicleSource(l_graph.node(30), m_maxattempts, 55, l_graph);
        VehicleSource source_4 = new VehicleSource(l_graph.node(40), m_maxattempts, 30, l_graph);
        VehicleSource source_5 = new VehicleSource(l_graph.node(50), m_maxattempts, 65, l_graph);
        */




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
