package com.abasynuniversity.TransportSystem.forStudents.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abasynuniversity.TransportSystem.forStudents.Model.Staff_Model;
import com.abasynuniversity.TransportSystem.forStudents.MySharePreference.MySharePreferences;
import com.abasynuniversity.TransportSystem.forStudents.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class Splash_Activity extends AppCompatActivity {

    Handler handler;
    public static final int REQUEST_CODE = 101;
    // Check internet Connection
    boolean connection_checking;
    MySharePreferences sharePreferences;
    Staff_Model staff_model;

    String imei;

    //Imageview for splash rotation
    ImageView iv;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        //  FirebaseMessaging.getInstance().subscribeToTopic("staff");
        checkRunTimePermission();
        Login_activity_Start();
        sharePreferences = new MySharePreferences(this);
        staff_model = new Staff_Model();
        staff_model = sharePreferences.getSingupdata(this);

        status = sharePreferences.getStatus_driver(this);

        iv = findViewById(R.id.iv_splash);

        iv.animate().scaleX(1f).scaleY(1f)
                .alpha(1f)
                .setDuration(2000);

        if (staff_model != null) {
            imei = getDeviceId(this);

            if (staff_model.getEmp_imei().equals(imei)) {
                if(status == true)
                    ride_status();

                else {
                    nav_activity();

                }
            }
        } else {
            Login_activity_Start();

        }
    }


    public void nav_activity() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Activity.this, DashBoard_Activity.class);

                startActivity(intent);
                finish();

            }

        }, 4000);
    }

    public void ride_status() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Activity.this, Single_Student_Send_Notification.class);

                startActivity(intent);
                finish();

            }

        }, 4000);
    }
    public void Login_activity_Start() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Activity.this,Registration_Activity.class);
                intent.putExtra("imei",imei);
                startActivity(intent);
                finish();

            }
        }, 4000);
    }

    public String getDeviceId(Context context) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imei = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                imei = mTelephony.getDeviceId();
            } else {
                imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

        return imei;
    }
    private boolean checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.READ_PHONE_STATE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 11111);
        } else {
            Toast.makeText(this, "permission is gratented succussfully" , Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}