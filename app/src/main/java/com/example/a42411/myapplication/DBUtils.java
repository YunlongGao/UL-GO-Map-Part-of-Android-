package com.example.a42411.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.print.PrinterId;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBUtils {
    private SQLiteDatabase sqLiteDatabase;
    private DBOpenHelper dbOpenHelper;
    private Cursor cursor;
    int count = 0;
    int randomNumber=0;
    public DBUtils(Context context,String databaseName,int version,String tableName,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy) {
        dbOpenHelper = new DBOpenHelper(context,databaseName,null,version);
        sqLiteDatabase=dbOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query(tableName,columns,selection,selectionArgs,groupBy,having,orderBy);
    }
    public int getCount(){
        count = cursor.getCount();
        //closeCursor(cursor);
        return count;
    }
    public int randomNumber(int count){
        Random random = new Random();
        randomNumber = random.nextInt(count);
        return randomNumber;
    }
    public List<String> getBuildingName( ){
        List<String> buildingName = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do {
                buildingName.add(cursor.getString(cursor.getColumnIndex("name_bd")));
            }
            while (cursor.moveToNext());
        }
        return buildingName;
    }
    public List<String> getBuildingId( ){
        List<String> buildingId = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do {
                buildingId.add(cursor.getString(cursor.getColumnIndex("id_bd")));
            }
            while (cursor.moveToNext());
        }
        return buildingId;
    }
    public List<String> getBuildingLatitude(){
        List<String> buildingLatitude = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do {
                buildingLatitude.add(cursor.getString(cursor.getColumnIndex("x_bd")));
            }
            while (cursor.moveToNext());
        }
        return buildingLatitude;
    }
    public List<String> getBuildingLongitude(){
        List<String> buildingLongitude = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do {
                buildingLongitude.add(cursor.getString(cursor.getColumnIndex("y_bd")));
            }
            while (cursor.moveToNext());
        }
        return buildingLongitude;
    }
    public List<String> getQuizContent( ){
        List<String> quizContent = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do {
                quizContent.add(cursor.getString(cursor.getColumnIndex("content_que")));
            }
            while (cursor.moveToNext());
        }
        return quizContent;
    }
    public List<String> getQuizAnswer( ){
        List<String> getQuizAnswer = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do {
                getQuizAnswer.add(cursor.getString(cursor.getColumnIndex("ans_que")));
            }
            while (cursor.moveToNext());
        }
        return getQuizAnswer;
    }
    public void closeCursor(Cursor cursor){
        cursor.close();
    }
}
