package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Categoria;
import org.generation.italy.service.CategoriaService;
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
public class CategorieController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	// crea categoria
		@GetMapping("/admin/createCategory")
		public String createCategoria(Model model) {
			model.addAttribute("listCat", categoriaService.findAllSortedByName());
			model.addAttribute("categoria", new Categoria());
			return "/admin/createCategory";
		}

		@PostMapping("/admin/createCategory")
		public String doCreateCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
				BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("listCat", categoriaService.findAllSortedByName());			
				return "/admin/createCategory";
			}
			categoriaService.save(categoria);
			redirectAttributes.addFlashAttribute("message", "Categoria creata con successo!");
			return "redirect:/eventi/admin/createCategory";
		}
		
		@GetMapping("/admin/delete/cat/{id}")
		public String doDeleteCat(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
			if(categoriaService.hasEvent(id)) {
				redirectAttributes.addFlashAttribute("errorMessage", "Impossibile cancellare questa categoria");
				return "redirect:/eventi/admin/createCategory";
			}
			categoriaService.deleteById(id);
			redirectAttributes.addFlashAttribute("message", "Categoria eliminata con successo!");
			return "redirect:/eventi/admin/createCategory";
		}
	
}
