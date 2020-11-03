package com.cards.fourofakind.test;

import com.cards.fourofakind.api.PlayerDeck;
import com.cards.fourofakind.impl.DeckImpl;
import com.cards.fourofakind.impl.HandImpl;
import com.cards.fourofakind.impl.PlayerImpl;
import com.cards.fourofakind.model.Card;
import com.cards.fourofakind.model.Pack;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


public class TestingSuite {

	@Test
	public void playerTests() {
		/*
			void start();
			void setNextPlayerDeck(PlayerDeck playerDeck);
			PlayerDeck getPlayerDeck();
		*/
		PlayerImpl tester = new PlayerImpl(1); // Redo this test
		PlayerDeck plDeck;
		// assert statements
		assertTrue("Player deck must return as the correct type",
		           tester.getPlayerDeck() instanceof PlayerDeck);
		//assertThat(tester.getPlayerDeck() instanceof PlayerDeck,
		//	"Player deck must be empty to start with");
	}

	@Test
	public void handTests() {
		/*
			void addCard(Card card);
			Card removeCard(Card preferredCard);
			Card[] getHand();
			void sortHand();
			boolean isWin();
		 */
		HandImpl tester = new HandImpl();
		DeckImpl deck = initDeck();
		assertNull(tester.getHand()[0], "Hand should be empty to begin with");
		initHand(tester, deck);
		assertEquals(1, tester.getHand()[0].getValue(), "First element should be" +
		                                                " equal to card with a value of 1");
		//add something for negative or out of bounds ?

	}

	private DeckImpl initDeck() {
		DeckImpl deck = new DeckImpl();
		for (int i = 0; i < 8; i++) {
			deck.addCard(new Card(i + 1));
		}
		return deck;
	}

	private HandImpl initHand(HandImpl hand, DeckImpl deck) {
		for (int i = 0; i < 4; i++) {
			hand.getHand()[i] = (deck.getCard());
		}
		hand.sortHand();
		return hand;
	}

	@Test
	public void deckTests() {
		/*
			public DeckImpl() {
				this.cards = new LinkedList<>();
			}
			public synchronized Card getCard() {
				return this.cards.poll();
			}
			@Override
			public synchronized void addCard(Card card) {
				this.cards.add(card);
			}
			@Override
			public boolean isEmpty() {
				return this.cards.isEmpty();
			}
		*/
		DeckImpl tester = initDeck();
		assertFalse(tester.isEmpty(), "Deck is not empty when initialised");
		for (int counter = 0; counter < 8; counter++) {
			tester.getCard();
		}
		assertTrue("Deck should be empty after subtracting all the cards", tester.isEmpty());
		tester.addCard(new Card(5));
		assertEquals(5, tester.getCard().getValue(), "First card's value = 5");
	}

	@Test
	public void cardTests() {
		/*
			public Card(int value) {
				this.value = value;
			}
			public int getValue() {
				return value;
			}
		*/
		Card tester = new Card(1);
		assertEquals(1, tester.getValue(), "Card value is equal to 1");
		//add something for negative or out of bounds ?

	}

	@Test
	public void packTests() {
		/*
			private void readPack(String filename);

		 */
		int numberPlayer = 4;
		Pack tester = new Pack("test.txt", numberPlayer);
		int testVal = numberPlayer * 8;
		assertTrue("Pack size should be equal to number of players * 8",
		           tester.getPack().length == testVal);
	}
}
