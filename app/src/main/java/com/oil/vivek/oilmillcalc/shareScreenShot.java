package com.oil.vivek.oilmillcalc;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Vivek on 22-Dec-15.
 */
public class shareScreenShot extends Activity{
    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE = 1;
    private static final int REQUEST_PERMISSION_SETTING = 2;
    static Intent shareIntent;
    Bitmap bm;
    Context ctx;

    public void shareScreenShotM(View view, ScrollView scrollView, Context context){
        ctx = context;
        bm = takeScreenShot(view,scrollView);
        checkPermission();
    }

    private void continueShare()
    {
        shareIntent = new Intent(Intent.ACTION_SEND);
        File file = savePic(bm, "oilcalc.png");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Shared via RP Oil Calculator");
        //Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        Uri uri = FileProvider.getUriForFile(ctx,
                BuildConfig.APPLICATION_ID + ".provider",
                new File(file.getAbsolutePath()));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setPackage("com.whatsapp");
        ctx.startActivity(shareIntent);
    }

    private Bitmap takeScreenShot(View u, ScrollView z)
    {
        u.setDrawingCacheEnabled(true);
        int totalHeight = z.getChildAt(0).getHeight();
        int totalWidth = z.getChildAt(0).getWidth();
        u.layout(0, 0, totalWidth, totalHeight);
        u.buildDrawingCache();
        Bitmap b = Bitmap.createBitmap(u.getDrawingCache());
        u.setDrawingCacheEnabled(false);
        u.destroyDrawingCache();
        /*View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height  - statusBarHeight);
        view.destroyDrawingCache();*/
        return b;
    }

    private static File savePic(Bitmap bm, String fileName)
    {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) ctx,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_WRITE);
        }
        else
        {
            continueShare();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    continueShare();
                } else {
                    System.out.println("Else");
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.READ_PHONE_STATE))
                    {
                        System.out.println("Alert show !");
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setCancelable(false);
                        builder.setTitle("Permission Required");
                        builder.setMessage("Storage permission is required to save the screenshot. Redirecting to device settings, kindly allow permission.");
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
                        System.out.println("Alert Show");
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            checkPermission();
        }
    }
}
