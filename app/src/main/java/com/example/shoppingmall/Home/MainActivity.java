package com.example.shoppingmall.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.shoppingmall.Item;
import com.example.shoppingmall.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // Item 객체들을 담기 위한 arrayList(어댑터로 전송)
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        reference = database.getReference("item"); // 데이터베이스 연결
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            // 파이어베이스 데이터베이스를 받아오는 것
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear(); // 배열 초기화
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    // for문으로 데이터 리스트를 추출함
                    Item item = snapshot.getValue(Item.class); // Item 객체에 데이터를 담음
                    arrayList.add(item);
                }
                adapter.notifyDataSetChanged(); // 저장, 새로고침
            }

            @Override
            // 데이터베이스를 가져오는 것이 실패했을 경우
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ERROR", "데이터를 가져오지 못했습니다.");
            }
        });

        // 리싸이클러 뷰에 어댑터 연결
        adapter = new Adapter(arrayList,this);
        recyclerView.setAdapter(adapter);
    }
}