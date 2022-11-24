package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.repository.PickupPointRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class PickupPointController {

  private final PickupPointRepository ppRepo;

  public PickupPointController(PickupPointRepository ppRepo) {
    this.ppRepo = ppRepo;
  }


  @GetMapping("pickuppoints/index")
  public String index(Model model){
    model.addAttribute("pickuppoints", ppRepo.getPickupPointsList());
    return "pickuppoints/index";
  }

  @GetMapping("pickuppoints/create")
  public String newPickupPoint(){

    return "pickupoints/create";
  }

  @PostMapping("pickuppoints/create")
  public String createPickupPoint(@RequestParam("name") String name,
                                  @RequestParam("street") String street,
                                  @RequestParam("city") String city,
                                  @RequestParam("zipcode") String zipCode,
                                  @RequestParam("country") String country){

    PickupPoint newPickupPoint = new PickupPoint(name, new Address(street, city, zipCode, country, LocalDateTime.now()), LocalDateTime.now());

    ppRepo.createPickupPoint(newPickupPoint);

    return "pickuppoints/create";
  }

  @GetMapping("pickuppoints/edit")
  public String editPickupPoint(){
    return "pickupoints/edit";
  }

  @PostMapping("pickuppoints/edit")
  public String updatePickupPoint(@RequestParam("pickuppointname") String name, @RequestBody PickupPoint pp){

    ppRepo.updatePickupPoint(pp, name);
    return "pickuppoints/edit";
  }

}
