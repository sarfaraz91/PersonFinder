package com.example.personfinder.GeneralClasses;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.personfinder.Arrays.MenuArray;
import com.example.personfinder.Database.SqliteDataHelper;
import com.example.personfinder.R;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ResourceType")
public class Global {

    public static boolean useFontFotViews = true;

    public static String user_id = "";

    public static String service_type = "";

    public static String service_price = "";

    public static boolean gotoHome = false;
    public static boolean isFromThankyou = false;

    public static boolean isSiftOn = true;
    public static boolean isBackFunctionally = false;
    public static String device_back_tag = "";

    public static boolean isAnimatedSwitching = true;
    public static boolean isFingerGestureEnable = false;

    public static int slideMenuWidth = 100;
    public static int deviceWidth = 720;
    public static int deviceHeight = 1280;

    public static int minTextSize = 6;
    public static int maxHeadingTextSize = 26;
    public static int maxTextSize = 20;

    public static int initIndex = 0;
    public static int clickedMenuIndex = 0;
    public static int shipping_click_index = 0;

    public static int toggle_key = 1;

    public static String pID = "";

    public static int row_index = -1;

    public static String Base_URL = "https://coolieonline.com";

  //  public static String Base_URL = "https://demo.coolieonline.com";

    public static final String IP_Loc = "https://webservice.casvpn.com/liveip.php";

    public static StringBuilder logsShow = new StringBuilder();

    public static int dialog_theme =
            android.app.AlertDialog.THEME_HOLO_LIGHT;
//            AlertDialog.THEME_HOLO_DARK;
    //com.nujeed.R.style.AppTheme;

    public static String controllerName = "";

    public static Handler delay_handler = new Handler();

    public static int time = 2000;

    public static RequestQueue get_queue;

    public static KProgressHUD mKProgressHUD;

//    public static ProgressLoaderViewController customLoader;

    public static PreferencesHandler preferencesHandler = new PreferencesHandler();

    public static GetterSetter getterSetter = new GetterSetter();

    public static JSONObject jsonObject = null;

    public static JSONArray jsonArray = null;

    public static JSONObject app_db = null;

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static int isSend;

    public static String dicitionary = "dicitionary";

    public static String user_login = "user_login";

    public static String flag_image_FilePath = "file:///android_asset/flags/";

    public static String accountStatus = "";

    public static String total_days;
    public static String new_date;
    public static String remaining_days;
    public static String expiry_date_txt;

    public static String User_name;

    public static SqliteDataHelper database;

    public static String StatusCode;

    public static boolean PendingOrder = false;

    public static String[] hand_wash_dry_items = {"Exterior Hand Wash", "Foam Cannon Soap (Chemical Guys Soap)", "High Pressure Wax", "Hand Dry", "Tires/Rims Clean and Dress"};

    public static ArrayList<MenuArray> menu_items = new ArrayList<MenuArray>();

    public static String access_token = "";
    public static String name = "";
    public static String phone_no = "";
    public static int userid;
    public static boolean iSoperator = true;
    public static boolean iSoperatorImages = false;

    public static String login_status = "";
    public static float service_pr = 0.00f;
    public static String package_id = "1";
    public static String user_role = "";
    public static String order_id = "";
    public static String scrn_size = "";
    public static List<String> array_list_user;
    public static ArrayList<String> array_list_dealers;
    public static ArrayList<String> array_list_packages;

    public static double Currentlatitude = 0;
    public static double Currentlongitude = 0;

    public static boolean fromBackground = false;
    public static boolean isRouteDraw = false;

    public static String pickupLocation = "Location not found";
    public static String dropLocation = "Location not found";

    public static boolean notification_isTrue = false;

    public static boolean Is_noti_com = false;
    public static boolean Is_new_order_frag= false;

    public static String image_base_url = "https://coolieonline.com/public/front/upload";

    public static BroadcastReceiver myBroadCastReciever;
    public static BroadcastReceiver myBroadCastRecieverAssignJob;

    public static String type = "operator";
    public static String status = "DeActive";

    public static boolean jobStatus = false;

    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }


    public static void ShowLog(String message) {
        final int chunkSize = 2048;
        for (int i = 0; i < message.length(); i += chunkSize) {
            Log.d(controllerName, controllerName + "-" + message.substring(i, Math.min(message.length(), i + chunkSize)));
        }
    }

    public static boolean isOdd(int val) {
        return (val & 0x01) != 0;
    }

    public static void HideKeyBoard(Context mContext, android.view.View clickedView) {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(clickedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message,
                Snackbar.LENGTH_SHORT).show();

    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        String encoded = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        }catch (Exception e){
            Log.e("Global",e.getMessage());
        }

        return encoded;
    }


    public static float ShortpointValues(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static boolean CheckInternetConnectivity(Context con) {
        android.net.ConnectivityManager cm = (android.net.ConnectivityManager)
                con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && (cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected())) {
            return true;
        } else {
            NetworkError(con);
            return false;
        }
    }

//    public static Bitmap SetMapMarker(Context context, int width, int height) {
//
//        int mheight = height;
//        int mwidth = width;
//        BitmapDrawable bitmapdraw = (BitmapDrawable) context.getResources().getDrawable(R.drawable.marker);
//        Bitmap b = bitmapdraw.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
//        return smallMarker;
//    }


    public static void SetFadeInAnimation(Context context, View layout, int duration) {

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(duration);

//        Animation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
//        fadeOut.setStartOffset(1000);
//        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
//        animation.addAnimation(fadeOut);
        layout.setAnimation(animation);
    }


    public static void SetFadeOutAnimation(Context context, View layout, int duration, int Offset) {

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(Offset);
        fadeOut.setDuration(duration);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        layout.setAnimation(animation);
    }

    public static int roundValue(float f) {
        int c = (int) ((f) + 0.5f);
        float n = f + 0.5f;
        return (n - c) % 2 == 0 ? (int) f : c;
    }

    private static void NetworkError(Context mContext) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, dialog_theme);
        dialog.setCancelable(false);
        dialog.setMessage(R.string.lbl_network_error);
//        keyValue(mContext, "network_error")
//        keyValue(mContext, "ok")
        dialog.setPositiveButton(R.string.lbl_ok,
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    public static void RedirectedDialog(final Context mContext, final String url) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, dialog_theme);
        dialog.setCancelable(false);
        TextView myMsg = new TextView(mContext);
      //  myMsg.setText(R.string.lbl_you_will_be_redirected);
        myMsg.setPadding(10, 20, 10, 10);
        myMsg.setGravity(Gravity.CENTER);
        myMsg.setTextColor(Color.WHITE);
        dialog.setView(myMsg);

        dialog.setNegativeButton("no",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        dialog.setPositiveButton("yes",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {

                        Intent browserIntentsupport = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(browserIntentsupport);
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    public static void saveBitmaptoExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/spot-free/assets");
        myDir.mkdirs();

//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "signature_order" + ".png";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static String ReadDicitionary(Context context) {

        String output = ReadFileFromAppCache(context, dicitionary);

        try {
            JSONObject jsonObject = new JSONObject(output);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return output;
    }


    public static void ShowAlertMessage(Context mContext, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, dialog_theme);
        dialog.setCancelable(true);
        dialog.setMessage(message);
        dialog.setPositiveButton(keyValue(mContext, "ok"),
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    public static void ShowAlertMessage(Context mContext, View view) {

        View view_layout = view;
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, dialog_theme);
        dialog.setCancelable(false);
        dialog.setView(view_layout);
        //dialog.show();

        AlertDialog alertDialog = dialog.create();
        alertDialog.dismiss();
    }

    public static String ReadFileFromAppCache(Context context, String file_name) {
        String output = "";
        int ch;

        java.io.File f = new java.io.File(context.getFilesDir() + "/" + file_name);
        StringBuffer strContent = new StringBuffer("");
        java.io.FileInputStream fin = null;

        try {
            fin = new java.io.FileInputStream(f);

            while ((ch = fin.read()) != -1)
                strContent.append((char) ch);

            fin.close();

        } catch (FileNotFoundException e) {
            output = "null";
        } catch (IOException ioe) {
        }
        try {
            output = java.net.URLDecoder.decode(strContent.toString(), "UTF-8");

        } catch (java.io.UnsupportedEncodingException uee) {
        }

        return output;

    }

    public static long DateintoTime(Context context, String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String DateFormatChanger(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(newDate);

        return date;
    }

    public static String DateFormatChangerReverse(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd-MMMM-yyyy");
        String date = format.format(newDate);

        return date;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public static long getDaysBetweenDates(String start, String end) {
        String DATE_FORMAT = "dd-MM-yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }

    public static void GetGlobalDate(final Context context) {

        Global.get_queue = Volley.newRequestQueue(context);

        StringRequest postRequest = new StringRequest(Request.Method.POST, Base_URL + "/date.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        PreferencesHandler preferencesHandler = new PreferencesHandler(context);
                        preferencesHandler.setCurrentDate(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("", "");

                return params;
            }
        };

        Global.get_queue.add(postRequest);

    }


    public static void GetScreenSizeActivity(Activity context) {
        DisplayMetrics met = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(met);// get display metrics object
        String strSize =
                new DecimalFormat("##.##").format(Math.sqrt(((met.widthPixels / met.xdpi) *
                        (met.widthPixels / met.xdpi)) +
                        ((met.heightPixels / met.ydpi) * (met.heightPixels / met.ydpi))));

        scrn_size = String.valueOf(roundValue(Float.parseFloat(strSize)));
    }

    public static void GetScreenSizeFragment(Context context) {
        DisplayMetrics met = new DisplayMetrics();
        ((FragmentActivity) context).getWindowManager().getDefaultDisplay().getMetrics(met);// get display metrics object
        String strSize =
                new DecimalFormat("##.##").format(Math.sqrt(((met.widthPixels / met.xdpi) *
                        (met.widthPixels / met.xdpi)) +
                        ((met.heightPixels / met.ydpi) * (met.heightPixels / met.ydpi))));

        scrn_size = String.valueOf(roundValue(Float.parseFloat(strSize)));
    }

    public static void StoreFileToAppCache(Context context, String data, String file_name) {

        ShowLog("StoreFileToAppCache = " + file_name);

        try {
            String encodedValue = "";
            try {
                encodedValue = java.net.URLEncoder.encode(data, "UTF-8");
            } catch (java.io.UnsupportedEncodingException uee) {
            }
            java.io.File f = new java.io.File(context.getFilesDir() + "/" + file_name);

            java.io.FileOutputStream fop = new java.io.FileOutputStream(f, false);

            if (f.exists()) {
                fop.write(encodedValue.toString().getBytes());
                fop.flush();
                fop.close();
            } else
                System.out.println("This file is not exist");
        } catch (Exception e) {
            ShowLog("StoreFileToAppCache-error = " + e.getMessage());
        }
    }

    public static int PxToDp(int px) {
        return (int) (px / android.content.res.Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * android.content.res.Resources.getSystem().getDisplayMetrics().density);
    }

    public static void ReleaseMemoryOnDestory() {
        try {
            System.gc();
        } catch (Exception ee) {
        }

        try {
            Runtime.getRuntime().gc();
        } catch (Exception ee) {
        }
    }

    public static void ReleaseMemoryOnBackButton() {
        try {
            System.gc();
        } catch (Exception ee) {
        }

        try {
            Runtime.getRuntime().gc();
        } catch (Exception ee) {
        }
    }

    public static void RecycleBitmap(android.graphics.Bitmap bitmap) {
        try {
            if (bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = null;

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String app_language = "en";
    public static String language_id = "1";
    public static String default_language_id = "2";

    public static String appFontNameLIGHT =
//            "MONTSERRAT-LIGHT.OTF";
//            "Myriad-Pro_31655.ttf";
            "questrial_regular.otf";

    public static String appFontNameREGULAR =
//            "MONTSERRAT-REGULAR.OTF";
//            "Myriad-Pro-Bold_31631.ttf";
            "questrial_regular.otf";

    public static String appFontNameSemiBold =
//            "Myriad-Pro-Semibold_31650.ttf";
            "quicksand_bold.otf";

    public static void ChangeAppFontFromLanguage() {
        if (app_language.equalsIgnoreCase("en")) {
//            appFontName = "MONTSERRAT-LIGHT.OTF";
//            appFontNameBold = "MONTSERRAT-REGULAR.OTF";

            appFontNameLIGHT = "questrial_regular.otf";
            appFontNameSemiBold = "quicksand_bold.otf";
            appFontNameREGULAR = "questrial_regular.otf";

        } else {
            //            appFontName = "MONTSERRAT-LIGHT.OTF";
            //            appFontNameBold = "MONTSERRAT-REGULAR.OTF";

            appFontNameLIGHT = "questrial_regular.otf";
            appFontNameSemiBold = "quicksand_bold.otf";
            appFontNameREGULAR = "questrial_regular.otf";
        }
    }

    public static void SetView(Context mContext, android.widget.Button selectedView, boolean isBold) {
        if (useFontFotViews) {
            String fontName = Global.appFontNameLIGHT;
            if (isBold) {
                fontName = Global.appFontNameREGULAR;
            }
            android.graphics.Typeface font = android.graphics.Typeface.createFromAsset(mContext.getAssets(), fontName);
            selectedView.setTypeface(font);
        }
    }

    public static void SetView(Context mContext, TextView selectedView, boolean isBold, boolean isSemiBold) {
        if (useFontFotViews) {
            String fontName = Global.appFontNameLIGHT;
            if (isBold) {
                fontName = Global.appFontNameREGULAR;
            } else if (isSemiBold) {
                fontName = Global.appFontNameSemiBold;
            }
            android.graphics.Typeface font = android.graphics.Typeface.createFromAsset(mContext.getAssets(), fontName);
            selectedView.setTypeface(font);
        }
    }

    public static void SetView(Context mContext, EditText selectedView, boolean isBold) {
        if (useFontFotViews) {
            String fontName = Global.appFontNameLIGHT;
            if (isBold) {
                fontName = Global.appFontNameREGULAR;
            }
            android.graphics.Typeface font = android.graphics.Typeface.createFromAsset(mContext.getAssets(), fontName);
            selectedView.setTypeface(font);
        }
    }

    public static void Setsatusbarcolor(Activity activity, boolean color_change) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (color_change == true) {
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_purple));
        } else {
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_yellow));
        }
    }

    public static boolean isUpdateMenu = false;
    public static boolean isUserLogin = false;
    public static boolean isLanguageChanged = false;

    public static boolean SMSVerificationAlert(Context mContext) {
        boolean is_sms_verify = false;
        if (user_id.equalsIgnoreCase("") || user_id.equalsIgnoreCase("null")) {

        } else {
            String profile_data = ReadFileFromAppCache(mContext, "profile_data");
            ShowLog("SMSVerificationAlert-profile_data = " + profile_data);
            try {
                JSONObject obj = new JSONObject(profile_data);
                String key = "number_verified";
                if (obj.toString().contains(key)) {
                    ShowLog("SMSVerificationAlert-contains = " + key);
                    String number_verified = obj.getString(key).toLowerCase();
                    if (number_verified.equalsIgnoreCase("n")) {
                        is_sms_verify = true;
                    }
                    ShowLog("SMSVerificationAlert-is_sms_verify = " + is_sms_verify + " , key = " + key);
                }

            } catch (Exception ee) {
                ShowLog("SMSVerificationAlert-is_sms_verify-error = " + ee.getMessage());
            }
        }

        return is_sms_verify;
    }

    public static String getFileName(Context mContext, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static void FragmentBackButtonClick(Activity mActivity) {
        Log.d("back_frag", Global.device_back_tag);
        mActivity.getFragmentManager().popBackStack(Global.device_back_tag,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }


    public static String language_data = "";
    public static JSONObject languageObj = new JSONObject();

    public static JSONObject getLanguage(String language_data) {
        ShowLog("language_id = " + language_id + " . getLanguage = " + language_data);
        JSONObject obj = new JSONObject();
        if (language_data.equalsIgnoreCase("") || language_data.equalsIgnoreCase("null")) {
            obj = new JSONObject();
        } else {
            try {
                JSONObject dataObj = new JSONObject(language_data);
                obj = new JSONObject(dataObj.getString(language_id));
            } catch (Exception ee) {
            }
        }
        ShowLog("selectedLanguege = " + obj.toString());
        return obj;
    }

    public static String keyValue(Context mContext, String key) {

        String data = "";
        try {
            data = languageObj.getString(key);
        } catch (Exception ee) {
            data = getResponseMessage(mContext, key);
        }
        //Global.ShowLog("key = " + key + " , value = " + data);

        if (data.equalsIgnoreCase("")) {
            data = key;
        }

        return key;
    }

    public static void changeFragment(Context context, Fragment fragment) {

//        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,
//                R.anim.fadeout);
//        transaction.replace(R.id.content_frame, fragment);
//        transaction.addToBackStack(device_back_tag);
//        transaction.commit();
    }


    public static String getResponseMessage(Context mContext, String key) {
        String message = "";
        try {
            int identifier = mContext.getResources().getIdentifier(key, "string", mContext.getPackageName());
            if (identifier != 0) {
                message = mContext.getResources().getString(identifier);
            }
        } catch (Exception ee) {
            message = key;
        }

        return message;
    }


    public static JSONObject signupJson = new JSONObject();


    public static void changeActivity(Context context, Activity activity) {

        Intent in = new Intent();
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.setClass(context, activity.getClass());
        context.startActivity(in);

    }

    public static void savedatatostorage(Context context, String data, String file_name) {
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(context.openFileOutput(file_name, Context.MODE_PRIVATE));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            out.write(data);
            out.write('\n');
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }

    public static void OpenKeybord(Context context, AutoCompleteTextView autoCompleteTextView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(autoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
    }

    public boolean checkFineLocationPermission(Activity activity, int REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCourseLocationPermission(Activity activity, int REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE);
            }
            return false;
        } else {
            return true;
        }
    }


    public boolean checkWriteStoragePermission(Activity activity, int REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE);
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean checkReadStoragePermission(Activity activity, int REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE);
            }
        } else {
            return false;
        }
        return true;
    }


}
