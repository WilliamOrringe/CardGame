package com.cards.fourofakind.api;

import com.cards.fourofakind.exception.NullCardException;
import com.cards.fourofakind.model.Card;

public interface Hand {
    /**
     * Method to add a Card object to hand.
     *
     * @param card          Card object
     */
    void addCard(Card card) throws NullCardException;

    /**
     * Method to remove a Card object from hand, which isn't the preferredCard.
     *
     * @param preferredCard         Card object
     * @return                      Card object
     */
    Card removeCard(Card preferredCard);

    /**
     * Method to update all the card's timestamp in the array of card objects.
     */
    void updateHandTimestamp();

    /**
     * Method used to return a string of the list of Card objects.
     *
     * @return              list of Card objects
     */
    String showHand();

    /**
     * Method used the get the an array of Card objects.
     *
     * @return          array of Card objects
     */
    Card[] getHand();

    /**
     * Method used to sort the array of Card objects in ascending order of their value.
     */
    void sortHand();

    /**
     * Method used to indicate whether or not the hand is a winning hand.
     *
     * @return              whether or not the hand is a winning hand
     */
    boolean isWin();
}
