package com.github.neuralnetworks.architecture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.neuralnetworks.tensor.Matrix;
import com.github.neuralnetworks.tensor.Tensor;
import com.github.neuralnetworks.tensor.TensorFactory;
import com.github.neuralnetworks.util.Environment;

/**
 * Factory for connections. In order to use shared weights it cannot be static.
 */
public class ConnectionFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Connections> connections;
    private float[] sharedWeights;

    public ConnectionFactory() {
	super();
	this.connections = new ArrayList<>();

	if (Environment.getInstance().getUseWeightsSharedMemory()) {
	    this.sharedWeights = new float[0];
	}
    }

    public FullyConnected fullyConnected(Layer inputLayer, Layer outputLayer, int inputUnitCount, int outputUnitCount) {
	Matrix weights = null;
	if (useSharedWeights()) {
	    int l = sharedWeights.length;
	    sharedWeights = Arrays.copyOf(sharedWeights, l + inputUnitCount * outputUnitCount);
	    updateSharedWeights();
	    weights = TensorFactory.tensor(sharedWeights, l, outputUnitCount, inputUnitCount);
	} else {
	    weights = TensorFactory.tensor(outputUnitCount, inputUnitCount);
	}

	return fullyConnected(inputLayer, outputLayer, weights);
    }

    public FullyConnected fullyConnected(Layer inputLayer, Layer outputLayer, Matrix weights) {
	FullyConnected result = new FullyConnected(inputLayer, outputLayer, weights);
	connections.add(result);
	return result;
    }

    public Conv2DConnection conv2d(Layer inputLayer, Layer outputLayer, int inputFeatureMapRows, int inputFeatureMapColumns, int inputFilters, int kernelRows, int kernelColumns, int outputFilters, int stride) {
	Tensor weights = null;
	if (useSharedWeights()) {
	    int l = sharedWeights.length;
	    sharedWeights = Arrays.copyOf(sharedWeights, l + outputFilters * inputFilters * kernelRows * kernelColumns);
	    updateSharedWeights();
	    weights = TensorFactory.tensor(sharedWeights, l, outputFilters, inputFilters, kernelRows, kernelColumns);
	} else {
	    weights = TensorFactory.tensor(outputFilters, inputFilters, kernelRows, kernelColumns);
	}

	return conv2d(inputLayer, outputLayer, inputFeatureMapRows, inputFeatureMapColumns, weights, stride);
    }

    public Conv2DConnection conv2d(Layer inputLayer, Layer outputLayer, int inputFeatureMapRows, int inputFeatureMapColumns, Tensor weights, int stride) {
	Conv2DConnection result = new Conv2DConnection(inputLayer, outputLayer, inputFeatureMapRows, inputFeatureMapColumns, weights, stride);
	connections.add(result);
	return result;
    }

    public Subsampling2DConnection subsampling2D(Layer inputLayer, Layer outputLayer, int inputFeatureMapRows, int inputFeatureMapColumns, int subsamplingRegionRows, int subsamplingRegionCols, int filters) {
	return new Subsampling2DConnection(inputLayer, outputLayer, inputFeatureMapRows, inputFeatureMapColumns, subsamplingRegionRows, subsamplingRegionCols, filters);
    }

    public boolean useSharedWeights() {
	return sharedWeights != null;
    }

    public List<Connections> getConnections() {
        return connections;
    }

    private void updateSharedWeights() {
	connections.stream().filter(c -> c instanceof WeightsConnections).forEach(c -> ((WeightsConnections) c).getWeights().setElements(sharedWeights));
    }
}
