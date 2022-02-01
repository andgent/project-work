package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Location;
import org.generation.italy.repository.EventoRepository;
import org.generation.italy.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

	@Autowired
	private LocationRepository repository;
	
	@Autowired
	private EventoRepository eventoRepository;
	
	public List<Location> findAllSortedByName() {
		return repository.findAll(Sort.by("nome"));
	}
	
	public Location save(Location location) {
		return repository.save(location);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
	
	
	// controllo se in una location ci sono eventi
	public boolean hasEvent(Integer id) {
		
		if(eventoRepository.findByLocation(id).isEmpty()) {
			return false;
		}
		
		return true;
	}
	
}
