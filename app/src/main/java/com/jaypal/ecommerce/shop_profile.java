package com.jaypal.ecommerce;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.jaypal.ecommerce.adapter.cart_shops_adapter;
import com.jaypal.ecommerce.model.cart_model;
import com.jaypal.ecommerce.model.cart_shop_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class shop_profile extends Fragment {
    private static final String TAG = "Profile";
    TextView username,mob,addr,fullname;
    Button logoutbtn;
    cart_shop_model sh;
   ImageView img;
    List<cart_shop_model> lis;
    RecyclerView recyclerView;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shop_profile, container, false);
        username=view.findViewById(R.id.user_name);
        mob=view.findViewById(R.id.user_email);
        addr=view.findViewById(R.id.address);
        fullname=view.findViewById(R.id.address_full_name);
        logoutbtn=view.findViewById(R.id.sign_out_btn);
        img=view.findViewById(R.id.product_image);
        lis=new ArrayList<>();
        recyclerView=view.findViewById(R.id.shop_recent_orders);
        sessionManager=new SessionManager(getContext());
        Log.d(TAG, "onCreateView: "+sessionManager.getUserDetail());
        getprofile();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false) );
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });
        getshoporder();
        return view;
    }
    private void getshoporder() {
        String url= "http://192.168.43.89/Ecommerce/getshoporder.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: in shopprofile order"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String imageurl = object.getString("shop_img");
                            String lastname = object.getString("lastname");
                            String name = object.getString("firstname");
                            String url = "http://192.168.43.89/Ecommerce/images/"+imageurl;
                            int custmer_id=object.getInt("customer_id");
                            int shop_id=object.getInt("shop_id");
                            int quan=object.getInt("quantity");
                            int pric=object.getInt("price");
                            //int status=object.getInt("status");
                            sh=new cart_shop_model(shop_id,name+lastname,0,imageurl,pric,quan,custmer_id);
                            lis.add(sh);

                        }
                        Log.d(TAG, "onResponse: inside recycler"+lis.toString()+lis.size());
                        cart_shops_adapter adapter=new cart_shops_adapter(lis, getContext(),4);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false) );
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
                map.put("shop_id",sessionManager.getUserDetail().get("ID"));
                map.put("mode","1");
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    private void getprofile() {
        String url= "http://192.168.43.89/Ecommerce/get_myshopprofile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                    if(succes.equals("1")) {
                        JSONObject js=jsonObject.optJSONObject("data");
                        username.setText(js.getString("shop_name"));
                    }
                    else{
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
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

}