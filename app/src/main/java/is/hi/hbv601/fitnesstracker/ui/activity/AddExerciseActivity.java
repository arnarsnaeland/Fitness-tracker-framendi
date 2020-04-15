package is.hi.hbv601.fitnesstracker.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import is.hi.hbv601.fitnesstracker.R;

public class AddExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        final Button mAddCardioButton = findViewById(R.id.add_cardio_button);
        final Button mAddStrengthButton = findViewById(R.id.add_strength_button);

        /**
         * Button
         * Navigates to Add Cardio page
         */
        mAddCardioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCardioActivity(v);
                //finish(); Sleppi finish hér svo back takkinn virki
            }

        });

        /**
         * Button
         * Navigates to Add Strength page
         */
        mAddStrengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStrengthActivity(v);
                //finish(); Sleppi finish hér svo back takkinn virki
            }

        });

    }

    public void AddCardioActivity(View v) {
        Intent i = new Intent(this, AddCardioActivity.class);
        startActivity(i);
    }

    public void AddStrengthActivity(View v) {
        Intent i = new Intent(this, AddStrengthActivity.class);
        startActivity(i);
    }
}
