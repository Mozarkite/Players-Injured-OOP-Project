package ML_Project;

import javax.swing.SwingUtilities;

public class control {
    public static void main(String[] args) {
        // Initialise the classifier, train it, and pass it to the GUI
        NaiveBayesClassifier classifier = new NaiveBayesClassifier();
        classifier.trainFromCSV("adjusted_player_injury_dataset.csv");

        // Optional: Print frequency table for debugging
        classifier.printFrequencyTable();

        // Evaluate accuracy from in-memory dataset (includes dynamic updates later)
        double initialAccuracy = classifier.evaluateAccuracyFromMemory();
        System.out.printf("Initial Model Accuracy: %.2f%%\n", initialAccuracy);

        // Create the GUI with the trained classifier
        new MainGUI(classifier);
    }
}
