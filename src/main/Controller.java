package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class Controller {

    private static final List<String> strings = new ArrayList<>();
    private final FileChooser fileChooser = new FileChooser();
    private final List<String> validExtensions = Collections.singletonList("csv");
    private final StringBuilder body = new StringBuilder();

    @FXML
    public VBox vBox;
    @FXML
    public CheckBox check_box;
    @FXML
    public Button buttonCSV;
    @FXML
    public WebView webView;
    @FXML
    public WebEngine webEngine;


    public Controller() {
    }

    @FXML
    private void initialize() {
    }

    private void printLog(List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }

        for (File file : files) {
            setFilePath(file);
            ParseCSV.main(file.getAbsolutePath());
            strings.clear();
            strings.addAll(ParseCSV.getRESULT());
            printResult();
        }
    }

    private void printResult() {
        if (!strings.isEmpty()) {
            if (check_box.isSelected()) body.setLength(0);
            for (String string : strings) {
                body.append(string);
            }
            htmlBuilder();
        }
    }

    private void htmlBuilder() {

        String header = "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>sfp_attenuation_level</title>\n" +
                "    <style>\n" +
                "        h1 {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 12pt;\n" +
                "            font-weight: bold;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 11pt;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        .p0 {\n" +
                "            font-size: 11pt;\n" +
                "            font-weight: bold;\n" +
                "            background: lightblue;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        samp {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 11pt;\n" +
                "        }\n" +
                "\n" +
                "        .samp{\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-weight: bold;\n" +
                "            font-size: 11pt;\n" +
                "        }\n" +
                "\n" +
                "        .sampGreen {\n" +
                "\n" +
                "            color: green;\n" +
                "        }\n" +
                "\n" +
                "        .sampRed {\n" +
                "\n" +
                "            color: red;\n" +
                "        }\n" +
                "\n" +
                "        .sampBlue {\n" +
                "\n" +
                "            color: blue;\n" +
                "        }\n" +
                "\n" +
                "        .sampYellow {\n" +
                "\n" +
                "            color: #ff5900;\n" +
                "        }\n" +
                "\n" +
                "        .sampPurple {\n" +
                "            text-transform: uppercase;\n" +
                "\n" +
                "            color: purple;\n" +
                "        }\n" +
                "\n" +
                "        .sampBlack {\n" +
                "\n" +
                "            color: black;\n" +
                "        }\n" +
                "\n" +
                "        .div {\n" +
                "            border: 2px solid black;\n" +
                "            background-color: lavender;\n" +
                "            padding: .2rem;\n" +
                "            display: compact;\n" +
                "            flex-direction: column;\n" +
                "        }\n" +
                "\n" +
                "        .warningGreen {\n" +
                "            font-size: 14pt;\n" +
                "            color: green;\n" +
                "        }\n" +
                "\n" +
                "        .warningRed {\n" +
                "            font-size: 14pt;\n" +
                "            color: red;\n" +
                "        }\n" +
                "\n" +
                "        .warningYellow {\n" +
                "            font-size: 14pt;\n" +
                "            color: #ff5900;\n" +
                "        }\n" +
                "\n" +
                "        [data-tooltip] {\n" +
                "            position: relative;\n" +
                "        }\n" +
                "\n" +
                "        [data-tooltip]::after {\n" +
                "            content: attr(data-tooltip);\n" +
                "            position: absolute;\n" +
                "            text-align: center;\n" +
                "            font-size: 9pt;\n" +
                "            white-space: nowrap;\n" +
                "            width: auto;\n" +
                "            font-weight: bold;\n" +
                "            left: 0;\n" +
                "            top: 0;\n" +
                "            background: ghostwhite;\n" +
                "            color: black;\n" +
                "            padding: 0.2em;\n" +
                "            pointer-events: none;\n" +
                "            opacity: 0;\n" +
                "            transition: 0.5s;\n" +
                "        }\n" +
                "\n" +
                "        [data-tooltip]:hover::after {\n" +
                "            opacity: 1.0;\n" +
                "            top: 1em;\n" +
                "            left: -2em;\n" +
                "        }\n" +
                "\n" +
                "        details[open] div {\n" +
                "            animation: spoiler 0.5s;\n" +
                "        }\n" +
                "\n" +
                "        @keyframes spoiler {\n" +
                "            0% {\n" +
                "                opacity: 0;\n" +
                "            }\n" +
                "            100% {\n" +
                "                opacity: 1;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n";
        String footer = "</body>\n" +
                "<footer>\n" +
                "</footer>\n" +
                "</html>";

        String html = header + body.toString() + footer;

        webEngine.loadContent(html);
        System.out.println(html);

    }

    public void onDragDropped(@NotNull DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        boolean success = false;

        if (dragboard.hasFiles()) {
            getDragEvent(dragboard, dragEvent);
            printResult();
            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    private @NotNull String getExtension(@NotNull String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            return fileName.substring(i + 1).toLowerCase();
        }
        return extension;
    }

    private void getDragEvent(Dragboard dragboard, @NotNull DragEvent dragEvent) {
        if (validExtensions.containsAll(dragEvent.getDragboard().getFiles().stream().map(file -> getExtension(file.getName())).collect(Collectors.toList()))) {
            for (int i = 0; i < dragboard.getFiles().size(); i++) {
                ParseCSV.main(dragboard.getFiles().get(i).getAbsolutePath());
            }
            strings.clear();
            strings.addAll(ParseCSV.getRESULT());
            dragEvent.consume();
        }
    }

    void getFileChooser(Stage stage) {
        if (getFilePath().exists()) {
            fileChooser.setInitialDirectory(new File(getFilePath().getParent()));
        } else {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        printLog(files);
    }

    @Contract(" -> new")
    private @NotNull File getFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return new File(System.getProperty("user.dir"));
        }
    }

    private void setFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            String s = file.getAbsolutePath();
            prefs.put("filePath", s);
        }
    }
}
