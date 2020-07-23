package main;

import au.com.bytecode.opencsv.CSVReader;
import main.classes.BBU_Port;
import main.classes.DSP_SFP;
import main.classes.LST_RRUCHAIN;
import main.classes.RRU;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class ParseCSV {

    private static final String REGEX_HEAD = "^(?:\\[BTS)\\w+]";
    private static final String REGEX_DSP_SFP_BBU_SIDE = "^[\\[](?:[0],[ ]+(?:[0]),[ ]+[0-5],[ ]+[C-R]+,[ ]+[0-5],[ ]+(?:[\\w]+,[ ]+|[A-z]+ [A-z,.]+,[ ]+){22}(-?\\d+,[ ]+){6})[]]";
    private static final String REGEX_DSP_SFP_RRU_SIDE = "^[\\[](?:[0],[ ]+(?:[\\d][0-9]|[\\d][0-8][0-5]),[ ]+[0-5],[ ]+[C-R]+,[ ]+[0-5],[ ]+(?:[\\w]+,[ ]+|[A-z]+ [A-z,.]+,[ ]+){22}(-?\\d+,[ ]+){6})[]]";
    private static final String REGEX_LST_RRUCHAIN = "^[\\[](?:[\\d][0-9]|[\\d][0-8][0-5]),[ ]+(?:[\\w]+,[ ]+){2}(?:[\\d],[ ]+){5}(?:(?:[\\w]+|[\\w]+ [\\w]+),[ ]+){13}[0-9,A-Z,\\-]+,[ ]+[\\w]+,[ ]+[0-9,.,:,A-Z,&]+,[ ]+(?:[\\w]+,[ ]+){2}[]]";

    private static final String SM = "<samp data-tooltip=\"SINGLEMODE\">SM</samp>";
    private static final String MM = "<samp data-tooltip=\"MULTIMODE\">MM</samp>";


    private static final ArrayList<Result> arrResult = new ArrayList<>();
    private static final ArrayList<String> arrScanDoc = new ArrayList<>();
    private static final ArrayList<String> RESULT = new ArrayList<>();
    private static final DSP_SFP dsp_sfp = new DSP_SFP();
    private static final LST_RRUCHAIN lst_rruchain = new LST_RRUCHAIN();
    private static String Header;

    static void main(String args) {

        arrResult.clear();
        arrScanDoc.clear();
        dsp_sfp.getArrBBU_port().clear();
        dsp_sfp.getArrRRU().clear();
        lst_rruchain.getArrRRU().clear();
        RESULT.clear();

        try {
            CSVReader reader = new CSVReader(new FileReader(args), ',', '"', 1);
            ScanDoc(reader);
        } catch (FileNotFoundException e) {
            System.out.println("main.FileNotFoundException " + e.getMessage());
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
            System.out.println("ScanDoc.IOException: " + e.getMessage());
        }
    }

    private static void Filter(String[] row) {

        String s = Arrays.toString(row);
        if (Pattern.matches("^[\\[](?:BTS)\\w+[]]", s)) {
            Header = row[0];
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
        for (RRU rru : lst_rruchain.getArrRRU()) {
            for (BBU_Port bbu : dsp_sfp.getArrBBU_port()) {
                for (RRU rru1 : dsp_sfp.getArrRRU()) {
                    try {
                        String BtsNameBBU_dsp = bbu.getBTS_name();
                        int slotBBU_dsp = bbu.getSlot_BBU();
                        int portBBU_dsp = bbu.getPort_BBU();
                        double tx_bbu = bbu.getTx_BBU() * 0.01;
                        double rx_bbu = bbu.getRx_BBU() * 0.01;
                        String sfp_BBU = bbu.getSfp_BBU();
                        double sfp_BBU_Speed = bbu.getSfp_BBU_Speed();
                        String sfp_BBU_Mode = bbu.getSfp_Mode();

                        String BtsNameRRU_dsp = rru1.getBTS_name();
                        int subRackRRU_dsp = rru1.getSubRack_RRU();
                        double tx_rru = rru1.getTx_RRU() * 0.01;
                        double rx_rru = rru1.getRx_RRU() * 0.01;
                        String sfp_RRU = rru1.getSfp_RRU();
                        double sfp_RRU_Speed = rru1.getSfp_RRU_Speed();
                        String sfp_RRU_Mode = rru1.getSfp_Mode();

                        String BtsNameRRU_lst = rru.getBTS_name();
                        int subRackRRU_lst = rru.getSubRack_RRU();
                        int slotRRU_lst = rru.getSlot_RRU();
                        int portRRU_lst = rru.getPort_RRU();

                        /*Фильтруем данные*/
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
                                                sfp_RRU_Mode);
                                        arrResult.add(result);
                                    }
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("getRESULT.IndexOutOfBoundsException: " + e.getMessage());
                    }
                }
            }
        }

        /*Устанавливаем заголовок*/
        List<String> set = arrScanDoc.stream().distinct().collect(Collectors.toList());
        for (String s : set) {
            if (Pattern.matches(REGEX_HEAD, s)) {
                String head = "<h1>" + s + "</h1>\n";
                RESULT.add(head);
            }

            /*Добавляем все результаты для БС,
             относитеьно значения заголовка,
             устанавливаем порядковые номера*/
            ArrayList<String> BtsName = new ArrayList<>();
            for (Result result : arrResult) {
                String s2 = "[" + result.getBts_name() + "]";
                if (s.equals(s2)) {
                    BtsName.add(s);
                    RESULT.add("\n<p>" + BtsName.size() + ":</p>\n" + result.getResult());
                }
            }
        }
        /*Возвращаем результат*/
        return RESULT;
    }

    private static void itIsSideBBU(String[] row) {
        String s = Arrays.toString(row);
        if (Pattern.matches(REGEX_DSP_SFP_BBU_SIDE, s)) {
            int subRack_BBU = Integer.parseInt(row[1].trim());
            int slot_BBU = Integer.parseInt(row[2].trim());
            int port_BBU = Integer.parseInt(row[4].trim());
            String sfp_BBU = row[7].trim();
            String sfp_mode = row[9].trim().equals("SINGLEMODE") ? SM : MM;
            double sfp_BBU_speed = Integer.parseInt(row[12].trim());
            int tx_BBU = Integer.parseInt(row[27].trim());
            int rx_BBU = Integer.parseInt(row[28].trim());
            BBU_Port bbuPorts = new BBU_Port(Header, subRack_BBU, slot_BBU, port_BBU, tx_BBU, rx_BBU, sfp_BBU, sfp_BBU_speed, sfp_mode);
            dsp_sfp.setBBU_port(bbuPorts);
        }
    }

    private static void itIsSideRRU(String[] row) {
        String s = Arrays.toString(row);
        if (Pattern.matches(REGEX_DSP_SFP_RRU_SIDE, s)) {
            int subRack_RRU = Integer.parseInt(row[1].trim());
            int slot_RRU = Integer.parseInt(row[2].trim());
            int port_RRU = Integer.parseInt(row[4].trim());
            String sfp_RRU = row[7].trim();
            String sfp_mode = row[9].trim().equals("SINGLEMODE") ? SM : MM;
            double sfp_RRU_speed = Integer.parseInt(row[12].trim());
            int tx_RRU = Integer.parseInt(row[27].trim());
            int rx_RRU = Integer.parseInt(row[28].trim());
            RRU rru = new RRU(Header, subRack_RRU, slot_RRU, port_RRU, tx_RRU, rx_RRU, sfp_RRU, sfp_RRU_speed, sfp_mode);
            dsp_sfp.setRRU(rru);
        }
    }

    private static void ifChainNo(String[] row) {
        String s = Arrays.toString(row);
        if (Pattern.matches(REGEX_LST_RRUCHAIN, s)) {
            int subRack_RRU = (Integer.parseInt(row[0].trim()));
            int slot_RRU = (Integer.parseInt(row[5].trim()));
            int port_RRU = (Integer.parseInt(row[6].trim()));
            RRU rru = new RRU(Header, subRack_RRU, slot_RRU, port_RRU, 0, 0, null, 0, null);
            lst_rruchain.getArrRRU(rru);
        }
    }
}
