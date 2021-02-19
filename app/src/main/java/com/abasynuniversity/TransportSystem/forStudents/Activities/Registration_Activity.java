package com.abasynuniversity.TransportSystem.forStudents.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.abasynuniversity.TransportSystem.forStudents.Model.Staff_Model;
import com.abasynuniversity.TransportSystem.forStudents.MySharePreference.MySharePreferences;
import com.abasynuniversity.TransportSystem.forStudents.R;
import com.abasynuniversity.TransportSystem.forStudents.Utils.Api;
import com.abasynuniversity.TransportSystem.forStudents.VolleyService.VolleyService;
import com.android.volley.VolleyError;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import de.mateware.snacky.Snacky;

public class Registration_Activity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout btn_submit_reg;
    EditText ed_emp_id, ed_full_name, ed_cnic, ed_contact;

    String  d_id,d_name,d_cnic,d_imei,d_status,d_contact;
    RelativeLayout btn_submit;
    private static final int REQUEST_CODE = 101;
    //Model class Student
    Staff_Model staff_model;
    //share preference for saving data first time
    MySharePreferences sharePreferences;
    //Netoworking calling
    VolleyService volleyService;
    //imei string for security reason
    String imei;
    ProgressDialog progressDialog;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        //registration wiget
        ed_emp_id = findViewById(R.id.ed_Reg_id);
        ed_full_name = findViewById(R.id.ed_full_name);
        ed_cnic = findViewById(R.id.ed_email);
        ed_contact = findViewById(R.id.ed_contact);

        //Button registration
        btn_submit = findViewById(R.id.btn_submit_reg);


        volleyService = new VolleyService(this);


        checkRunTimePermission();
        progressDialog = new ProgressDialog(this);

        getDeviceId(Registration_Activity.this);
        //student model registor
        staff_model = new Staff_Model();
        sharePreferences = new MySharePreferences(this);
        staff_model = sharePreferences.getSingupdata(this);

        status = sharePreferences.getStatus_driver(this);

        if (staff_model != null) {
            imei = getDeviceId(this);

            if (staff_model.getEmp_imei().equals(imei)) {
                if(status == true)
                    startActivity(new Intent(Registration_Activity.this,Single_Student_Send_Notification.class));

                else {
                    startActivity(new Intent(Registration_Activity.this,DashBoard_Activity.class));


                }
            }
        }
        btn_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submit_reg:
                Staff_Registration();
                break;
        }

    }

    private void Staff_Registration() {

        final String empid = ed_emp_id.getText().toString().trim();
        final String full_name = ed_full_name.getText().toString().trim();
        String cnic = ed_cnic.getText().toString().trim();
        String contact = ed_contact.getText().toString().trim();


        if (empid.equals("")) {
            this.ed_emp_id.setError("Please Enter Employee id");
        } else if (full_name.equals("")) {
            ed_full_name.setError("please enter Name");
        } else if (cnic.length() < 13) {
            ed_cnic.setError("Enter valid CNIC number");
        } else if (contact.length() < 10) {
            ed_contact.setError("Enter Valid Contact Number");
        } else {

            progressDialog.setMessage("data is uploading...");
            progressDialog.show();



            volleyService.driver_Registration(Api.DIRECTORY+Api.REGISTRAION_DRIVERS,
                    empid, full_name, cnic, contact, imei, new VolleyService.VolleyResponseListener() {
                        @Override
                        public void onSuccess(String response) {
                             Log.d("data", response);

                            try {
                                JSONObject object = new JSONObject(response);
                                String success = object.getString("status");
                                String message = object.getString("message");

                                if (success.equals("true") || success.equals("available") || success.equals("updated")) {

                                    JSONObject jsonObject =object.getJSONObject("data");
                                    d_id = jsonObject.getString("d_id");
                                    d_name = jsonObject.getString("d_name");
                                    d_cnic = jsonObject.getString("d_cnic");
                                    d_contact = jsonObject.getString("d_contact");
                                    d_imei = jsonObject.getString("d_imei");
                                    d_status = jsonObject.getString("d_status");





                                    Staff_Model staff_model = new Staff_Model();
                                    staff_model.setEmp_id(d_id);
                                    staff_model.setEmp_name(d_name);
                                    staff_model.setEmp_cnic(d_cnic);
                                    staff_model.setEmp_contact(d_contact);
                                    staff_model.setEmp_imei(imei);
                                    staff_model.setEmp_status(d_status);
                                    progressDialog.dismiss();

                                    sharePreferences.Save_singupdata(Registration_Activity.this, staff_model);
                                    startActivity(new Intent(Registration_Activity.this,DashBoard_Activity.class));
                                    finish();


                                }

                                else {
                                    progressDialog.dismiss();
                                    Snacky.builder().
                                            setActivity(Registration_Activity.this).
                                            setText(message).
                                            error().
                                            show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(VolleyError error) {
                            progressDialog.dismiss();


                            Log.e("error", error.getMessage());
                        }
                    });
        }


    }

    public String getDeviceId(Context context) {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imei = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                imei = mTelephony.getDeviceId();
            } else {
                imei = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}

