package com.github.neuralnetworks.training.rbm;

import com.github.neuralnetworks.architecture.types.RBM;
import com.github.neuralnetworks.calculation.RBMLayerCalculator;
import com.github.neuralnetworks.util.Constants;
import com.github.neuralnetworks.util.Environment;
import com.github.neuralnetworks.util.Properties;

/**
 * Base class for Aparapi Contrastive Divergence
 * Supports learning rate, momentum and weight decay
 */
public class AparapiCDTrainer extends CDTrainerBase {

    private static final long serialVersionUID = 1L;

    /**
     * weights update kernel for the connections between the visible and the hidden layer
     */
    private CDWeightUpdatesKernel weightUpdatesKernel;

    /**
     * weights update kernel for visible bias connections
     */
    private CDBiasUpdatesKernel visibleBiasUpdatesKernel;

    /**
     * weights update kernel for the hidden bias connections
     */
    private CDBiasUpdatesKernel hiddenBiasUpdatesKernel;

    public AparapiCDTrainer(Properties properties) {
	super(properties);
    }

    /* (non-Javadoc)
     * @see com.github.neuralnetworks.training.rbm.CDTrainerBase#updateWeights(com.github.neuralnetworks.architecture.Matrix, com.github.neuralnetworks.architecture.Matrix, com.github.neuralnetworks.architecture.Matrix, com.github.neuralnetworks.architecture.Matrix)
     * before each update the kernel update parameters are refreshed
     */
    @Override
    protected void updateWeights() {
	RBM rbm = getNeuralNetwork();

	RBMLayerCalculator lc = getLayerCalculator();
	int mbs = lc.getPositivePhaseVisible().getDimensions()[lc.getPositivePhaseVisible().getDimensions().length - 1];

	if (weightUpdatesKernel == null || weightUpdatesKernel.getMiniBatchSize() != mbs) {
	    weightUpdatesKernel = new CDWeightUpdatesKernel(lc.getPositivePhaseVisible(), lc.getPositivePhaseHidden(), lc.getNegativePhaseVisible(), lc.getNegativePhaseHidden(), rbm.getMainConnections().getWeights(), getLearningRate(), getMomentum(), getl1weightDecay(), getl2weightDecay());
	}
	Environment.getInstance().getExecutionStrategy().execute(weightUpdatesKernel, rbm.getMainConnections().getWeights().getRows());

	// update visible bias
	if (rbm.getVisibleBiasConnections() != null) {
	    if (visibleBiasUpdatesKernel == null || visibleBiasUpdatesKernel.getMiniBatchSize() != mbs) {
		visibleBiasUpdatesKernel = new CDBiasUpdatesKernel(rbm.getVisibleBiasConnections().getWeights(), lc.getPositivePhaseVisible(), lc.getNegativePhaseVisible(), getLearningRate(), getMomentum());
	    }

	    Environment.getInstance().getExecutionStrategy().execute(visibleBiasUpdatesKernel, rbm.getVisibleBiasConnections().getWeights().getSize());
	}

	// update hidden bias
	if (rbm.getHiddenBiasConnections() != null) {
	    if (hiddenBiasUpdatesKernel == null || hiddenBiasUpdatesKernel.getMiniBatchSize() != mbs) {
		hiddenBiasUpdatesKernel = new CDBiasUpdatesKernel(rbm.getHiddenBiasConnections().getWeights(), lc.getPositivePhaseHidden(), lc.getNegativePhaseHidden(), getLearningRate(), getMomentum());
	    }

	    Environment.getInstance().getExecutionStrategy().execute(hiddenBiasUpdatesKernel, rbm.getHiddenBiasConnections().getWeights().getSize());
	}
    }

    protected float getLearningRate() {
	return properties.getParameter(Constants.LEARNING_RATE);
    }

    protected float getMomentum() {
	return (float) (properties.getParameter(Constants.MOMENTUM) != null ? properties.getParameter(Constants.MOMENTUM) : 0f);
    }

    protected float getl1weightDecay() {
	return (float) (properties.getParameter(Constants.L1_WEIGHT_DECAY) != null ? properties.getParameter(Constants.L1_WEIGHT_DECAY) : 0f);
    }

    protected float getl2weightDecay() {
	return (float) (properties.getParameter(Constants.L2_WEIGHT_DECAY) != null ? properties.getParameter(Constants.L2_WEIGHT_DECAY) : 0f);
    }
}
