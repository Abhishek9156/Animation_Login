package com.example.broadcast.Paginatondata.CardOnclick;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.broadcast.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.myViewHolder>  {
    Context context;
    ArrayList<RData> rData;;
    int index = -1;
    private ItemClickListener mListener;


    public RecyclerAdaptor(Context applicationContext, ArrayList<RData> dataArrayList) {
        this.context = applicationContext;
        this.rData = dataArrayList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerdesign, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        final RData rata=rData.get(position);
        Collections.sort(rData, new Comparator<RData>() {
            @Override
            public int compare(RData o1, RData o2) {
                return o1.salary.compareTo(o2.salary);
            }
        });

        holder.id.setText("Id "+rata.getId());
        holder.name.setText("Name "+rata.getEname());
        holder.age.setText("Age "+rata.getAge());
        holder.sal.setText("Salary "+rata.getSalary());
        holder.img.setText("Img "+rata.getImg());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                Toast.makeText(context, "name"+rata.getId(), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                Toast.makeText(context, "img", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index = position;
                Toast.makeText(context, "layout", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        if(index==position){
           holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF4081"));
            holder.id.setTextColor(Color.parseColor("#FFFFFF"));
       }else{
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.id.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return rData.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public interface ItemClickListener {
        void ItemClickListener(View view,int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout relativeLayout;
        TextView id,name,sal,age,img;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.linerarlayout);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            sal = (TextView) itemView.findViewById(R.id.sal);
            age = (TextView) itemView.findViewById(R.id.age);
            img = (TextView) itemView.findViewById(R.id.img);
        }

        @Override
        public void onClick(View v) {
            if(mListener!=null) mListener.ItemClickListener(v,getAdapterPosition());
        }
    }
}
