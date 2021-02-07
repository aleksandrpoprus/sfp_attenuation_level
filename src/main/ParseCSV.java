package main;

import au.com.bytecode.opencsv.CSVReader;
import main.classes.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class ParseCSV {

    private static final String REGEX_HEAD = "(?:BTS)\\w+";
    private static final String REGEX_LST_RRU = "(?:\\d,[ ]+\\d+,[ ]+\\d+,[ ]+\\w+,[ ]+\\w+,[ ]+\\d+,[ ]+\\d,[ ]+\\w+,[ ]+\\w+,[ ]+\\d+,[ ]+\\d+,[ ]+\\w+,[ ]+\\w+,[ ]+\\d+,[ ]+\\d+,[ ]+\\d+,[ ]+\\d+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\d+,[ ]+\\w+,[ ]+\\d+\\-\\d+\\-\\d+[ ]+\\d+\\:\\d+:\\d+,[ ]+\\d+\\-\\d+\\-\\d+[ ]+\\d+\\:\\d+:\\d+,[ ]+\\w+,[ ]+\\w+,[ ]+\\d+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+(?:[\\w]+[\\:][\\w]+[\\&]){8}(?:[\\w\\.\\:]+),[ ]+\\w+,[ ]+\\w+,(?:[ ]+[\\w:]+,){2}[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+)";
    private static final String REGEX_DSP_SFP_RRU_SIDE = "(?:\\d,[ ]+\\d+,[ ]+\\d+,[ ]+\\w+,[ ]+\\d+,[ ]+(?:In Position),[ ]+(?:[\\w_]|[\\w])+,[ ]+(?:[\\w]+|[\\w]+[ ]+[\\w]+),[ ]+\\w+,[ ]+\\w+,[ ]+\\d+,[ ]+\\w+,[ ]+\\d+,[ ]+\\d+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+\\w+,[ ]+(?:\\d+|-\\d+),[ ]+\\d+,[ ]+\\d+,[ ]+\\d+,[ ]+\\d+,[ ]+\\d+,[ ]+\\d+,[ ]+\\d+,[ ]+\\d+,[ ]+(?:\\d+|-\\d+),[ ]+(?:\\d+|-\\d+),[ ]+\\d+,[ ]+(?:\\d+|-\\d+),[ ]+\\d+,[ ]+(?:\\d+|-\\d+),[ ]+)";
    private static final String REGEX_LST_RRUCHAIN = "(?:[\\d]|[\\d][0-9]|[\\d][0-8][0-5]),[ ]+(?:[\\w]+,[ ]+){2}(?:[\\d],[ ]+){5}(?:(?:[\\w]+|[\\w]+ [\\w]+),[ ]+){13}[0-9,A-Z\\-]+,[ ]+[\\w]+,[ ]+[0-9,.:A-Z&]+,[ ]+(?:[\\w]+,[ ]+){2}";

    private static final String SM = "<samp data-tooltip=\"SINGLEMODE\">SM</samp>";
    private static final String MM = "<samp data-tooltip=\"MULTIMODE\">MM</samp>";
    private static final String NULL = "<samp data-tooltip=\"NULL\">NULL</samp>";

    private static final ArrayList<Result> arrResult = new ArrayList<>();
    private static final ArrayList<String> arrScanDoc = new ArrayList<>();
    private static final ArrayList<String> RESULT = new ArrayList<>();

    private static final DSP_SFP dsp_sfp = new DSP_SFP();
    private static final LST_RRUCHAIN lst_rruchain = new LST_RRUCHAIN();
    private static final LST_RRU lst_rru = new LST_RRU();

    private static final List<Integer> RRUs = new ArrayList<>();

    private static String BTS_name;

    private static String REGEX(String regex) {
        return String.format("^[\\[]%s[]]", regex);
    }

    public static void main(String args) {

        arrResult.clear();
        arrScanDoc.clear();
        dsp_sfp.getArrSFP().clear();
        lst_rruchain.getArrChain().clear();
        lst_rru.getArrRRUs().clear();
        RRUs.clear();
        RESULT.clear();

        try {
            CSVReader reader = new CSVReader(new FileReader(args), ',', '"', 1);
            ScanDoc(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void ScanDoc(@NotNull CSVReader reader) {
        try {
            List<String[]> allRows = reader.readAll();
            for (String[] row : allRows) {
                if (!Arrays.toString(row).equals(Arrays.toString(new String[]{""}))) {
                    String[] s = Arrays.toString(row).trim().split(",");
                    arrScanDoc.add(s[0]);
                    Filter(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Filter(String[] row) {

        String s = Arrays.toString(row);
        if (Pattern.matches(REGEX(REGEX_HEAD), s)) {
            BTS_name = row[0];
        }
        createObjects(row);
    }

    public static ArrayList<String> getRESULT() {

        /*Мексиканская дуэль =)*/
        AAA();
        /*Устанавливаем заголовок*/
        List<String> set = arrScanDoc.stream().distinct().collect(Collectors.toList());
        for (String s : set) {
            if (Pattern.matches(REGEX(REGEX_HEAD), s)) {
                String head = "<div class=\"div1\"><h1>" + s + "</h1>\n";
                RESULT.add(head);

                /*Добавляем все результаты для БС,
             относитеьно значения заголовка,
             устанавливаем порядковые номера*/
                ArrayList<String> BtsName = new ArrayList<>();
                for (Result result : arrResult) {
                    String s2 = "[" + result.getBts_name() + "]";
                    if (s.equals(s2)) {
                        BtsName.add(s);
                        RESULT.add("<div class=\"div0\">" + result.getResult(BtsName.size()) + "</div>");
                    }
                }
                String tail = "</div>";
                RESULT.add(tail);
            }
        }
        /*Возвращаем результат*/
        return RESULT;
    }

    private static void AAA() {

        /*Проверка на обрыв оптики (или отключенеи RRU)*/
        if (lst_rruchain.getArrChain().size() != dsp_sfp.getArrSFP().size()) {
            AAAa();
        }

        /*Замена номера СHAIN из LST_RRUCHAIN на номер SubRack из LST_RRU*/
        if (!lst_rru.getArrRRUs().isEmpty()) {
            for (Chain chain : lst_rruchain.getArrChain()) {
                for (RRUs rrus : lst_rru.getArrRRUs()) {
                    if (chain.getBtsName().equals(rrus.getBtsName())) {
                        if (chain.getChainNo() == rrus.getChain()) {
                            chain.setChainNo(rrus.getSubRack());
                        }
                    }
                }
            }
        }

        SFP sfpHeadToRRU = null, sfpRRUtoHead = null, sfpRRUtoTail = null, sfpTailToRRU = null;
        for (Chain chain : lst_rruchain.getArrChain()) {
            for (SFP sfp : dsp_sfp.getArrSFP()) {
                if (chain.getBtsName().equals(sfp.getBtsName())) {
                    if (chain.getSubRack().equals(sfp.getSubRack()) & chain.getSlot().equals(sfp.getSlot()) & chain.getHeadPort().equals(sfp.getPort())) {
                        sfpHeadToRRU = sfp;
                    }

                    if (chain.getChainNo() == (sfp.getSubRack()) & chain.getTailSubRack() == null) {
                        sfpRRUtoHead = sfp;
                    }

                    if (chain.getChainNo() == (sfp.getSubRack()) & chain.getTailSubRack() != null) {
                        if (sfp.getPort() == 0) {
                            sfpRRUtoHead = sfp;
                        }

                        if (sfp.getPort() == 1) {
                            sfpRRUtoTail = sfp;
                        }
                    }
                    try {
                        assert chain.getTailSubRack() != null;
                        if (chain.getTailSubRack().equals(sfp.getSubRack()) & chain.getTailSlot().equals(sfp.getSlot()) & chain.getTailPort().equals(sfp.getPort())) {
                            sfpTailToRRU = sfp;
                        }
                    } catch (NullPointerException ignored) {
                    }
                }
            }

            if (sfpHeadToRRU != null & sfpRRUtoHead != null) {
                System.out.println("##################\n" +
                        chain.display() +
                        "\nsfpHeadToRRU=" + sfpHeadToRRU.display()
                        + "\nsfpRRUtoHead=" + sfpRRUtoHead.display());
                Result result = new Result(sfpHeadToRRU, sfpRRUtoHead);
                arrResult.add(result);
            }
            if (sfpRRUtoTail != null & sfpTailToRRU != null) {
                System.out.println("sfpRRUtoTail=" + sfpRRUtoTail.display());
                System.out.println("sfpTailToRRU=" + sfpTailToRRU.display());
                Result result = new Result(sfpRRUtoTail, sfpTailToRRU);
                arrResult.add(result);
            }

            sfpRRUtoTail = null;
            sfpTailToRRU = null;
        }
    }

    private static void AAAa() {

        RRUs.clear();

        for (Chain chain : lst_rruchain.getArrChain()) {
            RRUs.add(chain.getSubRack());
        }
        for (SFP SFP1 : dsp_sfp.getArrSFP()) {
            RRUs.add(SFP1.getSubRack());
        }

        List<Integer> rrus = RRUs.stream().distinct().collect(Collectors.toList());

        for (int i = 0; i < rrus.size(); i++) {
            for (SFP SFP : dsp_sfp.getArrSFP()) {
                try {
                    if (SFP.getSubRack().equals(rrus.get(i))) {
                        rrus.remove(i);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("IndexOutOfBoundsException=" + e.getMessage());
                }
            }
        }

        for (int i : rrus) {
            dsp_sfp.setSFP(new SFP(BTS_name, "", i, 0, 0, 0, 0, NULL, 0, null, 0
            ));
        }
    }

    private static @NotNull String sideSubRack(String @NotNull [] row) {
        return Integer.parseInt(row[1].trim()) == 0 ? "BBU" : "RRU";
    }

    private static int subRack(String @NotNull [] row) {
        return Integer.parseInt(row[1].trim());
    }

    private static int slot(String @NotNull [] row) {
        return Integer.parseInt(row[2].trim());
    }

    private static int port(String @NotNull [] row) {
        return Integer.parseInt(row[4].trim());
    }

    private static int chainNo(String @NotNull [] row) {
        return Integer.parseInt(row[0].trim());
    }

    private static int headSubRack(String @NotNull [] row) {
        return Integer.parseInt(row[4].trim());
    }

    private static int headSlot(String @NotNull [] row) {
        return Integer.parseInt(row[5].trim());
    }

    private static int headPort(String @NotNull [] row) {
        return Integer.parseInt(row[6].trim());
    }

    private static @Nullable Integer tailSubRack(String @NotNull [] row) {
        if (!(row[9].trim()).equals("NULL")) {
            return Integer.parseInt(row[9].trim());
        } else {
            return null;
        }
    }

    private static @Nullable Integer tailSlot(String @NotNull [] row) {
        if (!(row[10].trim()).equals("NULL")) {
            return Integer.parseInt(row[10].trim());
        } else {
            return null;
        }
    }

    private static @Nullable Integer tailPort(String @NotNull [] row) {
        if (!(row[11].trim()).equals("NULL")) {
            return Integer.parseInt(row[11].trim());
        } else {
            return null;
        }
    }

    private static int chain(String @NotNull [] row) {
        return Integer.parseInt(row[5].trim());
    }

    @Contract(pure = true)
    private static @NotNull String sfp_manufacturer_name(String @NotNull [] row) {
        return row[7].trim();
    }

    private static String sfp_mode(String @NotNull [] row) {
        return row[9].trim().equals("SINGLEMODE") ? SM : MM;
    }

    private static int sfp_wave_length(String @NotNull [] row) {
        return Integer.parseInt(row[10].trim()) / 100;
    }

    private static double sfp_speed(String @NotNull [] row) {
        double d = Integer.parseInt(row[12].trim());
        return d / 10;
    }

    private static double tx(String @NotNull [] row) {
        double d = Integer.parseInt(row[27].trim());
        return d / 100;
    }

    private static double rx(String @NotNull [] row) {
        double d = Integer.parseInt(row[28].trim());
        return d / 100;
    }

    private static void createObjects(String @NotNull [] row) {

        if (row.length == 34) {
            String s = Arrays.toString(row);
            if (Pattern.matches(REGEX(REGEX_DSP_SFP_RRU_SIDE), s)) {
                SFP sfp = new SFP(BTS_name, sideSubRack(row), subRack(row), slot(row), port(row), tx(row), rx(row), sfp_manufacturer_name(row), sfp_speed(row), sfp_mode(row), sfp_wave_length(row));
                dsp_sfp.setSFP(sfp);
            }
        }

        if (row.length == 27) {
            String s1 = Arrays.toString(row);
            if (row[1].trim().equals("LOADBALANCE")) {
                if (Pattern.matches(REGEX(REGEX_LST_RRUCHAIN), s1)) {
                    Chain chain = new Chain(BTS_name, chainNo(row), headSubRack(row), headSlot(row), headPort(row), tailSubRack(row), tailSlot(row), tailPort(row));
                    lst_rruchain.getArrChain(chain);
                }
            }

            if (row[1].trim().equals("CHAIN")) {
                if (Pattern.matches(REGEX(REGEX_LST_RRUCHAIN), s1)) {
                    Chain chain = new Chain(BTS_name, chainNo(row), headSubRack(row), headSlot(row), headPort(row), tailSubRack(row), tailSlot(row), tailPort(row));
                    lst_rruchain.getArrChain(chain);
                }
            }
        }

        if (row.length == 47) {
            String s0 = Arrays.toString(row);
            if (Pattern.matches(REGEX(REGEX_LST_RRU), s0)) {
                main.classes.RRUs RRUs = new RRUs(BTS_name, subRack(row), slot(row), chain(row));
                lst_rru.getArrRRUs(RRUs);
            }
        }
    }

}
