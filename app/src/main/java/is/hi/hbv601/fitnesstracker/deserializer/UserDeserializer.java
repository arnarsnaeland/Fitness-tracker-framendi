package is.hi.hbv601.fitnesstracker.deserializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import is.hi.hbv601.fitnesstracker.model.Cardio;
import is.hi.hbv601.fitnesstracker.model.Exercise;
import is.hi.hbv601.fitnesstracker.model.Route;
import is.hi.hbv601.fitnesstracker.model.Strength;
import is.hi.hbv601.fitnesstracker.model.User;

public class UserDeserializer {

    public static List<Exercise> deserializeExerciseList(User user, String jsonData) throws JSONException, ParseException {
        JSONObject jsnobject = new JSONObject(jsonData);
        JSONArray data = new JSONArray(jsnobject.getJSONArray("exercises"));

        List<Exercise> exerciseList = new ArrayList<>();
        for(int i = 0; i < data.length(); i++) {
            JSONObject jsonExercise = data.getJSONObject(i);
            if (jsonExercise.has("topSpeed")) {
                exerciseList.add(deserializeCardio(user, jsonExercise));
            }
            else if (jsonExercise.has("weigth")) {
                exerciseList.add(deserializeStrength(user, jsonExercise));
            }
        }
        return exerciseList;
    }

    /**
     * 
     * @param
     * {
     *  "id": 0,
     *  "duration": 30,
     *  "date": date,
     *  "type": type,
     *  "topSpeed": distance,
     *  "distance": distance,
     *  "route": route
     * }
     * @param jsonData
     * @return
     * @throws JSONException
     */
    public static Cardio deserializeCardio(User user, JSONObject jsonData) throws JSONException, ParseException {

        Long id = jsonData.getLong("id");
        int duration = jsonData.getInt("id");
        Date date = deserializeDate(jsonData.getString("date"));
        String type = jsonData.getString("type");
        int topSpeed = jsonData.getInt("topSpeed");
        int distance = jsonData.getInt("distance");
        Route route = null;
        // (User user, int duration, Date date, String type, int topSpeed, int distance, Route route)
        return new Cardio(user, duration, date, type, topSpeed, distance, route);
    }

    /**
     * {
     * "id": id,
     * "duration": duration,
     * "date": date,
     * "type": type,
     * "weight": weight,
     * "times": times
     * }
     * @param user
     * @param jsonData
     * @return
     * @throws JSONException
     */
    public static Strength deserializeStrength(User user, JSONObject jsonData) throws JSONException, ParseException {

        Long id = jsonData.getLong("id");
        int duration = jsonData.getInt("id");
        Date date = deserializeDate(jsonData.getString("date"));
        String type = jsonData.getString("type");
        int weight = jsonData.getInt("weight");
        int times = jsonData.getInt("times");
        // User user, int duration, Date date, String type, int weight, int times
        return new Strength(user, duration, date, type, weight, times);
    }

    public static Date deserializeDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return sdf.parse(date);
    }
}