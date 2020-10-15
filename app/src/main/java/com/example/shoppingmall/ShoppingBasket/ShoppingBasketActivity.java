package com.example.shoppingmall.ShoppingBasket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;


import com.example.shoppingmall.Home.MainActivity;
import com.example.shoppingmall.Item;
import com.example.shoppingmall.Payment.PaymentActivity;
import com.example.shoppingmall.R;

import java.util.ArrayList;

public class ShoppingBasketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);

        // Item 객체를 전달 받음
        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("item");
        Log.d("인텐트 전달 성공?!?!", item.getName());

        Button btn_home, btn_payment;

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // Item 객체들을 담기 위한 arrayList(어댑터로 전송)

        arrayList.add(item); // 장바구니에 담음



        btn_home = (Button)findViewById(R.id.btn_home);
        btn_payment = (Button)findViewById(R.id.btn_payment);


        // 장바구니에서 홈으로 이동
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingBasketActivity.this, MainActivity.class);
                setResult(ShoppingBasketActivity.RESULT_OK);
                startActivity(intent);
            }
        });

        // 장바구니에서 결제로 이동
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingBasketActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        adapter = new Adapter_Basket(arrayList, this);
        recyclerView.setAdapter(adapter);

    }
}