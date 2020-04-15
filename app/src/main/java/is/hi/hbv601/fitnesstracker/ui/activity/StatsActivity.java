package is.hi.hbv601.fitnesstracker.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import is.hi.hbv601.fitnesstracker.R;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        final Spinner mTimePeriodSpinner = (Spinner)findViewById(R.id.time_period_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.timePeriodList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimePeriodSpinner.setAdapter(myAdapter);

        //ÞESSI KÓÐABÚTUR LÆTUR APPIÐ KRASSA AF EINHVERJUM ÁSTÆÐUM
        /*TimePeriodSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTimePeriod = TimePeriodSpinner.getSelectedItem().toString();
            }
        }); */


    }


}
