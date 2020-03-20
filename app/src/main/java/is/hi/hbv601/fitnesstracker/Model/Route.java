package is.hi.hbv601.fitnesstracker.Model;

import java.util.List;
import java.util.ArrayList;


public class Route {
    private long id;

    private String name;
    private List<Coordinate> polyline = new ArrayList<>();

    public Route() { }

    public Route(String name, List<Coordinate> polyline) {
        this.name = name;
        this.polyline = polyline;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coordinate> getPolyline() {
        return polyline;
    }

    public void setPolyline(List<Coordinate> polyline) {
        this.polyline = polyline;
    }
}