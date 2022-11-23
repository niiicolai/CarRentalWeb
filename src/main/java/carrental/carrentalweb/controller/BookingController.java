package carrental.carrentalweb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BookingController {

  @GetMapping("bookings/index")
  public String index(HttpSession session, Model model){

    return "bookings/index";
  }

  @GetMapping("bookings/show")
  public String show(HttpSession session, Model model){

    return "bookings/show";
  }

  @GetMapping("bookings/create")
  public String create(HttpSession session, Model model){

    return "bookings/create";
  }
}
