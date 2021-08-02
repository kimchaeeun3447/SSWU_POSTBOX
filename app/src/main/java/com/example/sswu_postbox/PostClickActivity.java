package com.example.sswu_postbox;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

public class PostClickActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_click);

        Intent intent = getIntent();
        String titleText = intent.getStringExtra("webView_title");
        String dateText = intent.getStringExtra("webView_date");
        String url = intent.getStringExtra("url");

        //TextView webView_keyword = findViewById(R.id.webview_keyword);

        TextView webView_title = findViewById(R.id.webview_title);
        webView_title.setText(titleText);


        webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl(url);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());


        ImageButton activity_post_click_close_btn = findViewById(R.id.activity_post_click_close_btn);
        activity_post_click_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}
