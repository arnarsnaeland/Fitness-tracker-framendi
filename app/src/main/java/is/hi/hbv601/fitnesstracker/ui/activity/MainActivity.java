package is.hi.hbv601.fitnesstracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import is.hi.hbv601.fitnesstracker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button mStatsButton = findViewById(R.id.statsBtn);
        final Button mAddExerciseButton = findViewById(R.id.addExcerciseBtn);
        final Button mStartTrackingButton = findViewById(R.id.trackBtn);
        final Button mViewExercisesButton = findViewById(R.id.excerciseListBtn);

        /**
         * Button
         * Navigates to stats page
         */
        mStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stats(v);
                //finish(); Sleppi finish hér svo back takkinn virki
            }

        });

        /**
         * Button
         * Navigates to Add Exercise page
         */
        mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExercise(v);
                //finish(); Sleppi finish hér svo back takkinn virki
            }

        });

        /**
         * Button
         * Navigates to record route page
         */
        mStartTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordRoute(v);
                //finish(); Sleppi finish hér svo back takkinn virki
            }

        });

        /**
         * Button
         * Navigates to View exercises page
         */
        mViewExercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewExercises(v);
                //finish(); Sleppi finish hér svo back takkinn virki
            }

        });

    }

    public void Stats(View v){
        Intent i = new Intent(this, StatsActivity.class);
        startActivity(i);
    }

    public void AddExercise(View v){
        Intent i = new Intent(this, AddExerciseActivity.class);
        startActivity(i);
    }

    public void RecordRoute(View v){
        Intent i = new Intent(this, RecordRouteActivity.class);
        startActivity(i);
    }

    public void ViewExercises(View v){
        Intent i = new Intent(this, ViewExercisesActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}