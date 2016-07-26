/**
 * 
 */
package teamamused.common.models;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.BeforeClass;
import org.junit.Test;

import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.cards.CardFactory;
import teamamused.common.models.cards.GameCard;

/**
 * JUnit Tests zur Grundfunktionalität der Spieler 
 * @author Daniel
 */
public class PlayerTest {
	
	private static final String PLAYER_NAME = "Testplayer";
	private static final int PLAYER_NUMBER = 1;
	private static Player player;

	/**
	 * @throws java.lang.Exception J Unit handelt dies
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		player = new Player(PLAYER_NAME);
		player.initForGame(PLAYER_NUMBER);
	}

	/**
	 * Test method for {@link teamamused.common.models.Player#getSpecialCards()}.
	 */
	@Test
	public void testGetSpecialCards() {
		Hashtable<GameCard, ISpecialCard> allSpecialCards = CardFactory.getSpecialCards();
		ISpecialCard[] sps = player.getSpecialCards();
		// Check 1: Spezialkarten dürfen nicht NULL sein
		assertNotNull(sps);
		// Check 2: müssen initial 0 Stück sein
		assertTrue(sps.length == 0);
		// Check 3: müssen hinzugefügt werden können
		player.addSpecialCard(allSpecialCards.get(GameCard.SK_RoboterNF700));
		assertTrue(player.getSpecialCards().length == 1);
		// Check 4: müssen entfernt werden können
		player.removeSpecialCard(allSpecialCards.get(GameCard.SK_RoboterNF700));
		assertTrue(player.getSpecialCards().length == 0);
	}

	/**
	 * Test method for {@link teamamused.common.models.Player#getDeadCards()}.
	 */
	@Test
	public void testGetDeadCards() {
		Hashtable<GameCard, IDeadCard> allDeadCards = CardFactory.getDeadCards();
		IDeadCard[] dcs = player.getDeadCards();
		// Check 1: Todeskarten dürfen nicht NULL sein
		assertNotNull(dcs);
		// Check 2: müssen initial 0 Stück sein
		assertTrue(dcs.length == 0);
		// Check 3: müssen hinzugefügt werden können
		player.addDeadCard(allDeadCards.get(GameCard.Tod_Eins), null);
		assertTrue(player.getDeadCards().length == 1);
		// Check 4: müssen entfernt werden können
		player.removeDeadCard(allDeadCards.get(GameCard.Tod_Eins));
		assertTrue(player.getDeadCards().length == 0);
	}

	/**
	 * Test method for {@link teamamused.common.models.Player#getTargetCards()}.
	 */
	@Test
	public void testGetTargetCards() {
		Hashtable<GameCard, ITargetCard> allTargetCards = CardFactory.getTargetCards();
		ITargetCard[] tcs = player.getTargetCards();
		// Check 1: Zielkarten dürfen nicht NULL sein
		assertNotNull(tcs);
		// Check 2: müssen initial 0 Stück sein
		assertTrue(tcs.length == 0);
		// Check 3: müssen hinzugefügt werden können
		player.addTargetCard(allTargetCards.get(GameCard.ZK_Dinosaurier3));
		assertTrue(player.getTargetCards().length == 1);
		// Check 4: dürfen entfernt werden wenn noch nicht gwertet
		player.removeTargetCard(allTargetCards.get(GameCard.ZK_Dinosaurier3));
		assertTrue(player.getTargetCards().length == 0); 
		// Check 5: dürfen nicht mehr entfernt werden können wenn sie gewertet wurden
		allTargetCards.get(GameCard.ZK_Dinosaurier3).setIsValuated(true);
		player.addTargetCard(allTargetCards.get(GameCard.ZK_Dinosaurier3));
		assertTrue(player.getTargetCards().length == 1); 
		try {
			player.removeTargetCard(allTargetCards.get(GameCard.ZK_Dinosaurier3));
			fail("Zielkarten dürfen dem Spieler nicht mehr entfernt werden");
		} catch (Exception ex) {
			assertTrue(player.getTargetCards().length == 1); 
			System.out.println("PlayerTest: removeTargetCard erwartete Exception: " + ex.toString());
		}
	}

	/**
	 * Test method for {@link teamamused.common.models.Player#getPlayerName()}.
	 */
	@Test
	public void testGetPlayerName() {
		assertEquals(player.getPlayerName(), PLAYER_NAME);
	}

	/**
	 * Test method for {@link teamamused.common.models.Player#getPlayerNumber()}.
	 */
	@Test
	public void testGetPlayerNumber() {
		assertEquals(player.getPlayerNumber(), PLAYER_NUMBER);
	}

	/**
	 * Test method for {@link teamamused.common.models.Player#initForGame(int)}.
	 */
	@Test
	public void testSetPlayerNumber() {
		player.initForGame(PLAYER_NUMBER + 1);
		assertEquals(player.getPlayerNumber(), PLAYER_NUMBER + 1);
		player.initForGame(PLAYER_NUMBER);
	}

}
