package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import is.hi.hbv601.fitnesstracker.R;
import is.hi.hbv601.fitnesstracker.network.NetworkClient;

public class LoginActivity extends AppCompatActivity {

    private NetworkClient networkClient;

    private EditText mUsername;
    private EditText mPassword;

    ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button mLoginButton = findViewById(R.id.login_button);
        final Button mSignupPageButton = findViewById(R.id.signup_page_button);
        mProgressBar = findViewById(R.id.loading);

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
                    System.out.println(jsonUser.toString());
                    String response = networkClient.post("login", jsonUser.toString());
                    System.out.println(response);
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
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
