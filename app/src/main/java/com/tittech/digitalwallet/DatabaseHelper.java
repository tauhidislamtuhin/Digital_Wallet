package com.tittech.digitalwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "digital_wallet", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE expense (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT, time TEXT)");
        db.execSQL("CREATE TABLE income (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT, time TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS expense");
    db.execSQL("DROP TABLE IF EXISTS income");
    }

    //===============================================addExpense
    public boolean addExpense(double amount,String reason){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",timeDateFormat());
        db.insert("expense",null,conval);
        return true;

    }
    //===============================================addExpense
    public boolean addIncome(double amount,String reason){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",timeDateFormat());
        db.insert("income",null,conval);
        return true;

    }
    //==============================================calculateTotalExpense
    public double calculateTotalExpense(){
        double totalExpense =0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM expense",null);
        if (cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                double amount = cursor.getDouble(1);
                totalExpense = totalExpense+amount;
            }

        }
        return totalExpense;
    }
    //==============================================calculateTotalExpense
    public double calculateTotalIncome(){
        double totalIncome =0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM income",null);
        if (cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                double amount = cursor.getDouble(1);
                totalIncome = totalIncome+amount;
            }

        }
        return totalIncome;
    }
    //==============================================showAllExpense
    public  Cursor showAllExpense(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM expense",null);
        return cursor;
    }
    //==============================================showAllIncome
    public  Cursor showAllIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM income",null);
        return cursor;
    }


    //==============================================deleteByIdExpense
    public void deleteByIdExpense(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM expense WHERE id LIKE "+id);
    }
    //==============================================deleteByIdIncome
    public void deleteByIdIncome(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM income WHERE id LIKE "+id);
    }
    //==============================================updateExpense
    public boolean updateExpense(String id,double amount,String reason){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("id",id);
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",timeDateFormat());
        db.update("expense",conval,"id = ?",new String[]{ id });

        return true;

    }

    //==============================================updateIncome
    public boolean updateIncome(String id,double amount,String reason){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("id",id);
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",timeDateFormat());
        db.update("income",conval,"id = ?",new String[]{ id });

        return true;

    }

    //==============================================timeDateFormat
    public String timeDateFormat(){
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedTime = sdf.format(new Date(currentTimeMillis));

        String dateTimeArray[]=formattedTime.split(" ");

        String date = dateTimeArray[0];
        String time = dateTimeArray[1];
        String amPm ="";

        String timeArray[]=time.split(":");
        String hour24 = timeArray[0];
        String minutes = timeArray[1];
        int hour12 = Integer.parseInt(hour24);

        if (hour12>12){
            hour12 = hour12-12;
            amPm = "PM";
        }else {
            amPm = "AM";
        }
        return "Date : "+date+" Time : " +hour12+":"+minutes+amPm;

    }

}
