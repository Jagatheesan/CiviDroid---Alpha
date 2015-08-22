package you.in.spark.energy.cividroid.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;

public class RecyclerNotesAdapter extends Adapter<RecyclerNotesAdapter.MyViewHolder> {

    Cursor cursor;

    public RecyclerNotesAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public RecyclerNotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.note_layout, parent, false);
        RecyclerNotesAdapter.MyViewHolder vh = new RecyclerNotesAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerNotesAdapter.MyViewHolder holder, int position) {
        this.cursor.moveToPosition(position);

        holder.noteDate.setText("Conversation date: " + this.cursor.getString(0));

        Long lDuration = this.cursor.getLong(1);
        holder.noteDuration.setText("Duration: " + String.format("%d hr, %d min, %d sec",
                TimeUnit.SECONDS.toHours(lDuration),
                TimeUnit.SECONDS.toMinutes(lDuration - TimeUnit.HOURS.toSeconds(TimeUnit.SECONDS.toHours(lDuration))),
                lDuration - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(lDuration - TimeUnit.HOURS.toSeconds(TimeUnit.SECONDS.toHours(lDuration))))
        ));
        holder.noteText.setText(this.cursor.getString(2));
    }

    @Override
    public int getItemCount() {
        if (this.cursor == null) {
            return 0;
        } else {
            return this.cursor.getCount();
        }
    }

    public static class MyViewHolder extends ViewHolder {

        public TextView noteDate;
        public TextView noteDuration;
        public TextView noteText;

        public MyViewHolder(View v) {
            super(v);
            this.noteDate = (TextView) v.findViewById(id.noteDate);
            this.noteDuration = (TextView) v.findViewById(id.noteDuration);
            this.noteText = (TextView) v.findViewById(id.noteText);

        }
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
    }
}