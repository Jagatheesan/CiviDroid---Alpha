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
import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;
import you.in.spark.energy.cividroid.adapters.RecyclerContactsAdapter;

public class CiviContacts extends Fragment implements LoaderCallbacks<Cursor> {

    private RecyclerContactsAdapter recyclerContactsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout.recycler_layout_fragment,container,false);
        RecyclerView content = (RecyclerView) v.findViewById(id.rvContent);
        content.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.recyclerContactsAdapter = new RecyclerContactsAdapter(null, this.getActivity());
        content.setAdapter(this.recyclerContactsAdapter);
        this.getActivity().getSupportLoaderManager().initLoader(1,null,this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.getActivity(),Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE),new String[]{CiviContract.CONTACT_ID_FIELD,CiviContract.CONTACT_TABLE_COLUMNS[3]},null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        DatabaseUtils.dumpCursor(data);
        this.recyclerContactsAdapter.swapCursor(data);
        this.recyclerContactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.recyclerContactsAdapter.swapCursor(null);
        this.recyclerContactsAdapter.notifyDataSetChanged();
    }
}
