package main.classes;


public class HtmlBuilder {

    private StringBuilder stringBuilder;

    public HtmlBuilder() {
    }

    public HtmlBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    private String howItWork() {

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


    private String cssStyle() {
        return "h1 {\n" +
                "        font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "        font-size: 12pt;\n" +
                "        font-weight: bold;\n" +
                "        text-align: center;\n" +
                "        padding: 2px;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "        font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "        font-size: 10pt;\n" +
                "        text-align: left;\n" +
                "        padding: 2px;\n" +
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
                "        font-size: 10pt;\n" +
                "    }\n" +
                "\n" +
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
                "        color: orange;\n" +
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
                "        display: compact;\n" +
                "        flex-direction: column;\n" +
                "        padding: 8px;\n" +
                "    }\n" +
                "\n" +
                "    .div0 {\n" +
                "        border: 2px solid black;\n" +
                "        background-color: lightgoldenrodyellow;\n" +
                "        margin-top: 20px;\n" +
                "        margin-bottom: 20px;\n" +
                "        border-radius: 20px 20px 20px 20px;\n" +
                "    }\n" +
                "\n" +
                "    .div1 {\n" +
                "        border: 2px solid black;\n" +
                "        border-radius: 10px 10px 10px 10px;\n" +
                "        margin-top: 30px;\n" +
                "        background-color: lightgray;\n" +
                "    }\n" +
                "\n" +
                "    .div2 {\n" +
                "        padding: 2px;\n" +
                "        margin: 2px;\n" +
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
                "        color: orange;\n" +
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
                "        background: white;\n" +
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


    private String head() {

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

    public String summary_content(
            String BBU_sideSubRack,
            String BBU_sub,
            String BBU_slot,
            String BBU_port,
            String RRU_sideSubRack,
            String RRU_sub,
            String RRU_slot,
            String RRU_port,
            String sector,
            String diff,
            String speed,
            String mode
    ) {
        return BBU_sideSubRack + " (" + BBU_sub + ", " + BBU_slot + ", " + BBU_port + ") " +
                "<--> " +
                RRU_sideSubRack + " (" + RRU_sub + ", " + RRU_slot + ", " + RRU_port + ") " +
                "trx: " + diff +
                "tcr: " + speed +
                "mode: " + mode;
    }

    public String vols_attenuation_content_0(
            String BBU_sideSubRack,
            String BBU_sub,
            String BBU_slot,
            String BBU_port,
            String TRX1,
            String TRXd,
            String TRX2,
            String RRU_sideSubRack,
            String RRU_sub,
            String RRU_slot,
            String RRU_port
    ) {
        return "<p>" +
                BBU_sideSubRack + " (" + BBU_sub + ", " + BBU_slot + ", " + BBU_port + ") " +
                TRX1 +
                "(" +
                TRXd +
                ")" +
                TRX2 +
                RRU_sideSubRack + " (" + RRU_sub + ", " + RRU_slot + ", " + RRU_port + ") " +
                "</p>";
    }

    public String sfp_info_content(
            String BBU_sideSubRack,
            String BBU_sub,
            String BBU_slot,
            String BBU_port,
            String MN0,
            String TCR,
            String s0,
            String mode0,
            String WL0,
            String RRU_sideSubRack,
            String RRU_sub,
            String RRU_slot,
            String RRU_port,
            String MN1,
            String s1,
            String mode1,
            String WL1
    ) {
        String BBUsub = BBU_sideSubRack + " (" + BBU_sub + ", " + BBU_slot + ", " + BBU_port + ") ";
        String RRUsub = RRU_sideSubRack + " (" + RRU_sub + ", " + RRU_slot + ", " + RRU_port + ") ";

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


    private String footer() {
        return "<footer>" +
                js() +
                "</footer>" +
                "</html>";
    }

    private String js() {
        return "<script type=\"text/javascript\">alert('Yo!');</script>\n";
    }

    public String htmlBuilderHow() {
        return head() + "<body>" + howItWork() + "</body>" + footer();
    }

    public String htmlBuilder() {
        return head() + "<body>" + howItWork() + stringBuilder.toString() + "</body>" + footer();
    }
}
