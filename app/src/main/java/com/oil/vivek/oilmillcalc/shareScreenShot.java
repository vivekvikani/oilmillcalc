package com.oil.vivek.oilmillcalc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Vivek on 22-Dec-15.
 */
public class shareScreenShot {
    static Intent shareIntent;

    public void shareScreenShotM(View view, ScrollView scrollView){
        shareIntent = new Intent(Intent.ACTION_SEND);
        Bitmap bm = takeScreenShot(view,scrollView);
        File file = savePic(bm, "oilcalc.png");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Shared via RP Oil Calculator");
        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setPackage("com.whatsapp");
    }

    public Bitmap takeScreenShot(View u, ScrollView z)
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
    public static File savePic(Bitmap bm, String fileName)
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
}
