package com.jaypal.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaypal.ecommerce.R;
import com.jaypal.ecommerce.model.review_model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class review_adapter extends RecyclerView.Adapter<review_adapter.vholder> {
 Context context;
 List<review_model>list;

    public review_adapter(Context context, List<review_model> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public vholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_review_model,parent,false);
        return new vholder(v);
    }

    @Override
    public void onBindViewHolder(review_adapter.vholder holder, int position) {
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       // String dateString = formatter.format(new Date(list.get(position).getTimestamp()));
       // Timestamp timestamp=new Timestamp(Long.parseLong(list.get(position).getTimestamp()));
        holder.name.setText(list.get(position).getFirstname()+" "+list.get(position).getLastname());
        holder.rating.setRating(Float.parseFloat(list.get(position).getRating()));
        holder.time.setText(list.get(position).getTimestamp());
        holder.revi.setText(list.get(position).getReview());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class vholder extends RecyclerView.ViewHolder {
        TextView name,time,revi;
        RatingBar rating;
        ImageButton btn;
        public vholder( View itemView) {
            super(itemView);
                name=itemView.findViewById(R.id.rname);
                rating=itemView.findViewById(R.id.rrating);
                time=itemView.findViewById(R.id.rdate);
                revi=itemView.findViewById(R.id.rreview);
                btn=itemView.findViewById(R.id.rpopup);
        }
    }
}
