package com.example.pc41.sendnotification;

        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.app.Service;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.IBinder;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;

        import com.google.firebase.messaging.FirebaseMessagingService;
        import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessage extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private static final int NOTIFICATION_ID = 11;
    private String message = "";
    String offer_id;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

/*
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);

        String token = sp.getString("token", "");
        String userid = sp.getString("userid", "");

        if (!token.equals("")) {

            // AppData.goingpage="Notification";
            // AppData.offerback="offerback";
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sendNotification(intent, remoteMessage);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sendNotification(intent, remoteMessage);
        }
*/

        Intent intent = new Intent(this, MainActivity.class);

        sendNotification(intent,remoteMessage);




    }

    public void sendNotification(Intent intent, RemoteMessage remoteMessage) {
        //String deviceName = android.os.Build.MODEL;
        //String deviceMan = android.os.Build.MANUFACTURER;
        //if (remoteMessage.getNotification()!=null) {
        if (remoteMessage.getData().size()>0) {

            try {
                //String body1=remoteMessage.getNotification().getBody();
                /*String jsondata=remoteMessage.getNotification().toString();
                JSONObject json = new JSONObject(jsondata);*/

                NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                PendingIntent pintent= PendingIntent.getActivity(getApplicationContext(),1,intent,0);
               /* String body=remoteMessage.getNotification().getBody();
                String title=remoteMessage.getNotification().getTitle();
                String sound=remoteMessage.getNotification().getSound();
                int songid=getResources().getIdentifier(sound,"raw",getPackageName());*/

                //for Json Type Data
                Log.d(TAG, "From: " + remoteMessage.getFrom());
                // Check if message contains a data payload.
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                String body = remoteMessage.getData().get("body").toString();
                String title = remoteMessage.getData().get("title").toString();
                //String sound = remoteMessage.getData().get("sound").toString();
                // offer_id = remoteMessage.getData().get("offer_id").toString();

                //savePreference("offer_id",offer_id);
                //savePreference("goingpage","Notification");
                //saveFlag("flag","1");//for demo
                // int songid = getResources().getIdentifier(sound, "raw", getPackageName());
                NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setAutoCancel(true);
                builder.setOngoing(false);
                builder.setContentTitle(title);
                builder.setContentText(body);
                builder.setContentIntent(pintent);
                manager.notify(NOTIFICATION_ID,builder.build());

                //get data for notification

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void savePreference(String key, String value) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }
    //for demo
    public void saveFlag(String key, String value) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Noti", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }


}
