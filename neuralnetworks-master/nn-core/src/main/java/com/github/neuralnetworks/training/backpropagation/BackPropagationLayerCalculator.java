package com.github.neuralnetworks.training.backpropagation;

import java.util.Set;

import com.github.neuralnetworks.architecture.Layer;
import com.github.neuralnetworks.architecture.NeuralNetwork;
import com.github.neuralnetworks.calculation.memory.ValuesProvider;

/**
 * Base interface for layer calculator. The difference with the feedforward layer calculator is the "activations" parameter, which contains the activations from the feedforward phse
 */
public interface BackPropagationLayerCalculator {
    public void backpropagate(NeuralNetwork nn, Set<Layer> calculatedLayers, ValuesProvider activations, ValuesProvider results);
}
