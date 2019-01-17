package edu.bsu.cs445.archdemo;

import com.google.common.base.Preconditions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        createInitialScene(primaryStage);
    }

    private void createInitialScene(Stage stage) {
        Parent root;
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("edu/bsu/cs445/archdemo/main.fxml");
            Preconditions.checkNotNull(url, "Cannot load fxml resource");
            root = FXMLLoader.load(url);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        Scene scene = new Scene(root, 300,275);
        stage.setTitle("Naive DOMA Search");
        stage.setScene(scene);
        stage.show();
    }
}
