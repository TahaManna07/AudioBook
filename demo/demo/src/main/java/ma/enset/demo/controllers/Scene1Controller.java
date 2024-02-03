package ma.enset.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class Scene1Controller {

    public Button DowloadPage;
    @FXML
    private Button SelectPDF;

    @FXML
    private Label label;

    @FXML
    private Button newScene;

    @FXML
    private BorderPane principal;
    private String path;

    @FXML
    void selectPdf(ActionEvent event) {
        path = openFileChooser();
    }

    private String openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());
        }
        return selectedFile.getAbsolutePath();
    }

    @FXML
    void downloadPage(ActionEvent event) throws IOException {
        principal.getChildren().clear();

        // Load scene1.fxml using FXMLLoader
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/ma/enset/demo/ToDownLoadPage.fxml"));
        BorderPane scene2Root = fxmlLoader.load();

        Scene2Controller scene2Controller = fxmlLoader.getController();
        scene2Controller.setPdfFilePath(path);
        // Set the new content in the center of the principal BorderPane
        principal.setCenter(scene2Root);
    }
}
