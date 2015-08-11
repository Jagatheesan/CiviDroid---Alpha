package you.in.spark.energy.cividroid;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_about_fragment,container,false);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String[] cols = sp.getStringSet(CiviContract.CONTACTS_FIELD_NAME_PREF,null).toArray(new String[]{});
        String[] fieldTitles = sp.getStringSet(CiviContract.CONTACTS_FIELD_TITLE_PREF, null).toArray(new String[]{});

        Cursor detailCursor = getActivity().getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE), cols, CiviContract.RAW_CONTACT_ID_FIELD + "=?", new String[]{ContactView.contactID}, null);
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
                RecyclerView content = (RecyclerView) v.findViewById(R.id.rvContent);
                content.setLayoutManager(new LinearLayoutManager(getActivity()));
                content.setAdapter(recyclerContactsAdapter);
            }
        }
        detailCursor.close();
        return v;
    }
}
