package carrental.carrentalweb.order;

/*
 * Written by Nicolai Berg Andersen.
 */

import java.lang.Math;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.enums.CreditRatingState;
import carrental.carrentalweb.repository.CreditRatingRepository;

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
        CreditRating creditRating = new CreditRating(booking.getId(), state);
        CreditRatingRepository repository = new CreditRatingRepository();        
        repository.insert(creditRating);
    
        return creditRating;
    }
}
