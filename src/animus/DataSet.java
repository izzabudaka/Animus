package animus;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: tpeng
 * Date: 6/22/12
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataSet {

    private int nextIndex = 0;
    private HashMap<String,Integer> wordSet;        
    

    private int[] defaultLabels;
    private int[][] examples;
    private List<Instance> instances;

    private int exampleIndex;
    private int featuresNumber;
    private int examplesNumber;

    public DataSet() {
        this.wordSet = new HashMap<String,Integer>(1009);
        try {
            loadDataSet();
            loadExamples();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public List<Instance> getInstances() {
        return this.instances;
    }

    private void addToSet(String word) {
        if(!wordSet.containsKey(word)) {
            wordSet.put(word,nextIndex++);
        }
    }

    private void loadDataSet() 
        throws FileNotFoundException
    {   
        Scanner scanner = new Scanner(new File("wordSet.txt"));
        addToSet("dummy");

        while(scanner.hasNextLine()) {
        
            String line = scanner.nextLine();
            if (line.startsWith("#")) {
                continue;
            }

            addToSet(line);
        }

        featuresNumber    = wordSet.size();

    }

    public int[][] getExamples() {
        return this.examples;
    }

    public int[] getDefaultLabels() {
        return this.defaultLabels;
    }

    public int[] stringToVector(String text) {
        int[] vector = new int[featuresNumber];
        String[] words = text.split(" ");


        for(String word:words) {
            try{
                int featureIndex = wordSet.get(word);
                vector[featureIndex]++;
            } catch(NullPointerException e) {
                System.out.println("Word:" + word + " doesn't exist");
            } 
        }

        return vector;
    }

    private void loadExamples()
        throws FileNotFoundException
     {
        examplesNumber    = 130;

        defaultLabels = new int[examplesNumber];
        examples      = new int[examplesNumber][featuresNumber+2];

        int labelIndex = featuresNumber - 1;

        Scanner scanner = new Scanner(new File("traningData.txt"));

        int exampleIndex = 1;

        instances = new ArrayList<Instance>();

        // Dummy entry
        int[] array = new int[featuresNumber];
        instances.add(new Instance(0, array));
        
        // 1
        while(scanner.hasNextLine()) {
        
            System.out.println("KURWA");
            String wordLine = scanner.nextLine();

            // Extract Valuable Words
            String[] words = wordLine.split(" ");
            System.out.println(wordSet.size());

            for(String word:words) {
                try{
                    int featureIndex = wordSet.get(word);
                    System.out.println(exampleIndex + ":" + featureIndex);
                    examples[exampleIndex][featureIndex]++;
                } catch(NullPointerException e) {
                    System.out.println("Exp");                    
                    System.out.println("Word:" + word + " doesn't exist");
                } 
            }

            System.out.println("LKurwaabel:");
            int label = Integer.parseInt(scanner.nextLine());

            // Set the labels
            defaultLabels[exampleIndex]        = label;
            examples[exampleIndex][labelIndex] = label;

            instances.add(new Instance(label, examples[exampleIndex]));
         
            exampleIndex++;
        }        

    }


}
