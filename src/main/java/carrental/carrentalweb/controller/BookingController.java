package carrental.carrentalweb.controller;


import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.repository.PickupPointRepository;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class BookingController {

  private final BookingRepository br;

  public BookingController(BookingRepository br) {
    this.br = br;
  }



  @GetMapping("bookings/index{id}")
  public String index(@AuthenticationPrincipal User user, Model model){

    model.addAttribute("bookinglist", br.getBookingList(user));

    return "bookings/index";
  }

  @PostMapping()
  public String index(){

    return "redirect:bookings/edit";
  }

  /*@GetMapping("bookings/show/{id}")
  public String show(@AuthenticationPrincipal User user, Model model){


    return "bookings/show";
  }*/

  @GetMapping("bookings/create/{id}")
  public String showCreate(@AuthenticationPrincipal User user){


    return "bookings/create";
  }

  @PostMapping("bookings/create/{id}")
  public String create(@AuthenticationPrincipal User user){

    Booking newBooking = new Booking();

    br.createBooking(newBooking);

    return "bookings/create";
  }

  @GetMapping("bookings/edit/{id}")
  public String update(Model model, @RequestParam("booking") Booking booking){

    model.addAttribute("booking", booking);

    return "bookings/edit";
  }

  @PostMapping("bookings/edit/{id}")
  public String edit(@RequestParam("bookingId") Long id, @RequestBody Booking editBooking){

    br.updateBooking(editBooking, id);

    return "redirect:bookings/show";
  }
}
