package animus;

public class Determiner {

  double[] _oldRatios;

  public Determiner(int numClasses) {
    _oldRatios = new double[numClasses];
    for(int i=0; i<numClasses; i++) {
      _oldRatios[i] = 0.5;
    }
  }

  public double[] computeNewRatios(double[] newRatios) {
    double[] newr = _oldRatios;
  
    for(int i=0; i<newRatios.length; i++) {
      newr[i] = (_oldRatios[i] + newRatios[i])/2;
    }

    _oldRatios = newr;
    return newr;
  }

  public double[] deriveAudioGainValues(double threshold, double[] ratios) {
    double[] gains = new double[ratios.length];
    for(int i=0; i<gains.length; i++) {
      if(ratios[i] < threshold) gains[i] = -30;
      else {
        gains[i] = -(1-ratios[i]);
      }
    }

    return gains;
  }

  public double[] deriveAudioGainValues(double threshold) {
    return deriveAudioGainValues(threshold, _oldRatios);
  }

}
