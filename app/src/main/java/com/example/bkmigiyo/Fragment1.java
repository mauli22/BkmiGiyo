package com.example.bkmigiyo;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends Fragment {
    private Button btnOpenCurrentFragment;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Spinner spmakan, sppedas, sptoping;
    private CheckBox sayur, micin, ayam, garam;

    private String setMakan,setToping,setPedas,setKet;
    private String toping="";
    private String keterangan="";
    private DBPesanan dbpesanan;


    //An ArrayList for Spinner Items
    private ArrayList<String> menu,mtoping;

    //JSON Array
    private JSONArray result;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment1, null);
        return inflater.inflate(R.layout.fragment_fragment1, container, false);

    }

    public void saveData() {
        SQLiteDatabase create = dbpesanan.getWritableDatabase();
        /*ContentValues values = new ContentValues();
        values.put(DBPesanan.makanan, setMakan);
        values.put(DBPesanan.toping, setToping);
        values.put(DBPesanan.pedas, setPedas);*/
        String sqli = "INSERT INTO "+DBPesanan.table_name+" ("+DBPesanan.makanan+", "+
                DBPesanan.toping+" ,"+DBPesanan.pedas+", "+DBPesanan.ket+", "+DBPesanan.jumlahmakanan+") values "+
                "('"+setMakan+"','"+setToping+"','"+setPedas+"','"+setKet+"', '1')";
        create.execSQL(sqli);
    }

    private void setData() {

        setMakan = spmakan.getSelectedItem().toString();

        setToping = sptoping.getSelectedItem().toString();

        setPedas = sppedas.getSelectedItem().toString();

        if (sayur.isChecked()) {
            keterangan+= sayur.getText().toString()+", ";
        }else
            keterangan+="";
        if (micin.isChecked()) {
            keterangan+= micin.getText().toString()+", ";
        }else
            keterangan+="";
        if (ayam.isChecked()) {
            keterangan+= ayam.getText().toString()+", ";
        }else
            keterangan+="";
        if (garam.isChecked()) {
            keterangan+= garam.getText().toString();
        }else
            keterangan+="";
        setKet = keterangan;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnOpenCurrentFragment = view.findViewById(R.id.send1);
        spmakan = view.findViewById(R.id.menumakan);
        sppedas = view.findViewById(R.id.tpedas);
        sptoping = view.findViewById(R.id.sp_toping);

        sayur = view.findViewById(R.id.sayur);
        micin = view.findViewById(R.id.micin);
        ayam = view.findViewById(R.id.ayam);
        garam = view.findViewById(R.id.garam);

        //Initializing the ArrayList
        menu = new ArrayList<String>();
        mtoping = new ArrayList<String>();

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        dbpesanan = new DBPesanan(getContext());

        btnOpenCurrentFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                saveData();
                try{
                    Toast.makeText(getContext(),"Data Disimpan",Toast.LENGTH_SHORT).show();

                    editor.putString("tab_opened", "2");
                    editor.commit();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();

                }catch (Exception e){
                   // e.printStackTrace();
                }
            }

        });

        getDataMenu();

    }

    private void getmenu(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                menu.add(json.getString("namaMenu"));
            } catch (JSONException e) {
               // e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spmakan.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, menu));
    }
    private void gettoping(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                mtoping.add(json.getString("namaToping"));
            } catch (JSONException e) {
               // e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        sptoping.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mtoping));
    }
    private void getDataMenu(){
        StringRequest stringRequest = new StringRequest(Config.URLmenu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("result");
                            getmenu(result);
                            gettoping(result);
                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
