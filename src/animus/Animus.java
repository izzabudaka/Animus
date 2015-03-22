package animus;

import animus.StreamServer;
import animus.SpeechRecognizer;
import animus.AudioManager;
import animus.ClassifierWrapper;
import animus.Determiner;

public class Animus
{
  StreamServer _streamServer;
  AudioManager _audioManager;
  MagicClassifier _magicClassifier;
  SpeechRecognizer _speechRecognizer;
  ClassifierWrapper _wrapper;
  Determiner _determiner;


	public Animus(){
		super();

    _streamServer = null;
    _audioManager = null;
    _magicClassifier = null;
    _speechRecognizer = null;
    _wrapper = null;
    _determiner = null;
	}

  public void tick(double res[]) {
    System.out.println("tick");

    double[] newVals = _determiner.computeNewRatios(res);
    for(int i=0; i<newVals.length; i++) {
      System.out.print(newVals[i]);
    }
    System.out.println("\n");

    double[] gains = _determiner.deriveAudioGainValues(0.5);
    for(int i=0; i<gains.length; i++) {
      System.out.print(gains[i]);
    }
    System.out.println("\n");
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
    //_speechRecognizer.init();
    _streamServer = new StreamServer(_speechRecognizer);
    _determiner = new Determiner(3);
  }

  public void testAudio() {
    _audioManager.playSound(0, (float)1);
    _audioManager.playSound(1, (float)-20);
    while(_audioManager.isPlaying()){}
  }

  public void testDet() {
    System.out.println("////// DET TEST ////////");
    _wrapper.sendValues();
    _wrapper.sendValues();
  }

  public void run() {
    _speechRecognizer.recognizeFromFile("HQ-speech44100-mono.wav");
  }

  public static void main(String[] args) {
    Animus a = new Animus();

    a.init();
    //a.run();
    //a.testAudio();
    a.testDet();
  }

}

