package carrental.carrentalweb.services;

import org.springframework.stereotype.Service;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.enums.CreditRatingState;
import carrental.carrentalweb.repository.CreditRatingRepository;

/*
 * ## Author: 
 * Nicolai Berg Andersen
 * 
 * ## Class Description: 
 * A service class used to create, save, and simulate a credit rating check.
 * 
 * ## How to use:
 * 
 * // Get an existing user.
 * User user = ...;
 * 
 * 
 * // Execute credit rating check
 * // to get the latest state.
 * CreditRating creditRating = creditRatingService.check(user);
 *
 * 
 * // Check the credit rating.
 * if (creditRating.isApproved()) {
 *    // do something.
 * } else {
 *    // do something else. 
 * }
 * 
 * 
 */

@Service
public class CreditRatingService {


    /*
     * The credit rating repository is used to
     * check if the user already have a credit rating.
     */
    private CreditRatingRepository creditRatingRepository;


    /*
     * Constructor managed by Spring Boot.
     */
    public CreditRatingService(CreditRatingRepository creditRatingRepository) {
        this.creditRatingRepository = creditRatingRepository;
    }


    /*
     * Returns the next credit rating state.
     */
    private CreditRatingState nextState() {
        /* Ideal: check credit rating API by using user information. */
        /* Simulation: random return true or false. */   
        return Math.random() > .5 ? CreditRatingState.Approve : CreditRatingState.Reject;
    }


    /*
     * Returns the current credit rating, 
     * if any exist.
     */
    private CreditRating currentRating(User user) {
        return creditRatingRepository.find("user_id", user.getId());
    }
    

    /*
     * Performs a credit rating check,
     * and returns the result.
     */
    public CreditRating check(User user) {
        CreditRating creditRating = currentRating(user);
        CreditRatingState state = nextState();        

        if (creditRating == null) {
            creditRating = new CreditRating(state, user.getId());
            creditRatingRepository.insert(creditRating);
        } else {
            creditRating.setState(state);
            creditRatingRepository.update(creditRating);
        }
        
        return creditRating;
    }
}
