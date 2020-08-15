package main.classes;

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
                             int tx_bbu,
                             int rx_bbu,
                             int subRack_rru,
                             int tx_rru,
                             int rx_rru,
                             String sfp_BBU,
                             int sfp_BBU_Speed,
                             String sfp_RRU,
                             int sfp_RRU_Speed,
                             String sfp_BBU_Mode,
                             String sfp_RRU_Mode,
                             int sfp_BBU_Wave_Length,
                             int sfp_RRU_Wave_Length) {

        this.bts_name = bts_name;
        this.subRack_rru = subRack_rru;

        this.slot_bbu = slot_bbu;
        this.port_bbu = port_bbu;

        this.tx_bbu = tx_bbu * 0.01;
        this.rx_rru = rx_rru * 0.01;

        this.rx_bbu = rx_bbu * 0.01;
        this.tx_rru = tx_rru * 0.01;

        this.sfp_BBU = sfp_BBU;
        this.sfp_BBU_Speed = sfp_BBU_Speed * 0.1;

        this.sfp_RRU = sfp_RRU;
        this.sfp_RRU_Speed = sfp_RRU_Speed * 0.1;

        this.sfp_BBU_Mode = sfp_BBU_Mode;
        this.sfp_RRU_Mode = sfp_RRU_Mode;

        this.sfp_BBU_Wave_Length = sfp_BBU_Wave_Length / 100;
        this.sfp_RRU_Wave_Length = sfp_RRU_Wave_Length / 100;
    }

    public String getBts_name() {
        return bts_name;
    }

    public String getResult(int i) {

        difference1 = (tx_bbu - rx_rru < 0 ? (tx_bbu - rx_rru) * -1 : tx_bbu - rx_rru);
        difference2 = (rx_bbu - tx_rru < 0 ? (rx_bbu - tx_rru) * -1 : rx_bbu - tx_rru);

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
            s3 = sfp_info(true, false, false);
        } else if (sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(MM) ||
                sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(SM))) {
            s3 = sfp_info(false, true, false);
        } else if (sfp_BBU_Speed != sfp_RRU_Speed && (sfp_BBU_Mode.equals(SM) && sfp_RRU_Mode.equals(NULL) ||
                sfp_BBU_Mode.equals(MM) && sfp_RRU_Mode.equals(NULL))) {
            s3 = sfp_info(false, false, true);
        } else {
            s3 = sfp_info(false, false, false);
        }

        String result = String.format("%s</summary>" +
                        "<div>" +
                        "<div class=\"div2\">" +
                        "<p class=\"p0\">Затухание ВОЛС:</p>" +
                        "%s%n%s" +
                        "</div>" +
                        "<div class=\"div2\">" +
                        "<p class=\"p0\">Информация о SFP:</p>" +
                        "%s" +
                        "</div>" +
                        "</div>",
                details_summary(), s1, s2, s3);

        if (warningSignal) {
            return "<details open>" + "<summary style=\"text-align: center\">" + i + ": " + result + "</details>";
        } else {
            return "<details>" + "<summary style=\"text-align: center\">" + i + ": " + result + "</details>";
        }
    }


    private @NotNull String sfp_attenuation(boolean b0, boolean b1, boolean b2) {

        String BBU_slot, BBU_port, TRX1, TRXd, TRX2, RRU_sub;

        if (b0) {
            TRX1 = String.format("Tx= %.2f dbm --", tx_bbu);
            TRX2 = String.format("--> Rx= %.2f dbm ", rx_rru);
        } else {
            TRX1 = String.format("RX= %.2f dbm <--", rx_bbu);
            TRX2 = String.format("-- TX= %.2f dbm ", tx_rru);
        }

        BBU_slot = sampSamp("samp sampBlack", Integer.toString(slot_bbu));
        BBU_port = sampSamp("samp sampBlack", Integer.toString(port_bbu));
        TRXd = samp_selector(b0, b1, b2);
        RRU_sub = sampSamp("samp sampBlack", Integer.toString(subRack_rru));

        return new HtmlBuilder().vols_attenuation_content(BBU_slot, BBU_port, TRX1, TRXd, TRX2, RRU_sub);
    }

    private @NotNull String sfp_info(boolean b0, boolean b1, boolean b2) {

        String s0, s1, mode0, mode1;

        if (b0) {
            s0 = sampDataTooltip("В RRU sfp c инным TCR", sampSamp("samp sampRed", String.format("%.1f", sfp_BBU_Speed)));
            s1 = sampDataTooltip("В BBU sfp c инным TCR", sampSamp("samp sampRed", String.format("%.1f", sfp_RRU_Speed)));
            mode0 = sampSamp("samp sampBlue", sfp_BBU_Mode);
            mode1 = sampSamp("samp sampBlue", sfp_RRU_Mode);
        } else if (b1) {
            s0 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", String.format("%.1f", sfp_BBU_Speed)));
            s1 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", String.format("%.1f", sfp_RRU_Speed)));
            mode0 = sampSamp("samp sampYellow", sfp_BBU_Mode);
            mode1 = sampSamp("samp sampYellow", sfp_RRU_Mode);
        } else if (b2) {
            s0 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", String.format("%.1f", sfp_BBU_Speed)));
            s1 = sampDataTooltip("RRU недоступен!", sampSamp("samp sampRed", NULL));
            mode0 = sampSamp("samp sampRed", sfp_BBU_Mode);
            mode1 = sampSamp("samp sampRed", sfp_RRU_Mode);
        } else {
            s0 = sampSamp("samp sampBlue", String.format("%.1f", sfp_BBU_Speed));
            s1 = sampSamp("samp sampBlue", String.format("%.1f", sfp_RRU_Speed));
            mode0 = sampSamp("samp sampBlue", sfp_BBU_Mode);
            mode1 = sampSamp("samp sampBlue", sfp_RRU_Mode);
        }

        String BBU_slot, BBU_port, MN0, WL0, RRU_sub, MN1, WL1;

        BBU_slot = sampSamp("samp sampBlack", Integer.toString(slot_bbu));
        BBU_port = sampSamp("samp sampBlack", Integer.toString(slot_bbu));
        MN0 = sampDataTooltip("Manufacturer name", sampSamp("samp sampPurple", sfp_BBU));
        WL0 = sampSamp("samp sampBlue", Integer.toString(sfp_BBU_Wave_Length));

        RRU_sub = sampSamp("samp sampBlack", Integer.toString(subRack_rru));
        MN1 = sampDataTooltip("Manufacturer name", sampSamp("samp sampPurple", sfp_RRU));
        WL1 = sampSamp("samp sampBlue", Integer.toString(sfp_RRU_Wave_Length));

        return new HtmlBuilder().sfp_info_content(BBU_slot, BBU_port, MN0,
                TCR,
                s0, mode0, WL0, RRU_sub, MN1, s1, mode1, WL1);
    }

    private @NotNull String trx_selector(boolean b0, double difference) {

        if (difference >= 3.9 && difference <= 3.99 || difference >= 4)
            if (difference >= 4) {
                return sfp_attenuation(b0, true, false);
            } else {
                return sfp_attenuation(b0, true, true);
            }
        else {
            return sfp_attenuation(b0, false, false);
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

        String BBU_slot, BBU_port, RRU_sub, sector;

        BBU_slot = sampSamp("samp sampBlack", Integer.toString(slot_bbu));
        BBU_port = sampSamp("samp sampBlack", Integer.toString(port_bbu));
        RRU_sub = sampSamp("samp sampBlack", Integer.toString(subRack_rru));
        sector = sector_selector(subRack_rru);

        return new HtmlBuilder().summary_content(BBU_slot, BBU_port, RRU_sub, sector, diff, speed, mode);
    }

    private @NotNull String sector_selector(int i) {

        String sc;
        int ic = i - ((i / 10) * 10);

        ic++;

        sc = ic > 3 ? String.format("%s (indoor?)", ic) : String.format("%s", ic);

        if (i >= 90 && i <= 95) {
            return sampDataTooltip("C" + sc + " GSM-900 МГц", "G");
        } else if (i >= 100 && i <= 105) {
            return sampDataTooltip("C" + sc + " GSM-900 МГц", "G, ") + sampDataTooltip("C" + sc + " LTE-800 МГц", "L");
        } else if (i >= 180 && i <= 185) {
            return sampDataTooltip("C" + sc + " DSC-1800 МГц", "D");
        } else if (i >= 200 && i <= 205) {
            return sampDataTooltip("C" + sc + " DSC-1800 МГц", "D, ") + sampDataTooltip("C" + sc + " UMTS-2100 МГц", "U, ") + sampDataTooltip("C" + sc + " LTE-1800 МГц", "L");
        } else if (i >= 210 && i <= 215) {
            return sampDataTooltip("C" + sc + " UMTS-2100 МГц", "U");
        } else if (i >= 230 && i <= 235) {
            return sampDataTooltip("C" + sc + " LTE-2600 МГц TDD", "L");
        } else if (i >= 240 && i <= 245) {
            return sampDataTooltip("C" + sc + " LTE-2600 МГц FDD", "L");
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
                return sampDataTooltip("Превышение уровня затухания! " + String.format("%.2f > 4 dbm", difference), sampSamp("samp sampRed", String.format("%.2f", difference))) + " dbm";
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
