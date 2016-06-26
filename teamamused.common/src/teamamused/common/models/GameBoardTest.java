package teamamused.common.models;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.BeforeClass;
import org.junit.Test;

import teamamused.common.interfaces.*;
import teamamused.common.models.cubes.CubeColor;

/**
 * JUnit Tests zur Grundfunktionalität der Spielfeldes 
 * @author Daniel
 */
public class GameBoardTest {

	private static GameBoard board;
	/**
	 * Setup and TearDown
	 *	• Setup-methods: executed before the tests, to prepare data, etc.
	 *	– @BeforeClass methods are executed once, before all tests
	 *	– @Before methods are executed before each test
	 *	• TearDown methods: executed after the tests, to clean things up
	 *	– @AfterClass methods are executed once, after all tests
	 *	– @After methods are executed after each test
	 *	• You can have as many methods of each type as you like
	 *	– Warning: you cannot control the order in which they execute
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		board = new GameBoard();
	}
	
	/**
	 * Assert methods for test cases are in org.junit.Assert
	 * – The import statement: import static org.junit.Assert.*
	 * – assertEquals(Object expected, Object actual)
	 * – assertFalse(boolean condition)
	 * – assertTrue(boolean condition)
	 * – assertNotNull(Object object)
	 * – and many more...
	 * 
	 * Test method for {@link teamamused.common.models.GameBoard#getSpecialCards()}.
	 */
	@Test
	public void testSpecialCards() {
		ISpecialCard[] sps = board.getSpecialCards();
		// Check 1: sind Spezialkarten vorhanden
		assertNotNull(sps);
		// Check 2: sind es 6 Stück
		assertTrue(sps.length == 6); 
		// Check 3: kann eine Karte entfernt werden, dann sollten es nur noch 5 Stück sein
		ISpecialCard removedOne = board.removeSpecialCard(sps[0]);
		assertTrue(board.getSpecialCards().length == 5); 
		// Check 4: Die Karte wird wieder hinzugefügt und dan sollten wieder 6 Karten auf dem Tisch liegen
		board.addSpecialCard(removedOne);
		assertTrue(board.getSpecialCards().length == 6); 
	}

	/**
	 * Exceptions: 
	 * jUnit can handle exceptions for you, but it is easy to do it yourself
	 * – Just use try/catch, and tell jUnit if you excepted what happened
	 * Note: execution will stop as soon as we get any exception
	 * – Hence: we cannot have multiple tests in a single method
	 * – There are solutions, we'll get to that later...
	 * 
	 * Test method for {@link teamamused.common.models.GameBoard#getDeadCards()}.
	 */
	@Test
	public void testDeadCards() {
		IDeadCard[] dcs = board.getDeadCards();
		// Check 1: sind Todeskarten vorhanden
		assertNotNull(dcs);
		// Check 2: sind es 6 Stück
		assertTrue(dcs.length == 6); 
		// Check 3: kann eine Karte entfernt werden, dann sollten es nur noch 5 Stück sein
		IDeadCard removedOne = board.removeDeadCard(dcs[0]);
		assertTrue(board.getDeadCards().length == 5); 
		// Check 4: Die Karte wird wieder hinzugefügt
		// -> muss Exception geben, Todeskarten dürfen nicht mehr hinzugefügt werden!
		try {
			board.addDeadCard(removedOne, null);
			fail("Todeskarten dürfen dem Spieltischt nicht mehr hinzugefügt werden!");
		} catch(Exception ex) {
			assertTrue(board.getDeadCards().length == 5); 
			System.out.println("GameBoardTest: AddDeadCard erwartete Exception: " + ex.toString());
		}
	}

	/**
	 * Test method for {@link teamamused.common.models.GameBoard#getTargetCards()}.
	 */
	@Test
	public void testTargetCards() {
		ITargetCard[] tcs = board.getTargetCards();
		// Check 1: sind Zielkarten vorhanden
		assertNotNull(tcs);
		// Check 2: sind es 25 Stück
		assertTrue(tcs.length == 25); 
		// Check 3: kann eine Karte entfernt werden, dann sollten es nur noch 24 Stück sein
		ITargetCard removedOne = board.removeTargetCard(tcs[0]);
		assertTrue(board.getTargetCards().length == 24); 
		// Check 4: Die Karte wird wieder hinzugefügt
		// -> muss Exception geben, Zielkarten dürfen nicht mehr hinzugefügt werden!
		try {
			board.addTargetCard(removedOne);
			fail("Zielkarten dürfen dem Spieltischt nicht mehr hinzugefügt werden!");
		} catch(Exception ex) {
			assertTrue(board.getTargetCards().length == 24); 
			System.out.println("GameBoardTest: addTargetCard erwartete Exception: " + ex.toString());
		}
	}

	/**
	 * Test method for {@link teamamused.common.models.GameBoard#getCubes()}.
	 */
	@Test
	public void testGetCubes() {
		ICube[] cubes = board.getCubes();
		// Check 1: sind Würfel vorhanden
		assertNotNull(cubes);
		// Check 2: sind es 7 Stück
		assertTrue(cubes.length == 7); 
		// Checks der einzelnen Würfel
		Hashtable<CubeColor, Integer> htCubesByColor = new Hashtable<CubeColor, Integer>();
		for(ICube cube : cubes) {
			int count = ((int)htCubesByColor.getOrDefault(cube.getCubeColor(), 0))+1;
			htCubesByColor.put(cube.getCubeColor(), count);
			// Tests für den TodesWürfel
			if (cube.getCubeColor() == CubeColor.Pink) { 
				// Check: Todeswürfel darf keine Spezialkarte haben
				assertNull(cube.getSpecialCard());
			} else {
				// Check: Spezialkarte zugeteilt
				assertNotNull(cube.getSpecialCard());				
			}
		}
		// Check 3: 2 Rote würfel, 2 weisse, 2 scharze und 1 pinker
		assertTrue(htCubesByColor.getOrDefault(CubeColor.Black, 0) == 2);
		assertTrue(htCubesByColor.getOrDefault(CubeColor.Red, 0) == 2);
		assertTrue(htCubesByColor.getOrDefault(CubeColor.White, 0) == 2);
		assertTrue(htCubesByColor.getOrDefault(CubeColor.Pink, 0) == 1);
	}

}
