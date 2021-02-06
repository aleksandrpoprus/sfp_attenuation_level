package main.classes;

import java.util.ArrayList;

public class DSP_SFP {

    private final ArrayList<SFP> arrSFP = new ArrayList<>();

    public ArrayList<SFP> getArrSFP() {
        return arrSFP;
    }

    public void setSFP(SFP sfp) {
        arrSFP.add(sfp);
    }

}
