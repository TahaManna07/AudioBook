package ma.enset.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import ma.enset.demo.model.MediaLogic;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MediaPlayerController  {

    @FXML
    private Button btnPlay;

    @FXML
    private static Label lblDuration=new Label();

    @FXML
    private MediaView mediaView;

    @FXML
    private static Slider slider=new Slider();

    @FXML
    static Slider volumeSlider=new Slider();

    private static MediaLogic mediaPlayerBusiness = new MediaLogic();
    public static  File selectedFile;
    boolean play=false;

    @FXML
    private ImageView playPauseImage;

    @FXML
    void btnPlay(MouseEvent event) {
        if (!play) {
            mediaPlayerBusiness.play();
            play = true;
            playPauseImage.setImage(new Image("assets/pauseP.png"));
        } else {
            mediaPlayerBusiness.pause();
            play = false;
            playPauseImage.setImage(new Image("assets/play.png"));
        }
    }


    @FXML
    void btnStop(MouseEvent event) {
        mediaPlayerBusiness.stop();
    }

    public static void setMedia() {
       // FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Select Media");
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        //fileChooser.getExtensionFilters().add(extFilter);

     //   File selectedFile = fileChooser.showOpenDialog(null);
//impo
        if (selectedFile != null) {
            System.out.println(selectedFile);
            mediaPlayerBusiness.selectMedia(selectedFile);

            mediaPlayerBusiness.setOnReadyListener(() -> {
                Duration totalDuration = mediaPlayerBusiness.getTotalDuration();
                slider.setMax(totalDuration.toSeconds());
                lblDuration.setText("Duration: 00:00:00 / " + formatDuration(totalDuration));
            });

            mediaPlayerBusiness.setCurrentTimeListener(new javafx.beans.value.ChangeListener<Duration>() {
                @Override
                public void changed(javafx.beans.value.ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    slider.setValue(newValue.toSeconds());
                    lblDuration.setText("Duration: " + formatDuration(newValue) + " / " + formatDuration(mediaPlayerBusiness.getTotalDuration()));
                }
            });

            // Autres opérations
        }
        volumeSlider.setValue(mediaPlayerBusiness.getVolume() * 100);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayerBusiness.setVolume(newValue.doubleValue() / 100.0));
    }

    @FXML
    void skipForward(ActionEvent event) {
        mediaPlayerBusiness.skipForward();
    }

    @FXML
    void skipBackward(ActionEvent event) {
        mediaPlayerBusiness.skipBackward();
    }

    @FXML
    private void sliderPressed(MouseEvent event) {
        mediaPlayerBusiness.seek(Duration.seconds(slider.getValue()));
    }

    private static String formatDuration(Duration duration) {
        int hours = (int) duration.toHours();
        int minutes = (int) duration.toMinutes() % 60;
        int seconds = (int) duration.toSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


}
