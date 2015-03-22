package animus;

import animus.StreamServer;
import animus.SpeechRecognizer;
import animus.AudioManager;

public class Animus
{

  StreamServer _streamServer;
  AudioManager _audioManager;

	public Animus(){
		super();

    _streamServer = null;
    _audioManager = null;
	}

  public void init() {
    //_streamServer = new StreamServer();


    String[] filenames = {
      "s1.wav"
    };
    _audioManager = new AudioManager(filenames);
  }

  public void run() {
    //_streamServer.run();
    //SpeechRecognizer r = new SpeechRecognizer();
    //r.recognizeFromFile("asdf.wav");
    _audioManager.playSound(0, 1);

  }

  public static void main(String[] args) {
    Animus a = new Animus();

    a.init();
    a.run();
  }

}

