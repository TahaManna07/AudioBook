package ma.enset.demo.model;

import javafx.concurrent.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import ma.enset.demo.controllers.MediaPlayerController;

public class LecteurPDF {

    public void speak(String txt) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        try {
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc());
            synthesizer.allocate();
            synthesizer.resume();

            synthesizer.speakPlainText(txt, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synthesizer.deallocate();

        } catch (EngineException | AudioException | EngineStateError | IllegalArgumentException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String extractText(String pdfFilePath) {
        StringBuilder text = new StringBuilder();
        try {
            // Extraction du texte du PDF
            PdfReader pdfReader = new PdfReader(pdfFilePath);
            for (int page = 1; page <= pdfReader.getNumberOfPages(); page++) {
                text.append(PdfTextExtractor.getTextFromPage(pdfReader, page));
            }
            pdfReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public void lecteur(String pdfFilePath) {
        lecteur(pdfFilePath, 1); // Commence à partir de la première page par défaut
    }

    public void lecteur(String pdfFilePath, int startPageNumber) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Extraction du texte du PDF à partir de la page spécifiée
                    String text = extractTextFromPage(pdfFilePath, startPageNumber);

                    String currentDirectory = System.getProperty("user.dir");
                    String path= currentDirectory + File.separator + "audio";
                    SavingAudio audio= new SavingAudio(path);

                    MediaPlayerController.selectedFile =  audio.saveAudio(text);
                    MediaPlayerController.setMedia();
//
//
//                    // Configuration de FreeTTS
//                    System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
//                    com.sun.speech.freetts.Voice voice = VoiceManager.getInstance().getVoice("kevin16");
//                    voice.allocate();
//                    voice.speak(text);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        // Lancez la tâche dans un nouveau thread
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    public String getDurationForPage(String pdfFilePath, int pageNumber) {
        // Extract text from the specific page
        String text = extractTextFromPage(pdfFilePath, pageNumber);

        // Calculate duration based on the text content or any other logic
        double durationInSeconds = calculateDurationFromText(text);

        // Format duration as HH:mm:ss (replace with actual formatting logic)
        long seconds = (long) durationInSeconds;
        long minutes = (seconds / 60) % 60;
        long hours = seconds / 3600;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds % 60);
    }
    public double getAudioDuration(String audioFilePath) {
        try {
            File file = new File(audioFilePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            Bitstream bitstream = new Bitstream(fileInputStream);

            int audioFrames = 0;
            double totalAudioDuration = 0.0;

            while (true) {
                try {
                    javazoom.jl.decoder.Header header = bitstream.readFrame();
                    if (header == null) {
                        break;  // Fin du fichier
                    }
                    totalAudioDuration += header.max_number_of_frames(1);
                    audioFrames++;
                } catch (JavaLayerException e) {
                    break;  // Fin du fichier ou erreur
                }
            }

            // Obtenez la fréquence audio à partir du premier cadre
            int audioFrequency = bitstream.readFrame().frequency();

            double audioDurationInSeconds = totalAudioDuration / audioFrequency;

            fileInputStream.close();
            return audioDurationInSeconds;
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    public double calculateDurationForPage(int numberOfPages, double totalAudioDuration, int pageNumber) {
        // Calculer la durée estimée pour chaque page en distribuant également la durée totale entre les pages
        return (totalAudioDuration / numberOfPages) * pageNumber;
    }



    private double calculateDurationFromText(String text) {
        // Replace this with your actual logic to calculate duration from text content
        // For example, you might analyze the text and estimate the duration
        return text.length() * 0.1; // Just a placeholder, replace with your logic
    }
    private String extractTextFromPage(String pdfFilePath, int pageNumber) {
        StringBuilder text = new StringBuilder();
        try {
            // Extraction du texte du PDF à partir de la page spécifiée
            PdfReader pdfReader = new PdfReader(pdfFilePath);
            text.append(PdfTextExtractor.getTextFromPage(pdfReader, pageNumber));
            pdfReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public int getNumberOfPages(String pdfFilePath) throws IOException {
        PdfReader pdfReader = new PdfReader(pdfFilePath);
        int numberOfPages = pdfReader.getNumberOfPages();
        pdfReader.close();
        return numberOfPages;
    }
    public void lecteur(String pdfFilePath, int startPage, int endPage) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Extraction du texte du PDF à partir de la page spécifique
                    String text = extractText(pdfFilePath, startPage, endPage);
                    String currentDirectory = System.getProperty("user.dir");
                    String path= currentDirectory + File.separator + "audio";
                    SavingAudio audio= new SavingAudio(path);

                    MediaPlayerController.selectedFile =  audio.saveAudio(text);
                    MediaPlayerController.setMedia();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        // Lancez la tâche dans un nouveau thread
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private String extractText(String pdfFilePath, int startPage, int endPage) {
        StringBuilder text = new StringBuilder();
        try {
            // Extraction du texte du PDF à partir de la page spécifique
            PdfReader pdfReader = new PdfReader(pdfFilePath);
            for (int page = startPage; page <= endPage; page++) {
                text.append(PdfTextExtractor.getTextFromPage(pdfReader, page));
            }
            pdfReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
