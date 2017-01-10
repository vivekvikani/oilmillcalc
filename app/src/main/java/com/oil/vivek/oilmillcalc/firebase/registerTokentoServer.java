package com.oil.vivek.oilmillcalc.firebase;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oil.vivek.oilmillcalc.authentication;
import com.oil.vivek.oilmillcalc.htmlparse.AsyncTaskCompleteListener;
import com.oil.vivek.oilmillcalc.htmlparse.HttpRequester;
import com.oil.vivek.oilmillcalc.utils.AndyConstants;

import java.util.HashMap;

public class registerTokentoServer extends AppCompatActivity implements AsyncTaskCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void registerToken(final String token)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AndyConstants.URL, AndyConstants.ServiceType.REGISTERTOKEN);

        map.put(AndyConstants.Params.TOKEN, token);
        new HttpRequester(registerTokentoServer.this, map,
                AndyConstants.ServiceCode.REGISTER, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

    }
}
