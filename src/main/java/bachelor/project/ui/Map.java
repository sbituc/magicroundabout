package bachelor.project.ui;

/**
 * Created by Hooby on 10.10.2016.
 */
import javax.swing.JFrame;
import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapKit.DefaultProviders;


import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jxmapviewer.viewer.*;
import org.jdesktop.swingx.mapviewer.wms.WMSService;
import org.jdesktop.swingx.mapviewer.wms.WMSTileFactory;




import java.util.jar.JarFile;

/*public class Map {
    public static void main(String args[]){
        JFrame frame = new JFrame("JXMapViewer wih googlemap");
        frame .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JXMapKit map = new JXMapKit();
        map.setDefaultProvider(DefaultProviders.OpenStreetMaps);
        map.setAddressLocation(new GeoPosition(51.5628550,-1.7715011)); //magicroundabout
        map.setZoom(0);
        frame.add(map);
        frame.setBounds(400,400, 800, 800);
        frame.setVisible(true);

    }

}
*/
public class Map extends javax.swing.JFrame {
    public Map() {
        initComponents();
     /*   WMSService wms = new WMSService();
        wms.setLayer("BMNG");
        wms.setBaseUrl("http://wms.jpl.nasa.gov/wms.cgi?");
        TileFactory fact = new WMSTileFactory(wms);
        jXMapKit1.setTileFactory(fact);*/

    }

    private void initComponents() {
        jXMapKit1 = new org.jdesktop.swingx.JXMapKit();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jXMapKit1.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
        getContentPane().add(jXMapKit1, java.awt.BorderLayout.CENTER);
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Map().setVisible(true);
            }
        });
    }
    private org.jdesktop.swingx.JXMapKit jXMapKit1;
}