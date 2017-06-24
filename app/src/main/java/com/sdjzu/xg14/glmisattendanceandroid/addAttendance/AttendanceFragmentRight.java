package com.sdjzu.xg14.glmisattendanceandroid.addAttendance;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.core.BaseFragment;
import com.sdjzu.xg14.glmisattendanceandroid.core.MyApplication;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.DaoSession;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.EmployeeDao;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import org.greenrobot.greendao.query.QueryBuilder;
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

public class AttendanceFragmentRight extends BaseFragment {
    private List<Employee> mEmployeesRight = new ArrayList<>();
    private List<String> mDepartments = new ArrayList<>();
    private EmployeeAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_attendance_right, container, false);
    }

    @Override
    protected void refresh() {
        setUpData();
        adapter.notifyAllSectionsDataSetChanged();
    }

    @Override
    public void setUpView(View view) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        adapter = new EmployeeAdapter();

    }

    @Override
    public void setUpData() {
        mEmployeesRight.clear();
        recyclerView.setAdapter(adapter);
        DaoSession session = getInstances().getDaoSession();
        QueryBuilder<Employee> qb = session.getEmployeeDao().queryBuilder();
        mDepartments = listDepartment(session);
        qb.where(EmployeeDao.Properties.IsAttendant.eq(true))
                .orderAsc(EmployeeDao.Properties.Department);
        mEmployeesRight.addAll(qb.list());
        adapter.addList(mEmployeesRight, mDepartments);
        adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String name) {
                Employee employee = MyApplication.getInstances().getDaoSession().getEmployeeDao().queryBuilder()
                        .where(EmployeeDao.Properties.Name.eq(name)).unique();
                employee.setIsAttendant(false);
                getInstances().getDaoSession().getEmployeeDao().update(employee);
                mEmployeesRight.remove(employee);
                adapter.notifyDataSetChanged();
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
                + " FROM " + EmployeeDao.TABLENAME + " WHERE IS_ATTENDANT = 1";
        Cursor c = session.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }
}