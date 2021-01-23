package main;

import au.com.bytecode.opencsv.CSVReader;
import main.classes.*;

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
    private static final String REGEX_DSP_SFP_BBU_SIDE = "(?:[0],[ ]+(?:[0]),[ ]+[0-5],[ ]+[C-R]+,[ ]+[0-5],[ ]+(?:[\\w]+,[ ]+|[A-z]+ [A-z,.]+,[ ]+){22}(-?\\d+,[ ]+){6})";
    private static final String REGEX_DSP_SFP_RRU_SIDE = "(?:[0],[ ]+(?:[\\d][0-9]|[\\d][0-8][0-5]),[ ]+[0-5],[ ]+[C-R]+,[ ]+[0-5],[ ]+(?:[\\w]+,[ ]+|[A-z]+ [A-z,.]+,[ ]+){13}(-?\\d+,[ ]+){15})";
    private static final String REGEX_LST_RRUCHAIN = "(?:[\\d]|[\\d][0-9]|[\\d][0-8][0-5]),[ ]+(?:[\\w]+,[ ]+){2}(?:[\\d],[ ]+){5}(?:(?:[\\w]+|[\\w]+ [\\w]+),[ ]+){13}[0-9,A-Z\\-]+,[ ]+[\\w]+,[ ]+[0-9,.:A-Z&]+,[ ]+(?:[\\w]+,[ ]+){2}";

    private static final String SM = "<samp data-tooltip=\"SINGLEMODE\">SM</samp>";
    private static final String MM = "<samp data-tooltip=\"MULTIMODE\">MM</samp>";
    private static final String NULL = "<samp data-tooltip=\"NULL\">NULL</samp>";


    private static final ArrayList<Result> arrResult = new ArrayList<>();
    private static final ArrayList<String> arrScanDoc = new ArrayList<>();
    private static final ArrayList<String> RESULT = new ArrayList<>();
    private static final DSP_SFP dsp_sfp = new DSP_SFP();
    private static final LST_RRUCHAIN lst_rruchain = new LST_RRUCHAIN();
    private static final List<Integer> RRUs = new ArrayList<>();
    private static String BTS_name;
    private static String BtsNameBBU_dsp;
    private static int slotBBU_dsp;
    private static int portBBU_dsp;
    private static int tx_bbu;
    private static int rx_bbu;
    private static String sfp_BBU;
    private static int sfp_BBU_Speed;
    private static String sfp_BBU_Mode;
    private static int sfp_BBU_Wave_Length;
    private static String BtsNameRRU_dsp;
    private static int subRackRRU_dsp;
    private static int tx_rru;
    private static int rx_rru;
    private static String sfp_RRU;
    private static int sfp_RRU_Speed;
    private static String sfp_RRU_Mode;
    private static int sfp_RRU_Wave_Length;
    private static String BtsNameRRU_lst;
    private static int subRackRRU_lst;
    private static int slotRRU_lst;
    private static int portRRU_lst;

    private static String REGEX(String regex) {
        return String.format("^[\\[]%s[]]", regex);
    }


    public static void main(String args) {

        arrResult.clear();
        arrScanDoc.clear();
        dsp_sfp.getArrBBU_port().clear();
        dsp_sfp.getArrRRU().clear();
        lst_rruchain.getArrRRU().clear();
        RRUs.clear();
        RESULT.clear();

        try {
            CSVReader reader = new CSVReader(new FileReader(args), ',', '"', 1);
            ScanDoc(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void ScanDoc(CSVReader reader) {
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
        if (Pattern.matches("^[\\[](?:BTS)\\w+[]]", s)) {
            BTS_name = row[0];
        }

        if (row.length == 34) {
            itIsSideBBU(row);
            itIsSideRRU(row);
        }

        if (row.length == 27) {
            ifChainNo(row);
        }
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
        if (lst_rruchain.getArrRRU().size() != dsp_sfp.getArrRRU().size()) {
            AAAa();
        }

        for (RRU rru : lst_rruchain.getArrRRU()) {
            for (BBU_Port bbu : dsp_sfp.getArrBBU_port()) {
                for (RRU rru1 : dsp_sfp.getArrRRU()) {
                    try {
                        BtsNameBBU_dsp = bbu.getBTS_name();
                        slotBBU_dsp = bbu.getSlot();
                        portBBU_dsp = bbu.getPort();
                        tx_bbu = bbu.getTx();
                        rx_bbu = bbu.getRx();
                        sfp_BBU = bbu.getSfpManufacturerName();
                        sfp_BBU_Speed = bbu.getSfp_Speed();
                        sfp_BBU_Mode = bbu.getSfp_Mode();
                        sfp_BBU_Wave_Length = bbu.getSfp_Wave_Length();

                        BtsNameRRU_dsp = rru1.getBTS_name();
                        subRackRRU_dsp = rru1.getSubRack();
                        tx_rru = rru1.getTx();
                        rx_rru = rru1.getRx();
                        sfp_RRU = rru1.getSfpManufacturerName();
                        sfp_RRU_Speed = rru1.getSfp_Speed();
                        sfp_RRU_Mode = rru1.getSfp_Mode();
                        sfp_RRU_Wave_Length = rru1.getSfp_Wave_Length();

                        BtsNameRRU_lst = rru.getBTS_name();
                        subRackRRU_lst = rru.getSubRack();
                        slotRRU_lst = rru.getSlot();
                        portRRU_lst = rru.getPort();

                        /*Фильтруем данные*/
                        AAAb();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void AAAa() {

        RRUs.clear();

        for (RRU rru : lst_rruchain.getArrRRU()) {
            RRUs.add(rru.getSubRack());
        }
        for (RRU rru1 : dsp_sfp.getArrRRU()) {
            RRUs.add(rru1.getSubRack());
        }

        List<Integer> rrus = RRUs.stream().distinct().collect(Collectors.toList());

        for (int i = 0; i < rrus.size(); i++) {
            for (RRU rru : dsp_sfp.getArrRRU()) {
                try {
                    if (rru.getSubRack() == rrus.get(i)) {
                        rrus.remove(i);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("");
                }
            }
        }

        for (int i : rrus) {
            dsp_sfp.setRRU(new RRU(BTS_name, i, 0, 1, 0, 0, null, 0, NULL, 0));
        }
    }

    private static void AAAb() {
        if (BtsNameBBU_dsp.equals(BtsNameRRU_dsp) && BtsNameRRU_dsp.equals(BtsNameRRU_lst)) {
            if (subRackRRU_dsp == subRackRRU_lst) {
                if (slotBBU_dsp == slotRRU_lst) {
                    if (portBBU_dsp == portRRU_lst) {
                        Result result = new Result();
                        result.setForResult(BtsNameRRU_lst,
                                slotBBU_dsp, portBBU_dsp,
                                tx_bbu,
                                rx_bbu,
                                subRackRRU_lst,
                                tx_rru,
                                rx_rru,
                                sfp_BBU,
                                sfp_BBU_Speed,
                                sfp_RRU,
                                sfp_RRU_Speed,
                                sfp_BBU_Mode,
                                sfp_RRU_Mode,
                                sfp_BBU_Wave_Length,
                                sfp_RRU_Wave_Length);
                        arrResult.add(result);
                    }
                }
            }
        }
    }

    private static int subRack(String[] row, boolean b) {
        return b ? Integer.parseInt(row[1].trim()) : Integer.parseInt(row[0].trim());
    }

    private static int slot(String[] row, boolean b) {
        return b ? Integer.parseInt(row[2].trim()) : Integer.parseInt(row[5].trim());

    }

    private static int port(String[] row, boolean b) {
        return b ? Integer.parseInt(row[4].trim()) : Integer.parseInt(row[6].trim());

    }

    private static String sfp_manufacturer_name(String[] row) {
        return row[7].trim();
    }

    private static String sfp_mode(String[] row) {
        return row[9].trim().equals("SINGLEMODE") ? SM : MM;
    }

    private static int sfp_wave_length(String[] row) {
        return Integer.parseInt(row[10].trim());
    }

    private static int sfp_speed(String[] row) {
        return Integer.parseInt(row[12].trim());
    }

    private static int tx(String[] row) {
        return Integer.parseInt(row[27].trim());
    }

    private static int rx(String[] row) {
        return Integer.parseInt(row[28].trim());
    }

    private static void itIsSideBBU(String[] row) {
        String s = Arrays.toString(row);
        if (Pattern.matches(REGEX(REGEX_DSP_SFP_BBU_SIDE), s)) {
            BBU_Port bbuPorts = new BBU_Port(BTS_name, subRack(row, true), slot(row, true), port(row, true),
                    tx(row), rx(row),
                    sfp_manufacturer_name(row), sfp_speed(row), sfp_mode(row), sfp_wave_length(row));
            dsp_sfp.setBBU_port(bbuPorts);
        }
    }

    private static void itIsSideRRU(String[] row) {
        String s = Arrays.toString(row);
        if (Pattern.matches(REGEX(REGEX_DSP_SFP_RRU_SIDE), s)) {
            RRU rru = new RRU(BTS_name, subRack(row, true), slot(row, true), port(row, true), tx(row), rx(row),
                    sfp_manufacturer_name(row), sfp_speed(row), sfp_mode(row), sfp_wave_length(row));
            dsp_sfp.setRRU(rru);
        }
    }

    private static void ifChainNo(String[] row) {
        String s = Arrays.toString(row);
        if (Pattern.matches(REGEX(REGEX_LST_RRUCHAIN), s)) {
            RRU rru = new RRU(BTS_name, subRack(row, false), slot(row, false), port(row, false),
                    0, 0,
                    null, 0, null, 0);
            lst_rruchain.getArrRRU(rru);
        }
    }
}
