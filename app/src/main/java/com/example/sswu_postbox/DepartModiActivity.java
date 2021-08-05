package com.example.sswu_postbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class DepartModiActivity extends AppCompatActivity {

    Button modi_button;
    Spinner after_major;
    String selectedMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart_modi);

        modi_button = findViewById(R.id.modi_button);
        after_major = findViewById(R.id.after_major);

//        뒤로 가기 버튼
        ImageButton modi_back_btn = findViewById(R.id.modi_back_btn);
        modi_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        수정 버튼 클릭 시
        modi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();
            }
        });
    }

//    학과 수정
    void modify() {
        String url = "http://3.37.68.242:8000/users/";

        selectedMajor = after_major.getSelectedItem().toString();
    }


}