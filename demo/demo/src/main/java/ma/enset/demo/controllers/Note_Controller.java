package ma.enset.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ma.enset.demo.model.Note;

public class Note_Controller {

    @FXML
    private Label chapName;

    @FXML
    private TextArea noteText;

    @FXML
    private Label pageNumber;
    public void setData(Note note) {
        chapName.setText(note.getChap());
        noteText.setText(note.getText());
        pageNumber.setText(Integer.toString(note.getPagenumber()) );

    }

}
