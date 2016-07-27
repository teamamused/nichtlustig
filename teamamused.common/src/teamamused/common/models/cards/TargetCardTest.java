package teamamused.common.models.cards;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.junit.Test;

import teamamused.common.interfaces.ITargetCard;

public class TargetCardTest {

	@Test
	public void test() {
		TargetCard tc1 = new TargetCard(GameCard.ZK_Lemming1, 1, 15, null);
		TargetCard tc2 = new TargetCard(GameCard.ZK_Lemming1, 1, 15, null);
		assertTrue(tc1.equals(tc2));
		
		ArrayList<ITargetCard> cards = new ArrayList<ITargetCard>();
		cards.add(tc1);
		assertTrue(cards.contains(tc2));
		
		Set<ITargetCard> cardset = new HashSet<ITargetCard>();
		cardset.add(tc1);
		assertTrue(cardset.contains(tc2));

		Hashtable<ITargetCard, Boolean> targetCards = new Hashtable<ITargetCard, Boolean>();
		targetCards.put(tc1, true);
		assertTrue(targetCards.containsKey(tc2));
		
	}

}
