package com.example.personfinder.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDataHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "Coolie.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_OPERATOR_LOGIN_DATA = "table_operator_login_Data";
    public static final String TABLE_CARRIER_LOGIN_DATA = "table_carrier_login_Data";
    public static final String TABLE_OPERATOR_CONFIG = "table_operator_config";
    public static final String TABLE_OPERATOR_COMPANY_INFO = "table_operator_company_info";
    public static final String TABLE_CARRIER_INDIVIDUAL_LOGIN_DATA = "table_carrier__individual_login_Data";
    public static final String F_NAME = "f_name";
    public static final String L_NAME = "l_name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    public static final String LABOUR_COST = "labour_cost";
    public static final String QUICK_ORDER_COST = "quick_order_cost";
    public static final String ROOM_COST = "room_cost";
    public static final String SQ_FIT_COST = "sq_fit_cost";
    public static final String VILLAS_ROOM_COST = "villas_room_cost";
    public static final String GOOD_WRAP_COST = "good_rap_cost";
    public static final String QUICK_ORDER_HOUR = "quick_order_hour";
    public static final String TYPE = "type";
    public static final String TYPE_ID = "type_id";
    public static final String CREATED_ID = "created_at";
    public static final String UPDATED_ID = "updated_at";

    public static final String NTN = "ntn";
    public static final String COMPANY_NAME = "company_name";
    public static final String COMPANY_DATE = "company_date";
    public static final String ADDRESS = "address";
    public static final String COMPANY_LOGO = "company_logo";
    public static final String TRADE_LICENSE = "tarde_license";
    public static final String MEMORANDUM_ARTICLEID = "memorandum_articled";
    public static final String PASSPORT = "passport";
    public static final String VALID_RESIDANCE_VISA = "valid_residance_visa";
    public static final String EMIRATES_ID = "emirates_id";
    public static final String RTA = "rta";

    public SqliteDataHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
//        super(context, Environment.getExternalStorageDirectory() + "/Coolie-Shipper-DB/" + DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_OPERATOR_LOGIN_DATA + " (id INTEGER  primary key , " + F_NAME + " TEXT,  " + L_NAME + " TEXT, " + EMAIL + " TEXT , " + PHONE + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_CARRIER_LOGIN_DATA + " (id INTEGER  primary key , " + F_NAME + " TEXT,  " + L_NAME + " TEXT, " + EMAIL + " TEXT , " + PHONE + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_CARRIER_INDIVIDUAL_LOGIN_DATA + " (id INTEGER  primary key , " + F_NAME + " TEXT,  " + L_NAME + " TEXT, " + EMAIL + " TEXT , " + PHONE + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_OPERATOR_CONFIG + " (id INTEGER  primary key , " + LABOUR_COST + " TEXT,  " + QUICK_ORDER_COST + " TEXT, " + ROOM_COST + " TEXT , " + SQ_FIT_COST + " TEXT , "
        + VILLAS_ROOM_COST + " TEXT, " + GOOD_WRAP_COST + " TEXT, " + QUICK_ORDER_HOUR + " TEXT," + CREATED_ID + " TEXT," + UPDATED_ID + " TEXT,"
        + TYPE + " TEXT," + TYPE_ID + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_OPERATOR_COMPANY_INFO + " (id INTEGER  primary key , " + NTN + " TEXT,  " + COMPANY_NAME + " TEXT, " + COMPANY_DATE + " TEXT, "
                + ADDRESS + " TEXT , " + COMPANY_LOGO + " TEXT," + TRADE_LICENSE + " TEXT," + MEMORANDUM_ARTICLEID + " TEXT,"
        + PASSPORT + " TEXT," + VALID_RESIDANCE_VISA + " TEXT," + EMIRATES_ID + " TEXT," + RTA + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATOR_LOGIN_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARRIER_LOGIN_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATOR_CONFIG);
        onCreate(db);
    }


    public void insertDataOperatorLogin(String f_name, String l_name, String email, String phone) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues conValues = new ContentValues();
        conValues.put(F_NAME, f_name);
        conValues.put(L_NAME, l_name);
        conValues.put(EMAIL, email);
        conValues.put(PHONE, phone);

        long reusul = db.insert(TABLE_OPERATOR_LOGIN_DATA, null, conValues);
    }

    public boolean updateDataOperator(String f_name, String l_name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",1);
        contentValues.put(F_NAME,f_name);
        contentValues.put(L_NAME,l_name);
        contentValues.put(EMAIL,email);
        contentValues.put(PHONE,phone);
        db.update(TABLE_OPERATOR_LOGIN_DATA, contentValues, "id = ?",new String[] { "1" });
        return true;
    }

    public boolean updateDataCarrier(String f_name, String l_name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",1);
        contentValues.put(F_NAME,f_name);
        contentValues.put(L_NAME,l_name);
        contentValues.put(EMAIL,email);
        contentValues.put(PHONE,phone);
        db.update(TABLE_CARRIER_LOGIN_DATA, contentValues, "id = ?",new String[] { "1" });
        return true;
    }

    public Cursor getOperatorLoginData() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select * from " + TABLE_OPERATOR_LOGIN_DATA, null);

        return cur;
    }

    public void insertDataCarrierLogin(String f_name, String l_name, String email, String phone) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues conValues = new ContentValues();
        conValues.put(F_NAME, f_name);
        conValues.put(L_NAME, l_name);
        conValues.put(EMAIL, email);
        conValues.put(PHONE, phone);

        db.insert(TABLE_CARRIER_LOGIN_DATA, null, conValues);
    }


    public void insertDataCarrierIndividualLogin(String f_name, String l_name, String email, String phone) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues conValues = new ContentValues();
        conValues.put(F_NAME, f_name);
        conValues.put(L_NAME, l_name);
        conValues.put(EMAIL, email);
        conValues.put(PHONE, phone);

        db.insert(TABLE_CARRIER_INDIVIDUAL_LOGIN_DATA, null, conValues);
    }

    public Cursor getCarrierLoginData() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select * from " + TABLE_CARRIER_LOGIN_DATA, null);

        return cur;
    }


    public Cursor getCarrierIndividualLoginData() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select * from " + TABLE_CARRIER_INDIVIDUAL_LOGIN_DATA, null);

        return cur;
    }

    public Cursor getOperatorCompanyData() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select * from " + TABLE_OPERATOR_COMPANY_INFO, null);

        return cur;
    }


    public void deleteOperatorLoginData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OPERATOR_LOGIN_DATA, null, null);
        db.execSQL("delete from " + TABLE_OPERATOR_LOGIN_DATA);
        db.close();
    }

    public void deleteCarrierLoginData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARRIER_LOGIN_DATA, null, null);
        db.execSQL("delete from " + TABLE_CARRIER_LOGIN_DATA);
        db.close();
    }

    public void deleteCarrierIndividualLoginData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARRIER_INDIVIDUAL_LOGIN_DATA, null, null);
        db.execSQL("delete from " + TABLE_CARRIER_INDIVIDUAL_LOGIN_DATA);
        db.close();
    }

    public void insertOperatorConfig(String labour_cost, String quick_order_cost, String room_cost, String sq_fit_cost, String villas_room_cost,
                                     String good_rap_cost, String quick_order_hour, String created_at, String updated_at, String type, int type_id) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues conValues = new ContentValues();
        conValues.put(LABOUR_COST, labour_cost);
        conValues.put(QUICK_ORDER_COST, quick_order_cost);
        conValues.put(ROOM_COST, room_cost);
        conValues.put(SQ_FIT_COST, sq_fit_cost);
        conValues.put(VILLAS_ROOM_COST, villas_room_cost);
        conValues.put(GOOD_WRAP_COST, good_rap_cost);
        conValues.put(QUICK_ORDER_HOUR, quick_order_hour);
        conValues.put(CREATED_ID,created_at);
        conValues.put(UPDATED_ID,updated_at);
        conValues.put(TYPE,type);
        conValues.put(TYPE_ID,type_id);

        long result = db.insert(TABLE_OPERATOR_CONFIG, null, conValues);
        int abc = 10;
    }

    public Cursor getOperatorConfig() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select * from " + TABLE_OPERATOR_CONFIG, null);

        return cur;
    }

    public boolean updateOperatorConfig(String labour_cost, String quick_order_cost, String room_cost, String sq_fit_cost,
                                        String villas_room_cost, String good_rap_cost, String quick_order_hour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",1);
        contentValues.put(LABOUR_COST,labour_cost);
        contentValues.put(QUICK_ORDER_COST,quick_order_cost);
        contentValues.put(ROOM_COST,room_cost);
        contentValues.put(SQ_FIT_COST,sq_fit_cost);
        contentValues.put(VILLAS_ROOM_COST,villas_room_cost);
        contentValues.put(GOOD_WRAP_COST,good_rap_cost);
        contentValues.put(QUICK_ORDER_HOUR,quick_order_hour);

        int result = db.update(TABLE_OPERATOR_CONFIG, contentValues, "id = ?",new String[] { "1" });
        return true;
    }

    public void deleteOperatorConfig() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OPERATOR_CONFIG, null, null);
        db.execSQL("delete from " + TABLE_OPERATOR_CONFIG);
        db.close();
    }

    public void insertOperatorCompanyData(String ntn, String company_name, String company_date, String address, String company_logo, String tarde_license,
                                          String memorandum_articled, String passport, String valid_residance_visa, String emirates_id, String rta) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues conValues = new ContentValues();
        conValues.put(NTN, ntn);
        conValues.put(COMPANY_NAME,company_name);
        conValues.put(COMPANY_DATE, company_date);
        conValues.put(ADDRESS, address);
        conValues.put(COMPANY_LOGO, company_logo);
        conValues.put(TRADE_LICENSE, tarde_license);
        conValues.put(MEMORANDUM_ARTICLEID, memorandum_articled);
        conValues.put(PASSPORT, passport);
        conValues.put(VALID_RESIDANCE_VISA,valid_residance_visa);
        conValues.put(EMIRATES_ID,emirates_id);
        conValues.put(RTA,rta);

        db.insert(TABLE_OPERATOR_COMPANY_INFO, null, conValues);
    }

    public void deleteOperatorCompanyData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OPERATOR_COMPANY_INFO, null, null);
        db.execSQL("delete from " + TABLE_OPERATOR_COMPANY_INFO);
        db.close();
    }

    public boolean updateOperatorCompanyData(String ntn, String company_name, String company_date, String address, String company_logo, String tarde_license,
                                             String memorandum_articled, String passport, String valid_residance_visa, String emirates_id, String rta)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",1);
        contentValues.put(NTN, ntn);
        contentValues.put(COMPANY_NAME,company_name);
        contentValues.put(COMPANY_DATE, company_date);
        contentValues.put(ADDRESS, address);
        contentValues.put(COMPANY_LOGO, company_logo);
        contentValues.put(TRADE_LICENSE, tarde_license);
        contentValues.put(MEMORANDUM_ARTICLEID, memorandum_articled);
        contentValues.put(PASSPORT, passport);
        contentValues.put(VALID_RESIDANCE_VISA,valid_residance_visa);
        contentValues.put(EMIRATES_ID,emirates_id);
        contentValues.put(RTA,rta);

        db.update(TABLE_OPERATOR_COMPANY_INFO, contentValues, "id = ?",new String[] { "1" });
        return true;
    }

}