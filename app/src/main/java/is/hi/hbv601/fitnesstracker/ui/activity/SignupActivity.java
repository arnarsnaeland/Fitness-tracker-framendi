package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.hbv601.fitnesstracker.R;
import is.hi.hbv601.fitnesstracker.network.NetworkClient;
import okhttp3.MediaType;

public class SignupActivity extends AppCompatActivity {

    private static final String URL = "https://hugbo2-2020.herokuapp.com/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private NetworkClient networkClient;

    private EditText mSignupUsername;
    private EditText mSignupPassword;

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

        mSignupUsername = (EditText)findViewById(R.id.username_text);
        mSignupPassword = (EditText)findViewById(R.id.password_text);

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

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Vista username og pw í gagnagrunn og ath hvort username sé tekið osfrv
                LoginActivity(v);
                finish();
            }

        });
    }

    public void LoginActivity(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
