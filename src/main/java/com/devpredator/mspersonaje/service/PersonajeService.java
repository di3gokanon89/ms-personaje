/**
 * 
 */
package com.devpredator.mspersonaje.service;

import java.util.List;
import java.util.Optional;

import com.devpredator.msentity.entity.Personaje;

/**
 * @author DevPredator
 *
 */
public interface PersonajeService {
	/**
	 * Get the list of personaje.
	 * @return {@link Personaje} personaje founded.
	 */
	List<Personaje> findPersonajes();
	/**
	 * Get an personaje by its id.
	 * @return {@link Optional} the personaje founded.
	 */
	Optional<Personaje> findPersonajeById(Long id);
	/**
	 * Create or update an personaje in the database.
	 * @param anime {@link Personaje} personaje to create or update.
	 * @return {@link Personaje} personaje created or updated.
	 */
	Personaje savePersonaje(Personaje personaje);
	/**
	 * Delete an personaje by its id.
	 * @param id {@link Long} personaje's id to delete. 
	 */
	void deletePersonajeById(Long id);
}
