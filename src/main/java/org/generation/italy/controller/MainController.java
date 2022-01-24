package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Evento;
import org.generation.italy.service.CategoriaService;
import org.generation.italy.service.EventoService;
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
	
}
