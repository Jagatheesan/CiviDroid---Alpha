package you.in.spark.energy.cividroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;


public class IncomingCall extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {
            Intent callService = new Intent(context, CallIntentService.class);
            callService.putExtras(intent);
            context.startService(callService);
        }
    }

}
