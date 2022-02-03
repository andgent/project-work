package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Location;
import org.generation.italy.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/eventi")
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	// creazione location
		@GetMapping("/admin/createLocation")
		public String createLocation(Model model) {
			model.addAttribute("location", new Location());
			model.addAttribute("listLoc", locationService.findAllSortedByName());
			return "/admin/createLocation";
		}

		@PostMapping("/admin/createLocation")
		public String doCreateLocation(@Valid @ModelAttribute("location") Location location, BindingResult bindingResult,
				Model model, RedirectAttributes redirectAttributes) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("listLoc", locationService.findAllSortedByName());
				return "/admin/createLocation";
			}
			locationService.save(location);
			redirectAttributes.addFlashAttribute("message", "Location creata con successo!");
			return "redirect:/eventi/admin/createLocation";
		}
		
		
		@GetMapping("/admin/delete/loc/{id}")
		public String doDeleteLoc(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
			if(locationService.hasEvent(id)) {
				redirectAttributes.addFlashAttribute("errorMessage", "Impossibile cancellare questa location");
				return "redirect:/eventi/admin/createLocation";
			}
			locationService.deleteById(id);
			redirectAttributes.addFlashAttribute("message", "Location eliminata con successo!");
			return "redirect:/eventi/admin/createLocation";
			
		}
	
}
