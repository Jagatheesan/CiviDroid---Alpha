package you.in.spark.energy.cividroid.adapters;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;


public class RecyclerAboutContactAdapter extends Adapter<RecyclerAboutContactAdapter.MyViewHolder> {

    private final List<Pair<String, String>> detailPair;

    public RecyclerAboutContactAdapter(List<Pair<String, String>> detailPair) {
        this.detailPair = detailPair;
    }

    @Override
    public RecyclerAboutContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.contact_detail_item, parent, false);
        RecyclerAboutContactAdapter.MyViewHolder vh = new RecyclerAboutContactAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAboutContactAdapter.MyViewHolder holder, int position) {
        holder.detailType.setText(this.detailPair.get(position).first);
        holder.detail.setText(this.detailPair.get(position).second);
    }

    @Override
    public int getItemCount() {
        return this.detailPair.size();
    }

    public static class MyViewHolder extends ViewHolder {
        public TextView detailType;
        public TextView detail;

        public MyViewHolder(View v) {
            super(v);
            this.detailType = (TextView) v.findViewById(id.detailType);
            this.detail = (TextView) v.findViewById(id.detail);
        }
    }
}
