package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.services.CarInvoiceService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * Written by Nicolai Berg Andersen and Thomas S. Andersen
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

	@Autowired
    CarInvoiceService CarInvoiceService;

	@GetMapping("/bookings")
	public String index(@AuthenticationPrincipal User user, Model model) {

		/*
         * Employees kan se alle bookings.
         * & andre kan se deres egne bookings.
         */
        if (user.isEmployee()) {
            model.addAttribute("bookinglist", br.getBookingList());
        } else {
            model.addAttribute("bookinglist", br.getBookingList(user));
        }

		return "bookings/index";
	}
//Thomas
	@GetMapping("/bookings/create")
	public String showCreate(Model model){
		model.addAttribute("booking", new Booking());
		return "bookings/create";
	}

	@PostMapping("/bookings/create")
	public String create(@ModelAttribute("booking") Booking booking, RedirectAttributes redirectAttributes) {

		// Sørger for der ikke oprettes en booking på en bil,
		// som allerede er udlejet.
		long vehicleNumber = booking.getVehicleNumber();
		if (!carRepository.isCarAvailableForRent(vehicleNumber)) {
			redirectAttributes.addAttribute("response", "Bilen er ikke tilgængelig for udlejning!");
        	redirectAttributes.addAttribute("state", "danger");
			return "redirect:/cars";
		} else {
			// Sæt first rented at, hvis den ikke er sat.
			Car car = carRepository.findCarByVehicleNumber(vehicleNumber);
			if (car.getFirstRentedAt() == null) {
				carRepository.setFirstRentedAt(vehicleNumber);
			}

			br.createBooking(booking);

			Booking last = br.last();
			long id = last.getId();

			/* Send invoice. */
			bookingInvoiceService.execute(id);

			redirectAttributes.addAttribute("response", "Udlejningsaftale oprettet.");
			redirectAttributes.addAttribute("state", "success");

			return "redirect:/bookings/show/" + id;
		}
	}

	//Thomas
	@GetMapping("/bookings/kilometer_driven/{id}")
	public String updateBooking(Model model, @PathVariable Long id) {
		model.addAttribute("booking", br.find("id", id));
		return "bookings/edit_kilometer_driven";
	}

	@GetMapping("/bookings/deliver/{id}")
	public String deliver(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		br.setDeliveredAt(id);

		redirectAttributes.addAttribute("response", "Bil afleveret til kunde.");
        redirectAttributes.addAttribute("state", "success");

		return "redirect:/bookings";
	}

	@GetMapping("/bookings/return/{id}")
	public String returning(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		br.setReturnedAt(id);

		redirectAttributes.addAttribute("response", "Bil afleveret til beholdning.");
        redirectAttributes.addAttribute("state", "success");

		return "redirect:/bookings";
	}

	@GetMapping("/bookings/sell/{id}")
    public String sell(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Booking booking = br.findByBookingId(id);
		
		// Set returned at (important)
		br.setReturnedAt(id);

		// sell car
        carRepository.sellCar(booking.getVehicleNumber());
        CarInvoiceService.execute(booking.getId());

		redirectAttributes.addAttribute("response", "Bil solgt til kunde.");
        redirectAttributes.addAttribute("state", "success");

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

	@PatchMapping("/bookings/edit/{id}")
	public String updateBooking(Booking booking, @PathVariable Long id, RedirectAttributes redirectAttributes) {
		br.setKilometerDrivenAt(id, booking.getKilometerDriven());

		redirectAttributes.addAttribute("response", "Udlejningsaftale opdateret.");
        redirectAttributes.addAttribute("state", "success");

		return "redirect:/bookings";
	}
}
