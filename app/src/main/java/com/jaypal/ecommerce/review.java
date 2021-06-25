package com.jaypal.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaypal.ecommerce.adapter.review_adapter;
import com.jaypal.ecommerce.model.review_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class review extends AppCompatActivity {
    private static final String TAG = "review";
    private RatingBar rBar;
    private TextView tView;
    TextView aname,adate,areview;
    Button aupdate,adelete;
    RatingBar arating;
    private Button btn;
    private EditText result;
SessionManager sessionManager;
    RecyclerView recyclerView;
    EditText ren,rer;
    List<review_model>list;
    ImageButton imgbtn,img_popmenu;
    Button button;
    CardView cardView,ecardview;
    TextView tmyreview;
    int shop_id,product_id,customer_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        shop_id=getIntent().getIntExtra("shop_id",0);
        product_id=getIntent().getIntExtra("product_id",0);
        list=new ArrayList<>();
        customer_id=Integer.parseInt(new SessionManager(this).getUserDetail().get("ID"));
        sessionManager=new SessionManager(this);
        init();
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_review();
            }
        });
        getreviews();
    }

    private void getreviews() {
        String url= "http://192.168.43.89/Ecommerce/getreviews.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: review"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject js=jsonArray.getJSONObject(i);
                        if(js.getString("customer_id").equals(sessionManager.getUserDetail().get("ID")))
                        {
                            tmyreview.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.VISIBLE);
                            ecardview.setVisibility(View.GONE);
                            aname.setText(js.getString("firstname")+" "+js.getString("lastname"));
                            adate.setText(js.getString("time"));
                            arating.setRating(Float.parseFloat(js.getString("rating")));
                            areview.setText(js.getString("review"));

                        }else{
                            review_model m=new review_model(js.getString("rating"),js.getString("review"),js.getString("time"),js.getString("firstname"),
                                    js.getString("lastname"));
                            list.add(m);
                        }

                    }
                    review_adapter adapter=new review_adapter(review.this,list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false) );
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                if(product_id==0)
                {
                    map.put("product", String.valueOf(0));
                }else{
                    map.put("product", String.valueOf(product_id));
                }

                map.put("shop_id", String.valueOf(shop_id));
                map.put("id",sessionManager.getUserDetail().get("ID"));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(review.this);
        requestQueue.add(stringRequest);

    }

    private void send_review() {
         rBar.getRating();
         rer.getText().toString();

        String url= "http://192.168.43.89/Ecommerce/putreview.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: review"+response);
                rer.setText("");
                rBar.setRating((float) 0.0);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("rate", String.valueOf(rBar.getRating()));
                map.put("desc",rer.getText().toString());
                if(product_id==0)
                {
                    map.put("product", String.valueOf(0));
                }else{
                    map.put("product", String.valueOf(product_id));
                }

                map.put("shop_id", String.valueOf(shop_id));
                map.put("id",sessionManager.getUserDetail().get("ID"));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(review.this);
        requestQueue.add(stringRequest);




    }

    private void init()
    {
        rBar = (RatingBar) findViewById(R.id.ratingBar1);
        tView = (TextView) findViewById(R.id.rereview);
        cardView=findViewById(R.id.myreview);
        ecardview=findViewById(R.id.ereview);
        tmyreview=findViewById(R.id.tmyreview);
        imgbtn = findViewById(R.id.rebtn);
        img_popmenu=findViewById(R.id.popmenu);
        result = (EditText) findViewById(R.id.add);
        recyclerView=findViewById(R.id.recyclerre);

        aname=findViewById(R.id.aname);
        arating=findViewById(R.id.arating);
        areview=findViewById(R.id.areview);
        adate=findViewById(R.id.adate);
//    aupdate=findViewById(R.id.aupdate);
//    adelete=findViewById(R.id.adelete);


        rer=findViewById(R.id.rereview);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}