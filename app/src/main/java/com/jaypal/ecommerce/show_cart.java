package com.jaypal.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaypal.ecommerce.adapter.cart_shops_adapter;
import com.jaypal.ecommerce.adapter.shops_adapter;
import com.jaypal.ecommerce.model.cart_model;
import com.jaypal.ecommerce.model.cart_shop_model;
import com.jaypal.ecommerce.model.shop_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static easypay.manager.PaytmAssist.getContext;

public class show_cart extends AppCompatActivity {
    private static final String TAG = "show cart";
    RecyclerView recyclerView;
    cart_shop_model sh;
SessionManager sessionManager;
    List<cart_shop_model> lis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        sessionManager=new SessionManager(this);
        recyclerView=findViewById(R.id.recycler_showcart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lis=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false) );
        getcartshop();
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void getcartshop() {
        String url= "http://192.168.43.89/Ecommerce/getcartshop.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.d(TAG, "onResponse: in getcart "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String imageurl = object.getString("shop_img");
                            int catid = object.getInt("category_id");
                            String name = object.getString("shop_name");
                            String url = "http://192.168.43.89/Ecommerce/images/"+imageurl;
                            int custmer_id=object.getInt("customer_id");
                            int shop_id=object.getInt("shop_id");
                            int quan=object.getInt("quantity");
                            int pric=object.getInt("price");
                            int status=object.getInt("status");
                            sh=new cart_shop_model(shop_id,name,catid,imageurl,pric,quan);
                            lis.add(sh);
                        }
                        Log.d(TAG, "onResponse: inside recycler"+lis.toString()+lis.size());
                        cart_shops_adapter adapter=new cart_shops_adapter(lis, show_cart.this,0);
                        recyclerView.setLayoutManager(new LinearLayoutManager(show_cart.this,LinearLayoutManager.VERTICAL,false) );
                        recyclerView.setAdapter(adapter);
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
                map.put("customer_id",sessionManager.getUserDetail().get("ID"));
                map.put("mode","0");
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}