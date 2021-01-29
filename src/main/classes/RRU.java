package main.classes;

public class RRU extends BTS {

    public RRU(String btsName, int subRack, int slot, int chain, int port0, int port1, int tx, int rx, String sfpManufacturerName, int sfpSpeed, String sfpMode, int sfpWaveLength) {
        super(btsName, subRack, slot, chain, port0, port1, tx, rx, sfpManufacturerName, sfpSpeed, sfpMode, sfpWaveLength);
    }

    @Override
    public String getBtsName() {
        return btsName;
    }

    @Override
    public int getSubRack() {
        return subRack;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public int getChain() {
        return chain;
    }

    @Override
    public int getPort0() {
        return port0;
    }

    @Override
    public int getPort1() {
        return port1;
    }

    @Override
    public int getTx() {
        return tx;
    }

    @Override
    public int getRx() {
        return rx;
    }

    @Override
    public String getSfpManufacturerName() {
        return sfpManufacturerName;
    }

    @Override
    public int getSfpSpeed() {
        return sfpSpeed;
    }

    @Override
    public String getSfpMode() {
        return sfpMode;
    }

    @Override
    public int getSfpWaveLength() {
        return sfpWaveLength;
    }

    @Override
    public String display() {
        return "RRU{" +
                "btsName='" + btsName + '\'' +
                ", subRack=" + subRack +
                ", slot=" + slot +
                ", port0=" + port0 +
                ", port1=" + port1 +
                ", chain=" + chain +
                ", tx=" + tx +
                ", rx=" + rx +
                ", sfpManufacturerName='" + sfpManufacturerName + '\'' +
                ", sfpSpeed=" + sfpSpeed +
                ", sfpMode='" + sfpMode + '\'' +
                ", sfpWaveLength=" + sfpWaveLength +
                '}';
    }
}
