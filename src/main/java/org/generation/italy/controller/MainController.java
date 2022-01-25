package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Evento;
import org.generation.italy.model.Location;
import org.generation.italy.service.CategoriaService;
import org.generation.italy.service.EventoService;
import org.generation.italy.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eventi")

public class MainController {

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private LocationService locationService;
	
	@GetMapping
	public String eventsList(Model model) {
	
		model.addAttribute("list", eventoService.findAllSortedByName());
		
		return "/client/eventList";
	}
	
	@GetMapping("/admin")
	public String adminEventsList(Model model) {
		
		model.addAttribute("list", eventoService.findAllSortedByName());		
		return "/admin/adminEventList";
	}
	
	@GetMapping("/admin/create")
	public String createEvent(Model model) {
		model.addAttribute("list", locationService.findAllSortedByName());
		model.addAttribute("evento", new Evento());
		return "/admin/create";
	}
	
	@PostMapping("/admin/create")
	public String doCreateEvent(@Valid @ModelAttribute("evento")Evento evento, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "/admin/create";
		}
		eventoService.save(evento);
		return "redirect:/eventi/admin";
	}
	
	@GetMapping("/admin/createLocation")
	public String createLocation(Model model) {
		model.addAttribute("location", new Location());
		return "/admin/createLocation";
	}
	
	@PostMapping("/admin/createLocation")
	public String doCreateLocation(@ModelAttribute("location")Location location, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "/admin/createLocation";
		}
		locationService.save(location);
		return "redirect:/eventi/admin";
	}

}
