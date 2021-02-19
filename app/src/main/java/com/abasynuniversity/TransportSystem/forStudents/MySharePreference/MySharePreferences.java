package com.abasynuniversity.TransportSystem.forStudents.MySharePreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.abasynuniversity.TransportSystem.forStudents.Model.DashBoard_car_model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Record_save_Model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Staff_Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MySharePreferences {
    Context context;

    public MySharePreferences() {


    }

    public MySharePreferences(Context context) {

        this.context = context;

    }

    public static String LOGIN_FLAG = "IsLogin";

    public static final String MyPREFERENCES = "abasynTransportShareprence";
    public static final String MyPREFERENCES1 = "abasynCarData";
    public static SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferences;



    public void Save_singupdata(Context context , Staff_Model user){

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String mydata=gson.toJson(user);
        editor.putString("singup_data",mydata);
        editor.commit();

    }

    public Staff_Model getSingupdata(Context ctx){

        Staff_Model userdatahere=new Staff_Model();
        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);

        Gson gson = new Gson();
        String json = prfs.getString("singup_data", "");
        userdatahere = gson.fromJson(json, Staff_Model.class);
        return userdatahere;
    }
    //Function for verfication code

    //save data of the car
    public void save_car_data(Context context , Record_save_Model user){

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES1, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String mydata=gson.toJson(user);
        editor.putString("car_data",mydata);
        editor.commit();

    }

    public Record_save_Model get_car_data(Context ctx){

        Record_save_Model userdatahere=new Record_save_Model();
        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES1, Context.MODE_MULTI_PROCESS);

        Gson gson = new Gson();
        String json = prfs.getString("car_data", "");
        userdatahere = gson.fromJson(json, Record_save_Model.class);
        return userdatahere;
    }
    public void save_verification_code(Context context , Staff_Model staff){

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String mydata=gson.toJson(staff);
        editor.putString("verification_code",mydata);
        editor.commit();

    }
    //End of varification Code
    public Staff_Model getVerficationCode(Context ctx){

        Staff_Model userdatahere=new Staff_Model();
        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);

        Gson gson = new Gson();
        String json = prfs.getString("verification_code", "");
        userdatahere = gson.fromJson(json, Staff_Model.class);
        return userdatahere;
    }
    //function for status checking
    public void save_status_code(Context context , Staff_Model staff_model){


        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String mydata=gson.toJson(staff_model);
        editor.putString("status_code",mydata);
        editor.commit();

    }
    public Staff_Model getStatusCode(Context ctx){


        Staff_Model userdatahere=new Staff_Model();
        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);

        Gson gson = new Gson();
        String json = prfs.getString("status_code", "");
        userdatahere = gson.fromJson(json, Staff_Model.class);
        return userdatahere;
    }


/*save Login Status*/

    public Boolean getLoginStatus(Context ctx){

        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        Boolean link=prfs.getBoolean("loginstatus",false);
        return link;
    }

    public void saveLoginStatus(Context context, boolean status){

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putBoolean("loginstatus", status);
        editor.commit();

    }
    public void status_driver(Context context, boolean status)
    {
        sharedpreferences=context.getSharedPreferences(MyPREFERENCES,context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putBoolean("status",status);
        editor.commit();

    }
    public Boolean getStatus_driver(Context context)
    {
        SharedPreferences shared = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        Boolean link=shared.getBoolean("status",false);
        return link;
    }
//end of the signup status
    /*save Login Status*/
    // status for notification

    public void save_notification_count(Context context, int count){

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putInt("notification_count", count);
        editor.commit();

    }
    public int getNotification_Count(Context context)
    {
        SharedPreferences shared = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        int count=shared.getInt("notification_count",0);
        return count;
    }


    //end of the notifcaiton


}
