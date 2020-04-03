package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import is.hi.hbv601.fitnesstracker.R;
import is.hi.hbv601.fitnesstracker.model.User;
import is.hi.hbv601.fitnesstracker.network.NetworkClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private static final String URL = "https://hugbo2-2020.herokuapp.com/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private NetworkClient networkClient;

    private EditText mUsername;
    private EditText mPassword;

    ProgressBar mProgressBar;
    private TextView mLoginFailed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button mLoginButton = findViewById(R.id.login_button);
        final Button mSignupPageButton = findViewById(R.id.signup_page_button);
        mProgressBar = findViewById(R.id.loading);
        mLoginFailed = findViewById(R.id.login_failed_textview);

        mUsername = (EditText)findViewById(R.id.username_text);
        mPassword = (EditText)findViewById(R.id.password_text);

        mSignupPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                //finish();
            }

        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    toggleRefresh();
                    networkClient = new NetworkClient();
                    String userName = mUsername.getText().toString();
                    String password = mPassword.getText().toString();
                    JSONObject jsonUser = new JSONObject();
                    try {
                        jsonUser.put("userName", userName);
                        jsonUser.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Call call = post("login", jsonUser.toString());
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
                                    Map<String,Object> map = new HashMap<String,Object>();
                                    map = mapper.readValue(jsonData, new TypeReference<HashMap<String,Object>>(){});
                                    Log.v(TAG, map.toString());
                                    User loggedInUser = setUser(mUsername.toString(), mPassword.toString(), map.get("user").toString());
                                    Log.v(TAG, loggedInUser.getUserName());
                                    Log.v(TAG, loggedInUser.getPassword());
                                    Log.v(TAG, ("" + loggedInUser.getId()));
                                    Log.v(TAG, String.valueOf(loggedInUser.getUserExercises()));
                                    // MainActivity(v);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showLoginFailed();
                                        }
                                    });
                                }
                            } catch (IOException | JSONException e) {
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

    public void MainActivity(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    /**
     * Posts method Object to backend
     * @param url appended to URL String
     * @param json object that is posted to url
     * @return Call
     * @throws IOException
     */
    public Call post(String url, String json)  {
        OkHttpClient client = new OkHttpClient();
        url = URL + url;
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request);
    }

    private User setUser(String username, String password, String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        User user = new User(username, password);
        Log.v(TAG, json.toString());
        user.setId(json.getLong("id"));
        // TODO deserialize ExerciseList
        return user;
    }

    private void showLoginFailed() {
        mLoginFailed.setVisibility(View.VISIBLE);
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility()== View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo!= null && networkInfo.isConnected()) isAvailable = true;
        return isAvailable;
    }
}