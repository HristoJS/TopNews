package com.evilcorp.topnews.ui.sources;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.evilcorp.topnews.R;
import com.evilcorp.topnews.adapter.SourceRecyclerViewAdapter;
import com.evilcorp.topnews.model.local.Source;
import com.evilcorp.topnews.model.remote.SourceFilter;
import com.evilcorp.topnews.ui.articles.ArticlesActivity;
import com.evilcorp.topnews.ui.base.BaseActivity;
import com.evilcorp.topnews.view.CustomDialog;

import java.util.List;

public class SourceActivity extends BaseActivity implements
        SourceContract.View,
        SourceRecyclerViewAdapter.OnSourceInteractionListener,
        CustomDialog.DialogInteractListener {
    private static final String TAG = SourceActivity.class.getSimpleName();
    private static final int COLUMNT_COUNT = 3;
    private RecyclerView mRecyclerView;
    private SourceContract.Presenter mPresenter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private String[] mLanguagesArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_source);
        super.onCreate(savedInstanceState);
        mPresenter = new SourcePresenter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.source_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNT_COUNT));

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        Log.d(TAG, action + data);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_options_source, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if(item.getItemId() == R.id.action_filter){
            CustomDialog.newInstance(R.layout.dialog_filter).show(getSupportFragmentManager(),"WTF");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void showSources(List<Source> sources) {
        mRecyclerView.setAdapter(new SourceRecyclerViewAdapter(this, sources, this));
    }

    @Override
    public void onSourceSelected(Source source) {
        Intent articlesIntent = new Intent(this, ArticlesActivity.class);
        articlesIntent.putExtra(ArticlesActivity.ARG_SOURCE, source);
        startActivity(articlesIntent);
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDialogButtonClick(SourceFilter sourceFilter) {
        mPresenter.filterSources(sourceFilter);
    }
}
