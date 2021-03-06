package bachelor.project.vehicle;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Car extends CVehicle {
    /**
     * Konstruktor des Cars
     * @param p_currentSpeed
     * @param p_cellRoute
     * @param p_laneRoute
     * @param p_color
     */
    public Car(int p_currentSpeed, List p_cellRoute, LinkedList p_laneRoute, Color p_color) {
        super(p_currentSpeed, p_cellRoute, p_laneRoute, p_color);
        this.set_label("C"); // sets label for car to C
        this.set_length(2); // sets length of car to 2
    }

    @Override
    public IObject call() throws Exception {
        return null;
    }
}
