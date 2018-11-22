package com.example.raphaeld.cashcash;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raphaeld on 03/09/2018.
 */

public class DataBaseManager extends SQLiteOpenHelper {
    private static DataBaseManager mInstance = null;
    private static final String DATABASE_NAME = "cashcash.db";
    private static final int DATABASE_VERSION = 1;
    private Context cont;
    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont=context;
    }

    public static synchronized  DataBaseManager getInstance(Context ctx) {
        // singelton
        if (mInstance == null) {
            mInstance = new DataBaseManager(ctx.getApplicationContext());
        }
        return mInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String strSQL = "create table NotesToGiveBack ("
                + "idNote integer primary key autoincrement,"
                + "name text non null,"
                + "date text not null,"
                + "amount text not null,"
                + "devise text not null,"
                + "note text "
                + ")";
        String strSQL1 = "create table NotesToGetBack ("
                + "idNote integer primary key autoincrement,"
                + "name text non null,"
                + "date text not null,"
                + "amount text not null,"
                + "devise text not null,"
                + "note text "
                + ")";

        db.execSQL(strSQL);
        db.execSQL(strSQL1);
        Log.i("DataBaseManager", "onCreate invoked");
    }
    public void update (ElementList elementListOld,ElementList elementListNew,String table){
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("update " +
                table+" Set " +
                "name = '" +elementListNew.getNom()+"' , " +
                "date = '"+elementListNew.getMydate()+"' , " +
                "amount = '"+elementListNew.getPrix()+"' , " +
                "devise = '"+elementListNew.getDevise()+"' ," +
                "note = '"+elementListNew.getNote()+"' "+
                "where name = '"+elementListOld.getNom()+"' and " +
                "date = '"+elementListOld.getMydate()+"' and " +
                "amount =  '"+elementListOld.getPrix()+"' and "+
                "devise = '"+elementListOld.getDevise()+"' and " +
                "note = '"+elementListOld.getNote()+"'");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }



    public void addNoteToGiveBack(String name, String date, String amount, String devise, String note) {

        name = name.replace("'", "''");
        date = date.replace("'", "''");
        amount = amount.replace("'", "''");
        devise = devise.replace("'", "''");
        note = note.replace("'", "''");
        String strSql = "insert into NotesToGiveBack (name, date, amount, devise, note) values('"
                + name + "','" + date + "','" + amount + "','" + devise + "','" + note + "')";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void addNoteToGetBack(String name, String date, String amount, String devise, String note) {

        name = name.replace("'", "''");
        date = date.replace("'", "''");
        amount = amount.replace("'", "''");
        devise = devise.replace("'", "''");
        note = note.replace("'", "''");
        String strSql = "insert into NotesToGetBack (name, date, amount, devise, note) values('"
                + name + "','" + date + "','" + amount + "','" + devise + "','" + note + "')";
        this.getWritableDatabase().execSQL(strSql);
    }

    public ArrayList<ElementList> readAllToMe() {
        ArrayList<ElementList> list = new ArrayList<>();
        String srtsql = "select * from NotesToGetBack ";
        Cursor cursor = this.getReadableDatabase().rawQuery(srtsql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ElementList elementList = new ElementList(cont,cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5));
            list.add(elementList);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public ArrayList<ElementList> readAllFromMe() {
        ArrayList<ElementList> list = new ArrayList<>();
        String srtsql = "select * from NotesToGiveBack ";
        Cursor cursor = this.getReadableDatabase().rawQuery(srtsql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ElementList elementList = new ElementList(cont,cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5));
            list.add(elementList);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public  void deleteNoteFrom (ElementList el){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM NotesToGiveBack WHERE "
                + "name = '"+el.getNom()
                + "' and date = '"+el.getMydate()
                + "' and amount = '"+el.getPrix()
                + "' and devise = '"+el.getDevise()
                + "';");

    }
    public  void deleteNoteTo (ElementList el){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM NotesToGetBack WHERE "
                + "name = '"+el.getNom()
                + "' and date = '"+el.getMydate()
                + "' and amount = '"+el.getPrix()
                + "' and devise = '"+el.getDevise()
                + "';");
    }
}

