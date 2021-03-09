package com.example.sreed;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


public class Notification extends ContextWrapper {

    private static final String ID  ="com.example.taymed.cours7.EXP";
    private static final String NOM ="Cours7 canal 1";
    private NotificationManager Nmanag;

    @RequiresApi(api = Build.VERSION_CODES.O)

    public Notification(Context base) {
        super(base);
        createChannels();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        // importance_Default = aficher partout et fait du bruit mais ne s'afiche pas au premièer plan
        // "_HIGH = afficher partout fait du bruit
        // "_LOW = afficher partout mais n'est pas intrusive
        //"_MIN= ne s'affiche que dans la barre de menu
        //"_NONE = notification sans importance
        NotificationChannel canal_1 = new NotificationChannel(ID,NOM,Nmanag.IMPORTANCE_LOW);
        canal_1.enableLights(true);
        canal_1.setLightColor(Color.RED);
        canal_1.enableVibration(true);

        getNmanag().createNotificationChannel(canal_1);

    }

    public NotificationManager getNmanag() {
        if(Nmanag == null)
            Nmanag=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return Nmanag;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationCompat.Builder getmyChannelNotification(String titre, String contenu){
        return new NotificationCompat.Builder(getApplicationContext(),ID)
                .setContentText(contenu)
                .setContentTitle(titre)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);

    }
}
