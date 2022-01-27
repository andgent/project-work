package org.generation.italy.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.generation.italy.model.Categoria;
import org.generation.italy.model.EventoForm;
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
import org.springframework.web.bind.annotation.PathVariable;
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
		model.addAttribute("listCat", categoriaService.findAllSortedByName());
		model.addAttribute("listEve", eventoService.findAllSortedByName());

		return "/client/eventList";
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
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("listLoc", locationService.findAllSortedByName());
			model.addAttribute("listCat", categoriaService.findAllSortedByName());
			model.addAttribute("edit", false);
			return "/admin/create";
		}
		try {
			eventoService.save(eventoForm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/eventi/admin";
	}

	// edita evento
	@GetMapping("/admin/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("eventoForm", eventoService.getById(id));
		model.addAttribute("listLoc", locationService.findAllSortedByName());
		model.addAttribute("listCat", categoriaService.findAllSortedByName());
		return "/admin/create";
	}

	@PostMapping("/admin/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("eventoForm") EventoForm eventoForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("eventoForm", eventoService.getById(id));
			model.addAttribute("listLoc", locationService.findAllSortedByName());
			model.addAttribute("listCat", categoriaService.findAllSortedByName());
			model.addAttribute("edit", true);
			return "/admin/create";
		}

		try {
			eventoService.update(eventoForm, eventoService.getById(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/eventi/admin";
	}

	// creazione location
	@GetMapping("/admin/createLocation")
	public String createLocation(Model model) {
		model.addAttribute("location", new Location());
		model.addAttribute("listLoc", locationService.findAllSortedByName());
		return "/admin/createLocation";
	}

	@PostMapping("/admin/createLocation")
	public String doCreateLocation(@Valid @ModelAttribute("location") Location location, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("listLoc", locationService.findAllSortedByName());
			return "/admin/createLocation";
		}
		locationService.save(location);
		return "redirect:/eventi/admin";
	}

	// crea categoria
	@GetMapping("/admin/createCategory")
	public String createCategoria(Model model) {
		model.addAttribute("listCat", categoriaService.findAllSortedByName());
		model.addAttribute("categoria", new Categoria());
		return "/admin/createCategory";
	}

	@PostMapping("/admin/createCategory")
	public String doCreateCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("listCat", categoriaService.findAllSortedByName());
			return "/admin/createCategory";
		}
		categoriaService.save(categoria);
		return "redirect:/eventi/admin";
	}

	// delete
	@GetMapping("admin/delete/{id}")
	public String doDelete(@PathVariable("id") Integer id) {
		eventoService.deleteById(id);
		return "redirect:/eventi/admin";
	}

	@GetMapping("admin/delete/loc/{id}")
	public String doDeleteLoc(@PathVariable("id") Integer id) {
		locationService.deleteById(id);
		return "redirect:/eventi/admin/createLocation";
		
	}
	

	@GetMapping("admin/delete/cat/{id}")
	public String doDeleteCat(@PathVariable("id") Integer id) {
		categoriaService.deleteById(id);
		return "redirect:/eventi/admin/createCategory";
	}

}
