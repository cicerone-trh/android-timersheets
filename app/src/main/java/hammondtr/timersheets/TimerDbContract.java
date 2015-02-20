package hammondtr.timersheets;

import android.provider.BaseColumns;

public final class TimerDbContract {
    public TimerDbContract(){}
    /* Inner class that defines the table contents
    *    -- if my understanding is correct, BaseColumns creates unique id
    *       & I will use a Cursor to iterate through ids
    *
    *  TIMERS:
    *  id, name, type, full duration, completed duration, recap, date created
    * */

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "timersheets.db";
    public static final String CREATE_DATABASE = TimersTable.CREATE_TABLE + ";";

    private static final String TEXT_TYPE = " TEXT";
    private static final String TIME_TYPE = " TEXT";
    private static final String DATE_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static abstract class TimersTable implements BaseColumns {
        public static final String TABLE_NAME = "timerEntries";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_DURATION = "initDuration";
        public static final String COLUMN_NAME_COMPLETED_DURATION = "completedDuration";
        public static final String COLUMN_NAME_RECAP = "recap";
        public static final String COLUMN_NAME_CREATED_ON = "dateCreated";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DURATION + TIME_TYPE + COMMA_SEP +
                COLUMN_NAME_COMPLETED_DURATION + TIME_TYPE + COMMA_SEP +
                COLUMN_NAME_RECAP + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_CREATED_ON + DATE_TYPE + COMMA_SEP +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
