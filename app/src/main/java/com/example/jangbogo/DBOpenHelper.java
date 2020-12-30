package com.example.jangbogo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

public class DBOpenHelper {
    private static final String DATABASE_NAME = "Datasets.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase DB;
    Database DBHelper;
    private Context mCtx;

    private  class Database extends SQLiteOpenHelper{

        public Database(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Databases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ Databases.CreateDB._TABLENAME0);
            onCreate(db);
        }

    }
    public DBOpenHelper(Context context){
        this.mCtx = context;
    }

    public DBOpenHelper open() throws SQLException{
        DBHelper = new Database(mCtx,DATABASE_NAME,null,DATABASE_VERSION);
        DB = DBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        DBHelper.onCreate(DB);
    }
    public void close(){
        DB.close();
    }


    public long insertColumn(String name,String orderdate,int numbers)
    {
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.NAME,name);
        values.put(Databases.CreateDB.ORDERDATE,orderdate);
        values.put(Databases.CreateDB.numbers,numbers);
        System.out.println("인서트 됌");
        return DB.insert(Databases.CreateDB._TABLENAME0,null,values);
    }

    public Cursor selectColumns(){
        return DB.query(Databases.CreateDB._TABLENAME0,null,null,null,null,null,null);
    }


    public void deleteRow(String name,String orderdate,int numbers)
    {
        DB.execSQL("DELETE FROM "+Databases.CreateDB._TABLENAME0 + " WHERE name =\""+name+"\" AND orderdate = \"" + orderdate + "\" AND numbers = \""+numbers+"\"");
    }


    public void updateRow(String name,String orderdate,int numbers)
    {
        DB.execSQL("UPDATE "+Databases.CreateDB._TABLENAME0 + " SET numbers = numbers + "+numbers + " WHERE name = \""+name + "\" AND orderdate = \""+orderdate + "\"");
    }


}
