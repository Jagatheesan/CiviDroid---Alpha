package you.in.spark.energy.cividroid;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class CallIntentService extends IntentService {

    private int maxWait = 5;

    public CallIntentService() {
        super("LastSMSCallIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String lastConvDate = sp.getString(CiviContract.LAST_CONV_DATE_PREF, "0");

        Cursor callLog;
        do {
            callLog = this.getContentResolver().query(Calls.CONTENT_URI, CiviContract.CALL_LOG_COLUMNS, CiviContract.DATE_CALL_LOG_COLUMN + " > ?", new String[]{lastConvDate}, CiviContract.DATE_CALL_LOG_COLUMN + " DESC");
            if (callLog.moveToFirst()) {

                //sp.edit().putString(CiviContract.LAST_CONV_DATE_PREF, callLog.getString(1)).apply();

                String number, date, duration, name;
                duration = callLog.getString(2);
                if (duration == null) {
                    duration = "0";
                }
                name = callLog.getString(3);
                if (name == null) {
                    name = "null";
                }
                int type = callLog.getInt(4);


                if ((type == Calls.INCOMING_TYPE || type == Calls.OUTGOING_TYPE) && !duration.equalsIgnoreCase("0") && !name.equalsIgnoreCase("null")) {

                    number = callLog.getString(0);
                    date = callLog.getString(1);
                    String contactID = null;

                    //find if it's CiviCRM contact call

                    //find contact ID
                    Uri phoneNumberUri = Uri.withAppendedPath(Phone.CONTENT_FILTER_URI, Uri.encode(number));
                    Cursor res = this.getContentResolver().query(phoneNumberUri, new String[]{Phone.CONTACT_ID}, null, null, null);
                    while (res.moveToNext()) {
                        contactID = res.getString(0);

                        Cursor content = this.getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE), null, null, null, null);
                        content.close();


                        //check if rawContactID is present in Civi contacts table
                        Cursor civiCheck = this.getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE), new String[]{CiviContract.CONTACT_TABLE_COLUMNS[0]}, CiviContract.CONTACT_ID_FIELD + "=?", new String[]{contactID}, null);
                        if (civiCheck.moveToFirst()) {
                            Intent writeNotes = new Intent(this, WriteNotesService.class);
                            writeNotes.putExtra(CiviContract.NAME_CALL_LOG_COLUMN, name);
                            writeNotes.putExtra(CiviContract.DATE_CALL_LOG_COLUMN, date);
                            writeNotes.putExtra(CiviContract.CONTACT_ID_FIELD, civiCheck.getString(0));
                            writeNotes.putExtra(CiviContract.DURATION_CALL_LOG_COLUMN, duration);
                            this.startService(writeNotes);
                            break;
                        }
                        civiCheck.close();
                    }
                    res.close();
                }
            } else {
                Cursor test = this.getContentResolver().query(Calls.CONTENT_URI, CiviContract.CALL_LOG_COLUMNS, null, null, CiviContract.DATE_CALL_LOG_COLUMN + " DESC");
                test.close();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                this.maxWait--;
                continue;
            }
            break;
        } while (this.maxWait > 0);


        callLog.close();
    }

}