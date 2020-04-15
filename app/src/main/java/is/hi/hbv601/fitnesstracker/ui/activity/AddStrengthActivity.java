package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.hbv601.fitnesstracker.R;

public class AddStrengthActivity extends AppCompatActivity {

    int strengthDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_strength);

        final Spinner StrengthSpinner = (Spinner)findViewById(R.id.strength_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.strengthList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StrengthSpinner.setAdapter(myAdapter);

        //ÞESSI KÓÐABÚTUR LÆTUR APPIÐ KRASSA AF EINHVERJUM ÁSTÆÐUM
        /*StrengthSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStrength = CardioSpinner.getSelectedItem().toString();
            }
        }); */

        //strengthDuration = Integer.valueOf(CardioLengthInput.getText().toString());

        final Button mSaveStrengthButton = findViewById(R.id.save_strength);

        /**
         * Button
         * Navigates to main activity page
         */
        mSaveStrengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Tengja við gagnagrunn þannig að æfingar saveist
                Toast.makeText(getBaseContext(), "Saving Exercise...", Toast.LENGTH_SHORT).show();

                MainActivity(v);
                finish();
            }

        });
    }
    public void MainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
