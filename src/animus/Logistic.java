package animus;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tpeng
 * Date: 6/22/12
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Logistic {

    /** the learning rate */
    private double rate;

    /** the number of iterations */
    private int ITERATIONS = 3000;

    /** the weight to learn */
    private double[] weights;

    private static DataSet _dataSet = new DataSet();

    public Logistic(int n) {
        this.rate = 0.0001;
        weights = new double[n];
        initWeights(weights);
    }

    private void initWeights(double[] weights) {
        for (int i =0;i<weights.length ;i++ ) {
            weights[i] = Math.random();
        }
    }

    private double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    public void train(List<Instance> instances) {
        for (int n=0; n<ITERATIONS; n++) {
            double lik = 0.0;
            for (int i=0; i<instances.size(); i++) {
                int[] x = instances.get(i).getX();
                double predicted = classify(x);
                int label = instances.get(i).getLabel();
                for (int j=0; j<weights.length; j++) {
                    weights[j] = weights[j] + rate * (label - predicted) * x[j];
                }
                // not necessary for learning
                lik += label * Math.log(classify(x)) + (1-label) * Math.log(1- classify(x));
            }
             //System.out.println("iteration: " + n + " " + Arrays.toString(weights) + " mle: " + lik);
        }
    }

    private double classify(int[] x) {
        double logit = .0;

        for (int i=0; i<weights.length;i++)  {
            logit += weights[i] * x[i];
        }
        return sigmoid(logit);
    }


    public static double[] classify(String str) {

      double[] res = new double[6];
      int[] vec = _dataSet.stringToVector(str);
      for(int j=0; j<classifiers.length; j++) {
        res[j] = classifiers[j].classify(vec);
      }
      return res;
    }

    public static void train1() {
      int[][] examples           = _dataSet.getExamples();
      int[]   defaultLabels      = _dataSet.getDefaultLabels();
      List<Instance> instances   = _dataSet.getInstances();

      int featuresNumber = examples[0].length;
      int examplesNumber = examples.length;

      int[][] weights = new int[6][featuresNumber];

      int[] labels    = {0,1,2,3,4,5};

      System.out.println("Instances: "  + instances.size());
      System.out.println("ExamplesNum:" + examplesNumber);

      for(int label:labels) {

          for(int i = 1; i < examplesNumber -1 ; i++) {
              int labelX = defaultLabels[i] == label ? 1 : 0;
              instances.get(i).label = labelX;
          }
          
          classifiers[label] = new Logistic(featuresNumber - 2);
          classifiers[label].train(instances);
      }
    }


    private static Logistic[] classifiers = new Logistic[6];

    public static void main(String[] args) 
        throws FileNotFoundException 
    {

        DataSet dataSet = new DataSet();

        int[][] examples           = dataSet.getExamples();
        int[]   defaultLabels      = dataSet.getDefaultLabels();
        List<Instance> instances   = dataSet.getInstances();

        int featuresNumber = examples[0].length;
        int examplesNumber = examples.length;

        int[][] weights = new int[6][featuresNumber];

        int[] labels    = {0,1,2,3,4,5};

        System.out.println("Instances: "  + instances.size());
        System.out.println("ExamplesNum:" + examplesNumber);

        for(int label:labels) {

            for(int i = 1; i < examplesNumber -1 ; i++) {
                int labelX = defaultLabels[i] == label ? 1 : 0;
                instances.get(i).label = labelX;
            }
            
            classifiers[label] = new Logistic(featuresNumber - 2);
            classifiers[label].train(instances);
        }

        String text = "Fawkes dived His long golden beak sank out of the sight and a sudden shower of dark blood spattered the floor";
        int[] vector = dataSet.stringToVector(text);

        for(Logistic log:classifiers) {
            System.out.println(log.classify(vector));
        }

        String text2 = "Help me help me Harry muttered wildly someone anyone";
        int[] vector2 = dataSet.stringToVector(text2);

        for(Logistic log:classifiers) {
            System.out.println(log.classify(vector2));
        }

    }
}
