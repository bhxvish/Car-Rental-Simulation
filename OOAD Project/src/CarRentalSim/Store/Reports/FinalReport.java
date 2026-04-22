package CarRentalSim.Store.Reports;

import java.util.HashMap;
import java.util.Map;

/**
 * Immutable final summary view model for the UI layer.
 */
public class FinalReport {
    private final int dayNumber;
    private final int totalRentals;
    private final Map<String, Integer> customerTypeCounts;
    private final double totalRevenue;

    public FinalReport(int dayNumber,
                       int totalRentals,
                       Map<String, Integer> customerTypeCounts,
                       double totalRevenue) {
        this.dayNumber = dayNumber;
        this.totalRentals = totalRentals;
        this.customerTypeCounts = new HashMap<>(customerTypeCounts);
        this.totalRevenue = totalRevenue;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public int getTotalRentals() {
        return totalRentals;
    }

    public Map<String, Integer> getCustomerTypeCounts() {
        return new HashMap<>(customerTypeCounts);
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
