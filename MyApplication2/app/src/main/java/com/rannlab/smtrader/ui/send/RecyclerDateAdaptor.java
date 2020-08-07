package com.rannlab.smtrader.ui.send;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rannlab.smtrader.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerDateAdaptor extends RecyclerView.Adapter<RecyclerDateAdaptor.ViewHolderrs> {
//    private static final int TYPE_HEADER = 0;
//    private static final int TYPE_ITEM = 1;

    //Header datas;
    ArrayList<DateData> dateData;
    Context context;



    public RecyclerDateAdaptor(Context context ,ArrayList<DateData> dateData) {
        //  this.datas=activity;
        this.dateData=dateData;
        this.context=context;

    }



//    public void clearApplications() {
//        int size = this.dateData.size();
//        if (size > 0) {
//            for (int i = 0; i < size; i++) {
//                dateData.remove(0);
//            }
//
//            this.notifyItemRangeRemoved(0, size);
//        }
//    }
//
//    public void addApplications( ArrayList<DateData> dataDate) {
//        this.dateData.addAll(dateData);
//        this.notifyItemRangeInserted(0, dateData.size() - 1);
//    }

    @Override
    public RecyclerDateAdaptor.ViewHolderrs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {











//        if(viewType == TYPE_HEADER)
//        {
//            View v = LayoutInflater.from(context).inflate(R.layout.header_item, parent, false);
//            return  new VHHeader(v);
//        }
//        else if(viewType == TYPE_ITEM)
//        {
        View v = LayoutInflater.from(context).inflate(R.layout.datadaptorrecycler, parent, false);
        return new ViewHolderrs(v);
//        }
//        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }



//    private DateData getItem(int position)
//    {
//        return dateData == null ? 0 : dateData.size();
//    }






    @Override
    public void onBindViewHolder(@NonNull RecyclerDateAdaptor.ViewHolderrs viewHolder, int position) {

//        if(holder instanceof VHHeader)
//        {
//
//            VHHeader VHheader = (VHHeader)holder;
//            //VHheader.txtTitle.setText(" Total S.P. : \n"+"  Incentive :");
//            VHheader.txtTitle.setText("Total Profit : "+datas.getHeader()+"\nIncentive     : "+datas.getB());
//
//        }  else if(holder instanceof ViewHolderrs) {
//            DateData dateDate = getItem(position - 1);
//            ViewHolderrs viewHolder = (ViewHolderrs) holder;


        DateData imageView=dateData.get(position);

        String pn = imageView.getPname();
        String sc = imageView.getIncentive();
        String Actualsp=imageView.getActualsp();
//double a=0.0;
//a=a+((Double.valueOf(dateData1.getProfit())));
        //     Toast.makeText(context, "value"+a, Toast.LENGTH_SHORT).show();
        String per = imageView.getProper();
        String profi = imageView.getProfit();
        String rupee = imageView.getRupee();
        if(per.equals("null")){
            viewHolder.tvprofitper.setText("N.A.");
        }
          else if(per.equals("0")){
            viewHolder.tvprofitper.setText("₹"+rupee);
        }else {
            viewHolder.tvprofitper.setText(per + "%");
        }


        if (Double.parseDouble(profi) < Double.parseDouble(Actualsp)) {

            //     Picasso.with(context).load(R.drawable.download).placeholder(R.drawable.download).into(viewHolder.imageView);
            //   Glide.with()
            Glide.with(context).load(R.drawable.don).into(viewHolder.imageView);
            viewHolder.tvpname.setText(pn);
            viewHolder.tvscheme.setText(sc);

            viewHolder.tvprofit.setText(profi);

        } else if (profi.equals(Actualsp)) {
            Glide.with(context).load(R.drawable.equa).into(viewHolder.imageView);
            viewHolder.tvpname.setText(pn);
            viewHolder.tvscheme.setText(sc);
            viewHolder.tvprofit.setText(profi);

        }else {
            Glide.with(context).load(R.drawable.up).into(viewHolder.imageView);
            viewHolder.tvpname.setText(pn);
            viewHolder.tvscheme.setText(sc);
            // viewHolder.tvprofitper.setText(per + "%");
            viewHolder.tvprofit.setText(profi);

        }}


    // need to override this method
//    @Override
//    public int getItemViewType(int position) {
//        if(isPositionHeader(position))
//            return TYPE_HEADER;
//        return TYPE_ITEM;
//    }

//    private boolean isPositionHeader(int position) {
//        return position == 0;
//    }
//





    @Override
    public int getItemCount() {
        return dateData == null ? 0 : dateData.size();
    }

//    class VHHeader extends RecyclerDateAdaptor.ViewHolderrs{
//        TextView txtTitle;
//        public VHHeader(View itemView) {
//            super(itemView);
//            this.txtTitle = (TextView)itemView.findViewById(R.id.txtHeader);
//        }
//    }


    public class ViewHolderrs extends RecyclerView.ViewHolder {

        TextView tvpname,tvscheme,tvprofitper,tvprofit;
        ImageView imageView;
        public ViewHolderrs(@NonNull View itemView) {

            super(itemView);
            tvpname=itemView.findViewById(R.id.tvPName);
            tvscheme=itemView.findViewById(R.id.tvScheme1);
            tvprofitper=itemView.findViewById(R.id.tvProfitper);
            tvprofit=itemView.findViewById(R.id.tvprofit1);
            imageView=itemView.findViewById(R.id.increimage);


        }
    }
}

