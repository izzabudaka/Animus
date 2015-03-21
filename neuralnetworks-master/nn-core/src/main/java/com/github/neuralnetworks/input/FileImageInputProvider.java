package com.github.neuralnetworks.input;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * ImageInputProvider that retrieves all images from a directory (it have to contain only images and no subdirectories)
 */
public class FileImageInputProvider extends ImageInputProvider {

    private static final long serialVersionUID = 1L;

    private File directory;
    private String[] files;
    private List<Integer> elementsOrder;
    private int currentEl;

    public FileImageInputProvider(File directory) {
	this(null, directory);
    }

    public FileImageInputProvider(InputConverter inputConverter, File directory) {
	super(inputConverter);
	this.directory = directory;
	this.files = directory.list();
    }

    @Override
    public int getInputSize() {
	return files.length;
    }

    @Override
    public float[] getNextTarget() {
	return null;
    }

    @Override
    protected BufferedImage getNextImage() {
	BufferedImage result = null;
	try {
	    result = ImageIO.read(new File(directory, files[currentEl]));
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return result;
    }

    @Override
    public void beforeSample() {
	if (elementsOrder.size() == 0) {
	    resetOrder();
	}

	currentEl = elementsOrder.remove(getProperties().getUseRandomOrder() ? getProperties().getRandom().nextInt(elementsOrder.size()) : 0);
    }

    @Override
    public void reset() {
	super.reset();
	resetOrder();
    }

    public void resetOrder() {
	elementsOrder = new ArrayList<Integer>(files.length);
	for (int i = 0; i < files.length; i++) {
	    elementsOrder.add(i);
	}
    }
}
