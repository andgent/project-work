package org.generation.italy.repository;

import java.util.List;

import org.generation.italy.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
	
	@Query(value="select * from prenotazione where evento_id = ?", nativeQuery = true)
	public List<Prenotazione> findByEvento(Integer id);
	
}
