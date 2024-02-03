package ma.enset.demo.model;

import javafx.beans.value.ChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.util.function.Consumer;


import java.io.File;

public class MediaLogic {

    private Media media;
    private MediaPlayer mediaPlayer;

    public void play() {
        if (mediaPlayer != null && !mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.play();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void selectMedia(File selectedFile) {
        // Vérifier si mediaPlayer est déjà initialisé
        if (mediaPlayer != null) {
            // Arrêter la lecture en cours et libérer les ressources
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        if (selectedFile != null) {
            String url = selectedFile.toURI().toString();
            media = new Media(url);
            mediaPlayer = new MediaPlayer(media);
        }
    }

    public void setOnReadyListener(Runnable onReady) {
        mediaPlayer.setOnReady(onReady);
    }

    public void setCurrentTimeListener(ChangeListener<Duration> listener) {
        mediaPlayer.currentTimeProperty().addListener(listener);
    }


    public void seek(Duration duration) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(duration);
        }
    }

    public Duration getTotalDuration() {
        return media != null ? media.getDuration() : Duration.ZERO;
    }

    public double getVolume() {
        return mediaPlayer != null ? mediaPlayer.getVolume() : 0.0;
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public void skipForward() {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            Duration skipTime = currentTime.add(Duration.seconds(10.0));
            mediaPlayer.seek(skipTime);
        }
    }

    public void skipBackward() {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            Duration skipTime = currentTime.subtract(Duration.seconds(10.0));
            mediaPlayer.seek(skipTime);
        }
    }

    // Autres méthodes métier

}
