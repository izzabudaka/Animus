package animus;

import java.io.*;
import edu.cmu.sphinx.api.*;

public class SpeechRecognizer
{

	public SpeechRecognizer(){
		super();

    Configuration config = new Configuration();
    config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");

    try {
      LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(config);
      recognizer.startRecognition(true);
      SpeechResult result = recognizer.getResult();
      recognizer.stopRecognition();
    } catch (IOException e) {
      System.err.println(e);
    }
	}

}

