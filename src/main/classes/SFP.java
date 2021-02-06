package main.classes;

public class SFP extends BTS {

    private String sideSubRack;
    private Integer port;
    private double tx;
    private double rx;
    private String sfpManufacturerName;
    private double sfpSpeed;
    private String sfpMode;
    private Integer sfpWaveLength;

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

    public void setSideSubRack(String sideSubRack) {
        this.sideSubRack = sideSubRack;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public double getTx() {
        return tx;
    }

    public void setTx(double tx) {
        this.tx = tx;
    }

    public double getRx() {
        return rx;
    }

    public void setRx(double rx) {
        this.rx = rx;
    }

    public String getSfpManufacturerName() {
        return sfpManufacturerName;
    }

    public void setSfpManufacturerName(String sfpManufacturerName) {
        this.sfpManufacturerName = sfpManufacturerName;
    }

    public double getSfpSpeed() {
        return sfpSpeed;
    }

    public void setSfpSpeed(double sfpSpeed) {
        this.sfpSpeed = sfpSpeed;
    }

    public String getSfpMode() {
        return sfpMode;
    }

    public void setSfpMode(String sfpMode) {
        this.sfpMode = sfpMode;
    }

    public Integer getSfpWaveLength() {
        return sfpWaveLength;
    }

    public void setSfpWaveLength(Integer sfpWaveLength) {
        this.sfpWaveLength = sfpWaveLength;
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
