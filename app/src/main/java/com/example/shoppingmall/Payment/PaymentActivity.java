package com.example.shoppingmall.Payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmall.Home.MainActivity;
import com.example.shoppingmall.Item;
import com.example.shoppingmall.R;
import com.example.shoppingmall.ShoppingBasket.Adapter_Basket;
import com.example.shoppingmall.ShoppingBasket.ShoppingBasketActivity;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item> arrayList;
    private TextView tv_total;
    private EditText et_phone, et_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Item 객체를 전달 받음
        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("item");
        Log.d("인텐트 전달 성공?!?!", item.getName());

        Button btn_finish;

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // Item 객체들을 담기 위한 arrayList(어댑터로 전송)

        arrayList.add(item); // 장바구니에 담음

        btn_finish = (Button) findViewById(R.id.btn_finish);

        tv_total = (TextView) findViewById(R.id.tvTotal);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (EditText) findViewById(R.id.et_address);

        // 총 금액 계산
        int total = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            total += arrayList.get(i).getPrice();
        }

        tv_total.setText(String.valueOf(total) + "원");


        // 결제에서 홈으로 이동
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_address.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "주소를 입력해주세요.", Toast.LENGTH_SHORT).show();

                else if (et_phone.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "연락처를 입력해주세요.", Toast.LENGTH_SHORT).show();

                else {
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        });

        adapter = new Adapter_Payment(arrayList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}