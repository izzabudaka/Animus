package animus;

import animus.StreamServer;
import animus.SpeechRecognizer;
import animus.ClassifierWrapper;

public class Animus
{
  StreamServer _streamServer;

	public Animus(){
		super();

    _streamServer = null;
	}

  public void init() {
    _streamServer = new StreamServer();
  }

  public void run() {
    //_streamServer.run();
    MagicClassifier classifier = new MagicClassifier();
    bufferSize = 5; // in number of words
    ClassifierWrapper wrapper = new ClassifierWrapper(bufferSize, classifier);
    SpeechRecognizer r = new SpeechRecognizer(wrapper);
    r.recognizeFromFile("HQ-speech44100-mono.wav");
  }

  public static void main(String[] args) {
    Animus a = new Animus();
    
    a.init();
    a.run();
  }

}

