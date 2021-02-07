package main.classes;

import java.util.ArrayList;

public class LST_RRU {

    private final ArrayList<RRUs> arrRRUs = new ArrayList<>();

    public ArrayList<RRUs> getArrRRUs() {
        return arrRRUs;
    }

    public void getArrRRUs(RRUs rrus) {
        arrRRUs.add(rrus);
    }
}
