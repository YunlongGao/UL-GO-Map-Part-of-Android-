package me.ivanworld.test.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE_SQL ="CREATE TABLE tb_bd ( id_bd   INTEGER    PRIMARY KEY AUTOINCREMENT NOT NULL, name_bd TEXT   NOT NULL,x_bd    DOUBLE NOT NULL,y_bd    DOUBLE NOT NULL);";
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super( context, name, null, version );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( CREATE_TABLE_SQL );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
