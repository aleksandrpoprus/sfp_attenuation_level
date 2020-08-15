package main.classes;

public class BBU_Port extends BTS {

    private final int SubRack;
    private final int Slot;
    private final int Port;
    private final int Tx;
    private final int Rx;
    private final String SfpManufacturerName;
    private final int Sfp_Speed;
    private final String Sfp_Mode;
    private final int Sfp_Wave_Length;

    public BBU_Port(String BTS_name,
                    int SubRack,
                    int Slot,
                    int Port,
                    int Tx,
                    int Rx,
                    String SfpManufacturerName,
                    int Sfp_Speed,
                    String Sfp_Mode,
                    int Sfp_Wave_length) {
        super(BTS_name);
        this.SubRack = SubRack;
        this.Slot = Slot;
        this.Port = Port;
        this.Tx = Tx;
        this.Rx = Rx;
        this.SfpManufacturerName = SfpManufacturerName;
        this.Sfp_Speed = Sfp_Speed;
        this.Sfp_Mode = Sfp_Mode;
        this.Sfp_Wave_Length = Sfp_Wave_length;
    }

    public int getSubRack() {
        return SubRack;
    }

    public int getSlot() {
        return Slot;
    }

    public int getPort() {
        return Port;
    }

    public int getTx() {
        return Tx;
    }

    public int getRx() {
        return Rx;
    }

    public String getSfpManufacturerName() {
        return SfpManufacturerName;
    }

    public int getSfp_Speed() {
        return Sfp_Speed;
    }

    public String getSfp_Mode() {
        return Sfp_Mode;
    }

    public int getSfp_Wave_Length() {
        return Sfp_Wave_Length;
    }

    @Override
    public String display() {
        return "BBU_Port{" +
                "SubRack_BBU=" + SubRack +
                ", Slot_BBU=" + Slot +
                ", Port_BBU=" + Port +
                ", Tx_BBU=" + Tx +
                ", Rx_BBU=" + Rx +
                ", Sfp_BBU='" + SfpManufacturerName + '\'' +
                ", Sfp_BBU_Speed=" + Sfp_Speed +
                ", Sfp_Mode='" + Sfp_Mode + '\'' +
                ", Sfp_Wave_Length=" + Sfp_Wave_Length +
                '}';
    }
}
