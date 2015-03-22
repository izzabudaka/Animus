package animus;

import animus.MagicClassifier;

public class ClassifierWrapper{
  public String[] buffer;
  MagicClassifier classifier;
  public ClassifierWrapper(int bufferSize, MagicClassifier classifier){
    buffer = new String[bufferSize];
    this.classifier = classifier;
  }
  public void sendValues(){
    classifier.classify(buffer);
  }
}
