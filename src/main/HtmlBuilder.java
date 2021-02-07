package main;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class HtmlBuilder {

    private StringBuilder stringBuilder;

    public HtmlBuilder() {
    }

    public HtmlBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    @Contract(pure = true)
    private @NotNull String howItWork() {

        return "<div class=\"div1\"><details>" +
                "<summary style=\"text-align: center\"><samp class=\"samp sampBlack\">Как это работает?</samp></summary>" +
                "<div><p class=\"p1\">1. В u2020 во вкладке Maintenance выбрать \"MML Command\"." +
                "<p class=\"p1\">2. Слева в дереве NE выбрать БС(можно несколько)." +
                "<p class=\"p1\">3. Выполнить в консоли DSP SFP и LST RRUCHAIN" +
                "<p class=\"p1\">   (если 5G(R) то потребуется еще LST RRU)." +
                "<p class=\"p1\">4. Экспортировать результат в формате .csv" +
                "<p class=\"p1\">5. Открыть полученый файл .csv в приложении" +
                "<p class=\"p1\">(можно перетащить в окно программы).</div>" +
                "</details></div>";
    }


    @Contract(pure = true)
    private @NotNull String cssStyle() {
        return "h1 {font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12pt;font-weight: bold;text-align: center;padding: 2px;}" + "p {font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 10pt;text-align: left;padding: 2px;}" + ".p0 {font-weight: bold;background: lightblue;text-align: center;}" + ".p1 {font-weight: bold;}" + "samp {font-family: Verdana, Arial, Helvetica, sans-serif;font-weight: bold;font-size: 10pt;}" + ".sampGreen {color: green;}" + ".sampRed {color: red;}" + ".sampBlue {color: blue;}" + ".sampYellow {color: orange;}" + ".sampPurple {text-transform: uppercase;color: purple;}" + ".sampBlack {color: black;}" + "div {display: compact;flex-direction: column;padding: 8px;}" + ".div0 {border: 2px solid black;background-color: lightgoldenrodyellow;margin-top: 20px;margin-bottom: 20px;border-radius: 20px 20px 20px 20px;}" + ".div1 {border: 2px solid black;border-radius: 10px 10px 10px 10px;margin-top: 30px;background-color: lightgray;}" + ".div2 {padding: 2px;margin: 2px;}" + ".warningGreen {font-size: 14pt;color: green;}" + ".warningRed {font-size: 14pt;color: red;}" + ".warningYellow {font-size: 14pt;color: orange;}" + "[data-tooltip] {position: relative;}" + "[data-tooltip]::after {content: attr(data-tooltip);position: absolute;text-align: center;font-size: 9pt;white-space: nowrap;width: auto;font-weight: bold;left: 0;top: 0;background: white;color: black;padding: 0.2em;pointer-events: none;opacity: 0;transition: 0.5s;}" + "[data-tooltip]:hover::after {opacity: 1.0;top: 1em;left: -2em;}" + "details[open] div {animation: spoiler 0.5s;}" + "@keyframes spoiler { 0% {opacity: 0;} 100% {opacity: 1;} }";
    }


    @Contract(pure = true)
    private @NotNull String head() {

        return "<!DOCTYPE html>" +
                "<html lang=\"ru\">" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<title>sfp_attenuation_level</title>" +
                "<style>" +
                cssStyle() +
                "</style>" +
                "</head>";
    }

    public String summary_content(String sfp_sideSubRack0, String sfp_sub0, String sfp_slot0, String sfp_port0,
                                  String sfp_sideSubRack1, String sfp_sub1, String sfp_slot1, String sfp_port1,
                                  String sector0, String sector1, String diff, String speed, String mode
    ) {
        return sfp_sideSubRack0 + " (" + sfp_sub0 + ", " + sfp_slot0 + ", " + sfp_port0 + ") " +
                sector0 +
                "<-> " +
                sfp_sideSubRack1 + " (" + sfp_sub1 + ", " + sfp_slot1 + ", " + sfp_port1 + ") " +
                sector1 +
                " trx: " + diff +
                "tcr: " + speed +
                "mode: " + mode;
    }

    public String vols_attenuation_content_0(String sfp_sideSubRack0, String sfp_sub0, String sfp_slot0,
                                             String sfp_port0, String TRX1, String TRXd, String TRX2,
                                             String sfp_sideSubRack1, String sfp_sub1, String sfp_slot1,
                                             String sfp_port1
    ) {
        return "<p>" +
                sfp_sideSubRack0 + " (" + sfp_sub0 + ", " + sfp_slot0 + ", " + sfp_port0 + "); " +
                TRX1 +
                "(" +
                TRXd +
                ")" +
                TRX2 +
                sfp_sideSubRack1 + " (" + sfp_sub1 + ", " + sfp_slot1 + ", " + sfp_port1 + "); " +
                "</p>";
    }

    public String sfp_info_content(String sfp_sideSubRack0, String sfp_sub0, String sfp_slot0, String sfp_port0,
                                   String MN0, String TCR, String s0, String mode0, String WL0, String sfp_sideSubRack1,
                                   String sfp_sub1, String sfp_slot1, String sfp_port1, String MN1, String s1,
                                   String mode1, String WL1
    ) {
        String BBUsub = sfp_sideSubRack0 + " (" + sfp_sub0 + ", " + sfp_slot0 + ", " + sfp_port0 + ") ";
        String RRUsub = sfp_sideSubRack1 + " (" + sfp_sub1 + ", " + sfp_slot1 + ", " + sfp_port1 + ") ";

        String BBU = AAA(BBUsub, MN0, TCR, s0, mode0, WL0);
        String RRU = AAA(RRUsub, MN1, TCR, s1, mode1, WL1);

        return BBU + RRU;
    }

    public String AAA(String SUB,
                      String MN,
                      String TCR,
                      String s,
                      String mode,
                      String WL) {
        return "<p>" +
                SUB +
                MN + "; " +
                TCR + s + " Gbit/s; " +
                "mode: " + mode + ";" +
                "</p><p>" +
                "wavelength: " + WL + " nm;" +
                "</p>";
    }


    @Contract(pure = true)
    private @NotNull String footer() {
        return "<footer>" +
                js() +
                "</footer>" +
                "</html>";
    }

    @Contract(pure = true)
    private @NotNull String js() {
        return "<script type=\"text/javascript\">alert('Yo!');</script>";
    }

    public String htmlBuilderHow() {
        return head() + "<body>" + howItWork() + "</body>" + footer();
    }

    public String htmlBuilder() {
        return head() + "<body>" + howItWork() + stringBuilder.toString() + "</body>" + footer();
    }
}
