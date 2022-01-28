package org.generation.italy.repository;

import java.util.List;

import org.generation.italy.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
	 List<Evento> findByNomeContainingIgnoreCase(String keyword);
	
}
