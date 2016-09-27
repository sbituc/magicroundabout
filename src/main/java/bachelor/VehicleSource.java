package bachelor;

import bachelor.project.graph.network.INode;

public class VehicleSource implements IVehicleFactory {

	private final INode<T> m_startNode;
	
	public VehicleSource(final INode<T> p_startNode){
		m_startNode = p_startNode;

	}
	
	public IVehicleFactory call() throws Exception {

		// TODO Auto-generated method stub
		return null;
	}
/*
 * QUELLE!
 * 
 * Startpunkt bekommt sie vorgegeben
 * Zielpunkt zufällig ziehen
 * Route berechnen
 * 
 * Wann soll sie Auto erzeugen? Zufall 
 * Wieviele Autos
 */
}
