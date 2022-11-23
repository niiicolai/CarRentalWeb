package carrental.carrentalweb.order;

import java.lang.Math;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.enums.CreditRatingState;
import carrental.carrentalweb.repository.CreditRatingRepository;

/*
 * Written by Nicolai Berg Andersen.
 */

public class CreditRatingOrder {

    private Booking booking;

    public CreditRatingOrder(Booking booking) {
        this.booking = booking;
    }

    public CreditRating execute() {
        /* Ideal: check credit rating API by using user information. */
        /* Simulation: random return true or false. */        
        CreditRatingState state = CreditRatingState.Reject;
        if (Math.random() > .5) 
            state = CreditRatingState.Approve;

        /* Save credit rating */
        CreditRating creditRating = new CreditRating(state, booking.getId());
        CreditRatingRepository repository = new CreditRatingRepository();        
        repository.insert(creditRating);
    
        return creditRating;
    }
}
