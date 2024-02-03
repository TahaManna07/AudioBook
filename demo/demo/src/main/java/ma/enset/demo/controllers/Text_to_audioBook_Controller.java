package ma.enset.demo.controllers;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import ma.enset.demo.model.SavingAudio;

public class Text_to_audioBook_Controller {

    @FXML
    private Button StartButton;

    @FXML
    private Button audioBookImage;

    @FXML
    private TextField audioBookName;

    @FXML
    private BorderPane principal;

    @FXML
    private TextArea text;

    private String imagePath;

    @FXML
    void openFileChooser(ActionEvent event) {
        imagePath=openFileChooser();

    }

    @FXML
    void startConverting(ActionEvent event) {
        String currentDirectory = System.getProperty("user.dir");
        String path= currentDirectory + File.separator + "audio";
        SavingAudio audio= new SavingAudio(path);

        MediaPlayerController.selectedFile =  audio.saveAudio(text.getText());
        MediaPlayerController.setMedia();
    }


    private String openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());

        }
        return selectedFile.getAbsolutePath();
    }

}
