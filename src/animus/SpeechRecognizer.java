package animus;

import java.io.*;
import edu.cmu.sphinx.api.*;
import edu.cmu.sphinx.result.*;

public class SpeechRecognizer
{

  private Configuration config;
  private StreamSpeechRecognizer recognizer;

	public SpeechRecognizer(){
		super();

    config = new Configuration();
    config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");


    try {
      recognizer = new StreamSpeechRecognizer(config);
    } catch (Exception e) {
      System.out.println(e);
    }

    //try {
    //  LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(config);
    //  recognizer.startRecognition(true);
    //  SpeechResult result = recognizer.getResult();
    //  recognizer.stopRecognition();
    //} catch (IOException e) {
    //  System.err.println(e);
    //}
	}

  public void recognize(InputStream stream) {
    recognizer.startRecognition(stream);

    SpeechResult result = null;
    while ((result = recognizer.getResult()) != null) {
      System.out.println(result.getHypothesis());
    }

    recognizer.stopRecognition();

    for (WordResult r : result.getWords()) {
        System.out.println(r);
    }
  }

  public void recognizeFromFile(String filename) {
    try {
      StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
      System.out.println("INITIALIZED INITIALIZED");
      recognizer.startRecognition(new FileInputStream(filename));
      SpeechResult result = recognizer.getResult();
      recognizer.stopRecognition();
      for (WordResult r : result.getWords()) {
          System.out.println(r);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}

