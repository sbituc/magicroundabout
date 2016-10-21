package bachelor.project.vehicle;

/**
 * Interface für das schrittweise ausführen
 */
public interface ISteppable {

    /**
     * Methode, die die Objekte jeden Zeitschritt ausführen
     *
     * @param p_timeStep Zeitschritt in der Simulation
     */
    public void step(final int p_timeStep);

}