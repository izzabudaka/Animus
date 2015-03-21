/**
 * Copyright 2014 Neuroph Project http://neuroph.sourceforge.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.neuroph.core;

import java.io.*;
import java.util.*;

import org.neuroph.core.events.NeuralNetworkEvent;
import org.neuroph.core.events.NeuralNetworkEventListener;
import org.neuroph.core.exceptions.NeurophException;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.NeuralNetworkEventType;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.util.NeuralNetworkType;
import org.neuroph.util.NeurophArrayList;
import org.neuroph.util.plugins.PluginBase;
import org.neuroph.util.random.RangeRandomizer;
import org.neuroph.util.random.WeightsRandomizer;

/**
 * <pre>
 * Base class for artificial neural networks. It provides generic structure and functionality
 * for the neural networks. Neural network contains a collection of neuron layers and learning rule.
 * Custom neural networks are created by deriving from this class, creating layers of interconnected network specific neurons,
 * and setting network specific learning rule.
 * </pre>
 *
 * @see Layer
 * @see LearningRule
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class NeuralNetwork<L extends LearningRule> implements Serializable {

    /**
     * The class fingerprint that is set to indicate serialization compatibility
     * with a previous version of the class.
     */
    private static final long serialVersionUID = 6L;
    /**
     * Network type id (see neuroph.util.NeuralNetworkType)
     */
    private NeuralNetworkType type;

    /**
     * Neural network layers
     */
    // private Layer[] layers;
    private NeurophArrayList<Layer> layers;

    /**
     * Neural network output buffer
     */
    protected double[] output;

    /**
     * Reference to network input neurons
     */
    private NeurophArrayList<Neuron> inputNeurons;

    /**
     * Reference to network output neurons
     */
    private NeurophArrayList<Neuron> outputNeurons;

    /**
     * Learning rule for this network
     */
    private L learningRule; // learning algorithme
    /**
     * Separate thread for learning rule
     */
    private transient Thread learningThread; // thread for learning rule
    /**
     * Plugins collection
     */
    private Map<Class, PluginBase> plugins;
    /**
     * Label for this network
     */
    private String label = "";

    /**
     * List of neural network listeners
     */
    private transient List<NeuralNetworkEventListener> listeners = new ArrayList();

    /**
     * Creates an instance of empty neural network.
     */
    public NeuralNetwork() {
        this.layers = new NeurophArrayList<>(Layer.class);
        this.inputNeurons = new NeurophArrayList<>(Neuron.class);
        this.outputNeurons = new NeurophArrayList<>(Neuron.class);
        this.plugins = new HashMap<>();
    }

    /**
     * Adds layer to neural network
     *
     * @param layer layer to add
     */
    public void addLayer(Layer layer) {
//        // grow existing layers array to make space for new layer
//        this.layers = Arrays.copyOf(layers, layers.length + 1);    
//        // add new layer at the end of array
//        this.layers[layers.length - 1] = layer;                    

        // in case of null value throw exception to prevent adding null layers
        if (layer == null) {
            throw new IllegalArgumentException("Layer cant be null!");
        }

        // add layer to layers collection
        layers.add(layer);

        // set parent network for added layer
        layer.setParentNetwork(this);
        
        // notify listeners that layer has been added
        fireNetworkEvent(new NeuralNetworkEvent(layer, NeuralNetworkEventType.LAYER_ADDED));
    }

    /**
     * Adds layer to specified index position in network
     *
     * @param index index position to add layer
     * @param layer layer to add
     */
    public void addLayer(int index, Layer layer) {
//        // first grow layers array to make space for new layer
//        this.layers = Arrays.copyOf(layers, layers.length + 1); 
//
//        // then shift all layers to the right to make room at specified index position     
//        for (int i = layers.length - 1; i > index; i--) { 
//            this.layers[i] = this.layers[i - 1];
//        }
//        
//        // add new layer to array at specified index
//        this.layers[index] = layer;

        // in case of null value throw exception to prevent adding null layers
        if (layer == null) {
            throw new IllegalArgumentException("Layer cant be null!");
        }

        // add layer to layers collection at specified position        
        layers.add(index, layer);

        // set parent network for added layer
        layer.setParentNetwork(this);
        
        // notify listeners that layer has been added
        fireNetworkEvent(new NeuralNetworkEvent(layer, NeuralNetworkEventType.LAYER_ADDED));       
    }

    /**
     * Removes specified layer from network
     *
     * @param layer layer to remove
     * @throws Exception 
     */
    public void removeLayer(Layer layer)  {
//        int index = indexOf(layer);
//        removeLayerAt(index);

        if (!layers.remove(layer)){
        	throw new RuntimeException("Layer not in Neural n/w");
        }
        
        // notify listeners that layer has been removed
        fireNetworkEvent(new NeuralNetworkEvent(layer, NeuralNetworkEventType.LAYER_REMOVED));        
    }

    /**
     * Removes layer at specified index position from net
     *
     * @param index int value represents index postion of layer which should be
     * removed
     */
    public void removeLayerAt(int index) throws ArrayIndexOutOfBoundsException{
//        layers[index].removeAllNeurons();
//        
//        for (int i = index; i < layers.length - 1; i++) {
//            layers[i] = layers[i + 1];
//        }
//        layers[layers.length - 1] = null;
//        if (layers.length > 0) {
//            layers = Arrays.copyOf(layers, layers.length - 1);
//        }

        Layer layer = layers.get(index);
       
        	layers.remove(index);
        	
        
        // notify listeners that layer has been removed
        fireNetworkEvent(new NeuralNetworkEvent(layer, NeuralNetworkEventType.LAYER_REMOVED));
    }

    /**
     * Returns layers array
     *
     * @return array of layers
     */
    public final Layer[] getLayers() {
        return layers.asArray();
    }

    /**
     * Returns layer at specified index
     *
     * @param index layer index position
     * @return layer at specified index position
     */
    public Layer getLayerAt(int index) {
        return layers.get(index);
    }

    /**
     * Returns index position of the specified layer
     *
     * @param layer requested Layer object
     * @return layer position index
     */
    public int indexOf(Layer layer) {
        return layers.indexOf(layer);
//        for (int i = 0; i < this.layers.length; i++) {
//            if (layers[i] == layer) {
//                return i;
//            }
//        }
//
//        return -1;
    }

    /**
     * Returns number of layers in network
     *
     * @return number of layes in net
     */
    public int getLayersCount() {
        return layers.size();
    }

    /**
     * Sets network input. Input is an array of double values.
     *
     * @param inputVector network input as double array
     */
    public void setInput(double... inputVector) throws VectorSizeMismatchException {
        if (inputVector.length != inputNeurons.size()) {
            throw new VectorSizeMismatchException("Input vector size does not match network input dimension!");
        }

        int i = 0;
        for (Neuron neuron : this.inputNeurons) {
            neuron.setInput(inputVector[i]); // set input to the coresponding neuron
            i++;
        }

    }

    /**
     * Returns network output vector. Output vector is an array  collection of Double
     * values.
     *
     * @return network output vector
     */
    public double[] getOutput() {
        // double[] outputVector = new double[outputNeurons.length];// use attribute to avoid creating to arrays and avoid GC work
        for (int i = 0; i < outputNeurons.size(); i++) {
            output[i] = outputNeurons.get(i).getOutput();
        }

        return output;
    }

    /**
     * Performs calculation on whole network
     */
    public void calculate() {
        for (Layer layer : this.layers.asArray()) {
            layer.calculate();
        }

        fireNetworkEvent(new NeuralNetworkEvent(this, NeuralNetworkEventType.CALCULATED));
    }

    /**
     * Resets the activation levels for whole network
     */
    public void reset() {
        for (Layer layer : this.layers) {
            layer.reset();
        }
    }

    /**
     * Learn the specified training set
     *
     * @param trainingSet set of training elements to learn
     */
    public void learn(DataSet trainingSet) {
        if (trainingSet == null) {
            throw new IllegalArgumentException("Training set is null!");
        }

        learningRule.learn(trainingSet);
    }

    /**
     * Learn the specified training set, using specified learning rule
     *
     * @param trainingSet set of training elements to learn
     * @param learningRule instance of learning rule to use for learning
     */
    public void learn(DataSet trainingSet, L learningRule) {
        setLearningRule(learningRule);
        learningRule.learn(trainingSet);
    }

    /**
     * Starts learning in a new thread to learn the specified training set, and
     * immediately returns from method to the current thread execution
     *
     * @param trainingSet set of training elements to learn
     */
    public void learnInNewThread(final DataSet trainingSet) {
        learningThread = new Thread() {
            @Override
            public void run() {
                learningRule.learn(trainingSet);
            }
        };
        learningThread.setName("NeurophLearningThread");
        learningThread.start();
    }

    /**
     * Starts learning with specified learning rule in new thread to learn the
     * specified training set, and immediately returns from method to the
     * current thread execution
     *
     * @param trainingSet set of training elements to learn
     * @param learningRule learning algorithm
     */
    public void learnInNewThread(DataSet trainingSet, L learningRule) {
        setLearningRule(learningRule);
        learnInNewThread(trainingSet);
    }

    /**
     * Stops learning
     */
    public void stopLearning() {
        learningRule.stopLearning();
    }

    /**
     * Pause the learning - puts learning thread in wait state. Makes sense only
     * wen learning is done in new thread with learnInNewThread() method
     */
    public void pauseLearning() {
        if (learningRule instanceof IterativeLearning) {
            ((IterativeLearning) learningRule).pause();
        }
    }

    /**
     * Resumes paused learning - notifies the learning rule to continue
     */
    public void resumeLearning() {
        if (learningRule instanceof IterativeLearning) {
            ((IterativeLearning) learningRule).resume();
        }
    }

    /**
     * Randomizes connection weights for the whole network
     */
    public void randomizeWeights() {
        randomizeWeights(new WeightsRandomizer());
    }

    /**
     * Randomizes connection weights for the whole network within specified
     * value range
     */
    public void randomizeWeights(double minWeight, double maxWeight) {
        randomizeWeights(new RangeRandomizer(minWeight, maxWeight));
    }

    /**
     * Randomizes connection weights for the whole network using specified
     * random generator
     */
    public void randomizeWeights(Random random) {
        randomizeWeights(new WeightsRandomizer(random));
    }

    /**
     * Randomizes connection weights for the whole network using specified
     * randomizer
     *
     * @param randomizer random weight generator to use
     */
    public void randomizeWeights(WeightsRandomizer randomizer) {
        randomizer.randomize(this);
    }

    /**
     * Returns type of this network
     *
     * @return network type
     */
    public NeuralNetworkType getNetworkType() {
        return type;
    }

    /**
     * Sets type for this network
     *
     * @param type network type
     */
    public void setNetworkType(NeuralNetworkType type) {
        this.type = type;
    }

    /**
     * Returns input neurons
     *
     * @return input neurons
     */
    public Neuron[] getInputNeurons() {
        return this.inputNeurons.asArray();
    }

    /**
     * Gets number of input neurons
     *
     * @return number of input neurons
     */
    public int getInputsCount() {
        return this.inputNeurons.size();
    }

    /**
     * Sets input neurons
     *
     * @param inputNeurons array of input neurons
     */
    public void setInputNeurons(Neuron[] inputNeurons) {
        for (Neuron neuron : inputNeurons) {
            this.inputNeurons.add(neuron);
        }
    }

    /**
     * Returns output neurons
     *
     * @return array of output neurons
     */
    public Neuron[] getOutputNeurons() {
        return this.outputNeurons.asArray();
    }

    public int getOutputsCount() {
        return this.outputNeurons.size();
    }

    /**
     * Sets output neurons
     *
     * @param outputNeurons output neurons collection
     */
    public void setOutputNeurons(Neuron[] outputNeurons) {
        for (Neuron neuron : outputNeurons) {
            this.outputNeurons.add(neuron);
        }
        this.output = new double[outputNeurons.length];
    }
    
    /**
     * Sets labels for output neurons
     * @param labels  labels for output neurons
     */
    public void setOutputLabels(String[] labels) {
        for (int i=0; i<outputNeurons.size(); i++) {
            outputNeurons.get(i).setLabel(labels[i]);
        }
    }

    /**
     * Returns the learning algorithm of this network
     *
     * @return algorithm for network training
     */
    public L getLearningRule() {
        return this.learningRule;
    }

    /**
     * Sets learning algorithm for this network
     *
     * @param learningRule learning algorithm for this network
     */
    public void setLearningRule(L learningRule) {
        if (learningRule == null) {
            throw new IllegalArgumentException("Learning rule can't be null!");
        }

        learningRule.setNeuralNetwork(this);
        this.learningRule = learningRule;
    }

    /**
     * Returns the current learning thread (if it is learning in the new thread
     * Check what happens if it learns in the same thread)
     */
    public Thread getLearningThread() {
        return learningThread;
    }

    /**
     * Returns all network weights as an double array
     *
     * @return network weights as an double array
     */
    public Double[] getWeights() {
        List<Double> weights = new ArrayList();
        for (Layer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                for (Connection conn : neuron.getInputConnections()) {
                    weights.add(conn.getWeight().getValue());
                }
            }
        }

        return weights.toArray(new Double[weights.size()]);
    }

    /**
     * Sets network weights from the specified double array
     *
     * @param weights array of weights to set
     */
    public void setWeights(double[] weights) {
        int i = 0;
        for (Layer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                for (Connection conn : neuron.getInputConnections()) {
                    conn.getWeight().setValue(weights[i]);
                    i++;
                }
            }
        }
    }
    
    public boolean isEmpty() {
        return layers.isEmpty();
    }

    /**
     * Creates connection with specified weight value between specified neurons
     *
     * @param fromNeuron neuron to connect
     * @param toNeuron neuron to connect to
     * @param weightVal connection weight value
     */
    public void createConnection(Neuron fromNeuron, Neuron toNeuron, double weightVal) {
        //  Connection connection = new Connection(fromNeuron, toNeuron, weightVal);
        toNeuron.addInputConnection(fromNeuron, weightVal);
    }

    @Override
    public String toString() {
        if (label != null) {
            return label;
        }

        return super.toString();
    }

    /**
     * Saves neural network into the specified file.
     *
     * @param filePath file path to save network into
     */
    public void save(String filePath) {
        ObjectOutputStream out = null;
        try {
            File file = new File(filePath);
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            out.writeObject(this);
            out.flush();
        } catch (IOException ioe) {
            throw new NeurophException("Could not write neural network to file!", ioe);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Loads neural network from the specified file.
     *
     * @param filePath file path to load network from
     * @return loaded neural network as NeuralNetwork object
     * @deprecated Use createFromFile method instead
     */
    public static NeuralNetwork load(String filePath) {
        ObjectInputStream oistream = null;

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException("Cannot find file: " + filePath);
            }

            oistream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            NeuralNetwork nnet = (NeuralNetwork) oistream.readObject();
            return nnet;

        } catch (IOException ioe) {
            throw new NeurophException("Could not read neural network file!", ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new NeurophException("Class not found while trying to read neural network from file!", cnfe);
        } finally {
            if (oistream != null) {
                try {
                    oistream.close();
                } catch (IOException ioe) {
                }
            }
        }
    }

    /**
     * Loads neural network from the specified InputStream.
     *
     * @param inputStream input stream to load network from
     * @return loaded neural network as NeuralNetwork object
     */
    public static NeuralNetwork load(InputStream inputStream) {
        ObjectInputStream oistream = null;

        try {
            oistream = new ObjectInputStream(new BufferedInputStream(inputStream));
            NeuralNetwork nnet = (NeuralNetwork) oistream.readObject();

            return nnet;

        } catch (IOException ioe) {
            throw new NeurophException("Could not read neural network file!", ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new NeurophException("Class not found while trying to read neural network from file!", cnfe);
        } finally {
            if (oistream != null) {
                try {
                    oistream.close();
                } catch (IOException ioe) {
                }
            }
        }
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        listeners = new ArrayList();
    }

    /**
     * Loads and return s neural network instance from specified file
     *
     * @param file neural network file
     * @return neural network instance
     */
    public static NeuralNetwork createFromFile(File file) {
        ObjectInputStream oistream = null;

        try {
            if (!file.exists()) {
                throw new FileNotFoundException("Cannot find file: " + file);
            }

            oistream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            NeuralNetwork nnet = (NeuralNetwork) oistream.readObject();
            return nnet;

        } catch (IOException ioe) {
            throw new NeurophException("Could not read neural network file!", ioe);
            //ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            throw new NeurophException("Class not found while trying to read neural network from file!", cnfe);
            // cnfe.printStackTrace();
        } finally {
            if (oistream != null) {
                try {
                    oistream.close();
                } catch (IOException ioe) {
                }
            }
        }
    }

    public static NeuralNetwork createFromFile(String filePath) {
        File file = new File(filePath);
        return NeuralNetwork.createFromFile(file);
    }

    /**
     * Adds plugin to neural network
     *
     * @param plugin neural network plugin to add
     */
    public void addPlugin(PluginBase plugin) {
        plugin.setParentNetwork(this);
        this.plugins.put(plugin.getClass(), plugin);
    }

    /**
     * Returns the requested plugin
     *
     * @param pluginClass class of the plugin to get
     * @return instance of specified plugin class
     */
    public PluginBase getPlugin(Class pluginClass) {
        return this.plugins.get(pluginClass);
    }

    /**
     * Removes the plugin with specified name
     *
     * @param pluginClass class of the plugin to remove
     */
    public void removePlugin(Class pluginClass) {
        this.plugins.remove(pluginClass);
    }

    /**
     * Get network label
     *
     * @return network label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set network label
     *
     * @param label network label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    // This methods allows classes to register for LearningEvents
    public synchronized void addListener(NeuralNetworkEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener is null!");
        
        listeners.add(listener);
    }

    // This methods allows classes to unregister for LearningEvents
    public synchronized void removeListener(NeuralNetworkEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener is null!");
        
        listeners.remove(listener);
    }

    // This method is used to fire NeuralNetworkEvents
    public synchronized void fireNetworkEvent(NeuralNetworkEvent evt) {
        for (NeuralNetworkEventListener listener: listeners) {
            listener.handleNeuralNetworkEvent(evt);
        }
    }
}
