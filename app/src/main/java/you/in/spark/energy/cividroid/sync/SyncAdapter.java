package you.in.spark.energy.cividroid.sync;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import you.in.spark.energy.cividroid.CiviContract;
import you.in.spark.energy.cividroid.R;
import you.in.spark.energy.cividroid.api.ICiviApi;
import you.in.spark.energy.cividroid.authentication.AuthenticatorActivity;
import you.in.spark.energy.cividroid.entities.QueryResult;


public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private ContentResolver contentResolver;
    private static final String TAG = "SyncAdapter";


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        contentResolver = context.getContentResolver();
    }


    @Override
    public void onPerformSync(Account account, final Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String apiKey, siteKey;
        apiKey = sp.getString(CiviContract.API_KEY, null);
        siteKey = sp.getString(CiviContract.SITE_KEY, null);

        if (apiKey != null && siteKey != null) {

            JsonObject json = new JsonObject();
            json.addProperty("sequential", 1);
            final JsonObject modDateFilter = new JsonObject();
            modDateFilter.addProperty(">", "2015-06-18 23:40:24");
            json.add("modified_date", modDateFilter);

            final List<String> fieldNames = new ArrayList<String>();
            fieldNames.addAll(sp.getStringSet(CiviContract.CONTACTS_FIELD_NAME_PREF, null));
            String fields = "";
            for(String field : fieldNames) {
                fields+=","+field;
            }
            json.addProperty("return",fields);
            fields=null;

            RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).
                    setEndpoint("http://10.0.2.2:81").build();

            ICiviApi iCiviApi = adapter.create(ICiviApi.class);

            iCiviApi.getContacts(json.toString(), apiKey, siteKey, new Callback<QueryResult>() {
                @Override
                public void success(QueryResult contacts, Response response) {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                    //name.setText(contacts.getValues().get("2").getDisplayName());
                    Log.v(TAG, "Pass: " + response.toString());
                    Log.v(TAG, "Name: " + contacts.toString());
                    // Log.v(TAG,"Value size: "+contacts.getValues().get("1").getValues().toString());
                    //Log.v(TAG,"*"+contacts.getValues().get("2").getDisplayName()+"*");
                    if (contacts.getIsError() == 1) {
                        invalidate();
                    }
                    else {

                        Log.v(TAG, "Size of Values: " + contacts.getValues().size() + "");

                        List<JsonObject> responseList = contacts.getValues();
                        int responseListSize = responseList.size();

                        if (responseListSize > 0) {


                            Map<Integer,Integer> toSyncPositions = new HashMap<Integer, Integer>();

                            //get All phone numbers
                            Map<String,Integer> phoneNumbers = new HashMap<String, Integer>();
                            //Map<String,Integer> rawContactIDs = new HashMap<String, Integer>();

                            Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Contactables.RAW_CONTACT_ID, ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME}, null, null, null);
                            while (phones.moveToNext()) {
                               // rawContactIDs.add(phones.getInt(1));
                                Log.v("RawContact: ",phones.getInt(1)+"");
                                phoneNumbers.put(phones.getString(0).replaceAll("\\D", ""), phones.getInt(1));
                                Log.v(TAG, phoneNumbers.get(0) + "");
                            }
                            phones.close();
                            int phoneCount = phoneNumbers.size();

                            //get All email addresses
                            Map<String,Integer> emailAddresses = new HashMap<String, Integer>();
                            Cursor eAds = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Contactables.RAW_CONTACT_ID}, null, null, null);
                            while (eAds.moveToNext()) {
                                emailAddresses.put(eAds.getString(0),eAds.getInt(1));
                                Log.v(TAG, emailAddresses.get(0) + "");
                            }
                            eAds.close();
                            int emailCount = emailAddresses.size();

                            //running loop until emailCount and phoneCount exhaust or the responseList exhausts

                            for(int i = 0;i<responseListSize;i++) {
                                if (emailCount == 0 && phoneCount == 0) {
                                    break;
                                }
                                Log.v("SyncAdapter",responseList.toString());
                                String phone = responseList.get(i).get(CiviContract.PHONE_FIELD).getAsString().replaceAll("\\D", "");
                                String email = responseList.get(i).get(CiviContract.EMAIL_FIELD).getAsString();
                                if (phoneCount > 0) {
                                    if (phoneNumbers.containsKey(phone)) {
                                        phoneCount--;
                                        toSyncPositions.put(i, phoneNumbers.get(phone));
                                        continue;
                                    }
                                }
                                if (emailCount > 0) {
                                    if (emailAddresses.containsKey(email)) {
                                        emailCount--;
                                        toSyncPositions.put(i, emailAddresses.get(email));
                                    }
                                }
                            }



                            for (Map.Entry<Integer, Integer> entry : toSyncPositions.entrySet())
                            {
                                ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                                ops.add(ContentProviderOperation.newInsert(addCallerIsSyncAdapterParameter(ContactsContract.RawContacts.CONTENT_URI, true))
                                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, CiviContract.ACCOUNT)
                                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, CiviContract.ACCOUNT_TYPE)
                                        .withValue(ContactsContract.RawContacts.AGGREGATION_MODE, ContactsContract.RawContacts.AGGREGATION_MODE_DISABLED)
                                        .build());

                                ops.add(ContentProviderOperation.newInsert(addCallerIsSyncAdapterParameter(ContactsContract.Data.CONTENT_URI, true))
                                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                        .withValue(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/you.in.spark.energy.cividroid.profile")
                                        .withValue(ContactsContract.Data.DATA2, "Civicrm contact")
                                        .withValue(ContactsContract.Data.DATA3, "Open contact in Cividroid")
                                        .build());

                                try {
                                    ContentProviderResult[] results = getContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                                    long contactID = ContentUris.parseId(results[0].uri);
                                    Log.v("Inserted ID: ", "" + contactID);
                                    ContentValues cv = new ContentValues();
                                    cv.put(ContactsContract.AggregationExceptions.TYPE, ContactsContract.AggregationExceptions.TYPE_KEEP_TOGETHER);
                                    cv.put(ContactsContract.AggregationExceptions.RAW_CONTACT_ID1, contactID);
                                    cv.put(ContactsContract.AggregationExceptions.RAW_CONTACT_ID2, entry.getValue());

                                    //insert other values


                                    getContext().getContentResolver().update(ContactsContract.AggregationExceptions.CONTENT_URI, cv, null, null);

                                    int titleSize = fieldNames.size();
                                    ContentValues vals = new ContentValues();
                                    vals.put(CiviContract.CONTACT_ID_FIELD,String.valueOf(entry.getValue()));
                                    vals.put(CiviContract.RAW_CONTACT_ID_FIELD,contactID);
                                    for(int i = 0; i<titleSize;i++){
                                        vals.put(fieldNames.get(i), responseList.get(entry.getKey()).get(fieldNames.get(i)).getAsString());
                                    }

                                    Uri uri = getContext().getContentResolver().insert(Uri.parse(CiviContract.CONTENT_URI+"/"+CiviContract.CONTACTS_FIELD_TABLE),vals);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getContext(), "Fail" + error.getMessage(), Toast.LENGTH_LONG).show();
                    Log.v(TAG, "Fail: " + error.getMessage());
                    invalidate();
                }
            });

        } else {
            invalidate();
        }

    }

    private static Uri addCallerIsSyncAdapterParameter(Uri uri, boolean isSyncOperation) {
        if (isSyncOperation) {
            return uri.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true")
                    .build();
        }
        return uri;
    }

    private void invalidate() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.cividroid_logo)
                        .setContentTitle(getContext().getString(R.string.connection_error))
                        .setContentText(getContext().getString(R.string.connection_error_desc));

        Intent resultIntent = new Intent(getContext(), AuthenticatorActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(AuthenticatorActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());

    }

}
