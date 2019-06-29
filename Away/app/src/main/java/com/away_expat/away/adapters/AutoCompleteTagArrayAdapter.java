package com.away_expat.away.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Tag;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteTagArrayAdapter extends ArrayAdapter {

    private List<Tag> items;
    private Context mContext;
    private int itemLayout;

    private ListFilter listFilter = new ListFilter();
    private List<Tag> dataListAllItems;

    public AutoCompleteTagArrayAdapter(Context context, int resource, List<Tag> items) {
        super(context, resource, items);
        this.items = items;
        this.mContext = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Tag getItem(int position) {
        Log.d("CustomListAdapter", items.get(position).getName());
        return items.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.textView);
        strName.setText(getItem(position).getName());
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<>(items);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<Tag> matchValues = new ArrayList<>();

                for (Tag dataItem : dataListAllItems) {
                    if (dataItem.getName().toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                items = (ArrayList<Tag>) results.values;
            } else {
                items = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Tag)(resultValue)).getName();
            return str;
        }
    }
}