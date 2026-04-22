package CarRentalSim.Store.Chain;

import CarRentalSim.Cars.Car;
import CarRentalSim.Store.Store;

/**
 * Validates that each requested license plate exists in the pool.
 */
public class CarAvailabilityValidationHandler extends RentalRequestHandler {
    private final Store store;

    public CarAvailabilityValidationHandler(Store store) {
        this.store = store;
    }

    @Override
    public void handle(RentalRequest request) {
        for (String requestedPlate : request.getLicensePlates()) {
            boolean found = false;
            for (Car availableCar : this.store.getCarsAvailable()) {
                if (availableCar.getLicensePlate().equals(requestedPlate)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IllegalStateException("Requested car is not available: " + requestedPlate);
            }
        }

        this.forward(request);
    }
}
