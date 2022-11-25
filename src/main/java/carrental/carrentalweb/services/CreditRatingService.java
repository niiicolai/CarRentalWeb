package carrental.carrentalweb.services;

import org.springframework.stereotype.Service;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.enums.CreditRatingState;

@Service
public class CreditRatingService {
    
    public CreditRating check(Booking booking) {
        /* Ideal: check credit rating API by using user information. */
        /* Simulation: random return true or false. */        
        CreditRatingState state = CreditRatingState.Reject;
        if (Math.random() > .5) 
            state = CreditRatingState.Approve;

        return new CreditRating(state, booking.getId());
    }
}
