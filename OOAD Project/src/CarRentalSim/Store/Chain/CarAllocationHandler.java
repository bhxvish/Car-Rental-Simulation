package CarRentalSim.Store.Chain;

import CarRentalSim.Cars.Car;
import CarRentalSim.Store.Store;

/**
 * Allocates requested cars and accumulates costs.
 */
public class CarAllocationHandler extends RentalRequestHandler {
    private final Store store;

    public CarAllocationHandler(Store store) {
        this.store = store;
    }

    @Override
    public void handle(RentalRequest request) {
        for (String licensePlate : request.getLicensePlates()) {
            Car requestedCar = this.store.getCarStorage().requestCar(licensePlate, request.getExtras());
            request.addAllocatedCar(requestedCar);
            request.addBaseCost(requestedCar.getCost());
            request.addNightlyCost(requestedCar.getNightlyCost());
        }

        this.forward(request);
    }
}
