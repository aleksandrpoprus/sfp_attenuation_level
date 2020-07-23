package main.classes;

import java.util.ArrayList;

public class LST_RRUCHAIN {

    private final ArrayList<RRU> arrRRU = new ArrayList<>();

    public ArrayList<RRU> getArrRRU() {
        return arrRRU;
    }

    public void getArrRRU(RRU rru) {
        arrRRU.add(rru);
    }
}
