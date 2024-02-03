package ma.enset.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import ma.enset.demo.model.LibraryModel;
import ma.enset.demo.model.LecteurPDF;
import ma.enset.demo.model.SavingAudio;

import java.io.File;
import java.io.IOException;


public class Scene2Controller {

    @FXML
    private ListView<String> chapterListView;
    @FXML
    private MenuButton chapterMenuButton;
    @FXML
    private VBox chapterVBox;
    @FXML
    private BorderPane principal;
    @FXML
    private Button downloadButton;
    @FXML

    private Button addToLibrary;
    @FXML
    private Button startButton;
    @FXML
    private VBox pageButtonsVBox;

    @FXML
    private Button addToFavorite;
    private String pdfFilePath; // variable to store the PDF file path
//un seul lecteur
    LecteurPDF lecteurPDF = new LecteurPDF();

    @FXML
    private Text pdfNameLabel;

    // Setter for the PDF file path
    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
        updateChapterList();

        // Mise à jour du label avec le nom du fichier PDF sans l'extension
        File pdfFile = new File(pdfFilePath);
        String pdfFileName = pdfFile.getName();
        int lastDotIndex = pdfFileName.lastIndexOf('.');
        String pdfFileNameWithoutExtension = lastDotIndex > 0 ? pdfFileName.substring(0, lastDotIndex) : pdfFileName;

        pdfNameLabel.setText("AUDIOBOOK NAME: " + pdfFileNameWithoutExtension);

        // Ajout du PDF à la bibliothèque
      //  LibraryModel.addPdf(pdfFileNameWithoutExtension);

    }


    @FXML
    void startConverting(ActionEvent event) {
        if (pdfFilePath != null && !pdfFilePath.isEmpty()) {

            lecteurPDF.lecteur(pdfFilePath);
            // Ajoutez ici tout autre traitement associé au bouton "Start" dans Scene2
        }
    }

    private void updateChapterList() {
        // Supprimez les anciens éléments du MenuButton
        chapterMenuButton.getItems().clear();

        // Supprimez les anciens boutons de la VBox
        pageButtonsVBox.getChildren().clear();

        if (pdfFilePath != null && !pdfFilePath.isEmpty()) {
            // Obtenez le nombre de pages du PDF
            try {
                int numberOfPages = getNumberOfPagesFromPDF(pdfFilePath);

                // Ajoutez les nouveaux boutons au MenuButton et à la VBox
                for (int i = 1; i <= numberOfPages; i++) {
                    final int pageNumber = i; // Créez une variable finale pour capturer la valeur de i

                    // Ajoutez au MenuButton
                    MenuItem pageMenuItem = new MenuItem("Page " + pageNumber);
                    pageMenuItem.setOnAction(event -> {
                        try {
                            goToPage(pageNumber);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    chapterMenuButton.getItems().add(pageMenuItem);

                    // Obtenez la durée estimée pour la page spécifique
                    double durationForPage = 0;
                    try {
                        // Replace this with the actual method call to get the duration string
                        String durationString = lecteurPDF.getDurationForPage(pdfFilePath, pageNumber);

                        // Split the duration into hours, minutes, and seconds
                        String[] parts = durationString.split(":");
                        int hours = Integer.parseInt(parts[0]);
                        int minutes = Integer.parseInt(parts[1]);
                        int seconds = Integer.parseInt(parts[2]);

                        // Calculate the total duration in seconds
                        durationForPage = hours * 3600 + minutes * 60 + seconds;

                        // Now you can use the durationForPage variable as a double
                        // For example, print it to verify
                        System.out.println("Duration for page: " + durationForPage + " seconds");

                        // Continue with your code using the durationForPage variable

                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        // Handle exceptions appropriately
                        e.printStackTrace(); // Print the exception stack trace for debugging
                        // Add more specific exception handling or logging as needed
                    }

                    // Ajoutez une étiquette pour afficher la durée
                    Label durationLabel = new Label("Durée : " + formatDuration(durationForPage));
                    durationLabel.setStyle("-fx-text-fill: WHITE;");

                    // Ajoutez au bouton
                    Button pageButton = new Button("Page " + pageNumber);
                    pageButton.getStyleClass().add("page-button");
                    pageButton.setOnAction(event -> {
                        try {
                            goToPage(pageNumber);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    // Ajoutez la durée à la description du bouton
                    pageButton.setText("  Page " + pageNumber + "                                  "+ formatDuration(durationForPage));

                    // Ajoutez à la VBox
                    HBox pageBox = new HBox(pageButton);
                    //pageBox.setAlignment(Pos.CENTER);
                    //pageBox.setSpacing(5.0);
                    pageButtonsVBox.getChildren().add(pageBox);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String formatDuration(double durationInSeconds) {
        // Formatte la durée en HH:mm:ss (à ajuster si nécessaire)
        long seconds = (long) durationInSeconds;
        long minutes = (seconds / 60) % 60;
        //long hours = seconds / 3600;
        return String.format("%02d:%02d", minutes, seconds % 60);
    }

    private int getNumberOfPagesFromPDF(String pdfFilePath) throws IOException {
        return lecteurPDF.getNumberOfPages(pdfFilePath);
    }

    private void goToPage(int pageNumber) throws IOException {
        // Ajoutez le code pour aller à la page spécifique du PDF ici
        if (pdfFilePath != null && !pdfFilePath.isEmpty()) {
            lecteurPDF.lecteur(pdfFilePath, pageNumber, getNumberOfPagesFromPDF(pdfFilePath));
        }
    }

    @FXML
    void downloadAudio(ActionEvent event) {
        if (pdfFilePath != null && !pdfFilePath.isEmpty()) {
            // Utilisez pdfFilePath pour extraire le texte du PDF
            String pdfText = lecteurPDF.extractText(pdfFilePath);

            // Utilisez un DirectoryChooser pour permettre à l'utilisateur de choisir le répertoire pour enregistrer le fichier audio
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose Directory to Save Audio File");
            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null) {
                // Le chemin complet du fichier audio, sans extension pour que la classe SavingAudio ajoute l'extension appropriée
                String audioSavePath = selectedDirectory.getAbsolutePath() + File.separator + "audioFile";
                SavingAudio savingAudio = new SavingAudio(audioSavePath);
                savingAudio.saveAudio(pdfText);
            }
        }
    }

    private LibraryController libraryController;

    // ...

    @FXML
    void addToLibrary(ActionEvent event) {
        if (pdfFilePath != null && !pdfFilePath.isEmpty()) {
            File pdfFile = new File(pdfFilePath);
            String pdfFileName = pdfFile.getName();
            int lastDotIndex = pdfFileName.lastIndexOf('.');
            String pdfFileNameWithoutExtension = lastDotIndex > 0 ? pdfFileName.substring(0, lastDotIndex) : pdfFileName;

            String duration = "00:00"; // Replace this with the actual duration

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/enset/demo/Library.fxml"));
                Parent libraryParent = loader.load();
                LibraryController libraryController = loader.getController();
                libraryController.addToLibrary(pdfFileNameWithoutExtension, duration);
                principal.setCenter(libraryParent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void addToFavorite(ActionEvent event) {
        if (pdfFilePath != null && !pdfFilePath.isEmpty()) {
            File pdfFile = new File(pdfFilePath);
            String pdfFileName = pdfFile.getName();
            int lastDotIndex = pdfFileName.lastIndexOf('.');
            String pdfFileNameWithoutExtension = lastDotIndex > 0 ? pdfFileName.substring(0, lastDotIndex) : pdfFileName;

            String duration = "00:00"; // Replace this with the actual duration

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/enset/demo/Library.fxml"));
                Parent libraryParent = loader.load();
                LibraryController libraryController = loader.getController();
                libraryController.addToFavorite(pdfFileNameWithoutExtension, duration);

                principal.setCenter(libraryParent);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }










}
