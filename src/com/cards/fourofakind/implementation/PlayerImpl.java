package com.cards.fourofakind.implementation;

import com.cards.fourofakind.api.Deck;
import com.cards.fourofakind.api.Hand;
import com.cards.fourofakind.api.Player;
import com.cards.fourofakind.api.PlayerDeck;
import com.cards.fourofakind.model.Card;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerImpl implements Player {
    private final String name;
    private final Card preferredCard;
    private final Hand hand;
    private final Deck deck;
    private final static AtomicBoolean isStop = new AtomicBoolean(false);
    private PlayerDeck playerDeck;
    private int nextPlayerId;

    private static String winner;
    private static String winningHand;

    /**
     * Constructs an instance of the object containing playerNo argument.
     *
     * @param playerNo              number of players
     */
    public PlayerImpl(int playerNo) {
        this.name = "Player " + playerNo;
        preferredCard = new Card(playerNo);
        deck = new DeckImpl();
        hand = new HandImpl();
    }

    /**
     * Method to store next player's id in the round robin.
     *
     * @param id              player id
     */
    public void setNextPlayerId(int id) {
        this.nextPlayerId = id;
    }

    /**
     * Method to initialise player's hand by getting to first 4 cards from the deck.
     */
    public void initHand() {
        for (int handIndex = 0; handIndex < 4; handIndex++) {
            hand.getHand()[handIndex] = (deck.getCard());
        }
        hand.sortHand();
    }

    /**
     * Method to assign the next player's deck in the round-robin.
     *
     * @param playerDeck            PlayerDeck object
     */
    @Override
    public void setNextPlayerDeck(PlayerDeck playerDeck) {
        this.playerDeck = playerDeck;
    }

    /**
     * Method used to retrieve player's deck.
     *
     * @return          PlayerDeck object
     */
    @Override
    public PlayerDeck getDeck() {
        return this.deck;
    }

    /**
     * Method used to control to gameplay of the player.
     */
    @Override
    public void start() {
        try {
            //initialing gameplay file.
            String postfix = preferredCard.getValue() + ".txt";
            BufferedWriter gameplayWriter = new BufferedWriter(new FileWriter(
                    "player" + postfix));
            //initialing deck file.
            BufferedWriter deckWriter = new BufferedWriter(new FileWriter("deck" + postfix));

            writeToFile(gameplayWriter, name + " initial hand " + hand.showHand());
            while (!isStop.get()) {
                hand.updateHandTimestamp();
                if (hand.isWin()) {
                    winner = name;
                    winningHand = hand.showHand();
                    isStop.compareAndSet(false, true);
                } else {
                    Card newCard = null;
                    while (newCard == null && !isStop.get()) {
                        newCard = deck.getCard();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!isStop.get()) {
                        Card discardCard = hand.removeCard(preferredCard);
                        playerDeck.addCard(discardCard);
                        hand.addCard(newCard);
                        //Writing player's gameplay to a file.
                        assert newCard != null;
                        writeToFile(gameplayWriter, name + " draws a " + newCard.getValue() + " from deck " + preferredCard.getValue());
                        writeToFile(gameplayWriter, name + " discards a " + discardCard.getValue() + " to deck " + nextPlayerId);
                        writeToFile(gameplayWriter, name + " current hand is " + hand.showHand());

                        writeToFile(deckWriter, "Deck " + preferredCard.getValue() + " contents: " + deck.showDeck());
                    }
                }
            }
            deckWriter.close();
            writeWinningStatement(gameplayWriter);
            gameplayWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to write text to a text file.
     *
     * @param writer                BufferedWriter object
     * @param text                  string of text that need to be written
     */
    private void writeToFile(BufferedWriter writer, String text) {
        try {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to writing the final/winning statement to the text file.
     *
     * @param writer                BufferedWriter object
     */
    private void writeWinningStatement(BufferedWriter writer) {
        if (winner.equals(name)) {
            writeToFile(writer, winner + " wins");
            writeToFile(writer, winner + " exits");
            writeToFile(writer, winner + " final hand: " + winningHand);
        } else {
            writeToFile(writer, winner + " has informed " + name + " that " + winner + " has won");
            writeToFile(writer, name + " exits");
            writeToFile(writer, name + " hand: " + hand.showHand());
        }
    }
}
