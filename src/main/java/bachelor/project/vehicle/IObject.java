package bachelor.project.vehicle;

import java.util.concurrent.Callable;

/**
 * Interface für alle Objekte, die angetriggert werden können
 */

public interface IObject extends Callable<IObject>, ISteppable {

    /**
     * Methode, die die Objekte jeden Zeitschritt ausführen
     *
     * @param p_timeStep Zeitschritt in der Simulation
     */
    public void step(int p_timeStep);

    /**
     * Gibt an, ob das Object aus der IStappale Liste entfernt werden soll
     *
     * @return false oder true
     */
    public boolean canRemove();

}
