import com.cards.fourofakind.exception.IllegalFileException;
import com.cards.fourofakind.exception.IllegalFileInputException;
import com.cards.fourofakind.exception.NullCardException;
import com.cards.fourofakind.implementation.DeckImpl;
import com.cards.fourofakind.implementation.HandImpl;
import com.cards.fourofakind.implementation.PlayerImpl;
import com.cards.fourofakind.model.Card;
import com.cards.fourofakind.model.Pack;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


/**
 * The testing suite for the card game
 * Tests all non-interface classes
 */
class TestingSuite {
	/**
	 * The identifier for a file used in testing
	 */
	final String filenameTest = "test.txt";

	/**
	 * Runs all methods apart from the main() function and
	 * the test will fail if any of the methods don't work.
	 *
	 * The first test checks if the file exist after attempting to read
	 * from it.
	 *
	 * The other functions test the inner functionality of this class
	 * by testing every other function.
	 *
	 * @throws Exception the exception
	 */

	//PRIVATE METHOD TESTS USING REFLECT API
	@Test
	void testCardGame() throws Exception{
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
		File file = new File(filenameTest);
		assertTrue("File must exist",file.exists());
		CardGame gameTest2 = new CardGame(2,filenameTest);
		Method runMethod = CardGame.class.getDeclaredMethod("start");
		runMethod.setAccessible(true);
		runMethod.invoke(gameTest2);
	}

	/**
	 * This tests the writeToFile function in the PlayerImpl class
	 * to see if it writes the correct card values to the file given
	 *
	 * The first test checks to see if the file exists after the
	 * writer has attempted to make it.
	 *
	 * The second test checks to see if the file is readable so
	 * that the pack will be able to be made from the file.
	 * @throws Exception the exception
	 */
	@Test
	void writeFileTest() throws Exception {
		PlayerImpl p = new PlayerImpl(1);
		Method method = PlayerImpl.class.getDeclaredMethod("writeToFile",
		                                                   java.io.BufferedWriter.class, java.lang.String.class);
		method.setAccessible(true);
		FileWriter file = new FileWriter(filenameTest);
		BufferedWriter bw = new BufferedWriter(file);
		Object[] args =new Object[2];
		args[0] = bw;
		for (int i = 0; i < 16; i++) {
			if(i < 8) {
				args[1] = "" + (i % 2 + 1);
			}else{
				args[1] = "" + (i % 2 + 3);
			}
			method.invoke(p,args);
		}
		bw.close();
		File testFile = new File(filenameTest);
		assertTrue("File should exist",testFile.exists());
		assertTrue("File should be readable",testFile.canRead());
	}

	/**
	 * Reads from the file given to the Pack object and checks
	 * to see if it has read the correct data.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void readPackTest() throws Exception{
		Pack p = new Pack(filenameTest,2);
		Method method = Pack.class.getDeclaredMethod("readPack", String.class);
		method.setAccessible(true);
		method.invoke(p,filenameTest);
		assertEquals(1,p.getPack()[0].getValue(),"Value should be 1");
	}

	/**
	 * Sets up a card game with 2 players where the first player
	 * has a winning hand and then checks to see if the file
	 * has been correctly made containing the correct winner.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void writeWinTest() throws Exception{
		Pack p = new Pack(filenameTest,2);
		Method method1 = Pack.class.getDeclaredMethod("readPack", String.class);
		method1.setAccessible(true);
		PlayerImpl player1 = new PlayerImpl(1);
		PlayerImpl player2 = new PlayerImpl(2);
		for (int i = 0; i < p.getPack().length;i++) {
			if(i%2==0) {
				player1.getDeck().addCard(p.getPack()[i]);
			}else{
				player2.getDeck().addCard(p.getPack()[i]);
			}
		}
		player1.initHand();
		player2.initHand();
		player1.start();
		Method method2 = PlayerImpl.class.getDeclaredMethod("writeWinningStatement",
		                                                   java.io.BufferedWriter.class);
		method2.setAccessible(true);
		String storeLocation = "t" + filenameTest;
		FileWriter file = new FileWriter(storeLocation);
		BufferedWriter bw = new BufferedWriter(file);

		method2.invoke(player1, bw);
		bw.close();
		BufferedReader br = new BufferedReader(new FileReader(storeLocation));
		assertEquals("Player 1 wins", br.readLine());
	}
	//END OF PRIVATE FUNCTION TESTS

	//START OF PUBLIC FUNCTION TESTS
	/**
	 * Tests the functions in PlayerImpl
	 *
	 * The first test checks to see if the deck is empty when the player is
	 * initialised, which should return true.
	 *
	 * The second test checks to see if when adding cards to the players hand
	 * is the first cards value 1, which should return true.
	 */
	@Test
	void playerTests() {
		PlayerImpl tester = new PlayerImpl(1);
		DeckImpl testDeck = (DeckImpl) tester.getDeck();
		tester.setNextPlayerId(1);
		assertTrue("Deck is empty",testDeck.isEmpty());
		for (int counter = 0; counter < 8; counter++) {
			tester.getDeck().addCard(new Card((counter+1)%4));
		}
		tester.initHand();
		DeckImpl deckTest = (DeckImpl) tester.getDeck();
		assertEquals(1,deckTest.getCard().getValue(),"Value should be 1");
	}

	/**
	 * Tests the functions in the HandImpl class
	 *
	 * The first test checks if the hand is null to begin with
	 *
	 * The second test checks if the hand is a winning hand after
	 * adding cards.
	 *
	 * The third test checks if the timestamp of the card is 0 to begin with
	 *
	 * The fourth test checks if after updating the timestamp the value of
	 * the card equals 1.
	 *
	 * The fifth test checks to see if the hand contains the correct values.
	 *
	 * The sixth test checks if after adding a 5 to the hand that the last
	 * card after sorting is 5.
	 *
	 * The seventh test checks if it deletes the correct card with the
	 * corresponding index.
	 *
	 * @throws NullCardException the null card exception
	 */
	@Test
	void handTests() throws NullCardException {
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
	/*
	Helper function to initialise a deck of 1 2 3 ..
	returns the deck that has been initialised
	 */
	private DeckImpl initDeck() {
		DeckImpl deck = new DeckImpl();
		for (int i = 0; i < 8; i++) {
			deck.addCard(new Card(i + 1));
		}
		return deck;
	}

	/*
	Helper function to initialise a hand from the deck
	that has been created by the initDeck() function
	 */
	private void initHand(HandImpl hand, DeckImpl deck) {
		for (int i = 0; i < 4; i++) {
			hand.getHand()[i] = (deck.getCard());
		}
		hand.sortHand();
	}

	/**
	 * Tests the functions in DeckImpl
	 *
	 * The first test checks if the deck is not empty when initialised.
	 *
	 * The second test checks if the deck is empty after removing all
	 * the cards from the deck.
	 *
	 * The third test checks if the first and only card's value is equal
	 * to five after adding it.
	 *
	 * The fourth test checks if the showDeck() returns the correct
	 * string for the deck.
	 */
	@Test
	void deckTests() {
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
	 * Tests all the functions in Card class
	 *
	 * The first test checks if the card value is equal to 1
	 * after setting it with the constructor.
	 *
	 * The second test checks if the timestamp of the card is
	 * 0 when initialised.
	 *
	 * The third test checks to see if the timestamp updates
	 * for the card.
	 *
	 * The fourth test checks to see if the timestamp equals to
	 * 0 after being reset.
	 */
	@Test
	void cardTests() {
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
	 * Tests all the functions in the Pack class
	 *
	 * The first test checks to see if the pack size is correct
	 * with the specific number of players.
	 *
	 * The second test checks to see if the pack contains the
	 * correct data after reading from the test file.
	 * @throws IllegalFileException the illegal file exception
	 */
	@Test
	void packTests() throws IllegalFileException {
		int numberPlayer = 2;
		Pack tester = new Pack(filenameTest, numberPlayer);
		int testVal = numberPlayer * 8;
		assertEquals(testVal, tester.getPack().length,"Pack size should be equal to number of " +
		                                              "players * 8");
		Card[] expected = new Card[16];
		for (int i = 0; i < 16; i++) {
			if (i < 8) {
				expected[i] = new Card(i % 2 + 1);
			} else {
				expected[i] = new Card(i % 2 + 3);
			}
		}
		for (int counter = 0; counter < expected.length; counter++) {
			assertEquals(expected[counter].getValue(),tester.getPack()[counter].getValue()
					,"Values should match");
		}
	}
	//START OF EXCEPTION TESTS
	/**
	 * Tests to see if the exception contains the value given
	 */
	@Test
	void illegalFileTest(){
		IllegalFileException e = new IllegalFileException("test");
		assertNotNull(e,"not null");
	}

	/**
	 * Tests to see if the exception contains the value given
	 */
	@Test
	void illegalFileInputTest(){
		IllegalFileInputException e = new IllegalFileInputException("test");
		assertNotNull(e,"not null");
	}

	/**
	 * Tests to see if the exception contains the value given
	 */
	@Test
	void nullCard(){
		NullCardException e = new NullCardException("test");
		assertNotNull(e,"not null");
	}
	//END OF EXCEPTION TESTS

	//END OF PUBLIC FUNCTION TESTS
}

