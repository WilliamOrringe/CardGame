package com.cards.fourofakind.impl;

import com.cards.fourofakind.api.Deck;
import com.cards.fourofakind.model.Card;

import java.util.LinkedList;
import java.util.Queue;

public class DeckImpl implements Deck {
    private final Queue<Card> cards;

    public DeckImpl() {
        this.cards = new LinkedList<>();
    }

    public synchronized Card getCard() {
        return this.cards.poll();
    }

    @Override
    public synchronized void addCard(Card card) {
        this.cards.add(card);
    }

    @Override
    public boolean isEmpty() {
        return this.cards.isEmpty();
    }
}
