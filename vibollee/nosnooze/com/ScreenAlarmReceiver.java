package vibollee.nosnooze.com;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.view.View;
import android.widget.Button;


import vibollee.nosnooze.com.nosnooze.R;

public class ScreenAlarmReceiver extends ActionBarActivity
{
    Button btnStop;
    Button btnCancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_alarm_receiver);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                r.stop();
                finish();
            }
        });

        btnCancelAlarm = (Button) findViewById(R.id.btnCancelAlarm);
        btnCancelAlarm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                r.stop();
               ScreenMain.DeleteAlarm();
                finish();
            }
        });


    }


}
