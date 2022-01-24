package org.generation.italy.service;

import org.generation.italy.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

	@Autowired
	private EventoRepository repository;
	
}
