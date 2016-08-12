package teamamused.common.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Locale;

import teamamused.common.dtos.BeanPlayer;
import teamamused.common.dtos.BeanTargetCard;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.cards.CardFactory;
import teamamused.common.models.cards.GameCard;

/**
 * Spieler Klasse
 * 
 * @author Daniel
 *
 */
public class Player implements IPlayer, Serializable {

	/** Versionsnummer für die Serialisierung */
	private static final long serialVersionUID = 1;

	private transient Hashtable<GameCard, ISpecialCard> htSpecialCards;
	private transient Hashtable<GameCard, ITargetCard> htTargetCards;
	private transient Hashtable<GameCard, IDeadCard> htDeadCards;
	private transient Hashtable<GameCard, ITargetCard> htDeadOnTargetCards;

	private String playername;
	private boolean isConnected = true;
	private Locale local;
	private int playerNumber = 0;

	public Player() {
		super();
	}
	/**
	 * Instanziert einen neuen Spieler
	 * 
	 * @param playername
	 *            Benutzername
	 */
	public Player(String playername) {
		this();
		this.playername = playername;
	}

	/**
	 * Instanziert einen neuen Spieler
	 * 
	 * @param playername
	 *            Benutzername
	 * @param playerNumber
	 *            Spieler nummer
	 */
	public Player(String playername, int playerNumber) {
		this(playername);
		this.playerNumber = playerNumber;
	}

	/**
	 * Instanziert einen neuen Spieler
	 * 
	 * @param transportablePlayer
	 *            Player Bean Objekt
	 */
	public Player(BeanPlayer transportablePlayer) {
		this(transportablePlayer.playername, transportablePlayer.playerNumber);
		// Spielkarten
		htSpecialCards = new Hashtable<GameCard, ISpecialCard>();
		htTargetCards = new Hashtable<GameCard, ITargetCard>();
		htDeadCards = new Hashtable<GameCard, IDeadCard>();
		this.isConnected = transportablePlayer.connected;
		
		Hashtable<GameCard, IDeadCard> allDeadCards = CardFactory.getDeadCards();
		for (GameCard card : transportablePlayer.deadCards) {
			this.htDeadCards.put(card, allDeadCards.get(card));
		}
		Hashtable<GameCard, ISpecialCard> allSpecialCards = CardFactory.getSpecialCards();
		for (GameCard card : transportablePlayer.specialCards) {
			this.htSpecialCards.put(card, allSpecialCards.get(card));
		}
		Hashtable<GameCard, ITargetCard> allTargetCards = CardFactory.getTargetCards();

		for (BeanTargetCard card : transportablePlayer.targetCards) {
			ITargetCard tg = allTargetCards.get(card.gamecard);
			tg.setIsCoveredByDead(card.isCoveredByDead);
			tg.setIsValuated(card.isValuated);
			this.htTargetCards.put(card.gamecard, tg);
		}
	}
	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.IPlayer#initForGame(int number)
	 */
	@Override
	public void initForGame(int number) {
		this.playerNumber = number;
		this.htSpecialCards = new Hashtable<GameCard, ISpecialCard>();
		this.htTargetCards = new Hashtable<GameCard, ITargetCard>();
		this.htDeadCards = new Hashtable<GameCard, IDeadCard>();
		this.htDeadOnTargetCards = new Hashtable<GameCard, ITargetCard>();
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#getSpecialCards()
	 */
	@Override
	public ISpecialCard[] getSpecialCards() {
		ISpecialCard[] retval = new ISpecialCard[0];
		if (this.htSpecialCards.values() != null) {
			retval = Arrays.copyOf(this.htSpecialCards.values().toArray(), this.htSpecialCards.values().size(),
					ISpecialCard[].class);
			Arrays.sort(retval);
		}
		return retval;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#getDeadCards()
	 */
	@Override
	public IDeadCard[] getDeadCards() {
		IDeadCard[] retval = new IDeadCard[0];
		if (this.htDeadCards.values() != null) {
			retval = Arrays.copyOf(this.htDeadCards.values().toArray(), this.htDeadCards.values().size(),
					IDeadCard[].class);
			Arrays.sort(retval);
		}
		return retval;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#getTargetCards()
	 */
	@Override
	public ITargetCard[] getTargetCards() {
		ITargetCard[] retval = new ITargetCard[0];
		if (this.htTargetCards.values() != null) {
			retval = Arrays.copyOf(this.htTargetCards.values().toArray(), this.htTargetCards.values().size(),
					ITargetCard[].class);
			Arrays.sort(retval);
		}
		return retval;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#removeSpecialCard(ISpecialCard
	 *      specialCard)
	 */
	public ISpecialCard removeSpecialCard(ISpecialCard specialCard) {
		return this.htSpecialCards.remove(specialCard.getGameCard());
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#addSpecialCard(ISpecialCard
	 *      specialCard)
	 */
	public void addSpecialCard(ISpecialCard specialCard) {
		this.htSpecialCards.put(specialCard.getGameCard(), specialCard);
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#removeTargetCard(ITargetCard
	 *      targetCard)
	 */
	public ITargetCard removeTargetCard(ITargetCard targetCard) {
		if (this.htTargetCards.get(targetCard.getGameCard()).getIsValuated()) {
			throw new UnsupportedOperationException(
					"Die Zielkarte wurde bereits gewertet und kann dem Spieler nicht mehr entwendet werden.");
		}
		return this.htTargetCards.remove(targetCard.getGameCard());
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#addTargetCard(ITargetCard
	 *      targetCard)
	 */
	public void addTargetCard(ITargetCard targetCard) {
		this.htTargetCards.put(targetCard.getGameCard(), targetCard);
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#removeDeadCard(IDeadCard
	 *      deadCard)
	 */
	public IDeadCard removeDeadCard(IDeadCard deadCard) {
		if (this.htDeadOnTargetCards.containsKey(deadCard.getGameCard())) {
			ITargetCard toRelease = this.htDeadOnTargetCards.get(deadCard.getGameCard());
			this.htTargetCards.get(toRelease.getGameCard()).setIsCoveredByDead(false);
			this.htDeadOnTargetCards.remove(deadCard.getGameCard());
		}
		return this.htDeadCards.remove(deadCard.getGameCard());
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICardHolder#addDeadCard(IDeadCard
	 *      deadCard, ITargetCard targetCard)
	 */
	public void addDeadCard(IDeadCard deadCard, ITargetCard targetCard) {
		this.htDeadCards.put(deadCard.getGameCard(), deadCard);
		if (deadCard != null && targetCard != null) {
			this.htDeadOnTargetCards.put(deadCard.getGameCard(), targetCard);
		}
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.IPlayer#getTargetCardUnderDeadCard(IDeadCard
	 *      deadCard)
	 */
	@Override
	public ITargetCard getTargetCardUnderDeadCard(IDeadCard deadCard) {
		return this.htDeadOnTargetCards.get(deadCard.getGameCard());
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.IPlayer#getPlayerName()
	 */
	@Override
	public String getPlayerName() {
		return this.playername;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.IPlayer#getPlayerNumber()
	 */
	@Override
	public int getPlayerNumber() {
		return this.playerNumber;
	}


	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.IPlayer#getLocal()
	 */
	@Override
	public Locale getLocal() {
		return local;
	}
	
	/**
	 * Implementierung von:
	 * @see teamamused.common.interfaces.IPlayer#setLocal(Locale local)
	 */
	@Override
	public void setLocal(Locale local) {
		this.local = local;
	}

	/**
	 * Implementierung von:
	 * @see teamamused.common.interfaces.IPlayer#getConnected()
	 */
	@Override
	public boolean getConnected() {
		return this.isConnected;
	}

	/**
	 * Implementierung von:
	 * @see teamamused.common.interfaces.IPlayer#setConnected(boolean connected)
	 */
	@Override
	public void setConnected(boolean connected) {
		this.isConnected = connected;
	}
	
	@Override
	public String toString() {
		return this.playername;
	}

	/**
	 * Vergleiche via Username, da dieser Unique sein muss
	 */
	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
	
}
