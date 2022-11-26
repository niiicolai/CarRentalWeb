package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.repository.PickupPointRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PickupPointController {

	private final PickupPointRepository ppRepo;

	public PickupPointController(PickupPointRepository ppRepo) {
		this.ppRepo = ppRepo;
	}

	@GetMapping("/pickups")
	public String index(Model model) {
		model.addAttribute("pickupPoints", ppRepo.getPickupPointsList());
		return "pickups/index";
	}

	@GetMapping("/pickups/create")
	public String newPickupPoint(Model model) {
		model.addAttribute("pickupPoint", new PickupPoint());
		return "pickups/create";
	}

	@PostMapping("/pickups/create")
	public String createPickupPoint(PickupPoint pickupPoint) {
		ppRepo.createPickupPoint(pickupPoint);
		return "redirect:/pickups";
	}

	@GetMapping("/pickups/edit/{id}")
	public String updatePickupPoint(Model model, @PathVariable("id") Long id) {
		model.addAttribute("pickupPoint", ppRepo.findPickupPointById(id));
		return "pickups/edit";
	}

	@PatchMapping("/pickups/edit")
	public String editPickupPoint(PickupPoint pickupPoint) {
		ppRepo.updatePickupPoint(pickupPoint);
		return "redirect:/pickups";
	}
}
