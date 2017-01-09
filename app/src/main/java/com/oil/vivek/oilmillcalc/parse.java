package com.oil.vivek.oilmillcalc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Vivek on 17-Dec-15.
 */
public class parse {
    final ParseObject testObject = new ParseObject("Data");


    public int queryParseIMEI(String IMEI, String currentVersion)  //RETURNS DAYS LEFT
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Data");
        query.whereEqualTo("IMEI", IMEI);
        int daysLeft=0;
        List<ParseObject> objects;
        try {
            objects = query.find();
            if(objects.isEmpty())
            {
                return -101;
            }
            else
            {
                for(ParseObject obj : objects)
                {
                    daysLeft = obj.getInt("Days_Left");

                    String versionInParse = obj.getString("Version");
                    if(!currentVersion.equals(versionInParse))
                    {
                        obj.put("Version", currentVersion);
                        obj.saveInBackground();
                    }
                }
            }
        } catch (ParseException e) {
            //e.printStackTrace();
            if(e.getCode() == 100)
            {
                return -404; //No INTERNET
            }
        }
        return daysLeft;
    }


    public void queryParseIMEIinBG(Context context, String IMEI, String currentVersion)  //RETURNS DAYS LEFT
    {
        int daysLeft=0;
        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = appdata.edit();

        Calendar calendar = new GregorianCalendar();
        int todayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int lastAccessDayOfMonth = appdata.getInt("lastAccessDayofMonth", 0);
        if(todayDayOfMonth != lastAccessDayOfMonth)
        {
            daysLeft = appdata.getInt("daysLeft", 0);
            int temp;
            if(daysLeft>0){
                temp = daysLeft - 1;
            }else
            {
                temp = daysLeft;
            }
            editor.putInt("daysLeft", temp);

            lastAccessDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            editor.putInt("lastAccessDayofMonth", lastAccessDayOfMonth);
            editor.commit();
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Data");
        query.whereEqualTo("IMEI", IMEI);
        int daysLeftfromParse=0;
        daysLeft = appdata.getInt("daysLeft", 0);
        List<ParseObject> objects;
        try
        {
            objects = query.find();
            if(objects.isEmpty())
            {
                editor.putBoolean("nameNumberEntered",false);
                editor.commit();
            }
            else
            {
                for(ParseObject obj : objects)
                {
                    daysLeftfromParse = obj.getInt("Days_Left");
                 //   Log.d("scorses", "Parse days Left : " +daysLeftfromParse);
                    if(daysLeftfromParse == 0)
                    {
                        editor.putInt("daysLeft", daysLeftfromParse);
                        editor.commit();
                    }
                    else if(daysLeft > daysLeftfromParse)
                    {
                   //     Log.d("scorses", "Saving Parse to SP");
                        editor.putInt("daysLeft", daysLeftfromParse);
                        editor.commit();
                    }
                    else if(daysLeft < daysLeftfromParse)
                    {
                   //    Log.d("scorses", "Saving SP to Parse");
                        obj.put("Days_Left",daysLeft);
                        obj.saveInBackground();
                    }

                    String versionInParse = obj.getString("Version");
                    if(!currentVersion.equals(versionInParse))
                    {
                        obj.put("Version", currentVersion);
                        obj.saveInBackground();
                    }
                }
            }
        }
        catch (ParseException e)
        {
            //e.printStackTrace();
            if(e.getCode() == 100)
            {

            }
        }

    }


    public int savetoParse(String name,String number,String key,String IMEI,String locationString,String simID,String operatorName,String VersionNumber) throws ParseException {
        testObject.put("Name", name);
        testObject.put("Phone_Number", number);
        testObject.put("Key", key);
        testObject.put("IMEI", IMEI);
        testObject.put("Location", locationString);
        testObject.put("simID", simID);
        testObject.put("Operator_Name",operatorName);
        testObject.put("Version", VersionNumber);
        testObject.put("Days_Left", 3);
        testObject.save();
        return 1;
    }
}
