package main.classes;

public class SFP extends BTS {

    private final String sideSubRack;
    private final Integer port;
    private final double tx;
    private final double rx;
    private final String sfpManufacturerName;
    private final double sfpSpeed;
    private final String sfpMode;
    private final Integer sfpWaveLength;

    public SFP(String btsName, String sideSubRack, Integer subRack, Integer slot, Integer port, double tx, double rx, String sfpManufacturerName, double sfpSpeed, String sfpMode, Integer sfpWaveLength) {
        super(btsName, subRack, slot);
        this.sideSubRack = sideSubRack;
        this.port = port;
        this.tx = tx;
        this.rx = rx;
        this.sfpManufacturerName = sfpManufacturerName;
        this.sfpSpeed = sfpSpeed;
        this.sfpMode = sfpMode;
        this.sfpWaveLength = sfpWaveLength;
    }

    @Override
    public String getBtsName() {
        return btsName;
    }

    @Override
    public Integer getSubRack() {
        return subRack;
    }

    @Override
    public Integer getSlot() {
        return slot;
    }

    public String getSideSubRack() {
        return sideSubRack;
    }

    public Integer getPort() {
        return port;
    }

    public double getTx() {
        return tx;
    }

    public double getRx() {
        return rx;
    }

    public String getSfpManufacturerName() {
        return sfpManufacturerName;
    }

    public double getSfpSpeed() {
        return sfpSpeed;
    }

    public String getSfpMode() {
        return sfpMode;
    }

    public Integer getSfpWaveLength() {
        return sfpWaveLength;
    }

    @Override
    public String display() {
        return "SFP{" +
                "btsName='" + btsName + '\'' +
                ", sideSubRack=" + sideSubRack +
                ", subRack=" + subRack +
                ", slot=" + slot +
                ", port=" + port +
                ", tx=" + tx +
                ", rx=" + rx +
                ", sfpManufacturerName='" + sfpManufacturerName + '\'' +
                ", sfpSpeed=" + sfpSpeed +
                ", sfpMode='" + sfpMode + '\'' +
                ", sfpWaveLength=" + sfpWaveLength +
                '}';
    }
}
