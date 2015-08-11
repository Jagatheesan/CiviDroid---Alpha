package you.in.spark.energy.cividroid;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import you.in.spark.energy.cividroid.authentication.AuthenticatorActivity;


public class CiviAndroid extends AppCompatActivity {

    public static final String TAG = "CiviAndroid";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkAuthAndSetUp();
        syncNow(new Account(CiviContract.ACCOUNT, CiviContract.ACCOUNT_TYPE));

       /* setContentView(R.layout.cividroid_start_screen);
        ViewPager viewPager = (ViewPager) findViewById(R.id.civiPager);
        CardPagerAdapter cardPagerAdapter = new CardPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(cardPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.civiTablayout);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.civiToolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setIcon(R.drawable.civilogo);

        //toolbar.setTitle("Cividroid");





        //site key & api key already entered



        //Account account = CreateSyncAccount(this);
        //syncNow(account);


        /*setContentView(R.layout.authenticator_activity);

        FloatingActionButton fabSaveKeys = (FloatingActionButton) findViewById(R.id.fabSaveKeys);
        if (Build.VERSION.SDK_INT >= 21) {
            fabSaveKeys.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_white_24dp, null));
        } else {
            fabSaveKeys.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_white_24dp));
        }


        //final TextView name = (TextView) findViewById(R.id.name);

        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).
                    setEndpoint("http://10.0.3.2:16021").build();

        ICiviApi iCiviApi = adapter.create(ICiviApi.class);

        iCiviApi.getContacts(new Callback<QueryResult>() {
            @Override
            public void success(QueryResult queryResult, Response response) {
                Toast.makeText(CiviAndroid.this, "Success", Toast.LENGTH_LONG).show();
                //name.setText(contacts.getValues().get("2").getDisplayName());
                Log.v(TAG, "Pass: " + response.toString());
                Log.v(TAG,"Name: "+contacts.toString());
               // Log.v(TAG,"Value size: "+contacts.getValues().get("1").getValues().toString());
                //Log.v(TAG,"*"+contacts.getValues().get("2").getDisplayName()+"*");
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CiviAndroid.this,"Fail"+error.getMessage(),Toast.LENGTH_LONG).show();
                Log.v(TAG,"Fail: "+error.getMessage());
            }
        });*/
    }

    private void checkAuthAndSetUp() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String apiKey = sp.getString(CiviContract.API_KEY, null);
        String siteKey = sp.getString(CiviContract.SITE_KEY,null);
        if(apiKey==null && siteKey==null) {
            Intent intent = new Intent(this, AuthenticatorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra(CiviContract.CALL_FROM_ACTIVITY,true);
            startActivity(intent);
            finish();
        }
    }

    private void syncNow(Account account) {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        ContentResolver.requestSync(account, CiviContract.AUTHORITY, settingsBundle);
    }

    private class CardPagerAdapter extends FragmentPagerAdapter {


        public CardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CiviContacts();
                case 1:
                    return new CiviActivities();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.civi_contacts_tab);
                case 1:
                    return getString(R.string.civi_activities_tab);
                default:
                    return "";
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                CiviContract.ACCOUNT, CiviContract.ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }

        return newAccount;
    }


}
