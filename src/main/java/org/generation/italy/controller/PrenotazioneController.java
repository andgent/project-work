package org.generation.italy.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.generation.italy.model.Prenotazione;
import org.generation.italy.service.EventoService;
import org.generation.italy.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prenota")
public class PrenotazioneController {
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private EventoService eventoService;
	
	@GetMapping("/{id}")
	public String prenota(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("prenotazione", new Prenotazione());
		model.addAttribute("evento", eventoService.getById(id));
		return "/client/prenotazione";
	}
	
	@PostMapping("/{eventoId}")
	public String doPrenota(Model model,@Valid @ModelAttribute("prenotazione") Prenotazione prenotazione, BindingResult bindingResult, @PathVariable("eventoId") Integer id) {
		prenotazione.setEvento(eventoService.getById(id));
		prenotazione.setDataPrenotazione(LocalDateTime.now());
		
		if(!prenotazioneService.isValidPrenota(prenotazione)) {
			bindingResult.addError(new ObjectError("numeroPrenotati", "Impossibile prenotare più biglietti di quanti siano disponibili!"));
		}
		
		if(!prenotazioneService.isValidTime(prenotazione)) {
			bindingResult.addError(new ObjectError("dataPrenotazione", "Impossibile prenotare un evento imminente!"));
		}
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("evento", eventoService.getById(id));
			return "/client/prenotazione";
		}
		
		prenotazioneService.save(prenotazione);
		return "redirect:/eventi";
	}
	
}
