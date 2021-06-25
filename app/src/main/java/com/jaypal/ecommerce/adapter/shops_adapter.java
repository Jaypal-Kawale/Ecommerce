package com.jaypal.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jaypal.ecommerce.R;
import com.jaypal.ecommerce.model.product_model;
import com.jaypal.ecommerce.model.shop_model;
import com.jaypal.ecommerce.review;
import com.jaypal.ecommerce.shop_details;

import java.util.List;

public class shops_adapter extends RecyclerView.Adapter<shops_adapter.shop_viewholder> {
    Context context;
    List<shop_model>list;

    public shops_adapter(Context context, List<shop_model> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public shop_viewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.model_shop,parent,false);
        return new shop_viewholder(v);
    }

    @Override
    public void onBindViewHolder(shops_adapter.shop_viewholder holder, int position) {
        //holder.imageView_hospital.setImageResource(list.get(position).getShop_img());

        holder.name_hospital.setText(list.get(position).getShop_name());
        holder.addr_hospital.setText("open");
        Glide.with(context).load("http://192.168.43.89/Ecommerce/images/"+list.get(position).getShop_img()).into(holder.imageView_hospital);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, shop_details.class);
                intent.putExtra("shop_id",list.get(position).getShop_id());
                context.startActivity(intent);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             int id=list.get(position).getShop_id();
                Intent intent=new Intent(context, review.class);
                intent.putExtra("product_id",0);
                intent.putExtra("shop_id",id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class shop_viewholder extends RecyclerView.ViewHolder {
        TextView name_hospital;
        ImageView imageView_hospital;
        TextView addr_hospital;
        LinearLayout linearLayout;
        public shop_viewholder(View itemView) {
            super(itemView);
            imageView_hospital = itemView.findViewById(R.id.image_hospital4);
            addr_hospital=itemView.findViewById(R.id.te24);
            //imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            linearLayout=itemView.findViewById(R.id.rate_linearLayout);
            name_hospital = itemView.findViewById(R.id.te14);
        }
    }
}
