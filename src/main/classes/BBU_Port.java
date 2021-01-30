package main.classes;

public class BBU_Port extends BTS {

    int port0;
    int tx0;
    int rx0;
    String sfpManufacturerName0;
    int sfpSpeed0;
    String sfpMode0;
    int sfpWaveLength0;

    public BBU_Port(String btsName, int subRack, int slot, int port0, int tx0, int rx0, String sfpManufacturerName0, int sfpSpeed0, String sfpMode0, int sfpWaveLength0) {
        super(btsName, subRack, slot);
        this.port0 = port0;
        this.tx0 = tx0;
        this.rx0 = rx0;
        this.sfpManufacturerName0 = sfpManufacturerName0;
        this.sfpSpeed0 = sfpSpeed0;
        this.sfpMode0 = sfpMode0;
        this.sfpWaveLength0 = sfpWaveLength0;
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

    public int getPort0() {
        return port0;
    }

    public int getTx0() {
        return tx0;
    }

    public int getRx0() {
        return rx0;
    }

    public String getSfpManufacturerName0() {
        return sfpManufacturerName0;
    }

    public int getSfpSpeed0() {
        return sfpSpeed0;
    }

    public String getSfpMode0() {
        return sfpMode0;
    }

    public int getSfpWaveLength0() {
        return sfpWaveLength0;
    }

    @Override
    public String display() {
        return "BBU_Port{" +
                "btsName='" + btsName + '\'' +
                ", subRack=" + subRack +
                ", slot=" + slot +
                ", port0=" + port0 +
                ", tx0=" + tx0 +
                ", rx0=" + rx0 +
                ", sfpManufacturerName0='" + sfpManufacturerName0 + '\'' +
                ", sfpSpeed0=" + sfpSpeed0 +
                ", sfpMode0='" + sfpMode0 + '\'' +
                ", sfpWaveLength0=" + sfpWaveLength0 +
                '}';
    }
}
