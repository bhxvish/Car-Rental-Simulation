package CarRentalSim.View;

import CarRentalSim.Store.Reports.DailyReport;
import CarRentalSim.Store.Reports.FinalReport;

/**
 * View contract for rendering store reports.
 */
public interface StoreView {
    void renderDailyReport(DailyReport report);
    void renderFinalReport(FinalReport report);
}
