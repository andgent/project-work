package org.generation.italy.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.generation.italy.model.Categoria;
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
	
	// ricerca eventi per keyword
	public List<Evento> findByKeyword(String keyword){
		return repository.findByNomeContainingIgnoreCase(keyword);
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
	
	// filtro per categorie
	public List<Evento> findByCategorie(List<Categoria> filtriCat){
		
		List<Evento> allEventi = new ArrayList<Evento>();
		List<Evento> eventi = new ArrayList<Evento>();
		int i=0;
		
		for (Categoria cat : filtriCat) {
			List<Evento> e = repository.findByCategoria(cat.getId());
			i++;
			if (i == 1) {
				allEventi = e;
			} else {
				for (Evento evento : allEventi) {
					if (e.contains(evento)) {
						eventi.add(evento);
					}
				}
				allEventi = new ArrayList<Evento>();
				allEventi.addAll(eventi);
				eventi.clear();
			}
		}

		return allEventi;
	}
	
	// filtro per regione
	public List<Evento> findByRegione(String filtroRegione){
		return repository.findByRegione(filtroRegione);
	}
	
	//filtro per openSpace
	public List<Evento> findByOpenSpace(Boolean filtroOpenSpace){
		return repository.findByOpenSpace(filtroOpenSpace);
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
	
	public Evento save(Evento evento) {
		return repository.save(evento);
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
        evento.setModificato(false);
        evento.setAnnullato(false);
        evento.setPubblicato(false);
        if(eventoForm.getLocandina() != null && !eventoForm.getLocandina().isEmpty()) {
            evento.setLocandina(eventoForm.getLocandina().getBytes());
        }
        return repository.save(evento);
	}
	
        //modifica evento
        public Evento update(EventoForm eventoForm, Evento evento) throws IOException {
            if(evento.isPubblicato()) {
            	evento.setModificato(true);
            }
            evento.setNome(eventoForm.getNome());
            evento.setCategorie(eventoForm.getCategorie());
            evento.setBiglietto(eventoForm.getBiglietto());
            evento.setDescrizione(eventoForm.getDescrizione());
            evento.setLocation(eventoForm.getLocation());
            evento.setDataInizio(LocalDateTime.parse(eventoForm.getDataInizio()));
            evento.setDataFine(LocalDateTime.parse(eventoForm.getDataFine()));
            if(eventoForm.getLocandina() != null && !eventoForm.getLocandina().isEmpty()) {
                evento.setLocandina(eventoForm.getLocandina().getBytes());
            }
            return repository.save(evento);
           
    }
        
        public boolean isFuturo(String data) {
        	LocalDateTime d= LocalDateTime.parse(data);
        	if(d.isBefore(LocalDateTime.now())) {
        		return false;
        	}
        	return true;
        }

}