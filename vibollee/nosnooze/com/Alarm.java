package vibollee.nosnooze.com;

import java.io.Serializable;

/**
 * Created by Vibol on 4/9/2015.
 */
public class Alarm implements Serializable
{

    private String AlarmID = null;
    private String AlarmName = "";
    private String AlarmTime = "";
    private String AlarmSound = "";
    private String AlarmRepeats = "";
    private String AlarmStatus = "";


    public Alarm()
    {

    }

    public Alarm(String alarmName, String alarmTime, String alarmSound, String alarmRepeats)
    {
        AlarmName = alarmName;
        AlarmTime = alarmTime;
        AlarmSound = alarmSound;
        AlarmRepeats = alarmRepeats;
    }

    public String getAlarmID()
    {
        return AlarmID;
    }

    public void setAlarmID(String alarmID)
    {
        AlarmID = alarmID;
    }

    public String getAlarmName()
    {
        return AlarmName;
    }

    public void setAlarmName(String alarmName)
    {
        AlarmName = alarmName;
    }

    public String getAlarmTime()
    {
        return AlarmTime;
    }

    public void setAlarmTime(String alarmTime)
    {
        AlarmTime = alarmTime;
    }

    public String getAlarmSound()
    {
        return AlarmSound;
    }

    public void setAlarmSound(String alarmSound)
    {
        AlarmSound = alarmSound;
    }

    public String getAlarmRepeats()
    {
        return AlarmRepeats;
    }

    public void setAlarmRepeats(String alarmRepeats)
    {
        AlarmRepeats = alarmRepeats;
    }

    public String getAlarmStatus()
    {
        return AlarmStatus;
    }

    public void setAlarmStatus(String alarmStatus)
    {
        AlarmStatus = alarmStatus;
    }
}