package is.hi.hbv601.fitnesstracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;


@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cardio.class, name = "cardio"),
        @JsonSubTypes.Type(value = Strength.class, name = "strength") }
)
public class Exercise {
    private long id;

    @JsonIgnoreProperties("user")
    private User user;

    private int duration;
    private Date date;
    private String type;

    public Exercise() { }

    public Exercise(User user, int duration, Date date, String type) {
        this.user = user;
        this.duration = duration;
        this.date = date;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}