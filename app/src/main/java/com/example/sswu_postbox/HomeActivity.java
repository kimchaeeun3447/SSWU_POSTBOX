package com.example.sswu_postbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    //Recyclerview
    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        home_keyword_list();


        Button plus_keyword_btn = findViewById(R.id.my_keyword_list_plus_btn);
        plus_keyword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KeywordSettingActivity.class);
                startActivity(intent);
            }
        });


        Button check_keyword_post_btn = findViewById(R.id.check_keyword_post_btn);
        check_keyword_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckKeywordPostActivity.class);
                startActivity(intent);
            }
        });

/*
        ImageButton notification_btn = (ImageButton)findViewById(R.id.notification_btn);
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationListActivity.class);
                startActivity(intent);
            }
        });


        ImageButton locker_btn = (ImageButton)findViewById(R.id.locker_btn);
        locker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LockerActivity.class);
                startActivity(intent);
            }
        });

*/
    }


    //Recyclerview
    private void home_keyword_list(){

        recyclerView = findViewById(R.id.home_keyword_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("키워드1");
        itemList.add("키워드2");
        itemList.add("키워드3");
        itemList.add("키워드4");
        itemList.add("키워드5");
        itemList.add("키워드6");
        itemList.add("키워드7");
        itemList.add("키워드8");
        itemList.add("키워드9");

        adapter = new MyRecyclerAdapter(this, itemList, onClickItem);
        recyclerView.setAdapter(adapter);

    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(HomeActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    };
}