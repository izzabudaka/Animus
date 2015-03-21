import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.ling.HasWord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class PercTester{
  Map<String, Integer> wordVector;
  Map<String, List<Double>> weightsMatrix;
  private int labelsNo = 6;
  public PercTester() {
    wordVector = new HashMap(1009);
    weightsMatrix = new HashMap(1009);
    wordVector.put("dummy", 1);
    weightsMatrix.put("dummy", Arrays.asList(1.0, 1.0, 1.0,1.0,1.0,1.0));
    wordVector = readWords(wordVector, "WordVals.txt");
    weightsMatrix = readWeights(weightsMatrix, "WordVals.txt");
  }
  public double[] testPhrase(String[] phrase) {
    double[] results = new double[labelsNo];
    for(int i = 0; i < phrase.length; i++)
      if(wordVector.containsKey(phrase[i].toLowerCase()))
        wordVector.put(phrase[i], wordVector.get(phrase[i].toLowerCase())+1);
    for(int i = 0; i < labelsNo; i++){
      double dotProduct = dotProduct(wordVector, weightsMatrix, i);
      results[i] = sigmoid(dotProduct);
    }
    return results;
  }
  public double testPhrase(String[] phrase, int label) {
    double[] results = new double[labelsNo];
    for(int i = 0; i < phrase.length; i++)
      if(wordVector.containsKey(phrase[i].toLowerCase()))
        wordVector.put(phrase[i], wordVector.get(phrase[i].toLowerCase())+1);
    for(int i = 0; i < labelsNo; i++){
      double dotProduct = dotProduct(wordVector, weightsMatrix, i);
      results[i] = sigmoid(dotProduct);
    }
    return results[label];
  }
  public double sigmoid(double dotProduct){
    return 1.0/(1+Math.pow(Math.E,-1.0*dotProduct));
  }
  public double dotProduct(Map<String, Integer> wordVector, Map<String, List<Double>> weights, int clas) {
    double dotProduct = 0.0;
    for(Map.Entry<String, Integer> entry : wordVector.entrySet())
      dotProduct += entry.getValue()*weights.get(entry.getKey()).get(clas);
    return dotProduct;
  }
  private Map<String, Integer> readWords(Map<String, Integer> map, String filename){
    try{
      Scanner sc=new Scanner(new FileReader(filename));
      while (sc.hasNextLine()) {
        String word = sc.next();
        for(int i= 0; i < 6; i++){
          sc.next();
        }
        map.put(word, 0);
      }
    }
    catch(Exception e){
      System.out.println(e);
    }
    return map;
  }
  private static Map<String, List<Double>> readWeights(Map<String, List<Double>> map, String filename){
    try{
      Scanner sc=new Scanner(new FileReader(filename));
      while (sc.hasNextLine()) {
        String word = sc.next();
        List<Double> values = new ArrayList<Double>();
        for(int i= 0; i < 6; i++){
          String csc = sc.next();
          //values.add(i,Integer.parseInt(csc));
          values.add(i, Math.random() *20.0 - 10.0);
        }
        map.put(word, values);
      }
    }
    catch(Exception e){
      System.out.println(e);
    }
    return map;
  }
}