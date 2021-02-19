package com.abasynuniversity.TransportSystem.forStudents.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Send_Notification_Model {
    String stu_name,stu_reg_no,status,user_type,driver_name,v_no,v_name,v_time,driver_id,user_id,vehicle_id;
    ArrayList<vehical_Detail_registred_student_model> stud_list;

    public Send_Notification_Model() {
    }

    public String getStu_name() {
        return stu_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getV_no() {
        return v_no;
    }

    public void setV_no(String v_no) {
        this.v_no = v_no;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_time() {
        return v_time;
    }

    public void setV_time(String v_time) {
        this.v_time = v_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_reg_no() {
        return stu_reg_no;
    }

    public void setStu_reg_no(String stu_reg_no) {
        this.stu_reg_no = stu_reg_no;
    }

    public ArrayList<vehical_Detail_registred_student_model> getStud_list() {
        return stud_list;
    }

    public void setStud_list(ArrayList<vehical_Detail_registred_student_model> stud_list) {
        this.stud_list = stud_list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {

                jsonObject.put("driver_name", getDriver_name());
                jsonObject.put("v_no", getV_no());
                jsonObject.put("v_name", getV_name());
                jsonObject.put("v_time", getV_time());


               JSONArray jArray = new JSONArray();

            for (int i = 0; i < stud_list.size(); i++) {
                JSONObject jGroup = new JSONObject();

                if(stud_list.get(i).getStu_status().equals("ok")) {
                     jGroup.put("user_reg_no", stud_list.get(i).getUser_reg_no());
                    jGroup.put("user_type", stud_list.get(i).getUser_type());
                    jArray.put(jGroup);
                }
                else {

                }


            }

            jsonObject.put("assign_to", jArray);



            return jsonObject.toString();
        } catch (JSONException
                e) {
            e.printStackTrace();
            return "";
        }
    }
    public String toPickJSON() {

        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.put("d_id", getDriver_id());
            jsonObject.put("v_id", getVehicle_id());


               JSONArray jArray = new JSONArray();

            for (int i = 0; i < stud_list.size(); i++) {
                JSONObject jGroup = new JSONObject();

                if(stud_list.get(i).getStu_status().equals("ok")) {
                     jGroup.put("user_id", stud_list.get(i).getUser_id());
                    jArray.put(jGroup);
                }
                else {

                }


            }

            jsonObject.put("assign_to", jArray);



            return jsonObject.toString();
        } catch (JSONException
                e) {
            e.printStackTrace();
            return "";
        }
    }
}
