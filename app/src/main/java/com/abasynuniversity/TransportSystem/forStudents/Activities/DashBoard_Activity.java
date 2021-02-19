package com.abasynuniversity.TransportSystem.forStudents.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abasynuniversity.TransportSystem.forStudents.Adapter.DashBoard_Adapter;
import com.abasynuniversity.TransportSystem.forStudents.Model.DashBoard_car_model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Staff_Model;
import com.abasynuniversity.TransportSystem.forStudents.MySharePreference.MySharePreferences;
import com.abasynuniversity.TransportSystem.forStudents.R;
import com.abasynuniversity.TransportSystem.forStudents.Utils.Api;
import com.abasynuniversity.TransportSystem.forStudents.VolleyService.VolleyService;
import com.android.volley.VolleyError;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.mateware.snacky.Snacky;

public class DashBoard_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    Toolbar toolbar;
    //Navigation textview
    TextView tv_nav_reg_no,tv_nav_full_name,tv_nav_email;
    //share preference for navigation data
    MySharePreferences sharePreferences;
    Staff_Model staff_model;

    VolleyService volleyService;
    RecyclerView rv_dashBoard_vehicals;
    ProgressDialog progressDialog;

    ArrayList<DashBoard_car_model> car_list;
    DashBoard_Adapter dashBoard_adapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashy_board_);
        rv_dashBoard_vehicals = findViewById(R.id.rv_dashboard_vehicals);


        staff_model = new Staff_Model();
        sharePreferences = new MySharePreferences(this);
        staff_model = sharePreferences.getSingupdata(this);




        toolbar = findViewById(R.id.toolbar);
        volleyService  = new VolleyService(this);
        car_list = new ArrayList<>();
        dashBoard_adapter = new DashBoard_Adapter(car_list,this);

        rv_dashBoard_vehicals.setLayoutManager(new GridLayoutManager(this, 3));
        rv_dashBoard_vehicals.setAdapter(dashBoard_adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait data is loading....");
        progressDialog.show();
        All_vehicals();
        initNavigationMenu();
    }
    private void initNavigationMenu() {
        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setItemIconTintList(null);
         View headerView = nav_view.getHeaderView(0);

        tv_nav_full_name = headerView.findViewById(R.id.tv_nav_profile_name);
        tv_nav_reg_no = headerView.findViewById(R.id.tv_nav_profile_reg_no);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                drawer.openDrawer(GravityCompat.START);
                tv_nav_reg_no.setText("id:           "+staff_model.getEmp_id());
                tv_nav_full_name.setText("Name:  "+staff_model.getEmp_name());

                super.onDrawerOpened(drawerView);

            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();



        nav_view.setNavigationItemSelectedListener(DashBoard_Activity.this);

    }
    @Override
    public boolean onSupportNavigateUp() {

        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();


        if (id == R.id.nav_about) {
            if(checkInternetConnection(this) == true)
            {
                startActivity(new Intent(DashBoard_Activity.this,About_us.class));

            }
            else {
                Snacky.builder().
                        setActivity(this).
                        setText("Please Check your Internet Connection").
                        error().
                        show();
            }

        }





        return true;
    }
    public boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void All_vehicals() {

        volleyService.all_vehicles_dashboard(Api.DIRECTORY + Api.ALL_VERHICALS_DASHBOARD, new VolleyService.VolleyResponseListener() {
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
                            String v_id = obj_Array.getString("v_id");
                            String d_id = obj_Array.getString("driver_id");
                            String v_no = obj_Array.getString("v_no");
                            String v_name = obj_Array.getString("v_name");
                            String v_status = obj_Array.getString("v_status");
                            String dr_emp_id = obj_Array.getString("driver_emp_id");
                            String dr_name = obj_Array.getString("driver_name");
                            String dr_contact = obj_Array.getString("driver_contact");
                            String v_time = obj_Array.getString("v_time");



                            DashBoard_car_model car_model = new DashBoard_car_model();
                            car_model.setVehicles_id(v_id);
                            car_model.setDriver_id(d_id);
                            car_model.setCar_number(v_no);
                            car_model.setCar_name(v_name);
                            car_model.setVehicles_staus(v_status);
                            car_model.setDr_emp_id(dr_emp_id);
                            car_model.setDr_name(dr_name);
                            car_model.setDr_contact(dr_contact);
                            car_model.setTiming(v_time);
                            car_list.add(car_model);

                        }
                        progressDialog.dismiss();
                        dashBoard_adapter.notifyDataSetChanged();
                        rv_dashBoard_vehicals.setAdapter(dashBoard_adapter);

                    } else {
                        String message = object.getString("message");

                        Log.e("success_false", response);
                        Snacky.builder().setActivity(DashBoard_Activity.this).setText(message).error().show();


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
    public void onBackPressed() {

        androidx.appcompat.app.AlertDialog.Builder dailog  = new AlertDialog.Builder(this);
        dailog.setTitle("Exit Application :");
        dailog.setMessage("Are you want to exit the Application");
        dailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);


            }
        });
        dailog.setNegativeButton("NO",null);
        dailog.show();
    }
}