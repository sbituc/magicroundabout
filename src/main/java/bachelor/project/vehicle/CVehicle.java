package bachelor.project.vehicle;

import java.util.List;

/*
 * role model for all vehicles incl. cars, lorries, motorbikes etc.
 * for the moment we're just using cars (see Car class)
 */
public abstract class CVehicle implements IVehicle {

    /**
     * current speed of the vehicle
     */
    private int v_currentSpeed;

    /**
     * has the vehicle completed its route?
     */
    private boolean v_finishPassed = false;

    /**
     * Current vehicles position
     */
    private int v_currentPosition = 0;

    /**
     * Route for the vehicle
     */
    private final List v_route;

    /**
     * Color of the vehicle
     */
    private char v_color;

    // TODO  getColor in Abhängigkeit von der CarFactory-Position bzw. Nummer, Konstruktor und "Array" für Route anpassen

    /**
     * Vehicle-Constructor
     */
    public CVehicle(final int c_currentSpeed, final List c_route) {
        v_currentSpeed = c_currentSpeed;
        v_route = c_route;
    }

    /**
    * returns current speed
    */
    public int getCurrentSpeed() {
        return v_currentSpeed;
    }

    /**
     * set current speed
     */
    public void setCurrentSpeed(final int c_currentSpeed) {
        v_currentSpeed = c_currentSpeed;
    }

    //TODO Auto fahren lassen?!

    public IVehicle call() throws Exception {
        return null;
    }
}
