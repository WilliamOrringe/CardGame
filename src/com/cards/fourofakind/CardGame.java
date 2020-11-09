package com.cards.fourofakind;

import com.cards.fourofakind.model.Card;
import com.cards.fourofakind.model.Pack;
import com.cards.fourofakind.implementation.PlayerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class CardGame {
    private PlayerImpl[] players;

    /**
     * Constructs an instance of the object containing numberOfPlayer and filename arguments.
     *
     * @param numberOfPlayer                number of players
     * @param filename                      name of text file
     */
    public CardGame(int numberOfPlayer, String filename) {
        createPlayers(numberOfPlayer);
        dealPack(filename, numberOfPlayer);
        setPlayersInitialHand();
        setDiscardPlayerDeck();
    }

    /**
     * Method to start the game via CompletableFuture Threading method.
     */
    private void start() {
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        for (PlayerImpl player : players) {
            completableFutures.add(CompletableFuture.runAsync(player::start));
        }
        CompletableFuture<Void> futures = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
        futures.join();
    }

    /**
     * Method used to initialise an array of player objects.
     *
     * @param numberOfPlayer            number of player
     */
    private void createPlayers(int numberOfPlayer) {
        players = new PlayerImpl[numberOfPlayer];
        for (int playerIndex = 0; playerIndex < players.length; playerIndex++) {
            players[playerIndex] = new PlayerImpl(playerIndex + 1);
        }
    }

    /**
     * Method used to initialise player's deck.
     *
     * @param filename                  text file
     * @param numberOfPlayer            number of player
     */
    private void dealPack(String filename, int numberOfPlayer) {
        Pack pack = new Pack(filename, numberOfPlayer);
        int i = 0;
        for (Card card : pack.getPack()) {
            if (i < 8 * numberOfPlayer) {
                players[i % numberOfPlayer].getDeck().addCard(card);
                i++;
            }
        }
    }

    /**
     * Method to initialise all the player's hand.
     */
    private void setPlayersInitialHand() {
        for (PlayerImpl player : players) {
            player.initHand();
        }
    }

    /**
     * Method to initialise all the player's discard Deck.
     */
    private void setDiscardPlayerDeck() {
        for (int playerIndex = 0; playerIndex < players.length; playerIndex++) {
            if (playerIndex == players.length - 1) {
                players[playerIndex].setNextPlayerDeck(players[0].getDeck());
                players[playerIndex].setNextPlayerId(1);
            } else {
                players[playerIndex].setNextPlayerDeck(players[playerIndex + 1].getDeck());
                players[playerIndex].setNextPlayerId(playerIndex + 2);
            }
        }
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int numberOfPlayer;
        do {
            System.out.println("Please enter the number of players: ");
            numberOfPlayer = input.nextInt();
        }while (numberOfPlayer < 1);

        System.out.println("Please enter location of pack to load: ");
        String filename = input.next();

        input.close();

        CardGame game = new CardGame(numberOfPlayer, filename);
        game.start();
    }
}
