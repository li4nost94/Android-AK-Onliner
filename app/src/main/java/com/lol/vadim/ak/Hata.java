package com.lol.vadim.ak;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ShareActionProvider;

public class Hata extends Activity {

    private ShareActionProvider mShareActionProvider;
    private WebView mWebView;
   // private Intent mShareIntent;
    //private Intent shareIntent2;
    private String urll="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hata);
        isOnline();

        final Dialog dialog = new Dialog(this, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.activity_scren);
        dialog.show();


        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://r.onliner.by/ak/");
        mWebView.setWebViewClient(new MyAppWebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                urll = url;

                findViewById(R.id.progressBar1).setVisibility(View.GONE);
                findViewById(R.id.tv_o).setVisibility(View.GONE);
                findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

    }


    private Intent getDefaultShareIntent() {
        String urlp = urll;// = mWebView.getUrl();
        Log.d("URL", urlp);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "[А]ренда [К]вартиры");
        intent.putExtra(Intent.EXTRA_TEXT, urlp);
        return intent;
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        /** Getting the actionprovider associated with the menu item whose id is share */
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();

        /** Setting a share intent */
        mShareActionProvider.setShareIntent(getDefaultShareIntent());

        return super.onCreateOptionsMenu(menu);
        /*
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(getDefaultShareIntent());
        }
        return true;
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(getDefaultShareIntent());
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if(nInfo != null && nInfo.isConnected()) {
            Log.v("status_internet", "ONLINE");
            //Toast.makeText(getApplicationContext(), "ONLINE", Toast.LENGTH_LONG).show();
            return true;
        }
        else
        {
            Log.v("status_internet", "OFFLINE");
            alertOfflinewifi();
            //Toast.makeText(getApplicationContext(), "OFFLINE", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void alertOfflinewifi() {
        new AlertDialog.Builder(Hata.this)
                .setTitle("Нужен интернет")
                .setMessage("Пожалуйста подключитесь к интернету и обновите страницу.")
                .setIcon(R.drawable.wifi)
                .setPositiveButton("Подключить Wi-Fi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                wifi.setWifiEnabled(true);

                            }
                        })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                }).show();

    }




}
