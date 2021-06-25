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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class shop_orders extends Fragment {
    private static final String TAG = "shop_orders";
    RecyclerView recyclerView;
    Button placebtn;
    cart_shop_model sh;
    cart_model cart;
     SessionManager sessionManager;
    List<cart_shop_model> lis;
    List<Integer>list;
    List<cart_model>cartlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shop_orders, container, false);
        recyclerView=view.findViewById(R.id.recycler_shop_showorders);
        list=new ArrayList<>();
        lis=new ArrayList<>();
        cartlist=new ArrayList<>();
        sessionManager=new SessionManager(getContext());
       // fetch_cart();
        //fetch_shop();
        getshoporder();
        return view;
    }

    private void fetch_shop() {
        String url= "http://192.168.43.89/Ecommerce/getcustomer.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: fetch shop"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    //  Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            int id = object.getInt("customer_id");
                            if(list.contains(id))
                            {
                             //   String imageurl = object.getString("shop_img");
                               // int catid = object.getInt("category_id");
                                String name = object.getString("firstname")+" "+object.getString("lastname");
                               String url = "http://192.168.43.89/Ecommerce/images/"+"finegrip-pen.jpg";
                                int j=list.indexOf(id);
                                sh=new cart_shop_model(id,name,0,url,cartlist.get(j).getPrice(),cartlist.get(j).getQuantity(),cartlist.get(j).getCustomer_id());
                                lis.add(sh);

                                //Log.d(TAG, "onResponse: "+"hello"+id);
                                Log.d(TAG, "onResponse: inside recycler"+id+name+0+url+cartlist.get(j).getPrice()+cartlist.get(j).getQuantity());
                            }
                        }
                         Log.d(TAG, "onResponse: inside recycler"+lis.toString());
                        cart_shops_adapter adapter=new cart_shops_adapter(lis, getContext(),2);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false) );
                        recyclerView.setAdapter(adapter);
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
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.d(TAG, "onCreateView: "+list);


    }
    private void fetch_cart() {
        String url= "http://192.168.43.89/Ecommerce/get_cart.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Log.d(TAG, "onResponse: show cart "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            if((object.getInt("status")!=0&&object.getInt("status")!=4)&&object.getInt("shop_id")==Integer.parseInt(Objects.requireNonNull(sessionManager.getUserDetail().get("ID"))))
                            {
                                int cart_id=object.getInt("cart_id");
                                int custmer_id=object.getInt("customer_id");
                                int product_id=object.getInt("product_id");
                                int shop_id=object.getInt("shop_id");
                                int quan=object.getInt("quantity");
                                int pric=object.getInt("price");
                                int status=object.getInt("status");
                                cart=new cart_model(cart_id,custmer_id,product_id,shop_id,quan,pric,status);
                                cartlist.add(cart);
                                list.add(object.getInt("customer_id"));
                                Log.d(TAG, "onResponse: fetch cart"+custmer_id);
                            }

                        }

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
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void getshoporder() {
        String url= "http://192.168.43.89/Ecommerce/getshoporder.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: in getshop order"+response);
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
                        cart_shops_adapter adapter=new cart_shops_adapter(lis, getContext(),2);
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
                map.put("mode","0");
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}