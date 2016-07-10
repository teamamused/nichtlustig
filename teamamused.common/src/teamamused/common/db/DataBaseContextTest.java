package teamamused.common.db;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IDataBaseContext;

/**
 * Test Klasse für die Datenverwaltung
 * 
 * @author Daniel
 *
 */
public class DataBaseContextTest {

	@Before
	public void setUpBeforeTest() throws Exception {
		// Vor jedem Test eine neue saubere InMemory DB setzen
		InMemoryDataBaseContext iMDB = new InMemoryDataBaseContext();
		iMDB.loadContext();
		ServiceLocator.getInstance().setDBContext(iMDB);
	}

	@Test
	public void testPlayer() {
		// Benutzer erstellen
		try {
			assertNotNull("Benutzer erstellen", PlayerRepository.createPlayer("Daniel", "geheim"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Benutzer erstellen" +e.toString());
		}
		// Wenn ein zweiter User mit gleichem Login erstellt wird muss eine Exception geworfen werden
		try {
			assertNull("Benutzer erneut erstellen", PlayerRepository.createPlayer("Daniel", "geheim"));
			fail("Benutzer erneut erstellen");
		} catch (Exception e) {
			assertTrue("Benutzer erneut erstellen", true);
		}
		// Login Testen
		assertNull("Login mit falschem Passwort", PlayerRepository.validatePlayerLogin("Daniel", "wrongpassword"));
		assertNotNull("Login mit richtigem Passwort", PlayerRepository.validatePlayerLogin("Daniel", "geheim"));
		ServiceLocator.getInstance().getDBContext().saveContext();
	}

	@Test
	public void testGame() {
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		// Benutzer anlegen lassen
		DataBaseHelper.createDemoData(true, false, false);
		// GameInfo 1 erstellen
		try {
			assertTrue("GameInfo erstellen", db.addGame(new GameInfo(1, LocalDateTime.now().minusMinutes(25), LocalDateTime.now(), "Sandra", "Daniel")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("GameInfo erstellen" +e.toString());
		}
		// GameInfo 1 zum zweiten mal erstellen
		/*try {
			assertFalse("GameInfo erneut erstellen", db.addGame(new GameInfo(1, LocalDateTime.now().minusMinutes(25), LocalDateTime.now(), "Maja", "Michelle")));
			fail("GameInfo erneut erstellen");
		} catch (Exception e) {
			assertTrue("GameInfo erneut erstellen", true);
		}*/
		// GameInfo 2 erstellen
		try {
			assertTrue("GameInfo 2 erstellen", db.addGame(new GameInfo(2, LocalDateTime.now().minusMinutes(25), LocalDateTime.now(), "Maja", "Michelle")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("GameInfo 2 erstellen" +e.toString());
		}
		// Nächste GameId auslesen
		assertEquals("Nächste GameId auslesen", 3, GameInfoRepository.getNextGameId());
		
	}

}
