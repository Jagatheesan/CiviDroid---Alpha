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

import java.util.Map;
import java.util.Vector;

import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;


public class ContactSubtypeAdapter extends Adapter<ContactSubtypeAdapter.MyViewHolder> implements OnCheckedChangeListener {

    private Map<Integer, Pair<String,String>> subtypeNames;
    private final Vector<Integer> checked;
    private boolean enabled;

    public ContactSubtypeAdapter(Map<Integer, Pair<String, String>> subtypeNames) {
        this.subtypeNames = subtypeNames;
        this.checked = new Vector<>();
        this.enabled = false;
    }

    @Override
    public ContactSubtypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.subtype_item, parent, false);
        ContactSubtypeAdapter.MyViewHolder vh = new ContactSubtypeAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ContactSubtypeAdapter.MyViewHolder holder, int position) {
        holder.cbSubtype.setOnCheckedChangeListener(null);
        if(this.checked.contains(position)) {
            holder.cbSubtype.setChecked(true);
        } else {
            holder.cbSubtype.setChecked(false);
        }
        holder.cbSubtype.setTag(position);
        holder.cbSubtype.setOnCheckedChangeListener(this);
        holder.tvSubtypeName.setText(this.subtypeNames.get(position).second);
        holder.cbSubtype.setEnabled(this.enabled);
    }

    @Override
    public int getItemCount() {
        return this.subtypeNames.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            this.checked.add((Integer) buttonView.getTag());
        } else {
            this.checked.remove(buttonView.getTag());
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.notifyDataSetChanged();
    }

    public static class MyViewHolder extends ViewHolder {

        CheckBox cbSubtype;
        TextView tvSubtypeName;

        public MyViewHolder(View v) {
            super(v);
            this.cbSubtype = (CheckBox) v.findViewById(id.cbSubtype);
            this.tvSubtypeName = (TextView) v.findViewById(id.tvSubtypeName);
        }
    }

    public Vector<Integer> getChecked() {
        return this.checked;
    }

    public Vector<String> getCheckedLabels() {
        Vector<String> names = new Vector<>();
        for(int sel : this.checked) {
            names.add(this.subtypeNames.get(sel).first);
        }
        return names;
    }

    public void setRealData(Map<Integer, Pair<String, String>> subtypeNames) {
        this.subtypeNames = subtypeNames;
        this.notifyDataSetChanged();
    }
}
