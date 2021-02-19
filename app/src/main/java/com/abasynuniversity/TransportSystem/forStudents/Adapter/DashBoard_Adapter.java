package com.abasynuniversity.TransportSystem.forStudents.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abasynuniversity.TransportSystem.forStudents.Activities.Vehicles_Details;
import com.abasynuniversity.TransportSystem.forStudents.Model.DashBoard_car_model;
import com.abasynuniversity.TransportSystem.forStudents.Model.Record_save_Model;
import com.abasynuniversity.TransportSystem.forStudents.MySharePreference.MySharePreferences;
import com.abasynuniversity.TransportSystem.forStudents.R;

import java.util.ArrayList;

public class DashBoard_Adapter extends RecyclerView.Adapter<DashBoard_Adapter.MyViewHolder> {

    Context context;

    Record_save_Model record_save_model;
    MySharePreferences sharePreferences;



    private ArrayList<DashBoard_car_model> car_list;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_car_name,tv_car_no;
        CardView card_car_name;
        ImageView dash_board_vehical;




        public MyViewHolder(View view) {
            super(view);

            tv_car_no = view.findViewById(R.id.tv_car_no);
            tv_car_name = view.findViewById(R.id.tv_car_name);
            card_car_name = view.findViewById(R.id.card_car_number);
            dash_board_vehical = view.findViewById(R.id.dash_board_vehical);


        }

    }



    public DashBoard_Adapter(ArrayList<DashBoard_car_model> attendence_list, Context context) {
        this.car_list = attendence_list;
        this.context = context;
        record_save_model = new Record_save_Model();
        sharePreferences = new MySharePreferences(context);


    }


    @Override
    public DashBoard_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_dash_board, parent, false);

        return new DashBoard_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DashBoard_Adapter.MyViewHolder holder, int position) {
        final DashBoard_car_model c_list = car_list.get(position);

        holder.tv_car_name.setText(c_list.getCar_name());
        holder.tv_car_no.setText(c_list.getCar_number());

        if(c_list.getVehicles_staus().equals("0"))
        {
            holder.card_car_name.setCardBackgroundColor(context.getResources().getColor(R.color.card_color));
            holder.card_car_name.setOnClickListener(null);
        }
        else {

            holder.card_car_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    record_save_model.setEmp_name(c_list.getDr_name());
                    record_save_model.setCar_number(c_list.getCar_number());
                    record_save_model.setCar_name(c_list.getCar_name());
                    record_save_model.setCar_timing(c_list.getTiming());
                    record_save_model.setDriver_id(c_list.getDriver_id());
                    record_save_model.setVehicle_id(c_list.getVehicles_id());

                    sharePreferences.save_car_data(context,record_save_model);
                    Intent intent = new Intent(context, Vehicles_Details.class);
                     intent.putExtra("car_Data",c_list);
                    context.startActivity(intent);
                  //  ((Activity)context).finish();


                }
            });
        }








    }


    @Override
    public int getItemCount() {

        return car_list.size();

    }



}




