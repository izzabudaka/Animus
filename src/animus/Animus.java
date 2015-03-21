package animus;

import animus.StreamServer;
import animus.SpeechRecognizer;

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
    _streamServer.run();
    //SpeechRecognizer r = new SpeechRecognizer();
    //r.recognizeFromFile("asdf.wav");
  }

  public static void main(String[] args) {
    Animus a = new Animus();
    
    a.init();
    a.run();
  }

}

