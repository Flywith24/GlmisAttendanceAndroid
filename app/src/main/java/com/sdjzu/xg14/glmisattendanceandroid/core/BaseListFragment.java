//package com.sdjzu.xg14.glmisattendanceandroid.core;
//
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.sdjzu.xg14.glmisattendanceandroid.R;
//import com.sdjzu.xg14.glmisattendanceandroid.attendance.AddAttendanceActivity;
//import com.sdjzu.xg14.glmisattendanceandroid.attendance.EmployeeAdapter;
//import com.sdjzu.xg14.glmisattendanceandroid.core.BaseFragment;
//import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.BasePresenter;
//import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;
//import com.sdjzu.xg14.glmisattendanceandroid.utils.L;
//import com.sdjzu.xg14.glmisattendanceandroid.widgets.DividerItemDecoration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created on 23/05/2017.
// *
// * @author YYZ
// * @version 1.0.0
// */
//
//
//public class BaseListFragment extends BaseFragment {
//
//
//    @Override
//    public void enableLazyLoad() {
//        super.enableLazyLoad();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        enableLazyLoad();
//    }
//
//    @Override
//    public void setUpView(View view) {
//
//    }
//
//    @Override
//    public void setUpData() {
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
//
//    public void showLoading() {
//        showProgressDialog();
//    }
//
//    public void hideLoading() {
//        dismissProgressDialog();
//    }
//
//}
