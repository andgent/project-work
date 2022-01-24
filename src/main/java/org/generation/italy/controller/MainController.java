package org.generation.italy.controller;

import org.generation.italy.model.Evento;
import org.generation.italy.service.CategoriaService;
import org.generation.italy.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eventi")

public class MainController {

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private EventoService eventoService;
	
	@GetMapping
	public String list(@ModelAttribute("evento") Evento evento, Model model) {
	
		model.addAttribute("list", evento);
		
		return "/client/eventList";
	}
	
	@GetMapping("/admin")
	public String adminlist(Model model) {
		
		return "/admin/adminEventList";
	}
	
}
