package bachelor.project.vehicle;

/*
 * role model for all vehicles incl. cars, lorries, motorbikes etc.
 * for the moment we're just using cars (see Car class)
 */
public abstract class CVehicle implements IVehicle {
    private int speed;
    // TODO  add route, shape, color

    // TODO  getRoute(), getShape(), getColor()

    // TODO  finish constructor (route shape color speed)
    public CVehicle() {

    }

    public IVehicle call() throws Exception {
        // TODO Auto-generated method stub

        // Für finales, wenn Routenende erreicht NULL, sonst sich selbst
        // return route ende ? null : this;
        return null;
    }
}
