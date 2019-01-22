package edu.bsu.cs445.archdemo;

import com.google.common.base.Preconditions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Main extends Application {

    @FXML
    @SuppressWarnings("unused") // This field is used by FXML, so suppress the warning
    private TextField searchField;

    @FXML
    @SuppressWarnings("unused") // This field is used by FXML, so suppress the warning
    private Button searchButton;

    @FXML
    @SuppressWarnings("unused") // This field is used by FXML, so suppress the warning
    private Label resultCount;

    private ArtifactRecordCollection collection;

    @Override
    public void start(Stage primaryStage) {
        createInitialScene(primaryStage);
        JaxbParser parser = JaxbParser.create();
        InputStream owsleyStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("owsley.xml");
        CompletableFuture.runAsync(() -> collection = parser.parse(owsleyStream))
                .thenRun(() -> Platform.runLater(() -> {
                            try {
                                final String mainFxmlPath = "edu/bsu/cs445/archdemo/main.fxml";
                                URL url = Thread.currentThread().getContextClassLoader().getResource(mainFxmlPath);
                                Preconditions.checkNotNull(url, "Cannot load " + mainFxmlPath);
                                FXMLLoader fxmlLoader = new FXMLLoader(url);
                                fxmlLoader.setController(Main.this);
                                Parent root = fxmlLoader.load();
                                primaryStage.setScene(new Scene(root, 300, 275));
                            } catch (IOException ioe) {
                                throw new RuntimeException(ioe);
                            }
                        }
                ));
    }

    private void createInitialScene(Stage stage) {
        Parent root;
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("edu/bsu/cs445/archdemo/loading.fxml");
            Preconditions.checkNotNull(url, "Cannot load fxml resource");
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 300, 275);
        stage.setTitle("Naive DOMA Search");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void search() {
        Preconditions.checkNotNull(collection, "The collection should already be in memory");
        searchButton.setDisable(true);
        searchField.setDisable(true);
        String searchTerm = searchField.getText();
        List<ArtifactRecord> result = collection.stream()
                .filter(artifactRecord -> artifactRecord.getTitle().contains(searchTerm))
                .collect(Collectors.toList());
        resultCount.setText(String.valueOf(result.size()));
        searchButton.setDisable(false);
        searchField.setDisable(false);
    }
}
