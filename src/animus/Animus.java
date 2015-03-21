package animus;

import animus.StreamServer;

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
  }

  public static void main(String[] args) {
    Animus a = new Animus();
    
    a.init();
    a.run();
  }

}

