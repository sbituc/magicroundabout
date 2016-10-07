package bachelor.project.vehicle;

import bachelor.project.graph.network.INode;

//public class VehicleSource implements IVehicleFactory {
public class VehicleSource extends CVehicleFactory {
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
    private final INode m_startNode;

    public VehicleSource(final INode p_startNode) {
        m_startNode = p_startNode;

    }

    public IVehicleFactory call() throws Exception {
        return null;
    }

    protected void generateVehicles() {
        // TODO  Erzeugung der Fahrzeuge
    }

    public Car makeCar(String newCarType) {
        Car newCar = null;

        if (newCarType.equals("1")) {
            return new Car();
        } else return null;
    }

/*
    private static int randomGeneration() {
        int randomInt;
        Random myRandom = new Random(System.currentTimeMillis());


        for (int i = 0; i < 10000; i++) {
            randomInt = myRandom.nextInt(100);
            System.out.println(i + ": " + randomInt);
        }
        System.out.println("<50: " + j + " ---- >=50: " + k);

        //return randomInt;
        return 9;
    }
*/


/*
    public static void main(String[] args) {
        randomGeneration();
    }
*/
}
