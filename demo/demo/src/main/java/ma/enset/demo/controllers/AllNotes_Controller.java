package ma.enset.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ma.enset.demo.model.Note;

public class AllNotes_Controller implements Initializable {
    List<Note> allnotes;
    @FXML
    private HBox notes;

    @FXML
    private Label bookName;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        allnotes=new ArrayList<>(fillNote());
        try {
            for( Note n : allnotes) {
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ma/enset/demo/note.fxml"));

                AnchorPane vBox = fxmlLoader.load();
                ((Note_Controller) fxmlLoader.getController()).setData(n);
                notes.getChildren().add(vBox);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
    private List<Note> fillNote() {
        List<Note> ls= new ArrayList<>();
        String str="Syntaxe: Auxiliaire BE (conjugué au présent) + Verbe en −ing\r\n"
                + "Exemple:\r\n"
                + "I am playing football.\r\n"
                + "You are playing football.\r\n"
                + "He/She/It is playing football.\r\n"
                + "We are playing football.\r\n"
                + "You are playing football.\r\n"
                + "They are playing football.\r\n"
                + "Négation: I am not playing football, you are not playing football...\r\n"
                + "Question: Am I playing football? Are you playing football? Is he playing football? ...\r\n"
                + "Réponses:\r\n"
                + "Are they playing football?\r\n";
        for (int i=0;i<10;i++) {
            ls.add(new Note("book", "chap"+i, str, i));
            System.out.println(ls.get(i));
        }
        return ls;
    }
}
