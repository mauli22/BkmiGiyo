package com.example.bkmigiyo;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class fragment3 extends Fragment {
    private Button proses, hapus, add;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Spinner spmakan, spminum, spmeja, sppedas;
    private CheckBox uritan, ati, kepala, ceker, telor, daging;
    private TextView rincian;
    private ProgressDialog progress;
    EditText pelanggan1;
    Spinner meja1;
    private static final String ServerURL = "http://192.168.188.2/cafe-api/addPesanan.php";
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String menuHolder, topingHolder, pedasHolder, keteranganHolder, pelangganHolder, mejaHolder;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private SQLiteDatabase database;
    ListView lv;
    DBPesanan dbhelper;
    public static ArrayList<String> records;
    CustomAdapter adapter;
    MainActivity a;
    int x = 1;
    int b = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment3, null);

        return rootView;
        //return inflater.inflate(R.layout.fragment_fragment3, container, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.listview);
        records = new ArrayList<String>();
        adapter = new CustomAdapter(getContext(), R.layout.list_item, R.id.tempatid, records);
        lv.setAdapter(adapter);
        dbhelper = new DBPesanan(getContext());
        final SQLiteDatabase database = dbhelper.getWritableDatabase();


        Log.d("fragment3", "LISTVIEW BERJALAN");
        AndroidNetworking.initialize(getContext()); //inisialisasi library FAN


        proses = (Button) view.findViewById(R.id.proses);
        hapus = view.findViewById(R.id.delete);
        add = view.findViewById(R.id.tambah);
        sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pelanggan1 = view.findViewById(R.id.pelanggan);
        meja1 = view.findViewById(R.id.nomormeja);


        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());

        readSampleData();

        String sql = "SELECT * FROM " + DBPesanan.table_name;
        final int recordCount = database.rawQuery(sql, null).getCount();
        database.close();
        final TextView textViewRecordCount = (TextView) view.findViewById(R.id.txt_data_pesanan);
        textViewRecordCount.setText("DAFTAR PESANAN (" + recordCount + ")");

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menu="";
                String toping="";
                String pedas="";
                String keterangan="";
                String jumlH="";

                final String pelanggan = pelanggan1.getText().toString().trim();
                final String meja = meja1.getSelectedItem().toString();
                if (TextUtils.isEmpty(pelanggan)) {
                    Toast.makeText(getContext(), "Pelanggan is Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(meja)) {
                    Toast.makeText(getContext(), "Meja is Required", Toast.LENGTH_SHORT).show();
                } else {

                    SQLiteDatabase database = dbhelper.getReadableDatabase();
                    String sql = "SELECT * FROM " + DBPesanan.table_name;
                    //MEMBUAT KURSOR UNTUK MEMBUKA DATABASE
                    Cursor c = database.rawQuery(sql, null);

                    if (c.getCount() > 0)
                        while (c.moveToNext()) {
                            //
                            if (c.getString(c.getColumnIndex(DBPesanan.makanan))==null) {
                                menu = c.getString(c.getColumnIndex(DBPesanan.minuman));
                            }else {
                                menu = c.getString(c.getColumnIndex(DBPesanan.makanan));
                            }

                            if (c.getString(c.getColumnIndex(DBPesanan.toping))==null) {
                                toping = "-";
                            }else {
                                toping = c.getString(c.getColumnIndex(DBPesanan.toping));
                            }

                            if (c.getString(c.getColumnIndex(DBPesanan.pedas))==null) {
                                pedas = "-";
                            }else {
                                pedas = c.getString(c.getColumnIndex(DBPesanan.pedas));
                            }

                            if (c.getString(c.getColumnIndex(DBPesanan.ket))==null) {
                                keterangan = "-";
                            }else {
                                keterangan = c.getString(c.getColumnIndex(DBPesanan.ket));
                            }

                            if (c.getString(c.getColumnIndex(DBPesanan.jumlahmakanan))==null) {
                                jumlH = c.getString(c.getColumnIndex(DBPesanan.jumlahminuman));
                            }else {
                                jumlH = c.getString(c.getColumnIndex(DBPesanan.jumlahmakanan));
                            }

                            insertPesanan(menu,toping,pedas,keterangan,jumlH);
                            //Log.d("seharusnyaaa",menu+" - "+toping+" - "+pedas+" - "+keterangan+" - "+jumlH);
                        }

                    inserPelanggan(meja,pelanggan);

                    //SQLiteDatabase database = dbhelper.getReadableDatabase();
                    database.execSQL("DELETE FROM " + DBPesanan.table_name);
                    database.execSQL("delete from sqlite_sequence where name='" + DBPesanan.table_name + "'");
                    database.close();


                    editor.putString("tab_opened", "2");
                    editor.commit();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();


                }

            }

        });
    }

            private void readSampleData() {
                SQLiteDatabase database = dbhelper.getReadableDatabase();
                String sql = "SELECT * FROM " + DBPesanan.table_name;
                //MEMBUAT KURSOR UNTUK MEMBUKA DATABASE
                Cursor c = database.rawQuery(sql, null);
                String makanan, toping, toping1, toping2, toping3, toping4, toping5, pedas, keterangan1, keterangan2, keterangan3, keterangan4, jumlahmakanan;
                String minuman, jumlahminuman;
                int id;
                if (c.getCount() > 0)
                    while (c.moveToNext()) {
                        id = c.getInt(c.getColumnIndex(DBPesanan.ID));
                        makanan = c.getString(c.getColumnIndex(DBPesanan.makanan));
                        if (makanan == null)
                            makanan = "";
                        toping = c.getString(c.getColumnIndex(DBPesanan.toping));
                        if (toping == null)
                            toping = "";
                        pedas = c.getString(c.getColumnIndex(DBPesanan.pedas));
                        if (pedas == null)
                            pedas = "";
                        keterangan1 = c.getString(c.getColumnIndex(DBPesanan.ket));
                        if (keterangan1 == null)
                            keterangan1 = "";
                        jumlahmakanan = c.getString(c.getColumnIndex(DBPesanan.jumlahmakanan));
                        if (jumlahmakanan == null)
                            jumlahmakanan = "";
                        minuman = c.getString(c.getColumnIndex(DBPesanan.minuman));
                        if (minuman == null)
                            minuman = "";
                        jumlahminuman = c.getString(c.getColumnIndex(DBPesanan.jumlahminuman));
                        if (jumlahminuman == null)
                            jumlahminuman = "";
                /*String item=id+","+makanan+","+toping+","+toping1+","+toping2+","+toping3+","+toping4+","+toping5+","+
                        pedas+","+keterangan1+","+keterangan2+","+keterangan3+","+keterangan4+","+jumlahmakanan+","+minuman+","+jumlahminuman+","+null;*/
                        String item = id + "-" + makanan + "-" + keterangan1 + "-" + pedas + "-" + toping + "-" + jumlahmakanan + "-" + minuman + "-" + jumlahminuman + "-" + null;
                        records.add(item);
                    }

                //notify listview of dataset changed
                adapter.notifyDataSetChanged();
            }

            public void inserPelanggan(final String nomeja, final String pelanggan) {
                //final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"Menambahkan...","Tunggu...",false,false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_ADDPLGN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(getActivity(),"Server : " + response, Toast.LENGTH_LONG).show();
                                Log.d("berhasil",response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //loading.dismiss();
                                //Toast.makeText(getActivity(),"Server :" + error.toString(), Toast.LENGTH_LONG).show();
                                Log.d("errorrrrrrrrr",error.toString());
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(Config.KEY_MEJA,nomeja);
                        params.put(Config.KEY_PELANGGAN,pelanggan);
                        //Toast.makeText(getContext(),nomeja+" - "+pelanggan,Toast.LENGTH_LONG).show();
                        Log.d("seharusnyaaa",nomeja+" - "+pelanggan);

                        return params;
                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
    public void insertPesanan(final String menu1, final String toping1, final String pedas1, final String keterangan1, final String jmlh) {
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Pesanan Diproses...","Tunggu...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_ADDpssn,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        //Toast.makeText(getActivity(),"Server : " + response, Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"Pesanan Berhasil Diinput", Toast.LENGTH_LONG).show();
                        Log.d("berhasil",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getActivity(),"Koneksi Error ! ", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getActivity(),"Server :" + error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("errorrrrrrrrr",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(Config.KEY_MENU,menu1);
                params.put(Config.KEY_TOPING,toping1);
                params.put(Config.KEY_PEDAS,pedas1);
                params.put(Config.KEY_KETERANGAN,keterangan1);
                params.put(Config.KEY_JMLH,jmlh);
                //Toast.makeText(getContext(),nomeja+" - "+pelanggan,Toast.LENGTH_LONG).show();
                Log.d("seharusnyaaa",menu1+" - "+toping1+" - "+pedas1+" - "+keterangan1+" - "+jmlh);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}