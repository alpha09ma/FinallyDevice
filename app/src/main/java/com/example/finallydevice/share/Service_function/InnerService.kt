package com.example.finallydevice.share.Service_function

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Handler

import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.example.finallydevice.R
import java.time.LocalTime


public class  InnerService:Service() {
    companion object{
        val NOTIFICATION_ID=0x11
        val channelid="test"
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate();
        //发送与KeepLiveService中ID相同的Notification，然后将其取消并取消自己的前台显示
        val builder:Notification.Builder = Notification.Builder(this, channelid);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        startForeground(NOTIFICATION_ID, builder.build());
        Handler(Looper.myLooper()!!).postDelayed(object :
            Runnable {
            override fun run() {
                stopForeground(true);
                 val manager:NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                manager.cancel(NOTIFICATION_ID);
                stopSelf();
            }
        },100);
    }
    override fun onDestroy() {
        Log.d("destroy","innerservice"+ LocalTime.now().toString())
        super.onDestroy()
    }
}
