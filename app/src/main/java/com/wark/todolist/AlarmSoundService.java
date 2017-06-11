package com.wark.todolist;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.Toast;

public class AlarmSoundService extends Service {
    public AlarmSoundService() {
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        Toast.makeText(this,"알람",Toast.LENGTH_SHORT).show();
        Vibrator vide;
        vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vide.vibrate(1000);
        return START_NOT_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
