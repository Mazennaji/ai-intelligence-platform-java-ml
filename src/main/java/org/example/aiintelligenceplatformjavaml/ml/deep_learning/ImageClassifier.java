package org.example.aiintelligenceplatformjavaml.ml.deep_learning;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.util.Map;

@Component
public class ImageClassifier {

    private static final Logger log = LoggerFactory.getLogger(ImageClassifier.class);
    private static final int IMAGE_SIZE = 28 * 28; // MNIST dimensions
    private static final int NUM_CLASSES = 10;      // digits 0–9

    private MultiLayerNetwork model;
    private boolean modelReady = false;

    @PostConstruct
    public void init() {
        try {
            buildModel();
            log.info("Deep Learning model architecture initialized.");
        } catch (Exception e) {
            log.warn("Could not initialize DL4J model: {}", e.getMessage());
        }
    }

    public void buildModel() {
        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .seed(42)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(0.001))
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(IMAGE_SIZE)
                        .nOut(256)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nOut(128)
                        .activation(Activation.RELU)
                        .build())
                .layer(2, new DenseLayer.Builder()
                        .nOut(64)
                        .activation(Activation.RELU)
                        .build())
                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(NUM_CLASSES)
                        .activation(Activation.SOFTMAX)
                        .build())
                .build();

        model = new MultiLayerNetwork(config);
        model.init();
        modelReady = true;

        log.info("DL4J Model: {} parameters", model.numParams());
    }

    public Map<String, Object> predict(double[] pixelData) {
        if (!modelReady || model == null) {
            return Map.of(
                    "error", "Model not initialized",
                    "status", "unavailable"
            );
        }

        try {
            INDArray input = Nd4j.create(pixelData).reshape(1, IMAGE_SIZE);
            INDArray output = model.output(input);

            int predictedClass = output.argMax(1).getInt(0);
            double confidence = output.getDouble(0, predictedClass) * 100;

            return Map.of(
                    "predictedDigit", predictedClass,
                    "confidence", Math.round(confidence * 100.0) / 100.0,
                    "method", "Deep Neural Network (DL4J)",
                    "architecture", "784 → 256 → 128 → 64 → 10",
                    "note", "Model requires training on MNIST dataset for accurate predictions"
            );
        } catch (Exception e) {
            return Map.of(
                    "error", e.getMessage(),
                    "status", "prediction_failed"
            );
        }
    }

    public Map<String, Object> getModelInfo() {
        return Map.of(
                "modelReady", modelReady,
                "architecture", "Dense Neural Network",
                "layers", 4,
                "inputSize", IMAGE_SIZE,
                "outputClasses", NUM_CLASSES,
                "parameters", model != null ? model.numParams() : 0
        );
    }
}
