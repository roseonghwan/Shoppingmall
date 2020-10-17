package com.example.shoppingmall.ShoppingBasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;


import com.example.shoppingmall.Home.MainActivity;
import com.example.shoppingmall.Item;
import com.example.shoppingmall.Payment.PaymentActivity;
import com.example.shoppingmall.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShoppingBasketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item>arrayList;

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);

        // Item 객체를 전달 받음
        /*Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("item");
        Log.d("인텐트 전달 성공?!?!", item.getName());
*/
        db = FirebaseFirestore.getInstance();
        Button btn_home, btn_payment;

        arrayList = new ArrayList<>(); // Item 객체들을 담기 위한 arrayList(어댑터로 전송)

        // firestore에서 받아옴
        db.collection("basket")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                arrayList.add(new Item(document.get("image").toString(), document.get("name").toString(),Integer.parseInt(document.get("price").toString()),Boolean.parseBoolean(document.get("check").toString())));
                            }
                        }
                    }
                });


        // 파이어스토어에서 데이터를
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new Adapter_Basket(arrayList, getApplicationContext());
                recyclerView = findViewById(R.id.recyclerview);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                adapter.notifyDataSetChanged();
            }
        },1500);



        btn_home = (Button)findViewById(R.id.btn_home);
        btn_payment = (Button)findViewById(R.id.btn_payment);


        // 장바구니에서 홈으로 이동
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingBasketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 장바구니에서 결제로 이동
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance();
                Intent intent = new Intent(ShoppingBasketActivity.this, PaymentActivity.class);
                for (int i=0; i < arrayList.size(); i++){
                    if (arrayList.get(i).isCheck() == true){
                        db.collection("payment")
                                .add(arrayList.get(i));
                    }
                }
                // 구입할 물건을 체크한 후 결제로 이동하면 장바구니 모두 삭제
                db.collection("basket")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documnet : task.getResult()) {
                                        db.collection("basket").document(documnet.getId())
                                                .delete();
                                    }
                                }
                            }
                        });
                startActivity(intent);
            }
        });


    }
}