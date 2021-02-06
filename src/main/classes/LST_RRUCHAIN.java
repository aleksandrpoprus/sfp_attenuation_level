package main.classes;

import java.util.ArrayList;

public class LST_RRUCHAIN {

    private final ArrayList<Chain> arrChain = new ArrayList<>();

    public ArrayList<Chain> getArrChain() {
        return arrChain;
    }

    public void getArrChain(Chain chain) {
        arrChain.add(chain);
    }
}
