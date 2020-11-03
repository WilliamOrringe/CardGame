package com.cards.fourofakind.impl;

import com.cards.fourofakind.api.Hand;
import com.cards.fourofakind.model.Card;

import java.util.*;


public class HandImpl implements Hand {
    private Card[] cards;
	private	Random random = new Random();
    public HandImpl() {
       cards = new Card[4];
    }

    @Override
    public void addCard(Card card) {
        cards[3] = card;
        sortHand();
    }

    @Override
    public Card removeCard(Card preferredCard) {
        List<Card> leastFrequentCards = leastFrequent(preferredCard);
        Card removedCard = leastFrequentCards.get(random.nextInt(leastFrequentCards.size()));
        removingCard(removedCard);
        return removedCard;
    }

    @Override
    public Card[] getHand() {
        return cards;
    }

    @Override
    public void sortHand() {
        Arrays.sort(cards, Comparator.comparingInt(Card::getValue));
    }

    @Override
    public boolean isWin() {
	    return (cards[0].getValue() == cards[1].getValue()
            && cards[0].getValue() == cards[2].getValue()
            && cards[0].getValue() == cards[3].getValue());
    }

    private List<Card> leastFrequent(Card preferredCard) {
        sortHand();
        List<Card> leastFrequentCards = new ArrayList<>();
        int counter = 0;

	    for (Card value : cards) {
		    int tempCounter = 1;
		    for (Card card : cards) {
			    if (value.getValue() == card.getValue()) {
				    tempCounter++;
			    }
		    }
		    if (value.getValue() != preferredCard.getValue()) {
			    if (counter == 0) {
				    counter = tempCounter;
				    leastFrequentCards.add(value);
			    } else if (counter == tempCounter) {
				    leastFrequentCards.add(value);
			    } else if (tempCounter < counter) {
				    leastFrequentCards.clear();
				    counter = tempCounter;
				    leastFrequentCards.add(value);
			    }
		    }
	    }
        return leastFrequentCards;
    }

    private void removingCard(Card value) {
        ArrayList<Card> temp = new ArrayList<>(Arrays.asList(cards));
        temp.remove(value);
        cards = temp.toArray(cards);
    }
}
