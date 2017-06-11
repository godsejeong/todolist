package com.wark.todolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by pc on 2017-06-06.
 */

public class Set_todo extends Activity implements View.OnClickListener {
    EditText list_text;//일정적기
    FloatingActionButton fab;//플로팅버튼
    Button close;//x표시
    Switch sw;//스위치
    Button time_btn;//시간버튼
    Button cla_btn;//캘런더버튼
    TextView information;//정보
    ArrayList<Data> items;
    AlarmManager alarmManager;
    Calendar calendar = Calendar.getInstance();
    SharedPreferences prefs;
    int pos;
    Gson gson;
    Intent Alarm_intent;
    LinearLayout layout;
    PendingIntent Alarm_pintent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settodo);
        list_text = (EditText) findViewById(R.id.editText);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        close = (Button) findViewById(R.id.close_btn);
        sw = (Switch) findViewById(R.id.time_switch);
        time_btn = (Button) findViewById(R.id.time_btn);
        cla_btn = (Button) findViewById(R.id.cal_btn);
        information = (TextView) findViewById(R.id.inpormation);
        layout = (LinearLayout) findViewById(R.id.layout);
        GregorianCalendar calendar = new GregorianCalendar();
        //--
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        items = new ArrayList<>();
        items.clear();
        loadNowData();
        Intent intent = getIntent();
        pos = intent.getIntExtra("position", -1);
        Log.d("position", String.valueOf(pos));

        gson = new Gson();
        Alarm_intent = new Intent("com.test.alarmtestous.ALARM_START");
        layout.setVisibility(View.INVISIBLE);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (layout.getVisibility() == View.VISIBLE)
                    layout.setVisibility(View.INVISIBLE);
                else
                    layout.setVisibility(View.VISIBLE);
            }
        });//스위치 클릭시 layout를 보여주거나 없애줌

        fab.setOnClickListener(this);
        close.setOnClickListener(this);
        time_btn.setOnClickListener(this);
        cla_btn.setOnClickListener(this);
       // Log.d("set_items", items+"\n"+String.valueOf(items)+"\n"+items.get(0));
        if (pos != -1) {//리스트 클릭시에만 작동
            list_text.setText("" + items.get(pos).getTitle() + "");
            if (items.get(pos).getDate() != null) {
                layout.setVisibility(View.VISIBLE);
                Log.e("date", items.get(pos).getDate() + "");
                cla_btn.setText("" + items.get(pos).getDate());
                time_btn.setText("" + items.get(pos).getTime());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (list_text.getText().toString().equals("")) {
                    finish();
                } else if (pos != -1) {
                    Log.d("pos", "asdfasdf");
                    if (layout.getVisibility() == View.VISIBLE) {
                        items.set(pos, new Data(list_text.getText().toString(), cla_btn.getText().toString(), time_btn.getText().toString()));
                        Alarm_pintent = PendingIntent.getBroadcast(this, 0, Alarm_intent, Alarm_pintent.FLAG_UPDATE_CURRENT);
                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), Alarm_pintent);
                    } else
                        items.set(pos, new Data(list_text.getText().toString(), null, null));
                    Log.d("pos = 1", String.valueOf(items.get(pos)));
                    String stringItem = gson.toJson(items);
                    getIntent().putExtra("result", stringItem);
                    setResult(RESULT_OK, getIntent());
                    finish();
                } else if (layout.getVisibility() == View.VISIBLE) {
                    Log.d("Asd", "asdfasdf");
                    items.add(new Data(list_text.getText().toString(), cla_btn.getText().toString(), time_btn.getText().toString()));
                    String stringItem = gson.toJson(items);
                    getIntent().putExtra("result", stringItem);
                    setResult(RESULT_OK, getIntent());
                    Alarm_pintent = PendingIntent.getBroadcast(this, 0, Alarm_intent, Alarm_pintent.FLAG_UPDATE_CURRENT);
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),    Alarm_pintent);
                    finish();
                } else {
                    Log.d("gm","hmm");
                    items.add(new Data(list_text.getText().toString(), null, null));
                    String stringItem = gson.toJson(items);
                    getIntent().putExtra("result", stringItem);
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
                break;

            case R.id.close_btn:
                finish();
                break;

//            case R.id.cal_btn:
//                new DatePickerDialog(Set_todo.this, dateSetListener, year, month, day).show();
//                break;
//            case R.id.time_btn:
//                new TimePickerDialog(Set_Daily_Activity.this, timeSetListener, hour, minute, false).show();
//                break;

        }
    }
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            String msg = String.format("%d:%d  %s", hourOfDay, minute, (hourOfDay<13) ? "AM" : "PM");
            time_btn.setText(""+msg);
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(calendar.MINUTE,minute);
        }

    };
    void loadNowData() {
        Gson gson = new Gson();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String json = pref.getString("save", "");
        Log.d("asd", json);
        ArrayList<Data> items_ = new ArrayList<>();
        items_ = gson.fromJson(json, new TypeToken<ArrayList<Data>>(){}.getType());
        if (items_ != null) items.addAll(items_);
    }
}

