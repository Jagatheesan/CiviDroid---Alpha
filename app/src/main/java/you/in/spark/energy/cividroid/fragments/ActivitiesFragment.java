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
import you.in.spark.energy.cividroid.adapters.ActivitiesRecyclerAdapter;


public class ActivitiesFragment extends Fragment implements LoaderCallbacks<Cursor>{

    private ActivitiesRecyclerAdapter activitiesRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout.recycler_layout_fragment,container,false);
        RecyclerView content = (RecyclerView) v.findViewById(id.rvContent);
        content.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.activitiesRecyclerAdapter = new ActivitiesRecyclerAdapter(null);
        content.setAdapter(this.activitiesRecyclerAdapter);
        this.getActivity().getSupportLoaderManager().initLoader(2,null,this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.getActivity(), Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.ACTIVITY_TABLE),new String[]{CiviContract.ACTIVITY_TABLE_COLUMNS[2],CiviContract.ACTIVITY_TABLE_COLUMNS[6],CiviContract.ACTIVITY_TABLE_COLUMNS[3],CiviContract.ACTIVITY_TABLE_COLUMNS[5],CiviContract.ACTIVITY_TABLE_COLUMNS[4]},CiviContract.ACTIVITY_TABLE_COLUMNS[10]+" IS NULL",null,CiviContract.ACTIVITY_TABLE_COLUMNS[3]+" DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        DatabaseUtils.dumpCursor(data);
        this.activitiesRecyclerAdapter.swapCursor(data);
        this.activitiesRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.activitiesRecyclerAdapter.swapCursor(null);
        this.activitiesRecyclerAdapter.notifyDataSetChanged();
    }
}
