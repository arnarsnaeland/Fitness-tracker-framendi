package is.hi.hbv601.fitnesstracker.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

public class Coordinate {
    private long id;

    private Route route;

    private Double latitude;
    private Double longitude;

    public Coordinate() { }

    public Coordinate(Route route, Double latitude, Double longitude) {
        this.route = route;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}