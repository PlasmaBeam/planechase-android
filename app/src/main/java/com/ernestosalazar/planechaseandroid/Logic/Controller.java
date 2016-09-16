package com.ernestosalazar.planechaseandroid.Logic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by esalazar on 9/16/16.
 */
public class Controller {

    private ArrayList<Integer> mDeck;

    public Controller() {
        mDeck = new ArrayList<>();
        for (int i = 1; i < 83; i++) {
            mDeck.add(i);
        }
    }

    public int getFirstCard() {
        return mDeck.get(0);
    }

    public int nextCard() {
        int value;
        value = mDeck.remove(0);
        mDeck.add(value);
        return getFirstCard();
    }

    public int previousCard() {
        int value;
        value = mDeck.remove(mDeck.size() - 1);
        mDeck.add(0, value);
        return getFirstCard();
    }

    public void shuffleDeck() {
        Collections.shuffle(mDeck);
    }
}
