package com.example.sswu_postbox;

import androidx.annotation.Dimension;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class CheckKeywordPostActivity extends AppCompatActivity {

    String[] user_keyword_list = { "A" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_keyword_post);

        final GridView my_keyword_list = (GridView)findViewById(R.id.my_keyword_list2);
        MyGridAdapter gridAdapter = new MyGridAdapter(this);
        my_keyword_list.setAdapter(gridAdapter);
    }

    // Gridview Adapter
    public static class MyGridAdapter extends BaseAdapter {
        Context context;

        //사용자가 등록한 모든 키워드들을 받아올 변수(임시로 텍스트 넣어둠)
        String[] user_keyword_list = {"one", "two", "three",
                "four", "five","six",
                "7","8","9",
                "10","11","12",
                "13","14","15",
                "16","17","18"};


        public MyGridAdapter(Context c){
            context = c;
        }

        @Override
        //보여줄 키워드 개수
        public int getCount() {
            return user_keyword_list.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        // 띄우는 부분
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView = new TextView(context);
            textView.setLayoutParams(new GridView.LayoutParams(300,100));
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(Dimension.DP, 30);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.keyword_search));

            textView.setText(user_keyword_list[position]);

            return textView;

        }
    }

}