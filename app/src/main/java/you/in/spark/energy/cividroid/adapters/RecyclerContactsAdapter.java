package you.in.spark.energy.cividroid.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Photo;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import you.in.spark.energy.cividroid.CiviContract;
import you.in.spark.energy.cividroid.ContactView;
import you.in.spark.energy.cividroid.R.drawable;
import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;


public class RecyclerContactsAdapter extends Adapter<RecyclerContactsAdapter.MyViewHolder> implements OnClickListener {

    Cursor cursor;
    Context context;

    public RecyclerContactsAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
    }

    @Override
    public RecyclerContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.contact_item, parent, false);
        RecyclerContactsAdapter.MyViewHolder vh = new RecyclerContactsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerContactsAdapter.MyViewHolder holder, int position) {
        this.cursor.moveToPosition(position);
        if (Build.VERSION.SDK_INT >= 21) {
            holder.contactPhoto.setBackground(this.context.getDrawable(drawable.contact_gradient));
        } else {
            holder.contactPhoto.setBackground(this.context.getResources().getDrawable(drawable.contact_gradient));
        }
        Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, this.cursor.getLong(0));
        Uri displayPhotoUri = Uri.withAppendedPath(contactUri, Photo.DISPLAY_PHOTO);
        Glide.with(this.context).load(displayPhotoUri).asBitmap().centerCrop().fitCenter().into(holder.contactPhoto);
        holder.contactName.setText(this.cursor.getString(1));
        holder.v.setTag(position);
        holder.v.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        if (this.cursor == null) {
            return 0;
        } else {
            return this.cursor.getCount();
        }
    }

    @Override
    public void onClick(View v) {
        this.cursor.moveToPosition((Integer) v.getTag());
        Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, this.cursor.getLong(0));
        Uri displayPhotoUri = Uri.withAppendedPath(contactUri, Photo.DISPLAY_PHOTO);
        Intent intent = new Intent(this.context, ContactView.class);
        intent.putExtra(CiviContract.CONTACT_ID_FIELD, this.cursor.getString(0));
        intent.putExtra(CiviContract.PHOTO_URI, displayPhotoUri.toString());
        this.context.startActivity(intent);
    }

    public static class MyViewHolder extends ViewHolder {

        public ImageView contactPhoto;
        public TextView contactName;
        public View v;

        public MyViewHolder(View v) {
            super(v);
            this.contactPhoto = (ImageView) v.findViewById(id.contactPhoto);
            this.contactName = (TextView) v.findViewById(id.contactName);
            this.v = v;
        }
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
    }
}
