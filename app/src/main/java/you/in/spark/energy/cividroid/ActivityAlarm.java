package you.in.spark.energy.cividroid;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.Html;

import you.in.spark.energy.cividroid.R.drawable;
import you.in.spark.energy.cividroid.authentication.AuthenticatorActivity;


public class ActivityAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int id = Integer.valueOf(intent.getStringExtra(CiviContract.ACTIVITY_TABLE_COLUMNS[1]));
        String subject = null, detail = null;
        Cursor activity = context.getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.ACTIVITY_TABLE), new String[]{CiviContract.ACTIVITY_TABLE_COLUMNS[2], CiviContract.ACTIVITY_TABLE_COLUMNS[6]}, CiviContract.ACTIVITY_TABLE_COLUMNS[0] + "=?", new String[]{"" + id}, null);
        while (activity.moveToNext()) {
            subject = activity.getString(0);
            detail = activity.getString(1);
        }
        activity.close();

        if (subject == null) {
            subject = "";
        }

        if (detail == null) {
            detail = "";
        }

        Builder mBuilder =
                new Builder(context)
                        .setSmallIcon(drawable.cividroid_logo)
                        .setContentTitle(subject)
                        .setContentText(Html.fromHtml(detail));

        Intent resultIntent = new Intent(context, AuthenticatorActivity.class);


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
