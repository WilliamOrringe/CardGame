package com.cards.fourofakind.model;

public class Card {
    private final int value;
    private int timestamp = 0;

    /**
     * Constructs an instance of the object containing value argument.
     *
     * @param value                 value of the card
     */
    public Card(int value) {
        this.value = value;
    }

    /**
     * Method used to get the value of the card.
     *
     * @return              value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * Method used to get the timestamp of the card.
     *
     * @return              timestamp of the card
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Method to update the card's timestamp.
     */
    public void updateTimestamp() {
        timestamp++;
    }

    /**
     * Method to reset the card's timestamp.
     */
    public void resetTimestamp() {
        timestamp = 0;
    }
}
