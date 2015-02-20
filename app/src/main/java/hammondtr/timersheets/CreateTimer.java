package hammondtr.timersheets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

        // verify data is good & db connect is good
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // get data from fields
        EditText timerNameField = (EditText) findViewById(R.id.timer_name);
        String timerName = timerNameField.getText().toString();

        // db insert
        ContentValues timerData = new ContentValues();
        timerData.put(TimerDbContract.TimersTable.COLUMN_NAME_NAME, timerName);

        db.insert(TimerDbContract.TimersTable.COLUMN_NAME_NAME, null, timerData);

        // build intent
        /*
        Bundle timerData = new Bundle();
        timerData.putString("name", timerName);

        Intent intent = new Intent();
        intent.putExtras(timerData);
        setResult(RESULT_OK, intent);
        */
        finish();
    }

}
