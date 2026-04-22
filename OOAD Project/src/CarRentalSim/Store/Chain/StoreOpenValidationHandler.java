package CarRentalSim.Store.Chain;

import CarRentalSim.Store.Store;

/**
 * Ensures the store is open before processing rentals.
 */
public class StoreOpenValidationHandler extends RentalRequestHandler {
    private final Store store;

    public StoreOpenValidationHandler(Store store) {
        this.store = store;
    }

    @Override
    public void handle(RentalRequest request) {
        if (!this.store.isOpen()) {
            throw new IllegalStateException("Cannot rent cars while store is closed.");
        }
        this.forward(request);
    }
}
