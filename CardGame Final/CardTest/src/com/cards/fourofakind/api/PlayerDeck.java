package com.cards.fourofakind.api;

import com.cards.fourofakind.model.Card;

public interface PlayerDeck {
    /**
     * Method to add card to the player's deck.
     *
     * @param card          Card object
     */
    void addCard(Card card);
}
