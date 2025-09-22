# ⚽ Players Injured Project (Java)

This project is a **Java implementation of the approach of Naive Bayes–style classifier** designed to predict whether a player will be **injured** or **not injured** based on their training and match history.  

NOTE : Naives Bayes classifier was not used in the project, just replicates how it works.

The classifier uses a **frequency-based approach**: it learns from training data stored in a CSV file, counts the frequency of outcomes ("Yes" for injured, "No" for not injured) for each feature combination, and makes predictions based on the majority outcome.  

It also supports **in-memory training**, **accuracy evaluation**, and **dynamic dataset updates**.

---

## 🚀 Features

- ✅ Train the classifier directly from a CSV file  
- ✅ Store the full dataset in memory for further learning and evaluation  
- ✅ Predict injury status (`Yes` or `No`) based on player attributes  
- ✅ Dynamically add new training examples without reloading the CSV  
- ✅ Evaluate classifier accuracy using a **train/test split**  
- ✅ Debug and inspect **frequency tables** used by the model  

---

## 📂 Project Structure

ML_Project/
│
├── NaiveBayesClassifier.java # Core classifier implementation
├── PlayerRecord.java # Player record model (simple POJO with getters)
├── MainGUI.java #GUI implementation
├── Control.java #Control class used for initialising classes
├── adjusted_player_injury_dataset.csv # Example dataset (CSV file, not included here)
└── README.md # Documentation
