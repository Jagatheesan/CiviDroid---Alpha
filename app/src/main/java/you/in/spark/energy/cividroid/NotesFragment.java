package you.in.spark.energy.cividroid;

import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class NotesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private NotesCursorAdapter notesCursorAdapter;
    private String contactID;

    public NotesFragment() {}

    public NotesFragment(String contactID) {
        this.contactID = contactID;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_notes_fragment,container,false);
        ListView content = (ListView) v.findViewById(R.id.lvContent);
        notesCursorAdapter = new NotesCursorAdapter(getActivity(),null,false);
        content.setAdapter(notesCursorAdapter);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        Log.v("NotesFragment", "created");
        return v;
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.NOTES_TABLE),new String[]{CiviContract.ID_COLUMN,CiviContract.NOTES_DATE_COLUMN,CiviContract.NOTES_DURATION_COLUMN,CiviContract.NOTES_COLUMN},CiviContract.CONTACT_ID_FIELD+"=?",new String[]{contactID},null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        Log.v("Notesfragment","cursor size: "+data.getCount());
        notesCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        notesCursorAdapter.swapCursor(null);
    }
}
