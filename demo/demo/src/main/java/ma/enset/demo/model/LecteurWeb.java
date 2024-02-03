package ma.enset.demo.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Scanner;


import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.sun.speech.freetts.VoiceManager;


public class LecteurWeb {
    public void speak(String txt) {

        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        try {
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();


            synthesizer.speakPlainText(txt, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synthesizer.deallocate();


        } catch (EngineException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AudioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (EngineStateError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public StringBuilder lecteur(String path) {
        StringBuilder text = new StringBuilder();
        try {
            // Download PDF from the web
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();


            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            // Use the BufferedInputStream to create the PdfReader
            PdfReader pdfReader = new PdfReader(in);


            for (int page = 1; page <= pdfReader.getNumberOfPages(); page++) {
                text.append(PdfTextExtractor.getTextFromPage(pdfReader, page));
            }
            // Close the PDF document
            pdfReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}