package CarRentalSim.Store.Chain;

import CarRentalSim.Store.Store;

/**
 * Finalizes rental by creating and storing the rental record.
 */
public class RentalRecordCreationHandler extends RentalRequestHandler {
    private final Store store;

    public RentalRecordCreationHandler(Store store) {
        this.store = store;
    }

    @Override
    public void handle(RentalRequest request) {
        this.store.finalizeRentalRequest(request);
        this.forward(request);
    }
}
