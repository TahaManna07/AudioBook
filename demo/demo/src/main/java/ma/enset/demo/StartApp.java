package ma.enset.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class StartApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML de la scène d'accueil
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ma/enset/demo/acceuil.fxml")));
            Scene scene = new Scene(root);
            Image image = new Image("assets/icon.png");

            primaryStage.setScene(scene);
            primaryStage.getIcons().add(image);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void loadScene(ActionEvent event, String sceneName) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName + ".fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Appliquer la nouvelle scène au stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs lors du chargement du fichier FXML
        }
    }


    @FXML
    void startApp(ActionEvent event) {
        loadScene(event, "Sample");
    }

}
