package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.*;
import carrental.carrentalweb.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
 * Written by Mikkel Aabo Simonsen
 */
@Controller
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CreditRatingRepository creditRatingRepository;

    @Autowired
    PickupPointRepository pickupPointRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    BookingService bookingService;

    @GetMapping("/cars")
    public String cars(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("cars", carRepository.getAllCars());
        model.addAttribute("user", user);
        model.addAttribute("creditRating", user == null ? null : creditRatingRepository.find("user_id", user.getId()));
        model.addAttribute("booking", new Booking());
        model.addAttribute("pickupPoints", pickupPointRepository.getPickupPointsList());
        model.addAttribute("subscriptions", subscriptionRepository.getAll());
        model.addAttribute("bookingAmounts", bookingService.getBookingAmountsOfTheWeek());
        return "car/car_list";
    }

    @GetMapping("/add-car")
    public String addCarGet(Model model){
        model.addAttribute("car", new Car());
        return "car/add_car";
    }

    @PostMapping("/add-car")
    public String addCarPost(@ModelAttribute("car") Car newCar){
        carRepository.createCar(newCar);
        return "redirect:/add-car?success";
    }

    @GetMapping("/edit-car/{vehicle-number}")
    public String updateCarGet(@PathVariable("vehicle-number") long vehicleNumber, Model model){
        model.addAttribute("car", carRepository.findCarByVehicleNumber(vehicleNumber));
        return "car/edit_car";
    }

    @PatchMapping("/edit-car")
    public String updateCarPost(Car car){
        carRepository.updateCar(car);
        return "redirect:/edit-car/" + car.getVehicleNumber() + "?success";
    }

    @DeleteMapping("/delete-car/{vehicle-number}")
    public void deleteCar(@PathVariable("vehicle-number") long vehicleNumber){
        if (carRepository.findCarByVehicleNumber(vehicleNumber) != null){
            carRepository.deleteCarByVehicleNumber(vehicleNumber);
        }
    }

}