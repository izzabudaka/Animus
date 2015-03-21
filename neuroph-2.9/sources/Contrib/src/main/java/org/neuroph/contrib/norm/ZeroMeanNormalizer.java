package org.neuroph.contrib.norm;

import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.data.norm.Normalizer;

public class ZeroMeanNormalizer implements Normalizer {

	double[] mean;

	public void normalize(DataSet dataSet) {

		double[] maxInput = DataSetStatistics.calculateMaxByColumns(dataSet);
		double[] minInput = DataSetStatistics.calculateMinByColumns(dataSet);
		double[] meanInput = DataSetStatistics.calculateMean(dataSet);

		for (DataSetRow row : dataSet.getRows()) {
			double[] normalizedInput = row.getInput();

			for (int i = 0; i < dataSet.getInputSize(); i++) {
				double divider = maxInput[i] - minInput[i] == 0 ?  1 : maxInput[i] - minInput[i] ;
				normalizedInput[i] = (normalizedInput[i] - meanInput[i]) / divider;
			}
			row.setInput(normalizedInput);
		}

	}

}
