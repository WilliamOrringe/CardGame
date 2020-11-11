package com.cards.fourofakind.api;

import com.cards.fourofakind.model.Card;

public interface Deck extends PlayerDeck{
    /**
     * Method to get the Card object.
     *
     * @return          Card object
     */
    Card getCard();

    /**
     * Method to represent whether the Deck is empty.
     *
     * @return          if the Deck is empty
     */
    boolean isEmpty();

    /**
     * Method used to return a string of the list of Card objects.
     *
     * @return          list of Card objects
     */
    String showDeck();
}
