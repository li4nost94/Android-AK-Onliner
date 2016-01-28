package com.lol.vadim.ak;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Vadim on 02.10.2015.
 */
public class MyAppWebViewClient extends WebViewClient {



    @Override

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().endsWith("https://r.onliner.by/ak/")) {
            return false;
        }
        view.loadUrl(url);
        return true;
    }
}
