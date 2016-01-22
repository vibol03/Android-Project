package vibollee.nosnooze.com;


import android.app.AlarmManager;
import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import com.google.gson.Gson;

import java.util.Calendar;

import vibollee.nosnooze.com.nosnooze.R;


public class ScreenMain extends ActionBarActivity
{
    public final int NEW_ALARM = 1;
    public final int EDIT_ALARM = 2;
    public final int BROADCAST = 3;
    public final String LOGGING = "LOG";
    public String REPEAT_CODE = "";
    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;

    public String[] columns = {Database.getAlarmID(), Database.getAlarmName(), Database.getAlarmTime(), Database.getAlarmRepeats(), Database.getAlarmSound(), Database.getAlarmStatus()};
    private SimpleCursorAdapter ca;
    private ListView lstAlarm;
    private Button btnNew;
    private Database db;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);
        InitData();

        try
        {
            DisplayAlarmList();

        }
        catch (Exception ex)
        {
            Log.i(LOGGING, ex.toString());
//            ex.printStackTrace();
        }

        btnNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(v.getContext(), ScreenCreate.class);
                startActivityForResult(i, NEW_ALARM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
//        Log.i(LOGGING, "result");
        if (requestCode == NEW_ALARM)
        {
            if (resultCode == this.RESULT_OK)
            {
                Alarm alarm = (Alarm) data.getSerializableExtra("newAlarm");

                long insertFlag;
                if ((insertFlag = Database.insertAlarm(db, alarm)) != -1)
                {
                    DisplayAlarmList();
                } else
                {
                    Log.i(LOGGING, "Insert Unsuccessful");
                }
            }
        }

        if (requestCode == EDIT_ALARM)
        {
            if (resultCode == this.RESULT_OK)
            {
                Alarm alarm = (Alarm) data.getSerializableExtra("editAlarm");
                String updateQuery = "UPDATE " + Database.getTableName() + " SET " + Database.getAlarmName() + " = '" + alarm.getAlarmName() + "' ," + Database.getAlarmTime() + " = '" + alarm.getAlarmTime() + "' ," + Database.getAlarmSound() + " = '" + alarm.getAlarmSound() + "' ," + Database.getAlarmRepeats() + " = '" + alarm.getAlarmRepeats() + "' ";
                updateQuery += " WHERE " + Database.getAlarmID() + " = " + alarm.getAlarmID() + ";";

                Cursor cursor = Database.RawQuery(db, updateQuery);
                if (cursor.getCount() == 0)
                {
                    DisplayAlarmList();
                    SetAlarm("ONLY ONCE");
                }

            }
        }
    }

    protected void InitData()
    {
        db = new Database(this);
        lstAlarm = (ListView) findViewById(R.id.lstAlarm);
        btnNew = (Button) findViewById(R.id.btnNew);
        c = Database.selectAlarm(db, Database.getTableName(), columns, null, null, null, null, null);
    }

    public void DisplayAlarmList()
    {
        final String[] columns = {Database.getAlarmID(), Database.getAlarmTime(), Database.getAlarmName(), Database.getAlarmStatus(), Database.getAlarmRepeats()};
        c = Database.selectAlarm(db, Database.getTableName(), columns, null, null, null, null, null);

        int[] to = new int[]{
                R.id.alarmID,
                R.id.alarmTime,
                R.id.alarmName,
                R.id.alarmStatus,
                R.id.alarmRepeats,
        };

        ca = new SimpleCursorAdapter(this,
                R.layout.alarm_info,
                c,
                columns,
                to,
                0);

        lstAlarm.setAdapter(ca);

        lstAlarm.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id)
            {
                Alarm alarm = new Alarm();
                Cursor selectedCursor = (Cursor) listView.getItemAtPosition(position);

                String whereArgs = Integer.toString(selectedCursor.getInt(selectedCursor.getColumnIndexOrThrow(Database.getAlarmID())));
                Cursor data = Database.RawQuery(db, "SELECT * FROM " + Database.getTableName() + " WHERE " + Database.getAlarmID() + " = " + whereArgs);

                if (data.moveToFirst())
                {
                    alarm.setAlarmID(data.getString(data.getColumnIndexOrThrow(Database.getAlarmID())));
                    alarm.setAlarmName(data.getString(data.getColumnIndexOrThrow(Database.getAlarmName())));
                    alarm.setAlarmTime(data.getString(data.getColumnIndexOrThrow(Database.getAlarmTime())));
                    alarm.setAlarmSound(data.getString(data.getColumnIndexOrThrow(Database.getAlarmSound())));
                    alarm.setAlarmRepeats(data.getString(data.getColumnIndexOrThrow(Database.getAlarmRepeats())));
                    alarm.setAlarmStatus(data.getString(data.getColumnIndexOrThrow(Database.getAlarmStatus())));
                }

                Intent i = new Intent(view.getContext(), ScreenEdit.class);
                i.putExtra("editAlarm", new Gson().toJson(alarm));
                startActivityForResult(i, EDIT_ALARM);

            }
        });
    }

    public void btnOnOffOnClick(View v)
    {
        try
        {
            String query = "";
            final int position = lstAlarm.getPositionForView((View) v.getParent());
            Cursor selectedCursor = (Cursor) lstAlarm.getItemAtPosition(position);
            String status = selectedCursor.getString(selectedCursor.getColumnIndexOrThrow(Database.getAlarmStatus()));
            String whereArgs = Integer.toString(selectedCursor.getInt(selectedCursor.getColumnIndexOrThrow(Database.getAlarmID())));
            Cursor update;

            if (status.equals("ON"))
                query = "UPDATE " + Database.getTableName() + " SET " + Database.getAlarmStatus() + " = 'OFF'" + " WHERE " + Database.getAlarmID() + " = " + whereArgs;
            else
                query = "UPDATE " + Database.getTableName() + " SET " + Database.getAlarmStatus() + " = 'ON'" + " WHERE " + Database.getAlarmID() + " = " + whereArgs;


            update = Database.RawQuery(db, query);

            update.getCount();
            DisplayAlarmList();
        }catch (Exception ex){
            Log.i(LOGGING,"error: " + ex.toString());
        }
    }

    public void btnDeleteOnClick(View v)
    {
        final int position = lstAlarm.getPositionForView((View) v.getParent());
        Button s = (Button) v;
        Cursor selectedCursor = (Cursor) lstAlarm.getItemAtPosition(position);
        String whereArgs = Integer.toString(selectedCursor.getInt(selectedCursor.getColumnIndexOrThrow(Database.getAlarmID())));
        Cursor update = Database.RawQuery(db, "DELETE FROM " + Database.getTableName() + " WHERE " + Database.getAlarmID() + " = " + whereArgs);
        update.getCount();
        DisplayAlarmList();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //Log.i(LOGGING,"resume");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Log.i(LOGGING,"start");
    }


    protected void SetAlarm(String repeatCode)
    {
        if (repeatCode == "EVERYDAY")
        {
            alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, BROADCAST, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, 22);
            cal.set(Calendar.MINUTE, 47);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        if (repeatCode == "ONLY ONCE")
        {
            alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 21);

            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 5 * 1000, pendingIntent);

        }
    }

    public static void DeleteAlarm()
    {
        alarmManager.cancel(pendingIntent);
    }

    public long GetElaspedTime(Calendar cal)
    {
        long calTime = cal.getTimeInMillis();
        long sysTime = System.currentTimeMillis();

        if (calTime > sysTime)
            return calTime - sysTime;

        return 24 * 60 * 60 * 1000 - Math.abs(calTime - sysTime);
    }
}
