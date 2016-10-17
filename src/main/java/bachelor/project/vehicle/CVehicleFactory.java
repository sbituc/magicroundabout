package bachelor.project.vehicle;

import java.util.ArrayList;
import java.util.List;

public abstract class CVehicleFactory implements IVehicleFactory {
    protected List<IVehicle> vehicles = new ArrayList<IVehicle>();

    public CVehicleFactory() {
//        this.generateVehicles();
    }

    public List<IVehicle> getVehicles() {
        return vehicles;
    }

    public IVehicleFactory call() throws Exception {
        return null;
    }

    protected abstract void generateVehicles();
}
