package com.example.shoppingmall.Payment;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmall.Home.MainActivity;
import com.example.shoppingmall.Item;
import com.example.shoppingmall.R;
import com.example.shoppingmall.ShoppingBasket.Adapter_Basket;
import com.example.shoppingmall.ShoppingBasket.ShoppingBasketActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item> arrayList;
    private TextView tv_total;
    private EditText et_phone, et_address;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        db = FirebaseFirestore.getInstance();

        Button btn_finish;


        arrayList = new ArrayList<>(); // Item 객체들을 담기 위한 arrayList(어댑터로 전송)

        btn_finish = (Button) findViewById(R.id.btn_finish);

        tv_total = (TextView) findViewById(R.id.tvTotal);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (EditText) findViewById(R.id.et_address);

        // 파이어스토어에서 데이터 가져오기
        db.collection("payment")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                arrayList.add(new Item(document.get("image").toString(), document.get("name").toString(), Integer.parseInt(document.get("price").toString()), Boolean.parseBoolean(document.get("check").toString())));
                            }
                        }
                    }
                });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new Adapter_Payment(arrayList, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                // 총 금액 계산
                int total = 0;
                for (int i = 0; i < arrayList.size(); i++) {
                    total += arrayList.get(i).getPrice();
                }

                tv_total.setText(String.valueOf(total) + "원");

            }
        }, 1500);


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

                    // 결제한 후 결제 목록 전체 삭제
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

                    Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }


            }
        });


    }
}