package com.cards.fourofakind;

import java.util.*;

public class Player {
    private final String name;
    private final Card preferredCard;
    private Card[] hand = new Card[4];
    private Deck deck;

    public Player(String name) {
        this.name = name;
        preferredCard = new Card(Integer.parseInt(name.replaceAll("[\\D]", "")));

        deck = new Deck(new ArrayList<>());
    }

    private void sortHand() {
        Arrays.sort(this.hand, Comparator.comparingInt(Card::getValue));
    }

    public void setInitialHand() {
        for (int i = 0; i < hand.length; i++) {
            hand[i] = deck.getCards().remove(0);
        }
        sortHand();
    }

    public void addCardToDeck(Card card) {
        this.deck.getCards().add(card);
    }

    public void addCardToHand() {
        if (this.hand[3] == null) {
            this.hand[3] = this.deck.getCards().remove(0);
            sortHand();
        }
    }
    private List<Card> leastFrequent() {
        sortHand();
        List<Card> leastFrequentCards = new ArrayList<>();
        int counter = 0;

        for (int x = 0; x < this.hand.length; x++) {
            int tempCounter = 1;
            for (int y = 0; y < this.hand.length; y++) {
                if (x == y) {
                    continue;
                } else if (this.hand[x].getValue() == this.hand[y].getValue()) {
                    tempCounter++;
                }
            }
            if (this.hand[x].getValue() != preferredCard.getValue()) {
                if (counter == 0) {
                    counter = tempCounter;
                    leastFrequentCards.add(this.hand[x]);
                } else if (counter == tempCounter && counter != 0) {
                    leastFrequentCards.add(this.hand[x]);
                } else if (tempCounter < counter) {
                    leastFrequentCards.clear();
                    counter = tempCounter;
                    leastFrequentCards.add(this.hand[x]);
                } else {
                    continue;
                }
            }
        }
        return leastFrequentCards;
    }

    private void removeCard(Card value) {
        ArrayList<Card> temp = new ArrayList<>(Arrays.asList(this.hand));
        temp.remove(value);
        this.hand = temp.toArray(this.hand);
    }

    public Card removeCardFromHand() {
        Random random = new Random();
        List<Card> leastFrequentCards = leastFrequent();
        Card removedCard = leastFrequentCards.get(random.nextInt(leastFrequentCards.size()));
        removeCard(removedCard);
        return removedCard;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public Card[] getHand() {
        return hand;
    }
}
