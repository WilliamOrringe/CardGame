package com.cards.fourofakind;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return this.cards;
    }

//    private void sortDeck() {
//        this.cards.stream()
//                .sorted(Comparator.comparingInt(Card::getValue))
//                .collect(Collectors.toList());
//    }
}
