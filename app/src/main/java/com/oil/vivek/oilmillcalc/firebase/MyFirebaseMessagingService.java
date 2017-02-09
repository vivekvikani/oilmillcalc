package com.oil.vivek.oilmillcalc.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oil.vivek.oilmillcalc.R;
import com.oil.vivek.oilmillcalc.notification_screen;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vivek on 17-12-2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    Bitmap bitmap;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String imageUri;
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            imageUri = remoteMessage.getData().get("image");
            bitmap = getBitmapfromUrl(imageUri);
        }

        if (remoteMessage.getNotification() != null) {
             String title = remoteMessage.getNotification().getTitle();
            if(title != null)
                createNotification(remoteMessage.getNotification().getBody(), title, bitmap, remoteMessage.getSentTime());
            else
                createNotification(remoteMessage.getNotification().getBody(), getString(R.string.app_name), bitmap, remoteMessage.getSentTime());
        }
    }

    private void createNotification( String messageBody, String title, Bitmap image, long time) {
        Intent intent = new Intent( this , notification_screen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        intent.putExtra("body", messageBody);
        if(image!=null)
            intent.putExtra("image", image);
        else
            intent.putExtra("image", "");
        intent.putExtra("time", time);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = null;
        if(image != null){
             mNotificationBuilder = new NotificationCompat.Builder( this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(
                            getResources(), R.drawable.ic_launcher))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(image))/*Notification with Image*/
                    .setAutoCancel(true)
                    .setSound(notificationSoundURI)
                    .setContentIntent(resultIntent);
        }else{
            mNotificationBuilder = new NotificationCompat.Builder( this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(
                            getResources(), R.drawable.ic_launcher))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(notificationSoundURI)
                    .setContentIntent(resultIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
