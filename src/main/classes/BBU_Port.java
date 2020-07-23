package main.classes;

public class BBU_Port extends BTS {

    private final int SubRack_BBU;
    private final int Slot_BBU;
    private final int Port_BBU;
    private final int Tx_BBU;
    private final int Rx_BBU;
    private final String Sfp_BBU;
    private final double Sfp_BBU_Speed;
    private final String Sfp_Mode;

    public BBU_Port(String BTS_name,
                    int SubRack_BBU,
                    int Slot_BBU,
                    int Port_BBU,
                    int Tx_BBU,
                    int Rx_BBU,
                    String Sfp_BBU,
                    double Sfp_BBU_Speed,
                    String Sfp_Mode) {
        super(BTS_name);
        this.SubRack_BBU = SubRack_BBU;
        this.Slot_BBU = Slot_BBU;
        this.Port_BBU = Port_BBU;
        this.Tx_BBU = Tx_BBU;
        this.Rx_BBU = Rx_BBU;
        this.Sfp_BBU = Sfp_BBU;
        this.Sfp_BBU_Speed = Sfp_BBU_Speed;
        this.Sfp_Mode = Sfp_Mode;
    }

    public int getSubRack_BBU() {
        return SubRack_BBU;
    }

    public int getSlot_BBU() {
        return Slot_BBU;
    }

    public int getPort_BBU() {
        return Port_BBU;
    }

    public int getTx_BBU() {
        return Tx_BBU;
    }

    public int getRx_BBU() {
        return Rx_BBU;
    }

    public String getSfp_BBU() {
        return Sfp_BBU;
    }

    public double getSfp_BBU_Speed() {
        return Sfp_BBU_Speed;
    }

    public String getSfp_Mode() {
        return Sfp_Mode;
    }

    @Override
    public String display() {
        return "BBU_Port{" +
                "SubRack_BBU=" + SubRack_BBU +
                ", Slot_BBU=" + Slot_BBU +
                ", Port_BBU=" + Port_BBU +
                ", Tx_BBU=" + Tx_BBU +
                ", Rx_BBU=" + Rx_BBU +
                ", Sfp_BBU='" + Sfp_BBU + '\'' +
                ", Sfp_BBU_Speed=" + Sfp_BBU_Speed +
                ", Sfp_Mode='" + Sfp_Mode + '\'' +
                '}';
    }
}
