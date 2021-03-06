package com.oil.vivek.oilmillcalc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.oil.vivek.oilmillcalc.htmlparse.AsyncTaskCompleteListener;
import com.oil.vivek.oilmillcalc.htmlparse.HttpRequester;
import com.oil.vivek.oilmillcalc.htmlparse.ParseContent;
import com.oil.vivek.oilmillcalc.utils.AndyConstants;
import com.oil.vivek.oilmillcalc.utils.AndyUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class aboutUs extends ActionBarActivity implements View.OnClickListener, AsyncTaskCompleteListener {

    public String IMEI;
    public String VersionNumber;
    TextView trial;


    Button pay_online;
    Boolean FullVersionActive;
    SharedPreferences appdata;
    int daysLeft;
    ProgressDialog progress;
    private ParseContent parseContent;
    private ArrayList<HashMap<String,String>> alldetails;
    LinearLayout pay_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 0, 121, 107)));

        TextView privacypolicy = (TextView) findViewById(R.id.privacypolicyTV);
        privacypolicy.setText(
                Html.fromHtml(
                        "<a href=\"http://oilmill.madeovercode.com/privacypolicy\">Privacy Policy</a> "));
        privacypolicy.setMovementMethod(LinkMovementMethod.getInstance());

        pay_online = (Button) findViewById(R.id.pay_online);
        pay_online.setOnClickListener(this);

        appdata = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = appdata.edit();

        IMEI = appdata.getString("imei","0");

        daysLeft = appdata.getInt("daysLeft", 0);
        if(daysLeft >= 0)
        {
            editor.putBoolean("FullVersionActive", false);
            editor.commit();
        }
        else if(daysLeft == -1)
        {
            editor.putBoolean("FullVersionActive", true);
            editor.commit();
        }

        FullVersionActive = appdata.getBoolean("FullVersionActive", false);
        trial = (TextView) findViewById(R.id.trialDays);
        pay_view = (LinearLayout) findViewById(R.id.pay_view);

        if(FullVersionActive)
        {
            trial.setText("FULL VERSION");
            trial.setTextColor(Color.parseColor("#ff008174"));
            pay_view.setVisibility(View.GONE);

        }
        else
        {
            trial.setText("Trial Version");
            trial.setTextColor(Color.parseColor("#ffc00200"));
            pay_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onClick(View v) {
        parseContent = new ParseContent(this);

        switch (v.getId()){
            case R.id.pay_online:
                launchInstamojoRequest();
                break;
        }
    }

    private void launchInstamojoRequest()
    {
        progress = new ProgressDialog(this);
        progress.setTitle("Loading payment options");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();

        if (!AndyUtils.isNetworkAvailable(aboutUs.this)) {
            AndyUtils.showToast(
                    "Internet not available!",
                    aboutUs.this);
            progress.dismiss();
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AndyConstants.URL, AndyConstants.ServiceType.INSTAMOJOCREATE);

        map.put(AndyConstants.Params.NAME, appdata.getString("userName", null));
        map.put(AndyConstants.Params.MOBILE, appdata.getString("phnNumber", null));
        map.put(AndyConstants.Params.IMEI, IMEI);

        new HttpRequester(aboutUs.this, map,
                AndyConstants.ServiceCode.INSTAMOJOCREATE, this);
    }

    private void checkDaysLeftonServer() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        VersionNumber = getString(R.string.VersionNumber);
        if (!AndyUtils.isNetworkAvailable(aboutUs.this)) {
            AndyUtils.showToast(
                    "Internet not available!",
                    aboutUs.this);
            progress.dismiss();
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AndyConstants.URL, AndyConstants.ServiceType.LOGIN);

        map.put(AndyConstants.Params.IMEI, IMEI);
        map.put(AndyConstants.Params.VERSION, VersionNumber);
        map.put(AndyConstants.Params.DAYS_LEFT, String.valueOf(daysLeft));
        map.put(AndyConstants.Params.NOTIFICATION_TOKEN, appdata.getString("firebase_token", null));
        map.put(AndyConstants.Params.LAST_ACCESS, currentDateTimeString);

        new HttpRequester(aboutUs.this, map,
                AndyConstants.ServiceCode.LOGIN, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {
            case AndyConstants.ServiceCode.LOGIN:
                if (parseContent.isSuccess(response)) {
                    alldetails = parseContent.getDetaillogin(response);
                    if(Integer.parseInt(alldetails.get(0).get(AndyConstants.Params.DAYS_LEFT)) == -1)
                    {
                        progress.dismiss();
                        activate();
                    }
                    else
                    {
                        progress.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Not Activated by Admin", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }else {
                    String msg = parseContent.getErrorCode(response);
                    AndyUtils.showToast(
                            msg,
                            aboutUs.this);
                    Log.d("msg", msg);
                }

                break;

            case AndyConstants.ServiceCode.INSTAMOJOCREATE:
                    String paymentURL = parseContent.getPaymentURL(response);
                    if(paymentURL != null){
                        AndyUtils.showToastLong("Loading Payment Methods, Please Wait....", aboutUs.this);
                        WebView webView = loadWebView(paymentURL, this);
                        setContentView(webView);
                    }else{
                        AndyUtils.showToast(
                                "Sorry, something went wrong!",
                                aboutUs.this);
                        Log.e("Payment","IMEI not found | Parameter missing");
                    }
                    progress.dismiss();
                break;

            default:
                break;
        }
    }

    public void activate(){
        runOnUiThread(new Runnable() {
            public void run() {
                SharedPreferences.Editor editor = appdata.edit();
                editor.putBoolean("trialExpired", false);
                editor.putBoolean("FullVersionActive", true);
                editor.putInt("daysLeft", -1);
                editor.commit();

                trial.setText("FULL VERSION");
                trial.setTextColor(Color.parseColor("#ff008174"));
                pay_view.setVisibility(View.GONE);

                AlertDialog.Builder builder = new AlertDialog.Builder(aboutUs.this);
                builder.setCancelable(false);
                builder.setTitle("Congratulations");
                builder.setMessage("Full Version now Active");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public WebView loadWebView(final String URL, final Context ctx)
    {
        WebView mWebview  = new WebView(ctx);

        if (Build.VERSION.SDK_INT >= 19) {
            mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else
        {
            mWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                System.out.println("Override ");
                return handleUri(ctx, url);
            }
        });

        mWebview.loadUrl(URL);
        return mWebview;
    }

    private boolean handleUri(Context context, String url) {
        System.out.println("Logging URL: " +url);

        if (url.contains(AndyConstants.ServiceType.SUCCESS_URL)) {

            setContentView(R.layout.activity_about_us);

            progress = new ProgressDialog(context);
            progress.setTitle("Activating Full Version");
            progress.setMessage("Wait while contacting server....");
            progress.setCancelable(false);
            progress.show();

            final boolean[] bool = {true};
            final Handler handler = new Handler();
            final Thread thread = new Thread() {
                @Override

                public void run() {
                    try {
                        while(bool[0]) {
                            Looper.prepare();
                            handler.post(this);
                            bool[0] = false;
                            checkDaysLeftonServer();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            return true;
        } else {
            // Returning false means that you are going to load this url in the webView itself, not handled by default action
            return false;
        }
    }
}
