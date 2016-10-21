package bachelor.project.vehicle;

import java.util.ArrayList;
import java.util.List;

public abstract class CVehicleFactory implements IVehicleFactory {
    protected List<CVehicle> vehicles = new ArrayList<CVehicle>();

    public CVehicleFactory() {
//        this.generateVehicles();
    }

    public List<CVehicle> getVehicles() {
        return vehicles;
    }

    public IVehicleFactory call() throws Exception {
        return null;
    }

    protected abstract void generateVehicles();
}
