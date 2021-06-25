package com.jaypal.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.jaypal.ecommerce.model.product_model;
import com.jaypal.ecommerce.view.home;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class product_details extends AppCompatActivity implements Serializable {
    private static final String TAG = "product details";
    TextView pname,pdesc,pr;
Button addproduct;
ImageView imgview;
int product_id;
int shop_id;
int price;
int mode;
SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        sessionManager=new SessionManager(this);
        product_id=getIntent().getIntExtra("product_id",0);
        mode=getIntent().getIntExtra("mode",0);
        pname=findViewById(R.id.product_title);
        pdesc=findViewById(R.id.product_desc_here);
        pr=findViewById(R.id.product_price_goes);
        imgview=findViewById(R.id.product_images_goes);
        addproduct=findViewById(R.id.add_product_goes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.d(TAG, "onCreate: mode in product details"+mode);
        String tex="Remove Item";
        if(mode!=0)
            addproduct.setText(tex);
       getproduct();
       addproduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(mode!=0)
                   removeproduct();
               else
               addtocart();
           }
       });
    }

    private void removeproduct() {
        String url= "http://192.168.43.89/Ecommerce/removeproduct.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                Log.d(TAG, "onResponse: "+"Add to cart"+response);
                try {
                    jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")) {
                        Toast.makeText(product_details.this,"Successfully removed",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(product_details.this,"problem while removing item",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: "+e);
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
                map.put("id",sessionManager.getUserDetail().get("ID"));
                map.put("product_id", String.valueOf(product_id));



                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void addtocart() {
        String url= "http://192.168.43.89/Ecommerce/addtocart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                Log.d(TAG, "onResponse: "+"Add to cart"+response);
                try {
                    jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")) {
                        Toast.makeText(product_details.this,"Successfully added to cart",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(product_details.this,"problem while adding to cart",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: "+e);
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
                map.put("customer_id",sessionManager.getUserDetail().get("ID"));
                map.put("product_id", String.valueOf(product_id));
                map.put("shop_id", String.valueOf(shop_id));
                map.put("price", String.valueOf(price));


                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getproduct() {
        String url= "http://192.168.43.89/Ecommerce/get_productdetails.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                Log.d(TAG, "onResponse: "+"count"+response);
                try {
                    jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")) {
                        JSONObject js=jsonObject.getJSONObject("data");
                        pname.setText(js.getString("product_description"));
                        pdesc.setText(js.getString("product_name"));
                        shop_id=js.getInt("shop_id");
                        price=js.getInt("product_price");
                        Glide.with(getApplicationContext()).load("http://192.168.43.89/Ecommerce/images/"+js.getString("product_img")).into(imgview);
                    }
                    else{
                        Toast.makeText(product_details.this,"product details didnot retrived",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: "+e);
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
                 map.put("product_id", String.valueOf(product_id));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}