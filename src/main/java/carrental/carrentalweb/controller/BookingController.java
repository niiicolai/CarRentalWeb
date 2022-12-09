package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.repository.CarRepository;
import carrental.carrentalweb.repository.DamageReportRepository;
import carrental.carrentalweb.repository.InvoiceRepository;
import carrental.carrentalweb.repository.PickupPointRepository;
import carrental.carrentalweb.repository.SubscriptionRepository;
import carrental.carrentalweb.services.BookingInvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
 * Written by Thomas S. Andersen
 */
@Controller
public class BookingController {

	@Autowired
	private BookingInvoiceService bookingInvoiceService;

	@Autowired
    private DamageReportRepository damageReportRepository;

	@Autowired
    private InvoiceRepository invoiceRepository;

	@Autowired
    private CarRepository carRepository;

	@Autowired
    private PickupPointRepository pickupPointRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private BookingRepository br;

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

		Booking last = br.last();
		long id = last.getId();

		/* Send invoice. */
		bookingInvoiceService.execute(id);

		return "redirect:/bookings/show/" + id;
	}

	@GetMapping("/bookings/edit/{id}")
	public String updateBooking(Model model, @PathVariable Long id) {
		model.addAttribute("booking", br.find("id", id));
		return "bookings/edit";
	}

	@GetMapping("/bookings/deliver/{id}")
	public String deliver(@PathVariable Long id) {
		br.setDeliveredAt(id);
		return "redirect:/bookings";
	}

	@GetMapping("/bookings/return/{id}")
	public String returning(@PathVariable Long id) {
		br.setReturnedAt(id);
		return "redirect:/bookings";
	}

	@GetMapping("/bookings/show/{id}")
	public String showBooking(Model model, @PathVariable Long id) {
		Booking booking = br.find("id", id);

		model.addAttribute("booking", booking);
		model.addAttribute("invoices", invoiceRepository.where("booking_id", id));
		model.addAttribute("damageReport", damageReportRepository.get("booking_id", id));
		model.addAttribute("car", carRepository.findCarByVehicleNumber(booking.getVehicleNumber()));
		model.addAttribute("pickupPoint", pickupPointRepository.findPickupPointById(booking.getPickupPointId()));
		model.addAttribute("subscription", subscriptionRepository.get("name", booking.getSubscriptionName()));

		return "bookings/show";
	}
}
