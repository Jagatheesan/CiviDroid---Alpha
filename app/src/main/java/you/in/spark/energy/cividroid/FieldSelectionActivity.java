package you.in.spark.energy.cividroid;

import android.accounts.Account;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.AggregationExceptions;
import android.provider.ContactsContract.CommonDataKinds.Contactables;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Pair;
import android.view.View;

import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;
import you.in.spark.energy.cividroid.R.string;
import you.in.spark.energy.cividroid.api.ICiviApi;
import you.in.spark.energy.cividroid.entities.Contact;
import you.in.spark.energy.cividroid.fragments.ContactsSubTypeSelectionFragment;


public class FieldSelectionActivity extends AppCompatActivity {

    public static String apiKey, siteKey, websiteUrl, sourceContactID;
    public static LinearLayout waitScreen;
    public static ImageView civiRotate;
    private int waitTime;
    private int splitTime;
    public static int fragmentStatus;


    private static final int[] types = new int[3];
    private FieldSelectionActivity.ViewPagerAdapter viewPagerAdapter;
    private final Vector<String>[] subTypes = new Vector[3];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.field_selection);

        FieldSelectionActivity.waitScreen = (LinearLayout) this.findViewById(id.waitScreen);
        final TextView waitText = (TextView) this.findViewById(id.waitText);
        FieldSelectionActivity.civiRotate = (ImageView) this.findViewById(id.civilogoRotate);

        final Animation rotater = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //rotater.setInterpolator(new AccelerateInterpolator());

        rotater.setDuration(3000);
        rotater.setRepeatCount(Animation.INFINITE);
        rotater.setFillAfter(true);

        FieldSelectionActivity.civiRotate.setAnimation(rotater);

        Intent callerIntent = this.getIntent();
        FieldSelectionActivity.siteKey = callerIntent.getStringExtra(CiviContract.SITE_KEY);
        FieldSelectionActivity.apiKey = callerIntent.getStringExtra(CiviContract.API_KEY);
        FieldSelectionActivity.websiteUrl = callerIntent.getStringExtra(CiviContract.WEBSITE_URL);
        FieldSelectionActivity.sourceContactID = callerIntent.getStringExtra(CiviContract.SOURCE_CONTACT_ID);

        ViewPager viewPager = (ViewPager) this.findViewById(id.fieldPager);
        viewPager.setOffscreenPageLimit(2);
        this.viewPagerAdapter = new FieldSelectionActivity.ViewPagerAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(this.viewPagerAdapter);

        TabLayout tabLayout = (TabLayout) this.findViewById(id.fieldTabLayout);
        tabLayout.setupWithViewPager(viewPager);


        Button fabFields = (Button) this.findViewById(id.fabFields);
        fabFields.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FieldSelectionActivity.waitScreen.setVisibility(View.VISIBLE);
                FieldSelectionActivity.civiRotate.setAnimation(rotater);
                waitText.setText(FieldSelectionActivity.this.getString(string.syncing_contacts));

                final Vector<String>[] labels = FieldSelectionActivity.this.viewPagerAdapter.getLabels();
                FieldSelectionActivity.this.waitTime = labels[0].size() + labels[1].size();
                FieldSelectionActivity.this.splitTime = labels[0].size();


                //Logging
                for (Vector<String> oneLabels : labels) {
                    for (String value : oneLabels) {
                    }
                }

                //perform recursive synchronous sync in background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RestAdapter adapter = new Builder().setLogLevel(LogLevel.FULL).
                                setEndpoint(FieldSelectionActivity.websiteUrl).build();

                        ICiviApi iCiviApi = adapter.create(ICiviApi.class);

                        CiviAndroid.createSyncAccount(FieldSelectionActivity.this);
                        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

                        Map<String, String> fields = new HashMap<>();
                        String contactType, contactSubtype;



                        Map<Integer, Pair<Integer,Integer>> toSyncPositions = new HashMap<Integer, Pair<Integer, Integer>>();

                        //get All phone numbers
                        Map<String, Pair<Integer,Integer>> phoneNumbers = new HashMap<String, Pair<Integer, Integer>>();

                        Cursor phones = FieldSelectionActivity.this.getContentResolver().query(Phone.CONTENT_URI, new String[]{Phone.NUMBER, Phone.RAW_CONTACT_ID, Phone.CONTACT_ID}, null, null, null);
                        while (phones.moveToNext()) {
                            try {
                                String rawNumber = "" + phones.getString(0);
                                String normalizedNumber = "";
                                if (!rawNumber.isEmpty()) {
                                    if (rawNumber.charAt(0) != '+') {
                                        //manual normalize
                                        normalizedNumber = rawNumber.replaceAll("\\D", "");

                                    } else {
                                        normalizedNumber = String.valueOf(phoneUtil.parse(rawNumber, "").getNationalNumber());
                                    }
                                }
                                phoneNumbers.put(normalizedNumber, new Pair<Integer, Integer>(phones.getInt(1),phones.getInt(2)));
                            } catch (NumberParseException e) {
                            }

                        }
                        phones.close();
                        int phoneCount = phoneNumbers.size();

                        //get All email addresses
                        Map<String, Pair<Integer,Integer>> emailAddresses = new HashMap<String, Pair<Integer, Integer>>();
                        Cursor eAds = FieldSelectionActivity.this.getContentResolver().query(Email.CONTENT_URI, new String[]{Email.ADDRESS, Contactables.RAW_CONTACT_ID, Email.CONTACT_ID}, null, null, null);
                        while (eAds.moveToNext()) {
                            emailAddresses.put(eAds.getString(0), new Pair<Integer, Integer>(eAds.getInt(1),eAds.getInt(2)));
                        }
                        eAds.close();
                        int emailCount = emailAddresses.size();
                        Contact contacts;


                        JsonObject jsonObject;

                        for (int i = 0; i < FieldSelectionActivity.this.waitTime; i++) {

                            emailCount = emailAddresses.size();
                            phoneCount = phoneNumbers.size();

                            //resetting for fresh loop use
                            toSyncPositions.clear();
                            fields.clear();
                            contactType=null;
                            contactSubtype=null;

                            fields.put("key", FieldSelectionActivity.siteKey);
                            fields.put("api_key", FieldSelectionActivity.apiKey);
                            jsonObject = new JsonObject();
                            jsonObject.addProperty("sequential", "1");
                            if (i < FieldSelectionActivity.this.splitTime) {
                                contactType = labels[0].get(i);
                                jsonObject.addProperty("contact_type",contactType);
                            } else {
                                contactSubtype = labels[1].get(Math.abs(FieldSelectionActivity.this.splitTime - i));
                                jsonObject.addProperty("contact_sub_type", contactSubtype);

                            }
                            fields.put("json", jsonObject.toString());


                            contacts = iCiviApi.getContacts(fields);

                            if (contacts != null) {
                                if (contacts.getIsError() != 1) {

                                    int contactSize = contacts.getValues().size();

                                    if (contactSize > 0) {


                                        //running loop until emailCount and phoneCount exhaust or the responseList exhausts

                                        for (int c = 0; c < contactSize; c++) {
                                            if (emailCount == 0 && phoneCount == 0) {
                                                break;
                                            }

                                            if (phoneCount > 0) {
                                                try {
                                                    String civiRawNumber = "" + contacts.getValues().get(c).getPhone();
                                                    String civiNormalizedNumber = "";
                                                    if (!civiRawNumber.isEmpty()) {
                                                        if (civiRawNumber.charAt(0) != '+') {
                                                            //manual normalize
                                                            civiNormalizedNumber = civiRawNumber.replaceAll("\\D", "");

                                                        } else {
                                                            civiNormalizedNumber = String.valueOf(phoneUtil.parse(civiRawNumber, "").getNationalNumber());
                                                        }
                                                    }
                                                    if (phoneNumbers.containsKey(civiNormalizedNumber)) {
                                                        phoneCount--;
                                                        toSyncPositions.put(c, new Pair<Integer, Integer>(phoneNumbers.get(civiNormalizedNumber).first,phoneNumbers.get(civiNormalizedNumber).second));
                                                        c++;
                                                        continue;
                                                    }
                                                } catch (NumberParseException e) {
                                                }
                                            }

                                            if (emailCount > 0) {
                                                String email = contacts.getValues().get(c).getEmail();
                                                if (emailAddresses.containsKey(email)) {
                                                    emailCount--;
                                                    toSyncPositions.put(c, new Pair<Integer, Integer>(emailAddresses.get(email).first,emailAddresses.get(email).second));
                                                    c++;
                                                    continue;
                                                }
                                            }
                                        }


                                        for (Entry<Integer, Pair<Integer, Integer>> entry : toSyncPositions.entrySet()) {
                                            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                                            ops.add(ContentProviderOperation.newInsert(FieldSelectionActivity.addCallerIsSyncAdapterParameter(RawContacts.CONTENT_URI, true))
                                                    .withValue(RawContacts.ACCOUNT_NAME, CiviContract.ACCOUNT)
                                                    .withValue(RawContacts.ACCOUNT_TYPE, CiviContract.ACCOUNT_TYPE)
                                                    .withValue(RawContacts.AGGREGATION_MODE, RawContacts.AGGREGATION_MODE_DISABLED)
                                                    .build());

                                            ops.add(ContentProviderOperation.newInsert(FieldSelectionActivity.addCallerIsSyncAdapterParameter(Data.CONTENT_URI, true))
                                                    .withValueBackReference(Data.RAW_CONTACT_ID, 0)
                                                    .withValue(Data.MIMETYPE, "vnd.android.cursor.item/you.in.spark.energy.cividroid.profile")
                                                    .withValue(Data.DATA2, "Civicrm contact")
                                                    .withValue(Data.DATA3, "Open contact in Cividroid")
                                                    .build());

                                            try {
                                                ContentProviderResult[] results = FieldSelectionActivity.this.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                                                long contactID = ContentUris.parseId(results[0].uri);
                                                ContentValues cv = new ContentValues();
                                                cv.put(AggregationExceptions.TYPE, AggregationExceptions.TYPE_KEEP_TOGETHER);
                                                cv.put(AggregationExceptions.RAW_CONTACT_ID1, contactID);
                                                cv.put(AggregationExceptions.RAW_CONTACT_ID2, entry.getValue().first);

                                                //insert other values


                                                int aggregationResult = FieldSelectionActivity.this.getContentResolver().update(AggregationExceptions.CONTENT_URI, cv, null, null);

                                                Cursor ag = FieldSelectionActivity.this.getContentResolver().query(Contacts.CONTENT_URI,null,null,null,null);
                                                DatabaseUtils.dumpCursor(ag);
                                                ag.close();

                                                ContentValues vals = new ContentValues();


                                                vals.put(CiviContract.CONTACT_ID_FIELD, String.valueOf(entry.getValue().second));

                                                ContentValues allValues = contacts.getValues().get(entry.getKey()).getAllValues(contactType,contactSubtype);
                                                vals.putAll(allValues);


                                                Uri uri = FieldSelectionActivity.this.getContentResolver().insert(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE), vals);
                                                FieldSelectionActivity.this.getContentResolver().notifyChange(uri,null);
                                            } catch (Exception e) {
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(FieldSelectionActivity.this);
                        sp.edit().putString(CiviContract.API_KEY, FieldSelectionActivity.apiKey).apply();
                        sp.edit().putString(CiviContract.SITE_KEY, FieldSelectionActivity.siteKey).apply();
                        sp.edit().putString(CiviContract.WEBSITE_URL, FieldSelectionActivity.websiteUrl).apply();
                        sp.edit().putString(CiviContract.SOURCE_CONTACT_ID, FieldSelectionActivity.sourceContactID).apply();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            try {
                                SyncRequest request = new SyncRequest.Builder().syncPeriodic(1800,600).setSyncAdapter(new Account(CiviContract.ACCOUNT,CiviContract.ACCOUNT_TYPE),"com.android.contacts").build();
                                ContentResolver.requestSync(request);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ContentResolver.addPeriodicSync(new Account(CiviContract.ACCOUNT,CiviContract.ACCOUNT_TYPE), "com.android.contacts", new Bundle(), 1800);
                        }
                        ContentResolver.setSyncAutomatically(new Account(CiviContract.ACCOUNT,CiviContract.ACCOUNT_TYPE), "com.android.contacts", true);
                        Intent intent = new Intent(FieldSelectionActivity.this, CiviAndroid.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        FieldSelectionActivity.this.startActivity(intent);
                        FieldSelectionActivity.this.finish();
                    }
                }).start();

            }
        });

    }

    private static Uri addCallerIsSyncAdapterParameter(Uri uri, boolean isSyncOperation) {
        if (isSyncOperation) {
            return uri.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true")
                    .build();
        }
        return uri;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final ContactsSubTypeSelectionFragment[] fragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ContactsSubTypeSelectionFragment[3];
        }

        @Override
        public Fragment getItem(int position) {
            this.fragments[position] = new ContactsSubTypeSelectionFragment(position);
            return this.fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return FieldSelectionActivity.this.getString(string.individual_contacts_tab);
                case 1:
                    return FieldSelectionActivity.this.getString(string.household_contacts_tab);
                case 2:
                    return FieldSelectionActivity.this.getString(string.organization_contacts_tab);
                default:
                    return "";
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


        public Vector<String>[] getLabels() {
            Vector<String> contactLabels = new Vector<>();
            Vector<String> subtypeLabels = new Vector<>();
            for (int i = 0; i < 3; i++) {
                if (this.fragments[i].selectedChoice == 0) {
                    contactLabels.add(this.getPageTitle(i).toString());
                } else if (this.fragments[i].selectedChoice == 2) {
                    subtypeLabels.addAll(this.fragments[i].contactSubtypeAdapter.getCheckedLabels());
                }
            }

            return new Vector[]{contactLabels, subtypeLabels};
        }

    }


}
