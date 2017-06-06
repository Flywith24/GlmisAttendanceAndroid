package com.sdjzu.xg14.glmisattendanceandroid.widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sdjzu.xg14.glmisattendanceandroid.HomeActivity;
import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.attendance.AddAttendanceActivity;
import com.sdjzu.xg14.glmisattendanceandroid.utils.T;

import static com.sdjzu.xg14.glmisattendanceandroid.R.id.tv_cancel;

/**
 * 自定义退出弹出框
 *
 * @author qinfan
 *         <p>
 *         2015-11-6
 */
public class MyDialog {
    private Activity activity;
    private AlertDialog dialog;
    private String title;

    public MyDialog(Activity activity, String title) {
        this.activity = activity;
        this.title = title;
    }

    public void showDialog() {
        dialog = new AlertDialog.Builder(activity).create();
        //点击外部区域不能取消dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(keylistener);
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.widget_my_dialog);
        final EditText et_name = (EditText) window.findViewById(R.id.dialog_edit);
        TextView tv_confirm = (TextView) window.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
        tv_confirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if ("".equals(et_name.getText().toString())) {
                    T.showToast(activity,"考勤名称不能为空");
                }else {
                    dialog.dismiss();
                    Intent intent = new Intent(activity, AddAttendanceActivity.class);
                    intent.putExtra("attendance_name", et_name.getText().toString());
                    activity.startActivity(intent);
                }
            }
        });

        tv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    public static OnKeyListener keylistener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
}
