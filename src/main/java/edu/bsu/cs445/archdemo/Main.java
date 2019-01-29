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


public class Main extends Application {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 275;
    private static final String PACKAGE_PREFIX = "edu/bsu/cs445/archdemo";
    private static final String MAIN_FXML_PATH = PACKAGE_PREFIX + "/searchPane.fxml";
    private static final String LOADING_FXML_PATH = PACKAGE_PREFIX + "/loading.fxml";

    @SuppressWarnings("unused") //It's not used because in this case we only use it to cause a scene change.
    private static ArtifactRecordCollection collection;

    @FXML
    @SuppressWarnings("unused") // This field is used by FXML, so suppress the warning
    private TextField searchField;

    @FXML
    @SuppressWarnings("unused") // This field is used by FXML, so suppress the warning
    private Button searchButton;

    @FXML
    @SuppressWarnings("unused") // This field is used by FXML, so suppress the warning
    private Label resultCount;

//    This could be useful if we don't want to create a new collection and use the Main's instance
//    This makes it hard to test though so I've left this here just in case I need to talk to Dr. G
//    static ArtifactRecordCollection getCollection() {
//        return collection;
//    }

    @Override
    public void start(Stage primaryStage) {
        createInitialScene(primaryStage);
        loadSearchPane(primaryStage);
    }

    private void createInitialScene(Stage stage) {
        Parent root;
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(LOADING_FXML_PATH);
            Preconditions.checkNotNull(url, "Cannot load fxml resource");
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Naive DOMA Search");
        stage.setScene(scene);
        stage.show();
    }

    private void loadSearchPane(Stage primaryStage) {
        JaxbParser parser = JaxbParser.create();
        InputStream owsleyStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("owsley.xml");
        CompletableFuture.runAsync(() -> collection = parser.parse(owsleyStream))
                .thenRun(() -> Platform.runLater(() -> {
                            try {
                                final String mainFxmlPath = MAIN_FXML_PATH;
                                URL url = Thread.currentThread().getContextClassLoader().getResource(mainFxmlPath);
                                Preconditions.checkNotNull(url, "Cannot load " + mainFxmlPath);
                                FXMLLoader fxmlLoader = new FXMLLoader(url);
                                Parent root = fxmlLoader.load();
                                primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
                            } catch (IOException ioe) {
                                throw new RuntimeException(ioe);
                            }
                        }
                ));
    }

    @SuppressWarnings("unused") // This method is actually used via searchPane.fxml.
    @FXML
    private void searchButtonClicked(){
        toggleUISearchFieldsDisabled(true);
        String searchTerm = searchField.getText();
        ArtifactRecordCollectionSearch CollectionSearch = new ArtifactRecordCollectionSearch();
        List<ArtifactRecord> result = CollectionSearch.search(searchTerm);
        resultCount.setText(String.valueOf(result.size()));
        toggleUISearchFieldsDisabled(false);
    }

    private void toggleUISearchFieldsDisabled(Boolean theBoolean) {
        searchButton.setDisable(theBoolean);
        searchField.setDisable(theBoolean);
    }
}
