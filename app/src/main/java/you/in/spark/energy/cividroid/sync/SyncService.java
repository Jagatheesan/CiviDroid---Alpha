package you.in.spark.energy.cividroid.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class SyncService extends Service {

    private static final Object syncAdapterLock = new Object();
    private static SyncAdapter syncAdapter;


    @Override
    public void onCreate() {
        synchronized (SyncService.syncAdapterLock) {
            if (SyncService.syncAdapter == null)
                SyncService.syncAdapter = new SyncAdapter(this.getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return SyncService.syncAdapter.getSyncAdapterBinder();
    }
}
