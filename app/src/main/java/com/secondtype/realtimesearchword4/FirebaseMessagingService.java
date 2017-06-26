package com.secondtype.realtimesearchword4;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.net.URL;
import java.util.Map;

/**
 * Created by Moon on 2017-03-26.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage message)
    {
        String from = message.getFrom();
        Map<String, String> data = message.getData();
//        String title = data.get("title");
        String title = "지금이슈";
        String msg = data.get("message");
        String url_image = data.get("url_image");

        // 전달 받은 정보로 뭔가를 하면 된다.
        //토스트 띄우기
        Log.d("Title", "title = " + title);
        Log.d("Message", "message = " + msg);
        sendNotification(title, msg, url_image);
//        MainActivity.printToast("title = " + title + "\nmsg = " + msg);
    }
    private void sendNotification(String title, String body, String url_image) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);



        //이미지 온라인 링크를 가져와 비트맵으로 바꾼다.
        Bitmap bigPicture = null;
        try {
            URL url = new URL(url_image);
            bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder;

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if(bigPicture != null) {

//                  //BigTextStyle
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .setBigContentTitle("FCM Push Big Text")
//                        .bigText(messageBody))

            //이미지를 보내는 스타일 사용하기
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(bigPicture)
                    .setBigContentTitle(title)
                    .setSummaryText(body));

        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
