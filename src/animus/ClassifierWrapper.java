package animus;

import animus.MagicClassifier;

public class ClassifierWrapper{
  public String[] buffer;
  Animus _callback;
  public ClassifierWrapper(int bufferSize, Animus callback){
    buffer = new String[bufferSize];
    _callback = callback;
  }
  public void sendValues(){
    String str = "";
    for(int i=0; i<buffer.length; i++) str += buffer[i] + " ";
    double[] res = Logistic.classify(str);
    //System.out.print("asdf: ");
    //for(int i=0; i<res.length; i++) {
    //  System.out.print(res[i] + " ");
    //}
    System.out.print("\n");
    _callback.tick(res);
  }
}
