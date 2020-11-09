package com.cards.fourofakind.api;

public interface Player {
    /**
     * Method to assign the next player's deck in the round-robin.
     *
     * @param playerDeck            PlayerDeck object
     */
    void setNextPlayerDeck(PlayerDeck playerDeck);

    /**
     * Method used to retrieve player's deck.
     *
     * @return          PlayerDeck object
     */
    PlayerDeck getDeck();

    /**
     * Method used to control to gameplay of the player.
     */
    void start();
}

