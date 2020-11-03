package com.cards.fourofakind.api;

import com.cards.fourofakind.model.Card;

public interface Hand {
    void addCard(Card card);

    Card removeCard(Card preferredCard);

    Card[] getHand();

    void sortHand();

    boolean isWin();
}
