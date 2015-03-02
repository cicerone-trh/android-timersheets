package hammondtr.timersheets;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class TimerSheet extends ActionBarActivity {

    private static final int DEFINE_NEW_TIMER = 1;
    private TimerDbHelper dbHelper;
    private SQLiteDatabase db;
    private boolean isNewTimer;
    private Bundle newTimerInfo;
    private List<Timer> timers;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // initialization
        setContentView(R.layout.activity_timer_sheet);
        dbHelper = new TimerDbHelper(this);
        db = dbHelper.getReadableDatabase();
        isNewTimer = false;
        fragmentManager = getSupportFragmentManager();
        timers = new ArrayList<Timer>();

        // add Timer fragments from current Timesheet (saved in userprefs)
        /***** temporary: no timesheet table *****/

        loadTimesheet(0);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isNewTimer) {
            // add Timer to Timer Fragments
            Timer timer = new Timer();

            timer.setArguments(newTimerInfo);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_timer_sheet, timer).commit();
            timers.add(timer);
            isNewTimer = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // save Timers to SharedPrefs ?
        // save Timers to DB
        for (Timer t : timers) {
            t.stopTimer();
        }
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
        // set new timer variable for onResume
        if (resultCode == Activity.RESULT_OK && requestCode == DEFINE_NEW_TIMER){
            // create Timer from timerData
            newTimerInfo = data.getExtras();
            isNewTimer = true;
        }
    }

    public void addTimer(View view){
        // launch CreateTimer activity
        Intent intent = new Intent(this, CreateTimer.class);
        startActivityForResult(intent, DEFINE_NEW_TIMER);
    }

    private void loadTimesheet(long timesheetId) {
        // remove current timers
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // load timers related to timesheet
        String[] projection = {
                TimerDbContract.TimersTable._ID,
                TimerDbContract.TimersTable.COLUMN_NAME_NAME,
                TimerDbContract.TimersTable.COLUMN_NAME_CATEGORY,
                TimerDbContract.TimersTable.COLUMN_NAME_COMPLETED_DURATION
        };

        Cursor cursor = db.query(
                TimerDbContract.TimersTable.TABLE_NAME,
                projection,
                null, null, null, null, null
        );

        // populate TimerSheet with Timer fragments

        long tId;
        Timer newTimer;
        Bundle timerInfo;
        String name, category; int cDur;
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            newTimer = new Timer();
            timerInfo = new Bundle();

            // extract values
            tId = cursor.getLong(0);
            name = cursor.getString(1);
            category = cursor.getString(2);
            cDur = cursor.getInt(3);

            // setup timer argzzzzz
            timerInfo.putString("name", name);
            timerInfo.putString("type", category);
            timerInfo.putInt("duration", cDur);
            timerInfo.putLong("id", tId);

            newTimer.setArguments(timerInfo);
            fragmentManager.beginTransaction()
                    .add(R.id.activity_timer_sheet, newTimer).commit();
            timers.add(newTimer);

            cursor.moveToNext();
        }
    }

}
