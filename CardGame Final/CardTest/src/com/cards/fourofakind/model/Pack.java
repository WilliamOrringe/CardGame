package com.cards.fourofakind.model;

import com.cards.fourofakind.exception.IllegalFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pack {
    private final Card[] cards;

    /**
     * Constructs an instance of the object containing filename and numberOfPlayer arguments.
     *
     * @param filename                      name of text file
     * @param numberOfPlayer                number of players
     */
    public Pack (String filename, int numberOfPlayer) throws IllegalFileException {
        //the size is always 8 * number of players
        this.cards = new Card[8 * numberOfPlayer];
        readPack(filename);
    }

    /**
     * Method used to reading the cards from a text file.
     *
     * @param filename          name of text file
     */
    private void readPack(String filename) throws IllegalFileException {
        try {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
                //reading all the lines in the file
                for (int line = 0; line < this.cards.length; line++) {
                    String content = bufferedReader.readLine();
                    if (content == null) {
                        throw new IllegalFileException("Illegal File: The file needs to contain (8 * number of player) " +
                                "lines.");
                    } else if (content.equals("")) {
                        throw new IllegalFileException("Illegal File: One of the line in the file is empty.");
                    }
                    //adding to line to the array
                    this.cards[line] = new Card(Integer.parseInt(content));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to the get the pack of cards.
     *
     * @return          array of Card objects
     */
    public Card[] getCards() {
        return this.cards;
    }
}
