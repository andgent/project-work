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
	
	public boolean isValidLocation(List<Evento> eventList, EventoForm form) {
		boolean ok;
		for(Evento evento:eventList) {
			
			if(evento.getLocation() == form.getLocation()) {
				ok = isValidData(evento.getDataInizio(),evento.getDataFine(),LocalDateTime.parse(form.getDataInizio()),LocalDateTime.parse(form.getDataFine()));
				if(!ok) {
					return true;
				}
			}
		}
		return false;
	}
	
	// controllo se ci sono eventi con la stessa location
	public boolean isValidLocation(List<Evento> eventList, EventoForm form, Integer id) {
		boolean ok;
		for(Evento evento:eventList) {
			if(evento.getId() != id) {
				if(evento.getLocation() == form.getLocation()) {
					ok = isValidData(evento.getDataInizio(),evento.getDataFine(),LocalDateTime.parse(form.getDataInizio()),LocalDateTime.parse(form.getDataFine()));
					if(!ok) {
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	
	// controllo se in una stessa location 2 eventi si sovrappongono
	public boolean isValidData(LocalDateTime dataInizio1, LocalDateTime dataFine1, LocalDateTime dataInizio2, LocalDateTime dataFine2) {
		if(dataInizio1.isAfter(dataFine2)) {
			return true;
		}else if(dataInizio2.isAfter(dataFine1)) {
			return true;
		}
		return false;
	}
	
	//controllo se la data di fine è dopo la data di inizio
	public boolean isValidData(LocalDateTime dataInizio, LocalDateTime dataFine) {
		if(dataInizio.isAfter(dataFine)) {
			return true;
		}
		return false;
	}
	

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