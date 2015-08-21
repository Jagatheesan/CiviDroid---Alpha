package you.in.spark.energy.cividroid.adapters;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;


public class ActivitiesRecyclerAdapter extends Adapter<ActivitiesRecyclerAdapter.MyViewHolder> {

    Cursor cursor;

    public ActivitiesRecyclerAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ActivitiesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.activity_detail_layout, parent, false);
        ActivitiesRecyclerAdapter.MyViewHolder vh = new ActivitiesRecyclerAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ActivitiesRecyclerAdapter.MyViewHolder holder, int position) {
        this.cursor.moveToPosition(position);
        DatabaseUtils.dumpCurrentRow(this.cursor);
        String subject, details, location, duration, reminder;
        subject = this.cursor.getString(0);
        details = this.cursor.getString(1);
        reminder = this.cursor.getString(2);
        location = this.cursor.getString(3);
        duration = this.cursor.getString(4);
        if(subject!=null){
            holder.tvSubject.setText(subject);
        } else {
            holder.tvSubject.setText("");
        }
        if(details!=null) {
            holder.tvDetails.setText(Html.fromHtml(details));
        } else {
            holder.tvDetails.setText("");
        }
        if(reminder!=null) {
            holder.tvReminder.setText("Reminder: "+reminder);
        } else {
            holder.tvReminder.setText("");
        }
        if(duration!=null) {
            Long lDuration = Long.valueOf(duration);
            Long hours = TimeUnit.MINUTES.toHours(lDuration);
            Long minutes = lDuration;
            Long seconds = TimeUnit.MINUTES.toSeconds(lDuration);
            holder.tvDuration.setText("Duration: "+String.format("%d hr, %d min, %d sec",
                            hours,
                            minutes - TimeUnit.HOURS.toMinutes(hours),
                            0)
            );
        } else {
            holder.tvDuration.setText("");
        }
        if(location!=null) {
            holder.tvLocation.setText(location);
        } else {
            holder.tvLocation.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if(this.cursor ==null) {
            return 0;
        } else {
            return this.cursor.getCount();
        }
    }

    public static class MyViewHolder extends ViewHolder {

        public TextView tvSubject, tvDetails, tvLocation, tvDuration, tvReminder;

        public MyViewHolder(View v) {
            super(v);
            this.tvSubject = (TextView) v.findViewById(id.tvActivitySubject);
            this.tvDetails = (TextView) v.findViewById(id.tvActivityDetails);
            this.tvLocation = (TextView) v.findViewById(id.tvActivityLocation);
            this.tvDuration = (TextView) v.findViewById(id.tvDuration);
            this.tvReminder = (TextView) v.findViewById(id.tvActivityDateAndTime);
        }
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
    }


}
