package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.BookingRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
 * Written by Thomas S. Andersen
 */
@Controller
public class BookingController {

	private BookingRepository br;

	public BookingController(BookingRepository br) {
		this.br = br;
	}

	@GetMapping("/bookings")
	public String index(@AuthenticationPrincipal User user, Model model){

		model.addAttribute("bookinglist", br.getBookingList(user));

		return "bookings/index";
	}

	@GetMapping("/bookings/create")
	public String showCreate(Model model){
		model.addAttribute("booking", new Booking());
		return "bookings/create";
	}

	@PostMapping("/bookings/create")
	public String create(@ModelAttribute("booking") Booking booking) {
		br.createBooking(booking);
		return "redirect:/bookings/show/" + br.last().getId();
	}

	@GetMapping("/bookings/edit/{id}")
	public String updateBooking(Model model, @PathVariable Long id) {
		model.addAttribute("booking", br.find("id", id));
		return "bookings/edit";
	}

	@PatchMapping("/bookings/edit/{id}")
	public String editBooking(Booking booking) {
		br.updateBooking(booking);
		return "redirect:/bookings/show/" + booking.getId();
	}

	@GetMapping("/bookings/show/{id}")
	public String showBooking(Model model, @AuthenticationPrincipal Long id) {
		model.addAttribute("booking", br.find("id", id));
		return "bookings/show";
	}
}
