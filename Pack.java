package com.cards.fourofakind;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Pack {
    private final Card[] pack;

    public Pack (String filename, int numberOfPlayer){
        this.pack = new Card[8 * numberOfPlayer];

        readPack(filename);
//        shufflePack();
    }

    private void readPack(String filename) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            for (int i = 0; i < this.pack.length; i++) {
                try {
                    this.pack[i] = new Card(Integer.parseInt(bufferedReader.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void shufflePack() {
        Collections.shuffle(Arrays.asList(this.pack));
    }

    public Card[] getPack() {
        return this.pack;
    }
}
