package carrental.carrentalweb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {

  @GetMapping("bookings/index")
  public String index(){

    return "bookings/index";
  }



}
