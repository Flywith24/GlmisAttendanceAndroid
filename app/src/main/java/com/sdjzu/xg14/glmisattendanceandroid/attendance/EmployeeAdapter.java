package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.sdjzu.xg14.glmisattendanceandroid.R.array.employees;

/**
 * Created on 30/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> mEmployees = new ArrayList<>();
    private EmployeeAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(EmployeeAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private TextView employeeName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            employeeName = (TextView) view.findViewById(R.id.employee_name);
        }
    }

    public EmployeeAdapter() {
    }

    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_employee_item, viewGroup, false));
    }

    public void addList(Collection<? extends Employee> employeeList){
        mEmployees.clear();
        mEmployees.addAll(employeeList);
        notifyDataSetChanged();


    }

    @Override
    public void onBindViewHolder(final EmployeeAdapter.ViewHolder holder, final int position) {
        Employee employee = mEmployees.get(position);
        holder.employeeName.setText(employee.getName());
        if (mOnItemClickListener != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.
                            onItemClick(holder.mView, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
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
        notifyDataSetChanged();
    }

}
