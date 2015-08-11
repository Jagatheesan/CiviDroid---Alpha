package you.in.spark.energy.cividroid;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;


public class CallIntentService extends IntentService {


    public CallIntentService() {
        super("LastSMSCallIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String lastConvDate = sp.getString(CiviContract.LAST_CONV_DATE_PREF, "0");
        Log.v("LASTCONVDATE", lastConvDate);
        Cursor callLog = getContentResolver().query(CallLog.Calls.CONTENT_URI,CiviContract.CALL_LOG_COLUMNS,"("+CiviContract.NAME_CALL_LOG_COLUMN+" IS NOT NULL) AND ("+CiviContract.TYPE_CALL_LOG_COLUMN+" = ? OR "+CiviContract.TYPE_CALL_LOG_COLUMN+" = ?) AND ("+CiviContract.DATE_CALL_LOG_COLUMN+" > ?)",new String[]{String.valueOf(CallLog.Calls.INCOMING_TYPE),String.valueOf(CallLog.Calls.OUTGOING_TYPE),lastConvDate},CiviContract.DATE_CALL_LOG_COLUMN+" DESC");
        if(callLog.moveToFirst()) {
            sp.edit().putString(CiviContract.LAST_CONV_DATE_PREF,callLog.getString(1)).apply();
            String number = callLog.getString(0).replaceAll("\\D", "");
            String contactID = null;

            //find if it's CiviCRM contact call

            //find raw contact ID
            Uri phoneNumberUri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(number));
            Cursor res = getContentResolver().query(phoneNumberUri, new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID}, null, null, null);
            if(res.moveToFirst()) {
                contactID = res.getString(0);
                Log.v("CONTACTID",contactID);

                //check if rawContactID is present in Civi contacts table
                Cursor civiCheck = getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE),new String[]{CiviContract.ID_COLUMN},CiviContract.CONTACT_ID_FIELD+"=?",new String[]{contactID},null);
                if(civiCheck.moveToFirst()) {

                    Log.v("CIVICHECK","TRUE");
                    //it's a civicrm contact, launch Notes Dialog
                    Intent notesIntent = new Intent(this,WriteNotesService.class);
                    notesIntent.putExtra(CiviContract.NAME_CALL_LOG_COLUMN,callLog.getString(3));
                    notesIntent.putExtra(CiviContract.DATE_CALL_LOG_COLUMN,callLog.getString(1));
                    notesIntent.putExtra(CiviContract.NOTES_DURATION_COLUMN,callLog.getString(2));
                    notesIntent.putExtra(CiviContract.CONTACT_ID_FIELD,contactID);
                    startService(notesIntent);
                }
                civiCheck.close();
            }
            res.close();

        }
        DatabaseUtils.dumpCursor(callLog);
        callLog.close();

    }
}