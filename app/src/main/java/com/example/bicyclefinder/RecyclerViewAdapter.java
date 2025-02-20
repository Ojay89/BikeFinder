package com.example.bicyclefinder;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter<T>.MyViewHolder> {
    private static final String LOG_TAG = "MY_TAG";
    private final List<T> data;
    private OnItemClickListener<T> onItemClickListener;
    private final int viewId = View.generateViewId();


    public RecyclerViewAdapter(List<T> data) {
        this.data = data;
        Log.d(LOG_TAG, data.toString());

    }

    @NonNull
    @Override
    public RecyclerViewAdapter<T>.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = makeView(parent.getContext());
        Log.d(LOG_TAG, v.toString());
        MyViewHolder vh = new MyViewHolder(v);
        vh.view.setTextColor(Color.WHITE);
        vh.view.setTextSize(16);
        return vh;
    }

    private View makeView(Context context) {
        ViewGroup.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(context);
        textView.setId(viewId);
        textView.setLayoutParams(params);
        layout.addView(textView);
        return layout;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        T dataItem = data.get(position);
        Log.d(LOG_TAG, "onBindViewHolder " + data.toString());
        holder.view.setText(dataItem.toString());
        Log.d(LOG_TAG, "onBindViewHolder called " + position);
    }

    @Override
    public int getItemCount() {
        int count = data.size();
        Log.d(LOG_TAG, "getItemCount called: " + count);
        return count;
    }

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView view;
        ImageView imageView;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            view = itemView.findViewById(viewId);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getAdapterPosition(), data.get(getAdapterPosition()));
            }
        }
    }
}