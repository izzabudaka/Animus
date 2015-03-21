import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
/*import java.util.List;
import java.nio.charset.Charset;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;*/

public class SentenceWriter{
  public static void main(String[] args) throws Exception{
    if(args.length < 1){
      System.out.println("usage: java SentenceWriter fileToREAD");
      return;
    }
    readSentences(args[0]);

  }
  private static void readSentences(String filename) throws Exception
  {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      FileWriter outfile = new FileWriter("perceptron-data.txt", true);
      HashSet<Integer> setOfLabels = new HashSet<Integer>();
      int[] array = {1,2,3,4,5,6};
      for (int i : array)
        setOfLabels.add(i);
      try {
          StringBuilder sb = new StringBuilder();
          String line = br.readLine();

          while (line != null) {
              line = line.trim().replace(",","").replace("\"","").replace(".","").replace("!","").replace("?","").replace("\'","").replace("`","").toLowerCase();
              sb.append(line);
              sb.append(System.lineSeparator());
              line = br.readLine();
          }
          String everything = sb.toString();
          String[] tokens = everything.split("\\s+");
          int i = 0;
          while (i < tokens.length)
          {
              StringBuilder sentence = new StringBuilder();
              for (int j = i; j < i + 20; j ++)
              {
                  sentence.append(tokens[j]);
                  sentence.append(" ");
              }

              i += 20;
              System.out.println(sentence);
              Scanner in = new Scanner(System.in);
              int label = in.nextInt();
              if (label == 69)
                  return;
              else if (setOfLabels.contains(label))
              {
//                  System.out.println("dupa");
                outfile.write(sentence.toString() + "\n");
                outfile.write(label + "\n");
              }

          }
      } finally {
          outfile.close();
          br.close();

      }
    




  
  }//readSentences
}