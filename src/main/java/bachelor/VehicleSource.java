package bachelor;

import bachelor.project.graph.network.INode;

import java.util.Random;

public class VehicleSource implements IVehicleFactory {

    private final INode m_startNode;

	public VehicleSource(final INode p_startNode){
		m_startNode = p_startNode;

	}

	public IVehicleFactory call() throws Exception {

		// TODO Auto-generated method stub
		return null;
	}

	// TODO make methods work :(

    @Override
    public int randomGeneration() {
        /*
        int randomInt;
        Random myRandom = new Random(System.currentTimeMillis());
        randomInt = myRandom.nextInt(10);

        return randomInt;
        */
        return 8;
    }

    @Override
    public void generateCar() {
        return;
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

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {

//            int myInt = randomGeneration();
            System.out.println(i);

        }
    }
}
