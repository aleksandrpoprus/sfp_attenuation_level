package main;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

    private int sfp_BBU_Wave_Length;
    private int sfp_RRU_Wave_Length;

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
                             String sfp_RRU_Mode,
                             int sfp_BBU_Wave_Length,
                             int sfp_RRU_Wave_Length) {

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

        this.sfp_BBU_Wave_Length = sfp_BBU_Wave_Length;
        this.sfp_RRU_Wave_Length = sfp_RRU_Wave_Length;
    }

    public String getBts_name() {
        return bts_name;
    }

    public String getResult(int i) {

        difference1 = tx_bbu - rx_rru < 0 ? (tx_bbu - rx_rru) * -1 : tx_bbu - rx_rru;
        difference2 = rx_bbu - tx_rru < 0 ? (rx_bbu - tx_rru) * -1 : rx_bbu - tx_rru;

        warningGreen = sampSamp("warningGreen", " ▲ ");
        warningRed = sampSamp("warningRed", " ▲ ");
        warningYellow = sampSamp("warningYellow", " ▲ ");

        TCR = sampDataTooltip("Transmission code rate", "tcr: ");

        SM = sampDataTooltip("SINGLEMODE", "SM");
        MM = sampDataTooltip("MULTIMODE", "MM");
        NULL = sampDataTooltip("NULL", "NULL");


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
                        "<p class=\"p0\">Информация о SFP:</p>" +
                        "%s</div>" +
                        "</div>",
                details_summary(), s1, s2, s3);

        if (warningSignal) {
            return "<details open>" + "<summary style=\"text-align: center\">" + i + ": " + result + "</details>";
        } else {
            return "<details>" + "<summary style=\"text-align: center\">" + i + ": " + result + "</details>";
        }
    }


    private @NotNull String sfp_attenuation(boolean b0, boolean b1, boolean b2) {

        String result, TRX1, TRX2, warning = "";

        if (b0) {
            TRX1 = String.format("Tx= %.2f dbm --", tx_bbu);
            TRX2 = String.format("--> Rx= %.2f dbm ", rx_rru);
        } else {
            TRX1 = String.format("RX= %.2f dbm <--", rx_bbu);
            TRX2 = String.format("-- TX= %.2f dbm ", tx_rru);
        }

        result = "BBU(" +
                sampSamp("samp sampBlack", Integer.toString(slot_bbu)) +
                ", " +
                sampSamp("samp sampBlack", Integer.toString(port_bbu)) +
                ") " +
                TRX1 +
                samp_selector(b0, b1, b2) +
                TRX2 +
                "RRU (" +
                sampSamp("samp sampBlack", Integer.toString(subRack_rru)) +
                ")" +
                warning +
                ";";
        return result;
    }

    private @NotNull String sfp_info(boolean b0, boolean b1, boolean b2) {

        String s0, s1, mode0, mode1;

        if (b0) {
            s0 = sampDataTooltip("В RRU sfp c инным TCR", sampSamp("samp sampRed", Double.toString(sfp_BBU_Speed / 10)));
            s1 = sampDataTooltip("В BBU sfp c инным TCR", sampSamp("samp sampRed", Double.toString(sfp_RRU_Speed / 10)));
            mode0 = sampSamp("samp sampBlue", sfp_BBU_Mode);
            mode1 = sampSamp("samp sampBlue", sfp_RRU_Mode);
        } else if (b1) {
            s0 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", Double.toString(sfp_BBU_Speed / 10)));
            s1 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", Double.toString(sfp_RRU_Speed / 10)));
            mode0 = sampSamp("samp sampYellow", sfp_BBU_Mode);
            mode1 = sampSamp("samp sampYellow", sfp_RRU_Mode);
        } else if (b2) {
            s0 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", Double.toString(sfp_BBU_Speed / 10)));
            s1 = sampDataTooltip("RRU недоступен!", sampSamp("samp sampRed", NULL));
            mode0 = sampSamp("samp sampRed", sfp_BBU_Mode);
            mode1 = sampSamp("samp sampRed", sfp_RRU_Mode);
        } else {
            s0 = sampSamp("samp sampBlue", Double.toString(sfp_BBU_Speed / 10));
            s1 = sampSamp("samp sampBlue", Double.toString(sfp_RRU_Speed / 10));
            mode0 = sampSamp("samp sampBlue", sfp_BBU_Mode);
            mode1 = sampSamp("samp sampBlue", sfp_RRU_Mode);
        }

        String BBU =
                "BBU(" +
                        sampSamp("samp sampBlack", Integer.toString(slot_bbu)) +
                        ", " +
                        sampSamp("samp sampBlack", Integer.toString(port_bbu)) +
                        ") " +
                        sampDataTooltip("Manufacturer name", sampSamp("samp sampPurple", sfp_BBU)) +
                        "; " +
                        TCR +
                        s0 +
                        " Gbit/s; " +
                        "mode: " +
                        mode0 +
                        ";" +
                        "</p><p>" +
                        "wavelength: " +
                        sampSamp("samp sampBlue", Integer.toString(sfp_BBU_Wave_Length)) +
                        " nm" +
                        ";";
        String RRU =
                "RRU(" +
                        sampSamp("samp sampBlack", Integer.toString(subRack_rru)) +
                        ") " +
                        sampDataTooltip("Manufacturer name", sampSamp("samp sampPurple", sfp_RRU)) +
                        "; " +
                        TCR +
                        s1 +
                        " Gbit/s; " +
                        "mode: " +
                        mode1 +
                        ";" +
                        "</p><p>" +
                        "wavelength: " +
                        sampSamp("samp sampBlue", Integer.toString(sfp_RRU_Wave_Length)) +
                        " nm" +
                        ";";
        return BBU + "</p><p>" + RRU;
    }

    private @NotNull String trx_selector(boolean b0, double difference) {

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

        return "BBU(" +
                sampSamp("samp sampBlack", Integer.toString(slot_bbu)) +
                ", " +
                sampSamp("samp sampBlack", Integer.toString(port_bbu)) +
                ") " +
                "<--> " +
                "RRU " +
                sampSamp("samp sampBlack", Integer.toString(subRack_rru)) +
                " (" +
                sector_selector(subRack_rru) +
                ") " +
                "trx: " +
                diff +
                "tcr: " +
                speed +
                "mode: " +
                mode;
    }

    private @NotNull String sector_selector(int i) {

        if (i >= 90 && i <= 95) {
            return sampDataTooltip("GSM-900 МГц", "G");
        } else if (i >= 100 && i <= 105) {
            return sampDataTooltip("GSM-900 МГц", "G, ") + sampDataTooltip("LTE-800 МГц", "L");
        } else if (i >= 180 && i <= 185) {
            return sampDataTooltip("DSC-1800 МГц", "D");
        } else if (i >= 200 && i <= 205) {
            return sampDataTooltip("DSC-1800 МГц", "D, ") + sampDataTooltip("UMTS-2100 МГц", "U, ") + sampDataTooltip("LTE-1800 МГц", "L");
        } else if (i >= 210 && i <= 215) {
            return sampDataTooltip("UMTS-2100 МГц", "U");
        } else if (i >= 230 && i <= 235) {
            return sampDataTooltip("LTE-2600 МГц TDD", "L");
        } else if (i >= 240 && i <= 245) {
            return sampDataTooltip("LTE-2600 МГц FDD", "L");
        } else {
            return "";
        }
    }

    private @NotNull String samp_selector(boolean b0, boolean b1, boolean b2) {

        double difference = b0 ? difference1 : difference2;

        if (b1) {
            if (b2) {
                return sampDataTooltip("Уровень затухания близок к превышению!", sampSamp("samp sampYellow", String.format("%.2f", difference))) + " dbm";
            } else {
                return sampDataTooltip("Превышение уровня затухания!" + String.format("%.2f > 4 dbm", difference), sampSamp("samp sampRed", String.format("%.2f", difference))) + " dbm";
            }
        } else {
            return sampSamp("samp sampGreen", String.format("%.2f", difference)) + " dbm";
        }
    }


    @Contract(pure = true)
    private @NotNull String sampSamp(String css_class, String s) {
        return "<samp class=\"" + css_class + "\">" + s + "</samp>";
    }

    @Contract(pure = true)
    private @NotNull String sampDataTooltip(String s0, String s1) {
        return "<samp data-tooltip=\"" + s0 + "\">" + s1 + "</samp>";
    }
}
