package com.cards.fourofakind.test;

import com.cards.fourofakind.CardGame;
import com.cards.fourofakind.api.PlayerDeck;
import com.cards.fourofakind.exception.IllegalFileException;
import com.cards.fourofakind.exception.NullCardException;
import com.cards.fourofakind.implementation.DeckImpl;
import com.cards.fourofakind.implementation.HandImpl;
import com.cards.fourofakind.implementation.PlayerImpl;
import com.cards.fourofakind.model.Card;
import com.cards.fourofakind.model.Pack;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


/**
 * The type Testing suite.
 */
public class TestingSuite {
	String filenameTest = "test.txt";
	@Test
	public void testCardGame() throws Exception{
		CardGame gameTest = new CardGame(2,filenameTest);
		Method method = CardGame.class.getDeclaredMethod("createPlayers", int.class);
		method.setAccessible(true);
		method.invoke(gameTest,2);
		Method dealMethod = CardGame.class.getDeclaredMethod("dealPack", String.class, int.class);
		dealMethod.setAccessible(true);
		Object[] args = new Object[2];
		args[0] = filenameTest;
		args[1] = 2;
		dealMethod.invoke(gameTest, args);

	}

	@Test
	public void testPrivateMethod() throws Exception {
		PlayerImpl p = new PlayerImpl(1);
		Method method = PlayerImpl.class.getDeclaredMethod("writeToFile",
		                                                   java.io.BufferedWriter.class, java.lang.String.class);
		method.setAccessible(true);
		FileWriter file = new FileWriter(filenameTest);
		BufferedWriter bw = new BufferedWriter(file);
		Object[] args =new Object[2];
		args[0] = bw;
		args[1] = "1";
		for (int i = 0; i < 16; i++) {
			args[1] = "" + (i % 4 + 1);
			method.invoke(p,args);
		}
		bw.close();
		//Do your assertions
	}
	@Test
	public void readPackTest() throws Exception{
		Pack p = new Pack(filenameTest,2);
		Method method = Pack.class.getDeclaredMethod("readPack", String.class);
		method.setAccessible(true);
		method.invoke(p,filenameTest);
	}

	@Test
	public void writeWinTest() throws Exception{
		Pack p = new Pack(filenameTest,2);
		Method methoda = Pack.class.getDeclaredMethod("readPack", String.class);
		methoda.setAccessible(true);
		methoda.invoke(p,filenameTest);

		PlayerImpl player = new PlayerImpl(1);
		player.initHand();
		Method method = PlayerImpl.class.getDeclaredMethod("writeWinningStatement",
		                                                   java.io.BufferedWriter.class);
		method.setAccessible(true);
		FileWriter file = new FileWriter(filenameTest);
		BufferedWriter bw = new BufferedWriter(file);
		method.invoke(player, bw);
	}


	/**
	 * Player tests.
	 */
	@Test
	public void playerTests() {
		PlayerImpl tester = new PlayerImpl(1); // Redo this test
		PlayerDeck plDeck;
		DeckImpl testDeck = (DeckImpl) tester.getDeck();
		tester.setNextPlayerId(1);
		//assertEquals(1, tester.)
		assertTrue("Deck is empty",testDeck.isEmpty());
		for (int counter = 0; counter < 8; counter++) {
			tester.getDeck().addCard(new Card((counter+1)%4));
		}
		tester.initHand();
		DeckImpl deckTest = (DeckImpl) tester.getDeck();
		//assertEquals(1,deckTest[0].getValue(),"Value should be 1");
		//tester.start();
	}

	/**
	 * Hand tests.
	 */
	@Test
	public void handTests() throws NullCardException {
		HandImpl tester = new HandImpl();
		DeckImpl deck = initDeck();
		assertNull(tester.getHand()[0], "Hand should be empty to begin with");

		initHand(tester, deck);
		assertFalse(tester.isWin(),"Hand is not a winning hand");


		tester.updateHandTimestamp();
		assertEquals(1,tester.getHand()[0].getTimestamp(),"Updated time should be 1");

		assertEquals(1, tester.getHand()[0].getValue(), "First element should be" +
		                                                " equal to card with a value of 1");

		assertEquals("1 2 3 4 ", tester.showHand(), "Deck string must be = to: 1 2 3 4 ");
		//add something for negative or out of bounds ?
		tester.addCard(new Card(5));
		tester.sortHand();
		assertEquals(5,tester.getHand()[3].getValue(),"Last card should be 5 after adding");
		assertEquals(2,tester.removeCard(new Card(1)).getValue(),
		             "Deleted card should be 2");

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

	/**
	 * Deck tests.
	 */
	@Test
	public void deckTests() {
		DeckImpl tester = initDeck();
		assertFalse(tester.isEmpty(), "Deck is not empty when initialised");
		for (int counter = 0; counter < 8; counter++) {
			tester.getCard();
		}
		assertTrue("Deck should be empty after subtracting all the cards", tester.isEmpty());
		tester.addCard(new Card(5));
		assertEquals(5, tester.getCard().getValue(), "First card's value = 5");
		tester.addCard(new Card(10));
		assertEquals("10 ", tester.showDeck(), "Deck string must be = to: 10 ");
	}

	/**
	 * Card tests.
	 */
	@Test
	public void cardTests() {
		Card tester = new Card(1);

		assertEquals(1, tester.getValue(), "Card value is equal to 1");

		assertEquals(0,tester.getTimestamp(), "Time stamp must be 0");

		tester.updateTimestamp();
		assertEquals(1,tester.getTimestamp(),"Timestamp must be 1 after first update");

		tester.resetTimestamp();
		assertEquals(0,tester.getTimestamp(), "Timestamp must be 0 after reset");
		//add something for negative or out of bounds ? for that exception

	}

	/**
	 * Pack tests.
	 */
	@Test
	public void packTests() throws IllegalFileException {
		//make a read test
		int numberPlayer = 2;
		Pack tester = new Pack(filenameTest, numberPlayer);
		int testVal = numberPlayer * 8;
		assertTrue("Pack size should be equal to number of players * 8",
		           tester.getPack().length == testVal);
	}
}

