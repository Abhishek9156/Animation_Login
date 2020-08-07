package com.rannlab.smtrader;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LogIn extends AppCompatActivity {
    Button LogIn;
    TextView Forgot;
    EditText Mboil;
    EditText Password;
    RequestQueue requestQueue,requestQueue1;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    private  boolean isNetworkOk;
    PreferenceHelper preferenceHelper;
    UrlData urlData=new UrlData();
    protected Context context;
    SessionManager session;
    private final static int PERMISSION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // InternetCheck(com.example.myapplication.LogIn.this);
        session = new SessionManager(getApplicationContext());

        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        //   Toast.makeText(getApplicationContext(), ""+urlData.getS(), Toast.LENGTH_SHORT).show();
        isNetworkOk=NetworkHelper.isNetworkConnection(this);

        LogIn = findViewById(R.id.btnLogin);
        Forgot=findViewById(R.id.forgot);
        Mboil = findViewById(R.id.tvUserName);
        Password = findViewById(R.id.tvPassword);
        TakeLatiLog();

        progressDialog = new ProgressDialog(LogIn.this);
        preferenceHelper = new PreferenceHelper(this);
        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,ChangePasswordActivity.class));
                // finish();
            }
        });

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isNetworkOk=NetworkHelper.isNetworkConnection(getApplicationContext());
                String s=String.valueOf(isNetworkOk);
                if(s.equals("true")){  fetchData();}else {
                    showSnackerbar();

                }


            }      });


    }

    private void fetchData() {
        if (Mboil.length() == 0) {
            Mboil.setError("Enter Number");
        } else {


            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue1=Volley.newRequestQueue(getApplicationContext());
            String url = urlData.getS()+"/api_logs/user_login";


            progressDialog.setMessage("Please Wait");
            progressDialog.show();
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("mobile", Mboil.getText().toString().trim());
            stringStringHashMap.put("password", Password.getText().toString().trim());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                    new JSONObject(stringStringHashMap), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();

                    try {
                        String s=response.getString("msg").trim();
                        if(s.equals("Mobile or Password is wrong.") || s.equals("Your profile is not active. Please contact to your admin.")) {

                            Toast.makeText(getApplicationContext(), ""+response.getString("msg"), Toast.LENGTH_SHORT).show();
                        }else
                        {

                            try {

                                JSONObject jsonObject = response.getJSONObject("data");

                                preferenceHelper.putIsLogin(true);
                                //preferenceHelper.putFName(jsonObject.getString("first_name"));
                                //preferenceHelper.putLName(jsonObject.getString("last_name"));
                                preferenceHelper.putEmail(jsonObject.getString("email"));
                                preferenceHelper.putMobile(jsonObject.getString("mobile"));
                                preferenceHelper.putUSerId(jsonObject.getString("user_id"));
                                session.createLoginSession("Android Hive", "anroidhive@gmail.com");
                                imge(preferenceHelper.getUserId());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                startActivity(intent);
                                finish();
                                Password.setText("");
                                // finish();
                                Store();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Toast.makeText(LogIn.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
        }
    }

    private void showSnackerbar() {
        String message;
        int color;
        message = "  You are Offline..!!";
        color = Color.WHITE;

        Snackbar snackbar= Snackbar.make(findViewById(R.id.Loginla),message, Snackbar.LENGTH_LONG);
        View view=snackbar.getView();
        view.setBackgroundColor(Color.RED);

        TextView textView=view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(color);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

    private void Store() {

        SharedPreferences sharedPreferences=context.getSharedPreferences("com.rannlab.smtraders_status_login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("login_status","on");
        editor.commit();
    }


    private void TakeLatiLog() {
        if(ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(LogIn.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_ID);
        }else {
            getcurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_ID && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getcurrentLocation();
            }else {
                Toast.makeText(getApplicationContext(), "Permision denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getcurrentLocation() {
        final PreferenceHelper preferenceHelper=new PreferenceHelper(this);
        final LocationRequest locationRequest=new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(LogIn.this)
                .requestLocationUpdates(locationRequest,new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(LogIn.this)
                                .removeLocationUpdates(this);
                        if(locationResult!=null && locationResult.getLocations().size()>0){
                            int latestlocationIndex =locationResult.getLocations().size()-1;
                            double latitude=locationResult.getLocations().get(latestlocationIndex).getLatitude();
                            double logitude=locationResult.getLocations().get(latestlocationIndex).getLongitude();
                            //   Toast.makeText(LogIn.this, latitude+" =longo ="+logitude, Toast.LENGTH_SHORT).show();
                            String la=String.valueOf(latitude);
                            String lo=String.valueOf(logitude);
                            preferenceHelper.putlatitude(la);
                            preferenceHelper.putLongitute(lo);


                        }

                    }
                }, Looper.getMainLooper());
    }


    private void imge(String s){
        HashMap<String,String> stringStringHashMap=new HashMap<>();
        stringStringHashMap.put("user_id",s);
        final String url= urlData.getS()+"/api_logs/user_profile";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, new JSONObject(stringStringHashMap),
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("data");
                    String fname1=jsonObject.getString("path");
                   String imgdata=jsonObject.getString("file_name");
                    String s=urlData.getS()+"/"+fname1+"/"+imgdata;
                    preferenceHelper.putImg(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

requestQueue1.add(jsonObjectRequest);
    }



}

