package CarRentalSim.Controller;

import CarRentalSim.Store.Store;
import CarRentalSim.Store.StoreReportService;
import CarRentalSim.Store.Reports.DailyReport;
import CarRentalSim.Store.Reports.FinalReport;
import CarRentalSim.View.StoreView;

/**
 * MVC controller that coordinates store lifecycle and report rendering.
 */
public class StoreController {
    private final Store store;
    private final StoreReportService reportService;
    private final StoreView view;

    public StoreController(Store store, StoreReportService reportService, StoreView view) {
        this.store = store;
        this.reportService = reportService;
        this.view = view;
    }

    public void runDay(int dayNumber) {
        this.store.open();
        this.store.close();

        DailyReport report = this.reportService.buildDailyReport(dayNumber);
        this.view.renderDailyReport(report);
    }

    public void finishSimulation(int dayNumber) {
        FinalReport finalReport = this.reportService.buildFinalReport(dayNumber);
        this.view.renderFinalReport(finalReport);
    }
}
