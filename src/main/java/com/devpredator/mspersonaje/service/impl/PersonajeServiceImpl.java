/**
 * 
 */
package com.devpredator.mspersonaje.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpredator.msentity.entity.Personaje;
import com.devpredator.mspersonaje.repository.PersonajeRepository;
import com.devpredator.mspersonaje.service.PersonajeService;

/**
 * @author DevPredator
 *
 */
@Service
public class PersonajeServiceImpl implements PersonajeService {

	@Autowired
	private PersonajeRepository personajeRepository;
	
	@Override
	public List<Personaje> findPersonajes() {
		
		List<Personaje> personajes = StreamSupport.stream(
				this.personajeRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		return personajes;
	}

	@Override
	public Optional<Personaje> findPersonajeById(Long id) {
		return this.personajeRepository.findById(id);
	}

	@Override
	public Personaje savePersonaje(Personaje personaje) {
		return this.personajeRepository.save(personaje);
	}

	@Override
	public void deletePersonajeById(Long id) {
		this.personajeRepository.deleteById(id);
	}

}
