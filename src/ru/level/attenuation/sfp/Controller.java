package ru.level.attenuation.sfp;

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
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>sfp_attenuation_level</title>\n" +
                "    <style>\n" +
                "        h1 {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 11pt; /* Размер шрифта в пунктах */\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 10pt; /* Размер шрифта в пунктах */\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        samp {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 10pt; /* Размер шрифта в пунктах */\n" +
                "        }\n" +
                "\n" +
                "        samp0 {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 11pt; /* Размер шрифта в пунктах */\n" +
                "            color: green;\n" +
                "        }\n" +
                "\n" +
                "        samp1 {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 11pt; /* Размер шрифта в пунктах */\n" +
                "            color: red;\n" +
                "        }\n" +
                "\n" +
                "        samp2 {\n" +
                "            font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "            font-size: 10pt; /* Размер шрифта в пунктах */\n" +
                "            color: blue;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            height: 16px;\n" +
                "            width: 16px;\n" +
                "        }\n" +
                "\n" +
                "        #triangle-up-red {\n" +
                "            width: 0;\n" +
                "            height: 0;\n" +
                "            position: absolute;\n" +
                "            border-left: 8px solid transparent;\n" +
                "            border-right: 8px solid transparent;\n" +
                "            border-bottom: 16px solid red;\n" +
                "        }\n" +
                "\n" +
                "        #triangle-up-yellow {\n" +
                "            width: 0;\n" +
                "            height: 0;\n" +
                "            position: absolute;\n" +
                "            border-left: 8px solid transparent;\n" +
                "            border-right: 8px solid transparent;\n" +
                "            border-bottom: 16px solid orange;\n" +
                "        }\n" +
                "\n" +
                "        [data-tooltip] {\n" +
                "            position: relative; /* Относительное позиционирование */\n" +
                "        }\n" +
                "\n" +
                "        [data-tooltip]::after {\n" +
                "            content: attr(data-tooltip); /* Выводим текст */\n" +
                "            position: absolute; /* Абсолютное позиционирование */\n" +
                "            text-align: center;\n" +
                "            font-size: 8pt;\n" +
                "            white-space: nowrap; /* Переносов в тексте нет */\n" +
                "            width: auto; /* Ширина подсказки */\n" +
                "            font-weight: bold; /* Жирное начертание */\n" +
                "            left: 0;\n" +
                "            top: 0; /* Положение подсказки */\n" +
                "            background: #3989c9; /* Синий цвет фона */\n" +
                "            color: black; /* Цвет текста */\n" +
                "            padding: 0.3em; /* Поля вокруг текста */\n" +
                "            box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3); /* Параметры тени */\n" +
                "            pointer-events: none; /* Подсказка */\n" +
                "            opacity: 0; /* Подсказка невидима */\n" +
                "            transition: 0.5s; /* Время появления подсказки */\n" +
                "        }\n" +
                "\n" +
                "        [data-tooltip]:hover::after {\n" +
                "            opacity: 1.0; /* Показываем подсказку */\n" +
                "            top: 1.2em; /* Положение подсказки */\n" +
                "            left: -2em; /* Положение подсказки */\n" +
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
