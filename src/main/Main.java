package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public Parent root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));

        try {
            root = loader.load();
        } catch (NullPointerException | IOException e) {
            System.out.println("start" + e.getMessage());
        }

        Controller controller = loader.getController();
        controller.buttonCSV.setOnAction(event -> controller.getFileChooser(stage));
        controller.webEngine = controller.webView.getEngine();
        controller.webView.setOnDragDropped(controller::onDragDropped);

        Scene scene = new Scene(root, 600, 640);
        stage.setScene(scene);

        stage.setTitle("Проверка уровней затухания SFP");
        stage.setResizable(true);
        stage.setMaxWidth(600);
        stage.setMinWidth(600);
        stage.setMinHeight(200);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("res/images/icon.png")));

        stage.show();
    }
}
