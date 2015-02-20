package hammondtr.timersheets;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimerDbHelper extends SQLiteOpenHelper {

    private boolean isNewTimers;

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

    public boolean isNewTimers() {

        return false;
    }

    /*
    public void addTimer(ContentValues timerData){

    }
    */
}
