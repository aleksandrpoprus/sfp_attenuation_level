package main.classes;

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

        return "<details>" +
                "<summary style=\"text-align: center\"><samp class=\"samp sampBlack\">Как это работает?</samp></summary>" +
                "<div><p class=\"p1\">1. В u2020 во вкладке Maintenance выбрать \"MML Command\"." +
                "<p class=\"p1\">2. Слева в дереве NE выбрать БС(можно несколько)." +
                "<p class=\"p1\">3. Выполнить в консоли DSP SFP и LST RRUCHAIN." +
                "<p class=\"p1\">4. Экспортировать результат в формате .csv" +
                "<p class=\"p1\">5. Открыть полученый файл .csv в приложении" +
                "<p class=\"p1\">(можно перетащить в окно программы).</div>" +
                "</details>";
    }

    @Contract(pure = true)
    private @NotNull String cssStyle() {
        return "h1 {\n" +
                "        font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "        font-size: 12pt;\n" +
                "        font-weight: bold;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "        font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "        font-size: 11pt;\n" +
                "        text-align: left;\n" +
                "    }\n" +
                "\n" +
                "    .p0 {\n" +
                "        font-weight: bold;\n" +
                "        background: lightblue;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .p1 {\n" +
                "        font-weight: bold;\n" +
                "    }\n" +
                "\n" +
                "    samp {\n" +
                "        font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "        font-weight: bold;\n" +
                "        font-size: 11pt;\n" +
                "    }\n" +
                "    \n" +
                "    .sampGreen {\n" +
                "        color: green;\n" +
                "    }\n" +
                "\n" +
                "    .sampRed {\n" +
                "        color: red;\n" +
                "    }\n" +
                "\n" +
                "    .sampBlue {\n" +
                "        color: blue;\n" +
                "    }\n" +
                "\n" +
                "    .sampYellow {\n" +
                "        color: orangered;\n" +
                "    }\n" +
                "\n" +
                "    .sampPurple {\n" +
                "        text-transform: uppercase;\n" +
                "        color: purple;\n" +
                "    }\n" +
                "\n" +
                "    .sampBlack {\n" +
                "        color: black;\n" +
                "    }\n" +
                "\n" +
                "    div {\n" +
                "        padding: .2rem;\n" +
                "        display: compact;\n" +
                "        flex-direction: column;\n" +
                "    }\n" +
                "\n" +
                "    .div0 {\n" +
                "        border: 2px solid black;\n" +
                "        background-color: lavender;\n" +
                "    }\n" +
                "\n" +
                "    .div1 {\n" +
                "        border: 2px solid black;\n" +
                "        background-color: #96aabe;\n" +
                "    }\n" +
                "\n" +
                "    .warningGreen {\n" +
                "        font-size: 14pt;\n" +
                "        color: green;\n" +
                "    }\n" +
                "\n" +
                "    .warningRed {\n" +
                "        font-size: 14pt;\n" +
                "        color: red;\n" +
                "    }\n" +
                "\n" +
                "    .warningYellow {\n" +
                "        font-size: 14pt;\n" +
                "        color: orangered;\n" +
                "    }\n" +
                "\n" +
                "    [data-tooltip] {\n" +
                "        position: relative;\n" +
                "    }\n" +
                "\n" +
                "    [data-tooltip]::after {\n" +
                "        content: attr(data-tooltip);\n" +
                "        position: absolute;\n" +
                "        text-align: center;\n" +
                "        font-size: 9pt;\n" +
                "        white-space: nowrap;\n" +
                "        width: auto;\n" +
                "        font-weight: bold;\n" +
                "        left: 0;\n" +
                "        top: 0;\n" +
                "        background: ghostwhite;\n" +
                "        color: black;\n" +
                "        padding: 0.2em;\n" +
                "        pointer-events: none;\n" +
                "        opacity: 0;\n" +
                "        transition: 0.5s;\n" +
                "    }\n" +
                "\n" +
                "    [data-tooltip]:hover::after {\n" +
                "        opacity: 1.0;\n" +
                "        top: 1em;\n" +
                "        left: -2em;\n" +
                "    }\n" +
                "\n" +
                "    details[open] div {\n" +
                "        animation: spoiler 0.5s;\n" +
                "    }\n" +
                "\n" +
                "    @keyframes spoiler {\n" +
                "        0% {\n" +
                "            opacity: 0;\n" +
                "        }\n" +
                "        100% {\n" +
                "            opacity: 1;\n" +
                "        }\n" +
                "    }";
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

    public String summary_content(String BBU_slot, String BBU_port, String RRU_sub, String sector, String diff, String speed, String mode) {
        return "BBU(" + BBU_slot + ", " + BBU_port + ") " +
                "<--> " +
                "RRU " + RRU_sub + " (" + sector + ") " +
                "trx: " + diff +
                "tcr: " + speed +
                "mode: " + mode;
    }

    public String vols_attenuation_content(String BBU_slot, String BBU_port, String TRX1, String TRXd, String TRX2, String RRU_sub) {
        return "BBU(" + BBU_slot + ", " + BBU_port + ") " +
                TRX1 +
                TRXd +
                TRX2 +
                "RRU (" + RRU_sub + ");";
    }

    public String sfp_info_content(String BBU_slot, String BBU_port, String MN0, String TCR, String s0, String mode0, String WL0,
                                   String RRU_sub, String MN1, String s1, String mode1, String WL1) {
        String BBUsub = "BBU(" + BBU_slot + ", " + BBU_port + ") ";
        String RRUsub = "RRU (" + RRU_sub + ") ";

        String BBU = AAA(BBUsub,MN0,TCR,s0,mode0,WL0);
        String RRU = AAA(RRUsub,MN1,TCR,s1,mode1,WL1);

        return BBU + "</p><p>" + RRU;
    }
    public String AAA(String SUB, String MN, String TCR, String s, String mode, String WL) {
        return SUB +
                MN + "; " +
                TCR + s + " Gbit/s; " +
                "mode: " + mode + ";" +
                "</p><p>" +
                "wavelength: " + WL + " nm;";
    }

    @Contract(pure = true)
    private @NotNull String footer() {
        return "<footer>" +
                "</footer>" +
                "</html>";
    }

    public String htmlBuilderHow() {
        return head() + "<body>" + howItWork() + "</body>" + footer();
    }

    public String htmlBuilder() {
        return head() + "<body>" + howItWork() + stringBuilder.toString() + "</body>" + footer();
    }
}
