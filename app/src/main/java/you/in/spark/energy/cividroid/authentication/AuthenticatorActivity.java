package you.in.spark.energy.cividroid.authentication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import you.in.spark.energy.cividroid.CiviContract;
import you.in.spark.energy.cividroid.FieldSelectionActivity;
import you.in.spark.energy.cividroid.R;


public class AuthenticatorActivity extends AppCompatActivity{

    private Boolean callFromActivity = false;
    private static final String TAG = "AuthenticatorActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        callFromActivity = getIntent().getBooleanExtra(CiviContract.CALL_FROM_ACTIVITY, false);
        Log.v(TAG,callFromActivity.toString());

        setContentView(R.layout.authenticator_activity);

        final EditText etSiteKey = (EditText) findViewById(R.id.etSiteKey);
        final EditText etApiKey = (EditText) findViewById(R.id.etApiKey);

        FloatingActionButton fabSaveKeys = (FloatingActionButton) findViewById(R.id.fabSaveKeys);
        if (Build.VERSION.SDK_INT >= 21) {
            fabSaveKeys.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_white_24dp, null));
        } else {
            fabSaveKeys.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_white_24dp));
        }

        fabSaveKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "fabSaveKeys OnClick");
                if(!TextUtils.isEmpty(etApiKey.getText()) && !TextUtils.isEmpty(etSiteKey.getText())) {
                    Log.v(TAG, "not empty");
                    Intent intent = new Intent(AuthenticatorActivity.this, FieldSelectionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(CiviContract.SITE_KEY, etSiteKey.getText().toString());
                    intent.putExtra(CiviContract.API_KEY, etApiKey.getText().toString());
                    startActivity(intent);
                    //finish();
                    // SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(AuthenticatorActivity.this);
                    //sp.edit().putString(CiviContract.SITE_KEY,etSiteKey.getText().toString()).apply();
                    // sp.edit().putString(CiviContract.API_KEY,etApiKey.getText().toString()).apply();
                    //  Account newAccount = new Account(CiviContract.ACCOUNT, CiviContract.ACCOUNT_TYPE);
                    // Get an instance of the Android account manager
                    // AccountManager accountManager = (AccountManager) AuthenticatorActivity.this.getSystemService(ACCOUNT_SERVICE);
                    //  accountManager.addAccountExplicitly(newAccount,null,null);
                    /*if(callFromActivity){
                        Intent intent = new Intent(AuthenticatorActivity.this, CiviAndroid.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }*/
                }
            }
        });
    }


}
