package com.oil.vivek.oilmillcalc.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.oil.vivek.oilmillcalc.aboutUs;


/**
 * Created by Parsania Hardik on 19/01/2016.
 */
public class AndyUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
    public static void showToastLong(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    public static WebView loadWebView(final String URL, final Context ctx)
    {
        WebView mWebview  = new WebView(ctx);

        if (Build.VERSION.SDK_INT >= 19) {
            mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                return handleUri(ctx, url);
            }
        });

        mWebview.loadUrl(URL);
        return mWebview;
    }

    private static boolean handleUri(Context context, String url) {
        if (AndyConstants.ServiceType.SUCCESS_URL.equals(url)) {
            System.out.println("Success URL");
            Intent intent = new Intent(context,aboutUs.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            // Returning false means that you are going to load this url in the webView itself, not handled by default action
            return false;
        }
    }
}



 /*   @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        final Uri uri = Uri.parse(url);
        return handleUri(uri);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final Uri uri = request.getUrl();
        return handleUri(uri);
    }

    private boolean handleUri(final Uri uri) {

        // Based on some condition you need to determine if you are going to load the url
        // in your web view itself or in a browser.
        // You can use `host` or `scheme` or any part of the `uri` to decide.
        if (*//* any condition *//*) {
            // Returning false means that you are going to load this url in the webView itself
            return false;
        } else {
            // Returning true means that you need to handle what to do with the url
            // e.g. open web page in a Browser
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
    }*/