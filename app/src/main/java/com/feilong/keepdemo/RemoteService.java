package com.feilong.keepdemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class RemoteService extends ForegroundService {
    private static final String TAG = "RemoteService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(RemoteService.this, LocalService.class), mConnection, Service.BIND_IMPORTANT);
        CountDownTimer timer = new CountDownTimer(10 * 60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                Log.e(TAG, "onTick: " + l);
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //断开时，重启LocalService
            startService(new Intent(RemoteService.this, LocalService.class));
            bindService(new Intent(RemoteService.this, LocalService.class), mConnection, Service.BIND_IMPORTANT);
        }
    };
}
