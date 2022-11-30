package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.services.InvoiceGeneratorService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	public String create(Booking booking) {
		br.createBooking(booking);
		return "redirect:/cars";
	}
}
