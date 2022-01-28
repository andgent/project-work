package org.generation.italy.service;


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
		
		int capienza = prenotazione.getEvento().getLocation().getCapienza();
		int occupati = 0;
		for (Prenotazione p : repository.findByEvento(prenotazione.getEvento().getId())) {
			occupati += p.getNumeroPrenotati();
		}
		
		if(capienza - occupati >= prenotazione.getNumeroPrenotati()) {
			return true;
		}
		
		return false;
	}
	
}
