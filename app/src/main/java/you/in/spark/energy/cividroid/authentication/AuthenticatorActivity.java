package you.in.spark.energy.cividroid.authentication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import you.in.spark.energy.cividroid.CiviContract;
import you.in.spark.energy.cividroid.DBHelper;
import you.in.spark.energy.cividroid.FieldSelectionActivity;
import you.in.spark.energy.cividroid.R.drawable;
import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;
import you.in.spark.energy.cividroid.sync.SyncAdapter;

public class AuthenticatorActivity extends AppCompatActivity {

    private Boolean callFromActivity = false;
    private static final String TAG = "AuthenticatorActivity";
    private EditText etSiteKey, etApiKey, etWebsiteUrl, etSourceContactID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //clearing database incase user came to the screen by pressing 'Reset Profile'
        clearDatabase();

        this.callFromActivity = this.getIntent().getBooleanExtra(CiviContract.CALL_FROM_ACTIVITY, false);

        this.setContentView(layout.authenticator_activity);

        this.etSiteKey = (EditText) this.findViewById(id.etSiteKey);
        this.etApiKey = (EditText) this.findViewById(id.etApiKey);
        this.etWebsiteUrl = (EditText) this.findViewById(id.etWebsiteUrl);
        this.etSourceContactID = (EditText) this.findViewById(id.etSourceContactID);

        this.etSiteKey.setText("civisitekey");
        this.etApiKey.setText("superapikey");
        this.etWebsiteUrl.setText("http://densecloud.koding.io/wordpress/wp-content/plugins/civicrm/civicrm/extern");
        this.etSourceContactID.setText("2");

        if (savedInstanceState != null) {
            this.etSiteKey.setText(savedInstanceState.getString(CiviContract.SITE_KEY));
            this.etApiKey.setText(savedInstanceState.getString(CiviContract.API_KEY));
            this.etWebsiteUrl.setText(savedInstanceState.getString(CiviContract.WEBSITE_URL));
            this.etSourceContactID.setText(savedInstanceState.getString(CiviContract.SOURCE_CONTACT_ID));

        }

        FloatingActionButton fabSaveKeys = (FloatingActionButton) this.findViewById(id.fabSaveKeys);
        if (VERSION.SDK_INT >= 21) {
            fabSaveKeys.setImageDrawable(this.getResources().getDrawable(drawable.ic_save_white_24dp, null));
        } else {
            fabSaveKeys.setImageDrawable(this.getResources().getDrawable(drawable.ic_save_white_24dp));
        }

        fabSaveKeys.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SyncAdapter.isConnected(AuthenticatorActivity.this)) {
                    if (!TextUtils.isEmpty(AuthenticatorActivity.this.etApiKey.getText()) && !TextUtils.isEmpty(AuthenticatorActivity.this.etSiteKey.getText())) {
                        Intent intent = new Intent(AuthenticatorActivity.this, FieldSelectionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(CiviContract.SITE_KEY, AuthenticatorActivity.this.etSiteKey.getText().toString());
                        intent.putExtra(CiviContract.API_KEY, AuthenticatorActivity.this.etApiKey.getText().toString());
                        intent.putExtra(CiviContract.WEBSITE_URL, AuthenticatorActivity.this.etWebsiteUrl.getText().toString());
                        intent.putExtra(CiviContract.SOURCE_CONTACT_ID, AuthenticatorActivity.this.etSourceContactID.getText().toString());
                        AuthenticatorActivity.this.startActivity(intent);
                        AuthenticatorActivity.this.finish();
                    }
                } else {
                    Toast.makeText(AuthenticatorActivity.this, "No Internet Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    
    private void clearDatabase() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM " + CiviContract.CONTACTS_FIELD_TABLE);
        database.execSQL("DELETE FROM " + CiviContract.ACTIVITY_TABLE);
        database.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CiviContract.SITE_KEY, this.etSiteKey.getText().toString());
        outState.putString(CiviContract.API_KEY, this.etApiKey.getText().toString());
        outState.putString(CiviContract.WEBSITE_URL, this.etWebsiteUrl.getText().toString());
        outState.putString(CiviContract.SOURCE_CONTACT_ID, this.etSourceContactID.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
