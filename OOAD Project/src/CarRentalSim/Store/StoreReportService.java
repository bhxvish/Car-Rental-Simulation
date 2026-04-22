package CarRentalSim.Store;

import CarRentalSim.Store.Reports.DailyReport;
import CarRentalSim.Store.Reports.FinalReport;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Builds report models from store data for the view layer.
 */
public class StoreReportService {
    private final Store store;

    public StoreReportService(Store store) {
        this.store = store;
    }

    public DailyReport buildDailyReport(int dayNumber) {
        ArrayList<RentalRecord> completedRentals = new ArrayList<>();
        ArrayList<RentalRecord> activeRentals = new ArrayList<>();

        for (RentalRecord rentalRecord : this.store.getAllRentals()) {
            if (rentalRecord.isActive()) {
                activeRentals.add(rentalRecord);
            } else {
                completedRentals.add(rentalRecord);
            }
        }

        return new DailyReport(
                dayNumber,
                completedRentals,
                activeRentals,
                this.store.getCarsAvailable(),
                this.store.getLastClosedDayRevenue()
        );
    }

    public FinalReport buildFinalReport(int dayNumber) {
        HashMap<String, Integer> customerTypeCounts = new HashMap<>();

        for (RentalRecord rentalRecord : this.store.getAllRentals()) {
            String customerType = rentalRecord.getCustomer().getClass().getSimpleName();
            customerTypeCounts.put(customerType, customerTypeCounts.getOrDefault(customerType, 0) + 1);
        }

        return new FinalReport(
                dayNumber,
                this.store.getAllRentals().size(),
                customerTypeCounts,
                this.store.getRevenue()
        );
    }
}
