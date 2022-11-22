package carrental.carrentalweb.controller;

import carrental.carrentalweb.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

public class CarController {

    @Autowired
    CarRepository carRepository;

    @GetMapping("/cars")
    public String cars(Model model, HttpSession session){
        model.addAttribute("cars", carRepository.getAllCars());

        return "car/cars";
    }
}
