package com.cards.fourofakind.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Pack {
    private final Card[] pack;

    /**
     * Constructs an instance of the object containing filename and numberOfPlayer arguments.
     *
     * @param filename                      name of text file
     * @param numberOfPlayer                number of players
     */
    public Pack (String filename, int numberOfPlayer){
        //the size is always 8 * number of players
        this.pack = new Card[8 * numberOfPlayer];
        readPack(filename);
    }

    /**
     * Method used to reading the cards from a text file.
     *
     * @param filename          name of text file
     */
    private void readPack(String filename) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            //reading all the lines in the file
            for (int line = 0; line < this.pack.length; line++) {
                try {
                    //adding to line to the array
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
     * Method used to the get the pack of cards.
     *
     * @return          array of Card objects
     */
    public Card[] getPack() {
        return this.pack;
    }
}
