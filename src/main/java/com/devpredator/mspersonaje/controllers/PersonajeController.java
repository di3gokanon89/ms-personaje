/**
 * 
 */
package com.devpredator.mspersonaje.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devpredator.msentity.entity.Personaje;
import com.devpredator.mspersonaje.service.PersonajeService;

/**
 * @author DevPredator
 *
 */
@RestController
@RequestMapping("/personaje")
public class PersonajeController {
	/**
	 * Service bean with the businness logic of personajes.
	 */
	@Autowired
	private PersonajeService personajeServiceImpl;	
	/**
	 * Property to get the app environment (DEV, PROD, TEST, ETC.).
	 */
	@Value("${app.environment}")
	String appEnvironment;
	
	/**
	 *  CONSTANT TO GET THE LOGS.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonajeController.class); 
	
	@GetMapping("findPersonajes")
	public ResponseEntity<?> findPersonajes() {
		LOGGER.info("Run findPersonajes service.");
		
		System.out.println(this.appEnvironment);
		
		List<Personaje> personajes = this.personajeServiceImpl.findPersonajes();
		
		LOGGER.info("PERSONAJES FOUNDED: " + personajes.size());
		
		if (personajes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		LOGGER.info("FindPersonajes Service Finished");
		
		return ResponseEntity.ok(personajes);
	}
	
	@GetMapping("findPersonajeById/{id}")
	public ResponseEntity<?> findPersonajeById(@PathVariable("id") Long idPersonaje) {
		
		Optional<Personaje> personajeOptional = this.personajeServiceImpl.findPersonajeById(idPersonaje);
		
		if (!personajeOptional.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(personajeOptional.get());
	}
	
	@PostMapping("savePersonaje")
	public ResponseEntity<?> savePersonaje(@Valid @RequestBody Personaje personaje) {
		
		Personaje personajeSaved = this.personajeServiceImpl.savePersonaje(personaje);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(personajeSaved);
	}
	
	@PutMapping("updatePersonaje")
	public ResponseEntity<?> updatePersonaje(@RequestBody Personaje personaje) {
		Personaje personajeUpdated = this.personajeServiceImpl.savePersonaje(personaje);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(personajeUpdated);
	}
	
	@DeleteMapping("deletePersonajeById/{id}")
	public ResponseEntity<?> deletePersonajeById(@PathVariable("id") Long idPersonaje) {
		
		this.personajeServiceImpl.deletePersonajeById(idPersonaje);
		
		return ResponseEntity.ok().build();
	}
}
