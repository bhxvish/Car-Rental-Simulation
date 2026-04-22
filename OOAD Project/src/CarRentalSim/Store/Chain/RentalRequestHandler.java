package CarRentalSim.Store.Chain;

/**
 * Base handler for rental request processing.
 */
public abstract class RentalRequestHandler {
    private RentalRequestHandler next;

    public RentalRequestHandler setNext(RentalRequestHandler next) {
        this.next = next;
        return next;
    }

    protected void forward(RentalRequest request) {
        if (this.next != null) {
            this.next.handle(request);
        }
    }

    public abstract void handle(RentalRequest request);
}
