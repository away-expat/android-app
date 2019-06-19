package com.away_expat.away.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.R;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.views.RecyclerViewTagUserView;

import java.util.List;

public class TagUserAdapter extends RecyclerView.Adapter<RecyclerViewTagUserView> {

    private Context context;
    private List<Tag> horizontalList;
    private OnAddedTagClickListener mOnAddedTagClickListener;

    public TagUserAdapter(List<Tag> horizontalList, Context context, OnAddedTagClickListener onAddedTagClickListener) {
        this.horizontalList = horizontalList;
        this.context = context;
        this.mOnAddedTagClickListener = onAddedTagClickListener;
    }

    @Override
    public RecyclerViewTagUserView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tag_user, parent, false);
        return new RecyclerViewTagUserView(itemView, mOnAddedTagClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerViewTagUserView holder, int position) {
        Tag mItem = horizontalList.get(position);
        holder.textView.setText(mItem.getName());
    }

    @Override
    public int getItemCount() {
        return horizontalList == null ? 0 :horizontalList.size();
    }

    public interface OnAddedTagClickListener {
        void onClick(int position);
    }
}