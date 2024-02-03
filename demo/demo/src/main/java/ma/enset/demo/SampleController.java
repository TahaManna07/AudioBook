package ma.enset.demo;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class SampleController {

    @FXML
    private Button SelectPaper;

    @FXML
    private Button SelectPdf;

    @FXML
    private Button SelectWeb;

    @FXML
    private Button books;

    @FXML
    private Button copyText;

    @FXML
    private Button home;

    @FXML
    private Label label;

    @FXML
    private ImageView logo;

    @FXML
    private VBox menu;

    @FXML
    private ImageView next;

    @FXML
    private Button notes;

    @FXML
    private ImageView play;

    @FXML
    private ImageView prev;

    @FXML
    private BorderPane principal;

    @FXML
    private Rectangle rec;

    @FXML
    void Play(MouseEvent event) {

    }

    @FXML
    void copyText(ActionEvent event) {
        to("Text_to_audioBook");

    }

    @FXML
    void selectPaper(ActionEvent event) {

    }

    @FXML
    void selectPdf(ActionEvent event) {
        to("scene1");
    }

    @FXML
    void selectWeb(ActionEvent event)  {
        to("WebPdf_to_audioBook");
    }

    @FXML
    void toHome(ActionEvent event) {
        to("default");

    }

    @FXML
    void toLibrary(ActionEvent event) {
        to("Library");

    }

    @FXML
    void toNotes(ActionEvent event) {
        to("AllNotes");

    }
    private void to(String sceneName)  {

        try {
            principal.setTop(null);
            principal.setCenter(null);

            // Load scene1.fxml using FXMLLoader
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(sceneName+".fxml"));
            // Set the new content in the center of the principal BorderPane
            principal.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
