package is.hi.hbv601.fitnesstracker.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import is.hi.hbv601.fitnesstracker.model.Cardio;
import is.hi.hbv601.fitnesstracker.model.Strength;
import is.hi.hbv601.fitnesstracker.model.User;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkClient {

    private static final String URL = "https://hugbo2-2020.herokuapp.com/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String jsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static Cardio genCardio(User user, Date date) {
        String[] t = {"Bikeriding", "running"};
        int duration = (int)(Math.random()* 75 + 25);
        String type = t[(int)Math.round(Math.random())];
        int topSpeed = (int)(Math.random() * 25 + 12);
        int distance = (int)(Math.random() * 25 + 5);
        return new Cardio(user, duration, date, type, topSpeed, distance, null);
    }

    // Strength(Exercise(int duration, Date date, String type), int weight, int times)
    public static Strength genStrength(User user, Date date) {
        String[] t = {"veitekki", "ehe", "hehe"};
        int duration = (int)(Math.random()* 75) + 25;
        String type = t[(int)(Math.random() * 3)];
        int weight = (int)(Math.random() * 100) + 5;
        int times = (int)(Math.random() * 40 )+ 5;
        return new Strength(user, duration, date, type, weight, times);
    }

    public static void main(String[] args) throws IOException {
        NetworkClient example = new NetworkClient();
        User user = new User("Tommi", "Jenni");

        String startDate = "2020-01-01";
        LocalDate l = LocalDate.parse(startDate);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date d = Date.from(l.plusDays(2).atStartOfDay(defaultZoneId).toInstant());

        String response = example.post(URL + "login", jsonString(user));
        System.out.println(response);

        response = example.post(URL + "exercise/list", jsonString(user));
        System.out.println(response);

        String json = jsonString(genStrength(user, d));
        ObjectMapper objectMapper = new ObjectMapper();
        Strength s = objectMapper.readValue(json, Strength.class);
        System.out.println(s.toString());
        response = example.post(URL + "exercise/addstrength", json);
        System.out.println(response);
    }
}