package com.away_expat.away.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.R;
import com.away_expat.away.views.RecyclerViewTagActivityView;

import java.util.ArrayList;

public class TagActivityAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<String> horizontalList;

    public TagActivityAdapter(ArrayList<String> horizontalList, Context context) {
        this.horizontalList = horizontalList; this.context = context;
    }

    @Override
    public RecyclerViewTagActivityView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tag_activity, parent, false);
        return new RecyclerViewTagActivityView(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof RecyclerViewTagActivityView) {
            bindViewHolder((RecyclerViewTagActivityView) viewHolder, position);
        } else {
            Log.e("ERROR", "Should instanciable");
        }
    }

    public void bindViewHolder(final RecyclerViewTagActivityView holder, final int position) {
        String mItem = horizontalList.get(position);
        holder.textView.setText(mItem);
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}
