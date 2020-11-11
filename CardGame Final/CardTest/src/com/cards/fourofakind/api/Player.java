package com.cards.fourofakind.api;

public interface Player {
    /**
     * Method to store next player's id in the round robin.
     *
     * @param id              player id
     */
    void setNextPlayerId(int id);

    /**
     * Method to initialise player's hand by getting to first 4 cards from the deck.
     */
    void initHand();

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

