package org.generation.italy.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.generation.italy.model.Evento;
import org.generation.italy.model.EventoForm;
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
	
//	public Evento update(Evento evento) {
//		return repository.save(evento);
//	}
//
//	public Evento save(Evento evento) {
//		return repository.save(evento);
//	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	public Evento getById(Integer id) {
		return repository.getById(id);
	}
	
	public Evento save(EventoForm eventoForm) throws IOException {
        Evento evento = new Evento();
        evento.setNome(eventoForm.getNome());
        evento.setCategorie(eventoForm.getCategorie());
        evento.setBiglietto(eventoForm.getBiglietto());
        evento.setDescrizione(eventoForm.getDescrizione());
        evento.setLocation(eventoForm.getLocation());
        evento.setDataInizio(LocalDateTime.parse(eventoForm.getDataInizio()));
        evento.setDataFine(LocalDateTime.parse(eventoForm.getDataFine()));

        if(eventoForm.getLocandina() != null) {
            evento.setLocandina(eventoForm.getLocandina().getBytes());
        }
        return repository.save(evento);
	}
        
        public Evento update(EventoForm eventoForm, Evento evento) throws IOException {
            
            evento.setNome(eventoForm.getNome());
            evento.setCategorie(eventoForm.getCategorie());
            evento.setBiglietto(eventoForm.getBiglietto());
            evento.setDescrizione(eventoForm.getDescrizione());
            evento.setLocation(eventoForm.getLocation());
            evento.setDataInizio(LocalDateTime.parse(eventoForm.getDataInizio()));
            evento.setDataFine(LocalDateTime.parse(eventoForm.getDataFine()));

            if(eventoForm.getLocandina() != null) {
                evento.setLocandina(eventoForm.getLocandina().getBytes());
            }
            return repository.save(evento);
           
    }
	
	
	
	
}