package com.jaypal.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jaypal.ecommerce.R;
import com.jaypal.ecommerce.cart_details;
import com.jaypal.ecommerce.model.cart_shop_model;
import com.jaypal.ecommerce.order_details;
import com.jaypal.ecommerce.shop_orderdetails;

import java.util.List;

public class cart_shops_adapter extends RecyclerView.Adapter<cart_shops_adapter.cviewholder> {
    private static final String TAG = "in cart shops adapter";
    List<cart_shop_model> lis;
    Context context;
    int mode;

    public cart_shops_adapter(List<cart_shop_model> lis, Context context,int mode) {
        this.lis = lis;
        this.context = context;
        this.mode=mode;
    }

  
    @Override
    public cviewholder onCreateViewHolder(  ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.model_shop_cart,parent,false);
        return new cviewholder(v);
    
    }

    @Override
    public void onBindViewHolder(cviewholder holder, int position) {
        holder.name.setText(lis.get(position).getShop_name());
        holder.total.setText(String.valueOf(lis.get(position).getTotal())+" "+"RS");
       Log.d(TAG, "onBindViewHolder: "+lis.get(position).getTotal()+lis.get(position).getQty());
        holder.qty.setText(String.valueOf(lis.get(position).getQty()));
        Glide.with(context).load("http://192.168.43.89/Ecommerce/images/"+lis.get(position).getShop_img()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode==0)
                {
                    Intent intent =new Intent(context, cart_details.class);
                    intent.putExtra("shopid",lis.get(position).getShop_id());
                    context.startActivity(intent);
                }else if(mode==2)
                {
                   // Glide.with(context).load("http://192.168.43.89/Ecommerce/images/img.JPG").into(holder.imageView);
                    Intent intent =new Intent(context, shop_orderdetails.class);
                    intent.putExtra("custid",lis.get(position).getCustid());
                    Log.d(TAG, "onClick:inside cart shops adapter "+lis.get(position).getCustid());
                    context.startActivity(intent);
                }
                else if(mode==4)
                {
                    Intent intent =new Intent(context, shop_orderdetails.class);
                    intent.putExtra("custid",lis.get(position).getCustid());
                    intent.putExtra("mode",4);
                    Log.d(TAG, "onClick:inside cart shops adapter "+lis.get(position).getCustid());
                    context.startActivity(intent);

                }else if(mode==1)
                {
                    Intent intent =new Intent(context, order_details.class);
                    intent.putExtra("shopid",lis.get(position).getShop_id());
                    intent.putExtra("custid",lis.get(position).getCustid());
                    intent.putExtra("mode",1);
                    context.startActivity(intent);
                }
                else {
                    Intent intent =new Intent(context, order_details.class);
                    intent.putExtra("shopid",lis.get(position).getShop_id());
                    intent.putExtra("custid",lis.get(position).getCustid());
                    intent.putExtra("mode",0);
                    context.startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return lis.size();
    }

    public class cviewholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView total,qty,name;

        public cviewholder( View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cart_image_hospital4);
            total=itemView.findViewById(R.id.cart_total);
            qty=itemView.findViewById(R.id.cart_qty);
            name=itemView.findViewById(R.id.cart_te14);




        }
    }
}
