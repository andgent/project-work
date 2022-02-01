package org.generation.italy.repository;

import java.util.List;

import org.generation.italy.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
	
	 List<Evento> findByNomeContainingIgnoreCase(String keyword);
	
	 
	 @Query(value="select e.*\r\n"
	 		+ "from evento e \r\n"
	 		+ "join evento_categorie ec on e.id = ec.evento_id \r\n"
	 		+ "join categoria c on c.id = ec.categorie_id \r\n"
	 		+ "where c.id = ?", nativeQuery = true)
	 List<Evento> findByCategoria(Integer categoria);
	 
	 @Query(value="select e.*\r\n"
		 		+ "from evento e \r\n"
		 		+ "where e.location_id = ?", nativeQuery = true)
	 List<Evento> findByLocation(Integer id);
	 
	
	 
}
