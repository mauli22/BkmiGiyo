package com.example.bkmigiyo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


public class fragment2 extends Fragment {
    private Button btnOpenCurrentFragment;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Spinner spminum, spmilkshake;

    private DBPesanan dbPesanan;
    private String setMinum,minum1,minum2,minum3;

    //An ArrayList for Spinner Items
    private ArrayList<String> minum, minumEs;

    //JSON Array
    private JSONArray result;
    final static String tag = "MauliCreator-BakmiGiyo2019";
    String pelayan="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, null);
        btnOpenCurrentFragment = (Button) view.findViewById(R.id.send2);
        spminum = view.findViewById(R.id.menuminum);
        spmilkshake = view.findViewById(R.id.menuminum2);
        dbPesanan = new DBPesanan(getContext());

        //Initializing the ArrayList
        minum = new ArrayList<String>();
        minumEs = new ArrayList<String>();

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getDataMinuman();

        btnOpenCurrentFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                saveData();
                try{
                    Toast.makeText(getContext(),"Data Disimpan",Toast.LENGTH_SHORT).show();


                    editor.putString("tab_opened", "2");
                    editor.commit();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void saveData() {
        SQLiteDatabase bikin = dbPesanan.getWritableDatabase();
        String sqli = "INSERT INTO "+DBPesanan.table_name+" ("+DBPesanan.minuman+", "+
                DBPesanan.jumlahminuman+") values ('"+setMinum+"', '1')";
        bikin.execSQL(sqli);
    }
    private void setData() {
        if (spminum.getSelectedItem().toString() != "")
            minum1 = spminum.getSelectedItem().toString();
        else
            minum1="";
        if (spmilkshake.getSelectedItem().toString() != "")
            minum2 = spmilkshake.getSelectedItem().toString();
        else
            minum2="";

        setMinum = minum1+""+minum2;
    }
    private void getminuman(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                minum.add(json.getString("namaMinuman"));
            } catch (JSONException e) {
                //e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spminum.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, minum));
    }
    private void getminmEs(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                minumEs.add(json.getString("namaEs"));
            } catch (JSONException e) {
                //e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spmilkshake.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, minumEs));
    }
    private void getDataMinuman(){
        StringRequest stringRequest = new StringRequest(Config.URLmenu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("result");
                            getminuman(result);
                            getminmEs(result);
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
