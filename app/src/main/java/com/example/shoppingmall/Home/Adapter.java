package com.example.shoppingmall.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.Item;
import com.example.shoppingmall.Payment.PaymentActivity;
import com.example.shoppingmall.R;
import com.example.shoppingmall.ShoppingBasket.ShoppingBasketActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
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

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.iv_image = itemView.findViewById(R.id.iv_image);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_price = itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 상품을 선택했을 때 이동할 창으로 가기 위한 다이얼로그 띄우기
                    popup();
                }

                // 다이얼로그 띄우는 메서드
                private void popup() {
                    final int pos = getAdapterPosition(); // 클릭한 아이템 위치
                    final Item item = new Item(arrayList.get(pos).getImage(), arrayList.get(pos).getName(), arrayList.get(pos).getPrice(), arrayList.get(pos).isCheck());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("상품 선택");
                    builder.setMessage("상품을 선택하셨습니다. 어디로 이동하시겠습니까?");

                    // 장바구니 버튼 클릭 시 장바구니 품목으로 이동
                    builder.setPositiveButton("장바구니", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, ShoppingBasketActivity.class);
                            FirebaseFirestore db = FirebaseFirestore.getInstance(); // firestore 초기화
                            db.collection("basket")
                                    .add(item);
                            context.startActivity(intent);
                        }
                    });

                    // 결제 버튼을 클릭 시 해당 품목만 바로 결제창으로 이동
                    builder.setNegativeButton("결제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, PaymentActivity.class);
                            final FirebaseFirestore db = FirebaseFirestore.getInstance(); // firestore 초기화

                            // 결제 목록에 추가
                            db.collection("payment")
                                    .add(item);
                            context.startActivity(intent);
                        }
                    });

                    // 취소 버튼을 클릭 시 다이얼로그 닫힘
                    builder.setNeutralButton("취소", null);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            });
        }


    }
}
