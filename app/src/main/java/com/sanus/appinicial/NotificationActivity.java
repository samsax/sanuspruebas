package com.sanus.appinicial;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

/**
 * Created by Samuel on 20/07/2015.
 */
public class NotificationActivity extends Activity {
    int notificationID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificacion);
    }

    public void onClick(View v){
        displayNotification();
    }

    protected void displayNotification(){
        Intent i = new Intent(this, Menu.class);
        i.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker ="Ejemplo de notificacion";
        CharSequence contentTitle = "Sanus";
        CharSequence contentText = "Hora de algo";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(R.mipmap.ic_launcher, ticker, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .build();
        nm.notify(notificationID, noti);
    }

}
