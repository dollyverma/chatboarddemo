package com.example.chatdemo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import static com.android.volley.VolleyLog.TAG;


//import com.crashlytics.android.Crashlytics;

public class UIUtils
{
    public static void replaceView(ViewGroup containerView, View newView, View oldView) {
        containerView.addView(newView);
        containerView.removeView(oldView);
    }

    public static AlertDialog buildAlertDialog(Context context, String title, String message, String posBtnText, boolean isCancelable) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setCancelable(isCancelable);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, posBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static void logUser(Context context) {
        // You can call any combination of these three methods
//        Crashlytics.setUserIdentifier(FtcSharedPreferences.getSharedPrefString(context,ConstantValues.USER_ID));
//        Crashlytics.setUserEmail("user@fabric.io");
//        Crashlytics.setUserName("Test User");
    }

    public static AlertDialog buildAlertDialog(Context context, View view, boolean isCancelable) {
        AlertDialog dialog = new AlertDialog.Builder(view.getContext()).create();
//        dialog.setMessage(message);
//        dialog.setTitle(title);
        dialog.setCancelable(isCancelable);
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, posBtnText, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                buttonsListener.onPositiveButtonPress();
//            }
//        });
        return dialog;
    }

//    public static

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void reload(Activity activity) {
        Intent intent = activity.getIntent();
        activity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }

    public static String getVersionName(Context activity) {
        return "2.0";
    }

    public static String getVersionCode(Context activity) {
        return "2.0";
    }

    public static boolean isNetworkAvailable(Context context) {
         ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.d(TAG, "isNetworkAvailable: "+isConnected);
        return isConnected;
    }

    public static double screenSize(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        double wi = (double) width / (double) dm.xdpi;
        double hi = (double) height / (double) dm.ydpi;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        System.out.println("ScreenSize width = " + width + " height = " + height + " wi= " + wi + " hi= " + hi + " x = " + x + " y= " + y + " screenInches " + screenInches);
        return screenInches;
    }


}