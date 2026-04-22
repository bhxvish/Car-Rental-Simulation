package CarRentalSim.Store.Reports;

import CarRentalSim.Cars.Car;
import CarRentalSim.Store.RentalRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable daily view model for the UI layer.
 */
public class DailyReport {
    private final int dayNumber;
    private final List<RentalRecord> completedRentals;
    private final List<RentalRecord> activeRentals;
    private final List<Car> carsLeft;
    private final double dailyRevenue;

    public DailyReport(int dayNumber,
                       List<RentalRecord> completedRentals,
                       List<RentalRecord> activeRentals,
                       List<Car> carsLeft,
                       double dailyRevenue) {
        this.dayNumber = dayNumber;
        this.completedRentals = new ArrayList<>(completedRentals);
        this.activeRentals = new ArrayList<>(activeRentals);
        this.carsLeft = new ArrayList<>(carsLeft);
        this.dailyRevenue = dailyRevenue;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public List<RentalRecord> getCompletedRentals() {
        return new ArrayList<>(completedRentals);
    }

    public List<RentalRecord> getActiveRentals() {
        return new ArrayList<>(activeRentals);
    }

    public List<Car> getCarsLeft() {
        return new ArrayList<>(carsLeft);
    }

    public double getDailyRevenue() {
        return dailyRevenue;
    }
}
