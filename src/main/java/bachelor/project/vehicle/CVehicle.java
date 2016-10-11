package bachelor.project.vehicle;

import java.lang.reflect.Array;

/*
 * role model for all vehicles incl. cars, lorries, motorbikes etc.
 * for the moment we're just using cars (see Car class)
 */
public abstract class CVehicle implements IVehicle {

    /**
     * current speed of the vehicle
     */
    private int v_currentspeed;

    /**
     * has the vehicle completed its route?
     */
    private boolean v_finishpassed = false;

    /**
     * Current vehicles position
     */
    private int v_currentposition = 0;

    /**
     * Route for the vehicle
     */
    private final Array v_route;

    /**
     * Color of the vehicle
     */
    private char v_color;

    // TODO  getColor in Abhängigkeit von der CarFactory-Position bzw. Nummer, Konstruktor und "Array" für Route anpassen

    /**
     * Vehicle-Constructor
     */
    public CVehicle(final int c_currentspeed, final Array c_route) {
        v_currentspeed = c_currentspeed;
        v_route = c_route;
    }
    /**
    * returns current speed
    */
    public int getcurrentspeed() {
        return v_currentspeed;
    }

    /**
     * set current speed
     */
    public void setcurrentspeed(final int c_currentspeed) {
        v_currentspeed = c_currentspeed;
    }

    //TODO Auto fahren lassen?!

    public IVehicle call() throws Exception {
        return null;
    }
}
