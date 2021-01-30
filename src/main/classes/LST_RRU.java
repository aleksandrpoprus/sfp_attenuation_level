package main.classes;

import java.util.ArrayList;

public class LST_RRU {

    private final ArrayList<Chains> arrChains = new ArrayList<>();

    public ArrayList<Chains> getArrChains() {
        return arrChains;
    }

    public void getArrChains(Chains chains) {
        arrChains.add(chains);
    }
}
