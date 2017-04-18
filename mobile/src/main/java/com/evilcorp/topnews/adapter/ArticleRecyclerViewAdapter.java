package com.evilcorp.topnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.evilcorp.topnews.R;
import com.evilcorp.topnews.model.local.Article;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article} and makes a call to the
 * specified {@link OnNewsItemInteractionListener}.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {

    private final List<Article> mValues;
    private final OnNewsItemInteractionListener mListener;
    private Context mContext;

    public interface OnNewsItemInteractionListener {
        void onNewsItemSelected(Article article);
    }

    public ArticleRecyclerViewAdapter(Context context, List<Article> items, OnNewsItemInteractionListener listener) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_articles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mArticle = mValues.get(position);
        Glide.with(mContext)
                .load(holder.mArticle.getUrlToImage())
                .asBitmap()
                .fitCenter()
                .into(holder.mArticleImage);

        holder.mArticleTitle.setText(holder.mArticle.getTitle());
        holder.mArticleText.setText(holder.mArticle.getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onNewsItemSelected(holder.mArticle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView mArticleImage;
        final TextView mArticleTitle;
        final TextView mArticleText;
        Button mReadMoreButton;
        Article mArticle;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mArticleImage = (ImageView) view.findViewById(R.id.article_image);
            mArticleTitle = (TextView) view.findViewById(R.id.article_title);
            mArticleText = (TextView) view.findViewById(R.id.article_text);
            //mReadMoreButton = (Button) view.findViewById(R.id.read_more_button);
        }

    }
}
