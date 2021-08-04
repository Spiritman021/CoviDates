package com.tworoot2.covidupdates;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.activity.OnBackPressedCallback;

public class ProgressBar {
    ProgressDialog progressDialog;

    public void ShowDialog(Context context) {


        //setting up progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    public void DismissDialog() {
        progressDialog.dismiss();
    }
}
