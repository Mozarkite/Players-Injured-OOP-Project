package ML_Project;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainGUI extends JFrame {

    // Prediction ComboBoxes
    private JComboBox<String> playedMatchBox;
    private JComboBox<String> attendanceBox;
    private JComboBox<String> injuryBox;
    private JComboBox<String> weatherBox;

    // Add new training example ComboBoxes
    private JComboBox<String> newPlayedMatchBox;
    private JComboBox<String> newAttendanceBox;
    private JComboBox<String> newInjuryBox;
    private JComboBox<String> newWeatherBox;
    private JComboBox<String> actualInjuryBox;

    private JLabel resultLabel;

    private NaiveBayesClassifier predictor;

    public MainGUI(NaiveBayesClassifier predictor) {
        this.predictor = predictor;

        setTitle("Player Injury Predictor");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //============ Prediction Panel ============
        JPanel predictionPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        predictionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Make a Prediction",
                TitledBorder.LEFT,
                TitledBorder.TOP));

        playedMatchBox = new JComboBox<>(new String[]{"Yes", "No"});
        attendanceBox = new JComboBox<>(new String[]{"High", "Low"});
        injuryBox = new JComboBox<>(new String[]{"Yes", "No"});
        weatherBox = new JComboBox<>(new String[]{"Good", "Bad"});

        predictionPanel.add(new JLabel("Played Last Match:"));
        predictionPanel.add(playedMatchBox);

        predictionPanel.add(new JLabel("Training Attendance:"));
        predictionPanel.add(attendanceBox);

        predictionPanel.add(new JLabel("Previous Injury:"));
        predictionPanel.add(injuryBox);

        predictionPanel.add(new JLabel("Weather Condition:"));
        predictionPanel.add(weatherBox);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton predictButton = new JButton("Predict Injury");
        predictButton.addActionListener(e -> predictInjury());

        JButton trainButton = new JButton("Train Classifier");
        trainButton.addActionListener(e -> trainClassifier());

        buttonPanel.add(predictButton);
        buttonPanel.add(trainButton);

        predictionPanel.add(new JLabel()); // empty label to fill grid
        predictionPanel.add(buttonPanel);

        // ============ Add Training Row Panel ============
        JPanel addTrainingPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        addTrainingPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Add New Training Data",
                TitledBorder.LEFT,
                TitledBorder.TOP));

        newPlayedMatchBox = new JComboBox<>(new String[]{"Yes", "No"});
        newAttendanceBox = new JComboBox<>(new String[]{"High", "Low"});
        newInjuryBox = new JComboBox<>(new String[]{"Yes", "No"});
        newWeatherBox = new JComboBox<>(new String[]{"Good", "Bad"});
        actualInjuryBox = new JComboBox<>(new String[]{"Yes", "No"});

        addTrainingPanel.add(new JLabel("Played Last Match:"));
        addTrainingPanel.add(newPlayedMatchBox);

        addTrainingPanel.add(new JLabel("Training Attendance:"));
        addTrainingPanel.add(newAttendanceBox);

        addTrainingPanel.add(new JLabel("Previous Injury:"));
        addTrainingPanel.add(newInjuryBox);

        addTrainingPanel.add(new JLabel("Weather Condition:"));
        addTrainingPanel.add(newWeatherBox);

        addTrainingPanel.add(new JLabel("Actual Injury Outcome:"));
        addTrainingPanel.add(actualInjuryBox);

        JButton addRowButton = new JButton("Add Row");
        addRowButton.addActionListener(e -> addNewTrainingExample());

        JButton accuracyButton = new JButton("Accuracy");
        accuracyButton.addActionListener(e -> {
            double accuracy = predictor.evaluateAccuracyFromMemory();
            JOptionPane.showMessageDialog(
                    this,
                    String.format("Evaluation Completed!\nAccuracy: %.2f%%", accuracy),
                    "Accuracy Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        JPanel accuracyRowPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        accuracyRowPanel.add(addRowButton);
        accuracyRowPanel.add(accuracyButton);

        addTrainingPanel.add(accuracyRowPanel);
        addTrainingPanel.add(new JLabel()); // filler

        //============ Bottom Label ============
        resultLabel = new JLabel("Result will appear here.", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        //============ Main Layout ============
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(predictionPanel);
        centerPanel.add(addTrainingPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void predictInjury() {
        String match = (String) playedMatchBox.getSelectedItem();
        String attendance = (String) attendanceBox.getSelectedItem();
        String previousInjury = (String) injuryBox.getSelectedItem();
        String weather = (String) weatherBox.getSelectedItem();

        PlayerRecord record = new PlayerRecord(match, attendance, previousInjury, weather);
        String prediction = predictor.predict(record);

        resultLabel.setText("Predicted Injury: " + prediction);
    }

    private void addNewTrainingExample() {
        String match = (String) newPlayedMatchBox.getSelectedItem();
        String attendance = (String) newAttendanceBox.getSelectedItem();
        String previousInjury = (String) newInjuryBox.getSelectedItem();
        String weather = (String) newWeatherBox.getSelectedItem();
        String actualInjury = (String) actualInjuryBox.getSelectedItem();

        PlayerRecord newRecord = new PlayerRecord(match, attendance, previousInjury, weather);
        predictor.addTrainingExample(newRecord, actualInjury);

        resultLabel.setText("New training example added!");
        
        predictor.printFrequencyTable();
        double initialAccuracy = predictor.evaluateAccuracyFromMemory();
        System.out.printf("Updated Model Accuracy: %.2f%%\n", initialAccuracy);
    }

    private void trainClassifier() {
        predictor.trainFromCSV("adjusted_player_injury_dataset.csv");
        resultLabel.setText("Classifier retrained from CSV!");
    }
}
