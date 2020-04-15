package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601.fitnesstracker.R;

public class AddCardioActivity extends AppCompatActivity {

    //private EditText CardioLengthInput;
    //in minutes
    int cardioDuration;

    //in km
    double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cardio);

        final Spinner CardioSpinner = (Spinner)findViewById(R.id.cardio_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cardioList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CardioSpinner.setAdapter(myAdapter);

        //ÞESSI KÓÐABÚTUR LÆTUR APPIÐ KRASSA AF EINHVERJUM ÁSTÆÐUM
        /*CardioSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCardio = CardioSpinner.getSelectedItem().toString();
            }
        }); */

        //cardioLength = Integer.valueOf(CardioLengthInput.getText().toString());

        final Button mSaveCardioButton = findViewById(R.id.save_cardio);

        /**
         * Button
         * Navigates to main activity page
         */
        mSaveCardioButton.setOnClickListener(new View.OnClickListener() {
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
