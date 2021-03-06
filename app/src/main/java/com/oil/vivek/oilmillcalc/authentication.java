package com.oil.vivek.oilmillcalc;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oil.vivek.oilmillcalc.htmlparse.AsyncTaskCompleteListener;
import com.oil.vivek.oilmillcalc.htmlparse.HttpRequester;
import com.oil.vivek.oilmillcalc.htmlparse.ParseContent;
import com.oil.vivek.oilmillcalc.utils.AndyConstants;
import com.oil.vivek.oilmillcalc.utils.AndyUtils;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Vivek on 17/07/2015.
 */
public class authentication extends ActionBarActivity implements View.OnClickListener, AsyncTaskCompleteListener{
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE = 2;
    private static final int REQUEST_PERMISSION_SETTING = 3;
    public String IMEI;
    public String VersionNumber;
    public String operatorName;
    public String simID;
    private ParseContent parseContent;
    private ArrayList<HashMap<String,String>> alldetails;
    Button finalEnter;
    ProgressDialog progress;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progress = new ProgressDialog(this);
        progress.setTitle("Preparing App");
        progress.setMessage("This may take a few seconds...");
        progress.setCancelable(false);
        progress.show();

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        operatorName = telephonyManager.getNetworkOperatorName();
        checkPermission();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.finalEnter:
                finalEnter.setEnabled(false);
                EditText name = (EditText) findViewById(R.id.name);
                EditText number = (EditText) findViewById(R.id.number);
                int numberLength = number.getText().length();
                final String nameS = name.getText().toString();
                final String numberS = number.getText().toString();
                if(nameS.equals("")  || nameS.equals(null))
                {
                    Toast.makeText(getApplicationContext(), "Enter a Name", Toast.LENGTH_LONG).show();
                    finalEnter.setEnabled(true);
                }
                else if(numberS.equals("")  || numberS.equals(null) || numberLength<10)
                {
                    Toast.makeText(getApplicationContext(),"Enter a Valid Phone Number",Toast.LENGTH_LONG).show();
                    finalEnter.setEnabled(true);
                }
                else
                {

                    finalEnter.setEnabled(false);

                    VersionNumber = getString(R.string.VersionNumber);
                    progress = new ProgressDialog(this);
                    progress.setTitle("Activating Free Trial");
                    progress.setMessage("Wait while contacting server...");
                    progress.setCancelable(false);
                    progress.show();
                    finalEnter.setEnabled(true);

                    final boolean[] bool = {true};
                    final Handler handler = new Handler();
                    final Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                while (bool[0]) {
                                    checkandSave(nameS,numberS);
                                    Looper.prepare();
                                    handler.post(this);
                                    bool[0] = false;
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }
                break;
        }
    }

    private void checkIMEI() {
        if (!AndyUtils.isNetworkAvailable(authentication.this)) {
            AndyUtils.showToast(
                    "Internet is not available!",
                    authentication.this);
            progress.dismiss();
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AndyConstants.URL, AndyConstants.ServiceType.CHECKIMEI);

        map.put(AndyConstants.Params.IMEI, IMEI);
        new HttpRequester(authentication.this, map,
                AndyConstants.ServiceCode.CHECKIMEI, this);
    }

    private void checkandSave(String name, String number) {
        if (!AndyUtils.isNetworkAvailable(authentication.this)) {
            AndyUtils.showToast(
                    "Internet is not available!",
                    authentication.this);
            progress.dismiss();
        }else{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(AndyConstants.URL, AndyConstants.ServiceType.REGISTERATION);

            map.put(AndyConstants.Params.IMEI, IMEI);
            map.put(AndyConstants.Params.VERSION, VersionNumber);
            map.put(AndyConstants.Params.DAYS_LEFT, getString(R.string.days_left));
            map.put(AndyConstants.Params.NAME, name);
            map.put(AndyConstants.Params.MOBILE, number);
            map.put(AndyConstants.Params.CITY, "Location Disabled");
            new HttpRequester(authentication.this, map,
                    AndyConstants.ServiceCode.REGISTER, this);
        }
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        //Log.d("responsejson", response.toString());
        switch (serviceCode) {
            case AndyConstants.ServiceCode.REGISTER:

                if (parseContent.isSuccess(response)) {

                    SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(authentication.this);
                    SharedPreferences.Editor editor = appdata.edit();

                    alldetails = parseContent.getDetaillogin(response);
                    editor.putInt("daysLeft", Integer.parseInt(alldetails.get(0).get(AndyConstants.Params.DAYS_LEFT)));
                    editor.putString("userName", alldetails.get(0).get(AndyConstants.Params.NAME));
                    editor.putString("phnNumber", alldetails.get(0).get(AndyConstants.Params.MOBILE));
                    editor.putBoolean("nameNumberEntered", true);
                    editor.putBoolean("parseEntryDone", true);
                    editor.putInt("userRegistered", 0);
                    editor.putString("imei", IMEI);
                    editor.commit();

                    progress.dismiss();

                    Intent myIntent = new Intent(authentication.this, MainActivity.class);
                    startActivity(myIntent);
                    finish();

                }else {
                    String msg = parseContent.getErrorCode(response);
                    AndyUtils.showToast(
                            msg,
                            authentication.this);
                    progress.dismiss();
                }
                break;

            case AndyConstants.ServiceCode.CHECKIMEI:

                if (parseContent.isSuccess(response)) {

                    SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(authentication.this);
                    SharedPreferences.Editor editor = appdata.edit();

                    alldetails = parseContent.getDetaillogin(response);
                    editor.putInt("daysLeft", Integer.parseInt(alldetails.get(0).get(AndyConstants.Params.DAYS_LEFT)));
                    editor.putString("userName", alldetails.get(0).get(AndyConstants.Params.NAME));
                    editor.putString("phnNumber", alldetails.get(0).get(AndyConstants.Params.MOBILE));
                    editor.putBoolean("nameNumberEntered", true);
                    editor.putBoolean("parseEntryDone", true);
                    editor.putInt("userRegistered", 0);
                    editor.putString("imei", IMEI);
                    editor.commit();

                    progress.dismiss();

                    Intent myIntent = new Intent(authentication.this, MainActivity.class);
                    startActivity(myIntent);
                    finish();
                }
                else
                {
                    progress.dismiss();
                }
                break;

            default:
                break;
        }
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_WRITE);
        }*/
        else
        {
            continueLaunch();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    continueLaunch();
                } else {
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.READ_PHONE_STATE))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(authentication.this);
                        builder.setCancelable(false);
                        builder.setTitle("Permission Required");
                        builder.setMessage("Read Phone State permission is used to uniquely identify the user. Redirecting to device settings, kindly allow permission.");
                        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(authentication.this);
                        builder.setCancelable(false);
                        builder.setTitle("Permission Required");
                        builder.setMessage("This permission is required to continue using the app.");
                        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                checkPermission();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
                break;
        }
    }

    public void continueLaunch(){
        IMEI = telephonyManager.getDeviceId();
        simID = telephonyManager.getSimSerialNumber();

        setContentView(R.layout.activity_authentication);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 0, 121, 107)));
        EditText name = (EditText) findViewById(R.id.name);
        name.requestFocus();

        finalEnter = (Button) findViewById(R.id.finalEnter);
        finalEnter.setOnClickListener(this);

        parseContent = new ParseContent(this);

        checkIMEI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            checkPermission();
        }
    }
}
