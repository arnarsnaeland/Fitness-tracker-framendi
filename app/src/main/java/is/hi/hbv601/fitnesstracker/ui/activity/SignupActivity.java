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
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private NetworkClient networkClient;

    private EditText mUsername;
    private EditText mPassword;

    private TextView mSignupFailed;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final Button mSignupButton = findViewById(R.id.signup_button);
        final Button mLoginPageButton = findViewById(R.id.login_page_button);
        mProgressBar = findViewById(R.id.loading);
        mSignupFailed = findViewById(R.id.login_failed_textview);

        mUsername = (EditText)findViewById(R.id.username_text);
        mPassword = (EditText)findViewById(R.id.password_text);

        /**
         * Button
         * Navigates to login page
         */
        mLoginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity(v);
                finish();
            }

        });

        /**
         * Signup button
         * userName text from mUsername input
         * password text from mPassword input
         * On success: sets loggedInUser with user response from server.
         * On failure: displays error message.
         */
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (isNetworkAvailable()) {
                    toggleRefresh();
                    networkClient = new NetworkClient();
                    final String userName = mUsername.getText().toString();
                    final String password = mPassword.getText().toString();
                    // Create Json user
                    JSONObject jsonUser = new JSONObject();
                    try {
                        jsonUser.put("userName", userName);
                        jsonUser.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Post login information to server
                    //Call call = post("signup", jsonUser.toString());
                    Call call = networkClient.post("signup", jsonUser.toString());
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
                                    showSignupFailed();
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
                                    User loggedInUser = setUser(userName, password, map.get("user").toString());
                                    // TEMP TODO DELETE LOG
                                    Log.v(TAG, loggedInUser.getUserName());
                                    Log.v(TAG, loggedInUser.getPassword());
                                    Log.v(TAG, String.valueOf(loggedInUser.getId()));
                                    MainActivity(v);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showSignupFailed();
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

    public void LoginActivity(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    /**
     * Sets the user, adds id and userExercises list from response to user
     *
     * @param username from input
     * @param password from input
     * @param jsonData from response
     * @return User
     * @throws JSONException
     */
    private User setUser(String username, String password, String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        User user = new User(username, password);
        user.setId(json.getLong("id"));
        return user;
    }

    private void showSignupFailed() {
        mSignupFailed.setVisibility(View.VISIBLE);
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
