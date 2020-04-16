package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import is.hi.hbv601.fitnesstracker.R;
import is.hi.hbv601.fitnesstracker.deserializer.UserDeserializer;
import is.hi.hbv601.fitnesstracker.model.Exercise;
import is.hi.hbv601.fitnesstracker.model.User;
import is.hi.hbv601.fitnesstracker.network.NetworkClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;

public class StatsActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private static final String URL = "https://hugbo2-2020.herokuapp.com/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private NetworkClient networkClient;
    private UserDeserializer userDeserializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (isNetworkAvailable()) {
            networkClient = new NetworkClient();
            final String userName = "Tommi";
            final String password = "Jenni";
            // Create Json user
            JSONObject jsonUser = new JSONObject();
            try {
                jsonUser.put("userName", userName);
                jsonUser.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Post login information to server
            Call call = networkClient.post("exercise/list", jsonUser.toString());
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoginFailed();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        // List af exercises skilað
                        if (response.isSuccessful()) {
                            User loggedInUser = new User(userName, password);
                            List<Exercise> l = userDeserializer.deserializeExerciseList(loggedInUser,jsonData);
                            Log.v(TAG, l.get(0).getType());
                            loggedInUser.setUserExercises(l);
                            // TEMP TODO DELETE LOG
                            Log.v(TAG, String.valueOf(loggedInUser.getId()));
                            finish();
                        }
                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoginFailed() {

    }

    private void toggleRefresh() {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo!= null && networkInfo.isConnected()) isAvailable = true;
        return isAvailable;
    }
}
