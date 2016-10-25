package bachelor.project.vehicle;

import java.util.List;

public class Car extends CVehicle {
    /**
     * Konstruktor des Vehicles
     *
     * @param p_currentSpeed
     * @param p_route
     */
    public Car(int p_currentSpeed, List p_route, String p_color) {
        super(p_currentSpeed, p_route, p_color);
    }

    @Override
    public IObject call() throws Exception {
        return null;
    }
}
