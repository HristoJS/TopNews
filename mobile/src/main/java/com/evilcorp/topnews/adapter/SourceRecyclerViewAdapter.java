package com.evilcorp.topnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.evilcorp.topnews.R;
import com.evilcorp.topnews.model.local.Source;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Source} and makes a call to the
 * specified {@link OnSourceInteractionListener}.
 * TODO: Replace the implementation with code for your newsItems type.
 */
public class SourceRecyclerViewAdapter extends RecyclerView.Adapter<SourceRecyclerViewAdapter.ViewHolder> {

    private final List<Source> mSources;
    private final OnSourceInteractionListener mListener;
    private Context mContext;

    public SourceRecyclerViewAdapter(Context context, List<Source> items, OnSourceInteractionListener listener) {
        mContext = context;
        mSources = items;
        mListener = listener;
    }

    public interface OnSourceInteractionListener {
        void onSourceSelected(Source source);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_source, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mSources.get(position);
        Glide.with(mContext)
                .load(holder.mItem.getUrlsToLogos().getSmall())
                .asBitmap()
                .fitCenter()
                .into(holder.mSourceLogo);

        //holder.mSourceName.setText(holder.mItem.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSourceSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSources.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView mSourceLogo;
        TextView mSourceName;
        Source mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mSourceLogo = (ImageView) view.findViewById(R.id.source_logo);
            //mSourceName = (TextView) view.findViewById(R.id.source_name);
        }

    }
}
