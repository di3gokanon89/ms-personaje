/**
 * 
 */
package com.devpredator.mspersonaje.service.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devpredator.msentity.entity.Anime;
import com.devpredator.msentity.entity.Personaje;
import com.devpredator.mspersonaje.exceptions.PersonajeNullException;
import com.devpredator.mspersonaje.service.PersonajeService;

/**
 * @author DevPredator
 * @since v1.0 01/10/2021
 * @version v1.0 01/10/2021
 * 
 *          Unit test for Customers.
 */
@SpringBootTest
class PersonajeServiceTest {

	@Autowired
	private PersonajeService personajeService;
	
	private TestReporter testReporter;
	
	private TestInfo testInfo;
	
	@BeforeAll
	static void beforeAll() {

	}

	@BeforeEach
	void beforeEach(TestReporter testReporter, TestInfo testInfo) {
		this.testReporter = testReporter;
		this.testInfo = testInfo;
		
		testReporter.publishEntry("DISPLAY NAME: " + testInfo.getDisplayName());
		testReporter.publishEntry("TEST METHOD: " + testInfo.getTestMethod());
	}

	@DisplayName(value = "NESTED CLASS FIND CHARACTERS")
	@Nested
	class FindCharactersNested {

		@DisplayName(value = "FIND ALL CHARACTERS")
		@Test
		void testFindCharacters() {
			
			List<Personaje> personajes = personajeService.findPersonajes();
			
			assertNotNull(personajes);
			assertTrue(personajes.size() > 0);
		}

		@DisplayName(value = "FIND CHARACTER BY ID")
		@Test
		void testFindCharacterById() {

			Optional<Personaje> personajeFounded = personajeService.findPersonajeById(1L);
			
			assertTrue(!personajeFounded.isEmpty());
			assertEquals("Goku", personajeFounded.get().getNombre());
			assertEquals("Dragon Ball Z", personajeFounded.get().getAnime().getNombre());
		}
	}

	@DisplayName(value = "NESTED CLASS SAVE CHARACTERS")
	@Nested
	class SaveCharacterNested {
		
		@DisplayName(value = "STORE CHARACTER IS OK")
		@Test
		void testSaveCharacterIsOk() {
			
			Anime anime = new Anime();
			anime.setId(25L);
			anime.setNombre("Hellsing");
			anime.setAnio(2001);
			
			LocalDateTime localDateTime = LocalDateTime.of(2021, 2, 20, 02, 24, 44);
			anime.setFechaCreacion(localDateTime);
			
			Personaje personaje = new Personaje();
			personaje.setNombre("Victoria");
			personaje.setAnime(anime);
			
			Personaje personajeStored = personajeService.savePersonaje(personaje);
			
			assertNotNull(personajeStored);
			assertEquals("Victoria", personajeStored.getNombre());
			assertEquals("Hellsing", personajeStored.getAnime().getNombre());
			
		}

		@DisplayName(value = "STORE CHARACTER IS NULL")
		@Test
		void testSaveCharacterIsNull() {
			
			PersonajeNullException personajeNullException = 
					assertThrows(PersonajeNullException.class, () -> personajeService.savePersonaje(null));
			
			assertEquals("The character is null", personajeNullException.getMessage());
		}
	}

	@DisplayName(value = "DELETE CHARACTER BY ID")
	@Test
	void testDeleteCharacter() {
		personajeService.deletePersonajeById(10L);
		
		Optional<Personaje> personajeNotFounded = personajeService.findPersonajeById(5L);
		
		assertTrue(personajeNotFounded.isEmpty());
	}

	@AfterEach
	void afterEach() {

	}

	@AfterAll
	static void afterAll() {

	}

}
