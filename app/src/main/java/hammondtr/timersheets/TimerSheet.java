package hammondtr.timersheets;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class TimerSheet extends ActionBarActivity {

    private static final int DEFINE_NEW_TIMER = 1;
    private TimerDbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_sheet);
        dbHelper = new TimerDbHelper(this);
        db = dbHelper.getReadableDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();

        // check for new timers and update list

        /*
        Bundle timerData = data.getExtras();
        Timer timer = new Timer();
        timer.setArguments(timerData);

        // add Timer to TimerSheet
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_timer_sheet, timer).commit();
        */

        // Check for new Timers
            // Add new Timers to list
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer_sheet, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // result of creating timer
        if (resultCode == Activity.RESULT_OK && requestCode == DEFINE_NEW_TIMER){


            // create Timer from timerData
            Bundle timerData = data.getExtras();
            Timer timer = new Timer();
            timer.setArguments(timerData);

            // add Timer to TimerSheet
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_timer_sheet, timer).commit();



        }
    }

    public void addTimer(View view){
        // launch CreateTimer activity to determine properties
        Intent intent = new Intent(this, CreateTimer.class);
        startActivity(intent);

        // startActivityForResult(intent, DEFINE_NEW_TIMER);

    }

}
