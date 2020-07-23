package main.classes;

import java.util.ArrayList;

public class DSP_SFP {

    private final ArrayList<BBU_Port> arrBBU_port = new ArrayList<>();
    private final ArrayList<RRU> arrRRU = new ArrayList<>();

    public ArrayList<BBU_Port> getArrBBU_port() {
        return arrBBU_port;
    }

    public ArrayList<RRU> getArrRRU() {
        return arrRRU;
    }

    public void setBBU_port(BBU_Port bbuPorts) {
        arrBBU_port.add(bbuPorts);
    }

    public void setRRU(RRU rru) {
        arrRRU.add(rru);
    }
}
