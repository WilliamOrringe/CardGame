package com.cards.fourofakind.api;

public interface Player {
    void start();

    void setNextPlayerDeck(PlayerDeck playerDeck);

    PlayerDeck getPlayerDeck();
}

