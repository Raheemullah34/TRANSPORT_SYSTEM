package com.abasynuniversity.TransportSystem.forStudents.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abasynuniversity.TransportSystem.forStudents.Adapter.Vehicle_detail_Adapter;
import com.abasynuniversity.TransportSystem.forStudents.Model.DashBoard_car_model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Send_Notification_Model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Staff_Model;
import com.abasynuniversity.TransportSystem.forStudents.Model.vehical_Detail_registred_student_model;
import com.abasynuniversity.TransportSystem.forStudents.MySharePreference.MySharePreferences;
import com.abasynuniversity.TransportSystem.forStudents.R;
import com.abasynuniversity.TransportSystem.forStudents.Utils.Api;
import com.abasynuniversity.TransportSystem.forStudents.VolleyService.VolleyService;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.mateware.snacky.Snacky;

public class Vehicles_Details extends AppCompatActivity  implements View.OnClickListener {


    TextView tv_driver_name,tv_driver_contact,tv_car_name,tv_timing;

    DashBoard_car_model dashBoard_car_model;
    VolleyService volleyService;
    ArrayList<vehical_Detail_registred_student_model> student_list,send_to_notification_list;
    Vehicle_detail_Adapter vehicle_detail_adapter;
    RecyclerView rv_vehical_details;
    LinearLayoutManager linearLayoutManager;

    ImageView btn_send_Notification;
    ProgressDialog progressDialog;
    String toJson_String;

    vehical_Detail_registred_student_model s_model;
    MySharePreferences sharePreferences;
    Staff_Model staff_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles__details);

        rv_vehical_details = findViewById(R.id.rv_car_detail_students);
        btn_send_Notification = findViewById(R.id.btn_send_notification);
        sharePreferences = new MySharePreferences(this);
        staff_model = new Staff_Model();
        staff_model = sharePreferences.getSingupdata(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dashBoard_car_model = new DashBoard_car_model();
        dashBoard_car_model = (DashBoard_car_model)getIntent().getSerializableExtra("car_Data");

        tv_driver_name = findViewById(R.id.tv_driver_name);
        tv_driver_contact = findViewById(R.id.tv_driver_contact);
        tv_car_name = findViewById(R.id.tv_driver_car_name);
        tv_timing = findViewById(R.id.tv_pick_drop_time);
//
        tv_car_name.setText(dashBoard_car_model.getCar_name() + "/"+ dashBoard_car_model.getCar_number());
        tv_driver_name.setText(dashBoard_car_model.getDr_name());
        tv_driver_contact.setText(dashBoard_car_model.getDr_contact());
        tv_timing.setText(dashBoard_car_model.getTiming());

        student_list = new ArrayList<>();
        send_to_notification_list = new ArrayList<>();
        vehicle_detail_adapter = new Vehicle_detail_Adapter(student_list,this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_vehical_details.setLayoutManager(linearLayoutManager);
        rv_vehical_details.setAdapter(vehicle_detail_adapter);

        volleyService = new VolleyService(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("data is loading....");
        progressDialog.show();
        All_registred_Students();

        btn_send_Notification.setOnClickListener(this);



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
                            s_model.setStu_status("ok");

                            student_list.add(s_model);

                        }
                        progressDialog.dismiss();
                        vehicle_detail_adapter.notifyDataSetChanged();
                        rv_vehical_details.setAdapter(vehicle_detail_adapter);

                    } else {
                        String message = object.getString("message");

                        Log.e("success_false", response);
                        Snacky.builder().setActivity(Vehicles_Details.this).setText(message).error().show();


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
                Send_Notification_Model notify = new Send_Notification_Model();
                 notify.setStud_list(student_list);
                 notify.setDriver_name(staff_model.getEmp_name());
                 notify.setV_no(dashBoard_car_model.getCar_number());
                 notify.setV_name(dashBoard_car_model.getCar_name());
                 notify.setV_time(dashBoard_car_model.getTiming());
                 String convertedjson=notify.toJSON().replaceAll("\\\\","");
                 toJson_String = convertedjson.toString();
                 toJson_String = toJson_String.replaceAll("\\\\", "");


                     Log.d("submit",toJson_String);

                     progressDialog = new ProgressDialog(Vehicles_Details.this);
                     progressDialog.setMessage("notification sending...");
                     progressDialog.show();


                     letGo();


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

                        sharePreferences.status_driver(Vehicles_Details.this,true);

                        Intent intent = new Intent(Vehicles_Details.this,Single_Student_Send_Notification.class);
                        startActivity(intent);

                        Snacky.builder().setActivity(Vehicles_Details.this).setText(message).success().show();
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
}