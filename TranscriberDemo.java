package model;

import edu.cmu.sphinx.frontend.util.StreamDataSource;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;


/** A simple example that shows how to transcribe a continuous audio file that has multiple utterances in it. */
public class TranscriberDemo {

    /** Main method for running the HelloDigits demo. */
    public static void main(String[] args) {
        try {
            URL audioURL;
            if (args.length > 0) {
                audioURL = new File(args[0]).toURI().toURL();
            } else {
                audioURL =
                        TranscriberDemo.class.getResource("10001-90210-01803.wav");
            }

            URL configURL = TranscriberDemo.class.getResource("config.xml");

            ConfigurationManager cm = new ConfigurationManager(configURL);
            Recognizer recognizer = (Recognizer) cm.lookup("recognizer");

            /* allocate the resource necessary for the recognizer */
            recognizer.allocate();

            AudioInputStream ais = AudioSystem.getAudioInputStream(audioURL);
            StreamDataSource reader =
                    (StreamDataSource) cm.lookup("streamDataSource");
            reader.setInputStream(ais);

            boolean done = false;
            while (!done) {
                /*
             * This while loop will terminate after the last utterance
             * in the audio file has been decoded, in which case the
             * recognizer will return null.
             */
                Result result = recognizer.recognize();
                if (result != null) {
                    String resultText = result.getBestResultNoFiller();
                    System.out.println(resultText);
                } else {
                    done = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Problem when loading Transcriber: " + e);
            e.printStackTrace();
        } catch (PropertyException e) {
            System.err.println("Problem configuring Transcriber: " + e);
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Audio file format not supported.");
            e.printStackTrace();
        }
    }
}

    /** Converts this demo into a unit-test. */
   
 /*   public void testLatticeDemo() {
        try {
            main(new String[]{});
        } catch (Throwable t) {
            Assert.fail();
        }
    }
}*/