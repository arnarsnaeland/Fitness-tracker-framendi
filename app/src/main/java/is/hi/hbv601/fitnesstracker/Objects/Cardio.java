package is.hi.hbv601.fitnesstracker.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import javax.persistence.Entity;

public class Cardio extends Exercise {

    // km/h
    private int topSpeed;
    private int distance;
    private Route route;

    public Cardio() { }

    public Cardio(User user, int duration, Date date, String type, int topSpeed, int distance, Route route) {
        super(user, duration, date, type);
        this.topSpeed = topSpeed;
        this.distance = distance;
        this.route = route;
    }

    public Cardio(Cardio cardio) {
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}