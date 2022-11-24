package carrental.carrentalweb.controller;


import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.repository.PickupPointRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class BookingController {

  private final BookingRepository br;

  public BookingController(BookingRepository br) {
    this.br = br;
  }


  @GetMapping("bookings/index")
  public String index(HttpSession session, Model model){

    return "bookings/index";
  }

  @GetMapping("bookings/show/{id}")
  public String show(@AuthenticationPrincipal User user, Model model){

    model.addAttribute("bookinglist", br.getBookingList(user));

    return "bookings/show";
  }

  @GetMapping("bookings/create")
  public String showCreate(HttpSession session){

    return "bookings/create";
  }

  @GetMapping("bookings/create")
  public String create(HttpSession session){

    return "bookings/create";
  }
}
