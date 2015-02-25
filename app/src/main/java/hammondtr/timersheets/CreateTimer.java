package hammondtr.timersheets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class CreateTimer extends ActionBarActivity {

    private TimerDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);
        dbHelper = new TimerDbHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_timer, menu);
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

    // when user confirms Timer Create

    public void confirmCreate(View view){

        // get fields

        EditText timerNameField, timerCategoryField, hoursField, minutesField, secondsField;
        timerNameField = (EditText) findViewById(R.id.timer_name);
        timerCategoryField = (EditText) findViewById(R.id.timer_type);
        hoursField = (EditText) findViewById(R.id.timer_hours);
        minutesField = (EditText) findViewById(R.id.timer_minutes);
        secondsField = (EditText) findViewById(R.id.timer_seconds);

        // convert to dataz

        String timerName, timerCat, sHours, sMinutes, sSeconds;
        timerName = timerNameField.getText().toString();
        timerCat = timerCategoryField.getText().toString();

        // can't parseInt("") ~ woo woo learning Java - replacing "" with "0"
        sHours = (hoursField.getText().toString().equals("")) ?
                "0" : hoursField.getText().toString();
        sMinutes = (minutesField.getText().toString().equals("")) ?
                "0" : minutesField.getText().toString();
        sSeconds = (secondsField.getText().toString().equals("")) ?
                "0" : secondsField.getText().toString();

        int hours = Integer.parseInt(sHours);
        int minutes = Integer.parseInt(sMinutes);
        int seconds = Integer.parseInt(sSeconds);
        int timerSeconds = hours*60*60 + minutes*60 + seconds;

        // bundle data and add/create new timer
        Bundle timerData = new Bundle();
        timerData.putString("name", timerName);
        timerData.putString("type", timerCat);
        timerData.putInt("initDuration", timerSeconds);

        dbHelper.addNewTimer(timerData);

        finish();

        // db insert
        // verify data is good & db connect is good
        // SQLiteDatabase db = dbHelper.getWritableDatabase();
        /*
        ContentValues timerDataCustom = new ContentValues();
        timerDataCustom.put(TimerDbContract.TimersTable.COLUMN_NAME_NAME, timerName);

        db.insert(TimerDbContract.TimersTable.TABLE_NAME, null, timerDataCustom);

        // build intent
        Bundle timerData = new Bundle();
        timerData.putString("name", timerName);

        Intent intent = new Intent();
        intent.putExtras(timerData);
        setResult(RESULT_OK, intent);
        */

    }

    private int convertTimeInputToSeconds(String timeString){
        int s = 0;
        return s;
    }

}
