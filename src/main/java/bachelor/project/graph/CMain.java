package bachelor.project.graph;

import bachelor.project.graph.network.CGraph;
import bachelor.project.graph.network.IGraph;
import bachelor.project.graph.network.INode;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.util.Collection;

/**
 * main class for starting Jetty server on a Jar
 *
 * @see https://www.eclipse.org/jetty/documentation/9.3.x/embedding-jetty.html
 */
public final class CMain {



    /**
     * private ctor
     */
    private CMain() {
    }

    /**
     * main to run Jetty manually
     *
     * @param p_args arguments
     */
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

        // output of the node, edge and graph objects
        System.out.println("Knoten");
        System.out.println( l_configuration.nodes() );
        System.out.println("Kanten");
        System.out.println( l_configuration.edges() );
        System.out.println();

        // build graph
        final IGraph<Integer> l_graph = new CGraph<>( l_configuration.nodes(), l_configuration.edges() );
        System.out.println("Graph");
        System.out.println( l_graph );
        System.out.println();


        //



        // show neighbours
        System.out.println("Nachbarn");
        System.out.println( l_graph.neighbours( Integer.parseInt( l_cli.getOptionValue( "start", "11" ) ) ) );
        System.out.println();

        // show successors
        System.out.println("Nachfolger");
        System.out.println( l_graph.successors( Integer.parseInt( l_cli.getOptionValue( "start", "11" ) ) ) );
        System.out.println();

        // show end nodes
        System.out.println("Endknoten");
//        System.out.println( l_graph.getEndNodes() );
        final Collection<INode<Integer>> endNodes = l_graph.getEndNodes();
        System.out.println(endNodes);
        System.out.println();

        // calculate a route
        System.out.println(
            l_graph
                .route(
                    Integer.parseInt( l_cli.getOptionValue( "start", "10" ) ),
                    Integer.parseInt( l_cli.getOptionValue( "end", "29" ) )
                )
        );

    }

}
