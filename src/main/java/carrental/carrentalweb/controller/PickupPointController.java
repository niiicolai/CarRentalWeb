package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.repository.PickupPointRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public String updatePickupPoint(){
    //hvad skal man opdatere ud fra? Skal man vælge en bestemt attribut der skal ændres ved address eller pickuppoint eller skal det hele bare genindtastes?
    return "pickuppoints/edit";
  }

 /* @PostMapping()
  public String findPickupPointByName(Model model, String name){

    model.addAttribute(ppRepo.findPickupPointByName(name));

    return "";
  }

  @PostMapping()
  public String findPickupPointByZip(Model model, String zipCode){

    model.addAttribute(ppRepo.findPickupPointByZipcode(zipCode));

    return "";
  }
*/
}
