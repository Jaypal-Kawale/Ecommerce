package com.jaypal.ecommerce.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaypal.ecommerce.R;
import com.jaypal.ecommerce.adapter.shops_adapter;
import com.jaypal.ecommerce.model.shop_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class shops extends Fragment {

    private static final String TAG = "shops";
    RecyclerView recyclerView;
    shop_model sh;
    List<shop_model> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shops,container,false);
        recyclerView=view.findViewById(R.id.recycler_medicine_centers);
        //medicalAdapter_2 madapter=new medicalAdapter_2(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false) );

        list=new ArrayList<>();
        String url= "http://192.168.43.89/Ecommerce/getshops.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                  //  Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            int id = object.getInt("shop_id");
                            String imageurl = object.getString("shop_img");
                            int catid = object.getInt("category_id");
                            String name = object.getString("shop_name");
                            String url = "http://192.168.43.89/Ecommerce/images/"+imageurl;
                            sh=new shop_model(id,name,catid,imageurl);
                            list.add(sh);
                           // adapter.notifyDataSetChanged();

                        }
                        shops_adapter adapter=new shops_adapter(getContext(),list);
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
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    //    Log.d(TAG, "onCreateView: "+list);

      //  recyclerView.setAdapter(madapter);
        return view;

    }
}