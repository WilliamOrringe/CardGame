package com.cards.fourofakind;

import com.cards.fourofakind.model.Card;
import com.cards.fourofakind.model.Pack;
import com.cards.fourofakind.impl.PlayerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CardGame {
    private PlayerImpl[] players;

    public CardGame(int numberOfPlayer, String filename) {
        createPlayers(numberOfPlayer);
        dealPack(filename, numberOfPlayer);
        setPlayersInitialHand();
        setDiscardPlayerDeck();
    }

    private void start() {
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        for (PlayerImpl player : players) {
            completableFutures.add(CompletableFuture.runAsync(player::start));
        }
        CompletableFuture<Void> futures = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
        futures.join();
    }

    private void createPlayers(int numberOfPlayer) {
        players = new PlayerImpl[numberOfPlayer];
        for (int playerIndex = 0; playerIndex < players.length; playerIndex++) {
            players[playerIndex] = new PlayerImpl(playerIndex + 1);
        }
    }

    private void dealPack(String filename, int numberOfPlayer) {
        Pack pack = new Pack(filename, numberOfPlayer);
        int i = 0;
        for (Card card : pack.getPack()) {
            if (i < 8 * numberOfPlayer) {
                players[i % numberOfPlayer].getPlayerDeck().addCard(card);
                i++;
            }
        }
    }

    private void setPlayersInitialHand() {
        for (PlayerImpl p : players) {
            p.initHand();
        }
    }

    private void setDiscardPlayerDeck() {
        for (int playerIndex = 0; playerIndex < players.length; playerIndex++) {
            if (playerIndex == players.length - 1) {
                players[playerIndex].setNextPlayerDeck(players[0].getPlayerDeck());
            } else {
                players[playerIndex].setNextPlayerDeck(players[playerIndex + 1].getPlayerDeck());
            }
        }
    }


    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//
//        int numberOfPlayer;
//        do {
//            System.out.println("Please enter the number of players: ");
//            numberOfPlayer = input.nextInt();
//        }while (numberOfPlayer < 1);
//
//        System.out.println("Please enter location of pack to load: ");
//        String filename = input.next();
//
//        input.close();
//
//        CardGame game = new CardGame(numberOfPlayer, filename);

        CardGame game = new CardGame(4, "four.txt");
        game.start();
    }
}
