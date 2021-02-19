package com.abasynuniversity.TransportSystem.forStudents.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.abasynuniversity.TransportSystem.forStudents.Model.vehical_Detail_registred_student_model;
import com.abasynuniversity.TransportSystem.forStudents.R;

import java.util.ArrayList;
import java.util.List;

public class Vehicle_detail_Adapter extends RecyclerView.Adapter<Vehicle_detail_Adapter.MyViewHolder> implements Filterable{

    Context context;
    private List<vehical_Detail_registred_student_model> stud_list, filterlist;
    int count;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_reg_no, tv_stu_name;
        CheckBox chk_status;

        public MyViewHolder(View view) {
            super(view);

            tv_reg_no = view.findViewById(R.id.tv_student_reg_no);
            tv_stu_name = view.findViewById(R.id.tv_student_name);
            chk_status = view.findViewById(R.id.chk_notify_student);


        }

    }

    public Vehicle_detail_Adapter(List<vehical_Detail_registred_student_model> stud_list, Context context) {
        this.stud_list = stud_list;
        this.context = context;
        this.filterlist = stud_list;

    }


    @Override
    public Vehicle_detail_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_registred_student, parent, false);

        return new Vehicle_detail_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Vehicle_detail_Adapter.MyViewHolder holder, int position) {
        final vehical_Detail_registred_student_model sub_list = stud_list.get(position);


        holder.tv_reg_no.setText(sub_list.getUser_reg_no());
        holder.tv_stu_name.setText(sub_list.getUser_name());
        holder.chk_status.setChecked(true);


        holder.chk_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sub_list.setBool_studetn_staus(isChecked);
                    sub_list.setStu_status("ok");
                    holder.chk_status.setTextColor(context.getResources().getColor(R.color.blue_light));
                    holder.chk_status.setText("Notification");
                } else {
                    sub_list.setBool_studetn_staus(isChecked);
                    sub_list.setStu_status("no");
                    holder.chk_status.setTextColor(context.getResources().getColor(R.color.blue_light));
                    holder.chk_status.setText("Notification");
                }

            }
        });


    }


    @Override
    public int getItemCount() {

        return stud_list.size();

    }
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                ArrayList<vehical_Detail_registred_student_model> arrayListFilter = new ArrayList<vehical_Detail_registred_student_model>();

                if(constraint == null|| constraint.length() == 0) {
                    results.count = filterlist.size();
                    results.values = filterlist;
                } else {
                    for (vehical_Detail_registred_student_model itemModel : filterlist) {
                        if(itemModel.getUser_name().toLowerCase().contains(constraint.toString().toLowerCase()) || itemModel.getUser_reg_no().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            arrayListFilter.add(itemModel);
                        }
                    }
                    results.count = arrayListFilter.size();
                    results.values = arrayListFilter;

                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stud_list = (ArrayList<vehical_Detail_registred_student_model>) results.values;
                notifyDataSetChanged();

                if(stud_list.size() == 0) {
                    Toast.makeText(context, "Not Found", Toast.LENGTH_LONG).show();
                }

            }
        };
        return filter;
    }
}