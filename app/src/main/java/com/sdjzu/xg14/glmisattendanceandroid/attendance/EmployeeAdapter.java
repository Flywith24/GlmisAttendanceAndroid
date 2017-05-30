package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import java.util.List;

/**
 * Created on 30/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> mEmployees;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private TextView employeeName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            employeeName = (TextView) view.findViewById(R.id.employee_name);
        }
    }

    public EmployeeAdapter(List<Employee> employeeList) {
        mEmployees = employeeList;
    }

    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_employee_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Employee employee = mEmployees.get(position);
                Toast.makeText(v.getContext(), employee.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(EmployeeAdapter.ViewHolder holder, int position) {
        Employee employee = mEmployees.get(position);
        holder.employeeName.setText(employee.getName());
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    public void addData() {

    }

    /**
     * 添加数据
     */
    public void addData(int position, Employee e) {
        mEmployees.add(position, e);
        notifyItemInserted(position);
    }

    /**
     * 删除数据
     *
     * @param position
     */
    public void removeData(int position) {
        mEmployees.remove(position);
        notifyItemRemoved(position);
    }

}