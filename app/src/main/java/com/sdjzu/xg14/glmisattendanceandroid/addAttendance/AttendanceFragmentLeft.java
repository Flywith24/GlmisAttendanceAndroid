package com.sdjzu.xg14.glmisattendanceandroid.addAttendance;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.core.BaseFragment;
import com.sdjzu.xg14.glmisattendanceandroid.core.MyApplication;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.DaoSession;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.EmployeeDao;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

import static com.sdjzu.xg14.glmisattendanceandroid.core.MyApplication.getInstances;


/**
 * Created on 24/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AttendanceFragmentLeft extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLazyLoad();
    }

    @Override
    protected void refresh() {
        setUpData();
        adapter.notifyAllSectionsDataSetChanged();
    }

    @Override
    public void setUpView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        adapter = new EmployeeAdapter();
    }

    @Override
    public void setUpData() {
        mEmployees.clear();
        DaoSession daoSession = MyApplication.getInstances().getDaoSession();
        List<String> departments = listDepartment(daoSession);
        List<Employee> employees = daoSession.getEmployeeDao().queryBuilder()
                .where(EmployeeDao.Properties.IsAttendant.eq(false))
                .orderDesc(EmployeeDao.Properties.Department).list();
        mEmployees.addAll(employees);
        adapter.addList(mEmployees, departments);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String name) {
                Employee employee = MyApplication.getInstances().getDaoSession().getEmployeeDao().queryBuilder()
                        .where(EmployeeDao.Properties.Name.eq(name)).unique();
                employee.setIsAttendant(true);
                getInstances().getDaoSession().getEmployeeDao().update(employee);
                mEmployees.remove(employee);
                adapter.remove(employee);
            }
        });
    }

    /**
     * 获取所有的department
     *
     * @param session
     * @return
     */
    private List<String> listDepartment(DaoSession session) {
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT DISTINCT " + EmployeeDao.Properties.Department.columnName
                + " FROM " + EmployeeDao.TABLENAME + " WHERE IS_ATTENDANT = 0";
        try (Cursor c = session.getDatabase().rawQuery(query, null)) {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        }
        return result;
    }
}
