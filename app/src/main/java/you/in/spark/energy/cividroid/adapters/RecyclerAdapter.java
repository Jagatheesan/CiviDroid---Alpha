package you.in.spark.energy.cividroid.adapters;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;

/**
 * Created by dell on 21-06-2015.
 */
public class RecyclerAdapter extends Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerAdapter";

    private final List<Pair<String, String>> titleNamePairValues;
    private final Vector<Integer> selected;
    private final Vector<Integer> defaultFields;
    private final int activityType;

    public RecyclerAdapter(List<Pair<String, String>> titleNamePairValues, int activityType) {
        this.selected = new Vector<>();
        this.defaultFields = new Vector<>();
        this.titleNamePairValues = titleNamePairValues;
        this.activityType = activityType;

        if(activityType==0) {
            //find positions of default fields
            int size = titleNamePairValues.size();
            for (int i = 0; i < size; i++) {
                if (titleNamePairValues.get(i).second.equalsIgnoreCase("display_name") || titleNamePairValues.get(i).second.equalsIgnoreCase("email") || titleNamePairValues.get(i).second.equalsIgnoreCase("phone")) {
                    this.selected.add(i);
                    this.defaultFields.add(i);
                }
                if (this.defaultFields.size()==3) {
                    break;
                }
            }
        }

    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout.field_item, viewGroup, false);
        RecyclerAdapter.MyViewHolder vh = new RecyclerAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(this.titleNamePairValues.get(i).first);
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        if (this.selected.contains(i)) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }

        if(this.activityType ==0) {
            if (this.defaultFields.contains(i)) {
                viewHolder.checkBox.setEnabled(false);
                return;
            }
        }

        viewHolder.checkBox.setEnabled(true);


        viewHolder.checkBox.setTag(i);
        viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RecyclerAdapter.this.selected.add(Integer.valueOf(buttonView.getTag().toString()));
                } else {
                    RecyclerAdapter.this.selected.remove(Integer.valueOf(buttonView.getTag().toString()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.titleNamePairValues.size();
    }

    public static class MyViewHolder extends ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public CheckBox checkBox;

        public MyViewHolder(View v) {
            super(v);
            this.title = (TextView) v.findViewById(id.tvFieldTitle);
            this.checkBox = (CheckBox) v.findViewById(id.cbFieldCheckBox);
        }
    }


    public String[] getTitleNameFields() {
        String title = "", name = "";
        int selectedSize = this.selected.size();
        if(selectedSize>0) {
            title = this.titleNamePairValues.get(this.selected.get(0)).first;
            name = this.titleNamePairValues.get(this.selected.get(0)).second;
            for (int i = 1; i < selectedSize; i++) {
                title = title + "," + this.titleNamePairValues.get(this.selected.get(i)).first;
                name = name + "," + this.titleNamePairValues.get(this.selected.get(i)).second;
            }
        }
        return new String[]{title, name};
    }


}
