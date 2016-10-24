package bachelor.project.graph;

import bachelor.project.graph.network.CEdge;
import bachelor.project.graph.network.CNode;
import bachelor.project.graph.network.IEdge;
import bachelor.project.graph.network.INode;
import org.yaml.snakeyaml.Yaml;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public final class CYAML
{
    /**
     * set with nodes
     */
    private final Set<INode<Integer>> m_nodes;
    /**
     * set with edges
     */
    private final Set<IEdge<Integer>> m_edges;


    /**
     * ctor
     *
     * @param p_input input filename
     * @throws IOException thrown on io exception
     * @throws URISyntaxException thrown on icorrect path format
     */
    @SuppressWarnings( "unchecked" )
    public CYAML(final String p_input) throws IOException, URISyntaxException
    {
        // get all data from the YAML file
        final Map<String, Object> l_data = (Map<String, Object>) new Yaml().load( CYAML.getResourceURL( new File( p_input ) ).openStream() );

        // read the node part of the file and build up all node objects,
        // put the objects into the set and based on the node hashcode
        // methode the set will "uniquify" the nodes, so all duplicates
        // in the YAML configuration will be removed automatically,
        // at last create an unmodifiable set to avoid any modifcation
        // of the nodes within the set (no node addition or deletion is possible)

        m_nodes = Collections.unmodifiableSet(
            // get the "nodes" element from the ui but if it is not exists return an empty list to avoid null-pointer-exception
            ( (List<?>) l_data.getOrDefault( "nodes", Collections.EMPTY_LIST ) )
                .stream()
                // cast each element in the list to a ui (YAML file contains maps within the node list)
                .map( i -> (Map<String,Object>) i )
                // build node object from the ui item
//                .map( i -> new CNode<>( (Integer) i.get( "id" ), (Double) (i.get( "lon" ))*-10000000, (Double) (i.get( "lat" ))*10000000 ) )
//                  import WGS84 coordinates
                .map( i -> new CNode<>( (Integer) i.get( "id" ), (Double) (i.get( "lon" )), (Double) (i.get( "lat" )) ) )
//                  Import UTM coordinates
//               .map( i -> new CNode<>( (Integer) i.get( "id" ), (Integer) (i.get( "utme" )), (Integer) (i.get( "utmn" )) ) )
                // and convert all data into a set
                .collect(Collectors.toSet() )
        );

        // for the edges we iterate over the node list again and extract the edge identifier values
        m_edges = Collections.unmodifiableSet(
            // get the "nodes" element from the ui but if it is not exists return an empty list to avoid null-pointer-exception
            ( (List<?>) l_data.getOrDefault( "nodes", Collections.EMPTY_LIST ) )
                .stream()
                // cast each element in the list to a ui (YAML file contains maps within the node list)
                .map( i -> (Map<String,Object>) i )
                // get the "to" node identifiers from the node structure
                .flatMap( i -> {
                    // get the node identifier
                    final int l_id = (Integer) i.get( "id" );
                    // iterate over all "to" identifier and create am edge ( the "to" field is optional,
                    // so it is not exist return an empty list to avoid null-pointer-exception
                    return ((List<Integer>) i.getOrDefault( "to", Collections.EMPTY_LIST  ) )
                                .stream().map( j -> new CEdge<>( l_id, j ) );
                } )
                // collect all edges into a set
                .collect(Collectors.toSet() )
        );
    }

    /**
     * returns a file from a resource (Jar) or if it exists from filesystem
     *
     * @param p_file file relative to the CMain
     * @return URL of file or null
     * @throws MalformedURLException on incorrect URL
     * @throws URISyntaxException on incorrect URI syntax
     */
    private static URL getResourceURL(final File p_file ) throws MalformedURLException, URISyntaxException
    {
        try
        {
            if ( p_file.exists() )
                return p_file.toURI().normalize().toURL();
            return CYAML.class.getClassLoader().getResource( p_file.toString().replace( File.separator, "/" ) ).toURI().normalize().toURL();
        }
        catch ( final Exception l_exception )
        {
            System.err.println( MessageFormat.format( "source [{0}] not found", p_file ) );
            throw l_exception;
        }
    }

    /**
     * return the node set from the YAML file
     *
     * @return node set
     */
    public final Set<INode<Integer>> nodes()
    {
        return m_nodes;
    }

    /**
     * returns the edges
     *
     * @return edge set
     */
    public final Set<IEdge<Integer>> edges()
    {
        return m_edges;
    }
}
