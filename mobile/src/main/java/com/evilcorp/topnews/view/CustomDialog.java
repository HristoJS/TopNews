package com.evilcorp.topnews.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.evilcorp.topnews.R;
import com.evilcorp.topnews.model.remote.SourceFilter;

/**
 * Created by hristo.stoyanov on 4/10/2017.
 */

public class CustomDialog extends DialogFragment {
    private static final String TITLE_KEY = "title";
    private static final String MESSAGE_KEY = "message";
    private static final String POSITIVE_TEXT_KEY = "positive_text";
    private static final String NEGATIVE_TEXT_KEY = "negative_text";
    private static final String LAYOUT = "layout";

    private DialogInteractListener mListener;

    public static CustomDialog newInstance(String title, String message, String positiveText, String negativeText) {
        CustomDialog messageDialogFragment = new CustomDialog();
        Bundle args = new Bundle();
        args.putString(TITLE_KEY, title);
        args.putString(MESSAGE_KEY, message);
        args.putString(NEGATIVE_TEXT_KEY, negativeText);
        messageDialogFragment.setArguments(args);
        return messageDialogFragment;
    }

    public static CustomDialog newInstance(int layout) {
        CustomDialog messageDialogFragment = new CustomDialog();
        Bundle args = new Bundle();
        args.putInt(LAYOUT, layout);
        messageDialogFragment.setArguments(args);
        return messageDialogFragment;
    }

    public interface DialogInteractListener {
        void onDialogButtonClick(SourceFilter sourceFilter);
    }

    public CustomDialog() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogInteractListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getSimpleName() + " must implement " + DialogInteractListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int layout = getArguments().getInt(LAYOUT);
        if(getArguments().getInt(LAYOUT) != 0) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View dialogView = inflater.inflate(layout, null);

            final Spinner languageSpinner = setupSpinner(dialogView, R.id.languagesSpinner, SourceFilter.Languages.toStringArray());
            final Spinner categoriesSpinner = setupSpinner(dialogView, R.id.categoriesSpinner, SourceFilter.Categories.toStringArray());
            final Spinner countriesSpinner = setupSpinner(dialogView, R.id.countriesSpinner, SourceFilter.Countries.toStringArray());

            Dialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setView(dialogView)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SourceFilter sourceFilter = new SourceFilter(languageSpinner.getSelectedItem().toString(),
                                    categoriesSpinner.getSelectedItem().toString(),countriesSpinner.getSelectedItem().toString());
                            mListener.onDialogButtonClick(sourceFilter);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            //Dialog alertDialog = new Dialog(getActivity());
            //alertDialog.setContentView(layout);
            return alertDialog;
        }
        else {
            return generateAlertDialog();
        }
    }

    private Spinner setupSpinner(View parent, int id, String[] array){
        Spinner spinner = (Spinner) parent.findViewById(id);
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array));
        return spinner;
    }

    private Dialog generateAlertDialog() {
        Bundle args = getArguments();
        String title = args.getString(TITLE_KEY);
        String message = args.getString(MESSAGE_KEY);
        String positiveText = args.getString(POSITIVE_TEXT_KEY);
        String negativeText = args.getString(NEGATIVE_TEXT_KEY);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message);

        if (!TextUtils.isEmpty(positiveText)) {
            alertDialog
                .setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (mListener != null) {
                                mListener.onDialogButtonClick(null);
                                dismiss();
                            }
                        }
                    }
                );
        }

        if (!TextUtils.isEmpty(negativeText)) {
            alertDialog
                .setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (mListener != null) {
                                //mListener.onDialogButtonClick(false, CustomDialog.this.getTag());
                                dismiss();
                            }
                        }
                    }
                );
        }
        return alertDialog.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}