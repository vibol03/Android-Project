package vibollee.nosnooze.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Vibol on 4/9/2015.
 */
public class Database extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "NoSnooze.db";
    private static final String TABLE_NAME = "Alarms";
    private static final String AlarmID = "_id";
    private static final String AlarmName = "Name";
    private static final String AlarmTime = "Time";
    private static final String AlarmSound = "Sound";
    private static final String AlarmRepeats = "Repeats";
    private static final String AlarmStatus = "Status";

    public Database(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + AlarmID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AlarmName + " VARCHAR(50), " + AlarmTime + " VARCHAR(50), " + AlarmSound + " VARCHAR(50), " + AlarmRepeats + " VARCHAR(255), " + AlarmStatus + " VARCHAR(5));";
        //String query = "DROP TABLE IF EXISTS " + TABLE_NAME;

        try
        {
            db.execSQL(query);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;

        try
        {
            db.execSQL(query);
            onCreate(db);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public  static Cursor selectAlarm(Database db, String table, String[]columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        SQLiteDatabase sqlite = db.getReadableDatabase();

        return sqlite.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public static long insertAlarm(Database db, Alarm alarm){

        SQLiteDatabase sqlite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(AlarmName, alarm.getAlarmName());
        cv.put(AlarmTime, alarm.getAlarmTime());
        cv.put(AlarmSound, alarm.getAlarmSound());
        cv.put(AlarmRepeats, alarm.getAlarmRepeats());
        cv.put(AlarmStatus, alarm.getAlarmStatus());

        return sqlite.insert(TABLE_NAME, null, cv);
    }

    public static Cursor RawQuery(Database db, String sql){
        SQLiteDatabase sqlite = db.getWritableDatabase();

        return sqlite.rawQuery(sql, null);
    }



    public static String getTableName()
    {
        return TABLE_NAME;
    }

    public static String getAlarmID()
    {
        return AlarmID;
    }

    public static String getAlarmName()
    {
        return AlarmName;
    }

    public static String getAlarmTime()
    {
        return AlarmTime;
    }

    public static String getAlarmSound()
    {
        return AlarmSound;
    }

    public static String getAlarmRepeats()
    {
        return AlarmRepeats;
    }

    public static String getAlarmStatus()
    {
        return AlarmStatus;
    }
//    public long insert(Alarm a)
//    {
//
//    }

}
