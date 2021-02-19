package com.abasynuniversity.TransportSystem.forStudents.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.Toast;

import com.abasynuniversity.TransportSystem.forStudents.Adapter.Single_Student_Notification_Adapater;
import com.abasynuniversity.TransportSystem.forStudents.Adapter.Vehicle_detail_Adapter;
import com.abasynuniversity.TransportSystem.forStudents.Model.DashBoard_car_model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Record_save_Model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Send_Notification_Model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Staff_Model;
import com.abasynuniversity.TransportSystem.forStudents.Model.vehical_Detail_registred_student_model;
import com.abasynuniversity.TransportSystem.forStudents.MySharePreference.MySharePreferences;
import com.abasynuniversity.TransportSystem.forStudents.R;
import com.abasynuniversity.TransportSystem.forStudents.Utils.Api;
import com.abasynuniversity.TransportSystem.forStudents.VolleyService.VolleyService;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.mateware.snacky.Snacky;

public class Single_Student_Send_Notification extends AppCompatActivity implements View.OnClickListener {

    VolleyService volleyService;
    ArrayList<vehical_Detail_registred_student_model> student_list,send_to_notification_list;
    Single_Student_Notification_Adapater vehicle_detail_adapter;
    RecyclerView rv_vehical_details;
    LinearLayoutManager linearLayoutManager;

    ImageView btn_send_Notification,btn_reached_drive,btn_student_pick;
    ProgressDialog progressDialog;
    String toJson_String;

    vehical_Detail_registred_student_model s_model;
    MySharePreferences sharePreferences;
    Staff_Model staff_model;
    DashBoard_car_model dashBoard_car_model;

    String convert_json;
    Send_Notification_Model notify;
    //This model is created for ta save the data in sharepreference
    Record_save_Model record_save_model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single__student__send__notification);
        rv_vehical_details = findViewById(R.id.rv_car_detail_students);
        btn_send_Notification = findViewById(R.id.btn_send_notification);
        btn_reached_drive = findViewById(R.id.btn_car_reached);
        btn_student_pick = findViewById(R.id.btn_pick_student);
        sharePreferences = new MySharePreferences(this);
        staff_model = new Staff_Model();
        record_save_model = new Record_save_Model();
        staff_model = sharePreferences.getSingupdata(this);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        student_list = new ArrayList<>();
        send_to_notification_list = new ArrayList<>();
        vehicle_detail_adapter = new Single_Student_Notification_Adapater(student_list,this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_vehical_details.setLayoutManager(linearLayoutManager);
        rv_vehical_details.setAdapter(vehicle_detail_adapter);
        record_save_model = sharePreferences.get_car_data(Single_Student_Send_Notification.this);





        volleyService = new VolleyService(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("data is loading....");
        progressDialog.show();
        All_registred_Students();

        btn_send_Notification.setOnClickListener(this);
        btn_reached_drive.setOnClickListener(this);
        btn_student_pick.setOnClickListener(this);



    }
    public void All_registred_Students() {

        volleyService.vehical_detail_all_registerd_students(Api.DIRECTORY + Api.VEHICLE_DETAIL,"1", new VolleyService.VolleyResponseListener() {
            @Override
            public void onSuccess(String response) {

                Log.d("claim_approvel", response);

                try {


                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");

                    Log.e("success_try", response);
                    if (status.equals("true")) {

                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject obj_Array = jsonArray.getJSONObject(i);
                            String user_id = obj_Array.getString("user_id");
                            String user_name = obj_Array.getString("user_name");
                            String user_regno = obj_Array.getString("user_reg_no");
                            String user_type = obj_Array.getString("user_type");
                            s_model = new vehical_Detail_registred_student_model();
                            s_model.setUser_id(user_id);
                            s_model.setUser_name(user_name);
                            s_model.setUser_reg_no(user_regno);
                            s_model.setUser_type(user_type);
                            s_model.setStu_status("no");

                            student_list.add(s_model);

                        }
                        progressDialog.dismiss();
                        vehicle_detail_adapter.notifyDataSetChanged();
                        rv_vehical_details.setAdapter(vehicle_detail_adapter);

                    } else {
                        String message = object.getString("message");

                        Log.e("success_false", response);
                        Snacky.builder().setActivity(Single_Student_Send_Notification.this).setText(message).error().show();


                        progressDialog.dismiss();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError error) {
                progressDialog.dismiss();
                Log.d("error", error.getMessage());
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.btn_send_notification:
                notify = new Send_Notification_Model();
                notify.setStud_list(student_list);
                notify.setDriver_name(staff_model.getEmp_name());
                notify.setV_no(record_save_model.getCar_number());
                notify.setV_name(record_save_model.getCar_name());
                notify.setV_time(record_save_model.getCar_timing());
                convert_json=notify.toJSON().replaceAll("\\\\","");
                toJson_String = convert_json.toString();
                toJson_String = toJson_String.replaceAll("\\\\", "");


                Log.d("submit",toJson_String);

                progressDialog = new ProgressDialog(Single_Student_Send_Notification.this);
                progressDialog.setMessage("notification sending...");
                progressDialog.show();


                letGo();


                break;
            case R.id.btn_car_reached:

                reached_car_now_free();
                break;
            case R.id.btn_pick_student:

                Log.d("driver id ",record_save_model.getCar_name());

                 notify = new Send_Notification_Model();
                 notify.setDriver_id(record_save_model.getDriver_id());
                 notify.setVehicle_id(record_save_model.getVehicle_id());
                 notify.setStud_list(student_list);

                convert_json=notify.toPickJSON().replaceAll("\\\\","");
                toJson_String = convert_json.toString();
                toJson_String = toJson_String.replaceAll("\\\\", "");


                Log.d("submit",toJson_String);

                progressDialog = new ProgressDialog(Single_Student_Send_Notification.this);
                progressDialog.setMessage("notification sending...");
                progressDialog.show();


                pick_student();
                break;
        }
    }

    public void letGo()
    {
        volleyService.send_Notification_to_student(Api.DIRECTORY + Api.SEND_NOTIFICATION_TO_STUDENTS,toJson_String, new VolleyService.VolleyResponseListener() {
            @Override
            public void onSuccess(String response) {

                Log.d("send_notification", response);

                try {


                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    String message = object.getString("message");
                    if(status.equals("true")) {
                        progressDialog.dismiss();
                        Snacky.builder().setActivity(Single_Student_Send_Notification.this).setText(message).success().show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError error) {
                progressDialog.dismiss();
                Log.d("error", error.getMessage());
            }
        });

    }
    public void pick_student()
    {
        volleyService.send_Notification_to_student(Api.DIRECTORY + Api.STUDENT_PICKING,toJson_String, new VolleyService.VolleyResponseListener() {
            @Override
            public void onSuccess(String response) {

                Log.d("send_notification", response);

                try {


                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    String message = object.getString("message");
                    if(status.equals("true")) {
                        progressDialog.dismiss();
                        Snacky.builder().setActivity(Single_Student_Send_Notification.this).setText(message).success().show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError error) {
                progressDialog.dismiss();
                Log.d("error", error.getMessage());
            }
        });

    }
    public void reached_car_now_free()
    {
        sharePreferences.status_driver(Single_Student_Send_Notification.this,false);
        progressDialog = new ProgressDialog(Single_Student_Send_Notification.this);
        progressDialog.setMessage("Status updating ...");
        progressDialog.show();
        volleyService.driver_car_status_checking(Api.DIRECTORY + Api.CAR_STATUS_AFTER_DRIVE,record_save_model.getVehicle_id(), new VolleyService.VolleyResponseListener() {
            @Override
            public void onSuccess(String response) {

                Log.d("send_notification", response);

                try {


                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    String message = object.getString("message");
                    if(status.equals("true")) {
                        progressDialog.dismiss();
                        startActivity(new Intent(Single_Student_Send_Notification.this,DashBoard_Activity.class));
                        finish();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError error) {
                progressDialog.dismiss();
                Log.d("error", error.getMessage());
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Search by name,id,time");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                vehicle_detail_adapter.getFilter().filter(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                vehicle_detail_adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        boolean status = sharePreferences.getStatus_driver(Single_Student_Send_Notification.this);
        if(status == true)
        {
            androidx.appcompat.app.AlertDialog.Builder dailog  = new AlertDialog.Builder(this);
            dailog.setTitle("Info :");
            dailog.setMessage("if you have done the Ride please press the Reached button ");
            dailog.setPositiveButton("Reached", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    reached_car_now_free();
                }
            });
            dailog.setNegativeButton("No",null);

            dailog.show();
        }
        else
        {
            startActivity(new Intent(Single_Student_Send_Notification.this,DashBoard_Activity.class));
        }
    }
}