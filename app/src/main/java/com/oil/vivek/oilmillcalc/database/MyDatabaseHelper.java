package com.oil.vivek.oilmillcalc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static com.oil.vivek.oilmillcalc.database.Constants.EIGHT_COLUMN;
import static com.oil.vivek.oilmillcalc.database.Constants.FIFTH_COLUMN;
import static com.oil.vivek.oilmillcalc.database.Constants.FIRST_COLUMN;
import static com.oil.vivek.oilmillcalc.database.Constants.FOURTH_COLUMN;
import static com.oil.vivek.oilmillcalc.database.Constants.SECOND_COLUMN;
import static com.oil.vivek.oilmillcalc.database.Constants.SEVENTH_COLUMN;
import static com.oil.vivek.oilmillcalc.database.Constants.SIXTH_COLUMN;
import static com.oil.vivek.oilmillcalc.database.Constants.THIRD_COLUMN;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 30;
    public static final String DATABASE_NAME = "mainDB.db";
    public static final String TABLE_DETAILS = "customer_details";
    public static final String TABLE_ACCOUNT = "customer_account";
    public static final String TABLE_DEBIT_ACCOUNT = "customer_debit_account";
    public static final String TABLE_UPAD = "upad_table";
    public static final String TABLE_LEAVE = "leave_table";
    public static final String TABLE_EMPLOYEE = "employee_table";
    public static final String DATE = "date";
    public static final String FROMDATE = "from_date";
    public static final String TODATE = "to_date";
    public static final String TOTALLEAVE = "total_leave";
    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String AM_PM = "am_pm";
    public static final String NOTE = "note";
    public static final String AMNT = "amt";
    public static final String CREDITorDEBIT = "c_d";
    public static final String TOTALAMNT = "total_amt";
    public static final String TOTALCREDITorDEBIT = "total_c_d";
    public static final String UID = "uid";
    public static final String ACCOUNTUID = "account_uid";
    public static final String UPADUID = "upad_uid";
    public static final String LEAVEUID = "leave_uid";
    public static final String PIC = "profile_pic";
    public static final String FNAME = "first_name";
    public static final String LNAME = "last_name";
    public static final String NICKNAME = "nickname";
    public static final String ADDR = "address";
    public static final String MOB = "mobile_no";
    public static final String EMAIL = "email";
    public static final String CITY = "city";
    public static final String FRIENDS = "friends";
    public static final String FAV = "favourite";
    public static final String DOB = "dob";
    public static final String Mdate = "marriage_date";
    public static final String PROFINFO = "professional_info";
    public static final String SALARY = "salary";
    public static final String JDATE = "jdate";
    public static final String TIMING = "timing";
    public static final String MONTHYEAR = "month_year";

    public static final String paanType = "c1";
    public static final String sopari1 = "c2";
    public static final String sopari2 = "c3";
    public static final String sopari3 = "c4";
    public static final String sopariExtra = "c5";
    public static final String chunno1 = "c6";
    public static final String chunno2 = "c7";
    public static final String chunno3 = "c8";
    public static final String chunnoExtra = "c9";
    public static final String tabacco1 = "c10";
    public static final String tabacco2 = "c11";
    public static final String tabacco3 = "c12";
    public static final String tabaccoExtra = "c13";
    public static final String mukhwas1 = "c14";
    public static final String mukhwas2 = "c15";
    public static final String mukhwas3 = "c16";
    public static final String mukhwasExtra = "c17";
    public static final String katho1 = "c18";
    public static final String katho2 = "c19";
    public static final String kathoExtra = "c20";
    public static final String sadu1 = "c21";
    public static final String saduExtra = "c22";


    public static ArrayList<String> fnames,fnamesSMS;
    public static ArrayList<String> lnames,lnamesSMS;
    public static ArrayList<String> uid,uidSMS;
    public static ArrayList<String> starValue;
    public static ArrayList<String> nickname,nicknameSMS;
    public static ArrayList<String> number,numberSMS,prof;
    public static ArrayList<String> city;
    public static ArrayList<String> debitAmtSMS;

    public static Bitmap theImage;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);*/
        String query = "CREATE TABLE " + TABLE_DETAILS + "(" +
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                PIC + " TEXT, " +
                FNAME + " TEXT, " +
                LNAME + " TEXT, " +
                NICKNAME + " TEXT, " +
                MOB + " TEXT NOT NULL UNIQUE, " +
                EMAIL + " TEXT, " +
                ADDR + " TEXT, " +
                CITY + " TEXT, " +
                FRIENDS + " TEXT, " +
                FAV + " TEXT, " +
                DOB + " TEXT, " +
                Mdate + " TEXT, " +
                PROFINFO + " TEXT, " +
                paanType + " TEXT, " +
                sopari1 + " TEXT, " +
                sopari2 + " TEXT, " +
                sopari3 + " TEXT, " +
                sopariExtra + " TEXT, " +
                chunno1 + " TEXT, " +
                chunno2 + " TEXT, " +
                chunno3 + " TEXT, " +
                chunnoExtra + " TEXT, " +
                tabacco1 + " TEXT, " +
                tabacco2 + " TEXT, " +
                tabacco3 + " TEXT, " +
                tabaccoExtra + " TEXT, " +
                mukhwas1 + " TEXT, " +
                mukhwas2 + " TEXT, " +
                mukhwas3 + " TEXT, " +
                mukhwasExtra + " TEXT, " +
                katho1 + " TEXT, " +
                katho2 + " TEXT, " +
                kathoExtra + " TEXT, " +
                sadu1 + " TEXT, " +
                saduExtra + " TEXT " +
                ")";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_ACCOUNT + "(" +
                ACCOUNTUID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                UID + " INTEGER," +
                DAY + " INTEGER, " +
                MONTH + " INTEGER, " +
                YEAR + " INTEGER, " +
                HOUR + " INTEGER, " +
                MINUTE + " INTEGER, " +
                AM_PM + " INTEGER, " +
                NOTE + " TEXT, " +
                AMNT + " INTEGER, " +
                CREDITorDEBIT + " TEXT " +
                ")";
        db.execSQL(query2);

        String query3 = "CREATE TABLE " + TABLE_UPAD + "(" +
                UPADUID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                UID + " INTEGER," +
                DATE + " TEXT, " +
                AMNT + " INTEGER, " +
                NOTE + " INTEGER, " +
                MONTHYEAR + " TEXT " +
                ")";
        db.execSQL(query3);

        String query4 = "CREATE TABLE " + TABLE_LEAVE + "(" +
                LEAVEUID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                UID + " INTEGER, " +
                FROMDATE + " TEXT, " +
                TODATE + " TEXT, " +
                TOTALLEAVE + " INTEGER, " +
                NOTE + " TEXT, " +
                MONTHYEAR + " TEXT " +
                ")";
        db.execSQL(query4);

        String query5 = "CREATE TABLE " + TABLE_EMPLOYEE + "(" +
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                PIC + " TEXT, " +
                FNAME + " TEXT, " +
                LNAME + " TEXT, " +
                NICKNAME + " TEXT, " +
                MOB + " TEXT NOT NULL UNIQUE, " +
                EMAIL + " TEXT, " +
                ADDR + " TEXT, " +
                CITY + " TEXT, " +
                FRIENDS + " TEXT, " +
                DOB + " TEXT, " +
                Mdate + " TEXT, " +
                SALARY + " TEXT, " +
                JDATE + " TEXT, " +
                TIMING + " TEXT " +
                ")";
        db.execSQL(query5);

        String query6 = "CREATE TABLE " + TABLE_DEBIT_ACCOUNT + "(" +
                ACCOUNTUID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                UID + " INTEGER," +
                TOTALAMNT + " INTEGER, " +
                TOTALCREDITorDEBIT + " TEXT " +
                ")";
        db.execSQL(query6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPAD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBIT_ACCOUNT);
        String query6 = "CREATE TABLE " + TABLE_DEBIT_ACCOUNT + "(" +
                ACCOUNTUID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                UID + " INTEGER," +
                TOTALAMNT + " INTEGER, " +
                TOTALCREDITorDEBIT + " TEXT " +
                ")";
        db.execSQL(query6);
    }

    public Boolean addProduct(Context context, String pic, String fname, String lname, String nickname, String mob, String email, String address, String city, String friends, String fav, String dob,String mdate,String prof_info, String paanTypeS, String sopari1S, String sopari2S, String sopari3S, String sopariExtraS, String chunno1S, String chunno2S, String chunno3S, String chunnoExtraS, String tamaku1S, String tamaku2S, String tamaku3S, String tamakuExtraS, String mukhwas1S, String mukhwas2S, String mukhwas3S, String mukhwasExtraS,String katho1S, String katho2S, String kathoExtraS, String sadu1s, String saduExtraS)
    {
        ContentValues values = new ContentValues();
        values.put(PIC,pic);
        values.put(FNAME, fname);
        values.put(LNAME, lname);
        values.put(NICKNAME,nickname);
        values.put(MOB,mob);
        values.put(EMAIL,email);
        values.put(ADDR,address);
        values.put(CITY,city);
        values.put(FRIENDS,friends);
        values.put(FAV, fav);
        values.put(DOB, dob);
        values.put(Mdate, mdate);
        values.put(PROFINFO, prof_info);
        values.put(paanType, paanTypeS);
        values.put(sopari1, sopari1S);
        values.put(sopari2,sopari2S);
        values.put(sopari3,sopari3S);
        values.put(sopariExtra, sopariExtraS);
        values.put(chunno1, chunno1S);
        values.put(chunno2,chunno2S);
        values.put(chunno3,chunno3S);
        values.put(chunnoExtra, chunnoExtraS);
        values.put(tabacco1, tamaku1S);
        values.put(tabacco2,tamaku2S);
        values.put(tabacco3,tamaku3S);
        values.put(tabaccoExtra, tamakuExtraS);
        values.put(mukhwas1, mukhwas1S);
        values.put(mukhwas2,mukhwas2S);
        values.put(mukhwas3,mukhwas3S);
        values.put(mukhwasExtra, mukhwasExtraS);
        values.put(katho1, katho1S);
        values.put(katho2,katho2S);
        values.put(kathoExtra, kathoExtraS);
        values.put(sadu1,sadu1s);
        values.put(saduExtra,saduExtraS);
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.insertOrThrow(TABLE_DETAILS, null, values);
            db.close();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "Duplicate Number Found. Contact not Added", Toast.LENGTH_LONG).show();
            db.close();
            return false;
        }
    }

    public Boolean updateProduct(Context context, String[] uid, String pic, String fname, String lname, String nickname, String mob, String email, String address, String city, String friends, String fav, String dob,String mdate,String prof_info, String paanTypeS, String sopari1S, String sopari2S, String sopari3S, String sopariExtraS, String chunno1S, String chunno2S, String chunno3S, String chunnoExtraS, String tamaku1S, String tamaku2S, String tamaku3S, String tamakuExtraS, String mukhwas1S, String mukhwas2S, String mukhwas3S, String mukhwasExtraS,String katho1S, String katho2S, String kathoExtraS, String sadu1s, String saduExtraS)
    {
        ContentValues values = new ContentValues();
        values.put(PIC,pic);
        values.put(FNAME, fname);
        values.put(LNAME, lname);
        values.put(NICKNAME,nickname);
        values.put(MOB,mob);
        values.put(EMAIL,email);
        values.put(ADDR,address);
        values.put(CITY,city);
        values.put(FRIENDS,friends);
        values.put(DOB, dob);
        values.put(Mdate, mdate);
        values.put(PROFINFO, prof_info);
        values.put(paanType, paanTypeS);
        values.put(sopari1, sopari1S);
        values.put(sopari2,sopari2S);
        values.put(sopari3,sopari3S);
        values.put(sopariExtra, sopariExtraS);
        values.put(chunno1, chunno1S);
        values.put(chunno2,chunno2S);
        values.put(chunno3,chunno3S);
        values.put(chunnoExtra, chunnoExtraS);
        values.put(tabacco1, tamaku1S);
        values.put(tabacco2,tamaku2S);
        values.put(tabacco3,tamaku3S);
        values.put(tabaccoExtra, tamakuExtraS);
        values.put(mukhwas1, mukhwas1S);
        values.put(mukhwas2,mukhwas2S);
        values.put(mukhwas3,mukhwas3S);
        values.put(mukhwasExtra, mukhwasExtraS);
        values.put(katho1, katho1S);
        values.put(katho2,katho2S);
        values.put(kathoExtra, kathoExtraS);
        values.put(sadu1,sadu1s);
        values.put(saduExtra,saduExtraS);
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.update(TABLE_DETAILS, values, "uid=" + uid[0], null);
            db.close();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "Duplicate Number Found. Contact not Updated", Toast.LENGTH_LONG).show();
            db.close();
            return false;
        }
    }

    public HashMap getAllDetails(int uid){
        SQLiteDatabase db = getWritableDatabase();
        HashMap<String,String> all_details = new HashMap<String,String>();

        String sql = "SELECT * FROM customer_details WHERE uid = '" + uid + "'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                all_details.put("Pic",c.getString(c.getColumnIndex(PIC)));

                all_details.put("Email", c.getString(c.getColumnIndex(EMAIL)));
                all_details.put("Friend", c.getString(c.getColumnIndex(FRIENDS)));
                all_details.put("DOB", c.getString(c.getColumnIndex(DOB)));
                all_details.put("Mdate", c.getString(c.getColumnIndex(Mdate)));
                all_details.put("Prof_Info", c.getString(c.getColumnIndex(PROFINFO)));
                all_details.put("paanType", c.getString(c.getColumnIndex(paanType)));
                all_details.put("s1", c.getString(c.getColumnIndex(sopari1)));
                all_details.put("s2", c.getString(c.getColumnIndex(sopari2)));
                all_details.put("s3", c.getString(c.getColumnIndex(sopari3)));
                all_details.put("sExtra", c.getString(c.getColumnIndex(sopariExtra)));
                all_details.put("c1", c.getString(c.getColumnIndex(chunno1)));
                all_details.put("c2", c.getString(c.getColumnIndex(chunno2)));
                all_details.put("c3", c.getString(c.getColumnIndex(chunno3)));
                all_details.put("cExtra", c.getString(c.getColumnIndex(chunnoExtra)));
                all_details.put("t1", c.getString(c.getColumnIndex(tabacco1)));
                all_details.put("t2", c.getString(c.getColumnIndex(tabacco2)));
                all_details.put("t3", c.getString(c.getColumnIndex(tabacco3)));
                all_details.put("tExtra", c.getString(c.getColumnIndex(tabaccoExtra)));
                all_details.put("m1", c.getString(c.getColumnIndex(mukhwas1)));
                all_details.put("m2", c.getString(c.getColumnIndex(mukhwas2)));
                all_details.put("m3", c.getString(c.getColumnIndex(mukhwas3)));
                all_details.put("mExtra", c.getString(c.getColumnIndex(mukhwasExtra)));
                all_details.put("k1", c.getString(c.getColumnIndex(katho1)));
                all_details.put("k2", c.getString(c.getColumnIndex(katho2)));
                all_details.put("kExtra", c.getString(c.getColumnIndex(kathoExtra)));
                all_details.put("sadu1", c.getString(c.getColumnIndex(sadu1)));
                all_details.put("saduExtra", c.getString(c.getColumnIndex(saduExtra)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return all_details;
    }


    public ArrayList<HashMap<String,String>> getFavItems()
    {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<HashMap<String,String>> Fav_Items = new ArrayList<HashMap<String,String>>();
        String value = "T";
        String sql = "SELECT first_name,last_name,mobile_no,uid FROM customer_details WHERE favourite = '" + value + "'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put(FIRST_COLUMN, c.getString(c.getColumnIndex(FNAME)));
                temp.put(SECOND_COLUMN, c.getString(c.getColumnIndex(LNAME)));
                temp.put(THIRD_COLUMN, c.getString(c.getColumnIndex(MOB)));
                temp.put(FOURTH_COLUMN, c.getString(c.getColumnIndex(UID)));
                Fav_Items.add(temp);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        Collections.sort(Fav_Items, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(FIRST_COLUMN).compareTo(rhs.get(FIRST_COLUMN));
            }
        });
        return Fav_Items;
    }

    public void getAllNames()
    {
        SQLiteDatabase db = getWritableDatabase();
        fnames = new ArrayList<String>();
        lnames = new ArrayList<String>();
        uid = new ArrayList<String>();
        starValue = new ArrayList<String>();
        nickname = new ArrayList<String>();
        city = new ArrayList<String>();
        number = new ArrayList<String>();
        prof = new ArrayList<String>();

       /* Cursor c = db.rawQuery(" SELECT " + COLUMN_KEY + " FROM " + TABLE_DETAILS + " WHERE " + COLUMN_BKNAME + "=\"" + bookName + "\";",null ); */
        Cursor c = db.rawQuery("SELECT first_name,last_name,mobile_no,uid,nickname,favourite,city,professional_info FROM customer_details", null);
        if (c.moveToFirst()) {
            do {
                fnames.add(c.getString(c.getColumnIndex(FNAME)));
                lnames.add(c.getString(c.getColumnIndex(LNAME)));
                uid.add(String.valueOf(c.getInt(c.getColumnIndex(UID))));
                starValue.add(c.getString(c.getColumnIndex(FAV)));
                city.add(c.getString(c.getColumnIndex(CITY)));
                nickname.add(c.getString(c.getColumnIndex(NICKNAME)));
                number.add(c.getString(c.getColumnIndex(MOB)));
                prof.add(c.getString(c.getColumnIndex(PROFINFO)));

            }while (c.moveToNext());
        }

        c.close();
        db.close();
    }

    public void getAllDebitAccount()
    {
        SQLiteDatabase db = getWritableDatabase();
        fnamesSMS = new ArrayList<String>();
        lnamesSMS = new ArrayList<String>();
        uidSMS = new ArrayList<String>();
        nicknameSMS = new ArrayList<String>();
        numberSMS = new ArrayList<String>();
        debitAmtSMS = new ArrayList<String>();
        String D = "D";
        Cursor c = db.rawQuery("SELECT customer_details.uid,first_name,last_name,nickname,mobile_no,total_amt FROM customer_details INNER JOIN customer_debit_account ON customer_details.uid = customer_debit_account.uid WHERE customer_debit_account.total_c_d = '" + D + "'", null); //
        if (c.moveToFirst()) {
            do {
                fnamesSMS.add(c.getString(c.getColumnIndex(FNAME)));
                lnamesSMS.add(c.getString(c.getColumnIndex(LNAME)));
                nicknameSMS.add(c.getString(c.getColumnIndex(NICKNAME)));
                String MobOrg = c.getString(c.getColumnIndex(MOB));
                String[] mobArray = MobOrg.split(";");
                numberSMS.add(mobArray[0]);
                uidSMS.add(String.valueOf(c.getInt(c.getColumnIndex(UID))));
                debitAmtSMS.add(String.valueOf(Math.abs(c.getInt(c.getColumnIndex(TOTALAMNT)))));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
    }

    public void account_add(int uid, int day, int month, int year, int hour, int min, int am_pm, String note, int amount, String debit_credit)
    {
        ContentValues values = new ContentValues();
        values.put(UID,uid);
        values.put(DAY, day);
        values.put(MONTH,month);
        values.put(YEAR,year);
        values.put(HOUR,hour);
        values.put(MINUTE, min);
        values.put(AM_PM, am_pm);
        values.put(NOTE, note);
        values.put(AMNT, amount);
        values.put(CREDITorDEBIT, debit_credit);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    public ArrayList<HashMap<String,String>> get_account_details(int uid, int month, int year)
    {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<HashMap<String,String>> account_details = new ArrayList<HashMap<String,String>>();
        String sql = "SELECT * FROM customer_account WHERE uid = '" + uid + "' AND year = '" + year + "' AND month = '" + month + "'";
        Cursor c = db.rawQuery(sql, null);
        HashMap<String,String> temp = null;
        if (c.moveToFirst()) {
            do {
                temp=new HashMap<String, String>();

                temp.put(SECOND_COLUMN, c.getString(c.getColumnIndex(HOUR)));
                temp.put(THIRD_COLUMN, c.getString(c.getColumnIndex(MINUTE)));
                temp.put(FOURTH_COLUMN, c.getString(c.getColumnIndex(AM_PM)));
                temp.put(FIFTH_COLUMN, c.getString(c.getColumnIndex(NOTE)));
                temp.put(SIXTH_COLUMN, c.getString(c.getColumnIndex(AMNT)));
                temp.put(SEVENTH_COLUMN, c.getString(c.getColumnIndex(CREDITorDEBIT)));
                account_details.add(temp);
            }while (c.moveToNext());
        }
        c.close();
        db.close();

        Collections.sort(account_details, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(FIRST_COLUMN).compareTo(rhs.get(FIRST_COLUMN));
            }
        });

        Collections.reverse(account_details);
        return account_details;
    }


    public ArrayList<String> getUnique_Month(Integer uid, Integer year)
    {
        ArrayList<String> months= new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT DISTINCT month FROM customer_account WHERE uid = '" + uid + "' AND year = '" + year + "'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                    months.add(String.valueOf(c.getInt(c.getColumnIndex(MONTH))));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        Collections.sort(months, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        return months;
    }

    public ArrayList<String> getUnique_Year(Integer uid)
    {
        ArrayList<String> years= new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT DISTINCT year FROM customer_account WHERE uid = '" + uid + "'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                years.add(String.valueOf(c.getInt(c.getColumnIndex(YEAR))));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        Collections.sort(years, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });

        return years;
    }

    public int getTotal(int UID)
    {
        int total = 0;
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT amt,c_d FROM customer_account WHERE uid = '" + UID + "'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                int temp = c.getInt(c.getColumnIndex(AMNT));
                String CorD= c.getString(c.getColumnIndex(CREDITorDEBIT));

                if(CorD.equals("d"))
                {
                    total = total-temp;
                }
                else if(CorD.equals("c"))
                {
                    total = total+temp;
                }
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return total;
    }

    public void getalluid()
    {
        ArrayList<Integer> uids = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT uid FROM customer_details";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                uids.add(c.getInt(c.getColumnIndex(UID)));
            }while (c.moveToNext());
        }

        for(Integer temp : uids)
            createTotalEntry(temp);

        c.close();
        db.close();
    }
    public void createTotalEntry(int uid)
    {
        ContentValues values = new ContentValues();
        values.put(UID, uid);
        values.put(TOTALAMNT, 0);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_DEBIT_ACCOUNT, null, values);
        db.close();
    }

    public int getLastRecordUID()
    {
        int uid = 0;
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT uid FROM customer_details ORDER BY uid DESC LIMIT 1";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                uid = c.getInt(c.getColumnIndex(UID));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        System.out.println("Last UID" +uid);
        return uid;
    }

    public void setTotal(int uid, int totalAmt, String DorC)
    {
        ContentValues values = new ContentValues();
        values.put(TOTALAMNT, totalAmt);
        values.put(TOTALCREDITorDEBIT, DorC);
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_DEBIT_ACCOUNT, values, "uid=" + uid, null);
        db.close();
    }

    public void delete_user_records(int uid)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DETAILS + " WHERE " + UID + "=\"" + uid + "\";" );
        db.execSQL("DELETE FROM " + TABLE_ACCOUNT + " WHERE " + UID + "=\"" + uid + "\";");
        db.execSQL("DELETE FROM " + TABLE_DEBIT_ACCOUNT + " WHERE " + UID + "=\"" + uid + "\";");
        db.close();
    }

    public void addUpadRecord(int uid, String date, String amt, String note, String monthYear)
    {
        Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(UID,uid);
        values.put(DATE,date);
        values.put(AMNT,amt);
        values.put(NOTE, note);
        values.put(MONTHYEAR, monthYear);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_UPAD, null, values);
        db.close();
    }

    public void addLeaveRecord(int uid, String fromDate, String toDate, int days, String note, String monthYear)
    {
        Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(UID,uid);
        values.put(FROMDATE,fromDate);
        values.put(TODATE,toDate);
        values.put(TOTALLEAVE,days);
        values.put(NOTE, note);
        values.put(MONTHYEAR, monthYear);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LEAVE, null, values);
        db.close();
    }

    public Boolean addEmployee(Context context, String pic, String fname, String lname, String nickname, String mob, String email, String address, String city, String friends, String dob,String mdate, String Salary, String jDate, String timing)
    {
        ContentValues values = new ContentValues();
        values.put(PIC,pic);
        values.put(FNAME, fname);
        values.put(LNAME, lname);
        values.put(NICKNAME,nickname);
        values.put(MOB,mob);
        values.put(EMAIL,email);
        values.put(ADDR, address);
        values.put(CITY, city);
        values.put(FRIENDS,friends);
        values.put(DOB, dob);
        values.put(Mdate, mdate);
        values.put(SALARY, Salary);
        values.put(JDATE, jDate);
        values.put(TIMING, timing);
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.insertOrThrow(TABLE_EMPLOYEE, null, values);
            db.close();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "Duplicate Number Found. Contact not Added", Toast.LENGTH_LONG).show();
            db.close();
            return false;
        }
    }

    public Boolean updateEmployee(Context context, String[] uid, String pic, String fname, String lname, String nickname, String mob, String email, String address, String city, String Salary, String jDate, String timing)
    {
        ContentValues values = new ContentValues();
        values.put(PIC,pic);
        values.put(FNAME, fname);
        values.put(LNAME, lname);
        values.put(NICKNAME,nickname);
        values.put(MOB,mob);
        values.put(EMAIL,email);
        values.put(ADDR, address);
        values.put(CITY, city);
        values.put(SALARY, Salary);
        values.put(JDATE, jDate);
        values.put(TIMING, timing);
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.update(TABLE_EMPLOYEE, values, "uid=" + uid[0], null);
            db.close();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "Duplicate Number Found. Contact not Updated", Toast.LENGTH_LONG).show();
            db.close();
            return false;
        }
    }

    public void delete_emp_records(int uid)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EMPLOYEE + " WHERE " + UID + "=\"" + uid + "\";" );
        db.execSQL("DELETE FROM " + TABLE_LEAVE + " WHERE " + UID + "=\"" + uid + "\";");
        db.execSQL("DELETE FROM " + TABLE_UPAD + " WHERE " + UID + "=\"" + uid + "\";");
        db.close();
    }

    public ArrayList<HashMap<String,String>> getAllEmployees()
    {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<HashMap<String,String>> Fav_Items = new ArrayList<HashMap<String,String>>();
        String sql = "SELECT first_name,last_name,uid FROM employee_table";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put(FIRST_COLUMN, c.getString(c.getColumnIndex(FNAME)));
                temp.put(SECOND_COLUMN, c.getString(c.getColumnIndex(LNAME)));
                temp.put(THIRD_COLUMN, c.getString(c.getColumnIndex(UID)));

                Fav_Items.add(temp);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        Collections.sort(Fav_Items, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(FIRST_COLUMN).compareTo(rhs.get(FIRST_COLUMN));
            }
        });
        return Fav_Items;
    }

    public ArrayList<HashMap<String,String>> getFullEmployeeDetails(int uid)
    {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<HashMap<String,String>> all_emp_details = new ArrayList<HashMap<String,String>>();
        String sql = "SELECT * FROM employee_table WHERE uid = '" + uid + "'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put("PIC", c.getString(c.getColumnIndex(PIC)));
                temp.put(FIRST_COLUMN, c.getString(c.getColumnIndex(UID)));
                temp.put(SECOND_COLUMN, c.getString(c.getColumnIndex(FNAME)));
                temp.put(THIRD_COLUMN, c.getString(c.getColumnIndex(LNAME)));
                temp.put(FOURTH_COLUMN, c.getString(c.getColumnIndex(NICKNAME)));
                temp.put(FIFTH_COLUMN, c.getString(c.getColumnIndex(MOB)));
                temp.put(SIXTH_COLUMN, c.getString(c.getColumnIndex(EMAIL)));
                temp.put(SEVENTH_COLUMN, c.getString(c.getColumnIndex(ADDR)));
                temp.put(EIGHT_COLUMN, c.getString(c.getColumnIndex(CITY)));
                temp.put("Salary", c.getString(c.getColumnIndex(SALARY)));
                temp.put("Jdate", c.getString(c.getColumnIndex(JDATE)));
                temp.put("Timing", c.getString(c.getColumnIndex(TIMING)));
                all_emp_details.add(temp);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        Collections.sort(all_emp_details, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(SECOND_COLUMN).compareTo(rhs.get(SECOND_COLUMN));
            }
        });
        return all_emp_details;
    }

    public ArrayList<String> upadDates(int uid)
    {
        ArrayList<String> dates = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT date FROM upad_table WHERE uid = '" + uid + "'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                dates.add(c.getString(c.getColumnIndex(DATE)));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return dates;
    }

    public ArrayList<String> leaveDates(int uid)
    {
        ArrayList<String> dates = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
         String sql = "SELECT from_date FROM leave_table WHERE uid = '" + uid + "'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                dates.add(c.getString(c.getColumnIndex(FROMDATE)));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return dates;
    }

    public ArrayList<HashMap<String,String>> empUpadRecords(String monthyear, int uid)
    {
        ArrayList<HashMap<String,String>> upad_records = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM upad_table WHERE month_year = '" + monthyear + "' AND uid = '" + uid +"'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String,String> temp = new HashMap<>();
                temp.put(UPADUID,c.getString(c.getColumnIndex(UPADUID)));
                temp.put(DATE,c.getString(c.getColumnIndex(DATE)));
                temp.put(AMNT,c.getString(c.getColumnIndex(AMNT)));
                temp.put(NOTE,c.getString(c.getColumnIndex(NOTE)));
                upad_records.add(temp);
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        Collections.sort(upad_records, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(DATE).compareTo(rhs.get(DATE));
            }
        });

        return upad_records;
    }

    public ArrayList<HashMap<String,String>> empLeaveRecords(String monthyear, int uid)
    {
        ArrayList<HashMap<String,String>> leave_records = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM leave_table WHERE month_year = '" + monthyear + "' AND uid = '" + uid +"'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String,String> temp = new HashMap<>();
                temp.put(LEAVEUID,c.getString(c.getColumnIndex(LEAVEUID)));
                temp.put(FROMDATE,c.getString(c.getColumnIndex(FROMDATE)));
                temp.put(TODATE,c.getString(c.getColumnIndex(TODATE)));
                temp.put(NOTE,c.getString(c.getColumnIndex(NOTE)));
                temp.put(TOTALLEAVE,c.getString(c.getColumnIndex(TOTALLEAVE)));
                leave_records.add(temp);
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        Collections.sort(leave_records, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(FROMDATE).compareTo(rhs.get(FROMDATE));
            }
        });

        return leave_records;
    }

    public void deleteLeaveRecord(int leave_uid)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LEAVE + " WHERE " + LEAVEUID + "=\"" + leave_uid + "\";");
        db.close();
    }

    public void deleteUpadRecord(int upad_uid)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_UPAD + " WHERE " + UPADUID + "=\"" + upad_uid + "\";");
        db.close();
    }

}
