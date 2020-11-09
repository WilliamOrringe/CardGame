package com.cards.fourofakind.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Pack {
    private final Card[] pack;

    /**
     * Constructs an instance of the object containing filename and numberOfPlayer arguments.
     *
     * @param filename                      name of text file
     * @param numberOfPlayer                number of players
     */
    public Pack (String filename, int numberOfPlayer){
        this.pack = new Card[8 * numberOfPlayer];
        readPack(filename);
//        shufflePack();
    }

    /**
     * Method used to reading the cards from a text file.
     *
     * @param filename          name of text file
     */
    private void readPack(String filename) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            for (int line = 0; line < this.pack.length; line++) {
                try {
                    this.pack[line] = new Card(Integer.parseInt(bufferedReader.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to shuffle the pack of Card objects.
     */
    private void shufflePack() {
        Collections.shuffle(Arrays.asList(this.pack));
    }

    /**
     * Method used to the get the pack of cards.
     *
     * @return          array of Card objects
     */
    public Card[] getPack() {
        return this.pack;
    }
}
