package ma.enset.demo.model;
import javax.sound.sampled.AudioFileFormat;
import com.sun.speech.freetts.*;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

import java.io.File;

public class SavingAudio {

    private String audioSavePath;

    public SavingAudio(String audioSavePath) {
        this.audioSavePath = audioSavePath;
    }

    public File saveAudio(String text) {
        try {
            // Initialize FreeTTS components
            Voice voice;
            VoiceManager vm = VoiceManager.getInstance();
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            voice = vm.getVoice("kevin16");

            // Set up the audio player
            SingleFileAudioPlayer audioPlayer = new SingleFileAudioPlayer(audioSavePath, AudioFileFormat.Type.WAVE);
            voice.setAudioPlayer(audioPlayer);

            // Synthesize and save the audio
            voice.allocate();
            voice.speak(text);
            voice.deallocate();

            // Close the audio player to save the file
            audioPlayer.close();

            // Return the File object representing the saved audio file
            return new File(audioSavePath+".wav");

        } catch (Exception e) {
            // Handle exceptions, e.g., logging or throwing a custom exception
            e.printStackTrace();
            return null;  // Indicate that an error occurred
        }
    }
}
