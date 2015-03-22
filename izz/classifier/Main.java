public class Main{
  public static void main(String[] args) {
    PercTrainer trainer = new PercTrainer();
    trainer.modifyWeights();
    String[] phrase = "huge form of the dead basilisk over harry in his blood-soaked robes then to the diary in his hand she".split(" ");
    double[] result = trainer.tester.testPhrase(phrase);
    for(int i = 0; i < result.length; i++)
      System.out.println(result[i]);
  }
}