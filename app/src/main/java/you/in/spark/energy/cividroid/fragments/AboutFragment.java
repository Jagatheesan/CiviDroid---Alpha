package you.in.spark.energy.cividroid.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

import you.in.spark.energy.cividroid.CiviContract;
import you.in.spark.energy.cividroid.ContactView;
import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;
import you.in.spark.energy.cividroid.adapters.RecyclerAboutContactAdapter;

public class AboutFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout.recycler_layout_fragment, container, false);


        Cursor detailCursor = this.getActivity().getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE), CiviContract.CONTACT_TABLE_COLUMNS, CiviContract.CONTACT_ID_FIELD + "=?", new String[]{ContactView.contactID}, null);
        if (detailCursor.moveToFirst()) {
            ContactView.collapsingToolbarLayout.setTitle(detailCursor.getString(3));


            String detail = null;
            List<Pair<String, String>> detailPair = new ArrayList<>();

            //process result, remove blanks
            int len = CiviContract.CONTACT_FIELD_NAMES.length;
            for (int i = 1; i < len; i++) {
                detail = detailCursor.getString(i);
                if (detail != null && !detail.isEmpty()) {
                    detailPair.add(new Pair<>(CiviContract.CONTACT_FIELD_NAMES[i], detail));
                }
            }


            if (detailPair.size() > 0) {
                RecyclerAboutContactAdapter recyclerAboutContactAdapter = new RecyclerAboutContactAdapter(detailPair);
                RecyclerView content = (RecyclerView) v.findViewById(id.rvContent);
                content.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                content.setAdapter(recyclerAboutContactAdapter);
            }
        }
        detailCursor.close();
        return v;
    }
}
