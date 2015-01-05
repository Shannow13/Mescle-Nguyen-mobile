package esiea.mescle_nguyen_mobile;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

public class MyIntentService extends IntentService {

    private static final int MY_NOTIFICATION_ID = 1;
    NotificationManager notificationManager;
    Notification myNotification;

    public static final String ACTION_MyIntentService = "esiea.mescle_nguyen_mobile.androidintentservice.RESPONSE";
    public static final String ACTION_MyUpdate = "esiea.mescle_nguyen_mobile.androidintentservice.UPDATE";
    public static final String EXTRA_KEY_IN = "EXTRA_IN";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String EXTRA_KEY_UPDATE = "EXTRA_UPDATE";
    String msgFromActivity;
    String extraOut;

    public MyIntentService() {
        super("esiea.mescle_nguyen_mobile.MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Get input
        msgFromActivity = intent.getStringExtra(EXTRA_KEY_IN);
        extraOut = "Application créée par :\n" + msgFromActivity;

        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Send update
            Intent intentUpdate = new Intent();
            intentUpdate.setAction(ACTION_MyUpdate);
            intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
            intentUpdate.putExtra(EXTRA_KEY_UPDATE, i);
            sendBroadcast(intentUpdate);

            // Generate notification
            String notificationText = String.valueOf((int) (100 * i / 10)) + " %";
            myNotification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Merci !")
                    .setContentText(notificationText)
                    .setTicker("Nous vous remercions d'utiliser notre application !")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.logo)
                    .build();

            notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
        }

        // Return result
        Intent intentResponse = new Intent();
        intentResponse.setAction(ACTION_MyIntentService);
        intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
        intentResponse.putExtra(EXTRA_KEY_OUT, extraOut);
        sendBroadcast(intentResponse);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
}