package you.in.spark.energy.cividroid.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class AuthenticatorService extends Service {

    private Authenticator authenticator;

    @Override
    public void onCreate() {
        this.authenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.authenticator.getIBinder();
    }
}
