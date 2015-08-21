package you.in.spark.energy.cividroid.fragments;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import you.in.spark.energy.cividroid.CiviContract;
import you.in.spark.energy.cividroid.ContactView;
import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;
import you.in.spark.energy.cividroid.adapters.RecyclerNotesAdapter;


public class NotesFragment extends Fragment implements LoaderCallbacks<Cursor>{


    private RecyclerNotesAdapter recyclerNotesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout.recycler_layout_fragment,container,false);
        RecyclerView content = (RecyclerView) v.findViewById(id.rvContent);
        content.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.recyclerNotesAdapter = new RecyclerNotesAdapter(null);
        content.setAdapter(this.recyclerNotesAdapter);
        this.getActivity().getSupportLoaderManager().initLoader(3,null,this);
        return v;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Cursor cursor = this.getActivity().getContentResolver().query(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE),new String[]{CiviContract.CONTACT_TABLE_COLUMNS[0]},CiviContract.CONTACT_ID_FIELD+"=?",new String[]{ContactView.contactID},null);
        String civiId = "-1";
        if(cursor.moveToFirst()) {
            civiId = cursor.getString(0);
        }
        cursor.close();
        return new CursorLoader(this.getActivity(), Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.ACTIVITY_TABLE),new String[]{CiviContract.ACTIVITY_TABLE_COLUMNS[3],CiviContract.ACTIVITY_TABLE_COLUMNS[4],CiviContract.ACTIVITY_TABLE_COLUMNS[6]},CiviContract.ACTIVITY_TABLE_COLUMNS[10]+"=?",new String[]{civiId},CiviContract.ACTIVITY_TABLE_COLUMNS[3]+" DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        DatabaseUtils.dumpCursor(data);
        this.recyclerNotesAdapter.swapCursor(data);
        this.recyclerNotesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.recyclerNotesAdapter.swapCursor(null);
        this.recyclerNotesAdapter.notifyDataSetChanged();
    }
}
