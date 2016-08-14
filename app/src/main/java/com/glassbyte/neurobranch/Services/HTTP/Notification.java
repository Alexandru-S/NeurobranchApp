package com.glassbyte.neurobranch.Services.HTTP;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.glassbyte.neurobranch.Portal.QuestionPrefabs.EpochHolder;
import com.glassbyte.neurobranch.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ed on 11/06/16.
 */
public class Notification {
    public static class NotificationId {
        private final static AtomicInteger integer = new AtomicInteger(0);
        public static int getNotificationId() {
            return integer.incrementAndGet();
        }
    }

    public static class NotificationService {
        public static void notifyUserWeb(Context context, String trialId) {
            Bundle bundle = new Bundle();
            bundle.putString("TRIAL_ID", trialId);
            notifyUser(context, bundle);
        }

        public static void notifyUser(Context context, Bundle dateBundle) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.web_hi_res_512))
                    .setSmallIcon(R.drawable.cloud)
                    .setContentTitle("New questions can be answered")
                    .setContentText(dateBundle.getString("TRIAL_ID"));

            Intent resultIntent = new Intent(context, EpochHolder.class);
            resultIntent.putExtras(dateBundle);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addParentStack(EpochHolder.class).addNextIntent(resultIntent);

            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NotificationId.getNotificationId(), builder.build());
        }
    }
}
