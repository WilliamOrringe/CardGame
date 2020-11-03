package com.cards.fourofakind.api;

import com.cards.fourofakind.model.Card;

public interface Deck extends PlayerDeck{
    Card getCard();

    boolean isEmpty();
}
