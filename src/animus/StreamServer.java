package animus;

import java.net.*;
import java.io.*;
import javax.sound.sampled.*;

public class StreamServer
{
  public static void main(String[] args) {
    StreamServer s = new StreamServer();

    s.run();
  } 

  private ServerSocket serverSocket;

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

    try {
      inputStream = socket.getInputStream();

      bufferSize = socket.getReceiveBufferSize();
      System.out.println("buff size: " + bufferSize);

    } catch (IOException e) {
      System.out.println(e);
    }

    byte[] buffer = new byte[bufferSize];
    try{
      while(inputStream != null) {
        int size = inputStream.read(buffer, 0, buffer.length);
        if(size < 0) break;

        //we have buffer now?
        
        //InputStream bufferedIn = new BufferedInputStream(inputStream);
        //AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
      }
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
    } catch (UnsupportedEncodingException e) {
      System.out.println(e);
    //} catch (UnsupportedAudioFileException e) {
    //  System.out.println(e);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  //---------------------------------------------------------------------------
	public StreamServer(){
		super();

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

