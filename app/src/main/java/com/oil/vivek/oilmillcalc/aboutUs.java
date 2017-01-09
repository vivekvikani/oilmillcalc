package com.oil.vivek.oilmillcalc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oil.vivek.oilmillcalc.htmlparse.AsyncTaskCompleteListener;
import com.oil.vivek.oilmillcalc.htmlparse.HttpRequester;
import com.oil.vivek.oilmillcalc.htmlparse.ParseContent;
import com.oil.vivek.oilmillcalc.utils.AndyConstants;
import com.oil.vivek.oilmillcalc.utils.AndyUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class aboutUs extends ActionBarActivity implements View.OnClickListener, AsyncTaskCompleteListener {

    public String IMEI;
    public String VersionNumber;
    TextView trial;
    TextView internetTxt;
    TextView detailsTxt;

    Button final_activate;
    Boolean FullVersionActive;
    SharedPreferences appdata;
    int daysLeft;
    ProgressDialog progress;
    private ParseContent parseContent;
    private ArrayList<HashMap<String,String>> alldetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 0, 121, 107)));

        final_activate = (Button) findViewById(R.id.final_activate);
        final_activate.setOnClickListener(this);

        appdata = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = appdata.edit();

        daysLeft = appdata.getInt("daysLeft", 0);
        if(daysLeft==0)
        {
            editor.putBoolean("FullVersionActive", false);
            editor.commit();
        }
        else if(daysLeft== -1)
        {
            editor.putBoolean("FullVersionActive", true);
            editor.commit();
        }

        FullVersionActive = appdata.getBoolean("FullVersionActive", false);
        trial = (TextView) findViewById(R.id.trialDays);
        internetTxt = (TextView) findViewById(R.id.textView6);
        detailsTxt = (TextView) findViewById(R.id.textView5);
        if(FullVersionActive)
        {
            trial.setText("FULL VERSION");
            trial.setTextColor(Color.parseColor("#ff008174"));
            final_activate.setEnabled(false);

            final_activate.setVisibility(View.GONE);
            internetTxt.setVisibility(View.GONE);
            detailsTxt.setVisibility(View.GONE);

        }
        else
        {
            trial.setText("Trial Version");
            trial.setTextColor(Color.parseColor("#ffc00200"));
            final_activate.setEnabled(true);
            final_activate.setVisibility(View.VISIBLE);
            internetTxt.setVisibility(View.VISIBLE);
            detailsTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onClick(View v) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();
        VersionNumber = getString(R.string.VersionNumber);

        parseContent = new ParseContent(this);

        progress = new ProgressDialog(this);
        progress.setTitle("Activating Full Version");
        progress.setMessage("Wait while contacting server...");
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
    }

    private void checkDaysLeftonServer() {
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
                final_activate.setEnabled(false);

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
}
