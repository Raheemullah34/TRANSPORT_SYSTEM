package com.abasynuniversity.TransportSystem.forStudents.Model;

import java.io.Serializable;

public class DashBoard_car_model implements Serializable {
    String car_name,Car_number,vehicles_id,vehicles_staus,driver_id,dr_emp_id,dr_name,dr_contact,timing;

    public DashBoard_car_model() {
    }

    public String getVehicles_id() {
        return vehicles_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getDr_emp_id() {
        return dr_emp_id;
    }

    public void setDr_emp_id(String dr_emp_id) {
        this.dr_emp_id = dr_emp_id;
    }

    public String getDr_name() {
        return dr_name;
    }

    public void setDr_name(String dr_name) {
        this.dr_name = dr_name;
    }

    public String getDr_contact() {
        return dr_contact;
    }

    public void setDr_contact(String dr_contact) {
        this.dr_contact = dr_contact;
    }

    public void setVehicles_id(String vehicles_id) {
        this.vehicles_id = vehicles_id;
    }

    public String getVehicles_staus() {
        return vehicles_staus;
    }

    public void setVehicles_staus(String vehicles_staus) {
        this.vehicles_staus = vehicles_staus;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_number() {
        return Car_number;
    }

    public void setCar_number(String car_number) {
        Car_number = car_number;
    }
}
