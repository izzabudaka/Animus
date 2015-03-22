package animus;

import animus.MagicClassifier;

public class ClassifierWrapper{
  public String[] buffer;
  MagicClassifier classifier;
  Animus _callback;
  public ClassifierWrapper(int bufferSize, MagicClassifier classifier, Animus callback){
    buffer = new String[bufferSize];
    this.classifier = classifier;
    _callback = callback;
  }
  public void sendValues(){
    System.out.println("CALLING CLASSIFIER");
    double[] res = classifier.classify(buffer);
    _callback.tick(res);
  }
}
