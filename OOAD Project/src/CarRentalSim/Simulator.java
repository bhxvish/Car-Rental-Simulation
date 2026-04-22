package CarRentalSim;

import CarRentalSim.Controller.StoreController;
import CarRentalSim.Cars.Creation.CarStorage;
import CarRentalSim.Customers.BusinessCustomer;
import CarRentalSim.Customers.CasualCustomer;
import CarRentalSim.Customers.Customer;
import CarRentalSim.Customers.RegularCustomer;
import CarRentalSim.Store.Store;
import CarRentalSim.Store.StoreReportService;
import CarRentalSim.View.StoreView;

/**
 * CarRentalSim.Simulator Class
 */
public class Simulator {
    private Store store;
    private StoreController storeController;
    private final StoreView view;
    private static int currentDay;
    private final int FINALDAY = 35;

    public Simulator() {
        this(new Logger());
    }

    public Simulator(StoreView view) {
        this.view = view;
    }

    /**
     * Sets up and starts simulation
     * @param args
     */
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.runSimulation();
    }

    public void runSimulation() {
        this.setup();
        this.simulate();
    }

    /**
     * assumption: People/things inherently know what day and don't need to ask for the day. This is why this method is static
     * @return (int) currentDay - The current day in the simulation
     */
    public static int getCurrentDay(){
        return currentDay;
    }

    /**
     * Creates all objects that are part of the simulation:
     *  - 1 store
     *  - 12 customers
     *    - 4 casual customers
     *    - 4 regular customers
     *    - 4 business customers
     *  - 1 logger
     *  - 1 car storage
     */
    private void setup(){
        Simulator.currentDay = 1;

        // Store
        Store store = new Store();
        this.store = store;

        // MVC wiring
        StoreReportService reportService = new StoreReportService(store);
        this.storeController = new StoreController(store, reportService, this.view);

        // Customers
        for(int i = 0; i < 4; i++){
            Customer newCasualCustomer = new CasualCustomer();
            newCasualCustomer.startObserving(store);

            Customer newRegularCustomer = new RegularCustomer();
            newRegularCustomer.startObserving(store);

            Customer newBusinessCustomer = new BusinessCustomer();
            newBusinessCustomer.startObserving(store);
        }

        // CarStorage
        CarStorage carStorage = new CarStorage();
        store.setCarStorage(carStorage);

    }

    /**
     * Runs the simulation for a set number of days ( this.FINALDAY )
     *  - opening the store
     *  - closing the store
     *  - incrementing the current day
     */
    private void simulate(){
        for(this.currentDay = 1; this.currentDay <= this.FINALDAY; this.currentDay++) {
            this.storeController.runDay(this.currentDay);
        }
        this.storeController.finishSimulation(this.FINALDAY);
    }

}
