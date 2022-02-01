package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Categoria;
import org.generation.italy.repository.CategoriaRepository;
import org.generation.italy.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private EventoRepository eventoRepository;

	public List<Categoria> findAllSortedByName() {
		return repository.findAll(Sort.by("nome"));
	}

	public Categoria save(Categoria categoria) {
		return repository.save(categoria);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	public Categoria getById(Integer id) {
		return repository.getById(id);
	}
	
	public boolean hasEvent(Integer id) {

		if (eventoRepository.findByCategoria(id).isEmpty()) {
			return false;
		}

		return true;
	}
	
}
