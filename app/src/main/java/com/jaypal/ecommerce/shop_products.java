package com.jaypal.ecommerce;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaypal.ecommerce.adapter.products_adapter;
import com.jaypal.ecommerce.model.product_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shop_products extends Fragment {
    private static final String TAG = "Shop_products";
    RecyclerView recyclerView;
    Spinner spinner;
    int shop_id;
    FloatingActionButton floatingActionButton;
    SessionManager sessionManager;
    List<String> categories = new ArrayList<String>();
    List<product_model>list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shop_products,container,false);
        recyclerView=view.findViewById(R.id.shop_products);
        floatingActionButton=view.findViewById(R.id.floating);
        sessionManager=new SessionManager(getContext());
        list=new ArrayList<>();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addproduct();
            }
        });
        //medicalAdapter_2 madapter=new medicalAdapter_2(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false) );
          fetch_products();
        return view;
    }

    private void addproduct() {
        Intent intent=new Intent(getContext(),shop_addproduct.class);
        startActivity(intent);


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
                            product_model pro_model=new product_model(pro_id,pro_name,pro_desc,pro_img,pro_price,sub_id,cat_id,shop_id);
                            // Log.d(TAG, "onResponse: "+pro_desc);
                            list.add(pro_model);
                            // Log.d(TAG, "onResponse: "+list.size());
//                            Log.d(TAG, "onResponse: "+pro_model.toString());
                            // adapter.notifyDataSetChanged();

                        }
                        products_adapter adapter=new products_adapter(getContext(),list,1);
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
        Log.d(TAG, "fetch_products: "+list.size());
        //Log.d(TAG, "onCreateView: "+list);
    }
}