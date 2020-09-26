package com.rannlab.smtrader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    EditText pass,conpass;
    ProgressDialog progressDialog;
    private boolean isNetworkOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        pass=findViewById(R.id.tvPassword1);
        progressDialog=new ProgressDialog(getApplicationContext());
        conpass=findViewById(R.id.tvconPassword1);
        findViewById(R.id.btnLogin1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isNetworkOk = NetworkHelper.isNetworkConnection(getApplicationContext());
                String ss = String.valueOf(isNetworkOk);
                if (ss.equals("true")) {
                    fetchData();
                } else {
                    showSnackerbar();

                }

            }
        });
    }

    private void fetchData() {
       // progressDialog.setMessage("Please Wait");
        //progressDialog.show();

        String pass1=pass.getText().toString();
        String match=conpass.getText().toString();

        if(pass1.equals("")){
            pass.setError("Enter password");
        }else if(match.equals("")){
            conpass.setError("Enter password");

        }else {

            UrlData urlData = new UrlData();
            PreferenceHelper preferenceHelper = new PreferenceHelper(getApplicationContext());
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", preferenceHelper.getforgotuid());
            map.put("newpass", pass1);
            map.put("repeatpass", match);
            String url = urlData.getS() + "/api_logs/change_user_password";
            JsonObjectRequest jsonObjectRequestn = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(map), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
          //             progressDialog.dismiss();
                    try {
                        String s1 = response.getString("msg");
                        if (s1.equals("Updation successfull")) {
                            startActivity(new Intent(ForgotPassword.this, LogIn.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "" + response.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            });
            jsonObjectRequestn.setRetryPolicy(new DefaultRetryPolicy(500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonObjectRequestn);
        }

    }

    private void showSnackerbar() {

        String message;
        int color;

        message = "  You are Offline..!!";
        color = Color.WHITE;


        Snackbar snackbar = Snackbar.make(findViewById(R.id.fgp), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(color);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }
}
