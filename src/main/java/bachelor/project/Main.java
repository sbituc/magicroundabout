package bachelor.project;

import bachelor.project.graph.CYAML;
import bachelor.project.graph.network.CGraph;
import bachelor.project.graph.network.CNode;
import bachelor.project.graph.network.IGraph;
import bachelor.project.vehicle.VehicleSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

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
        // show neighbours
        System.out.println( l_graph.neighbours( Integer.parseInt( l_cli.getOptionValue( "start", "13" ) ) ) );

        // calculate a route
        System.out.println(
                l_graph
                        .route(
                                Integer.parseInt( l_cli.getOptionValue( "start", "10" ) ),
                                Integer.parseInt( l_cli.getOptionValue( "end", "49" ) )
                        )
        );

/*
 *  VehicleSource source_X = new VehicleSource(X0, int maxAttempts, int probability);
 *
 */
//        VehicleSource source_1 = new VehicleSource(10, 100000, 45);
//        VehicleSource source_1 = new VehicleSource(Integer.parseInt( l_cli.getOptionValue( "start", "10" ) ), 100000, 45);
    }
}
