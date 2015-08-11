package you.in.spark.energy.cividroid;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerAdapter";

    List<Pair<String,String>> titleNamePairValues;
    Vector<Integer> selected;

    public RecyclerAdapter(List<Pair<String,String>> titleNamePairValues) {
        Log.v(TAG, "constructor");
        selected = new Vector<>();
        this.titleNamePairValues = titleNamePairValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.field_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
       viewHolder.title.setText(titleNamePairValues.get(i).first);
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        if(selected.contains(i)){
            viewHolder.checkBox.setChecked(true);
        }
        else {
            viewHolder.checkBox.setChecked(false);
        }
        viewHolder.checkBox.setTag(i);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selected.add(Integer.valueOf(buttonView.getTag().toString()));
                } else {
                    selected.remove(Integer.valueOf(buttonView.getTag().toString()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleNamePairValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public CheckBox checkBox;
        public MyViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tvFieldTitle);
            checkBox = (CheckBox) v.findViewById(R.id.cbFieldCheckBox);
            Log.v(TAG,"constructor loaded");
        }
    }


    public Set<String>[] getTitleNameFields() {
        Set<String>[] values =new Set[2];
        values[0] = new LinkedHashSet<>();
        values[1] = new LinkedHashSet<>();
        Log.v(TAG,selected.size()+"");
        for(int sel : selected) {
            values[0].add(titleNamePairValues.get(sel).first);
            Log.v("RecyclerAdapter","first: "+titleNamePairValues.get(sel).first);
            values[1].add(titleNamePairValues.get(sel).second);
            Log.v("RecyclerAdapter","second: "+titleNamePairValues.get(sel).second);
        }
        return values;
    }


}
