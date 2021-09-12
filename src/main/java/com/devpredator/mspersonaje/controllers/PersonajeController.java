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

import com.devpredator.msentity.entity.Anime;
import com.devpredator.msentity.entity.Personaje;
import com.devpredator.mspersonaje.service.PersonajeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author DevPredator
 *
 */
@Api(tags = {"PERSONAJE'S MICROSERVICE"})
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
	
	@ApiOperation(value = "FIND PERSONAJES", notes = "Get the list of personajes", response = List.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Personajes founded successfully!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Personaje's forbidden!"),
		@ApiResponse(code = 404, message = "Personaje's resource not founded!")
	})
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
	
	@ApiOperation(value = "FIND PERSONAJE BY ID", notes = "Get an personaje by its id", response = Personaje.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Personaje founded successfully!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Personaje's forbidden!"),
		@ApiResponse(code = 404, message = "Personaje's resource not founded!")
	})
	@GetMapping("findPersonajeById/{id}")
	public ResponseEntity<?> findPersonajeById(
			@ApiParam(value = "The id of the personaje to find", name = "id", required = true, example = "Example: 10")
			@PathVariable("id") Long idPersonaje) {
		
		Optional<Personaje> personajeOptional = this.personajeServiceImpl.findPersonajeById(idPersonaje);
		
		if (!personajeOptional.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(personajeOptional.get());
	}
	
	@ApiOperation(value = "SAVE PERSONAJE", notes = "Store a new personaje in the database", response = Personaje.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Personaje request successfully!"),
		@ApiResponse(code = 201, message = "Personaje created successfully!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Personaje's forbidden!"),
		@ApiResponse(code = 404, message = "Personaje's resource not founded!")
	})
	@PostMapping("savePersonaje")
	public ResponseEntity<?> savePersonaje(
			@ApiParam(value = "The object of the personaje to store", name = "personaje", required = true) 
			@Valid @RequestBody Personaje personaje) {
		
		Personaje personajeSaved = this.personajeServiceImpl.savePersonaje(personaje);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(personajeSaved);
	}
	
	@ApiOperation(value = "UPDATE PERSONAJE", notes = "Update an personaje in the database", response = Personaje.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Personaje request successfully!"),
		@ApiResponse(code = 201, message = "Personaje updated successfully!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Personaje's forbidden!"),
		@ApiResponse(code = 404, message = "Personaje's resource not founded!")
	})
	@PutMapping("updatePersonaje")
	public ResponseEntity<?> updatePersonaje(
			@ApiParam(value = "The object of the personaje to update", name = "personaje", required = true) 
			@Valid @RequestBody Personaje personaje) {
		Personaje personajeUpdated = this.personajeServiceImpl.savePersonaje(personaje);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(personajeUpdated);
	}
	
	@ApiOperation(value = "DELETE PERSONAJE", notes = "Delete an personaje in the database", response = ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Personaje deleted successfully!"),
		@ApiResponse(code = 204, message = "No content for this personaje!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Personaje's forbidden!"),
		@ApiResponse(code = 404, message = "Personaje's resource not founded!")
	})
	@DeleteMapping("deletePersonajeById/{id}")
	public ResponseEntity<?> deletePersonajeById(
		@ApiParam(value = "The id of the personaje to delete", name = "id", required = true, example = "Example: 1")
		@PathVariable("id") Long idPersonaje) {
		
		this.personajeServiceImpl.deletePersonajeById(idPersonaje);
		
		return ResponseEntity.ok().build();
	}
}
