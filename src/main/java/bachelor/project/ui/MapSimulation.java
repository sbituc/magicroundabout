package bachelor.project.ui;
import javax.swing.JFrame;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
//import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import bachelor.project.ui.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.WMSTileFactoryInfo;



public class MapSimulation {
	public static void main(String[] args)
	{
		JXMapViewer mapViewer = new JXMapViewer();

		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
		TileFactoryInfo veInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
		DefaultTileFactory tileFactory = new DefaultTileFactory(veInfo);

		mapViewer.setTileFactory(tileFactory);
		
		// Use 8 threads in parallel to load the tiles
		tileFactory.setThreadPoolSize(8);

		// Set the focus
		GeoPosition magicRoundabout = new GeoPosition(51.5628550,-1.7715011);
		

		mapViewer.setZoom(0);
		mapViewer.setAddressLocation(magicRoundabout);
		
		// Display the viewer in a JFrame
		JFrame frame = new JFrame("Magic Roundabout");
		frame.getContentPane().add(mapViewer);
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
