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
import main.classes.HtmlBuilder;
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


    public Controller() {}

    @FXML
    private void initialize() {}

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
            html();
        }
    }

    public void goHtmlHow() {
        String html = new HtmlBuilder(body).htmlBuilderHow();
        webEngine.loadContent(html);
    }

    public void html() {
        String html = new HtmlBuilder(body).htmlBuilder();
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
