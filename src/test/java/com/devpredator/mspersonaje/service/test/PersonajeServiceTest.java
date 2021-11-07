/**
 * 
 */
package com.devpredator.mspersonaje.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.devpredator.msentity.entity.Anime;
import com.devpredator.msentity.entity.Personaje;
import com.devpredator.mspersonaje.exceptions.PersonajeNullException;
import com.devpredator.mspersonaje.repository.PersonajeRepository;
import com.devpredator.mspersonaje.service.PersonajeService;
import com.devpredator.mspersonaje.service.impl.PersonajeServiceImpl;

/**
 * @author DevPredator
 * @since v1.0 01/10/2021
 * @version v1.0 01/10/2021
 * 
 *          Unit test for Customers.
 */
@SpringBootTest
@ActiveProfiles(value = {"test"})
@ExtendWith(MockitoExtension.class)
class PersonajeServiceTest {

	@Mock
	private PersonajeRepository personajeRepository;
	
	@InjectMocks
	private PersonajeServiceImpl personajeService;
	
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
			//::::::::::::::::::::: GIVEN :::::::::::::::::::::::::::::.
			Anime animeDigimon = new Anime(1L, "Digimon", 2021, LocalDateTime.now());
			Anime animeDB = new Anime(2L, "Dragon Ball", 1986, LocalDateTime.now());
			
			Iterable<Personaje> personajesIterable = List.of(
				new Personaje(1L, "Agumón", animeDigimon),
				new Personaje(2L, "Goku", animeDB)
			);
			
			//::::::::::::::::::::: WHEN ::::::::::::::::::::::::::::::
			
			when(personajeRepository.findAll()).thenReturn(personajesIterable);
			
			//::::::::::::::::::::: THEN ::::::::::::::::::::::::::::::
			List<Personaje> personajes = personajeService.findPersonajes();
			
			assertNotNull(personajes);
			assertTrue(personajes.size() > 0);
			assertTrue(personajes.size() == 2);
			
			verify(personajeRepository).findAll();
		}

		@DisplayName(value = "FIND CHARACTER BY ID")
		@Test
		void testFindCharacterById() {

			//::::::::::::::::::::: GIVEN :::::::::::::::::::::::::
			
			Optional<Personaje> personajeOptional = Optional.of(
					new Personaje(1L, "Kabuterimon", new Anime(1L, "Digimon", 1999, LocalDateTime.now()))
			);
			
			//::::::::::::::::::::: WHEN ::::::::::::::::::::::::::
			
			when(personajeRepository.findById(any())).thenReturn(personajeOptional);

			//::::::::::::::::::::: THEN ::::::::::::::::::::::::::
			Optional<Personaje> personajeFounded = personajeService.findPersonajeById(1L);
			
			assertTrue(!personajeFounded.isEmpty());
			assertEquals("Kabuterimon", personajeFounded.get().getNombre());
			assertEquals("Digimon", personajeFounded.get().getAnime().getNombre());
			
			verify(personajeRepository).findById(any());
		}
	}

	@DisplayName(value = "NESTED CLASS SAVE CHARACTERS")
	@Nested
	class SaveCharacterNested {
		
		@DisplayName(value = "STORE CHARACTER IS OK")
		@Test
		void testSaveCharacterIsOk() {
			
			//::::::::::::::::::::::::: GIVEN :::::::::::::::::::::::::::::
			
			Anime anime = new Anime();
			anime.setId(25L);
			anime.setNombre("Demon Slayer");
			anime.setAnio(2019);
			
			LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 14, 12, 12, 12);
			anime.setFechaCreacion(localDateTime);
			
			Personaje personaje = new Personaje();
			personaje.setNombre("Nesuko Kamado");
			personaje.setAnime(anime);
			
			//:::::::::::::::::::::::::: WHEN ::::::::::::::::::::::::::::::
			when(personajeRepository.save(any(Personaje.class))).thenReturn(personaje);
			
			//:::::::::::::::::::::::::: THEN ::::::::::::::::::::::::::::::
			Personaje personajeStored = personajeService.savePersonaje(personaje);
			
			assertNotNull(personajeStored);
			assertEquals("Nesuko Kamado", personajeStored.getNombre());
			assertEquals("Demon Slayer", personajeStored.getAnime().getNombre());
			
			verify(personajeRepository).save(any(Personaje.class));
			
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
				
		// :::::::::::::::::::::: GIVEN ::::::::::::::::::::::::::::::..
		personajeService.deletePersonajeById(30L);
		
		//::::::::::::::::::::::: WHEN :::::::::::::::::::::::::::::::
		when(personajeRepository.findById(anyLong())).thenReturn(null);
		
		//::::::::::::::::::::::: THEN :::::::::::::::::::::::::::::::
		Optional<Personaje> personajeNotFounded = personajeService.findPersonajeById(30L);
		
		assertNull(personajeNotFounded);
		
		verify(personajeRepository).findById(anyLong());
	}

	@AfterEach
	void afterEach() {

	}

	@AfterAll
	static void afterAll() {

	}

}
