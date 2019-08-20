package com.example.bkmigiyo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    ProgressDialog pDialog;
    //An ArrayList for Spinner Items
    private ArrayList<String> user;

    String karyawanlogin = "";
    String resp = "";
    String umild;
    String uname;
    final static String tag = "MauliCreator-BakmiGiyo2019";
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    //JSON Array
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pDialog = new ProgressDialog(this);

        username = findViewById(R.id.uname);
        password = findViewById(R.id.password);
        //Initializing the ArrayList
        user = new ArrayList<String>();


        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        umild = sharedpreferences.getString(Config.KEY_EMAIL, null);

//        if (session) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.putExtra(tag, karyawanlogin);
//            startActivity(intent);
//        }
    }

    private void login() {
        //Getting values from edit texts
        uname = username.getText().toString().trim();
        final String passwd = password.getText().toString().trim();
        pDialog.setMessage("Proses masuk...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(Config.LOGIN_FAIL)) {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "ID/password salah!", Toast.LENGTH_LONG).show();
                        } else {
                            hideDialog();
                            karyawanlogin = response;
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Welcome bro, " + karyawanlogin, Toast.LENGTH_LONG).show();
                            gotoCourseActivity(response);


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(LoginActivity.this, "Servernya ga ada", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_EMAIL, uname);
                params.put(Config.KEY_PASSWORD, passwd);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void gotoCourseActivity(String respon) {
        // menyimpan login ke session
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(session_status, true);
        editor.putString(Config.KEY_EMAIL, uname);
        editor.commit();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        Bundle data = new Bundle();
        data.putString(tag, karyawanlogin);
        fragment3 fragtry = new fragment3();
        fragtry.setArguments(data);

        intent.putExtras(data);
        Log.d("intent", data.toString());
        startActivity(intent);
        finish();
    }

    public void doLogin(View view) {
        login();
    }

}
