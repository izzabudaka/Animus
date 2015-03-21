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

    r = new SpeechRecognizer();

    try {
      inputStream = socket.getInputStream();

      //bufferSize = socket.getReceiveBufferSize();
      //System.out.println("buff size: " + bufferSize);

    } catch (IOException e) {
      System.out.println(e);
    }

    int rate = 8000;
    int depth = 16;
    int channels = 1;
    boolean signed = false;
    boolean bigend = false;
    AudioFormat format = new AudioFormat(rate, depth, channels, signed, bigend);

    byte[] buffer = new byte[bufferSize];
    try{
      //while(inputStream != null) {
        //int size = inputStream.read(buffer, 0, buffer.length);
        //if(size < 0) break;

        //System.out.println("received: " + size);
        //we have buffer now?
        //
        
        InputStream bufferedIn = new BufferedInputStream(inputStream);
        AudioInputStream ais = new AudioInputStream(bufferedIn, format, 200000);
        //System.out.println("IS it blocking ??");


        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("asdf.wav"));

        //r.recognize(bufferedIn);
        r.recognizeFromFile("asdf.wav");

      //}
    //StringBuilder out = new StringBuilder();
    //try(Reader input = new InputStreamReader(inputStream, "UTF-8")) {
    //  System.out.println("Listening..");
    //  for(;;) {
    //    int size = input.read(buffer, 0, buffer.length);
    //    if(size < 0) break;

    //    InputStream bufferedIn = new BufferedInputStream(inputStream);
    //    AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);

    //    out.append(buffer, 0, size);
    //    System.out.println(out);
    //  }
    } catch (Exception e) {
      System.out.println("io exc " + e);
    }
  }

  //---------------------------------------------------------------------------
	public StreamServer(){
		super();

    ServerSocket serverSocket = null;
    r = new SpeechRecognizer();
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

