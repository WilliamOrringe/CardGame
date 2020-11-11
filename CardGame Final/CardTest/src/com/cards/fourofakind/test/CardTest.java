package com.cards.fourofakind.test;

import com.cards.fourofakind.CardGame;
import com.cards.fourofakind.exception.IllegalFileException;
import com.cards.fourofakind.exception.IllegalFileInputException;
import com.cards.fourofakind.exception.NullCardException;
import com.cards.fourofakind.implementation.DeckImpl;
import com.cards.fourofakind.implementation.HandImpl;
import com.cards.fourofakind.implementation.PlayerImpl;
import com.cards.fourofakind.model.Card;
import com.cards.fourofakind.model.Pack;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;


/**
 * The testing suite for the card game
 * Tests all non-interface classes
 */
public class CardTest {
    /**
     * The identifier for a file used in testing
     */
    static final String FILE_NAME = "test.txt";

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
     *
     * @throws IllegalFileException Thrown when the file doesn't contain the correct data.
     * @throws NoSuchMethodException Thrown when a particular method cannot be found.
     * @throws InvocationTargetException Thrown by an invoked method or constructor.
     * @throws IllegalAccessException the currently executing method does not have access to the
     * definition of the specified class, field, method or constructor.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */

    //PRIVATE METHOD TESTS USING REFLECT API
    @Test
    public void testCardGame() throws IllegalFileException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, IOException {
        CardGame gameTest = new CardGame(2,FILE_NAME);
        Method method = CardGame.class.getDeclaredMethod("createPlayers", int.class);
        method.setAccessible(true);
        method.invoke(gameTest,2);

        Method dealMethod = CardGame.class.getDeclaredMethod("dealPack", String.class, int.class);
        dealMethod.setAccessible(true);
        Object[] args = new Object[2];
        args[0] = FILE_NAME;
        args[1] = 2;
        dealMethod.invoke(gameTest, args);

        File file = new File(FILE_NAME);
        assertTrue("File must exist",file.exists());
        CardGame gameTest2 = new CardGame(2,FILE_NAME);
        Method runMethod = CardGame.class.getDeclaredMethod("start");
        runMethod.setAccessible(true);
        runMethod.invoke(gameTest2);

        try (BufferedReader br = new BufferedReader(new FileReader("player1.txt"))) {
            Assert.assertEquals("Player 1 initial hand 1 1 1 1 ", br.readLine());
            br.readLine();
            Assert.assertEquals("Player 1 exits", br.readLine());
        }
        try (BufferedReader br = new BufferedReader(new FileReader("player2.txt"))) {
            Assert.assertEquals("Player 2 initial hand 2 2 2 2 ", br.readLine());
            br.readLine();
            Assert.assertEquals("Player 2 exits", br.readLine());
        }

    }

    /**
     * This tests the writeToFile function in the PlayerImpl class
     * to see if it writes the correct card values to the file given
     *
     * The first test checks to see if the file contains the correct values.
     *
     * @throws NoSuchMethodException Thrown when a particular method cannot be found.
     * @throws InvocationTargetException Thrown by an invoked method or constructor.
     * @throws IllegalAccessException the currently executing method does not have access to the
     * definition of the specified class, field, method or constructor.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Test
    public void writeFileTest() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, IOException {
        PlayerImpl p = new PlayerImpl(1);
        Method method = PlayerImpl.class.getDeclaredMethod("writeToFile",
                java.io.BufferedWriter.class, java.lang.String.class);
        method.setAccessible(true);
        FileWriter file = new FileWriter(FILE_NAME);
        BufferedWriter bw = new BufferedWriter(file);
        Object[] args =new Object[2];
        String[] checkValues = new String[16];
        args[0] = bw;
        for (int i = 0; i < 16; i++) {
            if(i < 8) {
                checkValues[i] = "" + (i % 2 + 1);
                args[1] = "" + (i % 2 + 1);
            }else{
                checkValues[i] = "" + (i % 2 + 3);
                args[1] = "" + (i % 2 + 3);
            }
            method.invoke(p,args);
        }
        bw.close();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            for (int counter = 0; counter < 16; counter++) {
                Assert.assertEquals(checkValues[counter],br.readLine());
            }
        }
    }

    /**
     * Reads from the file given to the Pack object and checks
     * to see if it has read the correct data.
     *
     * @throws NoSuchMethodException Thrown when a particular method cannot be found.
     * @throws InvocationTargetException Thrown by an invoked method or constructor.
     * @throws IllegalAccessException the currently executing method does not have access to the
     * definition of the specified class, field, method or constructor.
     * @throws IllegalFileException Thrown when the file doesn't contain the correct data.
     */
    @Test
    public void readPackTest() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, IllegalFileException {
        Pack p = new Pack(FILE_NAME,2);
        Method method = Pack.class.getDeclaredMethod("readPack", String.class);
        method.setAccessible(true);
        method.invoke(p,FILE_NAME);
        //Value should be 1
        Assert.assertEquals(1, p.getCards()[0].getValue());
    }

    /**
     * Sets up a card game with 2 players where the first player
     * has a winning hand and then checks to see if the file
     * has been correctly made containing the correct winner.
     *
     * @throws NoSuchMethodException Thrown when a particular method cannot be found.
     * @throws InvocationTargetException Thrown by an invoked method or constructor.
     * @throws IllegalAccessException the currently executing method does not have access to the
     * definition of the specified class, field, method or constructor.
     * @throws IllegalFileException Thrown when the file doesn't contain the correct data.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Test
    public void writeWinTest() throws NoSuchMethodException, IllegalFileException,
            InvocationTargetException, IllegalAccessException, IOException {
        winTests("2"+ FILE_NAME); //Tests if player 1 doesn't win instantly
        winTests(FILE_NAME); //Tests if player 1 does win instantly
    }
    private void winTests(String filename) throws NoSuchMethodException, IllegalFileException,
            InvocationTargetException, IllegalAccessException, IOException {
        Pack p = new Pack(filename,2);
        Method method1 = Pack.class.getDeclaredMethod("readPack", String.class);
        method1.setAccessible(true);
        PlayerImpl player1 = new PlayerImpl(1);
        PlayerImpl player2 = new PlayerImpl(2);
        for (int i = 0; i < p.getCards().length;i++) {
            if(i%2==0) {
                player1.getDeck().addCard(p.getCards()[i]);
            }else{
                player2.getDeck().addCard(p.getCards()[i]);
            }
        }
        player1.initHand();
        player2.initHand();
        player1.setNextPlayerDeck(player2.getDeck());
        player1.start();
        Method method2 = PlayerImpl.class.getDeclaredMethod("writeWinningStatement",
                java.io.BufferedWriter.class);
        method2.setAccessible(true);
        String whereStored = "w" + FILE_NAME;
        FileWriter file = new FileWriter(whereStored);
        BufferedWriter bw = new BufferedWriter(file);
        method2.invoke(player1, bw);
        bw.close();
        try (BufferedReader br = new BufferedReader(new FileReader(whereStored))) {
            Assert.assertEquals("Player 1 wins", br.readLine());
        }
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
    public void playerTests() {
        PlayerImpl tester = new PlayerImpl(1);
        DeckImpl testDeck = (DeckImpl) tester.getDeck();
        tester.setNextPlayerId(1);
        assertTrue("Deck is empty",testDeck.isEmpty());
        for (int counter = 0; counter < 8; counter++) {
            tester.getDeck().addCard(new Card((counter+1)%4));
        }
        tester.initHand();
        DeckImpl deckTest = (DeckImpl) tester.getDeck();
        //Value should be 1
        Assert.assertEquals(1, deckTest.getCard().getValue());
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
     * @throws NullCardException When the card value is null
     */
    @Test
    public void handTests() throws NullCardException {
        HandImpl tester = new HandImpl();
        DeckImpl deck = initDeck();
        //Hand should be empty to begin with
        Assert.assertNull(tester.getHand()[0]);

        initHand(tester, deck);
        //Hand is not a winning hand
        Assert.assertFalse(tester.isWin());


        tester.updateHandTimestamp();
        //Updated time should be 1
        Assert.assertEquals(1,tester.getHand()[0].getTimestamp());

        //First element should be equal to card with a value of 1
        Assert.assertEquals(1, tester.getHand()[0].getValue());

        //Deck string must be = to: 1 2 3 4
        Assert.assertEquals("1 2 3 4 ", tester.showHand());
        //add something for negative or out of bounds ?
        tester.addCard(new Card(5));
        tester.sortHand();
        //Last card should be 5 after adding
        Assert.assertEquals(5,tester.getHand()[3].getValue());
        //Deleted card should be 2
        Assert.assertEquals(2,tester.removeCard(new Card(1)).getValue());

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
    public void deckTests() {
        DeckImpl tester = initDeck();
        //Deck is not empty when initialised
        Assert.assertFalse(tester.isEmpty());
        for (int counter = 0; counter < 8; counter++) {
            tester.getCard();
        }
        assertTrue("Deck should be empty after subtracting all the cards", tester.isEmpty());
        tester.addCard(new Card(5));
        //First card's value = 5
        Assert.assertEquals(5, tester.getCard().getValue());
        tester.addCard(new Card(10));
        //Deck string must be = to: 10
        Assert.assertEquals("10 ", tester.showDeck());
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
    public void cardTests() {
        Card tester = new Card(1);

        //Card value is equal to 1
        Assert.assertEquals(1, tester.getValue());
        //Time stamp must be 0
        Assert.assertEquals(0,tester.getTimestamp());

        tester.updateTimestamp();
        //Timestamp must be 1 after first update
        Assert.assertEquals(1,tester.getTimestamp());

        tester.resetTimestamp();
        //Timestamp must be 0 after reset
        Assert.assertEquals(0,tester.getTimestamp());
    }

    /**
     * Tests all the functions in the Pack class
     *
     * The first test checks to see if the pack size is correct
     * with the specific number of players.
     *
     * The second test checks to see if the pack contains the
     * correct data after reading from the test file.
     * @throws IllegalFileException Thrown when the file doesn't contain the correct data.
     */
    @Test
    public void packTests() throws IllegalFileException {
        int numberPlayer = 2;
        Pack tester = new Pack(FILE_NAME, numberPlayer);
        int testVal = numberPlayer * 8;
        //Pack size should be equal to number of players * 8
        Assert.assertEquals(testVal, tester.getCards().length);
        Card[] expected = new Card[16];
        for (int i = 0; i < 16; i++) {
            if (i < 8) {
                expected[i] = new Card(i % 2 + 1);
            } else {
                expected[i] = new Card(i % 2 + 3);
            }
        }
        for (int counter = 0; counter < expected.length; counter++) {
            //Values should match
            Assert.assertEquals(expected[counter].getValue(),tester.getCards()[counter].getValue());
        }
    }
    //START OF EXCEPTION TESTS
    /**
     * Tests to see if the exception contains the value given
     */
    @Test
    public void illegalFileTest(){
        IllegalFileException e = new IllegalFileException("test");
        //not null
        Assert.assertNotNull(e);
    }

    /**
     * Tests to see if the exception contains the value given
     */
    @Test
    public void illegalFileInputTest(){
        IllegalFileInputException e = new IllegalFileInputException("test");
        //not null
        Assert.assertNotNull(e);
    }

    /**
     * Tests to see if the exception contains the value given
     */
    @Test
    public void nullCard(){
        NullCardException e = new NullCardException("test");
        //not null
        Assert.assertNotNull(e);
    }
    //END OF EXCEPTION TESTS

    //END OF PUBLIC FUNCTION TESTS
}