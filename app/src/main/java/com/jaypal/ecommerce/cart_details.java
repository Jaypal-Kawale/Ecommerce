package com.jaypal.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.jaypal.ecommerce.adapter.products_adapter;
import com.jaypal.ecommerce.adapter.show_cart_list_adapter;
import com.jaypal.ecommerce.model.cart_details_model;
import com.jaypal.ecommerce.model.cart_model;
import com.jaypal.ecommerce.model.cart_shop_model;
import com.jaypal.ecommerce.model.product_model;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cart_details extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = "cart details";
    Button placebtn;
    TextView fname,addr,pin,totaltv;
RecyclerView recyclerView;
   int cust_id,shop_id;
    List<cart_details_model>olist;
    List<Integer>p;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        sessionManager=new SessionManager(this);
        recyclerView=findViewById(R.id.show_cart_list);
        fname=findViewById(R.id.full_name);
        addr=findViewById(R.id.address);
        pin=findViewById(R.id.pincode);
        totaltv=findViewById(R.id.total_cart_amount);
        placebtn=findViewById(R.id.cart_continue_btn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false) );
        cust_id= Integer.parseInt(sessionManager.getUserDetail().get("ID"));
        shop_id=getIntent().getIntExtra("shopid",0);
        olist=new ArrayList<>();
        Checkout.preload(getApplicationContext());
        p=new ArrayList<>();
        getcart();
 placebtn.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         int total=0;
         for(int i=0;i<olist.size();i++)
             total+=olist.get(i).getPrice();
         paymentnow(String.valueOf(total));
     }
 });
    }

    private void getcart() {
        String url= "http://192.168.43.89/Ecommerce/getcart.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int total=0;
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            int pro_id=object.getInt("product_id");
                            String pro_name=object.getString("product_name");
                            String pro_desc=object.getString("product_description");
                            String pro_img=object.getString("product_img");
                            int pro_price=object.getInt("product_price");
                            int shop_id=object.getInt("shop_id");
                            int cart_id=object.getInt("cart_id");
                            int custmer_id=object.getInt("customer_id");
                            int quan=object.getInt("quantity");
                            int pric=object.getInt("price");
                            int status=object.getInt("status");
                            cart_details_model m=new cart_details_model(cart_id,custmer_id,pro_id, shop_id,quan,pro_price, status,pro_name,pro_desc, pro_img,pric);
                            olist.add(m);
                            total+=pro_price*quan;
                        }
                        totaltv.setText(String.valueOf(total)+" "+"Rs");
                        show_cart_list_adapter adapter=new show_cart_list_adapter(olist,cart_details.this,1);
                        recyclerView.setAdapter(adapter);
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
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("shop_id", String.valueOf(shop_id));
                map.put("customer_id", String.valueOf(cust_id));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void paymentnow(String s) {
        final Activity activity=this;
        Checkout checkout=new Checkout();
        checkout.setKeyID("rzp_test_Ypa2yoK8SFAFgz");
        checkout.setImage(R.drawable.ic_paytm_logo);
        double finalamt=Float.parseFloat(s)*100;
        JSONObject option=new JSONObject();
        try {
            option.put("name",sessionManager.getUserDetail().get("NAME"));
            option.put("description","Reference id. #1234");
            option.put("image",R.drawable.ic_paytm_logo);
            option.put("theme.color","#3399cc");
            option.put("currency","INR");
            option.put("amount",finalamt+"");
            option.put("prefill.email",sessionManager.getUserDetail().get("EMAIL"));
            option.put("prefill.contact",sessionManager.getUserDetail().get("MOB"));
            checkout.open(activity,option);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void placeorder(int i) {
        String url= "http://192.168.43.89/Ecommerce/placeorder.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                    if(succes.equals("1")) {
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
        }){
           
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("cartid",String.valueOf(i));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onPaymentSuccess(String s) {
        for(int i=0;i<olist.size();i++)
            placeorder(olist.get(i).getCart_id());
        Toast.makeText(cart_details.this,"Order Placed Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(cart_details.this,"Failed",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}