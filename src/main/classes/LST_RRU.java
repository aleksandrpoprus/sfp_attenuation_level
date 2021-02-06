package main.classes;

import java.util.ArrayList;

public class LST_RRU {

    private final ArrayList<RRUs> arrChains = new ArrayList<>();

    public ArrayList<RRUs> getArrChains() {
        return arrChains;
    }

    public void getArrChains(RRUs RRUs) {
        arrChains.add(RRUs);
    }
}
