package main;


import main.classes.SFP;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Result {

    private final SFP sfp0;
    private final SFP sfp1;
    private Double difference1;
    private Double difference2;
    private String sfp_sideSubRack0;
    private String sfp_sub0;
    private String sfp_slot0;
    private String sfp_port0;
    private String sfp_sideSubRack1;
    private String sfp_sub1;
    private String sfp_slot1;
    private String sfp_port1;
    private String sfp_TX0;
    private String sfp_RX0;
    private String sfp_TX1;
    private String sfp_RX1;
    private boolean ws0 = true;
    private boolean ws1 = true;
    private boolean ws2 = true;
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

    public Double DifferenceRxTx() {
        return (sfp0.getTx() - sfp1.getRx() < 0 ? (sfp0.getTx() - sfp1.getRx()) * -1 : sfp0.getTx() - sfp1.getRx());
    }

    public Double DifferenceTxRx() {
        return (sfp0.getRx() - sfp1.getTx() < 0 ? (sfp0.getRx() - sfp1.getTx()) * -1 : sfp0.getRx() - sfp1.getTx());
    }

    public String sfp_info_selector() {
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

        difference1 = DifferenceRxTx();
        difference2 = DifferenceTxRx();

        warningGreen = sampSamp("warningGreen", " ▲ ");
        warningRed = sampSamp("warningRed", " ▲ ");
        warningYellow = sampSamp("warningYellow", " ▲ ");

        TCR = sampDataTooltip("Transmission code rate", "tcr: ");

        SM = sampDataTooltip("SINGLEMODE", "SM");
        MM = sampDataTooltip("MULTIMODE", "MM");
        NULL = sampDataTooltip("NULL", "NULL");

        String result0;

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
                details_summary(),
                trx_selector(true),
                trx_selector(false),
                sfp_info_selector());

        if (warningSignal) {
            return "<details open>" + "<summary style=\"text-align: center\">" + i + ": " + result0 + "</details>";
        } else {
            return "<details>" + "<summary style=\"text-align: center\">" + i + ": " + result0 + "</details>";
        }
    }

    private String sfp_attenuation(boolean b0, boolean b1, boolean b2) {

        if (b0) {
            sfp_TX0 = String.format("Tx= %.2f dbm --", sfp0.getTx());
            sfp_RX1 = String.format("--> Rx= %.2f dbm ", sfp1.getRx());
        } else {
            sfp_RX0 = String.format("Rx= %.2f dbm <--", sfp0.getRx());
            sfp_TX1 = String.format("-- Tx= %.2f dbm ", sfp1.getTx());
        }

        String TRxd = samp_selector(b0, b1, b2);

        return new HtmlBuilder().vols_attenuation_content_0(
                sfp_sideSubRack0,
                sfp_sub0,
                sfp_slot0,
                sfp_port0,
                b0 ? sfp_TX0 : sfp_RX0,
                TRxd,
                !b0 ? sfp_TX1 : sfp_RX1,
                sfp_sideSubRack1,
                sfp_sub1,
                sfp_slot1,
                sfp_port1
        );
    }

    private String sfp_info(boolean b0, boolean b1, boolean b2) {

        String s0, s1, mode0, mode1;

        if (b0) {
            s0 = sampDataTooltip("В " + sfp1.getSideSubRack() + " sfp c инным TCR", sampSamp("samp sampRed", String.format("%.1f", sfp0.getSfpSpeed())));
            s1 = sampDataTooltip("В " + sfp0.getSideSubRack() + " sfp c инным TCR", sampSamp("samp sampRed", String.format("%.1f", sfp1.getSfpSpeed())));
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


        String sfp_MN0 = sampDataTooltip("Manufacturer name", sampSamp("samp sampPurple", sfp0.getSfpManufacturerName()));
        String sfp_WL0 = sampSamp("samp sampBlue", Integer.toString(sfp0.getSfpWaveLength()));

        String sfp_MN1 = sampDataTooltip("Manufacturer name", sampSamp("samp sampPurple", sfp1.getSfpManufacturerName()));
        String sfp_WL1 = sampSamp("samp sampBlue", Integer.toString(sfp1.getSfpWaveLength()));

        return new HtmlBuilder().sfp_info_content(
                sfp_sideSubRack0,
                sfp_sub0,
                sfp_slot0,
                sfp_port0,
                sfp_MN0,
                TCR,
                s0,
                mode0,
                sfp_WL0,
                sfp_sideSubRack1,
                sfp_sub1,
                sfp_slot1,
                sfp_port1,
                sfp_MN1,
                s1,
                mode1,
                sfp_WL1
        );
    }

    private String trx_selector(boolean b0) {

        double difference = b0 ? difference1 : difference2;

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

    private String details_summary() {

        String diff;
        if (difference1 >= 3.9 && difference1 <= 3.99 || difference1 >= 4) {
            diff = difference1 >= 4 ? warningRed : warningYellow;
        } else if (difference2 >= 3.9 && difference2 <= 3.99 || difference2 >= 4) {
            diff = difference2 >= 4 ? warningRed : warningYellow;
        } else {
            ws0 = false;
            diff = warningGreen;
        }

        String speed;
        if (sfp0.getSfpSpeed() != sfp1.getSfpSpeed()) {
            speed = warningRed;
        } else {
            ws1 = false;
            speed = warningGreen;
        }

        String mode;
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

        sfp_sideSubRack0 = sfp0.getSideSubRack();
        sfp_sub0 = sampSamp("samp sampBlack", Integer.toString(sfp0.getSubRack()));
        sfp_slot0 = sampSamp("samp sampBlack", Integer.toString(sfp0.getSlot()));
        sfp_port0 = sampSamp("samp sampBlack", Integer.toString(sfp0.getPort()));

        sfp_sideSubRack1 = sfp1.getSideSubRack();
        sfp_sub1 = sampSamp("samp sampBlack", Integer.toString(sfp1.getSubRack()));
        sfp_slot1 = sampSamp("samp sampBlack", Integer.toString(sfp1.getSlot()));
        sfp_port1 = sampSamp("samp sampBlack", Integer.toString(sfp1.getPort()));

        String sector0 = sfp0.getSubRack() > 0 ? sector_selector(sfp1.getSubRack()) : sector_selector(sfp0.getSubRack());
        String sector1 = sfp1.getSubRack() > 0 ? sector_selector(sfp1.getSubRack()) : sector_selector(sfp0.getSubRack());

        return new HtmlBuilder().summary_content(sfp_sideSubRack0, sfp_sub0, sfp_slot0, sfp_port0, sfp_sideSubRack1,
                sfp_sub1, sfp_slot1, sfp_port1, sector0, sector1, diff, speed, mode);
    }

    private @NotNull String sector_selector(int i) {

        String sc;
        int ic = i - ((i / 10) * 10);

        ic++;

        sc = ic > 3 ? String.format("%s (indoor?)", ic) : String.format("%s", ic);

        if (i >= 60 && i <= 69) {
            return sampDataTooltip("C" + sc + " R-4900 МГц", "R;");
        } else if (i >= 70 && i <= 79) {
            return sampDataTooltip("C" + sc + " R-27200 МГц", "R;");
        } else if (i >= 90 && i <= 99) {
            return sampDataTooltip("C" + sc + " GSM-900 МГц", "G;");
        } else if (i >= 100 && i <= 109) {
            return sampDataTooltip("C" + sc + " GSM-900 МГц", "G, ") + sampDataTooltip("C" + sc + " NB IoT-800 МГц", "N;");
        } else if (i >= 180 && i <= 189) {
            return sampDataTooltip("C" + sc + " DSC-1800 МГц", "D;");
        } else if (i >= 200 && i <= 209) {
            return sampDataTooltip("C" + sc + " DSC-1800 МГц", "D, ") + sampDataTooltip("C" + sc + " UMTS-2100 МГц", "U, ") + sampDataTooltip("C" + sc + " LTE-1800 МГц", "L;");
        } else if (i >= 210 && i <= 219) {
            return sampDataTooltip("C" + sc + " UMTS-2100 МГц", "U;");
        } else if (i >= 230 && i <= 239) {
            return sampDataTooltip("C" + sc + " LTE-2600 МГц TDD", "L;");
        } else if (i >= 240 && i <= 249) {
            return sampDataTooltip("C" + sc + " LTE-2600 МГц FDD", "L;");
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
