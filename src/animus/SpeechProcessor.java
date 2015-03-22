package animus;

import java.io.*;

public interface SpeechProcessor {
  public void recognize(InputStream stream);
  public void recognizeFromFile(String filename);
  public void init();
}
