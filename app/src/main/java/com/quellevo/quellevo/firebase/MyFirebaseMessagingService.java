package com.quellevo.quellevo.firebase;

/**
 * Created by Nicolas on 17/1/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.quellevo.quellevo.R;
import com.quellevo.quellevo.home.HomeScreenActivity;
import com.quellevo.quellevo.utils.Constants;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final int NEW_INVITATION_TO_EVENT = 1;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. ApiResponse messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. ApiResponse messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //TODO MANEJAR CASES CON POSIBLES CLAVES ENVIADAS EN LA NOTIFICACION
        //SE OBTIENE LA DATA DE LA NOTIFICACION
        Map<String, String> data = remoteMessage.getData();
        //TODO OBTENER VALOR NUMERICO DE LA NOTIFICACION Y COMPARARLO CONTRA LAS CONSTANTES DE ESTA CLASE
        //VALUE(0) = VALOR NOTIF , 1,2,3 O 4 (VER CONSTANTES PARA EL SWITCH)
        //VALUE(1) TITULO NOTIF
        //VALUE(2) SUBTITULO NOTIF
        int notifValue = 0;
        String title = "Doc to Doc";
        String subtitle = "";
        Collection c = ((ArrayMap) data).values();
        if (c != null) {
            ArrayList<String> values = new ArrayList<>(c);
            notifValue = Integer.valueOf(values.get(0));
            switch (notifValue) {
                case NEW_INVITATION_TO_EVENT:
                    newInvitationToEvent(values);
                    break;
                default:
                    //TODO FIREBASE
                    break;
            }
        }

        //PARA LEER DE LA NOTIFICACION RECIBIDA HAY QUE PREGUNTAR POR LAS CLAVES DEL HASH OBTENIDO
        /*if (data != null) {
            String activity = data.get("activity");
            if (activity != null && activity.equals("HOME")) {
                goToHome(userB, "HOME", "PULSA PARA IR AL HOME");
            }
        }*/
        //   sendNotification("al tutorial");
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void newInvitationToEvent(ArrayList<String> values) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //value 1 TITLE
        // value 2 creator
        prepareNotificationGoTo(intent, "New Invitation", values.get(2) + " te invito al evento: " + values.get(1));
    }
    // [END receive_message]

    /* private void goToHome(boolean userB, String title, String message) {
         Intent intent = new Intent(this, userB ? HomeActivityUserB.class : HomeActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         prepareNotificationGoTo(intent, title, message);
     }
 */
    private void prepareNotificationGoTo(Intent intent, String title, String message) {
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        intent.putExtra(Constants.OPEN_FROM_NOTIFICATION, true);
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                // add all of DetailsActivity's parents to the stack,
                // followed by DetailsActivity itself
                .addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.quellevo_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    /*
        ESTO DEBERIA IR EN LAS CLASES QUE VAS APRETANDO LA NOTIFICACION QUE LLEGA
         if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
     */
}