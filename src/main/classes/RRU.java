package main.classes;

public class RRU extends BTS {

    int port0;
    int tx0;
    int rx0;
    String sfpManufacturerName0;
    int sfpSpeed0;
    String sfpMode0;
    int sfpWaveLength0;

    int port1;
    int tx1;
    int rx1;
    String sfpManufacturerName1;
    int sfpSpeed1;
    String sfpMode1;
    int sfpWaveLength1;

    public RRU(String btsName, int subRack, int slot,

               int port0,
               int tx0,
               int rx0,
               String sfpManufacturerName0,
               int sfpSpeed0,
               String sfpMode0,
               int sfpWaveLength0,

               int port1,
               int tx1,
               int rx1,
               String sfpManufacturerName1,
               int sfpSpeed1,
               String sfpMode1,
               int sfpWaveLength1)
    {
        super(btsName, subRack, slot);
        this.port0 = port0;
        this.tx0 = tx0;
        this.rx0 = rx0;
        this.sfpManufacturerName0 = sfpManufacturerName0;
        this.sfpSpeed0 = sfpSpeed0;
        this.sfpMode0 = sfpMode0;
        this.sfpWaveLength0 = sfpWaveLength0;
        this.port1 = port1;
        this.tx1 = tx1;
        this.rx1 = rx1;
        this.sfpManufacturerName1 = sfpManufacturerName1;
        this.sfpSpeed1 = sfpSpeed1;
        this.sfpMode1 = sfpMode1;
        this.sfpWaveLength1 = sfpWaveLength1;
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

    public int getPort1() {
        return port1;
    }

    public int getTx1() {
        return tx1;
    }

    public int getRx1() {
        return rx1;
    }

    public String getSfpManufacturerName1() {
        return sfpManufacturerName1;
    }

    public int getSfpSpeed1() {
        return sfpSpeed1;
    }

    public String getSfpMode1() {
        return sfpMode1;
    }

    public int getSfpWaveLength1() {
        return sfpWaveLength1;
    }

    public void setSubRack(int subRack) {
        super.subRack = subRack;
    }

    @Override
    public String display() {
        return "RRU{" +
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
                ", port1=" + port1 +
                ", tx1=" + tx1 +
                ", rx1=" + rx1 +
                ", sfpManufacturerName1='" + sfpManufacturerName1 + '\'' +
                ", sfpSpeed1=" + sfpSpeed1 +
                ", sfpMode1='" + sfpMode1 + '\'' +
                ", sfpWaveLength1=" + sfpWaveLength1 +
                '}';
    }

}
