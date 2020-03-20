package is.hi.hbv601.fitnesstracker.Model;

import java.util.Date;

public class Strength extends Exercise {
    
    private int weight;
    private int times;

    public Strength() { }

    public Strength(User user, int duration, Date date, String type, int weight, int times) {
        super(user, duration, date, type);
        this.weight = weight;
        this.times = times;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    } 

}