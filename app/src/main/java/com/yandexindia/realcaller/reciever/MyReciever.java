package com.yandexindia.realcaller.reciever;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.yandexindia.realcaller.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

public class MyReciever extends BroadcastReceiver {

    private WindowManager windowManager;
    private View popupView, DetailView;
    private String incomingNumber;
    private String phonenumber;
    private TextView textView, textView2;
    private Button cancel, Call, copy;
    static String name;
    int vari;
    private static String mLastState;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            windowManager.removeView(popupView);
        }
    };

    public MyReciever() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override

    public void onReceive(Context context, Intent intent) {

        windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.detail_popview, null);
        DetailView = layoutInflater.inflate(R.layout.detail_view, null);
        popupView.findViewById(R.id.pop).setOnTouchListener(new View.OnTouchListener() {
            final WindowManager.LayoutParams updatepar = params;
            double x;
            double y;
            double px;
            double py;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        x = updatepar.x;
                        y = updatepar.y;
                        px = event.getRawX();
                        py = event.getRawY();

                        break;
                    case MotionEvent.ACTION_MOVE:

                        updatepar.x = (int) (x + (event.getRawX() - px));
                        updatepar.y = (int) (y + (event.getRawY() - py));
                        windowManager.updateViewLayout(popupView, updatepar);

                    default:
                        break;
                }
                return false;
            }
        });

        Handler handler = new Handler(Looper.getMainLooper());
        try {
            final String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER) != null && !state.equals(mLastState)) {

                mLastState = state;
                phonenumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                name = getContactDisplayNameByNumber(phonenumber, context);
                windowManager.addView(popupView, params);

                textView = popupView.findViewById(R.id.mNumber);
                textView2 = popupView.findViewById(R.id.card_view2_number);
                cancel = popupView.findViewById(R.id.cancel);
                cancel.setOnClickListener(v -> {
                    handler.removeCallbacks(runnable);
                    windowManager.removeView(popupView);
                });
                handler.postDelayed(runnable, 5000);
                textView.setText(name);
                textView.setText(phonenumber);
            } else if (intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) != null && !state.equals(mLastState)) {
                mLastState = state;
                incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                name = getContactDisplayNameByNumber(incomingNumber, context);
                windowManager.addView(popupView, params);
                textView = popupView.findViewById(R.id.mNumber);
                textView2 = popupView.findViewById(R.id.card_view2_number);
                cancel = popupView.findViewById(R.id.cancel);
                cancel.setOnClickListener(v -> {
                    handler.removeCallbacks(runnable);
                    windowManager.removeView(popupView);

                });
                handler.postDelayed(runnable, 5000);
                textView.setText(name);
                textView2.setText(incomingNumber);
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE) && !state.equals(mLastState)) {
                mLastState = state;
                vari = 1;


//            Intent mIntent = new Intent(context, Detail_activity.class);
//            mIntent.setAction(Intent.ACTION_MAIN);
//            mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mIntent.putExtra("ContactName", name);
//            context.startActivity(mIntent);
                // windowManager.addView(DetailView, params);
//            MConatct = DetailView.findViewById(R.id.contact);
//            if(name!=null){
//                MConatct.setText(name);
//            }
//            cancel = DetailView.findViewById(R.id.popviewclose);
//            copy = popupView.findViewById(R.id.copy_icon);
//            Call = popupView.findViewById(R.id.Call_icon);
//            cancel.setOnClickListener(v -> {
//                handler.removeCallbacks(runnable);
//              //  windowManager.removeView(DetailView);
//            });
                // handler.postDelayed(runnable, 8000);
            }
        } catch (Exception e) {

            Toast.makeText(context, "Reciever not work", Toast.LENGTH_LONG).show();
        }
    }

    public String getContactDisplayNameByNumber(String number, Context context) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        name = "Incoming call from";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, null, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));

            } else {
                name = "Unknown Number";
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
    }
}


