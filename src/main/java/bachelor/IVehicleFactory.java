package bachelor;

import java.util.concurrent.Callable;

public interface IVehicleFactory extends Callable<IVehicleFactory> {

    int randomGeneration();

    void generateCar();

}
