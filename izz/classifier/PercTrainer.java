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
    predictedResults = new HashMap(1009);
    trainingData = readSentences("Sentences.txt");
  }
  public void modifyWeights(){
    Map
    double prevCost = 0.0;
    double currentCost = 0.0;
    for(int i = 0; i < labelNo; i++){
      Map<String, Double> predictedResults;
      for(Map.Entry<String, Integer> entry : trainingData.entrySet()){
        String[] phrase = entry.getKey().split(" ");
        predictedResults.put(entry.getKey(), tester.testPhrase(phrase,i));
      }
      while(Math.abs(prevCost - (currentCost=costFunction(predictedResults))) > maxCostDiff){
        prevCost = currentCost;
        for(Map.Entry<String, Integer> entry : tester.weightsMatrix.entrySet()){
          tester.weightsMatrix.put(entry.getKey(), tester.weightsMatrix.get(entry.getKey()).set(i, tester.weightsMatrix.get(entry.getKey()).get(i) -));
        }
      }
    }
  }
  public double updateWeight(double weight,Map<String, Double> results){
    double sum = 0.0;
    for(Map.Entry<String, Integer> entry : trainingData.entrySet()){
      String[] phrase = entry.getKey().split(" ");
      for(int i = 0; i < phrase.length; i++)
        sum += (results.get(entry.getKey()) - entry.getValue()) * tester.wordVector.get(entry.getKey());
    }
    return weight - learningRate/trainingData.size() * 
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
        trainingData.put(sentence, label);
      }
    }
    catch(Exception e){
      System.out.println(e);
    }
    return trainingData;
  }
}