public class Main{
  public static void main(String[] args) {
    PercTester tester = new PercTester();
    String[] phrase = new String[]{"bloody", "pure-blood"};
    double[] result = tester.testPhrase(phrase);
    for(int i = 0; i < result.length; i++)
      System.out.println(result[i]);
    PercTrainer trainer = new PercTrainer();
    System.out.println(trainer.trainingData);
  }
}