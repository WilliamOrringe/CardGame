package com.cards.fourofakind.implementation;

import com.cards.fourofakind.api.Deck;
import com.cards.fourofakind.api.Hand;
import com.cards.fourofakind.api.Player;
import com.cards.fourofakind.api.PlayerDeck;
import com.cards.fourofakind.exception.NullCardException;
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

    private PlayerDeck playerDeck;
    private int nextPlayerId;
    private static String winner;
    private static String winningHand;

    private static final AtomicBoolean isStop = new AtomicBoolean(false);

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
        //getting 4 cards from the deck
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
            try (BufferedWriter gameplayWriter = new BufferedWriter(new FileWriter(
                    "player" + preferredCard.getValue() + ".txt"))) {
                //initialing deck file.
                try (BufferedWriter deckWriter = new BufferedWriter(new FileWriter(
                        "deck" + preferredCard.getValue() + ".txt"))) {

                    writeToFile(gameplayWriter, name + " initial hand " + hand.showHand());
                    //check whether of not isStop is true
                    while (!isStop.get()) {
                        hand.updateHandTimestamp();
                        //check is the hand is a winning hand
                        if (hand.isWin()) {
                            winner = name;
                            winningHand = hand.showHand();
                            isStop.compareAndSet(false, true);
                        } else {
                            Card newCard = null;
                            //checking whether the card is null and whether of not isStop is true
                            while (newCard == null && !isStop.get()) {
                                newCard = deck.getCard();
                            }
                            //check whether of not isStop is true
                            if (!isStop.get()) {
                                Card discardCard = hand.removeCard(preferredCard);
                                playerDeck.addCard(discardCard);
                                hand.addCard(newCard);
                                //testing newCard is not null
                                assert newCard != null;
                                //Writing player's gameplay to a file.
                                writeToFile(gameplayWriter, name + " draws a " + newCard.getValue() + " from deck "
                                        + preferredCard.getValue());
                                writeToFile(gameplayWriter, name + " discards a " + discardCard.getValue()
                                        + " to deck " + nextPlayerId);
                                writeToFile(gameplayWriter, name + " current hand is " + hand.showHand());

                                writeToFile(deckWriter, "Deck " + preferredCard.getValue() + " contents: "
                                        + deck.showDeck());
                            }
                        }
                    }
                }
                writeWinningStatement(gameplayWriter);
            }
        } catch (IOException | NullCardException e) {
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
            //writing to a file
            writer.write(text);
            //inserting new line to the file
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
        //check whether the winner is the same as this player
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

    public static String getWinner() {
        return winner;
    }
}
