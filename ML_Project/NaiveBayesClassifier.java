package ML_Project;

import java.io.*;
import java.util.*;

public class NaiveBayesClassifier {

    private Map<String, Map<String, Integer>> frequencyTable = new HashMap<>();
    private Map<String, Map<String, Integer>> frequencyTable2 = new HashMap<>();
    private List<String[]> fullData = new ArrayList<>(); // ✅ In-memory dataset

    // Train the frequency table from CSV data and store in memory
    public void trainFromCSV(String filePath) {
        frequencyTable.clear();
        fullData.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] row = line.split(",");
                fullData.add(row); // ✅ store row in memory

                String playedLastMatch = row[0].trim();
                String trainingAttendance = row[1].trim();
                String previousInjury = row[2].trim();
                String weatherCondition = row[3].trim();
                String injured = row[4].trim();

                String key = playedLastMatch + "," + trainingAttendance + "," + previousInjury + "," + weatherCondition;

                frequencyTable.putIfAbsent(key, new HashMap<>());
                Map<String, Integer> labelCounts = frequencyTable.get(key);
                labelCounts.put(injured, labelCounts.getOrDefault(injured, 0) + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Predict using main frequency table
    public String predict(PlayerRecord record) {
        String key = record.getPlayedLastMatch() + "," +
                     record.getTrainingAttendance() + "," +
                     record.getPreviousInjury() + "," +
                     record.getWeatherCondition();

        Map<String, Integer> labelCounts = frequencyTable.getOrDefault(key, new HashMap<>());

        if (labelCounts.isEmpty()) return "Unknown";

        int yesCount = labelCounts.getOrDefault("Yes", 0);
        int noCount = labelCounts.getOrDefault("No", 0);

        return (yesCount >= noCount) ? "Yes" : "No";
    }

    // Predict using secondary frequency table
    public String predict2(PlayerRecord record) {
        String key = record.getPlayedLastMatch() + "," +
                     record.getTrainingAttendance() + "," +
                     record.getPreviousInjury() + "," +
                     record.getWeatherCondition();

        Map<String, Integer> labelCounts = frequencyTable2.getOrDefault(key, new HashMap<>());

        if (labelCounts.isEmpty()) return "Unknown";

        int yesCount = labelCounts.getOrDefault("Yes", 0);
        int noCount = labelCounts.getOrDefault("No", 0);

        return (yesCount >= noCount) ? "Yes" : "No";
    }

    // Add new training example (updates in-memory data and frequency table)
    public void addTrainingExample(PlayerRecord record, String actualLabel) {
        String key = record.getPlayedLastMatch() + "," +
                     record.getTrainingAttendance() + "," +
                     record.getPreviousInjury() + "," +
                     record.getWeatherCondition();

        // Update frequency table
        frequencyTable.putIfAbsent(key, new HashMap<>());
        Map<String, Integer> labelCounts = frequencyTable.get(key);
        labelCounts.put(actualLabel, labelCounts.getOrDefault(actualLabel, 0) + 1);

        // ✅ Add new record to full in-memory dataset
        String[] newRow = new String[] {
            record.getPlayedLastMatch(),
            record.getTrainingAttendance(),
            record.getPreviousInjury(),
            record.getWeatherCondition(),
            actualLabel
        };
        fullData.add(newRow);
        
    }
    

    // Evaluate accuracy using in-memory dataset
    public double evaluateAccuracyFromMemory() {
        frequencyTable2.clear();

        if (fullData.size() < 2) return 0.0;

        // Split into training and testing sets
        int trainSize = Math.min(150, fullData.size());
        List<String[]> trainingData = fullData.subList(0, trainSize);
        List<String[]> testingData = fullData.subList(trainSize, fullData.size());

        // Build frequencyTable2 from training data
        for (String[] row : trainingData) {
            String key = row[0].trim() + "," + row[1].trim() + "," + row[2].trim() + "," + row[3].trim();
            String injured = row[4].trim();

            frequencyTable2.putIfAbsent(key, new HashMap<>());
            Map<String, Integer> labelCounts = frequencyTable2.get(key);
            labelCounts.put(injured, labelCounts.getOrDefault(injured, 0) + 1);
        }

        // Test
        int correct = 0;

        for (String[] row : testingData) {
            PlayerRecord testRecord = new PlayerRecord(
                row[0].trim(), row[1].trim(), row[2].trim(), row[3].trim()
            );
            String actualLabel = row[4].trim();
            String predictedLabel = predict2(testRecord);

            if (predictedLabel.equalsIgnoreCase(actualLabel)) {
                correct++;
            }
        }

        if (testingData.size() == 0) return 100.0;

        return (correct * 100.0) / testingData.size();
    }

    // Print main frequency table
    public void printFrequencyTable() {
        for (Map.Entry<String, Map<String, Integer>> entry : frequencyTable.entrySet()) {
            String featureCombination = entry.getKey();
            Map<String, Integer> labelCounts = entry.getValue();

            System.out.println("Feature Combination: " + featureCombination);
            System.out.println("Yes Count: " + labelCounts.getOrDefault("Yes", 0));
            System.out.println("No Count: " + labelCounts.getOrDefault("No", 0));
            System.out.println("-----------------------------");
        }
    }

    // Print secondary frequency table
    public void printFrequencyTable2() {
        for (Map.Entry<String, Map<String, Integer>> entry : frequencyTable2.entrySet()) {
            String featureCombination = entry.getKey();
            Map<String, Integer> labelCounts = entry.getValue();

            System.out.println("Feature Combination: " + featureCombination);
            System.out.println("Yes Count: " + labelCounts.getOrDefault("Yes", 0));
            System.out.println("No Count: " + labelCounts.getOrDefault("No", 0));
            System.out.println("-----------------------------");
        }
    }
}
