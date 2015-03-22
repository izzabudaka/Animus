package animus;

import java.net.*;
import java.io.*;
import javax.sound.sampled.*;

import animus.SpeechRecognizer;

public class StreamServer
{
  private ServerSocket serverSocket;
  private SpeechRecognizer r;

  private Socket acceptConnection() {
    Socket socket = null;

    try {
      socket = serverSocket.accept();
    } catch (IOException e) {
      System.out.println("can't accept client conn " + e);
    }

    return socket;
  }

  private void acceptData(Socket socket) {
    InputStream inputStream = null;
    int bufferSize = 0;

    //r = new SpeechRecognizer();

    try {
      inputStream = socket.getInputStream();
    //a = new SpeechRecognizer();

      //bufferSize = socket.getReceiveBufferSize();
      //System.out.println("buff size: " + bufferSize);

    } catch (IOException e) {
      System.out.println(e);
    }

    int rate = 8000;
    int depth = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigend = false;
    AudioFormat format = new AudioFormat(rate, depth, channels, signed, bigend);

    byte[] buffer = new byte[bufferSize];
    try{
        
      InputStream bufferedIn = new BufferedInputStream(inputStream);
      AudioInputStream ais = new AudioInputStream(bufferedIn, format, 200000);


      AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("asdf.wav"));

      r.recognizeFromFile("asdf.wav");
    } catch (Exception e) {
      System.out.println("io exc " + e);
    }
  }

  //---------------------------------------------------------------------------
	public StreamServer(SpeechRecognizer rec){
		super();
    r = rec;
    ServerSocket serverSocket = null;
	}

  public void run() {
    try {
      System.out.println("Opening socket 666.");
      serverSocket = new ServerSocket(666);
    } catch (IOException e ){
      System.out.println("Can't setup serv on 666 " + e); 
    }

    Socket socket = acceptConnection();
    acceptData(socket);
  }




}

