package com.example.bkmigiyo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.bkmigiyo.fragment3.records;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SlidingTabAdapter slidingTabAdapter;
    private SharedPreferences.Editor editor;

    //for the tab's items
    private TabLayout.Tab makanan;
    private TabLayout.Tab minuman;
    private TabLayout.Tab rincian;

    //for get current item
    private SharedPreferences sharedPreferences;
    private int lastPosition = -1;
    int x=1;
    private DBPesanan dbhelper;
    private Activity activity;
    LayoutInflater inflater;
    ArrayList<String> records;
    SharedPreferences sharedpreferences;

    public String petugasPelayan="";
    final static String tag = "MauliCreator-BakmiGiyo2019";

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionmenu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(Config.KEY_EMAIL, null);
                editor.commit();

                Intent intent = new Intent(this,LoginActivity.class);
                finish();
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new DBPesanan(this);
        records=new ArrayList<String>();
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        // to hide the devider between action bar and tabLayout
        getSupportActionBar().setElevation(0);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);


        //initialize the adapter
        slidingTabAdapter = new SlidingTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slidingTabAdapter);


        //set your tab's item
        makanan = tabLayout.newTab();
        minuman = tabLayout.newTab();
        rincian = tabLayout.newTab();


        //labeling the tab's items
        makanan.setText("Makanan");
        minuman.setText("Minuman");
        rincian.setText("Rincian");


        //set the index of the tab's items
        tabLayout.addTab(makanan, 0);
        tabLayout.addTab(minuman, 1);
        tabLayout.addTab(rincian,2);

        //set tab selector color
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));

        //set the indicator
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));

        // switch the fragment when the current fragment was slided.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // switch the fragment when the tab item was clicked
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //get current tab
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String position = sharedPreferences.getString("tab_opened", null);
        if(position==null){
            viewPager.setCurrentItem(1,true);
        }else if(position=="0"){
            viewPager.setCurrentItem(0,true);
        }else if(position=="1"){
            viewPager.setCurrentItem(1,true);
        }else {
            viewPager.setCurrentItem(2,true);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tab_opened", "1");
        editor.commit();
    }
    public void hapusBaris(View view) {
        Button bt=(Button)view;
        final String del_id1=bt.getTag().toString();

        final DBPesanan dbhelper = new DBPesanan(this);
        final CustomAdapter adapter = new CustomAdapter(getApplicationContext(), R.layout.list_item, R.id.tempatpesanan, records);
        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        //MENAMPILKAN PERINGATAN MENGHAPUS DATA
        AlertDialog.Builder alertDialogHapus = new AlertDialog.Builder(this);
        alertDialogHapus.setTitle("Hapus Data");
        alertDialogHapus.setMessage("Anda yakin ingin menghapus data ini?");
        //KALAU YAKIN HAPUS
        alertDialogHapus.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try{
                    SQLiteDatabase database = dbhelper.getWritableDatabase();
                    database.delete(DBPesanan.table_name, DBPesanan.ID + "=?", new String[]{del_id1});
                    for (int i = 0; i < records.size(); i++) {
                        if (records.get(i).startsWith(del_id1)) {
                            records.remove(i);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    Log.d("sudah terhapus","harusnya refresh");

                    editor.putString("tab_opened", "2");
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //KALAU TIDAK YAKIN JANGAN DIHAPUS
        alertDialogHapus.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogHapus.show();
    }

    public void tambahJumlah(View view) {
        Button bt=(Button)view;
        final String bt_add=bt.getTag().toString();
        String jmlh;


        final DBPesanan dbhelper = new DBPesanan(this);
        final CustomAdapter adapter = new CustomAdapter(getApplicationContext(), R.layout.list_item, R.id.tempatpesanan, records);
        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        SQLiteDatabase database = dbhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + DBPesanan.table_name +" WHERE "+ DBPesanan.ID + "=" + bt_add;
        //MEMBUAT KURSOR UNTUK MEMBUKA DATABASE
        Cursor c = database.rawQuery(sql, null);

        if (c.getCount() > 0)
            while (c.moveToNext()) {
                if (c.getString(c.getColumnIndex(DBPesanan.jumlahmakanan)) == null) {
                    jmlh = c.getString(c.getColumnIndex(DBPesanan.jumlahminuman));
                    int jumlah = Integer.parseInt(jmlh)+x;
                    database.execSQL("UPDATE " + DBPesanan.table_name + " SET " + DBPesanan.jumlahminuman + "=" + jumlah + " WHERE " + DBPesanan.ID + "=?", new String[]{bt_add});

                    editor.putString("tab_opened", "2");
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    jmlh = c.getString(c.getColumnIndex(DBPesanan.jumlahmakanan));
                    int jumlah = Integer.parseInt(jmlh)+x;
                    database.execSQL("UPDATE " + DBPesanan.table_name + " SET " + DBPesanan.jumlahmakanan + "=" + jumlah + " WHERE " + DBPesanan.ID + "=?", new String[]{bt_add});

                    editor.putString("tab_opened", "2");
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        database.close();
    }
}
