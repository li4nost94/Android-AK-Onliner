package com.lol.vadim.ak;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OnOff extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_off);

       //Button btn_wifi = (Button)findViewById(R.id.btnwifi);
      //  Button btn_3g = (Button)findViewById(R.id.btn3g);


    }

    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.btn3g:
                setMobileDataEnabled();
                break;
            case R.id.btnwifi:
                alertOfflinewifi();


        }
    }

    public void alertOfflinewifi() {
        new AlertDialog.Builder(OnOff.this)
                .setTitle("Нужен интернет")
                .setMessage("Пожалуйста подключитесь к интернету и обновите страницу.")
                .setIcon(R.drawable.wifi)
                .setPositiveButton("Подключить Wi-Fi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(true);

                        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                        if (mWifi.isAvailable() == true) {
                          //  return "Connected to WiFi";
                        } else if (mMobile.isAvailable() == true) {
                           // return "Connected to Mobile Network";
                        }

                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }

    private void setMobileDataEnabled() {
        Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS); /// xz
        startActivity(intent);
    }

}
