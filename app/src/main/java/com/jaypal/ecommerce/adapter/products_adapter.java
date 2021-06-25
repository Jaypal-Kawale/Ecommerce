package com.jaypal.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
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
import com.jaypal.ecommerce.product_details;
import com.jaypal.ecommerce.review;
import com.jaypal.ecommerce.shop_details;

import java.io.Serializable;
import java.util.List;

public class products_adapter extends RecyclerView.Adapter<products_adapter.product_viewholder> {
    private static final String TAG = "Adapter";
    Context context;
    List<product_model> list;
    int mode=0;
    int id=0;

    public products_adapter(Context context, int id ,List<product_model> list) {
        this.context = context;
        this.list = list;
        this.mode = 0;
        this.id = id;
    }

    public products_adapter(Context context, List<product_model> list, int mode) {
        this.context = context;
        this.list = list;
        this.mode = mode;
        this.id=0;
    }

    public products_adapter(Context context, List<product_model> list) {
        this.context = context;
        this.list = list;
        this.mode=0;
        this.id=0;
    }


    @Override
    public product_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: "+"Inside");
        View v= LayoutInflater.from(context).inflate(R.layout.model_product,parent,false);
        return new product_viewholder(v);
    }

    @Override
    public void onBindViewHolder( products_adapter.product_viewholder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+list.get(position).getProduct_price());
        holder.product_name.setText(list.get(position).getProduct_description());
        holder.productprice.setText(String.valueOf(list.get(position).getProduct_price()+" "+"Rs"));
        Glide.with(context).load("http://192.168.43.89/Ecommerce/images/"+list.get(position).getProduct_img()).into(holder.productimg);
        Log.d(TAG, "onBindViewHolder: mode in "+mode);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode==0)
                {
                    product_model pq= list.get(position);
                    Intent intent=new Intent(context, product_details.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("product_id",pq.getProduct_id());
                    context.startActivity(intent);

                }else{
                    product_model pq= list.get(position);
                    Intent intent=new Intent(context, product_details.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("product_id",pq.getProduct_id());
                    intent.putExtra("mode",1);
                    context.startActivity(intent);

                }

            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_model pq= list.get(position);
                Intent intent=new Intent(context, review.class);
                intent.putExtra("product_id",pq.getProduct_id());
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

    public class product_viewholder extends RecyclerView.ViewHolder {
        TextView product_name;
        ImageView productimg;
        TextView productprice;
        LinearLayout linearLayout;
        public product_viewholder(View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.product_image);
            product_name=itemView.findViewById(R.id.product_title);
            //imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            linearLayout=itemView.findViewById(R.id.rate_linearLayout);
            productprice = itemView.findViewById(R.id.product_price);
        }
    }
}
