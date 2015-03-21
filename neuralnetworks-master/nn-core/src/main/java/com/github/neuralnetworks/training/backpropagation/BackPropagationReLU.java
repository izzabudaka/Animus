package com.github.neuralnetworks.training.backpropagation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.neuralnetworks.architecture.Connections;
import com.github.neuralnetworks.architecture.Layer;
import com.github.neuralnetworks.calculation.memory.ValuesProvider;
import com.github.neuralnetworks.tensor.Tensor;
import com.github.neuralnetworks.util.Properties;
import com.github.neuralnetworks.util.Util;

/**
 * Backpropagation connection calculator for relu units
 */
public class BackPropagationReLU extends BackPropagationConnectionCalculatorImpl {

    private static final long serialVersionUID = 1178188233641224762L;

    public BackPropagationReLU(Properties properties) {
	super(properties);
    }

    @Override
    protected void addBackpropFunction(List<Connections> inputConnections, Map<Connections, BackPropagationConnectionCalculator> connectionCalculators, ValuesProvider valuesProvider, ValuesProvider activations, Layer targetLayer) {
	for (Connections c : inputConnections) {
	    if (Util.isBias(c.getInputLayer()) && targetLayer != c.getInputLayer()) {
		connectionCalculators.put(c, new AparapiBackpropReLU(Arrays.asList(c), valuesProvider, activations, Arrays.asList(getWeightUpdates().get(c)), c.getInputLayer(), getLearningRate(), getMomentum(), getL1weightDecay(), getL2weightDecay()));
	    } else {
		connectionCalculators.put(c, new AparapiBackpropReLU(Arrays.asList(c), valuesProvider, activations, Arrays.asList(getWeightUpdates().get(c)), targetLayer, getLearningRate(), getMomentum(), getL1weightDecay(), getL2weightDecay()));
	    }
	}
    }

    public static class AparapiBackpropReLU extends AparapiBackpropagationFullyConnected {

	private static final long serialVersionUID = -3580345016542506932L;

	public AparapiBackpropReLU(List<Connections> inputConnections, ValuesProvider valuesProvider, ValuesProvider activations, List<Tensor> weightUpdates, Layer targetLayer, float learningRate, float momentum, float l1weightDecay, float l2weightDecay) {
	    super(inputConnections, valuesProvider, activations, weightUpdates, targetLayer, learningRate, momentum, l1weightDecay, l2weightDecay);
	}
	
	@Override
	protected void calcDerivative() {
	    int end = outputStartPosition + getGlobalId() * outputRowStep + miniBatchSize * outputColumnStep;
	    int outputId = outputStartPosition + getGlobalId() * outputRowStep;
	    int activationId = activationStartPosition + getGlobalId() * activationRowStep;
	    for (; outputId < end; outputId += outputColumnStep, activationId += activationColumnStep) {
		if (ffActivation[activationId] <= 0) {
		    output[outputId] = 0;
		}
	    }
	}
    }
}
