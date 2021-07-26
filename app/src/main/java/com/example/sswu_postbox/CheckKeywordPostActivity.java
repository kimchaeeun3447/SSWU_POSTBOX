package com.example.sswu_postbox;

import androidx.annotation.Dimension;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckKeywordPostActivity extends AppCompatActivity {
    String TAG = CheckKeywordPostActivity.class.getSimpleName();

    GridView my_keyword_list;
    MyGridAdapter gridAdapter;

    //listView
    ListView postList;
    ArrayList<String> post_keyword = new ArrayList<String>();
    ArrayList<String> post_title = new ArrayList<String>();
    ArrayList<String> post_date = new ArrayList<String>();
    MyListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_keyword_post);


        my_keyword_list = (GridView)findViewById(R.id.my_keyword_list2);
        gridAdapter = new MyGridAdapter(this);
        my_keyword_list.setAdapter(gridAdapter);

        keyword_list();

        // listView


        postList = findViewById(R.id.keyword_post_listView);
        myListAdapter = new MyListAdapter(this, post_keyword, post_title, post_date);
        postList.setAdapter(myListAdapter);

        notice_list();

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



        /* 웹뷰 시험
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
        }); */

    }

    void keyword_list() {
        String TAG = KeywordSettingActivity.class.getSimpleName();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        String url = "http://3.37.68.242:8000/detail/keywords/";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                gridAdapter.user_keyword_list.add(response.getJSONObject(i).getString("keyword"));
                                gridAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "keyword list get fail");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return give_token(token);
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    void notice_list() {
        String TAG = KeywordSettingActivity.class.getSimpleName();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        String url = "http://3.37.68.242:8000/userNotice/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject notice = response.getJSONObject(i).getJSONObject("notice");
                                post_keyword.add("keyword");
                                post_title.add(notice.getString("title"));
                                post_date.add(notice.getString("date"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myListAdapter.notifyDataSetChanged();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d(TAG, "notice user list error");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return give_token(token);
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    Map<String, String> give_token(String token) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        return headers;
    }
}