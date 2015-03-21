
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.util.*;
import java.io.PrintWriter;

class Trainer {
  public static void main(String[] args) throws Exception {
    String paragraph = "";
    List<List<HasWord>> sentences;
    if (args.length != 2) {
      System.err.println("usage: java Trainer modelFile fileToTag");
      return;
    }
    MaxentTagger tagger = new MaxentTagger(args[0]);
    sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(args[1])));
    for (List<HasWord> sentence : sentences) {
      List<TaggedWord> tSentence = tagger.tagSentence(sentence);
      paragraph +=  " " +Sentence.listToString(tSentence, false);
      //System.out.println(Sentence.listToString(tSentence, false));
    }
    Scanner in = new Scanner(System.in);
    System.out.print("Enter current Progress: ");
    int progress = in.nextInt();

    // Split paragraph into words and initialise arraylists
    String[] words = paragraph.split(" ");
    //HashSet<String> adverbs = new HashSet<String>();
    ArrayList adverbs = new ArrayList();
    ArrayList verbs = new ArrayList();
    ArrayList adjectives = new ArrayList();
    Map<String, List<Integer>> wantedWords = new HashMap();
    wantedWords = readFile(wantedWords, "WordVals.txt");
    HashSet<String> unwantedWords = new HashSet<String>(Arrays.asList("n't", "was", "had","as", "'d", "were", "get", "got", "do", "doing", "been", "here", "there"));
    HashSet<String> wantedPOS = new HashSet<String>(Arrays.asList("RB", "VB","JJ"));
    int unwantedCount = 0;
    // add adverbs, adjectives and verbs
    for(int i =0; i < words.length; i++) {
      String[] word = words[i].split("/");
      if(word.length > 1) {
        if(unwantedWords.contains(word[0]))
          unwantedCount++;
        else if(wantedPOS.contains(word[1]) && !wantedWords.containsKey(word[0]))
          wantedWords.put(word[0].toLowerCase(), Arrays.asList(0,0,0,0,0,0));
        if(word[1].equals("RB"))
          adverbs.add(word[0]);
        else if(word[1].startsWith("VB"))
          verbs.add(word[0]);
        else if(word[1].equals("JJ"))
          adjectives.add(word[0]);
      }
    }
    //System.out.println(unwantedCount);
    // Print adverbs, adjectives and verbs
    /*
    for(Object adverb : adverbs)
      System.out.println(adverb);

    for(Object verb : verbs)
      System.out.println(verb);

    for(Object adj : adjectives)
      System.out.println(adj);
    */
    // Suspense 0, Action 1, Dramatic 2, Happy 3, Sad 4, Neutral 5.
    int[] sent = new int[sentences.size()];
    int nprogress= 0;
    for(int i = progress; i < sentences.size(); i++){
      String sentence = sentences.get(i).toString().replace(",","").replace("[","").replace("]","").trim();
      System.out.print(i+1+".\n"+sentence+"\nInsert Mood(Suspense 0, Action 1, Dramatic 2, Happy 3, Sad 4, Neutral 5): ");
      int category = in.nextInt();
      if(category == 69){
        nprogress = i;
        i += 9999;
      }
      else
        sent[i] = category;
    }
    for(int i = progress; i < sentences.size(); i++){
      for(HasWord hWord : sentences.get(i)){
        String word = hWord.toString();
        if(wantedWords.containsKey(word)){
          List<Integer> wordValues = wantedWords.get(word);
          wordValues.set(sent[i], ((int)wordValues.get(sent[i]))+1);
          wantedWords.put(word, wordValues);
        }
      }
    }
    System.out.println(wantedWords);
    writeFile(wantedWords, "Sentences.txt");
    System.out.println("New Progress: " + nprogress);
  }
  private static Map<String, List<Integer>> readFile(Map<String, List<Integer>> map, String filename){
    try{
      Scanner sc=new Scanner(new FileReader(filename));
      while (sc.hasNextLine()) {
        System.out.println("Bo");
        String word = sc.next();
        List<Integer> values = new ArrayList<Integer>();
        for(int i= 0; i < 6; i++){
          String csc = sc.next();
          System.out.println(csc);
          values.add(i,Integer.parseInt(csc));
        }
        map.put(word, values);
        System.out.println(word);
      }
    }
    catch(Exception e){
      System.out.println(e);
    }
    return map;
  }
  private static void writeFile(Map<String, List<Integer>> map, String filename){
    try{
      PrintWriter writer = new PrintWriter(filename, "UTF-8");
      for (Map.Entry<String, List<Integer>> entry : map.entrySet())
        writer.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\n", entry.getKey(), entry.getValue().get(0),entry.getValue().get(1),entry.getValue().get(2),entry.getValue().get(3),entry.getValue().get(4),entry.getValue().get(5));
      writer.close();
    }
    catch(Exception e){
      System.out.println(e);
    }
  }

}
