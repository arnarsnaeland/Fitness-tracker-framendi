package is.hi.hbv601.fitnesstracker.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601.fitnesstracker.R;

public class AddCardioActivity extends AppCompatActivity {

    //private EditText CardioLengthInput;
    //in minutes
    int cardioDuration;
    private EditText mCardioDuration;

    //in km
    double distance;
    private EditText mDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cardio);

        final Spinner CardioSpinner = (Spinner)findViewById(R.id.cardio_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cardioList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CardioSpinner.setAdapter(myAdapter);

        /*CardioSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCardio = CardioSpinner.getSelectedItem().toString();
            }
        }); */

        //cardioLength = Integer.valueOf(CardioLengthInput.getText().toString());


    }
}
