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

    private String warningGreen, warningRed, warningYellow;

    private String TCR;

    private String SM, MM, NULL;

    private boolean warningSignal = true;

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

    public String getResult(int i) {

        difference1 = tx_bbu - rx_rru < 0 ? (tx_bbu - rx_rru) * -1 : tx_bbu - rx_rru;
        difference2 = rx_bbu - tx_rru < 0 ? (rx_bbu - tx_rru) * -1 : rx_bbu - tx_rru;

        warningGreen = "<samp class=\"warningGreen\"> ▲ </samp>";
        warningRed = "<samp class=\"warningRed\"> ▲ </samp>";
        warningYellow = "<samp class=\"warningYellow\"> ▲ </samp>";

        TCR = "<samp data-tooltip=\"Transmission code rate\">TCR</samp>: ";

        SM = "<samp data-tooltip=\"SINGLEMODE\">SM</samp>";
        MM = "<samp data-tooltip=\"MULTIMODE\">MM</samp>";
        NULL = "<samp data-tooltip=\"NULL\">NULL</samp>";

        String s1, s2, s3;

        s1 = trx_selector(true, difference1);
        s2 = trx_selector(false, difference2);

        if (sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(SM))) {
            s3 = "<p>" + sfp_info(true, false, false) + "</p>";
        } else if (sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(MM) ||
                sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(SM))) {
            s3 = "<p>" + sfp_info(false, true, false) + "</p>";
        } else if (sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(NULL) ||
                sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(NULL))) {
            s3 = "<p>" + sfp_info(false, false, true) + "</p>";
        } else {
            s3 = "<p>" + sfp_info(false, false, false) + "</p>";
        }

        String result = String.format("%s</summary><div>" +
                        "<div>" +
                        "<p class=\"p0\">Затухание ВОЛС:</p>" +
                        "%s%n%s</div>" +
                        "<div>" +
                        "<p class=\"p0\">Информация по SFP модулям:</p>" +
                        "%s</div>" +
                        "</div>",
                details_summary(), s1, s2, s3);

        if (warningSignal) {
            return "<details open>" + "<summary style=\"text-align: center\">" + i + ": " + result + "</details>";
        } else {
            return "<details>" + "<summary style=\"text-align: center\">" + i + ": " + result + "</details>";
        }
    }

    public String details_summary() {

        String diff, speed, mode;
        boolean ws0 = true, ws1 = true, ws2 = true;

        if (difference1 >= 3.9 && difference1 <= 3.99 || difference1 >= 4) {
            diff = difference1 >= 4 ? warningRed : warningYellow;
        } else if (difference2 >= 3.9 && difference2 <= 3.99 || difference2 >= 4) {
            diff = difference2 >= 4 ? warningRed : warningYellow;
        } else {
            ws0 = false;
            diff = warningGreen;
        }

        if (sfp_BBU_Speed != sfp_RRU_Speed) {
            speed = warningRed;
        } else {
            ws1 = false;
            speed = warningGreen;
        }

        if (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(MM) ||
                sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(SM)) {
            mode = warningYellow;
        } else if ((sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(NULL) ||
                sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(NULL))) {
            mode = warningRed;
        } else {
            ws2 = false;
            mode = warningGreen;
        }

        warningSignal = ws0 || ws1 || ws2;

        return "BBU(" + slot_bbu + ", " + port_bbu + ") " +
                "<--> " +
                "RRU(" + subRack_rru +
                " " + sector_selector(subRack_rru) + ") " +
                "trx: " +
                diff +
                "tcr: " +
                speed +
                "mode: " +
                mode;
    }

    private String sfp_attenuation(boolean b0, boolean b1, boolean b2) {

        String result, TRX1, TRX2, warning = "";

        if (b0) {
            TRX1 = String.format("Tx= %.2f dbm --", tx_bbu);
            TRX2 = String.format("--> Rx= %.2f dbm ", rx_rru);
        } else {
            TRX1 = String.format("RX= %.2f dbm <--", rx_bbu);
            TRX2 = String.format("-- TX= %.2f dbm ", tx_rru);
        }

        result = "BBU(" + slot_bbu + ", " + port_bbu + ") " +
                TRX1 +
                samp_selector(b0, b1, b2) +
                TRX2 +
                "RRU (" + subRack_rru + ")" +
                warning +
                ";";
        return result;
    }

    private String sfp_info(boolean b0, boolean b1, boolean b2) {

        String s0, s1, mode0, mode1, warning0 = "", warning1 = "";

        if (b0) {
            s0 = String.format("<samp data-tooltip=\"В RRU sfp c инным TCR\"><samp class=\"samp1\">%.1f</samp></samp> Gbit/s; ", sfp_BBU_Speed / 10);
            s1 = String.format("<samp data-tooltip=\"В BBU sfp c инным TCR\"><samp class=\"samp1\">%.1f</samp></samp> Gbit/s; ", sfp_RRU_Speed / 10);
            mode0 = String.format("<samp class=\"samp2\">%s</samp>", sfp_BBU_Mode);
            mode1 = String.format("<samp class=\"samp2\">%s</samp>", sfp_RRU_Mode);
        } else if (b1) {
            s0 = String.format("<samp data-tooltip=\"Разные TCR и MODE\"><samp class=\"samp1\">%.1f</samp></samp> Gbit/s; ", sfp_BBU_Speed / 10);
            s1 = String.format("<samp data-tooltip=\"Разные TCR и MODE\"><samp class=\"samp1\">%.1f</samp></samp> Gbit/s; ", sfp_RRU_Speed / 10);
            mode0 = String.format("<samp class=\"samp3\">%s</samp>", sfp_BBU_Mode);
            mode1 = String.format("<samp class=\"samp3\">%s</samp>", sfp_RRU_Mode);
        } else if (b2) {
            s0 = String.format("<samp data-tooltip=\"Разные TCR и MODE\"><samp class=\"samp1\">%.1f</samp></samp> Gbit/s; ", sfp_BBU_Speed / 10);
            s1 = String.format("<samp data-tooltip=\"RRU недоступен!\"><samp class=\"samp1\">%s</samp></samp> Gbit/s; ", NULL);
            mode0 = String.format("<samp class=\"samp1\">%s</samp>", sfp_BBU_Mode);
            mode1 = String.format("<samp class=\"samp1\">%s</samp>", sfp_RRU_Mode);
        } else {
            s0 = String.format("<samp class=\"samp2\">%.1f</samp> Gbit/s; ", sfp_BBU_Speed / 10);
            s1 = String.format("<samp class=\"samp2\">%.1f</samp> Gbit/s; ", sfp_RRU_Speed / 10);
            mode0 = String.format("<samp class=\"samp2\">%s</samp>", sfp_BBU_Mode);
            mode1 = String.format("<samp class=\"samp2\">%s</samp>", sfp_RRU_Mode);
        }

        String BBU =
                "BBU(" + slot_bbu + ", " + port_bbu + ") " +
                        "<samp class=\"samp4\"><samp data-tooltip=\"Manufacturer name\">" + sfp_BBU + "</samp></samp>; " +
                        TCR +
                        s0 +
                        "mode: " +
                        mode0 +
                        warning0 +
                        ";" +
                        "</p>\n";
        String RRU =
                "<p>" +
                        "RRU(" + subRack_rru + ") - " +
                        "<samp class=\"samp4\"><samp data-tooltip=\"Manufacturer name\">" + sfp_RRU + "</samp></samp>; " +
                        TCR +
                        s1 +
                        "mode: " +
                        mode1 +
                        ";" +
                        warning1;
        return BBU + RRU;
    }

    private String trx_selector(boolean b0, double difference) {

        if (difference >= 3.9 && difference <= 3.99 || difference >= 4)
            if (difference >= 4) {
                return "<p>" + sfp_attenuation(b0, true, false) + "</p>";
            } else {
                return "<p>" + sfp_attenuation(b0, true, true) + "</p>";
            }
        else {
            return "<p>" + sfp_attenuation(b0, false, false) + "</p>";
        }
    }

    private String sector_selector(int i) {

        String blue9g = "<samp data-tooltip=\"GSM-900 МГц\"><samp class=\"samp2\">";
        String yellow9l = "<samp data-tooltip=\"LTE-800 МГц\"><samp class=\"samp3\">";
        String green18d = "<samp data-tooltip=\"DSC-1800 МГц\"><samp class=\"samp0\">";
        String green18l = "<samp data-tooltip=\"LTE-1800 МГц\"><samp class=\"samp0\">";
        String red21u = "<samp data-tooltip=\"UMTS-2100 МГц\"><samp class=\"samp1\">";
        String yellow26f = "<samp data-tooltip=\"LTE-2600 МГц FDD\"><samp class=\"samp3\">";
        String yellow26t = "<samp data-tooltip=\"LTE-2600 МГц TDD\"><samp class=\"samp3\">";


        switch (i) {
            case (90):
                return blue9g + "C1</samp></samp>";
            case (91):
                return blue9g + "C2</samp></samp>";
            case (92):
                return blue9g + "C3</samp></samp>";
            case (93):
                return blue9g + "C1.1</samp></samp>";

            case (100):
                return blue9g + "C1</samp></samp>," + yellow9l + "C37</samp></samp>";
            case (101):
                return blue9g + "C2</samp></samp>," + yellow9l + "C38</samp></samp>";
            case (102):
                return blue9g + "C3</samp></samp>," + yellow9l + "C39</samp></samp>";
            case (103):
                return blue9g + "C1.1</samp></samp>," + yellow9l + "C37.1</samp></samp>";

            case (180):
                return green18d + "C4</samp></samp>";
            case (181):
                return green18d + "C5</samp></samp>";
            case (182):
                return green18d + "C6</samp></samp>";
            case (183):
                return green18d + "C4.1</samp></samp>";

            case (210):
                return red21u + "C7</samp></samp>";
            case (211):
                return red21u + "C8</samp></samp>";
            case (212):
                return red21u + "C9</samp></samp>";
            case (213):
                return red21u + "C7.1</samp></samp>";

            case (200):
                return green18d + "C4</samp></samp>," + red21u + "C7</samp></samp>," + green18l + "C47</samp></samp>";
            case (201):
                return green18d + "C5</samp></samp>," + red21u + "C8</samp></samp>," + green18l + "C48</samp></samp>";
            case (202):
                return green18d + "C6</samp></samp>," + red21u + "C9</samp></samp>," + green18l + "C49</samp></samp>";
            case (203):
                return green18d + "C4.1</samp></samp>," + red21u + "C7.1</samp></samp>," + green18l + "C47.1</samp></samp>";

            case (240):
                return yellow26f + "C57</samp></samp>";
            case (241):
                return yellow26f + "C58</samp></samp>";
            case (242):
                return yellow26f + "C59</samp></samp>";
            case (243):
                return yellow26f + "C57.1</samp></samp>";

            case (230):
                return yellow26t + "C77</samp></samp>";
            case (231):
                return yellow26t + "C78</samp></samp>";
            case (232):
                return yellow26t + "C79</samp></samp>";
            case (233):
                return yellow26t + "C77.1</samp></samp>";
            default:
                return "</samp>";

        }
    }

    private String samp_selector(boolean b0, boolean b1, boolean b2) {

        double difference = b0 ? difference1 : difference2;

        if (b1) {
            if (b2) {
                return String.format("(<samp class=\"samp3\" data-tooltip=\"Уровень затухания близок к превышению!\">%.2f</samp> dbm)", difference);
            } else {
                return String.format("(<samp class=\"samp1\" data-tooltip=\"Превышение уровня затухания! %.2f > 4 dbm\">%.2f</samp> dbm)", difference, difference);
            }
        } else {
            return String.format("(<samp class=\"samp0\">%.2f</samp> dbm)", difference);
        }
    }
}
