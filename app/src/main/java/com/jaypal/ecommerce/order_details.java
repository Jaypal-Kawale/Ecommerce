package com.jaypal.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class order_details extends AppCompatActivity {
    private static final String TAG = "order detals";
    cart_model cart;
    TextView deliveredbody,deliveredtime,shippingbody,shippeingdate,packbody,packdate,orderbody,orderdate,produtquan,productprice;
    Button btn;
    int stat,mode;
    LinearLayout linearLayout;
    ImageView packed,ordered,shipped,delivered;
    ProgressBar progressBar,packedprogress,shippedprogress,deliveredprogress;
    RecyclerView recyclerView;

    int cust_id,shop_id;

    List<cart_model>cartlist;
    List<cart_details_model>olist;
    List<Integer>p;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        sessionManager=new SessionManager(this);
        recyclerView=findViewById(R.id.show_order_list);
       // btn=findViewById(R.id.place_order);
        orderbody=findViewById(R.id.ordered_body);
        orderdate=findViewById(R.id.ordered_date);
        shippingbody=findViewById(R.id.shipping_body);
        shippeingdate=findViewById(R.id.shipping_date);
        packbody=findViewById(R.id.packed_body);
        deliveredbody=findViewById(R.id.delivered_body);
        deliveredtime=findViewById(R.id.delivered_date);
        packed=findViewById(R.id.packed_indicator);
        ordered=findViewById(R.id.ordered_indicator);
        shipped=findViewById(R.id.shipping_indicator);
        delivered=findViewById(R.id.delivered_indicator);
        packedprogress=findViewById(R.id.packed_shipping_progress);
        shippedprogress=findViewById(R.id.shipping_delivered_progress);
        deliveredprogress=findViewById(R.id.ordered_packed_progress);
        produtquan=findViewById(R.id.product_quantity);
        productprice=findViewById(R.id.product_price);
        linearLayout=findViewById(R.id.rate_now_container);
        btn=findViewById(R.id.cancelorder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mode=getIntent().getIntExtra("mode",0);
      //  Log.d(TAG, "onCreate: mode value"+mode);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false) );
        cust_id= Integer.parseInt(sessionManager.getUserDetail().get("ID"));
        shop_id=getIntent().getIntExtra("shopid",0);

        olist=new ArrayList<>();


        getcart();

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
    }
    private void getcart() {
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
                        if(mode==0)
                            btn.setVisibility(View.VISIBLE);
                   //     totaltv.setText(String.valueOf(total)+" "+"Rs");
                        show_cart_list_adapter adapter=new show_cart_list_adapter(olist,order_details.this);
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