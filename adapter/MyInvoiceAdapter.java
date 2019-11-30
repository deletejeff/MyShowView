package com.waakka.myshowview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waakka.myshowview.R;
import com.waakka.myshowview.been.InvoiceBeen;
import com.waakka.myshowview.been.ItemBeen;
import com.waakka.myshowview.been.JsonBeen;

import java.text.DecimalFormat;

public class MyInvoiceAdapter extends RecyclerView.Adapter<MyInvoiceAdapter.MyHolder> {


    private Context context;
    private InvoiceBeen been;
    private Typeface typeface;

    public MyInvoiceAdapter(Context context, InvoiceBeen been, Typeface typeface){
        this.been = been;
        this.context = context;
        this.typeface = typeface;
    }

    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_invoice,viewGroup,false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        if (i % 2 == 0){
            myHolder.rootview.setBackgroundColor(Color.parseColor("#d5f3fc"));
        }else{
            myHolder.rootview.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        SpannableString m1 = new SpannableString("m3");
        m1.setSpan(new RelativeSizeSpan(0.5f), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//一半大小
        m1.setSpan(new SuperscriptSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标



        InvoiceBeen.DataBean dataBeen = been.getData().get(i);

        myHolder.tv4.setText(doubleToString(dataBeen.getChargeMoney()) + " 元");
        myHolder.tv1.setText(dataBeen.getPayName());



        if (dataBeen.getPayName().equals("天然气")){

            myHolder.tv2.setText(doubleToString(dataBeen.getNum())+" ");
            myHolder.tv2.append(m1);
            myHolder.tv3.setText(doubleToString(dataBeen.getPrice())+" 元/");
            myHolder.tv3.append(m1);
        }else{
            myHolder.tv2.setText("/");
            myHolder.tv3.setText("/");
        }


    }



    @Override
    public int getItemCount() {
        return been.getData().size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5;
        LinearLayout rootview;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_1);
            tv2 = itemView.findViewById(R.id.tv_2);
            tv3 = itemView.findViewById(R.id.tv_3);
            tv4 = itemView.findViewById(R.id.tv_4);
            rootview = itemView.findViewById(R.id.rootview);
//            tv1.setTypeface(typeface);
//            tv2.setTypeface(typeface);
//            tv3.setTypeface(typeface);
//            tv4.setTypeface(typeface);
//            tv5.setTypeface(typeface);
        }
    }
}
