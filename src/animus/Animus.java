package animus;

import animus.StreamServer;
import animus.SpeechRecognizer;
import animus.AudioManager;
import animus.ClassifierWrapper;

public class Animus
{
  StreamServer _streamServer;
  AudioManager _audioManager;
  MagicClassifier _magicClassifier;
  SpeechRecognizer _speechRecognizer;
  ClassifierWrapper _wrapper;

	public Animus(){
		super();

    _streamServer = null;
    _audioManager = null;
    _magicClassifier = null;
    _speechRecognizer = null;
    _wrapper = null;
	}

  public void tick() {
    System.out.println("tick");
  }

  public void init() {
  
    _magicClassifier = new MagicClassifier();
    String[] filenames = {
      "s1.wav",
      "s2.wav"
    };
    _audioManager = new AudioManager(filenames);
    int bufferSize = 5;
    _wrapper = new ClassifierWrapper(bufferSize, _magicClassifier, this);
    _speechRecognizer = new SpeechRecognizer(_wrapper);
    _streamServer = new StreamServer(_speechRecognizer);
  }

  public void testAudio() {
    _audioManager.playSound(0, (float)1);
    _audioManager.playSound(1, (float)-20);
    while(_audioManager.isPlaying()){}
  }

  public void run() {
    _speechRecognizer.recognizeFromFile("HQ-speech44100-mono.wav");
  }

  public static void main(String[] args) {
    Animus a = new Animus();

    a.init();
    a.run();
    //a.testAudio();
  }

}

