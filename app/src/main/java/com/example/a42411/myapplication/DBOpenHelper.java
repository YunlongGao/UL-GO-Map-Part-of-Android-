package com.example.a42411.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBOpenHelper extends SQLiteOpenHelper {
    private Context context;
    final String TAG = "Questions";
    final String CREATE_TABLE_SQL="CREATE TABLE tb_que (id_que INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,content_que TEXT  NOT NULL,id_bd INTEGER NOT NULL);";
    private SQLiteDatabase sqLiteDatabase;

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context= context;
        Log.i(TAG,"DataBase Created!");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j) {
        Log.i(TAG," Version Update from "+i+" --> "+j);
    }
}
