package you.in.spark.energy.cividroid.sync;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.util.Pair;

import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import you.in.spark.energy.cividroid.ActivityAlarm;
import you.in.spark.energy.cividroid.CiviContract;
import you.in.spark.energy.cividroid.R.drawable;
import you.in.spark.energy.cividroid.R.string;
import you.in.spark.energy.cividroid.api.ICiviApi;
import you.in.spark.energy.cividroid.authentication.AuthenticatorActivity;
import you.in.spark.energy.cividroid.entities.CiviActivity;
import you.in.spark.energy.cividroid.entities.WriteNotesResult;


public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private final ContentResolver contentResolver;
    private static final String TAG = "SyncAdapter";


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.contentResolver = context.getContentResolver();
    }

    @TargetApi(VERSION_CODES.HONEYCOMB)
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        this.contentResolver = context.getContentResolver();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        if (isConnected(getContext())) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            String apiKey, siteKey, websiteUrl, lastScheduledID, sourceContactID;
            apiKey = sp.getString(CiviContract.API_KEY, null);
            siteKey = sp.getString(CiviContract.SITE_KEY, null);
            websiteUrl = sp.getString(CiviContract.WEBSITE_URL, null);
            sourceContactID = sp.getString(CiviContract.SOURCE_CONTACT_ID, null);

            String activityOffset = sp.getString(CiviContract.LAST_ACTIVITY_SYNC_ID, "0");
            String notesOffset = sp.getString(CiviContract.LAST_NOTES_SYNC_ID, "0");

            RestAdapter adapter = new RestAdapter.Builder().setLogLevel(LogLevel.FULL).
                    setEndpoint(websiteUrl).build();

            ICiviApi iCiviApi = adapter.create(ICiviApi.class);

            Map<String, String> fields = new HashMap<>();
            fields.put("key", siteKey);
            fields.put("api_key", apiKey);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("sequential", 1);
            jsonObject.addProperty("status_id", "Scheduled");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date(System.currentTimeMillis());


            fields.put("json", "{\"sequential\":1,\"id\":{\">\":" + activityOffset + "},\"status_id\":\"Scheduled\",\"activity_date_time\":{\">\":\"" + simpleDateFormat.format(currentDate) + "\"}}");


            //Sync Activities
            CiviActivity activity = null;
            try {
                activity = iCiviApi.getActivity(fields);
            } catch (RetrofitError re) {

            }

            if (activity != null) {
                if (activity.getIsError() != 1) {
                    int valSize = activity.getValues().size();
                    if (valSize > 0) {
                        ContentValues values[] = new ContentValues[valSize];
                        String scheduleDate = null;
                        Vector<Pair<String, String>> actIds = new Vector<>();

                        for (int i = 0; i < valSize; i++) {
                            values[i] = activity.getValues().get(i).getAllValues();
                            actIds.add(new Pair<String, String>(activity.getValues().get(i).getId(), activity.getValues().get(i).getActivityDateTime()));
                        }
                        sp.edit().putString(CiviContract.LAST_ACTIVITY_SYNC_ID, actIds.get(actIds.size() - 1).first).apply();
                        int added = this.contentResolver.bulkInsert(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.ACTIVITY_TABLE), values);
                        if (added > 0) {
                            for (Pair<String, String> act : actIds) {

                                Intent i = new Intent(this.getContext(), ActivityAlarm.class);
                                i.putExtra(CiviContract.ACTIVITY_TABLE_COLUMNS[1], act.first);
                                PendingIntent pi = PendingIntent.getBroadcast(this.getContext(), Integer.valueOf(act.first), i,
                                        PendingIntent.FLAG_ONE_SHOT);
                                Date date = null;
                                try {
                                    date = simpleDateFormat.parse(act.second);
                                } catch (ParseException e) {
                                }
                                AlarmManager alarmManager = (AlarmManager) this.getContext().getSystemService(Context.ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pi);

                            }
                        }
                    }
                } else {
                    this.invalidate();
                }
            }

            //Sync Notes on Calls
            if (sourceContactID != null) {
                fields.clear();
                fields.put("key", siteKey);
                fields.put("api_key", apiKey);

                String jsonValue = "{\"sequential\":1,\"activity_type_id\":\"Phone Call\",\"id\":{\">\":" + notesOffset + "},\"phone_number\":{\"IS NOT NULL\":1}}";

                fields.put("json", jsonValue);


                try {
                    activity = iCiviApi.getActivity(fields);
                } catch (RetrofitError re) {

                }

                if (activity != null) {
                    if (activity.getIsError() != 1) {
                        int size = activity.getValues().size();
                        if (size > 0) {
                            ContentValues values[] = new ContentValues[size];
                            for (int i = 0; i < size; i++) {
                                values[i] = activity.getValues().get(i).getAllNotesValue();
                            }
                            sp.edit().putString(CiviContract.LAST_NOTES_SYNC_ID, activity.getValues().get(activity.getValues().size() - 1).getId()).apply();
                            this.contentResolver.bulkInsert(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.ACTIVITY_TABLE), values);
                        }
                    } else {
                        this.invalidate();
                    }
                }


                //upload unsynced notes to web
                Vector<String> synced = new Vector<>();
                Cursor notes = this.contentResolver.query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.ACTIVITY_TABLE), new String[]{CiviContract.ACTIVITY_TABLE_COLUMNS[3], CiviContract.ACTIVITY_TABLE_COLUMNS[4], CiviContract.ACTIVITY_TABLE_COLUMNS[6], CiviContract.ACTIVITY_TABLE_COLUMNS[10]}, CiviContract.ACTIVITY_TABLE_COLUMNS[11] + "=?", new String[]{"1"}, null);
                while (notes.moveToNext()) {
                    fields.clear();
                    fields.put("key", siteKey);
                    fields.put("api_key", apiKey);
                    String json = "{\"sequential\":1,\"source_contact_id\":" + sourceContactID + ",\"activity_type_id\":\"Phone Call\",\"details\":\"" + notes.getString(2) + "\",\"activity_date_time\":\"" + notes.getString(0) + "\",\"duration\":" + notes.getString(1) + ",\"phone_number\":" + notes.getString(3) + "}";
                    fields.put("json", json);
                    WriteNotesResult result = null;
                    try {
                        result = iCiviApi.writeNotes(fields);
                    } catch (RetrofitError rfe) {
                    }
                    if (result != null) {
                        if (result.getIsError() == 0) {
                            synced.add(notes.getString(3));
                        }
                    }
                }
                notes.close();

                //update sync detail in local db
                if (synced.size() > 0) {
                    for (String id : synced) {
                        ContentValues val = new ContentValues();
                        val.putNull(CiviContract.ACTIVITY_TABLE_COLUMNS[11]);
                        this.contentResolver.update(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.ACTIVITY_TABLE), val, CiviContract.ACTIVITY_TABLE_COLUMNS[10] + "=?", new String[]{id});
                    }
                }
            }
        }


    }

    private void invalidate() {

        Builder mBuilder =
                new Builder(this.getContext())
                        .setSmallIcon(drawable.cividroid_logo)
                        .setContentTitle(this.getContext().getString(string.connection_error))
                        .setContentText(this.getContext().getString(string.connection_error_desc));

        Intent resultIntent = new Intent(this.getContext(), AuthenticatorActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.getContext());
        stackBuilder.addParentStack(AuthenticatorActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) this.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }

}
