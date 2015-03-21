import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class PercTrainer{
  Map<String, Integer> trainingData;
  PercTester tester;
  private final int labelNo = 6;
  private final double maxCostDiff = 0.001;
  private final double learningRate = 1;
  public PercTrainer(){
    tester = new PercTester();

    trainingData = new HashMap(1009);
    trainingData = readSentences("Sentences.txt");
  }
  public void modifyWeights(){
    double prevCost = 0.0;
    double currentCost = 0.0;
    for(int i = 0; i < labelNo; i++){
      Map<String, Double> predictedResults = new HashMap(1009);
      for(Map.Entry<String, Integer> entry : trainingData.entrySet()){
        String[] phrase = entry.getKey().split(" ");
        predictedResults.put(entry.getKey(), tester.testPhrase(phrase,i));
      }
      while(Math.abs(prevCost - (currentCost=costFunction(predictedResults))) > maxCostDiff){
        prevCost = currentCost;
        for(Map.Entry<String, Integer> entry : trainingData.entrySet()){
          Map<String, Integer> wordVector = getWordVector(entry.getKey());
          String[] phrases = entry.getKey().split(" ");
          double predictedVal = tester.testPhrase(phrases, i);
          double realVal;
          if(entry.getValue() == i)
            realVal = 1;
          else
            realVal = 0;
          for(String phrase : phrases){
            phrase = phrase.toLowerCase();
            if(tester.wordVector.containsKey(phrase)){
              double sum = 0.0;
              for(int sentence=0; sentence < trainingData.size(); sentence++)
                sum += predictedVal -realVal * wordVector.get(phrase);
              double nWeight = tester.weightsMatrix.get(phrase).get(i) - learningRate/trainingData.size() * sum;
              List<Double> wordWeights = tester.weightsMatrix.get(phrase);
              wordWeights.set(i, nWeight);
              tester.weightsMatrix.put(phrase, wordWeights);
            }
          }
        }
      }
    }
  }
  public Map<String, Integer> getWordVector(String phrase){
    String[] phrases = phrase.split(" ");
    Map<String, Integer> wordVector = new HashMap(1009);
    for(Map.Entry<String, Integer> entry : tester.wordVector.entrySet())
      wordVector.put(entry.getKey(), 0);
    for(String part : phrases){
      part = part.toLowerCase();
      if(tester.wordVector.containsKey(part))
        wordVector.put(part, wordVector.get(part)+1);
    }
    return wordVector;
  }
  public double costFunction(Map<String, Double> results) {
    double cost = -1.0/trainingData.size();
    double sum = 0;
    for(Map.Entry<String, Integer> entry : trainingData.entrySet())
       sum += entry.getValue()*Math.log(results.get(entry.getKey())) + (1.0- entry.getValue())*Math.log(1.0-results.get(entry.getKey()));
     return cost * sum;
  }
  private Map<String, Integer> readSentences(String filename){
    try{
      Scanner sc=new Scanner(new FileReader(filename));
      while (sc.hasNextLine()) {
        String sentence = sc.nextLine();
        System.out.println(sentence);
        int label = Integer.parseInt(sc.nextLine());
        trainingData.put(sentence.toLowerCase().replace(".","").replace(",","").replace("?","").replace("!","").replace(";","").replace("\"",""), label);
      }
    }
    catch(Exception e){
      System.out.println(e);
    }
    return trainingData;
  }
}