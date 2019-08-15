package com.example.bkmigiyo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;

public class DBPesanan extends SQLiteOpenHelper {
        public static final String ID = "id";
        public static final String nomeja = "meja";
        public static final String pembeli = "pembeli";
        public static final String makanan = "makanan";
        public static final String ket = "keterangan";
        public static final String pedas = "tk_pedas";
        public static final String toping = "toping";
        public static final String minuman = "minuman";
        public static final String jumlahmakanan = "JmlhMakan";
        public static final String jumlahminuman = "JmlhMinum";

    private static final int DB_version = 1;
    private static final String Db_name = "BakmiGiyo";
    public static final String table_name = "pesanan";


    public static final  String Create_tpesanan = "CREATE TABLE "+ table_name +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+nomeja+" TEXT, "
            +pembeli+" TEXT, "+makanan+" TEXT, "+ket+" TEXT, "+pedas+" TEXT, "+toping+" TEXT, "
            +jumlahmakanan+" INTEGER, "+minuman+" TEXT, "+jumlahminuman+" INTEGER"+")";

    private static final String Upgardetable = "DROP TABLE IF EXISTS "+table_name;

    DBPesanan(Context context){
        super(context, Db_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_tpesanan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Upgardetable);
        onCreate(db);
    }



    public void updateJumlah(long id){

    }
}
