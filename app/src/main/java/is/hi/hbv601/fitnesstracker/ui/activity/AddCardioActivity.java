package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import is.hi.hbv601.fitnesstracker.R;
import is.hi.hbv601.fitnesstracker.model.Cardio;
import is.hi.hbv601.fitnesstracker.model.User;
import is.hi.hbv601.fitnesstracker.network.NetworkClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddCardioActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private NetworkClient networkClient;

    String cardioType;

    private EditText mCardioDuration;
    private EditText mCardioDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cardio);

        mCardioDuration = (EditText) findViewById(R.id.cardio_duration);
        mCardioDistance = (EditText) findViewById(R.id.cardio_distance);

        Spinner CardioSpinner = (Spinner)findViewById(R.id.cardio_spinner);

        CardioSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cardioList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CardioSpinner.setAdapter(myAdapter);

        final Button mSaveCardioButton = findViewById(R.id.save_cardio);
        
        mSaveCardioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                        if (isNetworkAvailable()) {
                            toggleRefresh();
                            networkClient = new NetworkClient();
                            final int duration = Integer.parseInt(mCardioDuration.getText().toString());
                            final Date date = new Date();
                            final String type = "eh";
                            final int topspeed = 1;
                            final int distance = 1;
                            // Create Json user
                            final User loggedInUser = new User("Tommi", "Jenni");
                            JSONObject jsonUser = new JSONObject();
                            try {
                                jsonUser.put("userName", loggedInUser.getUserName());
                                jsonUser.put("password", loggedInUser.getPassword());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject jsonCardio = new JSONObject();
                            // User user, int duration, Date date, String type, int weight, int times
                            final Cardio cardio = new Cardio(loggedInUser, duration, date, type, topspeed, distance, null);
                            try {
                                jsonCardio.put("user", jsonUser);
                                jsonCardio.put("duration", duration);
                                jsonCardio.put("date", date);
                                jsonCardio.put("type", type);
                                jsonCardio.put("topspeed", topspeed);
                                jsonCardio.put("distance", distance);
                                jsonCardio.put("route", null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Post addcardio information to server
                            Call call = networkClient.post("exercise/addcardio", jsonUser.toString());
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
                                            showAddCardioFailed();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            toggleRefresh();
                                        }
                                    });
                                    try {
                                        String jsonData = response.body().string();
                                        Log.v(TAG, jsonData);
                                        // Login succesful
                                        if (response.isSuccessful()) {
                                            final ObjectMapper mapper = new ObjectMapper();
                                            Map<String,Object> map;
                                            map = mapper.readValue(jsonData, new TypeReference<HashMap<String,Object>>(){});
                                            Log.v(TAG, map.toString());
                                            // Bætir æfingu við listann á núverandi notenda.
                                            // addStrength(jsonData);
                                            loggedInUser.addExercise(cardio);
                                            MainActivity(v);
                                            finish();
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    showAddCardioFailed();
                                                }
                                            });
                                        }
                                    } catch (IOException e) {
                                        Log.e(TAG, "Exception caught: ", e);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

    public void MainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cardioType = parent.getItemAtPosition(position).toString();
        //Sýna valið item (testing)
        //Toast.makeText(parent.getContext(), "Selected : " + cardioType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showAddCardioFailed() {
        //mLoginFailed.setVisibility(View.VISIBLE);
    }

    private void toggleRefresh() {
        //
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo!= null && networkInfo.isConnected()) isAvailable = true;
        return isAvailable;
    }
}
