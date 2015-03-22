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
    String[] chunks = {
      "Fawkes dived his long golden beak sank out of sight and a sudden shower of dark blood splattered the floor",
      "the snake's tail thrashed narrowly missing harry and before harry could shut his eyes it turned harry looked straight into",
      "his face and saw that its eyes both its great bulbous yellow eyes had been punctured by the pheonix blood",
      "was streaming to the floor and the snake was spitting in agony no harry heard riddle screaming leave the bird",
      "leave the bird the boy is behind you you can still smell him kill him the blinded serpent swayed confused",
      "still deadly fawkes was circling its head piping his eerie song jabbing here and there at the basilisks scaly nose",
      "as the blood poured from its ruined eyes help me help me harry muttered wildly someone anyone"
    };

    try{
      System.out.println("RUNNING  MOCKS DA DSASD ASD ASD IN 5 SECONDS");
      for(int i=0; i<chunks.length; i++) {
        System.out.println(chunks[i]);
        process(chunks[i].split(" "));
        System.out.println("Next chunk in 6s");
        Thread.sleep(6000);
      }
      System.out.println("Done!");
    } catch (Exception e ){

    }
    //process("All the words are mocked".split(" "));
    //process("There are even more mocked inputs in fact this was not processed by speech".split(" "));
  }
}
