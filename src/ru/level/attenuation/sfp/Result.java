package ru.level.attenuation.sfp;

public class Result {

    private String bts_name;
    private int subRack_rru;
    private int slot_bbu;
    private int port_bbu;
    private double tx_bbu;
    private double rx_rru;
    private double rx_bbu;
    private double tx_rru;

    private String sfp_BBU;
    private double sfp_BBU_Speed;
    private String sfp_RRU;
    private double sfp_RRU_Speed;

    private String sfp_BBU_Mode;
    private String sfp_RRU_Mode;

    public void setForResult(String bts_name,
                             int slot_bbu,
                             int port_bbu,
                             double tx_bbu,
                             double rx_bbu,
                             int subRack_rru,
                             double tx_rru,
                             double rx_rru,
                             String sfp_BBU,
                             double sfp_BBU_Speed,
                             String sfp_RRU,
                             double sfp_RRU_Speed,
                             String sfp_BBU_Mode,
                             String sfp_RRU_Mode) {

        this.bts_name = bts_name;
        this.subRack_rru = subRack_rru;
        this.slot_bbu = slot_bbu;
        this.port_bbu = port_bbu;
        this.tx_bbu = tx_bbu;
        this.rx_rru = rx_rru;
        this.rx_bbu = rx_bbu;
        this.tx_rru = tx_rru;

        this.sfp_BBU = sfp_BBU;
        this.sfp_BBU_Speed = sfp_BBU_Speed;
        this.sfp_RRU = sfp_RRU;
        this.sfp_RRU_Speed = sfp_RRU_Speed;

        this.sfp_BBU_Mode = sfp_BBU_Mode;
        this.sfp_RRU_Mode = sfp_RRU_Mode;
    }

    public String getBts_name() {
        return bts_name;
    }

    public String getResult() {

        double difference1 = tx_bbu - rx_rru;
        double difference2 = rx_bbu - tx_rru;

        String warningRed = "<samp id=\"triangle-up-red\"></samp>";
        String warningYellow = "<samp id=\"triangle-up-yellow\"></samp>";

        String s1, s2, s3;
        if (difference1 > 4 || difference1 < -4) {
            difference1 = (difference1 < 0) ? difference1 * -1 : difference1;
            s1 = String.format("<p>" +
                            "BBU(%d %d) " +
                            "Tx= %.2f dbm --" +
                            "(<samp1 data-tooltip=\"Превышение уровня затухания! %.2f > 4 dbm\">%.2f</samp1> dbm)--> " +
                            "Rx= %.2f dbm " +
                            "RRU (%d) " +
                            "%s" +
                            "</p>\n",
                    slot_bbu, port_bbu,
                    tx_bbu,
                    difference1, difference1,
                    rx_rru,
                    subRack_rru,
                    warningRed);
        } else {
            difference1 = (difference1 < 0) ? difference1 * -1 : difference1;
            s1 = String.format("<p>" +
                            "BBU(%d %d) " +
                            "Tx= %.2f dbm --" +
                            "(<samp0>%.2f</samp0> dbm)--> " +
                            "Rx= %.2f dbm " +
                            "RRU (%d)" +
                            "</p>\n",
                    slot_bbu, port_bbu,
                    tx_bbu,
                    difference1,
                    rx_rru,
                    subRack_rru);
        }

        if (difference2 > 4 || difference2 < -4) {
            difference2 = (difference2 < 0) ? difference2 * -1 : difference2;
            s2 = String.format("<p>" +
                            "BBU(%d %d) " +
                            "Rx= %.2f dbm <--" +
                            "(<samp1 data-tooltip=\"Превышение уровня затухания! %.2f > 4 dbm\">%.2f</samp1> dbm)-- " +
                            "Tx= %.2f dbm " +
                            "RRU (%d) " +
                            "%s" +
                            "</p>\n",
                    slot_bbu, port_bbu,
                    rx_bbu,
                    difference2, difference2,
                    tx_rru,
                    subRack_rru,
                    warningRed);
        } else {
            difference2 = (difference2 < 0) ? difference2 * -1 : difference2;
            s2 = String.format("<p>" +
                            "BBU(%d %d) " +
                            "Rx= %.2f dbm <--" +
                            "(<samp0>%.2f</samp0> dbm)-- " +
                            "Tx= %.2f dbm " +
                            "RRU (%d)" +
                            "</p>\n",
                    slot_bbu, port_bbu,
                    rx_bbu,
                    difference2,
                    tx_rru,
                    subRack_rru);
        }

        String SM = "<samp data-tooltip=\"SINGLEMODE\">SM</samp>";
        String MM = "<samp data-tooltip=\"MULTIMODE\">MM</samp>";

        if(sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(SM))) {
            s3 = String.format("<p>" +
                            "BBU(%d %d) - " +
                            "<samp data-tooltip=\"Manufacturer name\">MF: %s</samp>; " +
                            "<samp data-tooltip=\"Transmission code rate\">TCR</samp>: " +
                            "<samp data-tooltip=\"В RRU sfp c инным TCR\"><samp1>%.1f</samp1></samp> Gbit/s; " +
                            "mode: " +
                            "<samp2>%s</samp2>. " +
                            "%s</p>\n" +
                            "<p>RRU(%d) - " +
                            "<samp data-tooltip=\"Manufacturer name\">MF: %s</samp>; " +
                            "<samp data-tooltip=\"Transmission code rate\">TCR</samp>: " +
                            "<samp data-tooltip=\"В BBU sfp c инным TCR\"><samp1>%.1f</samp1></samp> Gbit/s; " +
                            "mode: " +
                            "<samp2>%s</samp2>. " +
                            "%s" +
                            "</p>",
                    slot_bbu, port_bbu,
                    sfp_BBU,
                    sfp_BBU_Speed / 10,
                    sfp_BBU_Mode,
                    warningRed,
                    subRack_rru,
                    sfp_RRU,
                    sfp_RRU_Speed / 10,
                    sfp_RRU_Mode,
                    warningRed);
        } else if(sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(MM) || sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(SM))){
            s3 = String.format("<p>" +
                            "BBU(%d %d) - " +
                            "<samp data-tooltip=\"Manufacturer name\">MF: %s</samp>; " +
                            "<samp data-tooltip=\"Transmission code rate\">TCR</samp>: " +
                            "<samp data-tooltip=\"Разные TCR и MODE(требуется уточнение)\"><samp1>%.1f</samp1></samp> Gbit/s; " +
                            "mode: " +
                            "<samp2>%s</samp2>. " +
                            "%s</p>\n" +
                            "<p>RRU(%d) - " +
                            "<samp data-tooltip=\"Manufacturer name\">MF: %s</samp>; " +
                            "<samp data-tooltip=\"Transmission code rate\">TCR</samp>: " +
                            "<samp data-tooltip=\"Разные TCR и MODE(требуется уточнение)\"><samp1>%.1f</samp1></samp> Gbit/s; " +
                            "mode: " +
                            "<samp2>%s</samp2>. " +
                            "%s" +
                            "</p>",
                    slot_bbu, port_bbu,
                    sfp_BBU,
                    sfp_BBU_Speed / 10,
                    sfp_BBU_Mode,
                    warningYellow,
                    subRack_rru,
                    sfp_RRU,
                    sfp_RRU_Speed / 10,
                    sfp_RRU_Mode,
                    warningYellow);
        } else {
            s3 = String.format("<p>BBU(%d %d) - " +
                            "<samp data-tooltip=\"Manufacturer name\">MF: %s</samp>;" +
                            "<samp data-tooltip=\"Transmission code rate\">TCR</samp>: " +
                            "%.1f Gbit/s; " +
                            "<samp2>%s</samp2>" +
                            ".</p>\n" +
                            "<p>RRU(%d) - " +
                            "<samp data-tooltip=\"Manufacturer name\">MF: %s</samp>;" +
                            "<samp data-tooltip=\"Transmission code rate\">TCR</samp>: " +
                            "%.1f Gbit/s; " +
                            "<samp2>%s</samp2>" +
                            ".</p>",
                    slot_bbu, port_bbu,
                    sfp_BBU,
                    sfp_BBU_Speed / 10,
                    sfp_BBU_Mode,
                    subRack_rru,
                    sfp_RRU,
                    sfp_RRU_Speed / 10,
                    sfp_RRU_Mode);
        }
        return  s1 + s2 + s3;
    }

    @Override
    public String toString() {
        return "Result{" +
                "bts_name='" + bts_name + '\'' +
                ", subRack_rru=" + subRack_rru +
                ", slot_bbu=" + slot_bbu +
                ", port_bbu=" + port_bbu +
                ", tx_bbu=" + tx_bbu +
                ", rx_rru=" + rx_rru +
                ", rx_bbu=" + rx_bbu +
                ", tx_rru=" + tx_rru +
                '}';
    }
}
