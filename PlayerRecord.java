package ML_Project;


import java.util.Objects;


public class PlayerRecord {

	//Variables for the features
    private String playedLastMatch;

    private String trainingAttendance;

    private String previousInjury;

    private String weatherCondition;


    //Constructor to initialise the fields

    public PlayerRecord(String playedLastMatch, String trainingAttendance, String previousInjury, String weatherCondition) {

       setPlayedLastMatch(playedLastMatch);
       setTrainingAttendance(trainingAttendance);
       setPreviousInjury(previousInjury);
       setWeatherCondition(weatherCondition);

        this.trainingAttendance = trainingAttendance;

        this.previousInjury = previousInjury;

        this.weatherCondition = weatherCondition;

    }


    //Getter methods for each feature

    public String getPlayedLastMatch() {

        return playedLastMatch;

    }


    public String getTrainingAttendance() {

        return trainingAttendance;

    }


    public String getPreviousInjury() {

        return previousInjury;

    }


    public String getWeatherCondition() {

        return weatherCondition;

    }
    
 // Setter methods for each feature

    public void setPlayedLastMatch(String playedLastMatch) {
        this.playedLastMatch = playedLastMatch;
    }

    public void setTrainingAttendance(String trainingAttendance) {
        this.trainingAttendance = trainingAttendance;
    }

    public void setPreviousInjury(String previousInjury) {
        this.previousInjury = previousInjury;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

   


    

   

}