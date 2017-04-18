package com.evilcorp.topnews.ui.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.evilcorp.topnews.R;

/**
 * Created by hristo.stoyanov on 4/4/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {
    private boolean mActive;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;

    @Override
    protected void onPause() {
        super.onPause();
        mActive = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActive = true;
    }

    @Override
    public void showError(String error) {
        showAlert(error);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean isActive(){
        return mActive;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected void setToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
    }

    private void dismissDialog(DialogInterface dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgress();
        dismissDialog(mAlertDialog);
        dismissDialog(mProgressDialog);
    }

    @Override
    public void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();

        mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.app_name), msg);
    }

    @Override
    public void dismissProgress() {
        dismissDialog(mProgressDialog);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showAlert(String msg, DialogInterface.OnClickListener onClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mAlertDialog = builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.ic_notifications_black_24dp)
                .setPositiveButton("OK", onClickListener).create();
        mAlertDialog.show();
    }
    protected void showAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mAlertDialog = builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.ic_notifications_black_24dp)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        mAlertDialog.show();
    }
}
