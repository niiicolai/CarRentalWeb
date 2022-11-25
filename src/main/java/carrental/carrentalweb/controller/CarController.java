package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {

    @Autowired
    CarRepository carRepository;

    @GetMapping("/cars")
    public String cars(Model model){
        model.addAttribute("cars", carRepository.getAllCars());
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

    @GetMapping("/edit-car")
    public String updateCarGet(@PathVariable("vehicle-number") long vehicleNumber, Model model){
        model.addAttribute("cars", carRepository.findCarByVehicleNumber(vehicleNumber));
        return "car/edit-car";
    }

    @RequestMapping(value = "/edit-car/{vehicle-number}", method = RequestMethod.PUT)
    public void updateCarPost(@PathVariable("vehicle-number") long vehicleNumber){
        if (carRepository.findCarByVehicleNumber(vehicleNumber) != null){
            Car car = carRepository.findCarByVehicleNumber(vehicleNumber);
            carRepository.updateCar(car);
        }
    }

    @DeleteMapping(value = "/delete-car/{vehicle-number}")
    public void deleteCar(@PathVariable("vehicle-number") long vehicleNumber){
        if (carRepository.findCarByVehicleNumber(vehicleNumber) != null){
            carRepository.deleteCarByVehicleNumber(vehicleNumber);
        }
    }

}