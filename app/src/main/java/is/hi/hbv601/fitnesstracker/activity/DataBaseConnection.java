package is.hi.hbv601.fitnesstracker.activity;

import is.hi.hbv601.fitnesstracker.model.User;
import is.hi.hbv601.fitnesstracker.model.Cardio;
import is.hi.hbv601.fitnesstracker.model.Exercise;

import java.net.HttpURLConnection;
import java.net.URL;
import android.net.Uri;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.ZoneId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;


public class DataBaseConnection {

    private static final String URL_STRING = "https://hugbo2-2020.herokuapp.com";

    public static void Connect(String jsonString, URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        System.out.println(jsonString);

        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()){
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();

        System.out.println(responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
    }


    public static void ValidateUser(User user, String method) throws Exception {
        URL url = new URL(URL_STRING + method);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);

        DataBaseConnection.Connect(jsonString, url);
    }

    public static void AddExercise(Exercise exercise, String method) throws IOException {
        URL url = new URL(URL_STRING + method);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(exercise);

        DataBaseConnection.Connect(jsonString, url);
    }

    // Cardio(Exercise(int duration, Date date, String type), duration, topspeed, distance)
    public static Cardio genCardio(User user, Date date) {
        String[] t = {"Bikeriding", "running"};
        int duration = (int)(Math.random()* 75 + 25);
        String type = t[(int)Math.round(Math.random())];
        int topSpeed = (int)(Math.random() * 25 + 12);
        int distance = (int)(Math.random() * 25 + 5);
        return new Cardio(user, duration, date, type, topSpeed, distance, null);
    }

    public static void main(String args[]) {
        User tmp = new User("Tommi", "Jenni");

        String startDate = "2020-01-01";
        LocalDate l = LocalDate.parse(startDate);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date d = Date.from(l.plusDays(2).atStartOfDay(defaultZoneId).toInstant());
        System.out.println(d);
        try {
            DataBaseConnection.ValidateUser(tmp, "/login");
            Cardio c = genCardio(tmp, d);
            DataBaseConnection.AddExercise(c, "/exercise/addcardio");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}