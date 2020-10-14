package com.example.shoppingmall.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shoppingmall.Home.MainActivity;
import com.example.shoppingmall.R;
import com.example.shoppingmall.ShoppingBasket.ShoppingBasketActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button btn_home, btn_basket;

        btn_home = (Button)findViewById(R.id.btn_home);
        btn_basket = (Button)findViewById(R.id.btn_basket);

        // 결제에서 홈으로 이동
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 결제에서 장바구니로 이동
        btn_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, ShoppingBasketActivity.class);
                startActivity(intent);
            }
        });
    }
}