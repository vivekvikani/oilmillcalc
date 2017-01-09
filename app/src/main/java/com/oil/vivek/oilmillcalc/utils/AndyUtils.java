package com.oil.vivek.oilmillcalc.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Parsania Hardik on 19/01/2016.
 */
public class AndyUtils {

    static float density = 1;
    private static ProgressDialog mProgressDialog;
    private static Dialog mDialog;

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showSimpleProgressDialog(Context context) {
        showSimpleProgressDialog(context, null, "Loading...", false);
    }
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*public static void CustomProgressDialog(Context context, String title,
                                            String details, boolean iscancelable) {
        removeCustomProgressDialog();
        mDialog = new Dialog(context, R.style.MyDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_progress);
        ImageView imageView = (ImageView) mDialog
                .findViewById(R.id.iv_dialog_progress);
        ((TextView) mDialog.findViewById(R.id.tv_dialog_title)).setText(title);
        ((TextView) mDialog.findViewById(R.id.tv_dialog_detail))
                .setText(details);
        imageView.setBackgroundResource(R.drawable.loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) imageView
                .getBackground();
        mDialog.setCancelable(iscancelable);
        imageView.post(new Runnable() {

            @Override
            public void run() {
                frameAnimation.start();
                frameAnimation.setOneShot(false);
            }
        });

        mDialog.show();
    }*/
    public static void removeCustomProgressDialog() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
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
    public static boolean eMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }

    public static boolean passwordValidation(String passwordstring) {
        if (null == passwordstring || passwordstring.length() == 0) {
            return false;
        }
        Pattern passwordPattern = Pattern
                .compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&?*]).{6,20})");
        Matcher passwordMatcher = passwordPattern.matcher(passwordstring);
        return passwordMatcher.matches();
    }
    public static void showToast(String msg, Context ctx) {

        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

  /*  public static void showErrorToast(int id, Context ctx) {
        String msg = "Error";
        switch (id) {
            case 401:
                msg = ctx.getResources().getString(R.string.error_401);
                break;
            case 402:
                msg = ctx.getResources().getString(R.string.error_402);
                break;
            case 403:
                msg = ctx.getResources().getString(R.string.error_403);
                break;
            case 404:
                msg = ctx.getResources().getString(R.string.error_404);
                break;

            default:
                break;
        }
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }*/


}