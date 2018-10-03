package xyz.abhishekshah.accidentalertsystem;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

//import static xyz.abhishekshah.accidentalertsystem.R.drawable.ic_one;

public class NotificationHelper extends ContextWrapper{

    public static final String CHANNEL1ID = "channel1id";
    public static final String CHANNEL1NAME = "Channel1";

    private NotificationManager nManager;

    public NotificationHelper(Context base) {
        super(base);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
           createchannels();

        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createchannels() {

        NotificationChannel channel = new NotificationChannel(CHANNEL1ID,CHANNEL1NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {

        if(nManager==null)
        {
            nManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        }
        return nManager;
    }

    public NotificationCompat.Builder getChannelnotification() {
        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL1ID)
                .setContentTitle("working")
                .setVibrate(new long[]{1000,1000})
                .setSmallIcon(R.drawable.ic_one)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

    }
}
