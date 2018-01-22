package com.ilho.jungssam;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTabTotal, mTabSearch, mTabAdd, mTabExcel;
    private RelativeLayout mSearchLayout, mAddLayout, mExcelLayout;
    private RelativeLayout mSplashLayout;
    private LinearLayout mTotalLayout;

    private CheckBox mSub1, mSub2, mSub3, mSub4, mSub5, mSub6, mSub7, mSub8, mSub9, mSub10, mSub11, mSub12, mSub13, mSub14, mSub15, mSub16; // 학생 추가 수강과목 check box
    private TextView mTotal1, mTotal2, mTotal3, mTotal4, mTotal5, mTotal6, mTotal7, mTotal8, mTotal9, mTotal10, mTotal11, mTotal12, mTotal13, mTotal14, mTotal15, mTotal16, mTotalAll;

    private Button mBtnAddClear; // 학생 추가 화면 입력 clear 버튼
    private Button mBtnAddSave; // 학생 추가 저장 버튼

    private Button mBtnExelToDb;
    private Button mBtnDbToExel;
    private Button mBtnSearch;

    private EditText mEditSearch;

    private EditText mAddName, mAddSchool, mAddYear, mAddDate, mAddStdPhone, mAddParentPhone, mAddOther;


    private ListView mListView;

    private StudentListAdapter mListAdapter;

    private ArrayList<StudentInfo> mSearchStudentList = new ArrayList<StudentInfo>();

    private StdInfoDialog dialog = null;

    private final int REQUEST_PERMISSIONS = 100;

    private final static int TAB_SEARCH = 0;
    private final static int TAB_ADD = 1;
    private final static int TAB_EXCEL = 2;
    private final static int TAB_TOTAL = 3;

    private DB mDb = null;

    public interface DialogListener {
        public void clickConfirm();
        public void clickCancel();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > 22) {
            int perReEx = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int perWitEx = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (perReEx == PackageManager.PERMISSION_DENIED || perWitEx == PackageManager.PERMISSION_DENIED) {
                initPermission();
            }

            else {
                init();
                initTest();

                mDb = DB.getInstance(this);
            }
        }
        else {
            init();
            initTest();

            mDb = DB.getInstance(this);
        }
//        init();
//        initTest();
//        initPermission();
//
//        mDb = DB.getInstance(this);
    }

    private void initPermission() {
        final String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS)
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
                initTest();

                mDb = DB.getInstance(this);
            }

    }

    private void init() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSplashLayout.setVisibility(View.GONE);
            }
        }, 4000);
        mSplashLayout = (RelativeLayout) findViewById(R.id.splash);

        // 상단 탭 영역
        mTabTotal = (TextView) findViewById(R.id.tab_total);
        mTabSearch = (TextView) findViewById(R.id.tab_serch);
        mTabAdd = (TextView) findViewById(R.id.tab_add);
        mTabExcel = (TextView) findViewById(R.id.tab_excel);

        // 레이아웃 영역
        mTotalLayout = (LinearLayout) findViewById(R.id.total_layout);
        mSearchLayout = (RelativeLayout) findViewById(R.id.search_layout);
        mAddLayout = (RelativeLayout) findViewById(R.id.student_add_layout);
        mExcelLayout = (RelativeLayout) findViewById(R.id.exel_layout);

        //검색 화면
        mBtnSearch = (Button) findViewById(R.id.serch_btn);
        mEditSearch = (EditText) findViewById(R.id.serch_edit);
        mListView = (ListView) findViewById(R.id.serch_list);

        //학생 추가
        mAddName = (EditText) findViewById(R.id.add_edit_name);
        mAddSchool = (EditText) findViewById(R.id.add_edit_school);
        mAddYear = (EditText) findViewById(R.id.add_edit_year);
        mAddDate = (EditText) findViewById(R.id.add_edit_date);
        mAddStdPhone = (EditText) findViewById(R.id.add_edit_std_phone);
        mAddParentPhone = (EditText) findViewById(R.id.add_edit_parent_phone);
        mAddOther = (EditText) findViewById(R.id.add_edit_other);
        mSub1 = (CheckBox) findViewById(R.id.check_sub1);
        mSub2 = (CheckBox) findViewById(R.id.check_sub2);
        mSub3 = (CheckBox) findViewById(R.id.check_sub3);
        mSub4 = (CheckBox) findViewById(R.id.check_sub4);
        mSub5 = (CheckBox) findViewById(R.id.check_sub5);
        mSub6 = (CheckBox) findViewById(R.id.check_sub6);
        mSub7 = (CheckBox) findViewById(R.id.check_sub7);
        mSub8 = (CheckBox) findViewById(R.id.check_sub8);
        mSub9 = (CheckBox) findViewById(R.id.check_sub9);
        mSub10 = (CheckBox) findViewById(R.id.check_sub10);
        mSub11 = (CheckBox) findViewById(R.id.check_sub11);
        mSub12 = (CheckBox) findViewById(R.id.check_sub12);
        mSub13 = (CheckBox) findViewById(R.id.check_sub13);
        mSub14 = (CheckBox) findViewById(R.id.check_sub14);
        mSub15 = (CheckBox) findViewById(R.id.check_sub15);
        mSub16 = (CheckBox) findViewById(R.id.check_sub16);
        mBtnAddClear = (Button) findViewById(R.id.btn_add_clear);
        mBtnAddSave = (Button) findViewById(R.id.btn_add_save);
        mBtnAddClear.setOnClickListener(this);
        mBtnAddSave.setOnClickListener(this);

        // 통계화면
        mTotal1 = (TextView) findViewById(R.id.total1);
        mTotal2 = (TextView) findViewById(R.id.total2);
        mTotal3 = (TextView) findViewById(R.id.total3);
        mTotal4 = (TextView) findViewById(R.id.total4);
        mTotal5 = (TextView) findViewById(R.id.total5);
        mTotal6 = (TextView) findViewById(R.id.total6);
        mTotal7 = (TextView) findViewById(R.id.total7);
        mTotal8 = (TextView) findViewById(R.id.total8);
        mTotal9 = (TextView) findViewById(R.id.total9);
        mTotal10 = (TextView) findViewById(R.id.total10);
        mTotal11 = (TextView) findViewById(R.id.total11);
        mTotal12 = (TextView) findViewById(R.id.total12);
        mTotal13 = (TextView) findViewById(R.id.total13);
        mTotal14 = (TextView) findViewById(R.id.total14);
        mTotal15 = (TextView) findViewById(R.id.total15);
        mTotal16 = (TextView) findViewById(R.id.total16);
        mTotalAll = (TextView) findViewById(R.id.all_totall);


        mTabSearch.setBackgroundColor(Color.BLUE);
        mTabSearch.setTextColor(Color.WHITE);
        mTabAdd.setBackgroundColor(Color.WHITE);
        mTabAdd.setTextColor(Color.BLACK);
        mTabExcel.setBackgroundColor(Color.WHITE);
        mTabExcel.setTextColor(Color.BLACK);
        mTabTotal.setBackgroundColor(Color.WHITE);
        mTabTotal.setTextColor(Color.BLACK);

        mSearchLayout.setVisibility(View.VISIBLE);
        mAddLayout.setVisibility(View.GONE);
        mExcelLayout.setVisibility(View.GONE);
        mTotalLayout.setVisibility(View.GONE);

        mTabTotal.setOnClickListener(this);
        mTabSearch.setOnClickListener(this);
        mTabAdd.setOnClickListener(this);
        mTabExcel.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
    }

    private void initTest() {
        mBtnExelToDb = (Button) findViewById(R.id.exel_to_db_btn);
        mBtnDbToExel = (Button) findViewById(R.id.db_to_excel_btn);

        mBtnDbToExel.setOnClickListener(this);
        mBtnExelToDb.setOnClickListener(this);
    }

    private void tabSelect(int tab) {
        if(tab == TAB_SEARCH) {
            mTabSearch.setBackgroundColor(Color.BLUE);
            mTabSearch.setTextColor(Color.WHITE);
            mTabAdd.setBackgroundColor(Color.WHITE);
            mTabAdd.setTextColor(Color.BLACK);
            mTabExcel.setBackgroundColor(Color.WHITE);
            mTabExcel.setTextColor(Color.BLACK);
            mTabTotal.setBackgroundColor(Color.WHITE);
            mTabTotal.setTextColor(Color.BLACK);
        } else if(tab == TAB_ADD) {
            mTabAdd.setBackgroundColor(Color.BLUE);
            mTabAdd.setTextColor(Color.WHITE);
            mTabSearch.setBackgroundColor(Color.WHITE);
            mTabSearch.setTextColor(Color.BLACK);
            mTabExcel.setBackgroundColor(Color.WHITE);
            mTabExcel.setTextColor(Color.BLACK);
            mTabTotal.setBackgroundColor(Color.WHITE);
            mTabTotal.setTextColor(Color.BLACK);
        } else if(tab == TAB_EXCEL) {
            mTabExcel.setBackgroundColor(Color.BLUE);
            mTabExcel.setTextColor(Color.WHITE);
            mTabAdd.setBackgroundColor(Color.WHITE);
            mTabAdd.setTextColor(Color.BLACK);
            mTabSearch.setBackgroundColor(Color.WHITE);
            mTabSearch.setTextColor(Color.BLACK);
            mTabTotal.setBackgroundColor(Color.WHITE);
            mTabTotal.setTextColor(Color.BLACK);
        } else {
            mTabExcel.setBackgroundColor(Color.WHITE);
            mTabExcel.setTextColor(Color.BLACK);
            mTabAdd.setBackgroundColor(Color.WHITE);
            mTabAdd.setTextColor(Color.BLACK);
            mTabSearch.setBackgroundColor(Color.WHITE);
            mTabSearch.setTextColor(Color.BLACK);
            mTabTotal.setBackgroundColor(Color.BLUE);
            mTabTotal.setTextColor(Color.WHITE);
        }
    }


    @Override
    public void onClick(View view) {
        int resId = view.getId();

        if(resId == R.id.tab_serch) { // Tab search
            tabSelect(TAB_SEARCH);
            if(mSearchLayout.getVisibility() != View.VISIBLE) {
                mTotalLayout.setVisibility(View.GONE);
                mSearchLayout.setVisibility(View.VISIBLE);
                mAddLayout.setVisibility(View.GONE);
                mExcelLayout.setVisibility(View.GONE);
            }
        } else if (resId == R.id.tab_add) { // Tab 학생추가
            tabSelect(TAB_ADD);
            if(mAddLayout.getVisibility() != View.VISIBLE) {
                mTotalLayout.setVisibility(View.GONE);
                mSearchLayout.setVisibility(View.GONE);
                mAddLayout.setVisibility(View.VISIBLE);
                mExcelLayout.setVisibility(View.GONE);
            }
        } else if (resId == R.id.tab_excel) { // Tab excel
            tabSelect(TAB_EXCEL);
            if(mExcelLayout.getVisibility() != View.VISIBLE) {
                mTotalLayout.setVisibility(View.GONE);
                mSearchLayout.setVisibility(View.GONE);
                mAddLayout.setVisibility(View.GONE);
                mExcelLayout.setVisibility(View.VISIBLE);
            }
        } else if (resId == R.id.tab_total) { // Tab 통계
            tabSelect(TAB_TOTAL);
            if(mTotalLayout.getVisibility() != View.VISIBLE) {
                getTotal();
                mTotalLayout.setVisibility(View.VISIBLE);
                mSearchLayout.setVisibility(View.GONE);
                mAddLayout.setVisibility(View.GONE);
                mExcelLayout.setVisibility(View.GONE);
            }
        } else if (resId == R.id.exel_to_db_btn) { // 엑셀 to db 버튼
            copyExcelDataToDatabase();
        } else if (resId == R.id.db_to_excel_btn) { // db to excel 버튼)
            copyDatabaseToExcel();
        } else if (resId == R.id.serch_btn) { // search 화면의 search 버튼
            String name = mEditSearch.getText().toString();
            searchName(name);
        } else if (resId == R.id.btn_add_clear) {
            showDialog("", "입력내용 모두삭제합니다.", new DialogListener() {
                @Override
                public void clickConfirm() {
                    addLayoutClear();
                }

                @Override
                public void clickCancel() {

                }
            });

        } else if (resId == R.id.btn_add_save) {
            showDialog("학생저장", mAddName.getText().toString() + "학생 정보를 저장합니다", new DialogListener() {
                @Override
                public void clickConfirm() {
                    saveStudent();
                }

                @Override
                public void clickCancel() {

                }
            });
        }
    }

    private void getTotal() {
        int sum = 0;
        ArrayList<Integer> countList = mDb.getSubCount();
        String[] sub = {"한국사 : ", "세계사 : ", "동아시아사 : ", "세계지리 : ",
                "한국지리 : ", "생활과윤리 : ", "윤리와사상 : ", "법과정치 ",
                "경제 : ", "사문 : ", "물리 : ", "화확 : " ,
                "생물 : ", "지구과학 : ", "영어 : ", "수학 : "};
        TextView[] textLsit = {mTotal1, mTotal2, mTotal3, mTotal4,
                mTotal5, mTotal6, mTotal7, mTotal8,
                mTotal9, mTotal10, mTotal11, mTotal12,
                mTotal13, mTotal14, mTotal15, mTotal16};

        for(int i = 0; i < sub.length; i++) {
            TextView view = textLsit[i];
            view.setText(sub[i]+countList.get(i));
            sum = sum + countList.get(i);
        }
        mTotalAll.setText("총 합계 : " + sum);
    }

    private void showDialog(String title, String message, DialogListener dialogListener) {
        final DialogListener listener = dialogListener;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null) {
                    listener.clickConfirm();
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.setTitle(title);

        dialog.show();

    }

    private void addLayoutClear() {

        mAddName.setText("");
        mAddSchool.setText("");
        mAddYear.setText("");
        mAddDate.setText("");
        mAddStdPhone.setText("");
        mAddParentPhone.setText("");
        mAddOther.setText("");
        mSub1.setChecked(false);
        mSub2.setChecked(false);
        mSub3.setChecked(false);
        mSub4.setChecked(false);
        mSub5.setChecked(false);
        mSub6.setChecked(false);
        mSub7.setChecked(false);
        mSub8.setChecked(false);
        mSub9.setChecked(false);
        mSub10.setChecked(false);
        mSub11.setChecked(false);
        mSub12.setChecked(false);
        mSub13.setChecked(false);
        mSub14.setChecked(false);
        mSub15.setChecked(false);
        mSub16.setChecked(false);
    }

    private void saveStudent() {

        int std_id = mDb.getLastStdId();
        if(std_id <= 0) {
            std_id = 1;
        }

        String name = mAddName.getText().toString();
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


        mDb.setStudent(std_id, name, school, year,
                sub1, sub2, sub3, sub4, sub5, sub6, sub7, sub8, sub9, sub10, sub11, sub12, sub13, sub14, sub15, sub16,
                date, stdPhone, parPhone, other);
        addLayoutClear();

    }

    private void searchName(String name) {

        if(TextUtils.isEmpty(name)) {
            // 모든 학생 검색
            mSearchStudentList = mDb.getStudentAll();
        } else {
            // 이름으로 검색
            mSearchStudentList = mDb.getStudentSerchByName(name);
        }

        mListAdapter = new StudentListAdapter(this, R.layout.list_layout, mSearchStudentList);

        mListView.setDivider(new ColorDrawable(Color.BLACK));
        mListView.setDividerHeight(2);
        mListView.setSelector(new PaintDrawable(Color.BLUE));
        mListView.setAdapter(mListAdapter);
        mListView.setVisibility(View.VISIBLE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog = new StdInfoDialog(MainActivity.this, mSearchStudentList.get(i), mStdInfoDialogListener);
                dialog.show();
            }
        });
//        mListView.invalidateViews();
    }

    private StdInfoDialog.StdInfoDialogListener mStdInfoDialogListener = new StdInfoDialog.StdInfoDialogListener() {
        @Override
        public void clickSave(StudentInfo info) {

            Toast.makeText(MainActivity.this, info.name + " 학생 정보가 변경되었습니다.", Toast.LENGTH_SHORT).show();;
        }

        @Override
        public void clickDelete(final StudentInfo info) {
            showDialog("학생삭제", info.name + " 학생 정보를 삭제하시겠습니까?", new DialogListener() {
                @Override
                public void clickConfirm() {
                    mDb.deleteStudent(info.std_id);
                    dialog.dismiss();
                }

                @Override
                public void clickCancel() {

                }
            });
        }
    };

    private void copyDatabaseToExcel() {
        Log.w("DatabaseToExcel", "copyDatabaseToExcel()");

        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/JungSSam/");

        if(!path.exists()) {
            path.mkdirs();
        }

        try {
            File file = new File(path, "studentData.xls");

            if(file.exists()) {
                file.delete();
            }

            WorkbookSettings wbSetting = new WorkbookSettings();
            wbSetting.setLocale(new Locale("kr", "KO"));

            WritableWorkbook workbook;

            workbook = Workbook.createWorkbook(file, wbSetting);

            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            ArrayList<StudentInfo> stdList = new ArrayList<StudentInfo>();
            stdList = mDb.getStudentAll();

            if(stdList == null || stdList.size() == 0) {
                Toast.makeText(MainActivity.this, "학생 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            sheet.addCell(new Label(0,0, "no"));
            sheet.addCell(new Label(1,0, "이름"));
            sheet.addCell(new Label(3,0, "학교"));
            sheet.addCell(new Label(4,0, "학년"));
            sheet.addCell(new Label(5,0, "한국사"));
            sheet.addCell(new Label(6,0, "세계사"));
            sheet.addCell(new Label(7,0, "동아시아사"));
            sheet.addCell(new Label(8,0, "세계지리"));
            sheet.addCell(new Label(9,0, "한국지리"));
            sheet.addCell(new Label(10,0, "생활과윤리"));
            sheet.addCell(new Label(11,0, "윤리와사상"));
            sheet.addCell(new Label(12,0, "법과정치"));
            sheet.addCell(new Label(13,0, "경제"));
            sheet.addCell(new Label(14,0, "사문"));
            sheet.addCell(new Label(15,0, "물리"));
            sheet.addCell(new Label(16,0, "화학"));
            sheet.addCell(new Label(17,0, "생물"));
            sheet.addCell(new Label(18,0, "지구과학"));
            sheet.addCell(new Label(19,0, "영어"));
            sheet.addCell(new Label(20,0, "수학"));
            sheet.addCell(new Label(21,0, "등록일"));
            sheet.addCell(new Label(22,0, "학생연락처"));
            sheet.addCell(new Label(23,0, "부모연락처"));
            sheet.addCell(new Label(24,0, "특이사항"));

            for(int i = 0; i < stdList.size(); i++) {
                StudentInfo info = stdList.get(i);
                sheet.addCell(new Label(0,i+1, String.valueOf(info.std_id)));
                sheet.addCell(new Label(1,i+1, info.name));
                sheet.addCell(new Label(3,i+1, info.school));
                sheet.addCell(new Label(4,i+1, info.year));
                sheet.addCell(new Label(5,i+1, info.sub1));
                sheet.addCell(new Label(6,i+1, info.sub2));
                sheet.addCell(new Label(7,i+1, info.sub3));
                sheet.addCell(new Label(8,i+1, info.sub4));
                sheet.addCell(new Label(9,i+1, info.sub5));
                sheet.addCell(new Label(10,i+1, info.sub6));
                sheet.addCell(new Label(11,i+1, info.sub7));
                sheet.addCell(new Label(12,i+1, info.sub8));
                sheet.addCell(new Label(13,i+1, info.sub9));
                sheet.addCell(new Label(14,i+1, info.sub10));
                sheet.addCell(new Label(15,i+1, info.sub11));
                sheet.addCell(new Label(16,i+1, info.sub12));
                sheet.addCell(new Label(17,i+1, info.sub13));
                sheet.addCell(new Label(18,i+1, info.sub14));
                sheet.addCell(new Label(19,i+1, info.sub15));
                sheet.addCell(new Label(20,i+1, info.sub16));
                sheet.addCell(new Label(21,i+1, info.date));
                sheet.addCell(new Label(22,i+1, info.std_phone));
                sheet.addCell(new Label(23,i+1, info.parent_phone));
                sheet.addCell(new Label(24,i+1, info.other));
            }

            workbook.write();
            workbook.close();
            Toast.makeText(MainActivity.this, "데이터베이스를 엑셀로 저장완료하였습니다.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("151515", e.toString());
        }
    }

    private void copyExcelDataToDatabase() {
        Log.w("ExcelToDatabase", "copyExcelDataToDatabase()");


        Workbook workbook = null;
        Sheet sheet = null;
//        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "student.xls");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/JungSSam/student.xls");
        try {
//            InputStream is = getBaseContext().getResources().getAssets().open("student.xls");
            if(file == null || !file.exists()) {
                Toast.makeText(MainActivity.this, "엑셀파일이 없음.", Toast.LENGTH_SHORT).show();
                Log.e("151515", "엑셀파일이 없음 !!!");
                return;
            }
            workbook = Workbook.getWorkbook(file);
//            workbook = Workbook.getWorkbook(is);

            if (workbook != null) {
                sheet = workbook.getSheet(1);

                if (sheet != null) {

                    mDb.clearDb();
//                    if(true)
//                        return;

                    int nMaxColumn = 24;
                    int nRowStartIndex = 1;
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0;
                    int nColumnEndIndex = sheet.getRow(nRowEndIndex).length - 1;

                    for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
                        String title = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String body = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                        Log.e("151515", sheet.getCell(nColumnStartIndex, nRow).getContents().toString());
                        Log.e("151515", sheet.getCell(nColumnStartIndex+1, nRow).getContents().toString());
                        Log.e("151515", sheet.getCell(nColumnStartIndex+2, nRow).getContents().toString());
                        Log.e("151515", sheet.getCell(nColumnStartIndex+3, nRow).getContents().toString());
                        Log.e("151515", sheet.getCell(nColumnStartIndex+4, nRow).getContents().toString());
                        Log.e("151515", sheet.getCell(nColumnStartIndex+5, nRow).getContents().toString());
                        Log.e("151515", sheet.getCell(nColumnStartIndex+6, nRow).getContents().toString());
                        Log.e("151515", sheet.getCell(nColumnStartIndex+7, nRow).getContents().toString());
                        Log.e("151515", sheet.getCell(nColumnStartIndex+8, nRow).getContents().toString());

                        int id = Integer.valueOf(sheet.getCell(nColumnStartIndex, nRow).getContents().toString());
                        String name = sheet.getCell(nColumnStartIndex+1, nRow).getContents().toString();
                        String school = sheet.getCell(nColumnStartIndex+2, nRow).getContents().toString();
                        String year = sheet.getCell(nColumnStartIndex+3, nRow).getContents().toString();
                        String sub1 = sheet.getCell(nColumnStartIndex+4, nRow).getContents().toString();
                        String sub2 = sheet.getCell(nColumnStartIndex+5, nRow).getContents().toString();
                        String sub3 = sheet.getCell(nColumnStartIndex+6, nRow).getContents().toString();
                        String sub4 = sheet.getCell(nColumnStartIndex+7, nRow).getContents().toString();
                        String sub5 = sheet.getCell(nColumnStartIndex+8, nRow).getContents().toString();
                        String sub6 = sheet.getCell(nColumnStartIndex+9, nRow).getContents().toString();
                        String sub7 = sheet.getCell(nColumnStartIndex+10, nRow).getContents().toString();
                        String sub8 = sheet.getCell(nColumnStartIndex+11, nRow).getContents().toString();
                        String sub9 = sheet.getCell(nColumnStartIndex+12, nRow).getContents().toString();
                        String sub10 = sheet.getCell(nColumnStartIndex+13, nRow).getContents().toString();
                        String sub11 = sheet.getCell(nColumnStartIndex+14, nRow).getContents().toString();
                        String sub12 = sheet.getCell(nColumnStartIndex+15, nRow).getContents().toString();
                        String sub13 = sheet.getCell(nColumnStartIndex+16, nRow).getContents().toString();
                        String sub14 = sheet.getCell(nColumnStartIndex+17, nRow).getContents().toString();
                        String sub15 = sheet.getCell(nColumnStartIndex+18, nRow).getContents().toString();
                        String sub16 = sheet.getCell(nColumnStartIndex+19, nRow).getContents().toString();
                        String date = sheet.getCell(nColumnStartIndex+20, nRow).getContents().toString();
                        String stdPhone = sheet.getCell(nColumnStartIndex+21, nRow).getContents().toString();
                        String parPhone = sheet.getCell(nColumnStartIndex+22, nRow).getContents().toString();
                        String other = sheet.getCell(nColumnStartIndex+23, nRow).getContents().toString();

                        mDb.setStudent(id, name, school, year,
                                sub1, sub2,  sub3, sub4, sub5, sub6, sub7, sub8, sub9, sub10, sub11, sub12, sub13, sub14, sub15, sub16,
                                date, stdPhone, parPhone, other);

                    }
                } else {
                    System.out.println("Sheet is null!!");
                }
            } else {
                System.out.println("WorkBook is null!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
                file.delete();
            }
        }
    }

}
