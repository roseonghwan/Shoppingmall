package com.example.shoppingmall.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.Item;
import com.example.shoppingmall.Payment.PaymentActivity;
import com.example.shoppingmall.R;
import com.example.shoppingmall.ShoppingBasket.ShoppingBasketActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;


// 리사이클러 뷰를 구현하기 위한 어댑터
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Item> arrayList; // Item객체들을 담을 arrayList
    private Context context; // 어댑터는 context가 없으므로 선택한 액티비티에 대한 context를 가져올 때 필요

    public Adapter(ArrayList<Item> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    // 어댑터에 연결된 후 리스트 뷰에 대한 홀더를 최초로 생성
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
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
        Button btn_basket, btn_payment;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.iv_image = itemView.findViewById(R.id.iv_image);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_price = itemView.findViewById(R.id.tv_price);

            // 홈에서 품목을 클릭했을 시 이벤트
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition(); // 클릭한 아이템 위치
                    final Item item = new Item(arrayList.get(pos).getImage(), arrayList.get(pos).getName(), arrayList.get(pos).getPrice(), arrayList.get(pos).isCheck());

                    // 상품을 선택했을 때 아래 버튼 등장
                    MainActivity.linearLayout.setVisibility(View.VISIBLE);

                    // 장바구니를 클릭한 경우
                    MainActivity.btn_basket.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, ShoppingBasketActivity.class);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            // firestore 장바구니 데이터에 저장
                            db.collection("basket")
                                    .add(item);
                            context.startActivity(intent);
                        }
                    });

                    // 해당 품목 결제를 클릭한 경우
                    MainActivity.btn_payment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent intent = new Intent(context, PaymentActivity.class);
                            final FirebaseFirestore db = FirebaseFirestore.getInstance();

                            // 결제 목록 전체 삭제(해당 품목만 구입해야 하므로)
                            db.collection("payment")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot documnet : task.getResult()) {
                                                    db.collection("payment").document(documnet.getId())
                                                            .delete();
                                                }
                                            }
                                        }
                                    });

                            // firebstore 결제 데이터에 추가
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    db.collection("payment")
                                            .add(item);
                                    context.startActivity(intent);
                                }
                            },1000);

                        }
                    });
                }
            });
        }


    }
}
