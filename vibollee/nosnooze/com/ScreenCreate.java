package vibollee.nosnooze.com;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.LoggingMXBean;

import vibollee.nosnooze.com.nosnooze.R;

public class ScreenCreate extends ActionBarActivity {
    private final String LOGGING = "LOG";
    private final int RESULT_OK = 1;

    public Alarm alarm = new Alarm();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_create);


        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i = new Intent();

                    alarm = getData();
                    i.putExtra("newAlarm", alarm);
                    setResult(ScreenMain.RESULT_OK, i);
                    finish();
                }
                catch (Exception ex){
                    Log.i(LOGGING, ex.toString());
                }
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
            }
        });

    }

    public Alarm getData(){
        EditText createName = (EditText)findViewById(R.id.createName);
        EditText createTime = (EditText)findViewById(R.id.createTime);
        EditText createSound = (EditText)findViewById(R.id.createSound);
        EditText createRepeats = (EditText)findViewById(R.id.createRepeats);

        Alarm a = new Alarm();
        a.setAlarmName(createName.getText().toString());
        a.setAlarmTime(createTime.getText().toString());
        a.setAlarmSound(createSound.getText().toString());
        a.setAlarmRepeats(createRepeats.getText().toString());
        a.setAlarmStatus("ON");
        return a;
    }

}
