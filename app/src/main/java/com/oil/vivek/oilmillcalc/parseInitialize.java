package com.oil.vivek.oilmillcalc;

import android.app.Application;

//import com.oilCalc.vivek.oilmillcalc.R;
import com.parse.Parse;

/**
 * Created by Vivek on 19-Dec-15.
 */
public class parseInitialize extends Application {
    @Override
    public void onCreate() {
        Parse.initialize(this,getString(R.string.parse_app_id),getString(R.string.parse_client_key));
    }
}
