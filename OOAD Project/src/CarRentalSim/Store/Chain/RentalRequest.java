package CarRentalSim.Store.Chain;

import CarRentalSim.Cars.Car;
import CarRentalSim.Cars.Decorators.CarDecorator;
import CarRentalSim.Customers.Customer;

import java.util.ArrayList;

/**
 * Carries rental request data through the chain.
 */
public class RentalRequest {
    private final Customer customer;
    private final int duration;
    private final ArrayList<String> licensePlates;
    private final ArrayList<Class<? extends CarDecorator>> extras;
    private final ArrayList<Car> allocatedCars;
    private double baseCost;
    private double nightlyCost;

    public RentalRequest(Customer customer,
                         int duration,
                         ArrayList<String> licensePlates,
                         ArrayList<Class<? extends CarDecorator>> extras) {
        this.customer = customer;
        this.duration = duration;
        this.licensePlates = new ArrayList<>(licensePlates);
        this.extras = new ArrayList<>(extras);
        this.allocatedCars = new ArrayList<>();
        this.baseCost = 0;
        this.nightlyCost = 0;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<String> getLicensePlates() {
        return new ArrayList<>(licensePlates);
    }

    public ArrayList<Class<? extends CarDecorator>> getExtras() {
        return new ArrayList<>(extras);
    }

    public ArrayList<Car> getAllocatedCars() {
        return allocatedCars;
    }

    public void addAllocatedCar(Car car) {
        this.allocatedCars.add(car);
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void addBaseCost(double cost) {
        this.baseCost += cost;
    }

    public double getNightlyCost() {
        return nightlyCost;
    }

    public void addNightlyCost(double cost) {
        this.nightlyCost += cost;
    }
}
