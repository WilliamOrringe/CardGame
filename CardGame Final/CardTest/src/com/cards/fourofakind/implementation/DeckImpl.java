package com.cards.fourofakind.implementation;

import com.cards.fourofakind.api.Deck;
import com.cards.fourofakind.model.Card;

import java.util.LinkedList;
import java.util.Queue;

public class DeckImpl implements Deck {
    private final Queue<Card> cards;

    /**
     * Constructs an instance of the object with no message.
     */
    public DeckImpl() {
        this.cards = new LinkedList<>();
    }

    /**
     * Method to get the Card object.
     *
     * @return          Card object
     */
    @Override
    public synchronized Card getCard() {
        //removes the head (first element) of the queue
        return this.cards.poll();
    }

    /**
     * Method to represent whether the Deck is empty.
     *
     * @return          if the Deck is empty
     */
    @Override
    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

    /**
     * Method used to return a string of the list of Card objects.
     *
     * @return          list of Card objects
     */
    @Override
    public synchronized String showDeck() {
        StringBuilder deck = new StringBuilder();
        for (Card card : cards) {
            //joining string
            deck.append(card.getValue()).append(" ");
        }
        return deck.toString();
    }

    /**
     * Method to add card to the player's deck.
     *
     * @param card          Card object
     */
    @Override
    public synchronized void addCard(Card card) {
        this.cards.add(card);
    }
}
