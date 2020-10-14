package com.example.shoppingmall.ShoppingBasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.shoppingmall.Home.MainActivity;
import com.example.shoppingmall.Payment.PaymentActivity;
import com.example.shoppingmall.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);

        Button btn_home, btn_payment;

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
                Intent intent = new Intent(ShoppingBasketActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });



    }
}