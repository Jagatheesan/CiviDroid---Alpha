package you.in.spark.energy.cividroid;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Contactables;
import android.provider.ContactsContract.Contacts;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;
import you.in.spark.energy.cividroid.R.string;
import you.in.spark.energy.cividroid.fragments.AboutFragment;
import you.in.spark.energy.cividroid.fragments.NotesFragment;

public class ContactView extends AppCompatActivity {

    public static String contactID;
    private Uri contactUri;
    public static CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.contact_view);

        ContactView.collapsingToolbarLayout = (CollapsingToolbarLayout) this.findViewById(id.collapsingToolbarLayout);
        Toolbar toolbar = (Toolbar) this.findViewById(id.toolbar);


        this.setSupportActionBar(toolbar);

        ImageView ivContactPhoto = (ImageView) this.findViewById(id.ivContactPhoto);


        Intent intent = this.getIntent();

        if (intent.getData() != null) {
            Cursor detailExtractor = this.getContentResolver().query(intent.getData(), new String[]{Contacts.PHOTO_URI, Contactables.CONTACT_ID}, null, null, null);
            if (detailExtractor.moveToFirst()) {
                Glide.with(this).load(Uri.parse("" + detailExtractor.getString(0))).asBitmap().centerCrop().into(ivContactPhoto);
                ContactView.contactID = detailExtractor.getString(1);
            }
            detailExtractor.close();
        } else {
            ContactView.contactID = intent.getStringExtra(CiviContract.CONTACT_ID_FIELD);
            Glide.with(this).load(Uri.parse(intent.getStringExtra(CiviContract.PHOTO_URI))).asBitmap().centerCrop().into(ivContactPhoto);
        }

        this.contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.valueOf(ContactView.contactID));


        ViewPager viewPager = (ViewPager) this.findViewById(id.viewPager);
        ContactView.CardPagerAdapter cardPagerAdapter = new ContactView.CardPagerAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(cardPagerAdapter);

        TabLayout tabLayout = (TabLayout) this.findViewById(id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_contact_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case id.action_view_in_address_book:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(this.contactUri);
                this.startActivity(intent);
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
                    return new NotesFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return ContactView.this.getString(string.about_tab);
                case 1:
                    return ContactView.this.getString(string.notes_tab);
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
