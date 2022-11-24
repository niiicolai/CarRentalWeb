package carrental.carrentalweb.controller;


import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.repository.PickupPointRepository;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("bookings/create")
  public String showCreate(Model model){
    model.addAttribute("booking", new Booking());

    return "bookings/create";
  }

  @PostMapping("bookings/create")
  public String create(Booking booking){

    br.createBooking(booking);

    return "bookings/create";
  }

  @GetMapping("bookings/edit/{id}")
  public String update(Model model, @RequestParam("id") long id){
      model.addAttribute("booking", br.findBookingId(id));

    return "bookings/edit";
  }

  @PatchMapping("bookings/edit")
  public String edit(Booking booking){

    br.updateBooking(booking);

    return "redirect:bookings/show";
  }
}
