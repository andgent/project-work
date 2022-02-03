package org.generation.italy.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.generation.italy.model.Categoria;
import org.generation.italy.model.Evento;
import org.generation.italy.model.EventoForm;
import org.generation.italy.model.Prenotazione;
import org.generation.italy.service.CategoriaService;
import org.generation.italy.service.EventoService;
import org.generation.italy.service.LocationService;
import org.generation.italy.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/eventi")

public class MainController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private PrenotazioneService prenotazioneService;

	@GetMapping
	public String list(Model model, @RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "filtriCat", required = false) List<Categoria> filtriCat,
			@RequestParam(name = "filtroRegione", required = false) String filtroRegione) {
		List<Evento> result = new ArrayList<Evento>();

		if (keyword != null && !keyword.isEmpty()) {
			result = eventoService.findByKeyword(keyword);
			model.addAttribute("keyword", keyword);
		} else if (filtriCat != null && !filtriCat.isEmpty() && filtroRegione != null && !filtroRegione.isEmpty()) {
			List<Evento> result1;
			List<Evento> result2;
			result1 = eventoService.findByCategorie(filtriCat);
			result2 = eventoService.findByRegione(filtroRegione);
			for (Evento evento : result1) {
				if (result2.contains(evento)) {
					result.add(evento);
				}
			}
			model.addAttribute("filtroRegione", filtroRegione);
			model.addAttribute("filtriCat", filtriCat);
		} else if (filtriCat != null && !filtriCat.isEmpty()) {
			result = eventoService.findByCategorie(filtriCat);
			model.addAttribute("filtriCat", filtriCat);
		} else if (filtroRegione != null && !filtroRegione.isEmpty()) {
			result = eventoService.findByRegione(filtroRegione);
			model.addAttribute("filtroRegione", filtroRegione);
		} else {
			result = eventoService.findAllSortedByName();
		}

		model.addAttribute("listCat", categoriaService.findAllSortedByName());
		model.addAttribute("listLoc", locationService.findAllSortedByName());
		model.addAttribute("list", result);
		model.addAttribute("service", prenotazioneService);
		model.addAttribute("eventoService", eventoService);
		return "/client/eventList";
	}

	// dettagli
	@GetMapping("/dettagli/{id}")
	public String dettagli(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("evento", eventoService.getById(id));
		model.addAttribute("postiDisponibili", prenotazioneService.calcolaPosti(eventoService.getById(id)));
		model.addAttribute("prenotazione", new Prenotazione());
		model.addAttribute("eventoService", eventoService);
		return "/client/dettagli";
	}

	@PostMapping("/dettagli/{eventoId}")
	public String doPrenota(Model model, @Valid @ModelAttribute("prenotazione") Prenotazione prenotazione,
			BindingResult bindingResult, @PathVariable("eventoId") Integer id, RedirectAttributes redirectAttributes) {
		prenotazione.setEvento(eventoService.getById(id));
		prenotazione.setDataPrenotazione(LocalDateTime.now());

		if (prenotazione != null) {

			if (!prenotazioneService.isValidPrenota(prenotazione)) {
				bindingResult.addError(new ObjectError("numeroPrenotati",
						"Impossibile prenotare pi� biglietti di quanti siano disponibili!"));
			}

			if (!prenotazioneService.isValidTime(prenotazione)) {
				bindingResult
						.addError(new ObjectError("dataPrenotazione", "Impossibile prenotare un evento imminente!"));
			}
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("evento", eventoService.getById(id));
			return "/client/dettagli";
		}

		prenotazioneService.save(prenotazione);
		redirectAttributes.addFlashAttribute("message", "Prenotazione effettuata con successo");
		return "redirect:/eventi/dettagli/{eventoId}";
	}

	// visualizzazione locandina
	@RequestMapping(value = "/dettagli/{id}/photo", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getPhotoContent(@PathVariable Integer id) {
		byte[] photoContent = eventoService.getById(id).getLocandina();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(photoContent, headers, HttpStatus.OK);
	}

	// lista eventi
	@GetMapping("/admin")
	public String adminEventsList(Model model) {
		model.addAttribute("listCat", categoriaService.findAllSortedByName());
		model.addAttribute("listEve", eventoService.findAllSortedByName());
		return "/admin/adminEventList";
	}

	// creazione evento
	@GetMapping("/admin/create")
	public String createEvent(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("listLoc", locationService.findAllSortedByName());
		model.addAttribute("listCat", categoriaService.findAllSortedByName());
		model.addAttribute("eventoForm", new EventoForm());
		return "/admin/create";
	}

	@PostMapping("/admin/create")
	public String doCreateEvent(@Valid @ModelAttribute("eventoForm") EventoForm eventoForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {

		if (!eventoForm.getDataInizio().isEmpty() && !eventoForm.getDataFine().isEmpty()) {
			if (eventoService.isValidData(LocalDateTime.parse(eventoForm.getDataInizio()),
					LocalDateTime.parse(eventoForm.getDataFine()))) {
				bindingResult.addError(new ObjectError("dataInizio", "Le date inserite non vanno bene!"));
				model.addAttribute("dataError", "La data di fine non puo essere precedente a quella di inizio evento!");
			}
			if (eventoService.isValidLocation(eventoService.findAllSortedByName(), eventoForm)) {

				model.addAttribute("locationError", "Location gia utilizzata!");
				bindingResult.addError(new ObjectError("location", "Location gia' occupata in questa data!"));
			}
			if (!eventoService.isFuturo(eventoForm.getDataInizio())) {
				bindingResult.addError(new ObjectError("dataInizio", "Le date inserite non vanno bene!"));
				model.addAttribute("dataError", "La data di inizio e passata");
			}
		}

		if (bindingResult.hasErrors()) {
			ritornoErrori(model);
			model.addAttribute("edit", false);
			return "/admin/create";
		}

		try {
			eventoService.save(eventoForm);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/eventi/admin";
	}

	// edita evento
	@GetMapping("/admin/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("eventoForm", eventoService.getById(id).toEventoForm());
		ritornoErrori(model);
		return "/admin/create";
	}

	@PostMapping("/admin/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("eventoForm") EventoForm eventoForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Model model) {

		if (!eventoForm.getDataInizio().isEmpty() && !eventoForm.getDataFine().isEmpty()) {
			if (eventoService.isValidData(LocalDateTime.parse(eventoForm.getDataInizio()),
					LocalDateTime.parse(eventoForm.getDataFine()))) {
				bindingResult.addError(new ObjectError("dataInizio", "Le date inserite non vanno bene!"));
			}
			if (eventoService.isValidLocation(eventoService.findAllSortedByName(), eventoForm, id)) {
				bindingResult.addError(new ObjectError("dataInizio", "Location gi� occupata in questa data!"));
			}
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("eventoForm", eventoService.getById(id).toEventoForm());
			ritornoErrori(model);
			model.addAttribute("edit", true);
			return "/admin/create";
		}

		try {
			eventoService.update(eventoForm, eventoService.getById(id));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/eventi/admin";
	}

	// delete
	@GetMapping("/admin/delete/{id}")
	public String doDelete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		if (eventoService.getById(id).isPubblicato()) {
			redirectAttributes.addFlashAttribute("message", "Impossibile cancellare");
			return "redirect:/eventi/admin";
		}

		eventoService.deleteById(id);
		return "redirect:/eventi/admin";
	}

	// pubblica
	@GetMapping("/admin/pubblica/{id}")
	public String doPubblica(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		eventoService.getById(id).setPubblicato(true);
		eventoService.save(eventoService.getById(id));
		redirectAttributes.addFlashAttribute("message", "Evento pubblicato correttamente");
		return "redirect:/eventi/admin";
	}

	// annulla evento pubblicato
	@GetMapping("/admin/annulla/{id}")
	public String doAnnulla(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		eventoService.getById(id).setAnnullato(true);
		eventoService.save(eventoService.getById(id));
		redirectAttributes.addFlashAttribute("message", "Evento annullato correttamente");
		return "redirect:/eventi/admin";
	}
	
	// pagina eventi passati
	@GetMapping ("/admin/eventiPassati")
    public String eventiPassati(Model model) {
        model.addAttribute("listEve", eventoService.findAllSortedByName());
        model.addAttribute("service", eventoService);
        return "/admin/adminPastEventList";
    }

	private void ritornoErrori(Model model) {
		model.addAttribute("listLoc", locationService.findAllSortedByName());
		model.addAttribute("listCat", categoriaService.findAllSortedByName());
	}

}
