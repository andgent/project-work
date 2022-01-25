package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Evento;
import org.generation.italy.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

	@Autowired
	private EventoRepository repository;

	public List<Evento> findAllSortedByName() {
		return repository.findAll(Sort.by("nome"));
	}
	
	public Evento update(Evento evento) {
		return repository.save(evento);
	}

	public Evento save(Evento evento) {
		return repository.save(evento);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	public Evento getById(Integer id) {
		return repository.getById(id);
	}
}