package vibollee.nosnooze.com;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import vibollee.nosnooze.com.nosnooze.R;

public class ScreenEdit extends ActionBarActivity
{

    EditText editName;
    EditText editTime;
    EditText editSound;
    EditText editRepeats;

    Button btnSave;
    Button btnCancel;

    Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_edit);

        InitUI();

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                setData();
                i.putExtra("editAlarm", alarm);
                setResult(ScreenMain.RESULT_OK, i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }


    protected void InitUI()
    {
        Bundle alarmData = getIntent().getExtras();
        alarm = new Gson().fromJson(alarmData.getString("editAlarm"), Alarm.class);

        editName = (EditText) findViewById(R.id.editName);
        editTime = (EditText) findViewById(R.id.editTime);
        editSound = (EditText) findViewById(R.id.editSound);
        editRepeats = (EditText) findViewById(R.id.editRepeats);

        editName.setText(alarm.getAlarmName());
        editTime.setText(alarm.getAlarmTime());
        editSound.setText(alarm.getAlarmSound());
        editRepeats.setText(alarm.getAlarmRepeats());

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
    }

    public void setData()
    {
        alarm.setAlarmName(editName.getText().toString());
        alarm.setAlarmTime(editTime.getText().toString());
        alarm.setAlarmSound(editSound.getText().toString());
        alarm.setAlarmRepeats(editRepeats.getText().toString());
        alarm.setAlarmStatus("on");
    }
}
