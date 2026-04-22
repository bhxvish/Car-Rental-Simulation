package CarRentalSim.Store;

import CarRentalSim.Cars.Car;
import CarRentalSim.Cars.Creation.CarStorage;
import CarRentalSim.Cars.Decorators.CarDecorator;
import CarRentalSim.Customers.Customer;
import CarRentalSim.Simulator;
import CarRentalSim.Store.Chain.CarAllocationHandler;
import CarRentalSim.Store.Chain.CarAvailabilityValidationHandler;
import CarRentalSim.Store.Chain.RentalRecordCreationHandler;
import CarRentalSim.Store.Chain.RentalRequest;
import CarRentalSim.Store.Chain.RentalRequestHandler;
import CarRentalSim.Store.Chain.StoreOpenValidationHandler;
import CarRentalSim.Subject;

import java.util.ArrayList;

/**
 *  Store acts as the Subject for Observer Pattern
 */
public class Store extends Subject {
    private double revenue;
    private double dailyRevenue;
    private double lastClosedDayRevenue;
    private ArrayList<RentalRecord> rentalRecords = new ArrayList<>();
    private CarStorage carStorage;
    private RentalRequestHandler rentalRequestChain;
    private boolean open;

    public Store() {
        this.initializeRentalRequestChain();
    }

    private void initializeRentalRequestChain() {
        RentalRequestHandler openValidation = new StoreOpenValidationHandler(this);
        RentalRequestHandler availabilityValidation = new CarAvailabilityValidationHandler(this);
        RentalRequestHandler allocation = new CarAllocationHandler(this);
        RentalRequestHandler creation = new RentalRecordCreationHandler(this);

        openValidation.setNext(availabilityValidation)
                .setNext(allocation)
                .setNext(creation);

        this.rentalRequestChain = openValidation;
    }

    public void setCarStorage(CarStorage carStorage){
        this.carStorage = carStorage;
    }

    public CarStorage getCarStorage() {
        return this.carStorage;
    }

    /**
     * Total revenue a store has made
     * @return revenue + dailyRevenue
     */
    public double getRevenue(){
        return this.revenue + this.dailyRevenue;
    }

    public double getDailyRevenue() {
        return this.dailyRevenue;
    }

    public double getLastClosedDayRevenue() {
        return this.lastClosedDayRevenue;
    }

    /**
     * @param customer:Customer
     * @return ArrayList<RentalRecord>: The rental records of a given customer
     */
    public ArrayList<RentalRecord> getCustomerRentals(Customer customer){
        // get rental records for the given customer
        ArrayList<RentalRecord> customerRecords = new ArrayList<RentalRecord>();
        for (RentalRecord record: this.rentalRecords) {
            if(record.getCustomer() == customer){
                customerRecords.add(record);
            }
        }
        return customerRecords;
    }

    /**
     * @return ArrayList<RentalRecord>: All rental records of the store
     */
    public ArrayList<RentalRecord> getAllRentals() {
        return rentalRecords;
    }

    public boolean isOpen(){
        return open;
    }

    /**
     * @return ArrayList<Car>: All cars available for renting
     */
    public ArrayList<Car> getCarsAvailable(){
        return this.carStorage.getCarsLeft();
    }

    /**
     * Rents a set of cars with given extras for a customer
     *  - Prepares car with specifications given
     *  - Creates new rental record
     * @param customer:Customer
     * @param duration:int - How long the customer wants to rent the cars for
     * @param licensePlates:ArrayList<String> - List of the license plates of the cars the customer wants to rent
     * @param extras:ArrayList<Class<? extends CarDecorator>> - A list of CarDecorator class objects to specify what extra features a customer wants for a rental
     */
    public void rentCar(Customer customer, int duration, ArrayList<String> licensePlates, ArrayList<Class<? extends CarDecorator>> extras){
        RentalRequest request = new RentalRequest(customer, duration, licensePlates, extras);
        this.rentalRequestChain.handle(request);
    }

    public void finalizeRentalRequest(RentalRequest request) {
        RentalRecord newRentalRecord = new RentalRecord(
                request.getCustomer(),
                request.getBaseCost(),
                request.getNightlyCost(),
                request.getDuration(),
                Simulator.getCurrentDay(),
                request.getAllocatedCars()
        );
        this.dailyRevenue += newRentalRecord.getCost();
        this.rentalRecords.add(newRentalRecord);
    }

    /**
     * Returns the cars a given customer has that are due
     * assumption: The store will deal with returns so all a customer needs to do is give their information and the store will handle the return
     *
     * @param customer:Customer
     */
    public void returnCar(Customer customer){
        for (RentalRecord rentalRecord: this.rentalRecords) {
            if(rentalRecord.getDaysLeft() == 0 && rentalRecord.getCustomer() == customer){
                for (Car car: rentalRecord.getCars()) {
                   this.carStorage.returnCar(car);
                }
                rentalRecord.setReturned();
                break;
            }
        }
    }

    public void open(){
        this.open = true;
        super.notifyObservers();
    }

    /**
     * Closes the store and moves the daily revenue into the total revenue
     */
    public void close(){
       this.open = false;
       super.notifyObservers();
         this.lastClosedDayRevenue = this.dailyRevenue;
       this.revenue += this.dailyRevenue;
       this.dailyRevenue = 0;
    }
}
