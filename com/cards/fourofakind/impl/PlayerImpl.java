package com.cards.fourofakind.impl;

import com.cards.fourofakind.api.Deck;
import com.cards.fourofakind.api.Hand;
import com.cards.fourofakind.api.Player;
import com.cards.fourofakind.api.PlayerDeck;
import com.cards.fourofakind.model.Card;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerImpl implements Player {
    private final String name;
    private final Card preferredCard;
    private final Hand hand;
    private final Deck deck;
    private static AtomicBoolean isStop = new AtomicBoolean(false);
    private PlayerDeck playerDeck;

    public PlayerImpl(int playerNo) {
        this.name = "Player " + playerNo;
        preferredCard = new Card(playerNo);
        deck = new DeckImpl();
        hand = new HandImpl();
    }

    public void initHand() {
        for (int i = 0; i < 4; i++) {
            hand.getHand()[i] = (deck.getCard());
        }
        hand.sortHand();
    }

    private void displayHand() {
        System.out.println(name + "'s Hand");
        for(Card card : hand.getHand()) {
            System.out.print(card.getValue());
        }
    }

    @Override
    public void setNextPlayerDeck(PlayerDeck playerDeck) {
        this.playerDeck = playerDeck;
    }

    @Override
    public PlayerDeck getPlayerDeck() {
        return this.deck;
    }

    @Override
    public void start() {
        while (!isStop.get()) {
            if (!hand.isWin()) {
	            displayHand();
	            playerDeck.addCard(hand.removeCard(preferredCard));
	            hand.addCard(deck.getCard());
            } else {
	            displayHand();
	            System.out.println("Winner: " + name);
	            isStop.compareAndSet(false, true);
            }
        }
    }
}
