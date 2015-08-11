package you.in.spark.energy.cividroid;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Set;
import java.util.Vector;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import you.in.spark.energy.cividroid.api.ICiviApi;
import you.in.spark.energy.cividroid.authentication.AuthenticatorActivity;
import you.in.spark.energy.cividroid.entities.QueryResult;


public class FieldSelectionActivity extends AppCompatActivity {

    private Boolean manual;
    private Boolean callFromContactsField;
    private static final String TAG = "FieldSelection";
    private Vector<Integer> fieldsSelectedForContacts;
    private RecyclerAdapter recyclerAdapter;
    private TextView title;
    private TextView tvLoadingFields;
    private RecyclerView rvFields;
    private FloatingActionButton fabFields;
    private ICiviApi iCiviApi;
    private static Set<String> selectedContactFieldsTitle, selectedContactFieldsName, selectedActivityFieldsTitle, selectedActivityFieldsName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_selection);
        Intent callerIntent = getIntent();
        final String siteKey = callerIntent.getStringExtra(CiviContract.SITE_KEY);
        final String apiKey = callerIntent.getStringExtra(CiviContract.API_KEY);
        callFromContactsField = callerIntent.getBooleanExtra(CiviContract.CALL_FROM_CONTACTS_FIELD,false);

        title = (TextView) findViewById(R.id.tvFieldSelectionTitle);
        title.setText(getString(R.string.contacts_title));

        //disable Fab color until load
         fabFields = (FloatingActionButton) findViewById(R.id.fabFields);
         rvFields = (RecyclerView) findViewById(R.id.rvFields);
         tvLoadingFields = (TextView) findViewById(R.id.tvLoadingFields);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvFields.setLayoutManager(mLayoutManager);

        //lvFields.(getLayoutInflater().inflate(R.layout.listview_empty, null));
        Log.v(TAG,"restAdapter activated!");

        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).
                setEndpoint("http://10.0.2.2:81").build();

        iCiviApi = adapter.create(ICiviApi.class);

        if(!callFromContactsField) {
            iCiviApi.getContactsFields(apiKey, siteKey, new Callback<QueryResult>() {
                @Override
                public void success(QueryResult queryResult, Response response) {
                    Log.v(TAG, "success" + response.toString());
                    if (queryResult.getIsError() == 0) {
                        Log.v(TAG, "no error!");
                        // lvFields.setAdapter();
                        tvLoadingFields.setVisibility(View.GONE);
                        recyclerAdapter = new RecyclerAdapter(queryResult.getListedPairValues());
                        rvFields.setAdapter(recyclerAdapter);
                        //enable fab color here
                        fabFields.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Set<String>[] values = recyclerAdapter.getTitleNameFields();
                                selectedContactFieldsTitle = values[0];
                                selectedContactFieldsName = values[1];
                                values=null;
                                Intent intent = getIntent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                intent.putExtra(CiviContract.CALL_FROM_CONTACTS_FIELD, true);
                                startActivity(intent);
                                //finish();
                            }
                        });
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.v(TAG, error.toString());
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                fabFields.setImageDrawable(getResources().getDrawable(R.drawable.ic_navigate_next_white_24dp, null));
            } else {
                fabFields.setImageDrawable(getResources().getDrawable(R.drawable.ic_navigate_next_white_24dp));
            }
            title.setText(getString(R.string.activities_title));
            iCiviApi.getActivityFields(apiKey, siteKey, new Callback<QueryResult>() {
                @Override
                public void success(QueryResult queryResult, Response response) {
                    Log.v(TAG,""+response.toString());
                    tvLoadingFields.setVisibility(View.GONE);
                    recyclerAdapter = new RecyclerAdapter(queryResult.getListedPairValues());
                    rvFields.setAdapter(recyclerAdapter);
                    fabFields.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(FieldSelectionActivity.this);
                            sp.edit().putString(CiviContract.SITE_KEY,siteKey).apply();
                            sp.edit().putString(CiviContract.API_KEY,apiKey).apply();
                            Account newAccount = new Account(CiviContract.ACCOUNT,CiviContract.ACCOUNT_TYPE);
                            AccountManager accountManager = (AccountManager) FieldSelectionActivity.this.getSystemService(ACCOUNT_SERVICE);
                            accountManager.addAccountExplicitly(newAccount, null, null);
                            ContentResolver.setSyncAutomatically(newAccount, ContactsContract.AUTHORITY, true);
                            Set<String>[] values = recyclerAdapter.getTitleNameFields();
                            selectedActivityFieldsTitle = values[0];
                            selectedActivityFieldsName = values[1];
                            values=null;


                            sp.edit().putStringSet(CiviContract.CONTACTS_FIELD_TITLE_PREF,selectedContactFieldsTitle).apply();
                            sp.edit().putStringSet(CiviContract.CONTACTS_FIELD_NAME_PREF,selectedContactFieldsName).apply();
                            sp.edit().putStringSet(CiviContract.ACTIVITY_FIELD_TITLE_PREF,selectedActivityFieldsTitle).apply();
                            sp.edit().putStringSet(CiviContract.ACTIVITY_FIELD_NAME_PREF,selectedActivityFieldsName).apply();

                            //getContentResolver().bulkInsert(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE), selectedContactFields);
                            //getContentResolver().bulkInsert(Uri.parse(CiviContract.CONTENT_URI+"/"+CiviContract.ACTIVITY_FIELD_TABLE),selectedActivityFields);
                            Intent intent = new Intent(FieldSelectionActivity.this,CiviAndroid.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                        }
                    });

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.v(TAG,error.toString());
                }
            });
        }

    }

}
