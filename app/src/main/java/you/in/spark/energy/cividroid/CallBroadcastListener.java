package you.in.spark.energy.cividroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.CallLog.Calls;
import android.telephony.TelephonyManager;

public class CallBroadcastListener extends BroadcastReceiver {

    //preventing execution twice - issue in lollipop
    private static boolean first;
    private static boolean offHookFirst;


    @Override
    public void onReceive(Context context, Intent intent) {


        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {
            if(!CallBroadcastListener.first){
                CallBroadcastListener.first = true;
                return;
            }
            CallBroadcastListener.first =false;
            Intent callService = new Intent(context, CallIntentService.class);
            callService.putExtras(intent);
            context.startService(callService);
        } else {
            if(!CallBroadcastListener.offHookFirst) {

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                Cursor lastCallLog = context.getContentResolver().query(Calls.CONTENT_URI, CiviContract.CALL_LOG_COLUMNS, null,null, CiviContract.DATE_CALL_LOG_COLUMN + " DESC");
                if(lastCallLog.moveToFirst()) {
                    sp.edit().putString(CiviContract.LAST_CONV_DATE_PREF, lastCallLog.getString(1)).apply();
                }
                lastCallLog.close();
                CallBroadcastListener.offHookFirst = true;
                return;
            }
            CallBroadcastListener.offHookFirst = false;
        }
    }

}
