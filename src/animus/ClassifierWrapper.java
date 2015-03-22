package animus;

public class ClassifierWrapper{
  String[] buffer;
  MagicClassifier classifier;
  public ClassifierWrapper(int bufferSize, MagicClassifier classifier){
    buffer = new String[bufferSize];
    this.classifier = classifier;
  }
  public void sendValues(String[] words){
    for(int i=0; i < words.length; i++){
      buffer[i] = words[i];
      System.out.println(buffer[i]);
    }
    classifier.classify(words);
  }
}