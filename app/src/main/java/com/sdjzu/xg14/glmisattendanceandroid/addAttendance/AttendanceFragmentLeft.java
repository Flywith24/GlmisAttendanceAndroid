package com.sdjzu.xg14.glmisattendanceandroid.addAttendance;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class AttendanceFragmentLeft extends BaseFragment {
    private List<Employee> mEmployeesLeft = new ArrayList<>();
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
        return inflater.inflate(R.layout.fragment_add_attendance, container, false);
    }

    @Override
    protected void refresh() {
        setUpData();
        adapter.notifyAllSectionsDataSetChanged();
    }

    @Override
    public void setUpView(View view) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        adapter = new EmployeeAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUpData() {
        mEmployeesLeft.clear();
        DaoSession session = getInstances().getDaoSession();
        QueryBuilder<Employee> qb = session.getEmployeeDao().queryBuilder();
        mDepartments = listDepartment(session);
        qb.where(EmployeeDao.Properties.IsAttendant.eq(false))
                .orderAsc(EmployeeDao.Properties.Department);
        mEmployeesLeft.addAll(qb.list());
        adapter.addList(mEmployeesLeft, mDepartments);
        adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String name) {
                Employee employee = MyApplication.getInstances().getDaoSession().getEmployeeDao().queryBuilder()
                        .where(EmployeeDao.Properties.Name.eq(name)).unique();
                employee.setIsAttendant(true);
                getInstances().getDaoSession().getEmployeeDao().update(employee);
                mEmployeesLeft.remove(employee);
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
