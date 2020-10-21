package com.cards.fourofakind;

import java.util.Scanner;

public class CardGame {
    private Player[] players;
    private boolean isWinner = false;

    public CardGame(int numberOfPlayer, String filename) {
        createPlayers(numberOfPlayer);
        dealPack(filename, numberOfPlayer);
        setPlayersInitialHand();

        gameplay();
    }

    private void gameplay() {
        Player winingPlayer = winner();
        while (!isWinner && winingPlayer == null) {
            System.out.println("PLAYER'S HAND");
            for (Player p : players) {
                System.out.println(p.getName());
                for (Card c : p.getHand()) {
                    System.out.print(c.getValue() + " ");
                }
                System.out.println(" ");
            }
            for (int p = 0; p < players.length; p++) {
                if (p == players.length - 1) {
                    players[0].addCardToDeck(players[p].removeCardFromHand());
                } else {
                    players[p + 1].addCardToDeck(players[p].removeCardFromHand());
                }
                players[p].addCardToHand();
            }
            winingPlayer = winner();
        }
        System.out.println("PLAYER'S HAND");
        for (Player p : players) {
            System.out.println(p.getName());
            for (Card c : p.getHand()) {
                System.out.print(c.getValue() + " ");
            }
            System.out.println(" ");
        }
        System.out.println("Winner: " + winingPlayer.getName());
    }

    private void createPlayers(int numberOfPlayer) {
        players = new Player[numberOfPlayer];
        for (int p = 0; p < players.length; p++) {
            players[p] = new Player("Player " + (p + 1));
        }
    }

    private void dealPack(String filename, int numberOfPlayer) {
        Pack pack = new Pack(filename, numberOfPlayer);
        int i = 0;
        for (Card card : pack.getPack()) {
            if (i < 8 * numberOfPlayer) {
                players[i % numberOfPlayer].addCardToDeck(card);
                i++;
            }
        }
    }

    private void setPlayersInitialHand() {
        for (Player p : players) {
            p.setInitialHand();
        }
    }

    private Player winner() {
        for (Player player : players) {
            if (player.getHand()[0].getValue() == player.getHand()[1].getValue()
                    && player.getHand()[0].getValue() == player.getHand()[2].getValue()
                    && player.getHand()[0].getValue() == player.getHand()[3].getValue()) {
                isWinner = true;
                return player;
            }
        }
        return null;
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
    }
}
