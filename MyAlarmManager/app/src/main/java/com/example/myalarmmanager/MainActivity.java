package com.example.myalarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,  DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {
    TextView tvOnceDate;
    TextView tvOnceTime;
    TextView tvRepeatingTime;
    EditText edtOnceMessage;
    EditText edtRepeatingMessage;
    ImageButton btnOnceDate;
    ImageButton btnOnceTime;
    ImageButton btnRepeatingTime;
    Button btnSetOnce;
    Button btnSetRepeating;
    Button btnCancelRepeating;

    private AlarmReceiver alarmReceiver;
    final String DATE_PICKER_TAG = "DatePicker";
    final String TIME_PICKER_ONCE_TAG = "TimePickerOnce";
    final String TIME_PICKER_REPEAT_TAG = "TimePickerRepeat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOnceDate = findViewById(R.id.tv_once_date);
        tvOnceTime = findViewById(R.id.tv_once_time);
        edtOnceMessage = findViewById(R.id.edt_once_message);
        btnOnceDate = findViewById(R.id.btn_once_date);
        btnOnceTime = findViewById(R.id.btn_once_time);
        btnSetOnce = findViewById(R.id.btn_set_once_alarm);
        tvRepeatingTime = findViewById(R.id.tv_repeating_time);
        btnRepeatingTime = findViewById(R.id.btn_repeating_time);
        edtRepeatingMessage = findViewById(R.id.edt_repeating_message);
        btnSetRepeating = findViewById(R.id.btn_set_repeating_alarm);
        btnCancelRepeating = findViewById(R.id.btn_cancel_repeating_alarm);
        btnCancelRepeating.setOnClickListener(this);
        btnSetRepeating.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);
        btnOnceDate.setOnClickListener(this);
        btnOnceTime.setOnClickListener(this);
        btnSetOnce.setOnClickListener(this);
        alarmReceiver = new AlarmReceiver();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_once_date:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(),DATE_PICKER_TAG);
                break;
            case R.id.btn_once_time:
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(),TIME_PICKER_ONCE_TAG);
                break;
            case R.id.btn_set_once_alarm:
                String onceDate = tvOnceDate.getText().toString();
                String onceTime = tvOnceTime.getText().toString();
                String onceMessage = edtOnceMessage.getText().toString();
                alarmReceiver.setOneTimeAlarmManager(this,AlarmReceiver.TYPE_ONE_TIME,
                onceDate,onceTime,onceMessage);
                break;
            case R.id.btn_repeating_time:
                TimePickerFragment timePickerFragmentRepeat = new TimePickerFragment();
                timePickerFragmentRepeat.show(getSupportFragmentManager(), TIME_PICKER_REPEAT_TAG);
                break;
            case R.id.btn_set_repeating_alarm:
                String repeatTime = tvRepeatingTime.getText().toString();
                String repeatMessage = edtRepeatingMessage.getText().toString();
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                        repeatTime, repeatMessage);
                break;
            case R.id.btn_cancel_repeating_alarm:
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
                break;
        }
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tvOnceDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());
        switch (tag){
            case TIME_PICKER_ONCE_TAG:
                tvOnceTime.setText(dateFormat.format(calendar.getTime()));
                break;
            case TIME_PICKER_REPEAT_TAG:
                tvRepeatingTime.setText(dateFormat.format(calendar.getTime()));
                break;
            default:
                break;
        }
    }

}
