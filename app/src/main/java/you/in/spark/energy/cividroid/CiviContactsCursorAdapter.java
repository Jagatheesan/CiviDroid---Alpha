package you.in.spark.energy.cividroid;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class CiviContactsCursorAdapter extends CursorAdapter{

    private LayoutInflater inflater;
    private Context context;

    public CiviContactsCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.contact_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView contactPhoto = (ImageView) view.findViewById(R.id.contactPhoto);
        TextView contactName = (TextView) view.findViewById(R.id.contactName);


        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(cursor.getString(1)));
        Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);
        Glide.with(context).load(displayPhotoUri).asBitmap().centerCrop().into(contactPhoto);

        contactName.setText(cursor.getString(2));
    }
}
