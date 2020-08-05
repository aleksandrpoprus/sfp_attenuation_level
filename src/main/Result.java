package main;

public class Result {

    private String bts_name;
    private int subRack_rru;
    private int slot_bbu;
    private int port_bbu;
    private double tx_bbu;
    private double rx_rru;
    private double rx_bbu;
    private double tx_rru;

    private double difference1;
    private double difference2;

    private String sfp_BBU;
    private double sfp_BBU_Speed;
    private String sfp_RRU;
    private double sfp_RRU_Speed;

    private String sfp_BBU_Mode;
    private String sfp_RRU_Mode;

    private String warningRed;
    private String warningYellow;

    private String TCR;

    private String SM, MM, NULL;

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

        difference1 = tx_bbu - rx_rru;
        difference2 = rx_bbu - tx_rru;

        warningRed = "<samp id=\"triangle-up-red\"></samp>";
        warningYellow = "<samp id=\"triangle-up-yellow\"></samp>";

        TCR = "<samp data-tooltip=\"Transmission code rate\">TCR</samp>: ";

        SM = "<samp data-tooltip=\"SINGLEMODE\">SM</samp>";
        MM = "<samp data-tooltip=\"MULTIMODE\">MM</samp>";
        NULL = "<samp data-tooltip=\"NULL\">NULL</samp>";

        String s1, s2, s3;

        if (difference1 > 4 || difference1 < -4) {
            s1 = "<p>" + sfp_attenuation(true, true) + "</p>\n";
        } else {
            s1 = "<p>" + sfp_attenuation(true, false) + "</p>\n";
        }

        if (difference2 > 4 || difference2 < -4) {
            s2 = "<p>" + sfp_attenuation(false, true) + "</p>\n";
        } else {
            s2 = "<p>" + sfp_attenuation(false, false) + "</p>\n";
        }

        if (sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(SM))) {
            s3 = "<p>" + sfp_info(true, false, false) + "</p>\n";
        } else if (sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(MM) ||
                sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(SM))) {
            s3 = "<p>" + sfp_info(false, true, false) + "</p>\n";

        } else if (sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(NULL) ||
                sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(NULL))) {
            s3 = "<p>" + sfp_info(false, false, true) + "</p>\n";
        } else {
            s3 = "<p>" + sfp_info(false, false, false) + "</p>\n";
        }
        return s1 + "\n" + s2 + "\n" + s3 + "\n";
    }

    private String sfp_attenuation(boolean b, boolean wr) {

        String s1, TRX1, TRX2, warning;

        if (b) {
            TRX1 = String.format("Tx= %.2f dbm --", tx_bbu);
            TRX2 = String.format("--> Rx= %.2f dbm ", rx_rru);
        } else {
            TRX1 = String.format("RX= %.2f dbm <--", rx_bbu);
            TRX2 = String.format("-- TX= %.2f dbm ", tx_rru);
        }

        if (wr) {
            warning = warningRed;
        } else {
            warning = "";
        }

        s1 =
                "BBU(" + slot_bbu + ", " + port_bbu + ") " +
                        TRX1 +
                        sampSelector(b, wr) +
                        TRX2 +
                        "RRU (" + subRack_rru + ") " +
                        warning;
        return s1;
    }

    private String sfp_info(boolean b0, boolean b1, boolean b2) {

        String s0, s1, mode0, mode1, warning0, warning1;

        if (b0) {
            s0 = String.format("<samp data-tooltip=\"В RRU sfp c инным TCR\"><samp1>%.1f</samp1></samp> Gbit/s; ", sfp_BBU_Speed / 10);
            s1 = String.format("<samp data-tooltip=\"В RRU sfp c инным TCR\"><samp1>%.1f</samp1></samp> Gbit/s; ", sfp_RRU_Speed / 10);
            mode0 = String.format("<samp2>%s</samp2>. ", sfp_BBU_Mode);
            mode1 = String.format("<samp2>%s</samp2>. ", sfp_RRU_Mode);
            warning0 = warningRed;
            warning1 = warningRed;
        } else if (b1) {
            s0 = String.format("<samp data-tooltip=\"Разные TCR и MODE(требуется уточнение)\"><samp1>%.1f</samp1></samp> Gbit/s; ", sfp_BBU_Speed / 10);
            s1 = String.format("<samp data-tooltip=\"Разные TCR и MODE(требуется уточнение)\"><samp1>%.1f</samp1></samp> Gbit/s; ", sfp_RRU_Speed / 10);
            mode0 = String.format("<samp2>%s</samp2>. ", sfp_BBU_Mode);
            mode1 = String.format("<samp2>%s</samp2>. ", sfp_RRU_Mode);
            warning0 = warningYellow;
            warning1 = warningYellow;
        } else if (b2) {
            s0 = String.format("<samp data-tooltip=\"Разные TCR и MODE(требуется уточнение)\"><samp1>%.1f</samp1></samp> Gbit/s; ", sfp_BBU_Speed / 10);
            s1 = String.format("<samp data-tooltip=\"Питание RRU отключено или обрыв оптики!\"><samp1>%s</samp1></samp> Gbit/s; ", NULL);
            mode0 = String.format("<samp2>%s</samp2>. ", sfp_BBU_Mode);
            mode1 = String.format("<samp2>%s</samp2>. ", sfp_RRU_Mode);
            warning0 = warningYellow;
            warning1 = warningRed;
        } else {

            s0 = String.format("<samp0>%.1f</samp0> Gbit/s; ",sfp_BBU_Speed / 10);
            s1 = String.format("<samp0>%.1f</samp0> Gbit/s; ",sfp_RRU_Speed / 10);
            mode0 = String.format("<samp2>%s</samp2>. ", sfp_BBU_Mode);
            mode1 = String.format("<samp2>%s</samp2>. ", sfp_RRU_Mode);
            warning0 = "";
            warning1 = "";
        }

        String BBU =
                "BBU(" + slot_bbu + ", " + port_bbu + ") " +
                        "<samp data-tooltip=\"Manufacturer name\">MF: " + sfp_BBU + "</samp>; " +
                        TCR +
                        s0 +
                        "mode: " +
                        mode0 +
                        warning0 +
                        "</p>\n";
        String RRU =
                "<p>" +
                        "RRU(" + subRack_rru + ") - " +
                        "<samp data-tooltip=\"Manufacturer name\">MF: " + sfp_RRU + "</samp>; " +
                        TCR +
                        s1 +
                        "mode: " +
                        mode1 +
                        warning1;


        return BBU + RRU;
    }

    private String sampSelector(boolean b, boolean wr) {
        String s1;
        double difference = b ? (difference1 < 0) ? difference1 * -1 : difference1 : (difference2 < 0) ? difference2 * -1 : difference2;

        if (wr) {
            s1 = String.format("(<samp1 data-tooltip=\"Превышение уровня затухания! %.2f > 4 dbm\">%.2f</samp1> dbm)", difference, difference);
        } else {
            s1 = String.format("(<samp0>%.2f</samp0> dbm)", difference);
        }

        return s1;
    }
}