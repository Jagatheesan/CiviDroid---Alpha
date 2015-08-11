package you.in.spark.energy.cividroid;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class CiviContacts extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CiviContactsCursorAdapter civiContactsCursorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_notes_fragment,container,false);
        ListView content = (ListView) v.findViewById(R.id.lvContent);
        //TextView empty = (TextView) v.findViewById(R.id.listEmpty);
        civiContactsCursorAdapter = new CiviContactsCursorAdapter(getActivity(),null,false);
        //content.setEmptyView(empty);
        content.setAdapter(civiContactsCursorAdapter);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.CONTACTS_FIELD_TABLE),new String[]{CiviContract.ID_COLUMN,CiviContract.CONTACT_ID_FIELD,"display_name"},null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        civiContactsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        civiContactsCursorAdapter.swapCursor(null);
    }
}
