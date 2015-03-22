package animus;

import java.io.*;
import animus.ClassifierWrapper;

public class MockSpeechProcessor implements SpeechProcessor{
  private ClassifierWrapper _wrapper;

  public void init() {

  }

  public MockSpeechProcessor(ClassifierWrapper wrapper) {
    _wrapper = wrapper;
  }

  public void recognize(InputStream stream) {
    
  }

  private void process(String[] strings) {
    int i=0;
    for(int j=0; j<strings.length; j++) {
      _wrapper.buffer[i] = strings[j];
      i++;
      if(i >= _wrapper.buffer.length) {
        _wrapper.sendValues();
        i=0;
      }
    }
    if(i != 0) _wrapper.sendValues();
  }

  public void recognizeFromFile(String filename) {
    process("All the words are mocked".split(" "));
    process("There are even more mocked inputs in fact this was not processed by speech".split(" "));
  }
}
