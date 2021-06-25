package com.jaypal.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaypal.ecommerce.R;
import com.jaypal.ecommerce.adapter.products_adapter;
import com.jaypal.ecommerce.adapter.shops_adapter;
import com.jaypal.ecommerce.model.product_model;
import com.jaypal.ecommerce.model.shop_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shop_details extends AppCompatActivity {
    private static final  String TAG ="shop_details" ;
    RecyclerView recyclerView;
    Spinner spinner;
    int shop_id;
    List<String> categories = new ArrayList<String>();
    List<product_model>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        recyclerView=findViewById(R.id.hospital_details_recycler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        shop_id=getIntent().getIntExtra("shop_id",0);
     //   Log.d(TAG, "onCreate: "+getIntent().getIntExtra("shop_id",0));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true));
           list=new ArrayList<>();
       // recyclerView.setAdapter(t);
     //   fetch_products();
//         spinner = (Spinner) findViewById(R.id.spinner);
//       spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

      //  fetchsubcategory();
        fetch_products();
        Log.d(TAG, "onCreate: "+"inside shop_details");
    }

    private void fetchsubcategory() {
        String url= "http://192.168.43.89/Ecommerce/get_subcategory.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                   // Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            if(object.getInt("shop_id")==shop_id)
                                categories.add(String.valueOf(object.get("subcategory_name")));


                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,categories);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spinner.setAdapter(dataAdapter);
                     //   shops_adapter adapter=new shops_adapter(getContext(),list);
                       // recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("id","0");

                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
       //// Log.d(TAG, "onCreateView: "+list);
    }

    private void fetch_products() {
        String url= "http://192.168.43.89/Ecommerce/get_products.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                   // Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            if(shop_id==object.getInt("shop_id"))
                            {
                                //Log.d(TAG, "onResponse: "+object);
                                int pro_id=object.getInt("product_id");
                                String pro_name=object.getString("product_name");
                                String pro_desc=object.getString("product_description");
                                String pro_img=object.getString("product_img");
                                int pro_price=object.getInt("product_price");
                                int sub_id=object.getInt("subcategory_id");
                                int cat_id=object.getInt("category_id");
                                int shop_id=object.getInt("shop_id");
                                //   Log.d(TAG, "onResponse: "+pro_id+pro_name+pro_desc+pro_img);
                                product_model   pro_model=new product_model(pro_id,pro_name,pro_desc,pro_img,pro_price,sub_id,cat_id,shop_id);
                                // Log.d(TAG, "onResponse: "+pro_desc);
                                list.add(pro_model);
                                // Log.d(TAG, "onResponse: "+list.size());
//                            Log.d(TAG, "onResponse: "+pro_model.toString());
                                // adapter.notifyDataSetChanged();
                            }


                        }
                    products_adapter  adapter=new products_adapter(getApplicationContext(),shop_id,list);
                        recyclerView.setAdapter(adapter);
                        Log.d(TAG, "onResponse: "+"inside fetch products"+list.size());
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.toString());
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Log.d(TAG, "fetch_products: "+list.size());
        //Log.d(TAG, "onCreateView: "+list);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}