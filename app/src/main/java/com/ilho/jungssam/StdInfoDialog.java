package com.ilho.jungssam;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ian.park on 2018. 1. 18..
 */

public class StdInfoDialog extends Dialog {

    private Context mContext;

    private StudentInfo mStudentInfo;

    private TextView mTitle;

    private CheckBox mSub1, mSub2, mSub3, mSub4, mSub5, mSub6, mSub7, mSub8, mSub9, mSub10, mSub11, mSub12, mSub13, mSub14, mSub15, mSub16; // 학생 추가 수강과목 check box

    private EditText mAddName, mAddSchool, mAddYear, mAddDate, mAddStdPhone, mAddParentPhone, mAddOther;

    private Button mBtnEdit, mBtnDelete, mBtnSave;

    private LinearLayout mButtonLayout;

    private DB mDb = null;

    private StdInfoDialogListener mListener = null;

    public interface StdInfoDialogListener {
        public void clickSave(StudentInfo info);
        public void clickDelete(StudentInfo info);
    }

    public StdInfoDialog(@NonNull Context context, StudentInfo stdInfo, StdInfoDialogListener listener) {
        super(context);
        mContext = context;
        this.mStudentInfo = stdInfo;
        this.mListener = listener;

        setContentView(R.layout.std_info_dialog_layout);

        init();
    }

    private void init() {
        mDb = DB.getInstance(mContext);
        mTitle = (TextView) findViewById(R.id.dlg_title);
        mAddName = (EditText) findViewById(R.id.dlg_add_edit_name);
        mAddSchool = (EditText) findViewById(R.id.dlg_add_edit_school);
        mAddYear = (EditText) findViewById(R.id.dlg_add_edit_year);
        mAddDate = (EditText) findViewById(R.id.dlg_add_edit_date);
        mAddStdPhone = (EditText) findViewById(R.id.dlg_add_edit_std_phone);
        mAddParentPhone = (EditText) findViewById(R.id.dlg_add_edit_parent_phone);
        mAddOther = (EditText) findViewById(R.id.dlg_add_edit_other);
        mSub1 = (CheckBox) findViewById(R.id.dlg_check_sub1);
        mSub2 = (CheckBox) findViewById(R.id.dlg_check_sub2);
        mSub3 = (CheckBox) findViewById(R.id.dlg_check_sub3);
        mSub4 = (CheckBox) findViewById(R.id.dlg_check_sub4);
        mSub5 = (CheckBox) findViewById(R.id.dlg_check_sub5);
        mSub6 = (CheckBox) findViewById(R.id.dlg_check_sub6);
        mSub7 = (CheckBox) findViewById(R.id.dlg_check_sub7);
        mSub8 = (CheckBox) findViewById(R.id.dlg_check_sub8);
        mSub9 = (CheckBox) findViewById(R.id.dlg_check_sub9);
        mSub10 = (CheckBox) findViewById(R.id.dlg_check_sub10);
        mSub11 = (CheckBox) findViewById(R.id.dlg_check_sub11);
        mSub12 = (CheckBox) findViewById(R.id.dlg_check_sub12);
        mSub13 = (CheckBox) findViewById(R.id.dlg_check_sub13);
        mSub14 = (CheckBox) findViewById(R.id.dlg_check_sub14);
        mSub15 = (CheckBox) findViewById(R.id.dlg_check_sub15);
        mSub16 = (CheckBox) findViewById(R.id.dlg_check_sub16);

        mButtonLayout = (LinearLayout) findViewById(R.id.dlg_student_btn_layout);
        mBtnEdit = (Button) findViewById(R.id.dlg_std_edit_btn);
        mBtnDelete = (Button) findViewById(R.id.dlg_std_delete_btn);
        mBtnSave = (Button) findViewById(R.id.dlg_btn_add_save);

        mBtnEdit.setOnClickListener(mOnClickListener);
        mBtnDelete.setOnClickListener(mOnClickListener);
        mBtnSave.setOnClickListener(mOnClickListener);

        mAddName.setText(mStudentInfo.name);
        mAddSchool.setText(mStudentInfo.school);
        mAddYear.setText(mStudentInfo.year);
        mAddDate.setText(mStudentInfo.date);
        mAddStdPhone.setText(mStudentInfo.std_phone);
        mAddParentPhone.setText(mStudentInfo.parent_phone);
        mAddOther.setText(mStudentInfo.other);
        mSub1.setChecked(mStudentInfo.sub1.equalsIgnoreCase("Y") ? true:false);
        mSub2.setChecked(mStudentInfo.sub2.equalsIgnoreCase("Y") ? true:false);
        mSub3.setChecked(mStudentInfo.sub3.equalsIgnoreCase("Y") ? true:false);
        mSub4.setChecked(mStudentInfo.sub4.equalsIgnoreCase("Y") ? true:false);
        mSub5.setChecked(mStudentInfo.sub5.equalsIgnoreCase("Y") ? true:false);
        mSub6.setChecked(mStudentInfo.sub6.equalsIgnoreCase("Y") ? true:false);
        mSub7.setChecked(mStudentInfo.sub7.equalsIgnoreCase("Y") ? true:false);
        mSub8.setChecked(mStudentInfo.sub8.equalsIgnoreCase("Y") ? true:false);
        mSub9.setChecked(mStudentInfo.sub9.equalsIgnoreCase("Y") ? true:false);
        mSub10.setChecked(mStudentInfo.sub10.equalsIgnoreCase("Y") ? true:false);
        mSub11.setChecked(mStudentInfo.sub11.equalsIgnoreCase("Y") ? true:false);
        mSub12.setChecked(mStudentInfo.sub12.equalsIgnoreCase("Y") ? true:false);
        mSub13.setChecked(mStudentInfo.sub13.equalsIgnoreCase("Y") ? true:false);
        mSub14.setChecked(mStudentInfo.sub14.equalsIgnoreCase("Y") ? true:false);
        mSub15.setChecked(mStudentInfo.sub15.equalsIgnoreCase("Y") ? true:false);
        mSub16.setChecked(mStudentInfo.sub16.equalsIgnoreCase("Y") ? true:false);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int resId = view.getId();
            if(resId == R.id.dlg_std_edit_btn) {
                changeEditMode();
            } else if(resId == R.id.dlg_std_delete_btn) {
                if(mListener != null) {
                    mListener.clickDelete(mStudentInfo);
                }

            } else if(resId == R.id.dlg_btn_add_save) {
                changeViewMode();

                String school = mAddSchool.getText().toString();
                String year = mAddYear.getText().toString();
                String sub1 = mSub1.isChecked() ? "Y":"N";
                String sub2 = mSub2.isChecked() ? "Y":"N";
                String sub3 = mSub3.isChecked() ? "Y":"N";
                String sub4 = mSub4.isChecked() ? "Y":"N";
                String sub5 = mSub5.isChecked() ? "Y":"N";
                String sub6 = mSub6.isChecked() ? "Y":"N";
                String sub7 = mSub7.isChecked() ? "Y":"N";
                String sub8 = mSub8.isChecked() ? "Y":"N";
                String sub9 = mSub9.isChecked() ? "Y":"N";
                String sub10 = mSub10.isChecked() ? "Y":"N";
                String sub11 = mSub11.isChecked() ? "Y":"N";
                String sub12 = mSub12.isChecked() ? "Y":"N";
                String sub13 = mSub13.isChecked() ? "Y":"N";
                String sub14 = mSub14.isChecked() ? "Y":"N";
                String sub15 = mSub15.isChecked() ? "Y":"N";
                String sub16 = mSub16.isChecked() ? "Y":"N";

                String date = mAddDate.getText().toString();
                String stdPhone = mAddStdPhone.getText().toString();
                String parPhone = mAddParentPhone.getText().toString();
                String other = mAddOther.getText().toString();

                mDb.setStudent(mStudentInfo.std_id, mStudentInfo.name, school, year,
                        sub1, sub2, sub3, sub4, sub5, sub6, sub7, sub8, sub9, sub10, sub11, sub12, sub13, sub14, sub15, sub16,
                        date, stdPhone, parPhone, other);
                mListener.clickSave(mStudentInfo);
            }
        }
    };

    private void changeEditMode() {
        mAddSchool.setEnabled(true);
        mAddYear.setEnabled(true);
        mAddDate.setEnabled(true);
        mAddStdPhone.setEnabled(true);
        mAddParentPhone.setEnabled(true);
        mAddOther.setEnabled(true);

        mSub1.setClickable(true);
        mSub2.setClickable(true);
        mSub3.setClickable(true);
        mSub4.setClickable(true);
        mSub5.setClickable(true);
        mSub6.setClickable(true);
        mSub7.setClickable(true);
        mSub8.setClickable(true);
        mSub9.setClickable(true);
        mSub10.setClickable(true);
        mSub11.setClickable(true);
        mSub12.setClickable(true);
        mSub13.setClickable(true);
        mSub14.setClickable(true);
        mSub15.setClickable(true);
        mSub16.setClickable(true);

        mTitle.setText("학생정보(수정모드)");

        mButtonLayout.setVisibility(View.VISIBLE);
        mBtnEdit.setVisibility(View.INVISIBLE);

    }

    private void changeViewMode() {
        mAddSchool.setEnabled(false);
        mAddYear.setEnabled(false);
        mAddDate.setEnabled(false);
        mAddStdPhone.setEnabled(false);
        mAddParentPhone.setEnabled(false);
        mAddOther.setEnabled(false);

        mSub1.setClickable(false);
        mSub2.setClickable(false);
        mSub3.setClickable(false);
        mSub4.setClickable(false);
        mSub5.setClickable(false);
        mSub6.setClickable(false);
        mSub7.setClickable(false);
        mSub8.setClickable(false);
        mSub9.setClickable(false);
        mSub10.setClickable(false);
        mSub11.setClickable(false);
        mSub12.setClickable(false);
        mSub13.setClickable(false);
        mSub14.setClickable(false);
        mSub15.setClickable(false);
        mSub16.setClickable(false);

        mTitle.setText("학생정보");

        mButtonLayout.setVisibility(View.GONE);
        mBtnEdit.setVisibility(View.VISIBLE);
    }
}
