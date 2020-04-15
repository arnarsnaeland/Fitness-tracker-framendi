package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.hbv601.fitnesstracker.R;

public class AddStrengthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String strengthType;

    private EditText mStrengthDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_strength);

        mStrengthDuration = (EditText) findViewById(R.id.strength_duration);

        final Spinner StrengthSpinner = (Spinner)findViewById(R.id.strength_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.strengthList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StrengthSpinner.setAdapter(myAdapter);

        final Button mSaveStrengthButton = findViewById(R.id.save_strength);

        /**
         * Button
         * Navigates to main activity page
         */
        mSaveStrengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int strengthDuration = Integer.parseInt(mStrengthDuration.getText().toString());

                /*// TODO: Tengja við gagnagrunn þannig að æfingar saveist
                Toast.makeText(getBaseContext(), "Saving Exercise...", Toast.LENGTH_SHORT).show();

                MainActivity(v);
                finish(); */
            }

        });
    }
    public void MainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strengthType = parent.getItemAtPosition(position).toString();
        //Sýna valið item (testing)
        Toast.makeText(parent.getContext(), "Selected : " + strengthType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
