package com.jaypal.ecommerce.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.jaypal.ecommerce.R;
import com.jaypal.ecommerce.model.cart_details_model;
import com.jaypal.ecommerce.model.cart_shop_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class show_cart_list_adapter extends RecyclerView.Adapter<show_cart_list_adapter.vadapter> {
    private static final String TAG = "cart list adapter";
    List<cart_details_model> olist;
    Context context;
    int mode;

    public show_cart_list_adapter(List<cart_details_model> olist, Context context, int mode) {
        this.olist = olist;
        this.context = context;
        this.mode = mode;
    }

    public show_cart_list_adapter(List<cart_details_model> olist, Context context) {
        this.olist = olist;
        this.context = context;
        this.mode=0;
    }

    @Override
    public vadapter onCreateViewHolder( ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.model_product_cart,parent,false);
        return new vadapter(v);
    }

    @Override
    public void onBindViewHolder( show_cart_list_adapter.vadapter holder, int position) {
       if(mode==1)
           holder.cross.setVisibility(View.VISIBLE);
       holder.cross.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               removefromcart(olist.get(position).getCart_id());
           }
       });
        holder.name.setText(olist.get(position).getProduct_name());
        holder.price.setText(String.valueOf(olist.get(position).getPrice())+" "+"Rs");
        Glide.with(context).load("http://192.168.43.89/Ecommerce/images/"+olist.get(position).getProduct_img()).into(holder.imageView);

    }

    private void removefromcart(int id) {
        String url= "http://192.168.43.89/Ecommerce/removecartitem.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: in remove cartitem "+response);
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
              map.put("cartid", String.valueOf(id));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    @Override
    public int getItemCount() {
        return olist.size();
    }

    public class vadapter extends RecyclerView.ViewHolder {
        ImageView imageView,cross;
        TextView name,price;
        public vadapter( View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.product_image);
            name=itemView.findViewById(R.id.product_title);
            price=itemView.findViewById(R.id.product_price);
            cross=itemView.findViewById(R.id.cross_icon);
        }
    }
}
