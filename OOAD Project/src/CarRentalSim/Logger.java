package CarRentalSim;

import CarRentalSim.Cars.Car;
import CarRentalSim.Customers.Customer;
import CarRentalSim.Store.RentalRecord;
import CarRentalSim.Store.Reports.DailyReport;
import CarRentalSim.Store.Reports.FinalReport;
import CarRentalSim.View.StoreView;

import java.util.List;
import java.util.Map;

/**
 * Console view for the simulation.
 *
 * In MVC this class is only responsible for rendering report models.
 */
public class Logger implements StoreView {

    /**
     * Prints the daily activity of the observed store
     */
    @Override
    public void renderDailyReport(DailyReport report){
        List<RentalRecord> completedRentals = report.getCompletedRentals();
        List<RentalRecord> activeRentals = report.getActiveRentals();
        List<Car> carsLeft = report.getCarsLeft();

        System.out.println(String.format("Day: %d", report.getDayNumber()));

        System.out.println("=================================================================");

        // completed rentals
        System.out.println(String.format("There are %d completed car rentals", completedRentals.size()));
        for (RentalRecord rentalRecord: completedRentals) {
            Customer customer = rentalRecord.getCustomer();
            System.out.println(String.format("Customer ID: %d", System.identityHashCode(rentalRecord)));
            System.out.println(String.format("Customer Type: %s", customer.getClass().getSimpleName()));
            System.out.println(String.format("Rental Record ID: %d", System.identityHashCode(rentalRecord)));
            System.out.println("Cars rented:");
            System.out.println("");
            for(Car car : rentalRecord.getCars()){
                System.out.println(car.getDescription());
                System.out.println(String.format("License Plate: %s", car.getLicensePlate()));
                System.out.println("");
            }
            if(!rentalRecord.isActive()) {
                System.out.println(String.format("Duration: %d", rentalRecord.getDuration()));
                System.out.println(String.format("Total Fee: %.2f", rentalRecord.getCost()));
            }
            System.out.println("-----------------------------------------------------------------");
        }

        // Active rentals
        System.out.println(String.format("There are %d active car rentals", activeRentals.size()));
        for (RentalRecord rentalRecord: activeRentals) {
            Customer customer = rentalRecord.getCustomer();
            System.out.println(String.format("Customer ID: %d", System.identityHashCode(rentalRecord)));
            System.out.println(String.format("Customer Type: %s", customer.getClass().getSimpleName()));
            System.out.println(String.format("Rental Record ID: %d", System.identityHashCode(rentalRecord)));
            System.out.println("Cars rented:");
            System.out.println("");
            for(Car car : rentalRecord.getCars()){
                System.out.println(car.getDescription());
                System.out.println(String.format("License Plate: %s", car.getLicensePlate()));
                System.out.println("");
            }
            System.out.println("-----------------------------------------------------------------");
        }

        System.out.println(String.format("There are %d cars still available", carsLeft.size()));
        for (Car car : carsLeft){
            System.out.println(String.format("License Plate: %s", car.getLicensePlate()));
        }

        System.out.println("-----------------------------------------------------------------");

        System.out.println(String.format("The store made %.2f dollars today", report.getDailyRevenue()));

        System.out.println("=================================================================");

    }

    /**
     * Prints the final information of the store's activity during the simulation
     *  - Current Day
     *  - Total number of rentals
     *  - Distribution of rentals across customer types
     *  - The store's total revenue
     */
    @Override
    public void renderFinalReport(FinalReport report){
        System.out.println(String.format("Day: %d ( the final day )", report.getDayNumber()));

        System.out.println("=================================================================");

        System.out.println(String.format("%d rentals were made in total with the following customer type distribution:", report.getTotalRentals()));
        for (Map.Entry<String, Integer> entry : report.getCustomerTypeCounts().entrySet()) {
            System.out.println(String.format("%s: %d", entry.getKey(), entry.getValue()));
        }

        System.out.println(String.format("Total Store Revenue %.2f", report.getTotalRevenue()));

        System.out.println("=================================================================");
    }
}
