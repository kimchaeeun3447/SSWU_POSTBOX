package com.example.sswu_postbox;

import androidx.annotation.Dimension;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class CheckKeywordPostActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_keyword_post);


        final GridView my_keyword_list = (GridView)findViewById(R.id.my_keyword_list2);
        MyGridAdapter gridAdapter = new MyGridAdapter(this);
        my_keyword_list.setAdapter(gridAdapter);


        ImageButton back_btn = (ImageButton)findViewById(R.id.check_keyword_post_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button add_keyword_btn = (Button)findViewById(R.id.my_keyword_list_plus_btn);
        add_keyword_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KeywordSettingActivity.class);
                startActivity(intent);
            }
        });


        // 웹뷰 시험
        TextView title = findViewById(R.id.announcement_example_title);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PostClickActivity.class);
                startActivity(intent);

                WebView webview = findViewById(R.id.webview);
                webview.loadUrl("https://www.sungshin.ac.kr/main_kor/11107/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGbWFpbl9rb3IlMkYzMTgxJTJGMTAzMjc4JTJGYXJ0Y2xWaWV3LmRvJTNG");

                webview.setWebViewClient(new WebViewClient(){
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        view.loadUrl(request.getUrl().toString());

                        return true;
                    }
                });



            }
        });
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