package com.jaypal.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaypal.ecommerce.adapter.show_cart_list_adapter;
import com.jaypal.ecommerce.model.cart_details_model;
import com.jaypal.ecommerce.model.cart_model;
import com.jaypal.ecommerce.model.cart_shop_model;
import com.jaypal.ecommerce.model.product_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shop_orderdetails extends AppCompatActivity {
    private static final String TAG = "Shop_orderdetails";
    TextView deliveredbody,deliveredtime,shippingbody,shippeingdate,packbody,packdate,orderbody,orderdate,produtquan,productprice;
    RecyclerView recyclerView;
    int cust_id,shop_id,mode;
    int stat;

    ImageView packed,ordered,shipped,delivered;
    ProgressBar progressBar,packedprogress,shippedprogress,deliveredprogress;
    List<cart_details_model>olist;
    List<Integer>p;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_orderdetails);
        sessionManager=new SessionManager(this);
        recyclerView=findViewById(R.id.recycler_shop_orderdetails);
        packed=findViewById(R.id.packed_indicator);
        ordered=findViewById(R.id.ordered_indicator);
        shipped=findViewById(R.id.shipping_indicator);
        delivered=findViewById(R.id.delivered_indicator);
        orderbody=findViewById(R.id.ordered_body);
        orderdate=findViewById(R.id.ordered_date);
        shippingbody=findViewById(R.id.shipping_body);
        shippeingdate=findViewById(R.id.shipping_date);
        packbody=findViewById(R.id.packed_body);
        deliveredbody=findViewById(R.id.delivered_body);
        deliveredtime=findViewById(R.id.delivered_date);
        packedprogress=findViewById(R.id.packed_shipping_progress);
        shippedprogress=findViewById(R.id.shipping_delivered_progress);
        deliveredprogress=findViewById(R.id.ordered_packed_progress);
        produtquan=findViewById(R.id.product_quantity);
        productprice=findViewById(R.id.product_price);
        mode=getIntent().getIntExtra("mode",0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        packed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveredprogress.setProgress(100);
                packed.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
                updatestatus(2);
            }
        });
        shipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packedprogress.setProgress(100);
                updatestatus(3);
                shipped.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            }
        });
        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shippedprogress.setProgress(100);
                updatestatus(4);
                delivered.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false) );
        shop_id= Integer.parseInt(sessionManager.getUserDetail().get("ID"));

        cust_id=getIntent().getIntExtra("custid",0);
        olist=new ArrayList<>();

        getshoporderdetail();
    }
    private void updatestatus(int i) {

        Log.d(TAG, "updatestatus: called"+i);
        stat=i;
        for( int j=0;j<olist.size();j++)
        {
            String url= "http://192.168.43.89/Ecommerce/updatestatus.php";
            int finalJ = j;
            int finalJ1 = j;
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                          //  Log.d(TAG, "onResponse: "+response);
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
                    HashMap<String,String>map=new HashMap<>();
                    map.put("id", String.valueOf(olist.get(finalJ1).getCart_id()));
                   // Log.d(TAG, "getParams: "+ finalJ +olist.get(finalJ1).getCart_id());
                    map.put("status", String.valueOf(i));
                    return map;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }
    private void managelist() {
        if(stat==1)
        {
            ordered.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            orderbody.setVisibility(View.VISIBLE);

        }else if(stat==2)
        {
            ordered.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            deliveredprogress.setProgress(100);
            orderbody.setVisibility(View.VISIBLE);
            packbody.setVisibility(View.VISIBLE);
            //updatestatus(3);
            packed.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
        }else if(stat==3)
        {
            orderbody.setVisibility(View.VISIBLE);
            packbody.setVisibility(View.VISIBLE);
            shippingbody.setVisibility(View.VISIBLE);
            ordered.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            deliveredprogress.setProgress(100);
            //updatestatus(3);
            packed.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            packedprogress.setProgress(100);
          //  updatestatus(3);
            shipped.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));

        }else if (stat==4)
        {
            orderbody.setVisibility(View.VISIBLE);
            packbody.setVisibility(View.VISIBLE);
            shippingbody.setVisibility(View.VISIBLE);
            deliveredbody.setVisibility(View.VISIBLE);
            ordered.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            deliveredprogress.setProgress(100);
            //updatestatus(3);
            packed.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            packedprogress.setProgress(100);
            //  updatestatus(3);
            shipped.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
            shippedprogress.setProgress(100);
           // updatestatus(4);
            delivered.setColorFilter(getResources().getColor(R.color.successGreen,getTheme()));
        }
        show_cart_list_adapter adapter=new show_cart_list_adapter(olist,shop_orderdetails.this);
        recyclerView.setAdapter(adapter);
    }
    private void getshoporderdetail() {
        String url= "http://192.168.43.89/Ecommerce/getorderdetail.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: getcart"+response);
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
                            stat=status;
                            cart_details_model m=new cart_details_model(cart_id,custmer_id,pro_id, shop_id,quan,pro_price, status,pro_name,pro_desc, pro_img,pric);
                            olist.add(m);
                            total+=pro_price*quan;
                        }
                        show_cart_list_adapter adapter=new show_cart_list_adapter(olist,shop_orderdetails.this);
                        recyclerView.setAdapter(adapter);
                        produtquan.setText("Qty "+String.valueOf(olist.size()));
                        productprice.setText(String.valueOf(total)+" "+"Rs");
                       managelist();
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
                map.put("mode",String.valueOf(mode));
                Log.d(TAG, "getParams: "+shop_id+cust_id+mode);
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