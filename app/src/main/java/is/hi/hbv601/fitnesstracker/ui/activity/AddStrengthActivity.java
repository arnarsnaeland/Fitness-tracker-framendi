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
import is.hi.hbv601.fitnesstracker.model.Strength;
import is.hi.hbv601.fitnesstracker.model.User;
import is.hi.hbv601.fitnesstracker.network.NetworkClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;

public class AddStrengthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public static final String TAG = LoginActivity.class.getSimpleName();
    private static final String URL = "https://hugbo2-2020.herokuapp.com/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private NetworkClient networkClient;

    String strengthType;

    private EditText mStrengthDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_strength);

        mStrengthDuration = (EditText) findViewById(R.id.strength_duration);

        Spinner StrengthSpinner = (Spinner)findViewById(R.id.strength_spinner);

        StrengthSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.strengthList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StrengthSpinner.setAdapter(myAdapter);

        final Button mSaveStrengthButton = findViewById(R.id.save_strength);

        mSaveStrengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (isNetworkAvailable()) {
                    toggleRefresh();
                    networkClient = new NetworkClient();
                    final int duration = Integer.parseInt(mStrengthDuration.getText().toString());
                    final Date date = new Date();
                    final String type = "eh";
                    final int weight = 1;
                    final int times = 1;
                    // Create Json user
                    final User loggedInUser = new User("Tommi", "Jenni");
                    JSONObject jsonUser = new JSONObject();
                    try {
                        jsonUser.put("userName", loggedInUser.getUserName());
                        jsonUser.put("password", loggedInUser.getPassword());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonStrength = new JSONObject();
                    // User user, int duration, Date date, String type, int weight, int times
                    final Strength strength = new Strength(loggedInUser, duration, date, type, weight, times);
                    try {
                        jsonStrength.put("user", jsonUser);
                        jsonStrength.put("duration", duration);
                        jsonStrength.put("date", date);
                        jsonStrength.put("type", type);
                        jsonStrength.put("weight", weight);
                        jsonStrength.put("times", times);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Post addcardio information to server
                    Call call = networkClient.post("exercise/addstrength", jsonUser.toString());
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
                                    showAddStrengthFailed();
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
                                    loggedInUser.addExercise(strength);
                                    MainActivity(v);
                                    finish();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showAddStrengthFailed();
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

    public void addStrength(String jSONString) {
        // TODO
        // Deserialize strength
        // add to exercise list
    }


    public void MainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strengthType = parent.getItemAtPosition(position).toString();
        //Sýna valið item (testing)
        //Toast.makeText(parent.getContext(), "Selected : " + strengthType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    
    private void showAddStrengthFailed() {
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
