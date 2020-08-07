package com.rannlab.smtrader.ui.slideshow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.rannlab.smtrader.NetworkHelper;
import com.rannlab.smtrader.PName;
import com.rannlab.smtrader.PreferenceHelper;
import com.rannlab.smtrader.R;
import com.rannlab.smtrader.UrlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView recyclerView;
    RecyclerProductAdaptor recyclerAdaptor;
    ArrayList<PruductData> dataArrayList;
    ArrayList<PName> arrayList;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    SearchView searchView;
    View view;
    UrlData urlData=new UrlData();
    private boolean isNetworkOk;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = root.findViewById(R.id.ProductRecycker);

        searchView=root.findViewById(R.id.Searcg_BAr);
        progressDialog = new ProgressDialog(getContext());
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        dataArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        arrayList=new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(recyclerAdaptor==null){

                }else{
                    String Text=newText;
                    recyclerAdaptor.filter(Text);
                }

                return false;
            }
        });

        isNetworkOk=NetworkHelper.isNetworkConnection(getContext());
        String s=String.valueOf(isNetworkOk);
        if(s.equals("true")){     FetchData();}else {
            // Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            showSnackerbar();

        }





        //final TextView textView = root.findViewById(R.id.text_slideshow);
        return root;
    }
    private void showSnackerbar() {
        String message;
        int color;
        message = "  You are Offline..!!";
        color = Color.WHITE;
        view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        view=snackbar.getView();
        view.setBackgroundColor(Color.RED);
        TextView textView=view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(color);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }
    private void FetchData() {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        //SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MYKey", Context.MODE_PRIVATE);
        //String s=sharedPreferences.getString("Userid","");
        //Toast.makeText(getContext(), "=="+s, Toast.LENGTH_SHORT).show();
        final PreferenceHelper preferenceHelper=new PreferenceHelper(getContext());
        HashMap<String,String> stringStringHashMap=new HashMap<>();
        stringStringHashMap.put("user_id",preferenceHelper.getUserId());

        String url= urlData.getS()+"/api_logs/product_listing";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(stringStringHashMap) ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //  Toast.makeText(getContext(), "Response = "+response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {

                            String s=response.getString("msg").trim();
                            String s1="No products available!";
                            if(s.equals(s1)) {
                                final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                builder.setMessage("No products available");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog=builder.create();
                                alertDialog.show();
                            }else {


                                JSONArray jsonArray = response.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject hit = jsonArray.getJSONObject(i);
                                    //  SharedPreferences sharedPreferences=getContext().getSharedPreferences("Pdata",Context.MODE_PRIVATE);
                                    //SharedPreferences.Editor editor=sharedPreferences.edit();
                                    String ProductName = hit.getString("product_name");
                                    String SaleP = hit.getString("sale_price");
                                    String VName = hit.getString("vendor_name");

                                    String rupee = hit.getString("profit_val");

                                    String ProfitP = hit.getString("profit_per");
                                    preferenceHelper.putProduct_Stock(hit.getString("stock_value"));

                                    dataArrayList.add(new PruductData(ProductName, ProfitP, VName, SaleP,rupee));
                                    // arrayList.add(new PName(ProductName));
                                    // Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                                }

                                recyclerAdaptor = new RecyclerProductAdaptor(getActivity(), dataArrayList);
                                recyclerView.setAdapter(recyclerAdaptor);
                                //recyclerAdaptor.setOnItemClickListener(SlideshowFragment.this);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                // Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
//    @Override
//    public void onItemClick(int position) {
//        Intent detailIntent = new Intent(getContext(), ProductdataDisplay.class);
//        PreferenceHelper preferenceHelper=new PreferenceHelper(getContext());
//        PruductData clickedItem = dataArrayList.get(position);
//        preferenceHelper.putProductName(clickedItem.getProductName());
//        preferenceHelper.putEndDate(clickedItem.getEDate());
//        startActivity(detailIntent);
//
//
//
//
//    }

}


