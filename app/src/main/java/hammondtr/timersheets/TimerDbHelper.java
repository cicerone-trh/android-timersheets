package hammondtr.timersheets;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

/*  TIMERS:
 *  id, name, type, full duration, completed duration, recap, date created
 * */

public class TimerDbHelper extends SQLiteOpenHelper {

    public TimerDbHelper (Context context){
        super(context, TimerDbContract.DATABASE_NAME, null, TimerDbContract.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        // create TimersTable and TimersheetsTable; guess this should probably be
        // merged in the Contract to one command...
            // EDIT: execSQL is one command
        db.execSQL(TimerDbContract.TimersTable.CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TimerDbContract.TimersTable.DELETE_TABLE);
        onCreate(db);
    }

    // returns id
    public long addNewTimer(Bundle timerData){

        // extract timerData, create ContentValues, insert
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues t = new ContentValues();

        if (timerData.getString("name") != null){
            t.put(TimerDbContract.TimersTable.COLUMN_NAME_NAME, timerData.getString("name"));
        }
        if (timerData.getString("type") != null){
            t.put(TimerDbContract.TimersTable.COLUMN_NAME_CATEGORY, timerData.getString("type"));
        }

        // duration is either 0 or something, so no need to check; if it's 0 it's a stopwatch
        t.put(TimerDbContract.TimersTable.COLUMN_NAME_DURATION, timerData.getInt("initDuration"));
        t.put(TimerDbContract.TimersTable.COLUMN_NAME_COMPLETED_DURATION, timerData.getInt("duration"));

        // insert and return id
        return db.insert(TimerDbContract.TimersTable.TABLE_NAME, null, t);

    }

    public void updateTimerTime(long id, int timeElapsed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TimerDbContract.TimersTable.COLUMN_NAME_COMPLETED_DURATION, timeElapsed);

        String selection = TimerDbContract.TimersTable._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        db.update(
            TimerDbContract.TimersTable.TABLE_NAME,
            values,
            selection,
            selectionArgs
        );
    }

}
