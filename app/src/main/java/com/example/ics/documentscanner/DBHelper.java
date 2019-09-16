package com.example.ics.documentscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.EnumMap;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class DBHelper extends SQLiteOpenHelper  {
   SQLiteDatabase db;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
        database.close();
//          if(database.toString() != null){
//return true;
//          }
//          return false;
    }
    public Boolean queryData_forcus(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
          if(database.toString() != null){
return true;
          }
          return false;
    }
    public void insertDatatoAccount_user( String CUSTOMER_CODE){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ACCOUNT_USER_CODE VALUES (NULL,?)";

        ContentValues values = new ContentValues();
        values.clear();
        values.put("CUSTOMER_CODE", CUSTOMER_CODE);
        long newRowId;
        newRowId = database.insert(
                "ACCOUNT_USER_CODE",
                null,
                values);
        database.close();
        //  database.close();
//        System.out.println("statement execute insert is "+statement.executeInsert());
//        statement.executeInsert();

    }
    public void insertDatatoAccount( String NAME , String EMAIL , String MOBILE ,String LOCATION , String LANDMARK ,  String PAN , String GST,String CUSTOMER_ID , String IMAGE_ADDRESS,String CUSTOMER_CODE){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ACCOUNT VALUES (NULL, ?, ?, ?,?,?, ?, ?,?,?,?)";

        ContentValues values = new ContentValues();
        values.clear();
        //  statement.bindDouble(1, Id);
        values.put("Name",NAME);
        values.put("Email", EMAIL);
        values.put("Mobile", MOBILE);
        values.put("Location", LOCATION);
        values.put("Landmark", LANDMARK);
        values.put("PAN", PAN);
        values.put("GST", GST);
        values.put("CUSTOMER_ID", CUSTOMER_ID);
//        statement.bindString(9, Customer_code);
        values.put("IMAGE_ADDRESS", IMAGE_ADDRESS);
        values.put("CUSTOMER_CODE", CUSTOMER_CODE);
        long newRowId;
        newRowId = database.insert(
                "ACCOUNT",
                null,
                values);
        database.close();
      //  database.close();
//        System.out.println("statement execute insert is "+statement.executeInsert());
//        statement.executeInsert();

    }

    public void insertDatatoAccountfor_image( String Name,String Image_address ,String Customer_code ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ACCOUNT_IMAGE VALUES (NULL, ?, ?, ?)";
        ContentValues values = new ContentValues();
        values.clear();

//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();

        values.put("NAME", Name);
        values.put("IMAGE_ADDRESS", Image_address);
//        statement.bindString(9, Customer_code);
       values.put("CUSTOMER_CODE", Customer_code);
        long newRowId;
        newRowId = database.insert(
                "ACCOUNT_IMAGE",
                null,
                values);
        database.close();
//        statement.executeInsert();
    }

    public void insertDatatoAccountfor_image_for_acc( String Name,String Image_address ,String Customer_code ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ACCOUNT_IMAGE VALUES (NULL, ?, ?, ?)";
        ContentValues values2 = new ContentValues();
        values2.clear();

//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();

        values2.put("NAME", Name);
        values2.put("IMAGE_ADDRESS", Image_address);
//        statement.bindString(9, Customer_code);
        values2.put("CUSTOMER_CODE", Customer_code);
        long newRowId;
        newRowId = database.insert(
                "ACCOUNT_IMAGE",
                null,
                values2);
        database.close();
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();
//
//        statement.bindString(1, Name);
//        statement.bindString(9, Image_address);
////        statement.bindString(9, Customer_code);
//        statement.bindString(3, Customer_code);
//
//        statement.executeInsert();
    }
    public void insertDatatoIncome(int id, String Name, String Email , int Mobile,String Location , String Landmark ,  String PAN , String GST,String Customer_id , String Image_address){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Accounts VALUES (NULL, ?, ?, ?,?,?, ?, ?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, Name);
        statement.bindString(2, Email);
        statement.bindDouble(3, Mobile);
        statement.bindString(4, Location);
        statement.bindString(5, Landmark);
        statement.bindString(6, PAN);

        statement.executeInsert();
    }
//
//    public void insertDatatoAccount(String name, String price, byte[] image){
//        SQLiteDatabase database = getWritableDatabase();
//        String sql = "INSERT INTO Accounts VALUES (NULL, ?, ?, ?,?,?, ?, ?,?)";
//
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();
//
//        statement.bindString(1, name);
//        statement.bindString(2, price);
//        statement.bindBlob(3, image);
//
//        statement.executeInsert();
//    }
public void updateData(String Id, String NAME, String EMAIL, String MOBILE, String LOCATION, String LANDMARK, String PAN, String GST, String CUSTOMER_ID, String IMAGE_ADDRESS, String CUSTOMER_CODE) {
    SQLiteDatabase database = getWritableDatabase();


    SQLiteDatabase db = getWritableDatabase();
    db.isOpen();
    ContentValues contentValues = new ContentValues();
//    contentValues.put("Id", Id);
//    contentValues.put("NAME", NAME);
//    contentValues.put("EMAIL", EMAIL);
//    contentValues.put("MOBILE", MOBILE);
//    contentValues.put("LOCATION", LOCATION);
//    contentValues.put("LANDMARK", LANDMARK);
//    contentValues.put("PAN", PAN);
//    contentValues.put("GST", GST);
//    contentValues.put("CUSTOMER_ID", CUSTOMER_ID);
//    contentValues.put("CUSTOMER_CODE", CUSTOMER_CODE);
//    contentValues.put("IMAGE_ADDRESS", IMAGE_ADDRESS);
//
//    String selection = "PAN" + " = ?";
//       // db.execSQL("UPDATE ACCOUNT SET IMAGE_ADDRESS="+IMAGE_ADDRESS +" WHERE PAN IS "+PAN);
//      //  db.update("ACCOUNT", contentValues, selection, new String[]{PAN});
//        Log.e("pan is",""+PAN);
//
//        //  db.rawQuery("UPDATE ACCOUNT SET IMAGE_ADDRESS ="+IMAGE_ADDRESS+" WHERE CUSTOMER_CODE = "+CUSTOMER_CODE,new String[]{Id,NAME,EMAIL, MOBILE , LOCATION,LANDMARK,PAN,GST,CUSTOMER_ID,CUSTOMER_CODE});
//        Log.e("Database " , "at update is "+ db.update("ACCOUNT", contentValues, "Id = ? AND NAME = ? AND EMAIL = ? AND MOBILE = ? AND LOCATION = ? AND LANDMARK = ? AND PAN = ? AND  GST = ? AND  CUSTOMER_ID = ?    AND CUSTOMER_CODE = ? " , new String[]{Id,NAME,EMAIL, MOBILE , LOCATION,LANDMARK,PAN,GST,CUSTOMER_ID,CUSTOMER_CODE}));
//    //    Log.e("Database " , "at update QUERY "+ db.update("ACCOUNT", contentValues, selection, new String[]{PAN}));
//        Log.e("Database " , "at update is "+db.getPath());


 //   db.close();
    //    contentValues.put("Id", Id);
//    contentValues.put("NAME", NAME);
//    contentValues.put("EMAIL", EMAIL);
//    contentValues.put("MOBILE", MOBILE);
//    contentValues.put("LOCATION", LOCATION);
//    contentValues.put("LANDMARK", LANDMARK);
//    contentValues.put("PAN", PAN);
//    contentValues.put("GST", GST);
//    contentValues.put("CUSTOMER_ID", CUSTOMER_ID);
//    contentValues.put("CUSTOMER_CODE", CUSTOMER_CODE);
///////////////////////////////////////////////////    try 2
    contentValues.put("IMAGE_ADDRESS", IMAGE_ADDRESS);
    String selection = "PAN" + " LIKE ?";
//    String[] selectionArgs = { NAME , EMAIL ,MOBILE,LOCATION,LANDMARK,PAN,GST,CUSTOMER_ID,CUSTOMER_CODE };
    String[] selectionArgs = { PAN };
    long count = db.update(
            "ACCOUNT",
            contentValues,
            selection,
            selectionArgs);
    Log.e("pan was",""+PAN);
    db.close();
}
    public void updateData(String name, String price, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE FOOD SET name = ?, price = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM ACCOUNT";
        database.execSQL(sql);

//        String sql = "DELETE FROM ACCOUNT WHERE id = ?";
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindDouble(1, (double)id);
//
//        statement.execute();
        database.close();
    }
    public  void deleteData_for_image(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM ACCOUNT_IMAGE WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }
    public  void deleteData_for_image() {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM ACCOUNT_IMAGE";
        database.execSQL(sql);
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindString(2,Costomer);
//
//        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }

    public Cursor getData_for_cus(String sql, String cust_code_1){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, new String[] {cust_code_1});

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
