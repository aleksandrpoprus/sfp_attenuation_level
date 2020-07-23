package main.classes;

public class RRU extends BTS {

    private final int SubRack_RRU;
    private final int Slot_RRU;
    private final int Port_RRU;
    private final int Tx_RRU;
    private final int Rx_RRU;
    private final String Sfp_RRU;
    private final double Sfp_RRU_Speed;
    private final String Sfp_Mode;

    public RRU(String BTS_name,
               int SubRack_RRU,
               int Slot_RRU,
               int Port_RRU,
               int Tx_RRU,
               int Rx_RRU,
               String Sfp_RRU,
               double Sfp_RRU_Speed,
               String Sfp_Mode) {
        super(BTS_name);
        this.SubRack_RRU = SubRack_RRU;
        this.Slot_RRU = Slot_RRU;
        this.Port_RRU = Port_RRU;
        this.Tx_RRU = Tx_RRU;
        this.Rx_RRU = Rx_RRU;
        this.Sfp_RRU = Sfp_RRU;
        this.Sfp_RRU_Speed = Sfp_RRU_Speed;
        this.Sfp_Mode = Sfp_Mode;
    }

    public int getSubRack_RRU() {
        return SubRack_RRU;
    }

    public int getSlot_RRU() {
        return Slot_RRU;
    }

    public int getPort_RRU() {
        return Port_RRU;
    }

    public int getTx_RRU() {
        return Tx_RRU;
    }

    public int getRx_RRU() {
        return Rx_RRU;
    }

    public String getSfp_RRU() {
        return Sfp_RRU;
    }

    public double getSfp_RRU_Speed() {
        return Sfp_RRU_Speed;
    }

    public String getSfp_Mode() {
        return Sfp_Mode;
    }

    @Override
    public String display() {
        return "RRU{" +
                "SubRack_RRU=" + SubRack_RRU +
                ", Slot_RRU=" + Slot_RRU +
                ", Port_RRU=" + Port_RRU +
                ", Tx_RRU=" + Tx_RRU +
                ", Rx_RRU=" + Rx_RRU +
                ", Sfp_RRU='" + Sfp_RRU + '\'' +
                ", Sfp_RRU_Speed=" + Sfp_RRU_Speed +
                ", Sfp_Mode='" + Sfp_Mode + '\'' +
                '}';
    }
}
