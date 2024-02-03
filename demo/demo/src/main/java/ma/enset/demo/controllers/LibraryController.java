package ma.enset.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ma.enset.demo.model.LibraryModel;

import java.util.List;

public class LibraryController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollPane1;
    @FXML
    private VBox pdfButtonsContainer;
    @FXML
    private VBox pdfButtonsContainer1; // Conteneur pour les favoris


    public void initialize() {
        pdfButtonsContainer = new VBox();
        pdfButtonsContainer1 = new VBox(); // Initialisation du conteneur pour les favoris

        scrollPane.setContent(pdfButtonsContainer);
        scrollPane1.setContent(pdfButtonsContainer1);

        updateLibrary();
        updateFavoriteList();
    }

    public void addToLibrary(String pdfName, String duration) {
        if (!LibraryModel.getPdfNames().contains(pdfName)) {
            LibraryModel.addPdf(pdfName);
            updateLibrary();
        }
    }
    public void addToFavorite(String pdfName, String duration) {
        if (!LibraryModel.getFavoritePdfNames().contains(pdfName)) {
            LibraryModel.addFavoritePdf(pdfName);
            updateFavoriteList();
        }
    }

    private void updateLibrary() {
        pdfButtonsContainer.getChildren().clear();
        List<String> pdfNames = LibraryModel.getPdfNames();
        for (String name : pdfNames) {
            Button pdfButton = new Button(name);
            pdfButtonsContainer.getChildren().add(pdfButton);
            pdfButton.getStyleClass().add("page-button1");

        }
    }
    private void updateFavoriteList() {
        pdfButtonsContainer1.getChildren().clear();
        List<String> favoritePdfNames = LibraryModel.getFavoritePdfNames();
        for (String name : favoritePdfNames) {
            Button pdfButton = new Button(name);
            pdfButtonsContainer1.getChildren().add(pdfButton); // Utilisez le conteneur des favoris
            pdfButton.getStyleClass().add("page-button1");
        }
    }

    }
