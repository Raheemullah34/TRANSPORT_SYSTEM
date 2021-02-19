package com.abasynuniversity.TransportSystem.forStudents.Model;

public class vehical_Detail_registred_student_model {
    String user_id,user_name,user_reg_no,stu_status,user_type,driver_id,vehicle_id;
    boolean bool_studetn_staus;

    public vehical_Detail_registred_student_model() {
    }

    public String getUser_id() {
        return user_id;
    }

    public boolean isBool_studetn_staus() {
        return bool_studetn_staus;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setBool_studetn_staus(boolean bool_studetn_staus) {
        this.bool_studetn_staus = bool_studetn_staus;
    }

    public String getStu_status() {
        return stu_status;
    }

    public void setStu_status(String stu_status) {
        this.stu_status = stu_status;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_reg_no() {
        return user_reg_no;
    }

    public void setUser_reg_no(String user_reg_no) {
        this.user_reg_no = user_reg_no;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
