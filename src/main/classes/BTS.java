package main.classes;

public abstract class BTS {

    final String btsName;
    final int subRack;
    final int slot;
    int chain;
    final int port0;
    final int port1;
    final int tx;
    final int rx;
    final String sfpManufacturerName;
    final int sfpSpeed;
    final String sfpMode;
    final int sfpWaveLength;

    public BTS(String btsName,
               int subRack,
               int slot,
               int chain,
               int port0,
               int port1,
               int tx,
               int rx,
               String sfpManufacturerName,
               int sfpSpeed,
               String sfpMode,
               int sfpWaveLength) {
        this.btsName = btsName;
        this.subRack = subRack;
        this.slot = slot;
        this.port0 = port0;
        this.port1 = port1;
        this.chain = chain;
        this.tx = tx;
        this.rx = rx;
        this.sfpManufacturerName = sfpManufacturerName;
        this.sfpSpeed = sfpSpeed;
        this.sfpMode = sfpMode;
        this.sfpWaveLength = sfpWaveLength;
    }

    public abstract String getBtsName();

    public abstract int getSubRack();

    public abstract int getSlot();

    public abstract int getChain();

    public abstract int getPort0();

    public abstract int getPort1();

    public abstract int getTx();

    public abstract int getRx();

    public abstract String getSfpManufacturerName();

    public abstract int getSfpSpeed();

    public abstract String getSfpMode();

    public abstract int getSfpWaveLength();

    public abstract String display();

}
