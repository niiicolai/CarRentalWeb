package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.repository.PickupPointRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
  public String newPickupPoint(Model model){
    model.addAttribute("pickupPoint", new PickupPoint());
    return "pickupoints/create";
  }


  @PostMapping("pickuppoints/create")
  public String createPickupPoint(PickupPoint pickupPoint){
    ppRepo.createPickupPoint(pickupPoint);
    return "pickuppoints/create";

  }

  @GetMapping("pickuppoints/edit{id}")
  public String updatePickupPoint(Model model, @RequestParam("name") String name){
    model.addAttribute("pickupPoint", ppRepo.findPickupPointByName(name));
    return "pickupoints/edit";
  }

  @PatchMapping("pickuppoints/edit")
  public String editPickupPoint(PickupPoint pickupPoint){
    ppRepo.updatePickupPoint(pickupPoint);
    return "redirect:/pickuppoints";
  }

}
