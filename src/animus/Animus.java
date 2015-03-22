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
  SpeechProcessor _speechRecognizer;
  ClassifierWrapper _wrapper;
  Determiner _determiner;


	public Animus(){
		super();

    _streamServer = null;
    _audioManager = null;
    _speechRecognizer = null;
    _wrapper = null;
    _determiner = null;
	}

  public void tick(double res[]) {
    //System.out.println("tick");

    double[] newVals = _determiner.computeNewRatios(res);
    //for(int i=0; i<newVals.length; i++) {
    //  System.out.print(newVals[i] + " ");
    //}
    //System.out.println("\n");

    double[] gains = _determiner.deriveAudioGainValues(0.55);
    System.out.println("gains: ");
    for(int i=0; i<gains.length; i++) {
      System.out.print(gains[i] + " ");
    }
    System.out.println("\n");

    for(int i=0; i<5; i++) {
      _audioManager.playSound(i, (float)gains[i]);
    }
  }

  public void init() {
  
    String[] filenames = {
      "music/Suspense.wav",
      "music/Action.wav",
      "music/Dramatic.wav",
      "music/Happy.wav",
      "music/Sad.wav"
    };

    Logistic.train1();

    _audioManager = new AudioManager(filenames);
    int bufferSize = 5;
    _wrapper = new ClassifierWrapper(bufferSize, this);
    //_speechRecognizer = new SpeechRecognizer(_wrapper);
    _speechRecognizer = new MockSpeechProcessor(_wrapper);
    //_speechRecognizer.init();
    _streamServer = new StreamServer(_speechRecognizer);
    _determiner = new Determiner(6);
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
    _streamServer.run();
    _speechRecognizer.recognizeFromFile("HQ-speech44100-mono.wav");

    while(_audioManager.isPlaying()){}
  }

  public static void main(String[] args) {
    Animus a = new Animus();

    a.init();
    a.run();
    //a.testAudio();
    //a.testDet();
  }

}

