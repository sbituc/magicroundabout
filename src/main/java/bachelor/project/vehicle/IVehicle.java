package bachelor.project.vehicle;

import java.util.concurrent.Callable;

public interface IVehicle extends Callable<IVehicle> {
    /**
     Gibt an, ob das Fahrzeug aus der Liste entfernt werden kann
     @return false oder true
     */
    public boolean finishpassed();

}
