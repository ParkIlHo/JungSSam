package com.ilho.jungssam;

import android.content.Context;
import android.util.Log;

/**
 * Created by ian.park on 2018. 1. 17..
 */

public class DBConfig {


    //db name
    public static final String DB_NAME = "jungssam.db";

    // db version
    public static final int DB_VERSION	= 1;

    // table name
    public static final String TB_STUDENT = "student_info";

    // Columns
    public static final String CO_STD_ID = "std_id";
    public static final String CO_NAME = "name";
    public static final String CO_SCHOOL = "school";
    public static final String CO_YEAR = "year";
    public static final String CO_SUB_1 = "sub1"; // 한국사
    public static final String CO_SUB_2 = "sub2"; // 세계사
    public static final String CO_SUB_3 = "sub3"; // 동아시아사
    public static final String CO_SUB_4 = "sub4"; // 세계지리
    public static final String CO_SUB_5 = "sub5"; // 한국지리
    public static final String CO_SUB_6 = "sub6"; // 생활과윤리
    public static final String CO_SUB_7 = "sub7"; // 윤리와사상
    public static final String CO_SUB_8 = "sub8"; // 법과정치
    public static final String CO_SUB_9 = "sub9"; // 경제
    public static final String CO_SUB_10 = "sub10"; // 사문
    public static final String CO_SUB_11 = "sub11"; // 물리
    public static final String CO_SUB_12 = "sub12"; // 화학
    public static final String CO_SUB_13 = "sub13"; // 생물
    public static final String CO_SUB_14 = "sub14"; // 지구과학
    public static final String CO_SUB_15 = "sub15"; // 영어
    public static final String CO_SUB_16 = "sub16"; // 수학
    public static final String CO_DATE = "date";
    public static final String CO_STD_PHONE = "stu_phone";
    public static final String CO_PAR_PHONE = "par_phone";
    public static final String CO_OTHER = "other";

    public static final String CREATE_STUDENT = "CREATE TABLE IF NOT EXISTS " + TB_STUDENT +
            "(" + CO_STD_ID + " INT NOT NULL," +
            CO_NAME + " VARCHAR(20) NOT NULL," +
            CO_SCHOOL + " VARCHAR(20) NOT NULL," +
            CO_YEAR + " CHAR(1) NOT NULL DEFAULT 1," +
            CO_SUB_1 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_2 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_3 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_4 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_5 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_6 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_7 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_8 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_9 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_10 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_11 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_12 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_13 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_14 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_15 + " CHAR(1) NOT NULL DEFAULT N," +
            CO_SUB_16 + " CHAR(1) NOT NULL DEFAULT N," +
//            CO_DATE + " DATETIME," +
            CO_DATE + " VARCHAR(20) NOT NULL," +
            CO_STD_PHONE + " VARCHAR(20)," +
            CO_PAR_PHONE + " VARCHAR(20)," +
            CO_OTHER + " VARCHAR(500)," +
            " Primary Key(" + CO_STD_ID + ")" + ");";
}
