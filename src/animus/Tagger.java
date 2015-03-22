package animus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.util.*;
import java.io.PrintWriter;

public class Tagger{
  public static void main(String[] args) throws Exception{
    String paragraph = "";
    List<List<HasWord>> sentences;
    if (args.length != 2) {
      System.err.println("usage: java TaggerDemo modelFile fileToTag");
      return;
    }
    MaxentTagger tagger = new MaxentTagger(args[0]);
    sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(args[1])));
    for (List<HasWord> sentence : sentences) {
      List<TaggedWord> tSentence = tagger.tagSentence(sentence);
      paragraph +=  " " +Sentence.listToString(tSentence, false);
      //System.out.println(Sentence.listToString(tSentence, false));
    }
    ArrayList<String> words = new ArrayList<String>();
    String[] wordsWithPos = paragraph.split(" ");
    for(String wordWithPos : wordsWithPos){
      String[] word = wordWithPos.split("/");
      if(word.length > 1) {
        word[0] = word[0].toLowerCase();
        if(word[1].equals("RB"))
          words.add(word[0]);
        else if(word[1].startsWith("VB"))
          words.add(word[0]);
        else if(word[1].equals("JJ"))
          words.add(word[0]);    }
    }
    List<String> places = Arrays.asList("n't", "was", "had","as", "'d", "were", "get", "got", "do", "doing", "been", "here", "there");
    for(String unwantedWord : places)
      words.remove(unwantedWord);
    writeFile(words, "banana");
  }
  private static void writeFile(ArrayList<String> words, String filename){
    try{
      PrintWriter writer = new PrintWriter(filename, "UTF-8");
      for (String word : words)
        writer.printf("%s\n", word);
      writer.close();
    }
    catch(Exception e){
      System.out.println(e);
    }
  }
}
