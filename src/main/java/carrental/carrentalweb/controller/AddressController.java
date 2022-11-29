package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.repository.AddressRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressController {

  private AddressRepository ar;

  public AddressController(AddressRepository ar) {
    this.ar = ar;
  }

  @GetMapping("/address")
  public String index(Model model){

    model.addAttribute("addresslist", ar.getAddressList());

    return "address/index";
  }

  @GetMapping("/address/create")
  public String showCreate(Model model){
    model.addAttribute("address", new Address());
    return "address/create";
  }

  //Added extra e to resolve conflict
  @PostMapping("/bookings/createe")
  public String create(Address address) {
    ar.createAddress(address);
    return "redirect:/address";
  }
}
