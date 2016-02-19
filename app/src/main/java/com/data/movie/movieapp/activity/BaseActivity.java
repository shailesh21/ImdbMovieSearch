package com.data.movie.movieapp.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.data.movie.movieapp.BaseApplication;
import com.data.movie.movieapp.utils.WebServiceInterface;

import butterknife.ButterKnife;

/**
 * Created by Shailesh on 18/02/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void showSnackBar(View snackBarView, String message) {
        if (snackBarView != null)
            Snackbar.make(snackBarView, message, Snackbar.LENGTH_LONG).show();
    }

    public WebServiceInterface getService() {
        return ((BaseApplication) getApplicationContext()).getService();
    }

    public boolean isNetworkAvailable() {
        boolean isNetworkConnected = false;
        try {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            if (connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo()
                    .isAvailable() && connManager.getActiveNetworkInfo().isConnected()) {
                isNetworkConnected = true;
            }
        } catch (Exception ex) {
            isNetworkConnected = false;
        }
        return isNetworkConnected;
    }

    public void hideKeyboard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                    .INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
