package you.in.spark.energy.cividroid;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;


public class ContactView extends AppCompatActivity {

    private Uri contactUri;
    protected static String contactID;
    private String contactIDonDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_view);

        Intent intent = getIntent();
        contactID = intent.getData().getLastPathSegment();


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        CardPagerAdapter cardPagerAdapter = new CardPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(cardPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


        Cursor getName = getContentResolver().query(intent.getData(),new String[]{ContactsContract.Contacts.DISPLAY_NAME},null, null, null);
        getName.moveToFirst();
        collapsingToolbarLayout.setTitle(getName.getString(0));

        setSupportActionBar(toolbar);
        toolbar.setTitle("ZOL");

        getSupportActionBar().setTitle("LOL");


        Cursor c = getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE), null, CiviContract.RAW_CONTACT_ID_FIELD + "=?", new String[]{contactID}, null);

        if(c.moveToFirst()) {
            contactIDonDevice = c.getString(1);
            contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactIDonDevice));
            Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);
            ImageView ivContactPhoto = (ImageView) findViewById(R.id.ivContactPhoto);
            Glide.with(this).load(displayPhotoUri).asBitmap().centerCrop().into(ivContactPhoto);
        }

        c.close();



        /*SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String[] cols = sp.getStringSet(CiviContract.CONTACTS_FIELD_NAME_PREF,null).toArray(new String[]{});
        String[] fieldTitles = sp.getStringSet(CiviContract.CONTACTS_FIELD_TITLE_PREF, null).toArray(new String[]{});

        Cursor detailCursor = getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE), cols, CiviContract.RAW_CONTACT_ID_FIELD + "=?", new String[]{contactID}, null);
        if(detailCursor.moveToFirst()) {

            int curSize = cols.length;
            String detail = null;
            List<Pair<String,String>> detailPair = new ArrayList<>();
            //process result, remove blanks
            for(int i = 0; i<curSize;i++) {
                detail = detailCursor.getString(i);
                if(!detail.isEmpty()) {
                    detailPair.add(new Pair<>(fieldTitles[i],detail));
                }
            }

            if(detailPair.size()>0) {
                RecyclerContactsAdapter recyclerContactsAdapter = new RecyclerContactsAdapter(detailPair);
                RecyclerView content = (RecyclerView) findViewById(R.id.rvContent);
                content.setLayoutManager(new LinearLayoutManager(this));
                content.setAdapter(recyclerContactsAdapter);
            }
        }
        detailCursor.close();

*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_view,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_view_in_address_book:     Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(contactUri);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class CardPagerAdapter extends FragmentPagerAdapter {


        public CardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AboutFragment();
                case 1:
                    return new NotesFragment(contactID);
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.about_tab);
                case 1:
                    return getString(R.string.notes_tab);
                default:
                    return "";
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
