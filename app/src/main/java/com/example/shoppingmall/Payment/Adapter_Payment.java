package com.example.shoppingmall.Payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.Item;
import com.example.shoppingmall.R;
import com.example.shoppingmall.ShoppingBasket.Adapter_Basket;

import java.util.ArrayList;

public class Adapter_Payment extends RecyclerView.Adapter<Adapter_Payment.ViewHolder> {


    private ArrayList<Item> arrayList; // Item객체들을 담을 arrayList
    private Context context; // 어댑터는 context가 없으므로 선택한 액티비티에 대한 context를 가져올 때 필요

    public Adapter_Payment(ArrayList<Item> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    // 어댑터에 연결된 후 리스트 뷰에 대한 홀더를 최초로 생성
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 어댑터로 전송된 이미지, 텍스트를 로드해서 넣어줌
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImage())
                .into(holder.iv_image);
        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_price.setText(String.valueOf(arrayList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // list_item에 있는 것들을 불러옴
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_name, tv_price;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.iv_image = itemView.findViewById(R.id.iv_image);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}

