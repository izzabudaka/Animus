import java.util.*;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.nio.charset.Charset;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class SentenceWriter{
  public static void main(String[] args) throws Exception{
    if(args.length < 1){
      System.out.println("usage: java SentenceWriter fileToREAD");
      return;
    }
    String sentences[] = readSentences(args[0]);
    for(String sentence : sentences)
      if(sentence != null)
        System.out.println(sentence);
  }
  private static String[] readSentences(String filename) throws Exception{
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(
            new FileInputStream(filename),
            Charset.forName("UTF-8")));
    int c;
    String[] sentences = new String[1009];
    int i = 0;
    String sentence = "";
    while((c = reader.read()) != -1) {
      char character = (char) c;
      if(character == '.'){
        sentences[i] = sentence;
        sentence = "";
        i++;
      }
      else if(character != '\n')
        sentence += character;
      else
        sentence += " ";
    }
    return sentences;
  }
}