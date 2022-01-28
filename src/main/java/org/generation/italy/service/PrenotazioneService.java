package org.generation.italy.service;


import org.generation.italy.model.Evento;
import org.generation.italy.model.Prenotazione;
import org.generation.italy.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
	
	@Autowired
	private PrenotazioneRepository repository;
	
		
	
	public Prenotazione save(Prenotazione prenotati) {
		return repository.save(prenotati);
	}
	
	public Prenotazione getById(Integer id) {
		return repository.getById(id);
	}

	public Prenotazione deleteById(Integer id) {
		return repository.getById(id);
	}

	public boolean isValidPrenota(Prenotazione prenotazione) {
		if(calcolaPosti(prenotazione.getEvento()) >= prenotazione.getNumeroPrenotati()) {
			return true;
		}
		
		return false;
	}
	
	public boolean isValidTime(Prenotazione prenotazione) {
		if(prenotazione.getDataPrenotazione().plusDays(1).isBefore(prenotazione.getEvento().getDataInizio())) {
			return true;
		}
		return false;
	}
	
	//calcolo posti disponibili
	public int calcolaPosti(Evento evento) {
		int capienza = evento.getLocation().getCapienza();
		int occupati=0;
		
		for (Prenotazione p : repository.findByEvento(evento.getId())) {
			occupati += p.getNumeroPrenotati();
		}
		return capienza-occupati;
	}
	
}
