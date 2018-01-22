package com.ilho.jungssam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian.park on 2018. 1. 17..
 */

public class DB {

    private static DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;
    private Context context;

    public DB(Context context) {
        this.context = context;
    }

    private static DB db = null;

    public static DB getInstance(Context context) {
        if(db == null) {
            db = new DB(context);
        }
        return db;
    }

    public synchronized DB open() throws SQLException {
        if(mDbHelper == null) {
            mDbHelper = new DatabaseHelper(this.context);
            mDb = mDbHelper.getWritableDatabase();
        }
        return this;
    }

    public DB close() throws SQLException {
        mDb.close();
        mDbHelper.close();
        mDbHelper = null;

        return this;
    }

    private void chkDB() {
        try {
            if (mDb == null) {
                this.open();
            }
            if (mDb.isOpen() == false) {
                this.open();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long clearDb() {
        chkDB();

        long rtn = -1l;
        mDb.beginTransaction();

        try {
            rtn = mDb.delete(DBConfig.TB_STUDENT, null, null);

            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }
        return rtn;
    }

    public long setStudent(int stdId, String name, String school, String year,
                           String sub1, String sub2, String sub3, String sub4, String sub5, String sub6, String sub7, String sub8, String sub9, String sub10, String sub11, String sub12, String sub13, String sub14, String sub15, String sub16,
                           String date, String stdPhone, String parPhone, String other) {
        chkDB();

        long rtn = -1l;
        mDb.beginTransaction();

        try {
            String whereClause = "" +
                    DBConfig.CO_STD_ID + "=?";

            String[] whereArgs =
                    {
                            String.valueOf(stdId)
                    };

            ContentValues val = new ContentValues();
            val.put(DBConfig.CO_STD_ID, stdId);
            val.put(DBConfig.CO_NAME, name);
            val.put(DBConfig.CO_SCHOOL, school);
            val.put(DBConfig.CO_YEAR, year);
            val.put(DBConfig.CO_SUB_1, sub1);
            val.put(DBConfig.CO_SUB_2, sub2);
            val.put(DBConfig.CO_SUB_3, sub3);
            val.put(DBConfig.CO_SUB_4, sub4);
            val.put(DBConfig.CO_SUB_5, sub5);
            val.put(DBConfig.CO_SUB_6, sub6);
            val.put(DBConfig.CO_SUB_7, sub7);
            val.put(DBConfig.CO_SUB_8, sub8);
            val.put(DBConfig.CO_SUB_9, sub9);
            val.put(DBConfig.CO_SUB_10, sub10);
            val.put(DBConfig.CO_SUB_11, sub11);
            val.put(DBConfig.CO_SUB_12, sub12);
            val.put(DBConfig.CO_SUB_13, sub13);
            val.put(DBConfig.CO_SUB_14, sub14);
            val.put(DBConfig.CO_SUB_15, sub15);
            val.put(DBConfig.CO_SUB_16, sub16);
            val.put(DBConfig.CO_DATE, date);
            val.put(DBConfig.CO_STD_PHONE, stdPhone);
            val.put(DBConfig.CO_PAR_PHONE, parPhone);
            val.put(DBConfig.CO_OTHER, other);

            //Log.w("home","com_type: " + comInfo.com_type);
            rtn = mDb.update(DBConfig.TB_STUDENT, val, whereClause, whereArgs);
            if (rtn < 1) {
                Log.w("w", val.toString());
                rtn = mDb.insertOrThrow(DBConfig.TB_STUDENT, null, val);
            }

            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }
        return rtn;
    }

    public ArrayList<StudentInfo> getStudentAll() {
        chkDB();

        String sql = "SELECT * FROM " + DBConfig.TB_STUDENT;

        Log.e("DB:::getStudentAll","sql = " + sql);

        Cursor c = mDb.rawQuery(sql, null);

        if(c.getCount() > 0) {
            ArrayList<StudentInfo> studentInfoList = new ArrayList<StudentInfo>();

            while (c.moveToNext()) {
                StudentInfo info = new StudentInfo();

                info.std_id = c.getInt(c.getColumnIndex(DBConfig.CO_STD_ID));
                info.name = c.getString(c.getColumnIndex(DBConfig.CO_NAME));
                info.school = c.getString(c.getColumnIndex(DBConfig.CO_SCHOOL));
                info.year = c.getString(c.getColumnIndex(DBConfig.CO_YEAR));
                info.sub1 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_1));
                info.sub2 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_2));
                info.sub3 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_3));
                info.sub4 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_4));
                info.sub5 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_5));
                info.sub6 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_6));
                info.sub7 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_7));
                info.sub8 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_8));
                info.sub9 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_9));
                info.sub10 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_10));
                info.sub11 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_11));
                info.sub12 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_12));
                info.sub13 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_13));
                info.sub14 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_14));
                info.sub15 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_15));
                info.sub16 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_16));
                info.date = c.getString(c.getColumnIndex(DBConfig.CO_DATE));
                info.std_phone = c.getString(c.getColumnIndex(DBConfig.CO_STD_PHONE));
                info.parent_phone = c.getString(c.getColumnIndex(DBConfig.CO_PAR_PHONE));
                info.other = c.getString(c.getColumnIndex(DBConfig.CO_OTHER));

                studentInfoList.add(info);
            }
            c.close();
            return studentInfoList;
        } else {
            return new ArrayList<StudentInfo>();
        }

    }

    public ArrayList<Integer> getSubCount() {
        ArrayList<Integer> countList = new ArrayList<Integer>();

        String[] colLsit = {DBConfig.CO_SUB_1, DBConfig.CO_SUB_2, DBConfig.CO_SUB_3, DBConfig.CO_SUB_4,
                DBConfig.CO_SUB_5, DBConfig.CO_SUB_6, DBConfig.CO_SUB_7, DBConfig.CO_SUB_8,
                DBConfig.CO_SUB_9, DBConfig.CO_SUB_10, DBConfig.CO_SUB_11, DBConfig.CO_SUB_12,
                DBConfig.CO_SUB_13, DBConfig.CO_SUB_14, DBConfig.CO_SUB_15, DBConfig.CO_SUB_16};

        chkDB();

        for(int i = 0; i < colLsit.length; i++) {
            String sql = "SELECT count(1) as " + DBConfig.CO_STD_ID
                    + " FROM " + DBConfig.TB_STUDENT
                    + " WHERE " + colLsit[i] + " = 'Y'";

            Cursor c = mDb.rawQuery(sql, null);
            c.moveToFirst();

            if(c.getCount() > 0) {
                countList.add(c.getInt(0));
                Log.e("15151515", "151515 = " + c.getInt(0));
            } else {
                countList.add(0);
                Log.e("15151515", "151515 = 0");
            }
        }
        return countList;
    }

    public ArrayList<StudentInfo> getStudentSerchByName(String name) {
        chkDB();

        String sql = "SELECT * FROM " + DBConfig.TB_STUDENT
                + " WHERE " + DBConfig.CO_NAME + " = '" + name + "'";

        Log.e("DB","getStudentSerchByName::::sql = " + sql);

        Cursor c = mDb.rawQuery(sql, null);

        if(c.getCount() > 0) {
            ArrayList<StudentInfo> studentInfoList = new ArrayList<StudentInfo>();

            while (c.moveToNext()) {
                StudentInfo info = new StudentInfo();

                info.std_id = c.getInt(c.getColumnIndex(DBConfig.CO_STD_ID));
                info.name = c.getString(c.getColumnIndex(DBConfig.CO_NAME));
                info.school = c.getString(c.getColumnIndex(DBConfig.CO_SCHOOL));
                info.year = c.getString(c.getColumnIndex(DBConfig.CO_YEAR));
                info.sub1 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_1));
                info.sub2 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_2));
                info.sub3 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_3));
                info.sub4 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_4));
                info.sub5 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_5));
                info.sub6 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_6));
                info.sub7 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_7));
                info.sub8 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_8));
                info.sub9 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_9));
                info.sub10 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_10));
                info.sub11 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_11));
                info.sub12 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_12));
                info.sub13 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_13));
                info.sub14 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_14));
                info.sub15 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_15));
                info.sub16 = c.getString(c.getColumnIndex(DBConfig.CO_SUB_16));
                info.date = c.getString(c.getColumnIndex(DBConfig.CO_DATE));
                info.std_phone = c.getString(c.getColumnIndex(DBConfig.CO_DATE));
                info.parent_phone = c.getString(c.getColumnIndex(DBConfig.CO_PAR_PHONE));
                info.other = c.getString(c.getColumnIndex(DBConfig.CO_OTHER));

                studentInfoList.add(info);
            }
            c.close();
            return studentInfoList;
        } else {
            return new ArrayList<StudentInfo>();
        }

    }

    public int getLastStdId() {
        chkDB();


        String sql = "SELECT * FROM " + DBConfig.TB_STUDENT
                + " WHERE " + DBConfig.CO_STD_ID + " = (SELECT MAX(" + DBConfig.CO_STD_ID + ") FROM " + DBConfig.TB_STUDENT + ")";

//        String sql = "SELECT MAX(" + DBConfig.CO_STD_ID + ")"
//                + " FROM " + DBConfig.TB_STUDENT;

        Log.e("DB","getStudentSerchByName::::sql = " + sql);

        Cursor c = mDb.rawQuery(sql, null);

        if(c.getCount() > 0) {

            while (c.moveToNext()) {

                return c.getInt(c.getColumnIndex(DBConfig.CO_STD_ID)) + 1;
            }
        }
        return -1;
    }

    public long deleteStudent(int stdId) {
        chkDB();

        long rtn = -1l;
        mDb.beginTransaction();

        try {
            String whereClause = "" +
                    DBConfig.CO_STD_ID + "=?";

            String[] whereArgs =
                    {
                            String.valueOf(stdId)
                    };

            //Log.w("home","com_type: " + comInfo.com_type);
            rtn = mDb.delete(DBConfig.TB_STUDENT, whereClause, whereArgs);

            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }
        return rtn;
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        private String[] arr_sql_table = {
                DBConfig.CREATE_STUDENT
        };

        private Context mDbContext;

        public DatabaseHelper(Context context) {
            super(context, DBConfig.DB_NAME, null, DBConfig.DB_VERSION);
            mDbContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.e("DB onCreate() ::: ","onCreate : " + arr_sql_table[0]);
            execRawQuery(db, arr_sql_table);

            Log.d("DB onCreate() ::: ", "db : " + db);
            Log.d("DB onCreate() ::: ", "Creating database version : " + DBConfig.DB_VERSION + ".");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d("Home", "db : " + db);
            Log.d("Home", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

//            if (oldVersion < 2) {
//                String[] arr_sql = {
//                        DBConfig.CREATE_FEEDBACKINFO + ";",
//                        "PRAGMA user_version = 2;"
//                };
//                execRawQuery(db, arr_sql);
//            }
        }

        private void execRawQuery(SQLiteDatabase db, String[] arr_sql) {
            if (arr_sql != null) {
                for (String sql : arr_sql) {
                    try {
                        db.execSQL(sql);
                    } catch (Exception e) {
                        Log.e("exception","SQL ERROR : " + sql);
                    }
                }
            }
        }
    }
}
