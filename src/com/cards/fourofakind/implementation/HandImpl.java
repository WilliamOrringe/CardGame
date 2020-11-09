package com.cards.fourofakind.implementation;

import com.cards.fourofakind.api.Hand;
import com.cards.fourofakind.model.Card;

import java.util.*;

public class HandImpl implements Hand {
    private Card[] cards;

    /**
     * Constructs an instance of the object with no message.
     */
    public HandImpl() {
       cards = new Card[4];
    }

    /**
     * Method to add a Card object to hand.
     *
     * @param card          Card object
     */
    @Override
    public void addCard(Card card) {
        cards[3] = card;
        sortHand();
    }

    /**
     * Method to remove a Card object from hand, which isn't the preferredCard.
     *
     * @param preferredCard         Card object
     * @return                      Card object
     */
    @Override
    public Card removeCard(Card preferredCard) {
        List<Card> leastFrequentCards = leastFrequent(preferredCard);
        Card removedCard;
        if (leastFrequentCards.isEmpty()){
            removedCard = cards[getLongestWaitingCardIndex(Arrays.asList(cards), preferredCard)];
        } else {
            removedCard = leastFrequentCards.get(getLongestWaitingCardIndex(leastFrequentCards, preferredCard));
        }
        removingCard(removedCard);
        removedCard.resetTimeStamp();
        return removedCard;
    }

    /**
     * Method to update all the card's timestamp in the array of card objects.
     */
    @Override
    public void updateHandTimestamp() {
        for (Card card : cards) {
            card.updateTimeStamp();
        }
    }

    /**
     * Method used to return a string of the list of Card objects.
     *
     * @return              list of Card objects
     */
    @Override
    public String showHand() {
        StringBuilder hand = new StringBuilder();
        for (Card card : cards) {
            hand.append(card.getValue()).append(" ");
        }
        return hand.toString();
    }

    /**
     * Method used the get the an array of Card objects.
     *
     * @return          array of Card objects
     */
    @Override
    public Card[] getHand() {
        return cards;
    }

    /**
     * Method used to sort the array of Card objects in ascending order of their value.
     */
    @Override
    public void sortHand() {
        Arrays.sort(cards, Comparator.nullsLast(Comparator.comparingInt(Card::getValue)));
    }

    /**
     * Method used to indicate weather or not the hand is a winning hand.
     *
     * @return              weather or not the hand is a winning hand
     */
    @Override
    public boolean isWin() {
        int value = cards[0].getValue();
	    return (value == cards[1].getValue()
            && value == cards[2].getValue()
            && value == cards[3].getValue());
    }

    /**
     * Method used to get a list of Card objects which occurs least frequently in the hand.
     *
     * @param preferredCard             player's preferred card
     * @return                          list of Card objects
     */
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

    /**
     * Method to get the Card object's index that has waiting the longest.
     *
     * @param preferredCard             player's preferred card
     * @return                          Card object's index
     */
    private int getLongestWaitingCardIndex(List<Card> cards, Card preferredCard) {
        int timestamp = 0;
        int index = 0;

        for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
            if (preferredCard.getValue() != cards.get(cardIndex).getValue()
                    && timestamp < cards.get(cardIndex).getTimeStamp()) {
                timestamp = cards.get(cardIndex).getTimeStamp();
                index = cardIndex;
            }
        }
        return index;
    }

    /**
     * Method to clean the allocated space in the array.
     *
     * @param value             Card object that needs to be removed
     */
    private void removingCard(Card value) {
        //removing all the null element in the array and removing the card from hand.
        ArrayList<Card> temp = new ArrayList<>(Arrays.asList(cards));
        temp.remove(value);
        cards = temp.toArray(cards);
    }
}
