import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.ling.HasWord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

class Tester{
  public static void main(String[] args){
    if(args.length != 1){
      System.out.println("usage: Tester file");
      return;
    }
    Map<String, List<Integer>> trainingD = new HashMap();
    trainingD = readFile(trainingD, "WordVals.txt");
    trainingD.remove("be");
    trainingD.remove("back");
    trainingD.remove("'ve");
    trainingD.remove("in");
    trainingD.remove("Then");
    trainingD.remove("then");
    trainingD.remove("up");
    trainingD.remove("like");
    List<List<HasWord>> sentences;
    try{
      sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(args[0])));
      System.out.println(sentences);
      for(int i = 0; i < sentences.size(); i++){
        int action, suspense, dramatic, happy, sad, neutral;
        action = suspense = dramatic = happy = sad = neutral=0;
        for(int j = 0; j < sentences.get(i).size(); j++){
          String word = sentences.get(i).get(j).toString().toLowerCase();
          if(trainingD.containsKey(word)){
            System.out.println(word);
            suspense += trainingD.get(word).get(0);
            action += trainingD.get(word).get(1);
            dramatic += trainingD.get(word).get(2);
            happy += trainingD.get(word).get(3);
            sad += trainingD.get(word).get(4);
            neutral += trainingD.get(word).get(5);
          }
        }
        int maxCategory = Math.max(suspense, Math.max(action, Math.max(dramatic, Math.max(happy, Math.max(sad, neutral)))));
        System.out.printf("Sentence %d. Suspense %d, Action %d, Dramatic %d, Happy %d, Sad %d, Neutral %d, Max: %d\n", i+1,suspense, action, dramatic, happy, sad, neutral, maxCategory);
      }
    }
    catch(Exception e){
      System.out.println(e);
    }
  }
  private static Map<String, List<Integer>> readFile(Map<String, List<Integer>> map, String filename){
    try{
      Scanner sc=new Scanner(new FileReader(filename));
      while (sc.hasNextLine()) {
        String word = sc.next();
        List<Integer> values = new ArrayList<Integer>();
        for(int i= 0; i < 6; i++){
          String csc = sc.next();
          values.add(i,Integer.parseInt(csc));
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