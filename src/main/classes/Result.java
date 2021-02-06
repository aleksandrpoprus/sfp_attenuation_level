package main.classes;


public class Result {

    private SFP sfp0;
    private SFP sfp1;

    private Double difference1;
    private Double difference2;

    private String warningGreen, warningRed, warningYellow;
    private String TCR;
    private String SM, MM, NULL;

    private boolean warningSignal = true;

    public Result(SFP sfp0, SFP sfp1) {
        this.sfp0 = sfp0;
        this.sfp1 = sfp1;
    }

    public String getBts_name() {
        return sfp0.getBtsName();
    }

    public Double DifferenceRxTx(SFP sfp0, SFP sfp1) {
        return (sfp0.getTx() - sfp1.getRx() < 0 ? (sfp0.getTx() - sfp1.getRx()) * -1 : sfp0.getTx() - sfp1.getRx());
    }

    public Double DifferenceTxRx(SFP sfp0, SFP sfp1) {
        return (sfp0.getRx() - sfp1.getTx() < 0 ? (sfp0.getRx() - sfp1.getTx()) * -1 : sfp0.getRx() - sfp1.getTx());
    }

    public String sfp_info_selector(SFP sfp0, SFP sfp1) {
        String s;
        if (sfp0.getSfpSpeed() != sfp1.getSfpSpeed() && (sfp0.getSfpMode().equals(SM) && sfp1.getSfpMode().equals(SM))) {
            s = sfp_info(true, false, false);
        } else if (sfp0.getSfpSpeed() != sfp1.getSfpSpeed() && (sfp0.getSfpMode().equals(SM) && sfp1.getSfpMode().equals(MM) ||
                sfp0.getSfpMode().equals(MM) && sfp1.getSfpMode().equals(SM))) {
            s = sfp_info(false, true, false);
        } else if (sfp0.getSfpSpeed() != sfp1.getSfpSpeed() && (sfp0.getSfpMode().equals(SM) && sfp1.getSfpMode().equals(NULL) ||
                sfp0.getSfpMode().equals(MM) && sfp1.getSfpMode().equals(NULL))) {
            s = sfp_info(false, false, true);
        } else {
            s = sfp_info(false, false, false);
        }
        return s;
    }

    public String getResult(int i) {

        difference1 = DifferenceRxTx(sfp0, sfp1);
        difference2 = DifferenceTxRx(sfp0, sfp1);

        warningGreen = sampSamp("warningGreen", " ▲ ");
        warningRed = sampSamp("warningRed", " ▲ ");
        warningYellow = sampSamp("warningYellow", " ▲ ");

        TCR = sampDataTooltip("Transmission code rate", "tcr: ");

        SM = sampDataTooltip("SINGLEMODE", "SM");
        MM = sampDataTooltip("MULTIMODE", "MM");
        NULL = sampDataTooltip("NULL", "NULL");

        String result0, result1 = "";

        result0 = String.format(
                "%s</summary>" +
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
                details_summary(sfp0, sfp1, difference1, difference2),
                trx_selector(true, difference1),
                trx_selector(false, difference2),
                sfp_info_selector(sfp0, sfp1));

        if (warningSignal) {
            return "<details open>" + "<summary style=\"text-align: center\">" + i + ": " + result0 + "</details>";
        } else {
            return "<details>" + "<summary style=\"text-align: center\">" + i + ": " + result0 + "</details>";
        }
    }


    private String sfp_attenuation(boolean b0, boolean b1, boolean b2) {

        String BBU_sideSubRack,
                BBU_sub0,
                BBU_slot0,
                BBU_port0,
                BBU_TX0 = "",
                BBU_RX0 = "",
                TRXd0,
                RRU_TX0 = "",
                RRU_RX0 = "",
                RRU_sideSubRack,
                RRU_sub0,
                RRU_slot0,
                RRU_port0;

        if (b0) {
            BBU_TX0 = String.format("Tx= %.2f dbm --", sfp0.getTx());
            RRU_RX0 = String.format("--> Rx= %.2f dbm ", sfp1.getRx());
        } else {
            BBU_RX0 = String.format("RX= %.2f dbm <--", sfp0.getRx());
            RRU_TX0 = String.format("-- TX= %.2f dbm ", sfp1.getTx());
        }

        BBU_sideSubRack = sfp0.getSideSubRack();
        BBU_sub0 = sampSamp("samp sampBlack", Integer.toString(sfp0.getSubRack()));
        BBU_slot0 = sampSamp("samp sampBlack", Integer.toString(sfp0.getSlot()));
        BBU_port0 = sampSamp("samp sampBlack", Integer.toString(sfp0.getPort()));
        TRXd0 = samp_selector(b0, b1, b2);
        RRU_sideSubRack = sfp1.getSideSubRack();
        RRU_sub0 = sampSamp("samp sampBlack", Integer.toString(sfp1.getSubRack()));
        RRU_slot0 = sampSamp("samp sampBlack", Integer.toString(sfp1.getSlot()));
        RRU_port0 = sampSamp("samp sampBlack", Integer.toString(sfp1.getPort()));

        return new HtmlBuilder().vols_attenuation_content_0(
                BBU_sideSubRack,
                BBU_sub0,
                BBU_slot0,
                BBU_port0,
                b0 ? BBU_TX0 : BBU_RX0,
                TRXd0,
                !b0 ? RRU_TX0 : RRU_RX0,
                RRU_sideSubRack,
                RRU_sub0,
                RRU_slot0,
                RRU_port0
        );
    }

    private String sfp_info(boolean b0, boolean b1, boolean b2) {

        String s0, s1, mode0, mode1;

        if (b0) {
            s0 = sampDataTooltip("В RRU sfp c инным TCR", sampSamp("samp sampRed", String.format("%.1f", sfp0.getSfpSpeed())));
            s1 = sampDataTooltip("В BBU sfp c инным TCR", sampSamp("samp sampRed", String.format("%.1f", sfp1.getSfpSpeed())));
            mode0 = sampSamp("samp sampBlue", sfp0.getSfpMode());
            mode1 = sampSamp("samp sampBlue", sfp1.getSfpMode());
        } else if (b1) {
            s0 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", String.format("%.1f", sfp0.getSfpSpeed())));
            s1 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", String.format("%.1f", sfp1.getSfpSpeed())));
            mode0 = sampSamp("samp sampYellow", sfp0.getSfpMode());
            mode1 = sampSamp("samp sampYellow", sfp1.getSfpMode());
        } else if (b2) {
            s0 = sampDataTooltip("Разные TCR и MODE", sampSamp("samp sampRed", String.format("%.1f", sfp0.getSfpSpeed())));
            s1 = sampDataTooltip("RRU недоступен!", sampSamp("samp sampRed", NULL));
            mode0 = sampSamp("samp sampRed", sfp0.getSfpMode());
            mode1 = sampSamp("samp sampRed", sfp1.getSfpMode());
        } else {
            s0 = sampSamp("samp sampBlue", String.format("%.1f", sfp0.getSfpSpeed()));
            s1 = sampSamp("samp sampBlue", String.format("%.1f", sfp1.getSfpSpeed()));
            mode0 = sampSamp("samp sampBlue", sfp0.getSfpMode());
            mode1 = sampSamp("samp sampBlue", sfp1.getSfpMode());
        }

        String BBU_sideSubRack,
                BBU_sub,
                BBU_slot,
                BBU_port,
                BBU_MN,
                BBU_WL,
                RRU_sideSubRack,
                RRU_sub,
                RRU_slot,
                RRU_port,
                RRU_MN,
                RRU_WL;

        BBU_sideSubRack = sfp0.getSideSubRack();
        BBU_sub = sampSamp("samp sampBlack", Integer.toString(sfp0.getSubRack()));
        BBU_slot = sampSamp("samp sampBlack", Integer.toString(sfp0.getSlot()));
        BBU_port = sampSamp("samp sampBlack", Integer.toString(sfp0.getPort()));
        BBU_MN = sampDataTooltip("Manufacturer name", sampSamp("samp sampPurple", sfp0.getSfpManufacturerName()));
        BBU_WL = sampSamp("samp sampBlue", Integer.toString(sfp0.getSfpWaveLength()));

        RRU_sideSubRack = sfp1.getSideSubRack();
        RRU_sub = sampSamp("samp sampBlack", Integer.toString(sfp1.getSubRack()));
        RRU_slot = sampSamp("samp sampBlack", Integer.toString(sfp1.getSlot()));
        RRU_port = sampSamp("samp sampBlack", Integer.toString(sfp1.getPort()));
        RRU_MN = sampDataTooltip("Manufacturer name", sampSamp("samp sampPurple", sfp1.getSfpManufacturerName()));
        RRU_WL = sampSamp("samp sampBlue", Integer.toString(sfp1.getSfpWaveLength()));

        return new HtmlBuilder().sfp_info_content(
                BBU_sideSubRack,
                BBU_sub,
                BBU_slot,
                BBU_port,
                BBU_MN,
                TCR,
                s0,
                mode0,
                BBU_WL,
                RRU_sideSubRack,
                RRU_sub,
                RRU_slot,
                RRU_port,
                RRU_MN,
                s1,
                mode1,
                RRU_WL
        );
    }

    private String trx_selector(boolean b0, double difference) {

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


    public String details_summary(SFP sfp0, SFP sfp1, Double diff0, Double diff1) {

        String diff, speed, mode;
        boolean ws0 = true, ws1 = true, ws2 = true;

        if (diff0 >= 3.9 && diff0 <= 3.99 || diff0 >= 4) {
            diff = diff0 >= 4 ? warningRed : warningYellow;
        } else if (diff1 >= 3.9 && diff1 <= 3.99 || diff1 >= 4) {
            diff = diff1 >= 4 ? warningRed : warningYellow;
        } else {
            ws0 = false;
            diff = warningGreen;
        }

        if (sfp0.getSfpSpeed() != sfp1.getSfpSpeed()) {
            speed = warningRed;
        } else {
            ws1 = false;
            speed = warningGreen;
        }

        if (sfp0.getSfpMode().equals(SM) && sfp1.getSfpMode().equals(MM) ||
                sfp0.getSfpMode().equals(MM) && sfp1.getSfpMode().equals(SM)) {
            mode = warningYellow;
        } else if ((sfp0.getSfpMode().equals(SM) && sfp1.getSfpMode().equals(NULL) ||
                sfp0.getSfpMode().equals(MM) && sfp1.getSfpMode().equals(NULL))) {
            mode = warningRed;
        } else {
            ws2 = false;
            mode = warningGreen;
        }

        warningSignal = ws0 || ws1 || ws2;

        String
                BBU_sideSubRack,
                BBU_sub,
                BBU_slot,
                BBU_port,
                RRU_sideSubRack,
                RRU_sub,
                RRU_slot,
                RRU_port,
                sector;

        BBU_sideSubRack = sfp0.getSideSubRack();
        BBU_sub= sampSamp("samp sampBlack", Integer.toString(sfp0.getSubRack()));
        BBU_slot = sampSamp("samp sampBlack", Integer.toString(sfp0.getSlot()));
        BBU_port = sampSamp("samp sampBlack", Integer.toString(sfp0.getPort()));

        RRU_sideSubRack = sfp1.getSideSubRack();
        RRU_sub= sampSamp("samp sampBlack", Integer.toString(sfp1.getSubRack()));
        RRU_slot = sampSamp("samp sampBlack", Integer.toString(sfp1.getSlot()));
        RRU_port = sampSamp("samp sampBlack", Integer.toString(sfp1.getPort()));
        if (sfp1.getSubRack() > 0) {
            sector = sector_selector(sfp1.getSubRack());
        } else {
            sector = sector_selector(sfp0.getSubRack());
        }
        return new HtmlBuilder().summary_content(
                BBU_sideSubRack,
                BBU_sub,
                BBU_slot,
                BBU_port,
                RRU_sideSubRack,
                RRU_sub,
                RRU_slot,
                RRU_port,
                sector, diff, speed, mode);
    }

    private String sector_selector(int i) {

        String sc;
        int ic = i - ((i / 10) * 10);

        ic++;

        sc = ic > 3 ? String.format("%s (indoor?)", ic) : String.format("%s", ic);

        if (i >= 60 && i <= 69) {
            return sampDataTooltip("C" + sc + " R-4900 МГц", "R, ");
        } else if (i >= 70 && i <= 79) {
            return sampDataTooltip("C" + sc + " R-27900 МГц", "R, ");
        } else if (i >= 90 && i <= 99) {
            return sampDataTooltip("C" + sc + " GSM-900 МГц", "G");
        } else if (i >= 100 && i <= 109) {
            return sampDataTooltip("C" + sc + " GSM-900 МГц", "G, ") + sampDataTooltip("C" + sc + " LTE-800 МГц", "L");
        } else if (i >= 180 && i <= 189) {
            return sampDataTooltip("C" + sc + " DSC-1800 МГц", "D");
        } else if (i >= 200 && i <= 209) {
            return sampDataTooltip("C" + sc + " DSC-1800 МГц", "D, ") + sampDataTooltip("C" + sc + " UMTS-2100 МГц", "U, ") + sampDataTooltip("C" + sc + " LTE-1800 МГц", "L");
        } else if (i >= 210 && i <= 219) {
            return sampDataTooltip("C" + sc + " UMTS-2100 МГц", "U");
        } else if (i >= 230 && i <= 239) {
            return sampDataTooltip("C" + sc + " LTE-2600 МГц TDD", "L");
        } else if (i >= 240 && i <= 249) {
            return sampDataTooltip("C" + sc + " LTE-2600 МГц FDD", "L");
        } else {
            return "";
        }
    }

    private String samp_selector(boolean b0, boolean b1, boolean b2) {

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


    private String sampSamp(String css_class, String s) {
        return "<samp class=\"" + css_class + "\">" + s + "</samp>";
    }


    private String sampDataTooltip(String s0, String s1) {
        return "<samp data-tooltip=\"" + s0 + "\">" + s1 + "</samp>";
    }
}
