package is.hi.hbv601.fitnesstracker.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;

    public String userName;
    private String password;
    private List<Exercise> userExercises = new ArrayList<>();

    public User() { }

    /**
     * Initializes user with empty userExercises
     * Used in development
     * @param userName
     * @param password
     */
    public User(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    @JsonCreator
    public User(@JsonProperty("id")long id,
                @JsonProperty("userName")String userName,
                @JsonProperty("password")String password,
                @JsonProperty("userExercises") List<Exercise> userExercises) {
        super();
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userExercises = userExercises;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Exercise> getUserExercises() {
        return userExercises;
    }

    public void setUserExercises(List<Exercise> exercises) {
        this.userExercises = exercises;
    }

    public void addExercise(Exercise exercise) {
        userExercises.add(exercise);
    }
}